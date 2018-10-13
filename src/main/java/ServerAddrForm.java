import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ServerAddrForm {
	private JFrame frame;
	private JTextField textField;
	private boolean ok = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerAddrForm window = new ServerAddrForm();
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
	public ServerAddrForm() {
		initialize();
	}

	public String getAddr() {
		return textField.getText();
	}

	public boolean okPressed() {
		return ok;
	}

	public void setVisible(boolean b) {
		frame.setVisible(b);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 99);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblServerAddress = new JLabel("Server Address:");
		lblServerAddress.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblServerAddress.setBounds(21, 23, 89, 14);
		frame.getContentPane().add(lblServerAddress);

		textField = new JTextField();
		textField.setBounds(120, 22, 204, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		textField.setText("localhost");

		JButton btnOk = new JButton("OK");
		btnOk.setBounds(335, 21, 89, 23);
		frame.getContentPane().add(btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ok = true;
				frame.setVisible(false);
			}
		});
	}
}
