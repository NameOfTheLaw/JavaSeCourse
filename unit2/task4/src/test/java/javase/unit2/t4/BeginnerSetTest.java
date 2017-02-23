package javase.unit2.t4;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by andrey on 23.02.2017.
 */
public class BeginnerSetTest {

    @Test
    public void testSortByCost() {
        BeginnerSet beginnerSet = new BeginnerSet();

        List<StationaryItem> expected = beginnerSet.getItems().stream()
                .sorted(Comparator.comparing(StationaryItem::getCost))
                .collect(Collectors.toList());

        List<StationaryItem> sortedSet = beginnerSet.sortByCost();

        assertEquals(expected, sortedSet);
    }

    @Test
    public void testSortByName() {
        BeginnerSet beginnerSet = new BeginnerSet();

        List<StationaryItem> expected = beginnerSet.getItems().stream()
                .sorted(Comparator.comparing(StationaryItem::getName))
                .collect(Collectors.toList());

        List<StationaryItem> sortedSet = beginnerSet.sortByName();

        assertEquals(expected, sortedSet);
        assertArrayEquals(expected.toArray(), sortedSet.toArray());
    }

    @Test
    public void testSortByCostAndName() {
        BeginnerSet beginnerSet = new BeginnerSet();
        List<StationaryItem> expected = beginnerSet.getItems().stream()
                .sorted(Comparator.comparing(StationaryItem::getCost)
                    .thenComparing(Comparator.comparing(StationaryItem::getName)))
                .collect(Collectors.toList());

        List<StationaryItem> sortedSet = beginnerSet.sortByCostAndName();
    }
}