package javase.unit2.t4;

/**
 * Created by andrey on 23.02.2017.
 */
public class PencilWithEraser extends Pencil {

    private Eraser eraser;

    public PencilWithEraser(int width, String color, int cost, String name, Eraser eraser) {
        super(width, color, cost, name);
        this.eraser = eraser;
    }

    public Eraser getEraser() {
        return eraser;
    }

    public void setEraser(Eraser eraser) {
        this.eraser = eraser;
    }
}
