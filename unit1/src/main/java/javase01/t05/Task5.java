package javase01.t05;

/**
 * Created by andrey on 06.02.2017.
 */
public class Task5 {

    private static int size = 6;

    public static void main(String[] args) {
        if (size < 1) {
            System.out.println("size is to small");
        } else {
            printMatrix(generateMatrix(size));
        }
    }

    private static int[][] generateMatrix(int size) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    matrix[i][j] = 1;
                } else if (i == size - 1 - j) {
                    matrix[i][j] = 1;
                }
            }
        }
        return matrix;
    }

    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            int lineLength = matrix[i].length;
            for (int j = 0; j < lineLength - 1; j++) {
                System.out.print(matrix[i][j] + " | ");
            }
            System.out.println(matrix[i][lineLength - 1]);
        }
    }
}
