package javase.unit4.task4;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class FilmCollection implements Serializable {

    private Set<Film> films = new HashSet<>();

    public static FilmCollection load(String fileName) throws IOException, ClassNotFoundException {
        File loadingFile = new File(fileName);
        if (!loadingFile.exists()) {
            throw new FileNotFoundException();
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            return (FilmCollection) inputStream.readObject();
        }
    }

    public void save(String fileName) throws IOException {
        File creatingFile = new File(fileName);
        creatingFile.delete();

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(creatingFile))) {
            outputStream.writeObject(this);
            creatingFile.createNewFile();
        }
    }

    public Set<Actor> getActors() {
        Set<Actor> actors = new HashSet<>();

        films.forEach((film) -> actors.addAll(film.getActors()));

        return actors;
    }

    public int size() {
        return films.size();
    }

    public void add(Film film) {
        Objects.requireNonNull(film);

        films.add(film);
    }

    public void remove(Film film) {
        Objects.requireNonNull(film);

        films.remove(film);
    }

    public Set<Film> getFilms() {
        return new HashSet<>(films);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilmCollection that = (FilmCollection) o;

        return films != null ? films.equals(that.films) : that.films == null;
    }

    @Override
    public int hashCode() {
        return films != null ? films.hashCode() : 0;
    }
}
