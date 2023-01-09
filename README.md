# Matala2-OOP | Naor Tzadok & Nitay Levy
In this assignment, we create several text files and calculate the total number of lines in these files. To do so, we'll use three methods: one without threads, one with threads, and one using ThreadPool. The class Ex2_1 contains the three mentioned functions and Ex2 implements them.

## Ex2_1 Class Functions
### createTextFiles(int n, int seed, int bound)<br>
This function creates text files with a random number of lines, obtained using the class "Random" with a seed and bound parameters.<br><br>
**Input:**<br>
n - a natural number representing the number of text files.<br>
bound - max number of lines in each file.<br>
seed  - The number of lines in each file is a random number, obtained using the class "Random" with a seed and bound parameters.<br><br>
**Output:** an array of all file names.

### public static int getNumOfLines(String[] fileNames)<br>
This function returns the total number of lines in all created files without using threads.<br><br>
**Input:** an array that contains the names of the files.<br><br>
**Output:** the total number of lines in all the files.

### public static int getNumOfLinesThreads(String[] fileNames)<br>
This function returns the total number of lines in all created files using threads.<br><br>
**Input:** an array that contains the names of the files.<br><br>
**Output:** the total number of lines in all the files.

### public static int getNumOfLinesThreadPool(String[] fileNames)<br>
This function returns the total number of lines in all created files using a threadpool.<br><br>
**Input:** an array that contains the names of the files.<br><br>
**Output:** the total number of lines in all the files.

## Variations in the Functions' Time Complexity
To check the time complexity, we created 500 files with a maximum of 100 lines in each. Then, we measured in miliseconds the time it took for each function to count the total number of lines. See image:

![צילום מסך 2023-01-02 211304](https://user-images.githubusercontent.com/118196923/210635587-e342fee5-b83d-46e6-a7de-c7d2fb0a8e00.png)

The results show that using threadpool was the fastest, followed by using threads, and function 2 without threads was the slowest.

![צילום מסך 2023-01-02 211237](https://user-images.githubusercontent.com/118196923/210272717-842dc98b-5521-49d6-994a-c7960c549dc9.png)

**Reasons**<br>
The method without threads was the slowest because there is a single process that counts lines in each file one by one, whereas in the other functions this task is divided into multiple parallel processes.<br><br>
Why using a thread pool was faster than manually creating threads?<br>
Well, the thread pool reuses its threads to perform the work you'd like to get done. Creating a single new thread takes about 1MB of memory, which you are avoiding by reusing threads via a thread pool. Also, creating and destroying a thread has setup and teardown costs (it takes CPU time). After a thread pool thread finishes executing your task, it returns to the thread pool (it is not destroyed) so that it can pick up the next available task. A thread pool creates a new thread only if it does not have a thread to execute a newly queued task; it can help indirectly to keep the number of threads to be as close as the number of CPUs.

## Ex2_1 Class Diagram
![WhatsApp_2023-01-04_00 18 55_50](https://user-images.githubusercontent.com/118196923/210634669-1c48fcdf-744a-44cc-9896-5fd070598c63.jpg)

## Part B Classes Description
At the second part of the assignment, we create a new type that represents an asynchronous task with priority and a new ThreadPool type that supports owning tasks
priority.

## Task class
The Task class - represents an operation that can be run asynchronously and can return a value of some type (that is, it will be defined as a generic return type). It is not necessary for the operation to succeed and in case of failure, an exception will be thrown.<br>
### The fields are:<br>
**Callable(Type)**  task - the task which will be performed.<br>
**TaskType**  taskType - the type of the task i.e: COMPUTATIONAL, I/O, OTHER.


## Class functions<br>
- ### Task(Callable<Type> task, TaskType taskType)<br>
    This function used as a constructor for task. <br><br>

    **Input:**<br>
    task - an operation.<br>
    taskType - the type of the task.<br><br>

    **Output:**<br>
    A new generic task.<br>

- ### Task createTask(Callable<Type> task, TaskType taskType)<br>
    This function creates a task instance.<br><br>

    **Input:**<br>
    task - Java's built-in type of task that can be executed asynchronously with a return value.<br>
    taskType - the type of the task.<br>

    **(Note: if the TaskType wasn't given the TaskType value will be set as a default)**<br><br>
    **Output:**<br>
    A new Task.<br>


- ###  call() throws Exception<br>
    This function computes a result, or throws an exception if unable to do so.<br>

- ### TaskType getTaskType()<br>
    This function return the TaskType of the task.<br><br>

    **Output:**<br>
The TaskType of the task.<br>

## CustomExecuter class
A custom thread pool class that defines a method for submitting a generic task to a priority queue, and a method for submitting a generic task created by a
Callable(V) and a Type, passed as arguments. <br>

### The fields are:<br>
**Int**  cores - the amount of available cores for JVM.<br>
**Int[]** threadTypeCount - an array which counts the number of threads from each priority that were submitted.<br>

## Class functions<br>
- ### CustomExecutor()<br>
  This function creates a customexecutor instance by initial the following values:<br><br>
  
  **Input:**<br>
  *corePoolSize* – the number of threads to keep in the pool.<br>
  *maximumPoolSize* – the maximum number of threads to allow in the pool.<br>
  *keepAliveTime* – when the number of threads is greater than the core,<br> 
                  this is the maximum time that excess idle threads will wait for new tasks before terminating.<br>
  *unit* – the time unit for the keepAliveTime argument.<br>
  *workQueue* – the queue to use for holding tasks before they are executed.<br>
              This queue will hold only the Runnable tasks submitted by the execute method.<br>
  *threadFactory* – the factory to use when the executor creates a new thread.<br><br>

  **Output:**<br>
  A new customexecutor.<br><br>
