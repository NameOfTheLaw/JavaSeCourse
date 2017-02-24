package javase.unit2.t5;

/**
 * Created by andrey on 24.02.2017.
 */
public interface Mark {

    Number getValue();

}

class FloatMark implements Mark {

    private float value;

    public FloatMark(float value) {
        this.value = value;
    }

    public FloatMark(int value) {
        this.value = value;
    }

    @Override
    public Number getValue() {
        return new Float(value);
    }

}

class IntegerMark implements Mark {

    private int value;

    public IntegerMark(float value) {
        this.value = (int) value;
    }

    public IntegerMark(int value) {
        this.value = value;
    }

    @Override
    public Number getValue() {
        return new Integer(value);
    }

}
