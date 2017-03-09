package javase.unit4.task4;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Film implements Serializable {

    private String name;
    private Set<Actor> actors;

    public Film(String name, Actor... actors) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(actors);

        this.name = name;
        this.actors = new HashSet<>(Arrays.asList(actors));
    }

    public String getName() {
        return name;
    }

    public Set<Actor> getActors() {
        return new HashSet<>(actors);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        if (name != null ? !name.equals(film.name) : film.name != null) return false;
        return actors != null ? actors.equals(film.actors) : film.actors == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (actors != null ? actors.hashCode() : 0);
        return result;
    }
}
