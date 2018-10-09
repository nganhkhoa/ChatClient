import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.*;

// import org.json.*;
import com.google.gson.*;
import com.google.gson.reflect.*;

// Client class
public class ClientTest {
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;
    private InetAddress ip;
    private Scanner scn;

    private Gson gson = new Gson();

    public ClientTest() throws IOException {
        try {
            scn = new Scanner(System.in);

            // getting localhost ip
            ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056
            s = new Socket(ip, 6660);

            // obtaining input and out streams
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
        } catch (IOException ex) {
            // pass
            System.out.println("Cannot connect to server");
            throw ex;
        }
    }

    public void run() {
        String tosend = "";
        try {
           
                tosend = "hey";
                dos.writeUTF(tosend);
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ClientTest client = new ClientTest();
            client.run();
        } catch (IOException ex) {
            return;
        }
    }

}
