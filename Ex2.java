
public class Ex2 {
    public static void main(String[] args) {
        String[] s = Ex2_1.createTextFiles(30, 1, 15);
        long start1 = System.currentTimeMillis();
        Ex2_1.getNumOfLines(s);
        long end1 = System.currentTimeMillis();
        System.out.println("Elapsed Time for function 2 in milliseconds: "+ (end1-start1));
        long start2 = System.currentTimeMillis();
        Ex2_1.getNumOfLinesThreads(s);
        long end2 = System.currentTimeMillis();
        System.out.println("Elapsed Time for function 3 in milliseconds: "+ (end2-start2));
        long start3 = System.currentTimeMillis();
        Ex2_1.getNumOfLinesThreadPool(s);
        long end3 = System.currentTimeMillis();
        System.out.println("Elapsed Time for function 4 in milliseconds: "+ (end3-start3));

    }
}
