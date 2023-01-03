
public class Ex2 {
    public static void main(String[] args) {
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

        ///////Part 2///////

        Task<Integer> task = new Task<Integer>(() -> {
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        }, Task.TaskType.COMPUTATIONAL);


    }
}
