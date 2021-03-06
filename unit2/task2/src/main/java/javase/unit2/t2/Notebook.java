package javase.unit2.t2;

/**
 * Class represents a notebook.
 *
 * Created by andrey on 23.02.2017.
 */
public class Notebook implements StationaryItem {
    private int pages;
    private int cost;
    private String name;

    public Notebook(int pages, int cost, String name) {
        this.pages = pages;
        this.cost = cost;
        this.name = name;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public int getCost() {
        return cost;
    }

    public void setCost() {
        this.cost = cost;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
