package javase.unit2.t3;

/**
 * Created by andrey on 23.02.2017.
 */
public abstract class WritingItem implements StationaryItem {
    private int width;
    private String color;
    private int cost;
    private String name;

    public WritingItem(int width, String color, int cost, String name) {
        this.width = width;
        this.color = color;
        this.cost = cost;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
