import java.util.concurrent.*;

public class CustomExecutor extends ThreadPoolExecutor {
    public CustomExecutor(){
        super(2, (Runtime.getRuntime().availableProcessors())-1, 0L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>(), new PriorityThreadFactory());

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
