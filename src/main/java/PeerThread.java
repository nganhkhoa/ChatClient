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

    PeerThread(Socket socket, DataInputStream dis, Observer obs) {
        this.socket = socket;
        this.dis = dis;
        this.obs = obs;
    }

    // TODO: Find a way to shutdown this beautifully

    @Override
    public void run() {
        while (!shutdown) {
            try {
                // wait for message from peer
                String out = dis.readUTF();
                System.out.println("[P]:[" + socket.getRemoteSocketAddress() + "]:" + out);
                // obs.notify();
            } catch (Exception ex) {
                break;
            }
        }
    }
}
