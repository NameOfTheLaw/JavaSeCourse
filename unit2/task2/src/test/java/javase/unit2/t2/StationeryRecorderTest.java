package javase.unit2.t2;

import javase.unit2.t1.Pen;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by andrey on 23.02.2017.
 */
public class StationeryRecorderTest {

    @Test
    public void testAddItem() {
        StationeryRecorder recorder = new StationeryRecorder();

        Pen pen = new Pen(10, "red", 50, "cool pen");
        Notebook notebook = new Notebook(10, 150, "cool notebook");

        assertFalse(recorder.contains(pen));
        assertFalse(recorder.contains(notebook));
        recorder.addItem(pen);
        recorder.addItem(notebook);
        assertTrue(recorder.contains(pen));
        assertTrue(recorder.contains(notebook));
    }

    @Test
    public void testAddItemWithTheDublicatedItem() {
        StationeryRecorder recorder = new StationeryRecorder();

        Pen pen = new Pen(10, "red", 50, "cool pen");

        assertFalse(recorder.contains(pen));
        recorder.addItem(pen);
        recorder.addItem(pen);
        assertTrue(recorder.contains(pen));
        recorder.removeItem(pen);
        assertTrue(recorder.contains(pen));
        recorder.removeItem(pen);
        assertFalse(recorder.contains(pen));
    }

    @Test(expected = NullPointerException.class)
    public void testAddItemIfArgIsNull() {
        StationeryRecorder recorder = new StationeryRecorder();

        recorder.addItem(null);
    }

    @Test
    public void testRemoveItem() {
        StationeryRecorder recorder = new StationeryRecorder();

        Pen pen = new Pen(10, "red", 50, "cool pen");

        assertFalse(recorder.contains(pen));
        recorder.addItem(pen);
        assertTrue(recorder.contains(pen));
        recorder.removeItem(pen);
        assertFalse(recorder.contains(pen));
        recorder.removeItem(pen);
        assertFalse(recorder.contains(pen));
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveItemIfArgIsNull() {
        StationeryRecorder recorder = new StationeryRecorder();

        recorder.removeItem(null);
    }

    @Test
    public void testContains() {
        StationeryRecorder recorder = new StationeryRecorder();

        Pen pen = new Pen(10, "red", 50, "cool pen");

        assertFalse(recorder.contains(pen));
        recorder.addItem(pen);
        assertTrue(recorder.contains(pen));
        recorder.removeItem(pen);
        assertFalse(recorder.contains(pen));
    }

    @Test(expected = NullPointerException.class)
    public void testContainsIfArgIsNull() {
        StationeryRecorder recorder = new StationeryRecorder();

        recorder.contains(null);
    }

    @Test
    public void testClear() {
        StationeryRecorder recorder = new StationeryRecorder();

        Pen pen = new Pen(10, "red", 50, "cool pen");
        Notebook notebook = new Notebook(10, 150, "cool notebook");

        recorder.clear();
        recorder.addItem(pen);
        recorder.addItem(notebook);
        assertEquals(2, recorder.getItemsCount());
        recorder.clear();
        assertEquals(0, recorder.getItemsCount());
    }

    @Test
    public void testGetItemsCount() {
        StationeryRecorder recorder = new StationeryRecorder();

        Pen pen = new Pen(10, "red", 50, "cool pen");
        Notebook notebook = new Notebook(10, 150, "cool notebook");

        recorder.addItem(pen);
        recorder.addItem(notebook);
        assertEquals(2, recorder.getItemsCount());
        recorder.removeItem(pen);
        assertEquals(1, recorder.getItemsCount());
        recorder.removeItem(notebook);
        assertEquals(0, recorder.getItemsCount());
    }

    @Test
    public void testGetSummaryCost() {
        StationeryRecorder recorder = new StationeryRecorder();

        Pen pen = new Pen(10, "red", 50, "cool pen");
        Notebook notebook = new Notebook(10, 150, "cool notebook");

        assertEquals(0, recorder.getSummaryCost());
        recorder.addItem(pen);
        assertEquals(50, recorder.getSummaryCost());
        recorder.addItem(pen);
        assertEquals(100, recorder.getSummaryCost());
        recorder.addItem(notebook);
        assertEquals(250, recorder.getSummaryCost());
        recorder.clear();
        assertEquals(0, recorder.getSummaryCost());
    }
}