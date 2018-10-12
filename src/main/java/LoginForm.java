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
import javax.swing.JTextPane;

public class LoginForm {
    private JFrame frame;
    private JTextField txtUsername;
    private JTextField txtPassword;
    private FrontendHandler feh;
    int term = 0;

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
    
    public void close() {
    	WindowEvent winClosingEvent = new WindowEvent(this.frame,WindowEvent.WINDOW_CLOSING);
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
        btnLogIn.setBounds(43, 122, 89, 23);
        frame.getContentPane().add(btnLogIn);
        btnLogIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = txtPassword.getText();
                feh.login(username, password);
            }
        });

        JButton btnSignUp = new JButton("Sign up");
        btnSignUp.setBounds(143, 122, 89, 23);
        frame.getContentPane().add(btnSignUp);
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                feh.showForm(FormType.SIGNUP_FORM);
            }
        });

        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(243, 122, 89, 23);
        frame.getContentPane().add(btnExit);
        
        JLabel lblNoticeError = new JLabel("");
        lblNoticeError.setBounds(122, 97, 164, 14);
        frame.getContentPane().add(lblNoticeError);
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame = new JFrame("Exit");
                if (JOptionPane.showConfirmDialog(frame, "Confirm if you want to exit",
                        "Login System", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_NO_OPTION) {
                  	close();
                }
                
            }
        });
    }
}
