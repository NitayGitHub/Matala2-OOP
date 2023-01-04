import java.util.Comparator;
import java.util.concurrent.*;

public class CustomExecutor extends ThreadPoolExecutor {
    private static int cores = Runtime.getRuntime().availableProcessors();

    public CustomExecutor() {
        super(cores / 2, cores - 1, 300L, TimeUnit.MILLISECONDS,
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
        if (task1.getTaskType().getPriorityValue() < task2.getTaskType().getPriorityValue()) {
            return -1;
        }
        if (task1.getTaskType().getPriorityValue() > task2.getTaskType().getPriorityValue()) {
            return 1;
        }
        return 0;
    }
}
