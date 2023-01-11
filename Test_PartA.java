
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Test_PartA {

    @Test
    void test(){
        String[] s = Ex2_1.createTextFiles(500, 1, 100);
        long start1 = System.currentTimeMillis();
        int x = Ex2_1.getNumOfLines(s);
        long end1 = System.currentTimeMillis();

        long start2 = System.currentTimeMillis();
        int y = Ex2_1.getNumOfLinesThreads(s);
        long end2 = System.currentTimeMillis();

        long start3 = System.currentTimeMillis();
        int z = Ex2_1.getNumOfLinesThreadPool(s);
        long end3 = System.currentTimeMillis();

        assertTrue((end1 - start1) < (end2 - start2) &&  (end2 - start2) < (end3 - start3));
    }
}
