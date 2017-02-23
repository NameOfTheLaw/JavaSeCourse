package javase.unit2.t4;

/**
 * Created by andrey on 23.02.2017.
 */
public class Eraser implements StationaryItem {
    private int cost;
    private String name;

    public Eraser(int cost, String name) {
        this.cost = cost;
        this.name = name;
    }

    @Override
    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
