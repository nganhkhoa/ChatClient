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

    String currentChatUser;

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
            System.out.println("[FEH]::" + r);
            if (r.task().equals("exit"))
                break;

            if (r.task().equals("newmessage")) {
                // new message
                String username = r.param().get(0);
                String msg = r.param().get(1);
                messageForm.newMessage(username, msg);
            }
        }

        loginForm.setVisible(false);
        messageForm.setVisible(false);
        signupForm.setVisible(false);
    }

    @Override
    public void receive_answer(InternalRequest r) {
        System.out.println("[FEH]::" + r);

        if (r.task().equals("signin")) {
            if (!r.success())
                return;
            showForm(FormType.MESSAGE_FORM);
        } else if (r.task().equals("signup")) {
            if (!r.success())
                return;
            System.out.println(r.result().get(0));
            showForm(FormType.LOGIN_FORM);
        } else if (r.task().equals("getip")) {
            if (!r.success())
                messageForm.Error("Error getip!");
            else {
                messageForm.newNotifier(r.result().get(0) + ":" + r.result().get(1));
                String username = r.param.get(0);
                String ip = r.result().get(0);
                String port = r.result().get(1);

                currentChatUser = username;

                // sends a record to MessageHandler to saves IP and port of people
                obs.notify(new InternalRequest(se, ServiceEnum.MESSAGE_HANDLER, "newrecord",
                    Arrays.asList(username, ip, port)));
            }
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

    public void login(String username, String password) {
        obs.notify(new InternalRequest(
            se, ServiceEnum.SERVER_CONNECTOR, "signin", Arrays.asList(username, password)));
    }

    public void signup(String username) {
        obs.notify(new InternalRequest(
            se, ServiceEnum.SERVER_CONNECTOR, "signup", Arrays.asList(username)));
    }

    public void getIP(String name) {
        obs.notify(
            new InternalRequest(se, ServiceEnum.SERVER_CONNECTOR, "getip", Arrays.asList(name)));
    }

    public void sendMessage(String msg) {
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
