import java.awt.EventQueue;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginForm {
    private JFrame frame;
    private JTextField txtUsername;
    private JTextField txtPassword;
    private FrontendHandler feh;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginForm window = new LoginForm();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public LoginForm() {
        initialize();
    }
    public LoginForm(FrontendHandler feh) {
        initialize();
        this.feh = feh;
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }

    public void showError(String title, String content) {
        JOptionPane.showMessageDialog(null, content, title, JOptionPane.ERROR_MESSAGE);
    }

    public void close() {
        WindowEvent winClosingEvent = new WindowEvent(this.frame, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 392, 211);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblUsername.setBounds(43, 44, 64, 20);
        frame.getContentPane().add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPassword.setBounds(43, 75, 64, 14);
        frame.getContentPane().add(lblPassword);

        txtUsername = new JTextField();
        txtUsername.setBounds(122, 44, 164, 20);
        frame.getContentPane().add(txtUsername);
        txtUsername.setColumns(10);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(122, 73, 164, 20);
        frame.getContentPane().add(txtPassword);

        JButton btnLogIn = new JButton("Log in");
        btnLogIn.setBounds(37, 116, 89, 23);
        frame.getContentPane().add(btnLogIn);
        btnLogIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = txtPassword.getText();

                feh.login(username, password);
            }
        });

        JButton btnSignUp = new JButton("Sign up");
        btnSignUp.setBounds(137, 116, 89, 23);
        frame.getContentPane().add(btnSignUp);
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                feh.showForm(FormType.SIGNUP_FORM);
            }
        });

        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(237, 116, 89, 23);
        frame.getContentPane().add(btnExit);
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                feh.call_shutdown();
            }
        });
    }
}
