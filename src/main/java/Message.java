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
            // read file to calculate checksum (MD5 maybe?)
        }
    }

    public String toString() {
        return from + "->" + to + ":" + msg;
    }

    public boolean sendfile() {
        return false;
    }
}
