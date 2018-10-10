import java.io.*;
import java.net.*;
import java.util.*;

import com.google.gson.*;
import com.google.gson.reflect.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PeerThread extends Subscriber {
    final Socket socket;
    final DataInputStream dis;
    final Observer obs;

    Gson gson;

    // only for receiving files
    boolean sendFileFlag = false;
    int fileSize = 0;
    String filename;

    PeerThread(Socket socket, DataInputStream dis, Observer obs) {
        this.socket = socket;
        this.dis = dis;
        this.obs = obs;

        gson = new Gson();
    }

    // TODO: Find a way to shutdown this beautifully

    @Override
    public void run() {
        while (!shutdown) {
            if (sendFileFlag) {
                receive_file();
            }

            Message msg = null;

            try {
                // wait for message from peer
                byte[] encoded_msg = dis.readUTF().getBytes();
                String json_msg = new String(Base64.getDecoder().decode(encoded_msg));
                msg = gson.fromJson(json_msg, Message.class);
            } catch (IOException ex) {
                // pass
            } catch (IllegalArgumentException ex) {
                System.out.println("[P]::Receive from server is not valid base64");
                continue;
            } catch (JsonParseException ex) {
                System.out.println("[P]::Receive from server is not valid json");
                continue;
            }

            System.out.println("[P]:[" + socket.getRemoteSocketAddress() + "]:" + msg);
            obs.send_message(msg);
        }
    }

    private void receive_file() {}
}
