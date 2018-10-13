import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ChatClient {
    public static void main(String[] argv) {
        Observer observer = new Observer();

        Subscriber server;
        Subscriber messageHandler;
        Subscriber messageSwarm;
        Subscriber frontendHandler;

        // server address selector
        ServerAddrForm saf = new ServerAddrForm();
        saf.setVisible(true);
        while (!saf.okPressed()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                // pass
                return;
            }
        }

        try {
            server = new ServerConnector(observer, saf.getAddr());
        } catch (IOException e) {
            System.out.println("Cannot connect to server");
            return;
        }
        try {
            messageHandler = new MessageHandler(observer);
        } catch (IOException e) {
            System.out.println("Cannot start MessageHandler");
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
        observer.add(messageHandler);
        observer.add(messageSwarm);
        observer.add(frontendHandler);

        server.start();
        messageHandler.start();
        messageSwarm.start();
        frontendHandler.start();

        try {
            server.join();
            System.out.println("SERVER_CONNECTOR SHUTDOWN OK");
            messageHandler.join();
            System.out.println("MESSAGE_HANDLER SHUTDOWN OK");
            frontendHandler.join();
            System.out.println("FRONTEND HANDLER SHUTDOWN OK");
            // not easy to shutdown this
            // messageSwarm.join();
        } catch (InterruptedException e) {
            // pass
        }

        System.out.println("ALL PROCESS SHUTDOWN");
        System.out.println("NO ERROR DETECTED");
    }
}
