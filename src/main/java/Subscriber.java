import java.util.*;

public abstract class Subscriber extends Thread

{
    // protected class ReqAns {
    //     public ReqAns(Request r, Answer a) {
    //         req = r;
    //         ans = a;
    //     }
    //     public Request req;
    //     public Answer ans;
    // }

    // List<ReqAns> req_ans_queue = new ArrayList<ReqAns>();
    // List<InternalRequest> request_list = new ArrayList<InternalRequest>();
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

    public void receive_answer(InternalRequest r) {}

    public InternalRequest wait_request() {
        while (true) {
            // for (ReqAns ra : req_ans_queue) {
            //     if (ra.ans.isAnswered()) {
            //         req_ans_queue.remove(ra);
            //         return ra;
            //     }
            // }
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
