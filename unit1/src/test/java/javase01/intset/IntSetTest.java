package javase01.intset;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by andrey on 02.02.2017.
 */
public class IntSetTest {

    @Test
    public void add() throws Exception {
        IntSet set = new IntSet();

        assertFalse(set.contains(0));
        set.add(0);
        assertTrue(set.contains(0));

        assertFalse(set.contains(7));
        set.add(7);
        set.add(7);
        assertTrue(set.contains(7));

        assertFalse(set.contains(67));
        set.add(67);
        set.add(67);
        assertTrue(set.contains(67));

        set.add(-1);
        assertFalse(set.contains(-1));
    }

    @Test
    public void remove() throws Exception {
        IntSet set = new IntSet();

        set.add(67);
        assertTrue(set.contains(67));
        set.remove(67);
        assertFalse(set.contains(67));
        set.remove(67);
        assertFalse(set.contains(67));

        set.remove(5);
        assertFalse(set.contains(5));

        set.remove(-1);
        assertFalse(set.contains(-1));

    }

    @Test
    public void contains() throws Exception {
        IntSet set = new IntSet();

        for (int i=-1; i < 256; i++) {
            assertFalse(set.contains(i));
        }

        set.add(5);
        set.add(-1);
        set.add(63);
        set.add(63);
        set.add(64);

        for (int i=-1; i < 129; i++) {
            if (i == 5 || i == 63 || i == 64) {
                assertTrue(set.contains(i));
            } else {
                assertFalse(set.contains(i));
            }
        }

    }

    @Test
    public void union() throws Exception {
        IntSet set = new IntSet();
        IntSet otherSet = new IntSet();

        set.add(-1);
        set.add(5);
        set.add(63);
        set.add(127);
        set.add(128);

        otherSet.add(64);
        otherSet.add(127);
        otherSet.add(129);

        IntSet unionSet = set.union(otherSet);

        for (int i=-1; i < 130; i++) {
            if (i == 5 || i == 63 || i == 64 || i == 127 || i == 128 || i == 129) {
                assertTrue(unionSet.contains(i));
            } else {
                assertFalse(unionSet.contains(i));
            }
        }

        set = new IntSet();
        otherSet = new IntSet();

        set.add(5);
        set.add(7);

        otherSet.add(70);
        otherSet.add(80);

        unionSet = set.union(otherSet);

        for (int i=-1; i < 130; i++) {
            if (i == 5 || i == 7 || i == 70 || i == 80) {
                assertTrue(unionSet.contains(i));
            } else {
                assertFalse(unionSet.contains(i));
            }
        }
    }

    @Test
    public void intersection() throws Exception {
        IntSet set = new IntSet();
        IntSet otherSet = new IntSet();

        set.add(-1);
        set.add(5);
        set.add(63);
        set.add(127);
        set.add(128);

        otherSet.add(-1);
        otherSet.add(64);
        otherSet.add(64);
        otherSet.add(127);
        otherSet.add(129);

        IntSet intersectionSet = set.intersection(otherSet);

        for (int i=-1; i < 130; i++) {
            if (i == 127) {
                assertTrue(intersectionSet.contains(i));
            } else {
                assertFalse(intersectionSet.contains(i));
            }
        }

        set = new IntSet();
        otherSet = new IntSet();

        set.add(10);
        set.add(20);
        set.add(64);
        set.add(70);

        otherSet.add(-1);
        otherSet.add(10);
        otherSet.add(64);
        otherSet.add(127);
        otherSet.add(129);

        intersectionSet = set.intersection(otherSet);

        for (int i=-1; i < 130; i++) {
            if (i == 10 || i == 64) {
                assertTrue(intersectionSet.contains(i));
            } else {
                assertFalse(intersectionSet.contains(i));
            }
        }
    }

    @Test
    public void difference() throws Exception {
        IntSet set = new IntSet();
        IntSet otherSet = new IntSet();

        set.add(-1);
        set.add(5);
        set.add(63);
        set.add(127);
        set.add(128);

        otherSet.add(-1);
        otherSet.add(64);
        otherSet.add(64);
        otherSet.add(127);
        otherSet.add(129);

        IntSet differenceSet = set.difference(otherSet);

        for (int i=-1; i < 130; i++) {
            if (i == 5 || i == 63 || i == 64 || i == 128 || i == 129) {
                assertTrue(differenceSet.contains(i));
            } else {
                assertFalse(differenceSet.contains(i));
            }
        }
    }

    @Test
    public void isSubsetOf() throws Exception {
        IntSet set = new IntSet();
        IntSet subset = new IntSet();
        assertTrue(subset.isSubsetOf(set));
        set.add(5);
        set.add(6);
        set.add(7);
        set.add(8);
        set.add(70);
        set.add(129);
        assertTrue(subset.isSubsetOf(set)); //Empty set is subset of any set
        subset.add(70);
        subset.add(8);
        assertTrue(subset.isSubsetOf(set));
        subset.add(75);
        assertFalse(subset.isSubsetOf(set));
        set.add(75);
        assertTrue(subset.isSubsetOf(set));
        assertFalse(set.isSubsetOf(subset));
    }

    @Test
    public void minus() throws Exception {
        IntSet set = new IntSet();
        IntSet otherSet = new IntSet();

        set.add(10);
        set.add(20);
        set.add(64);
        set.add(70);

        otherSet.add(-1);
        otherSet.add(10);
        otherSet.add(64);
        otherSet.add(127);
        otherSet.add(129);

        IntSet minusSet = set.minus(otherSet);

        for (int i=-1; i < 130; i++) {
            if (i == 20 || i == 70) {
                assertTrue(minusSet.contains(i));
            } else {
                assertFalse(minusSet.contains(i));
            }
        }
    }

}