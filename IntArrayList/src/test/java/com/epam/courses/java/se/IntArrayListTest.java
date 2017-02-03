package com.epam.courses.java.se;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by andrey on 03.02.2017.
 */
public class IntArrayListTest {

    @Test
    public void add() throws Exception {
        IntArrayList arrayList = new IntArrayList();
        assertEquals(-1, arrayList.indexOf(10));
        arrayList.add(10);
        assertEquals(0, arrayList.indexOf(10));
        assertEquals(-1, arrayList.indexOf(-5));
        arrayList.add(-5);
        assertEquals(1, arrayList.indexOf(-5));
        arrayList.add(7);
        assertEquals(2, arrayList.indexOf(7));
    }

    @Test
    public void remove() throws Exception {
        IntArrayList arrayList = new IntArrayList();
        assertEquals(-1, arrayList.indexOf(5));
        arrayList.add(5);
        assertEquals(0, arrayList.indexOf(5));
        arrayList.add(7);
        assertEquals(1, arrayList.indexOf(7));
        assertEquals(2, arrayList.size());
        arrayList.remove(0);
        assertEquals(-1, arrayList.indexOf(5));
        assertEquals(0, arrayList.indexOf(7));
        assertEquals(1, arrayList.size());
    }

    @Test
    public void get() throws Exception {
        IntArrayList arrayList = new IntArrayList(new int[] {3, 4, 7, 8});
        assertEquals(7, arrayList.get(2));
        arrayList.remove(1);
        assertEquals(7, arrayList.get(1));
        assertEquals(8, arrayList.get(2));
        arrayList.remove(0);
        arrayList.remove(0);
        assertEquals(8, arrayList.get(0));
        arrayList.add(9);
        assertEquals(9, arrayList.get(1));
    }

    @Test
    public void size() throws Exception {
        IntArrayList arrayList = new IntArrayList(new int[] {4, 5, 7});
        assertEquals(3, arrayList.size());
        arrayList.add(5);
        assertEquals(4, arrayList.size());
        arrayList.add(8);
        arrayList.remove(0);
        assertEquals(4, arrayList.size());
        arrayList.remove(0);
        assertEquals(3, arrayList.size());
    }

    @Test
    public void sort() throws Exception {
        final int[] ints = {12, 0, -13, 666, 2, 56, 56, 56, 120, -1, 1, 0, Integer.MAX_VALUE, Integer.MIN_VALUE};
        final int[] expected = Arrays.copyOf(ints, ints.length);
        Arrays.sort(expected);

        final IntArrayList list = new IntArrayList(ints);

        list.sort();

        for (int i = 0; i < expected.length; i++) {
            assertEquals("i = " + i, expected[i], list.get(i));
        }
    }

    @Test
    public void recursiveIndexOf() throws Exception {
        int[] testData = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        IntArrayList arrayList = new IntArrayList(testData);

        assertEquals(9, arrayList.recursiveIndexOf(10));
        assertEquals(0, arrayList.recursiveIndexOf(1));
        assertEquals(1, arrayList.recursiveIndexOf(2));
        assertEquals(3, arrayList.recursiveIndexOf(4));

        assertEquals(-1, arrayList.recursiveIndexOf(-5));
        assertEquals(-11, arrayList.recursiveIndexOf(15));

        testData = new int[] {-5, 1, 3, 7, 10};
        arrayList = new IntArrayList(testData);

        assertEquals(-4, arrayList.recursiveIndexOf(4));
        assertEquals(-3, arrayList.recursiveIndexOf(2));
        assertEquals(0, arrayList.recursiveIndexOf(-5));
        assertEquals(-1, arrayList.recursiveIndexOf(-6));
    }

    @Test
    public void cyclicIndexOf() throws Exception {
        int[] testData = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        IntArrayList arrayList = new IntArrayList(testData);

        assertEquals(9, arrayList.cyclicIndexOf(10));
        assertEquals(0, arrayList.cyclicIndexOf(1));
        assertEquals(1, arrayList.cyclicIndexOf(2));
        assertEquals(3, arrayList.cyclicIndexOf(4));

        assertEquals(-1, arrayList.cyclicIndexOf(-5));
        assertEquals(-11, arrayList.cyclicIndexOf(15));

        testData = new int[] {-5, 1, 3, 7, 10};
        arrayList = new IntArrayList(testData);

        assertEquals(-4, arrayList.cyclicIndexOf(4));
        assertEquals(-3, arrayList.cyclicIndexOf(2));
        assertEquals(0, arrayList.cyclicIndexOf(-5));
        assertEquals(-1, arrayList.cyclicIndexOf(-6));
    }

}