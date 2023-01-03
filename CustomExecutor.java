import java.util.concurrent.PriorityBlockingQueue;

public class CustomExecutor{
    private final int cores = Runtime.getRuntime().availableProcessors();
    private final PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();

    public static Object submit(Task<Object> task) throws Exception {
        return task.call();

    }


}
