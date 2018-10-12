import java.io.*;
import java.net.*;
import java.util.*;

import com.google.gson.*;
import com.google.gson.reflect.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;


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

                if(msg.file != null){
                    sendFileFlag = true;
                    filename = msg.file;
                }
            } catch (IOException ex) {
                // pass
            } catch (IllegalArgumentException ex) {
                System.out.println("[P]::Receive from server is not valid base64");
                break;
            } catch (JsonParseException ex) {
                System.out.println("[P]::Receive from server is not valid json");
                break;
            }

            System.out.println("[P]:[" + socket.getRemoteSocketAddress() + "]:" + msg);
            obs.send_message(msg);
        }
    }

    private void receive_file() {
        try{
            FileOutputStream fos = new FileOutputStream(filename);

            byte[] buffer = new byte[4096];
		
		    int filesize = 15123; // Send file size in separate msg
		    int read = 0;
		    int totalRead = 0;
		    int remaining = filesize;
		    while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
			    totalRead += read;
			    remaining -= read;
			    System.out.println("read " + totalRead + " bytes.");
			    fos.write(buffer, 0, read);
            }
            fos.close();
            dis.close();
        }catch(IOException e){
            System.out.println("Error File");
        }        
            
        //obs.send_message(msg);
    }
}
