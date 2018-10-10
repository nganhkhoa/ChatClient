import java.util.*;

public class InternalRequest {
    final ServiceEnum from;
    final ServiceEnum to;
    final String task;
    final List<String> param;

    private List<String> result;
    private boolean success;

    public InternalRequest(ServiceEnum from, ServiceEnum to, String task, List<String> param) {
        this.from = from;
        this.to = to;
        this.task = task;
        this.param = param;
    }

    public ServiceEnum from() {
        return from;
    }

    public ServiceEnum to() {
        return to;
    }

    public String task() {
        return task;
    }

    public List<String> param() {
        return param;
    }

    public String toString() {
        if (result == null)
            return from + "." + task + "(" + param + ")";
        else
            return to + "." + task + "(" + param + "):" + (success ? "+" : "-") + result;
    }

    public void answer(boolean success) {
        this.success = success;
        this.result = Arrays.asList();
    }

    public void answer(boolean success, List<String> result) {
        this.success = success;
        this.result = result;
    }

    public boolean success() {
        return success;
    }
    public List<String> result() {
        return result;
    }
}
