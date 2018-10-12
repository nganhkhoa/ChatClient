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
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JSplitPane;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

public class MessageForm {
    private JFrame frame;
    private JTextField txtName;
    private JTextField txtInput_message;
    private JTextField txtNamefile;
    JTextArea txtareaShow_message;
    JTextArea txtareaShow_lstfriend;
    private FrontendHandler feh;
    private JTextField txtInput_friends;
    String fname;
    String filename;

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
    
    public void newMessage(String message, String Username) {
        txtareaShow_message.append("[" + Username + "]: ");
        txtareaShow_message.append(message);
        txtareaShow_message.append("\n");
    }

    public void newNotifier(String name) {
        txtareaShow_lstfriend.append(name);
    }

    public void Error(String error) {
        JOptionPane.showMessageDialog(null, "Error", error, JOptionPane.ERROR_MESSAGE);
    }
    
    public void NewUname(String Newname) {
    	txtName.setText(Newname); 
    }
    
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 635, 508);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblName = new JLabel("Name");
        lblName.setBounds(22, 38, 68, 14);
        frame.getContentPane().add(lblName);

        txtName = new JTextField();
        txtName.setBounds(88, 34, 200, 23);
        frame.getContentPane().add(txtName);
        txtName.setColumns(10);

        JButton btnGo = new JButton("Go");
        btnGo.setBounds(298, 34, 54, 23);
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
            }
        });

        JLabel lblFile = new JLabel("File:");
        lblFile.setBounds(10, 408, 46, 14);
        frame.getContentPane().add(lblFile);

        JButton btnBorrow_File = new JButton("...");
        btnBorrow_File.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
                JFileChooser filedilg=new JFileChooser();
                filedilg.showOpenDialog(filedilg);
                filename = filedilg.getSelectedFile().getAbsolutePath();// path of file
                txtNamefile.setText(filename);
                File file1 = new File(filename); 
                fname = file1.getName();// filename  
                System.out.println("THE FILE NAME IS "+fname);
            }
        });
        btnBorrow_File.setBounds(444, 404, 79, 23);
        frame.getContentPane().add(btnBorrow_File);
        
        txtNamefile = new JTextField();
        txtNamefile.setBounds(88, 405, 342, 20);
        frame.getContentPane().add(txtNamefile);
        txtNamefile.setColumns(10);

        JButton btnSend_1 = new JButton("Send");
        btnSend_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		txtareaShow_message.append("[Me]: ");
                txtareaShow_message.append(fname);
        	}
        });
        btnSend_1.setBounds(530, 404, 79, 23);
        frame.getContentPane().add(btnSend_1);

        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(362, 33, 54, 25);
        frame.getContentPane().add(btnExit);
        
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(22, 13, 68, 14);
        frame.getContentPane().add(lblUsername);
        
        JLabel lblShowUname = new JLabel("");
        lblShowUname.setBounds(88, 11, 200, 16);
        frame.getContentPane().add(lblShowUname);
        
        txtInput_friends = new JTextField();
        txtInput_friends.setBounds(425, 10, 172, 20);
        frame.getContentPane().add(txtInput_friends);
        txtInput_friends.setColumns(10);
        
        JButton btnCreateGroup = new JButton("Create group");
        btnCreateGroup.setBounds(487, 34, 111, 23);
        frame.getContentPane().add(btnCreateGroup);
        
        JLabel lblAnnouncement = new JLabel("");
        lblAnnouncement.setBounds(10, 433, 420, 25);
        frame.getContentPane().add(lblAnnouncement);
        
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
