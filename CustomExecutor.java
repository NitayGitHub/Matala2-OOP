import java.util.Comparator;
import java.util.concurrent.*;

public class CustomExecutor extends ThreadPoolExecutor {
    public CustomExecutor() {
        super(2, (Runtime.getRuntime().availableProcessors()) - 1, 0L, TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<>(10, new PriorityQueueComparator()), new PriorityThreadFactory());
    }

    public <T> Future<T> submit(Callable<T> task, Task.TaskType taskType) throws Exception {
        return this.submit(new Task<>(task, taskType));
    }
}

class PriorityThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
}

class PriorityQueueComparator<Type> implements Comparator<Task<Type>> {
    @Override
    public int compare(Task<Type> task1, Task<Type> task2) {
        return Integer.compare(task2.getTaskType().getPriorityValue(), task1.getTaskType().getPriorityValue());
    }
}
