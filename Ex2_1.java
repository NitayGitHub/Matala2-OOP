import java.io.*;
import java.util.Random;

public class Ex2_1 {

    public static String[] createTextFiles(int n, int seed, int bound) {
        File myDir = new File("C:\\temp\\Ex2");
        boolean wasDirMade = myDir.mkdirs();
        int linesCount;
        Random rand = new Random(seed);

        for (int i = 1; i <= n; i++) {
            File newFile = new File("C:\\temp\\Ex2\\newFile" + i + ".txt");
            try {
                boolean wasFileMade = newFile.createNewFile();
                FileWriter fw = new FileWriter("C:\\temp\\Ex2\\newFile" + i + ".txt");
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

    public static int getNumOfLines(String[] fileNames) {
        int count = 0;
        for (String fileName : fileNames) {
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

        }

        return count;
    }
}
