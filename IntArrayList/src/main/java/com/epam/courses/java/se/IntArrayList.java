package com.epam.courses.java.se;

import java.util.Arrays;

/**
 * Represents the list structure for int values.
 *
 * Based on <code>int[]</code>.
 * Size of the <code>IntArrayList</code> object is dynamic and depends on the last index of the inserted value.
 * Once you add an <code>i</code> value in the list, size of it would always be more than for <code>i</code> values.
 * Created by andrey on 02.02.2017.
 */
public class IntArrayList {

    public static final int RECURSIVE_BINARY_SEARCH = 1;
    public static final int CYCLE_BINARY_SEARCH = 2;

    private int[] data;
    private int size;

    public IntArrayList() {
        this.data = new int[10];
        size = 0;
    }

    public IntArrayList(int[] data) {
        this.data = Arrays.copyOf(data, data.length);
        size = data.length;
    }

    /**
     * Add a value to the list.
     *
     * @param value
     */
    public void add(int value) {
        ensureCapasity(size + 1);
        data[size] = value;
        size += 1;
    }

    /**
     * Returns to <code>i</code> value in the list.
     *
     * @param i
     * @return value
     */
    public int get(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        return data[i];
    }

    /**
     * Removes an element by the index from the list.
     *
     * @param i
     */
    public void remove(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        for (int j = i; j < size-1; j++) {
            data[j] = data[j+1];
        }
        size -= 1;
    }

    /**
     * Sort the list of the elements with increasing.
     *
     * Uses <code>mergeSort()</code>.
     */
    public void sort() {
        mergeSort(data, 0, size(), new int[size()]);
    }

    private void mergeSort(int[] data, int startInclusive, int endExclusive, int[] free) {
        int length = endExclusive - startInclusive;
        if (length <= 1) {
            return;
        }

        int mid = startInclusive + length/2;

        mergeSort(data, startInclusive, mid, free);
        mergeSort(data, mid, endExclusive, free);

        merge(data, startInclusive, mid, endExclusive, free);
    }

    private void merge(int[] data, int startInclusive, int mid, int endExclusive, int[] free) {
        System.arraycopy(data, startInclusive, free, startInclusive, endExclusive - startInclusive);

        int i = startInclusive;
        int j = mid;

        for (int k = startInclusive; k < endExclusive; k++) {
            if (i >= mid) data[k] = free[j++];
            else if (j >= endExclusive) data[k] = free[i++];
            else if (free[i] > free [j]) data[k] = free[j++];
            else data[k] = free[i++];
        }
    }

    /**
     * Returns the index of the given value or index to insert in the sorted collection.
     *
     * Uses default search algorithm.
     * @param value
     * @return index of the value or -indexToInsert-1
     */
    public int indexOf(int value) {
        return recursiveBinarySearch(data, 0, size, value);
    }

    /**
     * Returns the index of the given value or index to insert in the sorted collection.
     *
     * Uses recursive binary search algorithm.
     * @param value
     * @return index of the value or -indexToInsert-1
     */
    public int recursiveIndexOf(int value) {
        return recursiveBinarySearch(data, 0, size, value);
    }

    /**
     * Returns the index of the given value or index to insert in the sorted collection.
     *
     * Uses cyclic binary search algorithm.
     * @param value
     * @return index of the value or -indexToInsert-1
     */
    public int cyclicIndexOf(int value) {
        return cyclicBinarySearch(data, 0, size, value);
    }

    /**
     * Current size of the list
     *
     * @return <code>index + 1</code> of the last element in the list or <code>0</code> if list is empty.
     */
    public int size() {
        return size;
    }

    private int getCapacity() {
        return data.length;
    }

    private void ensureCapasity(int requiredCapasity) {
        if (requiredCapasity <= getCapacity()) {
            return;
        }
        int newCapacity = Math.max(requiredCapasity, (getCapacity() * 3) / 2 + 1);
        data = Arrays.copyOf(data, newCapacity);
    }

    private int recursiveBinarySearch(int[] data, int startInclusive, int endExclusive, int value) {
        int length = endExclusive - startInclusive;
        if (length == 0) {
            return -startInclusive - 1;
        }

        int mid = startInclusive + length/2;
        if (data[mid] == value) {
            return mid;
        } else if (data[mid] > value) {
            return recursiveBinarySearch(data, startInclusive, mid, value);
        } else {
            return recursiveBinarySearch(data, mid + 1, endExclusive, value);
        }
    }

    private int cyclicBinarySearch(int[] data, int startInclusive, int endExclusive, int value) {
        //Every time the distance between startInclusive and endExclusive gets shorter.
        //So we can loop while(true) and be sure that the cycle is going to stop.
        while (true) {
            int length = endExclusive - startInclusive;
            if (length == 0) {
                return -startInclusive - 1;
            }

            int mid = startInclusive + length/2;
            if (data[mid] == value) {
                return mid;
            } else if (data[mid] > value) {
                endExclusive = mid;
            } else {
                startInclusive = mid + 1;
            }
        }
    }
}
