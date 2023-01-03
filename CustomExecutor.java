import java.util.Objects;
import java.util.concurrent.*;

public class CustomExecutor {
    private final int cores = Runtime.getRuntime().availableProcessors();
    private final PriorityBlockingQueue<Task<Object>> queue = new PriorityBlockingQueue<>();
    private final ExecutorService tasksPoll = Executors.newFixedThreadPool(cores - 1);

    public Object submit(Task<Object> task) throws Exception {
        queue.add(task);
        return tasksPoll.submit(Objects.requireNonNull(queue.poll()));

    }


}
