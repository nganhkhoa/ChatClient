import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.*;

// import org.json.*;
import com.google.gson.*;
import com.google.gson.reflect.*;

public class MessageSwarm extends Subscriber {
    public static int PORT = 6660;
    private ServerSocket serversocket;
    private Socket socket;
    private DataInputStream dis;

    List<Subscriber> peers;

    public MessageSwarm(Observer obs) throws IOException {
        this.se = ServiceEnum.MESSAGE_SWARM;
        this.obs = obs;
        peers = new LinkedList<Subscriber>();

        while(true){
            try {
                // create a socket server to listen
                serversocket = new ServerSocket(PORT);
                break;
            } catch (IOException ex) {
                PORT += 1;
                continue;
            }
        }
    }

    public void run() {
        // notify server the port im listening to
        obs.notify(new InternalRequest(
            se, ServiceEnum.SERVER_CONNECTOR, "signin", Arrays.asList(Integer.toString(PORT))));

        while (!shutdown) {
            try {
                socket = serversocket.accept();
                dis = new DataInputStream(socket.getInputStream());
                Subscriber t = new PeerThread(socket, dis, obs);
                // obs.add(t);
                peers.add(t);
                t.start();
            } catch (Exception ex) {
                // pass
            }
        }

        // close peers
        for (Subscriber peer : peers) {
            try {
                peer.interrupt();
            } catch (Exception ex) {
                // pass
            }
        }
    }
}
