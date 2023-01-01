public class Ex2 {
    public static void main(String[] args) {
        String s[] = Ex2_1.createTextFiles(4, 1, 10);
        int x = Ex2_1.getNumOfLines(s);
        System.out.println(x);
    }
}
