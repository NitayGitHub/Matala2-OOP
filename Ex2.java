public class Ex2 {
    public static void main(String[] args) {
        String s[] = Ex2_1.createTextFiles(4, 1, 9);
        int x = Ex2_1.getNumOfLinesThreads(s);
        System.out.println(x);

    }
}
