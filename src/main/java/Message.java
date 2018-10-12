import java.util.*;
import java.lang.*;

public class Message {
    public String from;
    public String to;

    public List<String> IP;
    public List<Integer> port;
    int people;

    public String msg;
    String file; // the file name

    int len; // of file or msg

    String checksum; // of file

    public Message(String from, String to, List<String> IP, List<Integer> port, String tosend,
        boolean isFile) {
        this.from = from;
        this.to = to;
        this.IP = IP;
        this.port = port;

        if (!isFile) {
            this.msg = tosend;
            this.len = tosend.length();
        } else {
            this.file = tosend;
            // read file to set len
            // InputStream is = new FileInputStream(file);
            // read file to calculate checksum (MD5 maybe?)
        }
    }

    public String toString() {
        if (file == null)
            return from + "->" + to + ":" + msg;
        else
            return from + "->" + to + ":" + file + "[" + checksum + "]";
    }

    public boolean sendfile() {
        return false;
    }
}
