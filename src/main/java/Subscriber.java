import java.util.*;

public abstract class Subscriber extends Thread

{
    Queue<InternalRequest> process_queue = new LinkedList<InternalRequest>();
    protected boolean shutdown = false;
    ServiceEnum se;
    Observer obs;

    public ServiceEnum se() {
        return se;
    }

    public void receive_request(InternalRequest r) {
        process_queue.add(r);
    }

    /// special one for MessageHandler
    public void receive_message(Message m) {}

    public void receive_answer(InternalRequest r) {}

    public InternalRequest wait_request() {
        while (true) {
            if (process_queue.size() == 0) {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    // pass
                }
                continue;
            }
            return process_queue.remove();
        }
    }

    public void shutdown() {
        shutdown = true;
    }
}
