package javase.unit2.t3;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by andrey on 23.02.2017.
 */
public class BeginnerSet {
    private Set<StationaryItem> items = new HashSet<>();

    public BeginnerSet() {
        items.add(new Pen(10, "blue", 50, "BluePen"));
        items.add(new Pen(10, "black", 50, "BlackPen"));
        items.add(new Pencil(10, "black", 30, "TypicalPencil"));
        items.add(new Eraser(20, "JustEraser"));
        Eraser eraser = new Eraser(0, "PencilsEraser");
        items.add(new PencilWithEraser(10, "black", 30, "TypicalPencil", eraser));
        items.add(new Notebook(100, 150, "CoolNotebook"));
    }

    public Set<StationaryItem> getItems() {
        return items;
    }
}
