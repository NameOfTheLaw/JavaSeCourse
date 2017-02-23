package javase.unit2.t2;

import javase.unit2.t2.stationaryitems.StationaryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 23.02.2017.
 */
public class StationeryRecorder {
    private List<StationaryItem> items = new ArrayList<>();

    public void addItem(StationaryItem item) {
        if (item == null) {
            throw new NullPointerException();
        }
        items.add(item);
    }

    public void removeItem(StationaryItem item) {
        if (item == null) {
            throw new NullPointerException();
        }
        items.remove(item);
    }

    public boolean contains(StationaryItem item) {
        if (item == null) {
            throw new NullPointerException();
        }
        return items.contains(item);
    }

    public void clear() {
        items.clear();
    }

    public int getItemsCount() {
        return items.size();
    }

    public int getSummaryCost() {
        return items.stream()
                .mapToInt(item -> item.getCost())
                .sum();
    }
}
