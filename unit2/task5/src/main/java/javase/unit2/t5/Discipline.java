package javase.unit2.t5;

/**
 * Created by andrey on 24.02.2017.
 */
public enum Discipline {
    MATH(false),
    ENGLISH(true);

    private boolean isMarkFloat;

    Discipline(boolean isMarkFloat) {
        this.isMarkFloat = isMarkFloat;
    }

    public boolean isMarkFloat() {
        return isMarkFloat;
    }

    public Mark markOf(int markValue) {
        if (isMarkFloat) {
            return new FloatMark(markValue);
        } else {
            return new IntegerMark(markValue);
        }
    }

    public Mark markOf(double markValue) {
        return markOf((float) markValue);
    }

    public Mark markOf(float markValue) {
        if (isMarkFloat) {
            return new FloatMark(markValue);
        } else {
            return new IntegerMark(markValue);
        }
    }
}
