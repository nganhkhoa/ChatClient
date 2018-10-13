import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuccessfulSignupForm {

	private JFrame frame;
	private JTextField username;
	private JTextField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SuccessfulSignupForm window = new SuccessfulSignupForm();
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
	public SuccessfulSignupForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("You are successful!");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 21));
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setBackground(Color.RED);
		lblNewLabel.setBounds(112, 39, 252, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblYourUsername = new JLabel("Your username:");
		lblYourUsername.setBounds(37, 105, 112, 15);
		frame.getContentPane().add(lblYourUsername);
		
		JLabel lblYourPassword = new JLabel("Your password:");
		lblYourPassword.setBounds(37, 156, 120, 15);
		frame.getContentPane().add(lblYourPassword);
		
		username = new JTextField();
		username.setBackground(Color.WHITE);
		username.setEditable(false);
		username.setBounds(167, 103, 195, 19);
		frame.getContentPane().add(username);
		username.setColumns(10);
		username.setText("User name"); //Khoa sua cho nay nha
		
		password = new JTextField();
		password.setBackground(Color.WHITE);
		password.setEditable(false);
		password.setBounds(167, 154, 199, 19);
		frame.getContentPane().add(password);
		password.setColumns(10);
		password.setText("Random_laksdfjalsdkfjlskdjf");
		
		JButton btnReturn = new JButton("Return");
		btnReturn.setBounds(154, 219, 117, 25);
		frame.getContentPane().add(btnReturn);
		btnReturn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Goi form sign in len
            }
        });
	}
}
