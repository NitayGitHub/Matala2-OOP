import java.util.Comparator;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicIntegerArray;


// This class represent a type of threadpool that can submit tasks with Tasktype.
// CustomExecutor using a priorityqueue to organize the tasks to be performed, by give a high priority to task with some tasktype.

public class CustomExecutor extends ThreadPoolExecutor {
    private static final int cores = Runtime.getRuntime().availableProcessors(); // the number of cores in JVM
    private final AtomicIntegerArray threadTypeCount = new AtomicIntegerArray(4); // an array that counting the number of threads from each different tasktype that are in process 

    // This function creates new CustomExecutor.
    // @param 
    // corePoolSize – the number of threads to keep in the pool.
    // maximumPoolSize – the maximum number of threads to allow in the pool.
    // keepAliveTime – when the number of threads is greater than the core,
    //                 this is the maximum time that excess idle threads will wait for new tasks before terminating.
    // unit – the time unit for the keepAliveTime argument.
    // workQueue – the queue to use for holding tasks before they are executed.
    //             This queue will hold only the Runnable tasks submitted by the execute method.
    // threadFactory – the factory to use when the executor creates a new thread.
    // 
    // @output
    // A new customexecutor   
  
    public CustomExecutor() {
        super(cores / 2, cores - 1, 300L, TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<Runnable>(10, new PriorityQueueComparator()), new PriorityThreadFactory());
    }
    
    // This function uses to give a new task and tasktype to the Customexecutor and also increase/decrease the threadtypecount array.
    // @param
    // task- new operation to be performed.
    // tasktype- the type of the task.
    // @output
    // a Future representing pending completion of the task
    
    public <T> Future<T> submit(Callable<T> task, Task.TaskType taskType){
        int typeNum = taskType.getPriorityValue();
        threadTypeCount.incrementAndGet(typeNum);
        Future<T> res = super.submit(new Task<>(task, taskType));
        super.submit(new Task<>(() -> {
            while (!res.isDone()) {
            }
            threadTypeCount.decrementAndGet(typeNum);
            return 1;
        }, taskType));
        return res;
    }
    
    // This function uses to give a new task to the Customexecutor and also increase/decrease the threadtypecount array.
    // @param
    // task- new operation to be performed.
    // @output
    // a Future representing pending completion of the task
    
    public <T> Future<T> submit(Task<T> task){
        return this.submit(task.getCallable(), task.getTaskType());
    }
    
    // This function uses to give an operation to the Customexecutor and also increase/decrease the threadtypecount array.
    // @param
    // task- new operation to be performed.
    // @output
    // a Future representing pending completion of the task
   
    @Override
    public <T> Future<T> submit(Callable<T> callable){
        return this.submit(callable, Task.TaskType.IO);
    }
    
    // This function return the maximum priority of Task instances in the queue at any given time.
    // @output
    // the maximum priority of Task instances in the queue.
    
    public int getCurrentMax() {
        if (threadTypeCount.get(1) != 0) {
            return 1;
        } else if (threadTypeCount.get(2) != 0) {
            return 2;
        } else if (threadTypeCount.get(3) != 0) {
            return 3;
        }
        return -1;
    }
    
    // This function terminate the customexecutor. 
    // firstlly it stops the ability to insert new tasks to the queue,
    // and finish all the tasks that already in the queue.
    // @void
    
    public void gracefullyTerminate() {
        this.shutdown();
        while (!this.isTerminated()) {
        }
    }
}

// An inside class that represent a type of thredfactory which support priority givness to tasks.

class PriorityThreadFactory implements ThreadFactory {
    
    // This function creates a new thread and sets the thread to be daemon.
    // @param 
    // r – a runnable to be executed by new thread instance
    // @output
    // t - constructed thread, or null if the request to create a thread is rejected
    
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
}


// An inside class that represent a comparator for the customexecutor in order to comapre tasks.

class PriorityQueueComparator<Type> implements Comparator<Task<Type>> {
    
    // This function compare two tasks by looking on their tasktype value. 
    // @param
    // task1, task2 - new tasks to be performed.
    // @output
    // 1 if task1 priority is higher then task2 priority, or -1 if task1 priority is lower then task2 priority.
    
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
