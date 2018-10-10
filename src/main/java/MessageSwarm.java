import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.*;

// import org.json.*;
import com.google.gson.*;
import com.google.gson.reflect.*;

public class MessageSwarm extends Subscriber {
    public static int PORT = 6660;
    private String IP;
    private ServerSocket serversocket;
    private Socket socket;
    private DataInputStream dis;

    List<Subscriber> peers;

    public MessageSwarm(Observer obs) throws IOException {
        this.se = ServiceEnum.MESSAGE_SWARM;
        this.obs = obs;
        peers = new LinkedList<Subscriber>();

        while (true) {
            try {
                // create a socket server to listen
                serversocket = new ServerSocket(PORT);
                break;
            } catch (IOException ex) {
                PORT += 1;
                continue;
            }
        }

        // get IP of machine in LAN
        // https://stackoverflow.com/questions/9481865/getting-the-ip-address-of-the-current-machine-using-java
        try (final DatagramSocket s = new DatagramSocket()) {
            s.connect(InetAddress.getByName("8.8.8.8"), 10002);
            this.IP = s.getLocalAddress().getHostAddress();
        } catch (Exception ex) {
            // pass
        }
    }

    public void run() {
        // notify server the port im listening to
        obs.notify(new InternalRequest(
            se, ServiceEnum.SERVER_CONNECTOR, "signin", Arrays.asList(IP, Integer.toString(PORT))));

        // TODO: Because ServerSocket.accept() blocks until a connection is receive
        // The while checking is never to be asset until a connection is receive
        // A quick search on google hands us "Using time out" and "ServerSocketChannel"
        // Using time out is a good approch but handling exception is bad
        // ServerSocketChannel, however is the New Input Output for Java
        // In the future, change to ServerSocketChannel
        // The code will be like this:
        //
        // while (!shutdown) {
        //      socket = serversocket.accept();
        //      if (socket == null) {
        //          try {
        //              sleep(100);
        //          }
        //          catch (InterruptedException ex) {
        //              // pass
        //          }
        //          continue;
        //      }
        //      // process socket connection here
        // }
        while (!shutdown) {
            try {
                socket = serversocket.accept();
                System.out.println("[MS]::Receive connection");
                dis = new DataInputStream(socket.getInputStream());
                Subscriber t = new PeerThread(socket, dis, obs);
                peers.add(t);
                t.start();
            } catch (Exception ex) {
                // pass
            }
        }

        // close peers
        // not a good way, I know
        for (Subscriber peer : peers) {
            try {
                peer.interrupt();
                peer.join();
            } catch (Exception ex) {
                // pass
            }
        }
    }
}
