import java.util.*;

public class Observer {
    final List<Subscriber> subscriber = new ArrayList<Subscriber>();
    public Observer() {}

    public void add(Subscriber t) {
        subscriber.add(t);
    }

    public void notify(InternalRequest r) {
        for (Subscriber s : subscriber) {
            if (s.se() == r.to()) {
                s.receive_request(r);
                break;
            }
        }
    }

    public void send_answer(ServiceEnum to, InternalRequest r) {
        for (Subscriber s : subscriber) {
            if (s.se() == to) {
                s.receive_answer(r);
                break;
            }
        }
    }

    public void shutdown() {
        for (Subscriber s : subscriber) {
            // create a shutdown request
            s.receive_request(new InternalRequest(null, null, "exit", null));
            // will remove this later
            s.shutdown();
        }
    }
}
