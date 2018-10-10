import java.util.*;

public class FrontendHandler extends Subscriber {
    final LoginForm loginForm;
    final MessageForm messageForm;
    final SignupForm signupForm;

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
            else
                messageForm.newNotifier(r.result().get(0) + ":" + r.result().get(1));
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

    public void call_shutdown() {
        obs.shutdown();
    }
}
