import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSplitPane;

public class MessageForm {
    private JFrame frame;
    private JTextField txtName;
    private JTextField txtInput_message;
    private JTextField txtNamefile;
    JTextArea txtareaShow_message;
    JTextArea txtareaShow_lstfriend;
    private FrontendHandler feh;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MessageForm window = new MessageForm();
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
    public MessageForm() {
        initialize();
    }
    public MessageForm(FrontendHandler feh) {
        initialize();
        this.feh = feh;
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }

    public void newMessage(String user, String message) {
        txtareaShow_message.append("[" + user + "]: ");
        txtareaShow_message.append(message);
        txtareaShow_message.append("\n");
    }

    public void newNotifier(String name) {
        txtareaShow_lstfriend.append(name);
    }

    public void Error(String error) {
        JOptionPane.showMessageDialog(null, "Error", error, JOptionPane.ERROR_MESSAGE);
    }
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 635, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblName = new JLabel("Name");
        lblName.setBounds(23, 35, 68, 14);
        frame.getContentPane().add(lblName);

        txtName = new JTextField();
        txtName.setBounds(91, 25, 200, 30);
        frame.getContentPane().add(txtName);
        txtName.setColumns(10);

        JButton btnGo = new JButton("Go");
        btnGo.setBounds(352, 30, 100, 23);
        frame.getContentPane().add(btnGo);
        btnGo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText();
                feh.getIP(name);
            }
        });

        txtareaShow_message = new JTextArea();
        txtareaShow_message.setBounds(10, 76, 458, 280);
        frame.getContentPane().add(txtareaShow_message);

        txtareaShow_lstfriend = new JTextArea();
        txtareaShow_lstfriend.setBounds(487, 76, 122, 280);
        frame.getContentPane().add(txtareaShow_lstfriend);

        JLabel lblMessage = new JLabel("Message:");
        lblMessage.setBounds(10, 370, 46, 14);
        frame.getContentPane().add(lblMessage);

        txtInput_message = new JTextField();
        txtInput_message.setBounds(88, 367, 380, 20);
        frame.getContentPane().add(txtInput_message);
        txtInput_message.setColumns(10);

        JButton btnSend = new JButton("Send Message");
        btnSend.setBounds(497, 366, 101, 23);
        frame.getContentPane().add(btnSend);
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = txtInput_message.getText();
                txtareaShow_message.append("[Me]: ");
                txtareaShow_message.append(message);
                txtareaShow_message.append("\n");
                txtInput_message.setText(null);
                feh.sendMessage(message);
            }
        });

        JLabel lblFile = new JLabel("File:");
        lblFile.setBounds(10, 408, 46, 14);
        frame.getContentPane().add(lblFile);

        txtNamefile = new JTextField();
        txtNamefile.setBounds(88, 405, 342, 20);
        frame.getContentPane().add(txtNamefile);
        txtNamefile.setColumns(10);

        JButton btnBorrow_File = new JButton("...");
        btnBorrow_File.setBounds(444, 404, 79, 23);
        frame.getContentPane().add(btnBorrow_File);

        JButton btnSend_1 = new JButton("Send");
        btnSend_1.setBounds(530, 404, 79, 23);
        frame.getContentPane().add(btnSend_1);

        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(510, 30, 100, 25);
        frame.getContentPane().add(btnExit);
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame = new JFrame("Exit");
                if (JOptionPane.showConfirmDialog(frame, "Confirm if you want to exit",
                        "Login System", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_NO_OPTION) {
                    feh.call_shutdown();
                    // System.exit(0);
                }
            }
        });
    }
}
