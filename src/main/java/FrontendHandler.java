import java.util.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FrontendHandler extends Subscriber {
    final LoginForm loginForm;
    final MessageForm messageForm;
    final SignupForm signupForm;

    String myUsername;
    String myIP;
    String myPort;

    String currentChatUser;

    // Map of user --> messages
    // session only
    Map<String, LinkedList<String>> messageLog = new HashMap<String, LinkedList<String>>();

    public FrontendHandler(Observer obs) {
        this.obs = obs;
        this.se = ServiceEnum.FRONTEND_HANDLER;

        loginForm = new LoginForm(this);
        messageForm = new MessageForm(this);
        signupForm = new SignupForm(this);

        showForm(FormType.LOGIN_FORM);
    }

    public void run() {
        // just wait
        while (!shutdown) {
            InternalRequest r = wait_request();
            System.out.println("[FH]::" + r);
            if (r.task().equals("exit"))
                break;

            if (r.task().equals("signin")) {
                if (r.from() == ServiceEnum.MESSAGE_SWARM) {
                    myIP = r.param().get(0);
                    myPort = r.param().get(1);
                }
            }

            else if (r.task().equals("newmessage")) {
                // new message
                String username = r.param().get(0);
                String msg = r.param().get(1);
                String log_msg = "[" + username + "]: " + msg;

                if (username.equals(currentChatUser)) {
                    messageForm.newMessage(username, msg);
                    messageLog.get(username).addFirst(log_msg);
                }

                else {
                    messageForm.setnewNotify(username);
                    messageForm.newNotifier(username);
                    if (!messageLog.containsKey(username)) {
                        messageLog.put(username, new LinkedList<String>(Arrays.asList(log_msg)));
                    } else
                        messageLog.get(username).addFirst(log_msg);
                }
            }

            else if (r.task().equals("newfile")) {
                // new file
                String username = r.param().get(0);
                String file = r.param().get(1);
                String log_msg = "[" + username + "]:FILE: " + file;

                if (username.equals(currentChatUser)) {
                    messageForm.newMessage(username, log_msg);
                    messageLog.get(username).addFirst(log_msg);
                }

                else {
                    messageForm.setnewNotify(username);
                    messageForm.newNotifier(username);
                    if (!messageLog.containsKey(username)) {
                        messageLog.put(username, new LinkedList<String>(Arrays.asList(log_msg)));
                    } else
                        messageLog.get(username).addFirst(log_msg);
                }
            }
        }

        loginForm.setVisible(false);
        messageForm.setVisible(false);
        signupForm.setVisible(false);
    }

    @Override
    public void receive_answer(InternalRequest r) {
        System.out.println("[FH]::" + r);

        if (r.task().equals("signin")) {
            if (!r.success())
                return;

            // From SERVER_CONNECTOR
            myUsername = r.param().get(0);
            messageForm.setCurrentUserInfo(myUsername, myIP, myPort);
            showForm(FormType.MESSAGE_FORM);
        }

        else if (r.task().equals("signup")) {
            if (!r.success())
                return;
            System.out.println(r.result().get(0));
            showForm(FormType.LOGIN_FORM);
        }

        else if (r.task().equals("getip")) {
            if (!r.success()) {
                messageForm.Error("Error getip!");
                return;
            }
            String username = r.param.get(0);
            String ip = r.result().get(0);
            String port = r.result().get(1);

            currentChatUser = username;
            messageForm.newNotifier(username);
            if (!messageLog.containsKey(username)) {
                messageLog.put(username, new LinkedList<String>());
            }

            // sends a record to MessageHandler to saves IP and port of people
            obs.notify(new InternalRequest(
                se, ServiceEnum.MESSAGE_HANDLER, "newrecord", Arrays.asList(username, ip, port)));
        }
    }

    public void showForm(FormType ft) {
        switch (ft) {
            case LOGIN_FORM:
                loginForm.setVisible(true);
                messageForm.setVisible(false);
                signupForm.setVisible(false);
                break;
            case SIGNUP_FORM:
                signupForm.setVisible(true);
                messageForm.setVisible(false);
                loginForm.setVisible(false);
                break;
            case MESSAGE_FORM:
                messageForm.setVisible(true);
                signupForm.setVisible(false);
                loginForm.setVisible(false);
                break;
        }
    }

    public void changeCurrentChatUser(String username) {
        currentChatUser = username;
        messageForm.setMessageBoard(messageLog.get(username));
    }

    public void login(String username, String password) {
        obs.notify(new InternalRequest(
            se, ServiceEnum.SERVER_CONNECTOR, "signin", Arrays.asList(username, password)));
    }

    public void signup(String username) {
        obs.notify(new InternalRequest(
            se, ServiceEnum.SERVER_CONNECTOR, "signup", Arrays.asList(username)));
    }

    public void getIP(String name, String type) {
        obs.notify(
            new InternalRequest(se, ServiceEnum.SERVER_CONNECTOR, "getip", Arrays.asList(name)));
        // new InternalRequest(se, ServiceEnum.SERVER_CONNECTOR, "getip", Arrays.asList(name,
        // type)));
    }

    public void sendMessage(String msg) {
        messageLog.get(currentChatUser).addFirst("[Me]: " + msg);
        obs.notify(new InternalRequest(
            se, ServiceEnum.MESSAGE_HANDLER, "sendmessage", Arrays.asList(currentChatUser, msg)));
    }

    public void sendFile(String filename) {
        obs.notify(new InternalRequest(
            se, ServiceEnum.MESSAGE_HANDLER, "sendfile", Arrays.asList(currentChatUser, filename)));
    }

    public void call_shutdown() {
        obs.shutdown();
    }
}
