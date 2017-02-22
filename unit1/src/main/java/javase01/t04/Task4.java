package javase01.t04;

import java.util.Random;

/**
 * Created by andrey on 06.02.2017.
 */
public class Task4 {

    private static int n = 10;

    public static void main(String[] args) {
        double max = Double.NEGATIVE_INFINITY;
        if (n <= 0) {
            System.out.println("n is too small");
        } else {
            double[] mas = generateValues(n*2);
            for (int i = 0; i < n; i++) {
                max = Math.max(max, mas[i] + mas[n*2 - 1 - i]);
            }
        }
        System.out.println("max = " + max);
    }

    private static double[] generateValues(int size) {
        double[] mas = new double[size];
        Random randomiser = new Random();
        for (int i = 0; i < mas.length; i++) {
            mas[i] = randomiser.nextDouble();
        }
        return mas;
    }

}
