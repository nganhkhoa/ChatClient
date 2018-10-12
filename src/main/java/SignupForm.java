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

public class SignupForm {
    private JFrame frame;
    private JTextField txtUsername;
    private JTextField txtPassword;
    private FrontendHandler feh;
    private JPasswordField passwordField;
    private JPasswordField passwordField_1;

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

    public int check(String input) {
    	if (input.length() >= 5 && input.length() <= 32) {
    		return 1;
    	}
    	else 
    		return 0;
    }
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 448, 262);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblUsername.setBounds(16, 42, 64, 20);
        frame.getContentPane().add(lblUsername);

        /* JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPassword.setBounds(43, 75, 64, 14);
        frame.getContentPane().add(lblPassword);
        */

        txtUsername = new JTextField();
        txtUsername.setBounds(138, 44, 190, 20);
        frame.getContentPane().add(txtUsername);
        txtUsername.setColumns(10);

        /* txtPassword = new JPasswordField();
        txtPassword.setBounds(122, 73, 164, 20);
        frame.getContentPane().add(txtPassword);
        */

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(76, 189, 89, 23);
        frame.getContentPane().add(btnSubmit);
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //String password = txtPassword.getText();
                String username = txtUsername.getText();

                //System.out.println(password + username);

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
        btnExit.setBounds(206, 189, 89, 23);
        frame.getContentPane().add(btnExit);
        
        JLabel lblPassword = new JLabel("New password");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPassword.setBounds(16, 77, 95, 20);
        frame.getContentPane().add(lblPassword);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(138, 79, 190, 20);
        frame.getContentPane().add(passwordField);
        
        JLabel lblRepassword = new JLabel("Retype password");
        lblRepassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblRepassword.setBounds(16, 111, 106, 19);
        frame.getContentPane().add(lblRepassword);
        
        passwordField_1 = new JPasswordField();
        passwordField_1.setBounds(138, 110, 190, 20);
        frame.getContentPane().add(passwordField_1);
        
        JLabel lblNotice = new JLabel("Password: 5 - 32 letters");
        lblNotice.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblNotice.setBounds(138, 136, 167, 28);
        frame.getContentPane().add(lblNotice);
        
        JButton btnCheck = new JButton("Check");
        btnCheck.setBounds(338, 43, 89, 23);
        frame.getContentPane().add(btnCheck);
        
        JLabel lblNoticeCorrect = new JLabel("");
        lblNoticeCorrect.setBounds(138, 64, 106, 14);
        frame.getContentPane().add(lblNoticeCorrect);
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
