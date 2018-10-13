import java.util.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

public class MessageForm {
    private JFrame frame;
    private JTextField txtName;
    private JTextField txtInput_message;
    private JTextField txtNamefile;
    private JTextArea txtareaShow_message;
    private FrontendHandler feh;
    String typeRoom[] = {"People", "Room", "Create room"};

    private JComboBox<String> type = new JComboBox<>(typeRoom);
    private DefaultListModel<String> person_List = new DefaultListModel<>();

    JLabel lblUname = new JLabel();
    JLabel lblIp = new JLabel();
    JLabel lblPort = new JLabel();

    JLabel lblNotify = new JLabel("No unread message");

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
        if (person_List.contains(name))
            return;
        person_List.addElement(name);
    }

    public void Error(String error) {
        JOptionPane.showMessageDialog(null, "Error", error, JOptionPane.ERROR_MESSAGE);
    }
    /**
     * Initialize the contents of the frame.
     */

    public void setCurrentUserInfo(String name, String IP, String port) {
        lblUname.setText(name);
        lblIp.setText("IP: " + IP);
        lblPort.setText("Port: " + port);
    }

    public void setnewNotify() {
        lblNotify.setText("No unread message");
    }

    public void setnewNotify(String name) {
        lblNotify.setText("You have new message from " + name);
    }

    public void setMessageBoard(List<String> mess_list) {
        txtareaShow_message.setText("");
        for (String mess : mess_list) {
            txtareaShow_message.append(mess + "\n");
        }
    }

    public void close() {
        WindowEvent winClosingEvent = new WindowEvent(this.frame, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 635, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblName = new JLabel("Find");
        lblName.setBounds(12, 52, 68, 14);
        frame.getContentPane().add(lblName);

        txtName = new JTextField();
        txtName.setBounds(78, 52, 200, 30);
        frame.getContentPane().add(txtName);
        txtName.setColumns(10);
        txtName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText();
                feh.getIP(name, (String) type.getSelectedItem());
                txtName.setText("");
            }
        });

        JButton btnGo = new JButton("Go");
        btnGo.setBounds(494, 51, 100, 30);
        frame.getContentPane().add(btnGo);
        btnGo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText();
                feh.getIP(name, (String) type.getSelectedItem());
                txtName.setText("");
            }
        });

        txtareaShow_message = new JTextArea();
        txtareaShow_message.setEditable(false);
        txtareaShow_message.setBounds(10, 125, 458, 255);
        frame.getContentPane().add(txtareaShow_message);

        JLabel lblMessage = new JLabel("Message:");
        lblMessage.setBounds(10, 392, 46, 14);
        frame.getContentPane().add(lblMessage);

        txtInput_message = new JTextField();
        txtInput_message.setBounds(74, 390, 380, 20);
        frame.getContentPane().add(txtInput_message);
        txtInput_message.setColumns(10);
        txtInput_message.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = txtInput_message.getText();
                txtareaShow_message.append("[Me]: ");
                txtareaShow_message.append(message);
                txtareaShow_message.append("\n");
                txtInput_message.setText(null);
                feh.sendMessage(message);
            }
        });

        JButton btnSend = new JButton("Send Message");
        btnSend.setBounds(493, 388, 101, 23);
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
        lblFile.setBounds(10, 431, 46, 14);
        frame.getContentPane().add(lblFile);

        txtNamefile = new JTextField();
        txtNamefile.setBounds(74, 429, 342, 20);
        frame.getContentPane().add(txtNamefile);
        txtNamefile.setColumns(10);

        JButton btnBorrow_File = new JButton("...");
        btnBorrow_File.setBounds(431, 427, 79, 23);
        frame.getContentPane().add(btnBorrow_File);
        btnBorrow_File.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser filedilg = new JFileChooser();
                filedilg.showOpenDialog(filedilg);
                String filename = filedilg.getSelectedFile().getAbsolutePath(); // path of file
                txtNamefile.setText(filename);
                // File file1 = new File(filename);
                // fname = file1.getName(); // filename
                // System.out.println("THE FILE NAME IS " + fname);
            }
        });

        JButton btnSend_1 = new JButton("Send");
        btnSend_1.setBounds(524, 427, 79, 23);
        frame.getContentPane().add(btnSend_1);
        btnSend_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtareaShow_message.append("[Me]: FILE:");
                txtareaShow_message.append(txtNamefile.getText());
                txtareaShow_message.append("\n");
                txtInput_message.setText(null);
                feh.sendFile(txtNamefile.getText());

                txtNamefile.setText("");
            }
        });

        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(493, 7, 100, 25);
        frame.getContentPane().add(btnExit);
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame = new JFrame("Exit");
                if (JOptionPane.showConfirmDialog(
                        frame, "Confirm if you want to exit", "ABCD", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_NO_OPTION) {
                    feh.call_shutdown();
                    // System.exit(0);
                }
            }
        });

        JList<String> list = new JList<>(person_List);
        list.setBounds(480, 124, 118, 256);
        frame.getContentPane().add(list);
        list.addMouseListener(new MouseAdapter() {
            // on double click people on right hand box
            public void mouseClicked(MouseEvent evt) {
                JList<?> list = (JList<?>) evt.getSource();
                if (evt.getClickCount() == 2) {
                    // int index = list.locationToIndex(evt.getPoint());
                    // System.out.println(list.getSelectedValue());
                    feh.changeCurrentChatUser((String) list.getSelectedValue());
                }
            }
        });

        lblNotify.setVerticalAlignment(SwingConstants.TOP);
        lblNotify.setHorizontalAlignment(SwingConstants.LEFT);
        lblNotify.setBounds(12, 100, 266, 14);
        frame.getContentPane().add(lblNotify);

        lblUname.setBounds(10, 12, 70, 15);
        frame.getContentPane().add(lblUname);

        lblIp.setBounds(79, 12, 127, 14);
        frame.getContentPane().add(lblIp);

        lblPort.setBounds(218, 12, 100, 15);
        frame.getContentPane().add(lblPort);

        type.setBounds(331, 54, 118, 28);
        frame.getContentPane().add(type);

        JButton btnSetting = new JButton("Setting");
        btnSetting.setBounds(331, 7, 117, 25);
        frame.getContentPane().add(btnSetting);
    }
}
