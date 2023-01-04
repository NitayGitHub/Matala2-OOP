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

![צילום מסך 2023-01-02 211237](https://user-images.githubusercontent.com/118196923/210272717-842dc98b-5521-49d6-994a-c7960c549dc9.png)

## Class Diagram
