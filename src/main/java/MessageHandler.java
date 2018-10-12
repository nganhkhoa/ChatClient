import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.*;

// import org.json.*;
import com.google.gson.*;
import com.google.gson.reflect.*;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

class Connection {
    public List<Socket> s;
    public List<DataOutputStream> dos;

    public List<String> IP;
    public List<Integer> port;
}

public class MessageHandler extends Subscriber {
    // username --> coresponding socket
    Map<String, Connection> connection = new HashMap<String, Connection>();
    String Username = null;
    String myIP = null;
    int myPort = 0;

    final int BUFFER_SIZE = 4096;

    Gson gson = new Gson();

    public MessageHandler(Observer obs) throws IOException {
        this.obs = obs;
        this.se = ServiceEnum.MESSAGE_HANDLER;
    }

    @Override
    public void run() {
        while (!shutdown) {
            InternalRequest r = wait_request();
            System.out.println("[MH]::" + r);
            if (r.task().equals("exit"))
                break;

            if (r.task().equals("signin")) {
                // from MessageSwarm
                myIP = r.param().get(0);
                myPort = Integer.parseInt(r.param().get(1));
            }

            else if (r.task().equals("newrecord")) {
                // receives from FrontendHandler, after a successful getip
                // Should it be receiving from ServerConnector?
                // newrecord(username, ip, port)
                String username = r.param().get(0);
                String ip = r.param().get(1);
                String port = r.param().get(2);
                if (connection.containsKey(username))
                    continue;

                Connection c = new Connection();
                c.IP = Arrays.asList(myIP, ip);
                c.port = Arrays.asList(myPort, Integer.parseInt(port));
                connection.put(username, c);
            }

            else if (r.task().equals("sendmessage")) {
                // sendmessage(to, msg)
                String to = r.param().get(0);
                String msg = r.param().get(1);
                Connection c = connection.get(to);

                if (c.s == null)
                    create_socket(c);

                Message m = new Message(Username, to, c.IP, c.port, msg, false);
                String json_msg = gson.toJson(m);
                byte[] encoded_msg = Base64.getEncoder().encode(json_msg.getBytes());
                String tosend = new String(encoded_msg);
                for (DataOutputStream dos : c.dos) {
                    try {
                        dos.writeUTF(tosend);
                    } catch (IOException ex) {
                        // pass
                    }
                }
            }

            else if (r.task().equals("sendfile")) {
                String to = r.param().get(0);
                String filedirectory = r.param().get(1);
                int index = filedirectory.lastIndexOf("\\");
                String filename = filedirectory.substring(index + 1);

                Connection c = connection.get(to);
                if (c.s == null)
                    create_socket(c);

                Message m = new Message(Username, to, c.IP, c.port, filename, true);
                String json_msg = gson.toJson(m);
                byte[] encoded_msg = Base64.getEncoder().encode(json_msg.getBytes());
                String tosend = new String(encoded_msg);

                for (DataOutputStream dos : c.dos) {
                    try {
                        dos.writeUTF(tosend);
                    } catch (IOException ex) {
                        // pass
                    }
                }

                byte[] buffer = new byte[BUFFER_SIZE];
                for (DataOutputStream dos : c.dos) {
                    try {
                        FileInputStream fis = new FileInputStream(new File(filedirectory));
                        while (fis.read(buffer) > 0) {
                            // System.out.println("file : " + buffer);
                            dos.write(buffer);
                        }
                        fis.close();
                    } catch (IOException ex) {
                        // pass
                    }
                }
            }
        }

        // closing socket
        System.out.println("[MH]::Shutting down");
        for (Map.Entry<String, Connection> entry : connection.entrySet()) {
            try {
                Connection c = entry.getValue();
                for (Socket s : c.s) s.close();
            } catch (IOException ex) {
                // pass
            } catch (NullPointerException ex) {
                // pass
            }
        }
        System.out.println("[MH]::Shut down complete");
    }

    @Override
    public void receive_answer(InternalRequest r) {
        System.out.println("[MH]::" + r);
        if (!r.success())
            return;
        if (r.task().equals("signin")) {
            // from ServerConnector
            Username = r.param().get(0);
        }
    }

    @Override
    public void receive_message(Message m) {
        // receives message from MessageSwarm Peer
        if (m.sendfile()) {
            return;
        }

        if (!connection.containsKey(m.from)) {
            Connection c = new Connection();
            // try {
            //     InetAddress ip = InetAddress.getByName(m.IP);
            //     c.s = new Socket(ip, m.port);
            //     c.dos = new DataOutputStream(c.s.getOutputStream());
            // } catch (IOException ex) {
            //     // pass
            // }
            c.IP = m.IP;
            c.port = m.port;
            connection.put(m.from, c);
        }
        // Connection c = connection.get(m.from);

        // sends to FrontEndHandler to display
        obs.notify(new InternalRequest(
            se, ServiceEnum.FRONTEND_HANDLER, "newmessage", Arrays.asList(m.from, m.msg)));
    }

    private void create_socket(Connection c) {
        try {
            c.s = new LinkedList<Socket>();
            c.dos = new LinkedList<DataOutputStream>();
            for (int i = 0; i < c.IP.size(); i++) {
                if (c.IP.get(i).equals(myIP) && c.port.get(i) == myPort)
                    continue;
                Socket s = new Socket(c.IP.get(i), c.port.get(i));
                c.s.add(s);
                c.dos.add(new DataOutputStream(s.getOutputStream()));
            }
        } catch (IOException ex) {
            // pass
        }
    }
}
