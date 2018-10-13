import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

public class SignupForm {
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
                    SignupForm window = new SignupForm();
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
    public SignupForm() {
        initialize();
    }
    public SignupForm(FrontendHandler feh) {
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

        /* JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPassword.setBounds(43, 75, 64, 14);
        frame.getContentPane().add(lblPassword);
        */

        txtUsername = new JTextField();
        txtUsername.setBounds(122, 44, 164, 20);
        frame.getContentPane().add(txtUsername);
        txtUsername.setColumns(10);

        /* txtPassword = new JPasswordField();
        txtPassword.setBounds(122, 73, 164, 20);
        frame.getContentPane().add(txtPassword);
        */

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(70, 116, 89, 23);
        frame.getContentPane().add(btnSubmit);
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // String password = txtPassword.getText();
                String username = txtUsername.getText();

                // System.out.println(password + username);

                if (username == null) {
                    JOptionPane.showMessageDialog(
                        null, "Invalid Signup", "Signup Errorr", JOptionPane.ERROR_MESSAGE);
                    txtPassword.setText(null);
                    txtUsername.setText(null);
                }

                else {
                    feh.signup(username);
                }
            }
        });

        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(200, 116, 89, 23);
        frame.getContentPane().add(btnExit);
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame = new JFrame("Exit");
                if (JOptionPane.showConfirmDialog(frame, "Confirm if you want to exit",
                        "Login System", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_NO_OPTION) {
                    feh.call_shutdown();
                }
            }
        });
    }
}
