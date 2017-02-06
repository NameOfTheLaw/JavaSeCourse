package javase01.t02;

/**
 * Created by andrey on 06.02.2017.
 */
public class Task2 {

    private static double epsilon = (double) 1/12;

    public static void main(String[] args) {
        if (epsilon > 0) {
            int i = 1;
            double a;
            do {
                a = getValue(i);
                i++;
                System.out.println(a);
            } while (epsilon < a);
            System.out.println(i - 1);
        } else {
            System.out.println("epsilon is too small");
        }
    }

    private static double getValue(int i) {
        return 1/Math.pow(i + 1, 2);
    }
}
