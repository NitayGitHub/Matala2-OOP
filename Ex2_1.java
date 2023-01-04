import java.io.*;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Ex2_1 {
    private static final AtomicInteger threadsCount = new AtomicInteger();

    public static String[] createTextFiles(int n, int seed, int bound) {
        File myDir = new File("C:\\temp\\Ex2");
        boolean wasDirMade = myDir.mkdirs();
        int linesCount;
        Random rand = new Random(seed);

        for (int i = 1; i <= n; i++) {
            File newFile = new File("C:\\temp\\Ex2\\File_" + i + ".txt");
            try {
                boolean wasFileMade = newFile.createNewFile();
                FileWriter fw = new FileWriter("C:\\temp\\Ex2\\File_" + i + ".txt");
                PrintWriter outs = new PrintWriter(fw);
                linesCount = rand.nextInt(bound);
                for (int j = 0; j < linesCount; j++) {
                    outs.println((j + 1) + ": Hello World.");
                }
                outs.close();
                fw.close();
            } catch (IOException ep) {
                System.err.println("File creation failed");
                ep.printStackTrace();
            }
        }

        return myDir.list();
    }

    private static int countFileLines(String fileName) {
        int count = 0;
        try {
            FileReader fr = new FileReader("C:\\temp\\Ex2\\" + fileName);
            BufferedReader br = new BufferedReader(fr);
            String str = br.readLine();
            while (str != null) {
                count++;
                str = br.readLine();
            }
            br.close();
            fr.close();
        } catch (IOException ex) {
            System.err.println("File read failed");
            ex.printStackTrace();
        }
        return count;
    }


    public static int getNumOfLines(String[] fileNames) {
        int count = 0;
        for (String fileName : fileNames) {
            count += countFileLines(fileName);
        }
        return count;
    }

    public static class NOLThread extends Thread {
        private final String fileName;
        private final CountDownLatch latch;

        public NOLThread(String fileName, CountDownLatch latch) {
            this.fileName = fileName;
            this.latch = latch;
        }

        @Override
        public void run() {
            threadsCount.addAndGet(countFileLines(fileName));
            latch.countDown();
        }
    }

    public static int getNumOfLinesThreads(String[] fileNames) {
        int res;
        CountDownLatch latch = new CountDownLatch(fileNames.length);
        for (String fileName : fileNames) {
            new NOLThread(fileName, latch).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        res = threadsCount.get();
        threadsCount.set(0);
        return res;
    }

    public static class NOLThreadPool implements Callable<String> {
        private final String fileName;

        public NOLThreadPool(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public String call() {
            threadsCount.addAndGet(countFileLines(fileName));
            return "called";
        }
    }

    public static int getNumOfLinesThreadPool(String[] fileNames) {
        int res;
        ExecutorService pool = Executors.newFixedThreadPool(fileNames.length);
        for (String fileName : fileNames) {
            pool.submit(new NOLThreadPool(fileName));
        }

        pool.shutdown();
        while (!pool.isTerminated()) {
        }
        res = threadsCount.get();
        threadsCount.set(0);
        return res;
    }
}
