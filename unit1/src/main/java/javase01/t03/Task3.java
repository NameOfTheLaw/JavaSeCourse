package javase01.t03;

/**
 * Created by andrey on 06.02.2017.
 */
public class Task3 {

    public static double a = -2.3;
    public static double b = 6;
    public static double h = 0.5;

    public static void main(String[] args) {
        if (a > b) {
            System.out.println("must be: a > b");
        } else if (h <= 0) {
            System.out.println("must be: h > 0");
        } else {
            double currentPoint = a;
            while (currentPoint < b) {
                System.out.println(currentPoint + " | " + getF(currentPoint));
                currentPoint += h;
            }
            System.out.println(b + " | " + getF(b));
        }
    }

    private static double getF(double x) {
        return Math.tan(2 * x) - 3;
    }
}
