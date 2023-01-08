import java.util.concurrent.*;

import static java.lang.Thread.sleep;


public class Ex2 {
    public static void main(String[] args) throws Exception {
        ///////Part 1 - Test///////

        /*String[] s = Ex2_1.createTextFiles(500, 1, 100);
        long start1 = System.currentTimeMillis();
        int x = Ex2_1.getNumOfLines(s);
        long end1 = System.currentTimeMillis();
        System.out.println(x + " lines. Elapsed Time for function 2 in milliseconds: " + (end1 - start1));
        long start2 = System.currentTimeMillis();
        int y = Ex2_1.getNumOfLinesThreads(s);
        long end2 = System.currentTimeMillis();
        System.out.println(y + " lines. Elapsed Time for function 3 in milliseconds: " + (end2 - start2));
        long start3 = System.currentTimeMillis();
        int z = Ex2_1.getNumOfLinesThreadPool(s);
        long end3 = System.currentTimeMillis();
        System.out.println(z + " lines. Elapsed Time for function 4 in milliseconds: " + (end3 - start3));*/

        ///////Part 2 - Test///////

        CustomExecutor customExecutor = new CustomExecutor();
        Task<Integer> task = Task.createTask(() -> {
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        }, Task.TaskType.COMPUTATIONAL);
        Future<Integer> sumTask = customExecutor.submit(task);
        final int sum;
        try {
            sum = sumTask.get(1, TimeUnit.MILLISECONDS);
            System.out.println("Sum of 1 through 10 = " + sum);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        Callable<Double> callable1 = () -> {
            return 1000 * Math.pow(1.02, 5);
        };
        Callable<String> callable2 = () -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };
        Future<Double> priceTask = customExecutor.submit(callable1, Task.TaskType.COMPUTATIONAL);
        Future<String> reverseTask = customExecutor.submit(callable2, Task.TaskType.IO);
        final Double totalPrice;
        final String reversed;
        try {
            totalPrice = priceTask.get();
            reversed = reverseTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Reversed String = " + reversed);
        System.out.println("Total Price = " + totalPrice);
        System.out.println("Current maximum priority = " + customExecutor.getCurrentMax());
        customExecutor.gracefullyTerminate();

    }
}
