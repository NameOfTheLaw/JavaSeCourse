package javase.unit2.t1;

/**
 * Class represents just a pen object.
 *
 * Created by andrey on 23.02.2017.
 */
public class Pen {
    private int width;
    private String color;
    private int cost;
    private String name;

    public Pen(int width, String color, int cost, String name) {
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pen pen = (Pen) o;

        if (width != pen.width) return false;
        if (cost != pen.cost) return false;
        if (color != null ? !color.equals(pen.color) : pen.color != null) return false;
        return name != null ? name.equals(pen.name) : pen.name == null;
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + cost;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Pen{" +
                "width=" + width +
                ", color='" + color + '\'' +
                ", cost=" + cost +
                ", name='" + name + '\'' +
                '}';
    }
}
