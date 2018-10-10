import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.*;

public class ChatClient {
    public static void main(String[] argv) {
        Observer observer = new Observer();

        Subscriber server;
        Subscriber messageSwarm;
        Subscriber frontendHandler;

        try {
            server = new ServerConnector(observer, "localhost");
        } catch (IOException e) {
            System.out.println("Cannot connect to server");
            return;
        }
        try {
            messageSwarm = new MessageSwarm(observer);
        } catch (IOException e) {
            System.out.println("Cannot start MessageSwarm");
            return;
        }
        try {
            frontendHandler = new FrontendHandler(observer);
        } catch (Exception e) {
            System.out.println("Cannot start GUI");
            return;
        }

        observer.add(server);
        observer.add(messageSwarm);
        observer.add(frontendHandler);

        server.start();
        messageSwarm.start();
        frontendHandler.start();

        try {
            server.join();
            messageSwarm.join();
            frontendHandler.join();
        } catch (InterruptedException e) {
            // pass
        }

        System.out.println("ALL PROCESS SHUTDOWN");
        System.out.println("NO ERROR DETECTED");
    }
}
