import java.util.concurrent.*;

// This class represents an operation that can be run asynchronously and can return a value of some type (that is, it will be defined as a generic return type). 
// It is not necessary for the operation to succeed and in case of failure, an exception will be thrown.

public class Task<Type> implements Callable<Type> {
    private final Callable<Type> task; // the task will be performed.
    private final TaskType taskType; // the type of the task i.e: COMPUTATIONAL, I/O, OTHER.

    // This function used as a constructor for task.
    // @param
    // task- an operation to be performed.
    // tasktype - the type of the task.
    // @output
    // A new task with a tasktype.

    public Task(Callable<Type> task, TaskType taskType) {
        this.taskType = taskType;
        this.task = task;
    }

    // This function used as a constructor for task.
    // @param
    // task- an operation to be performed.
    // @output
    // A new task with a default tasktype which is -OTHER.

    public Task(Callable<Type> task) {
        this.taskType = TaskType.OTHER;
        this.task = task;
    }

    // This function computes a result, or throws an exception if unable to do so.
    @Override
    public Type call() throws Exception {
        return task.call();
    }

    // This function creates a task instance.
    // @param 
    // task - Java's built-in type of task that can be executed asynchronously with a return value.
    // taskType - the type of the task.
    // @output
    // A new Task instance.

    public static <Type> Task<Type> createTask(Callable<Type> task, TaskType taskType) {
        return new Task<Type>(task, taskType);
    }

    // This function gets the task type and return it.
    // @output
    // task type.

    public TaskType getTaskType() {
        return taskType;
    }

    // This function gets the operation and return it.
    // @output
    // task.

    public Callable<Type> getCallable() {
        return task;
    }

    //An enum that represent different Tasks types and define their priority values.

    public enum TaskType {
        COMPUTATIONAL(1) {
            @Override
            public String toString() {
                return "Computational Task";
            }
        },
        IO(2) {
            @Override
            public String toString() {
                return "IO-Bound Task";
            }
        },
        OTHER(3) {
            @Override
            public String toString() {
                return "Unknown Task";
            }
        };

        private int typePriority; // an integer which represent the priority of a task type.

        // This function sets the priority of a task type. first it checks if the priority is valid.
        // if isn't, it throws an exception.
        // @param
        // priority- an integer value.
        // @output
        // Exception- IllegalArgumentException.

        private TaskType(int priority) {
            if (validatePriority(priority)) typePriority = priority;
            else
                throw new IllegalArgumentException("Priority is not an integer");
        }

        // This function sets the priority of a task type. first it checks if the priority is valid.
        // if isn't, it throws an exception.
        // @param
        // priority- an integer value.
        // @output
        // Exception- IllegalArgumentException.

        public void setPriority(int priority) {
            if (validatePriority(priority)) this.typePriority = priority;
            else
                throw new IllegalArgumentException("Priority is not an integer");
        }

        // This function gets the priority of a task type and return it.
        // @output
        // type priority.

        public int getPriorityValue() {
            return typePriority;
        }

        // This function gets the type of a task and return it.
        // @output
        // task type.
        public TaskType getType() {
            return this;
        }

        /**
         * priority is represented by an integer value, ranging from 1 to 10 * @param priority * @return whether the priority is valid or not
         */
        private static boolean validatePriority(int priority) {
            return priority >= 1 && priority <= 10;
        }
    }

}
