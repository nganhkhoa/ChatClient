import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.*;

// import org.json.*;
import com.google.gson.*;
import com.google.gson.reflect.*;

public class ServerConnector extends Subscriber {
    final private int PORT = 7789;
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;
    private InetAddress ip;

    private Gson gson;

    private boolean isLoggedIn = false;

    private String username;
    private String password;
    private String IP; // of message swarm
    private int port; // from message swarm

    public ServerConnector(Observer obs, String serverAddress) throws IOException {
        this.obs = obs;
        this.se = ServiceEnum.SERVER_CONNECTOR;
        gson = new Gson();

        // server ip
        ip = InetAddress.getByName(serverAddress);

        s = new Socket(ip, PORT);
        dis = new DataInputStream(s.getInputStream());
        dos = new DataOutputStream(s.getOutputStream());

        System.out.println("[SC]::Connected to " + serverAddress + " on port " + PORT);
    }

    public void run() {
        while (!shutdown) {
            InternalRequest r = wait_request();
            ServerRequest sreq;
            ServerResponse sres = null;
            System.out.println("[SC]::" + r);

            if (r.task().equals("exit"))
                break;

            if (!isLoggedIn && !r.task().equals("signup")) {
                if (!r.task().equals("signin"))
                    continue;

                if (r.from() == ServiceEnum.MESSAGE_SWARM) {
                    if (r.param().size() != 2)
                        continue;
                    IP = r.param().get(0);
                    port = Integer.parseInt(r.param().get(1));
                } else if (r.from() == ServiceEnum.FRONTEND_HANDLER) {
                    // set username and password
                    if (r.param().size() != 2)
                        continue;
                    username = r.param().get(0);
                    password = r.param().get(1);
                }

                if (username == null || password == null || port == 0)
                    continue;

                // enough information to signin
                sreq = new ServerRequest(
                    "signin", Arrays.asList(username, password, IP, Integer.toString(port)));
            } else {
                sreq = new ServerRequest(r.task(), r.param());
            }
            try {
                String json_msg = gson.toJson(sreq);
                byte[] encoded_msg = Base64.getEncoder().encode(json_msg.getBytes());
                String tosend = new String(encoded_msg);

                dos.writeUTF(tosend);
                encoded_msg = dis.readUTF().getBytes();

                json_msg = new String(Base64.getDecoder().decode(encoded_msg));
                sres = gson.fromJson(json_msg, ServerResponse.class);
            } catch (IOException ex) {
                // pass
            } catch (IllegalArgumentException ex) {
                System.out.println("[SC]::Receive from server is not valid base64");
                continue;
            } catch (JsonParseException ex) {
                System.out.println("[SC]::Receive from server is not valid json");
                continue;
            }

            System.out.println("[SC]::SERVER:" + sres);

            // process sres
            if (!isLoggedIn && !r.task().equals("signup")) {
                if (sres.success()) {
                    isLoggedIn = true;
                    r.answer(true);
                } else {
                    // well, wrong credentials
                    username = null;
                    password = null;
                    r.answer(false);
                }
                obs.send_answer(r.from(), r);
                obs.send_answer(ServiceEnum.MESSAGE_HANDLER, r);
                obs.send_answer(ServiceEnum.FRONTEND_HANDLER, r);
                continue;
            }

            // now the user is logged in and requesting
            if (sreq.getTask() == "signup") {
                r.answer(sres.success(), sres.getResult());
                obs.send_answer(r.from(), r);
            }

            else if (sreq.getTask() == "getip") {
                r.answer(sres.success(), sres.getResult());
                obs.send_answer(r.from(), r);
            }
        }

        System.out.println("[SC]::Shutting down");

        String tosend = gson.toJson(new ServerRequest("exit"));
        byte[] encoded_msg = Base64.getEncoder().encode(tosend.getBytes());
        tosend = new String(encoded_msg);

        try {
            dos.writeUTF(tosend);
            dis.readUTF();
        } catch (IOException e) {
            // pass
        }

        System.out.println("[SC]::Shut down complete");
    }
}
