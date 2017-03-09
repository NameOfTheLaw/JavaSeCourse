package javase.unit4.task4;

import java.io.Serializable;

public class Actor implements Serializable {

    private final String name;

    public Actor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Actor actor = (Actor) o;

        return name != null ? name.equals(actor.name) : actor.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
