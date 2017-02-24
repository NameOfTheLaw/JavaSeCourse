package javase.unit2.t5;

/**
 * Created by andrey on 24.02.2017.
 */
public enum Discipline {
    MATH(false),
    ENGLISH(true),
    HISTORY(false);

    private boolean isMarkFloat;

    Discipline(boolean isMarkFloat) {
        this.isMarkFloat = isMarkFloat;
    }

    /**
     * Returns a formatted mark value of the current Discipline.
     *
     * @param markValue
     * @return
     */
    public Mark markOf(int markValue) {
        if (isMarkFloat) {
            return new FloatMark(markValue);
        } else {
            return new IntegerMark(markValue);
        }
    }

    /**
     * Returns a formatted mark value of the current Discipline.
     *
     * @param markValue
     * @return
     */
    public Mark markOf(double markValue) {
        return markOf((float) markValue);
    }

    /**
     * Returns a formatted mark value of the current Discipline.
     *
     * @param markValue
     * @return
     */
    public Mark markOf(float markValue) {
        if (isMarkFloat) {
            return new FloatMark(markValue);
        } else {
            return new IntegerMark(markValue);
        }
    }
}
