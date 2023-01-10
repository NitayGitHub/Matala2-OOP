import java.io.*;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Ex2_1 {
    private static final AtomicInteger threadsCount = new AtomicInteger(); // a variable that handle the total number of lines that were been count. 
    
    // The function creates n text files on disk and returns an array of the file names. 
    // @param
    // n- a natural number representing the number of text files.
    // seed- the initial value of the internal state of the pseudorandom number generator which is maintained by method next.
    // bound - the upper bound (exclusive). Must be positive.
    // @output
    // An array of the file names.
    
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

    // The function counts the number of lines in a given text file and return it. 
    // @param
    // filename- a text file name.
    // @output
    // count- the number of lines.
    
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

    // The function counts the total number of lines in a given text files array and return it. 
    // @param
    // filename- a text file array.
    // @output
    // count- the total number of lines.
    
    public static int getNumOfLines(String[] fileNames) {
        int count = 0;
        for (String fileName : fileNames) {
            count += countFileLines(fileName);
        }
        return count;
    }
    
    // An inside class that represent a type of thread which support the ability of lines counting in a text file.
    
    public static class NOLThread extends Thread {
        private final String fileName; // the text file name.
        private final CountDownLatch latch; // a lock that prevents from the main function to keep running until latch is equal to zero. 
        
        // This function creates a new NOLThread.
        // @param
        // filename- the name of the txt file.
        // latch- the amount of threads to be completed.
        // @output
        // a new NOLThread.
        
        public NOLThread(String fileName, CountDownLatch latch) {
            this.fileName = fileName;
            this.latch = latch;
        }
        
        // This function starts the thread. after the thread finished, latch decrease by 1.
        @Override
        public void run() {
            threadsCount.addAndGet(countFileLines(fileName));
            latch.countDown();
        }
    }
    
    // This function counts the total number of lines in a given text files array by using NOLThreads, and return it. 
    // @param
    // filename- a text file array.
    // @output
    // res- the total number of lines.
    
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
    
    
    // An inside class that represent a type of threadpool which support the ability of lines counting in a text file.
    
    public static class NOLThreadPool implements Callable<String> {
        private final String fileName;
        
        // This function creates a new NOLThreadPool.
        // @param
        // filename- the name of the txt file.
        // @output
        // a new NOLThreadPool.
        
        public NOLThreadPool(String fileName) {
            this.fileName = fileName;
        }
        
        // This function computes a result, or throws an exception if unable to do so.
        // @output
        // a message that says a thread has been called.
        @Override
        public String call() {
            threadsCount.addAndGet(countFileLines(fileName));
            return "called";
        }
    }
    
    // This function counts the total number of lines in a given text files array by using NOLThreadsPool, and return it. 
    // @param
    // filename- a text file array.
    // @output
    // res- the total number of lines.
    
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
