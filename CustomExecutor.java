import java.util.Comparator;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class CustomExecutor extends ThreadPoolExecutor {
    private static final int cores = Runtime.getRuntime().availableProcessors();
    private static final AtomicIntegerArray threadTypeCount = new AtomicIntegerArray(4);

    public CustomExecutor() {
        super(cores / 2, cores - 1, 300L, TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<>(10, new PriorityQueueComparator()), new PriorityThreadFactory());
    }

    public <T> Future<T> submit(Callable<T> task, Task.TaskType taskType) throws Exception {
        int typeNum = taskType.getPriorityValue();
        threadTypeCount.incrementAndGet(typeNum);
        Future<T> res = this.submit(new Task<>(task, taskType));
        this.submit(new Task<>(() -> {
            while (!res.isDone()) {}
            threadTypeCount.decrementAndGet(typeNum);
            return 1;
        }, taskType));
        return res;
    }


    public static int getCurrentMax() {
        if (threadTypeCount.get(0) != 0) {
            return 0;
        } else if (threadTypeCount.get(1) != 0) {
            return 1;
        } else if (threadTypeCount.get(2) != 0) {
            return 2;
        }
        return 2;
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
