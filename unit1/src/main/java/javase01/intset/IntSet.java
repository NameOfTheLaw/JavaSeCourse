package javase01.intset;

/**
 * Class represents standard behavior for sets.
 *
 * Created by andrey on 02.02.2017.
 */
public class IntSet {

    private long[] data;

    public IntSet() {
        this(new long[1]);
    }

    private IntSet(long[] data) {
        this.data = data;
    }

    /**
     * Add a value to the set.
     *
     * @param value
     */
    public void add(int value) {
        if (value < 0) {
            return;
        }
        int valueOrder = value / 64;
        if (valueOrder > data.length - 1) {
            resizeArray(valueOrder);
        }
        data[valueOrder] |= 1L << (value % 64);
    }

    /**
     * Remove a value from the set.
     *
     * @param value
     */
    public void remove(int value) {
        if (value < 0) {
            return;
        }
        int valueOrder = value / 64;
        if (valueOrder > data.length - 1) {
            return;
        }
        data[valueOrder] &= ~(1L << (value % 64));
    }

    /**
     * Checks if the set contains the given value.
     *
     * @param value
     * @return
     */
    public boolean contains(int value) {
        if (value < 0) {
            return false;
        }
        int valueOrder = value / 64;
        if (valueOrder > data.length - 1) {
            return false;
        }
        return (data[valueOrder] & (1L << (value % 64))) != 0;
    }

    /**
     * Return union of the current set and the given.
     *
     * @param other
     * @return
     */
    public IntSet union(IntSet other) {
        long[] biggerArray, smallerArray;
        if (this.data.length > other.data.length) {
            biggerArray = this.data;
            smallerArray = other.data;
        } else {
            biggerArray = other.data;
            smallerArray = this.data;
        }
        long[] result = new long[biggerArray.length];
        for (int i = 0; i < smallerArray.length; i++) {
            result[i] = biggerArray[i] | smallerArray[i];
        }
        if (biggerArray.length != smallerArray.length) {
            System.arraycopy(biggerArray, smallerArray.length, result, smallerArray.length, biggerArray.length - smallerArray.length);
        }
        return new IntSet(result);
    }

    /**
     * Return intersection of the current set and the given.
     *
     * @param other
     * @return
     */
    public IntSet intersection(IntSet other) {
        long[] biggerArray, smallerArray;
        if (this.data.length > other.data.length) {
            biggerArray = this.data;
            smallerArray = other.data;
        } else {
            biggerArray = other.data;
            smallerArray = this.data;
        }
        long[] result = new long[biggerArray.length];
        for (int i = 0; i < smallerArray.length; i++) {
            result[i] = biggerArray[i] & smallerArray[i];
        }
        return new IntSet(result);
    }

    /**
     * Return difference between the current set and the given.
     *
     * Works like XOR operation.
     * @param other
     * @return
     */
    public IntSet difference(IntSet other) {
        long[] biggerArray, smallerArray;
        if (this.data.length > other.data.length) {
            biggerArray = this.data;
            smallerArray = other.data;
        } else {
            biggerArray = other.data;
            smallerArray = this.data;
        }
        long[] result = new long[biggerArray.length];
        for (int i = 0; i < smallerArray.length; i++) {
            result[i] = biggerArray[i] ^ smallerArray[i];
        }
        if (biggerArray.length != smallerArray.length) {
            System.arraycopy(biggerArray, smallerArray.length, result, smallerArray.length, biggerArray.length - smallerArray.length);
        }
        return new IntSet(result);
    }

    /**
     * Return result of minus operation between the current set and the given.
     *
     * Works like operation on sets: A\B.
     * @param other
     * @return
     */
    public IntSet minus(IntSet other) {
        long[] biggerArray, smallerArray;
        if (this.data.length > other.data.length) {
            biggerArray = this.data;
            smallerArray = other.data;
        } else {
            biggerArray = other.data;
            smallerArray = this.data;
        }
        long[] result = new long[biggerArray.length];
        for (int i = 0; i < smallerArray.length; i++) {
            result[i] = this.data[i] & (~(other.data[i]));
        }
        if (biggerArray.length != smallerArray.length && biggerArray == this.data) {
            System.arraycopy(biggerArray, smallerArray.length, result, smallerArray.length, biggerArray.length - smallerArray.length);
        }
        return new IntSet(result);
    }

    /**
     * Checks if the current set is subset of the given set.
     *
     * @param other
     * @return
     */
    public boolean isSubsetOf(IntSet other) {
        for (int i = 0; i < this.data.length && i < other.data.length; i++) {
            if (!(this.data[i] == (this.data[i] & other.data[i]))) {
                return false;
            }
        }
        if (this.data.length <= other.data.length) {
            return true;
        }
        for (int i = other.data.length; i < this.data.length; i++) {
            if (!(this.data[i] == 0)) {
                return false;
            }
        }
        return true;
    }

    private void resizeArray(int valueOrder) {
        long[] newData = new long[valueOrder + 1];
        System.arraycopy(data, 0, newData, 0, data.length);
        data = newData;
    }

}
