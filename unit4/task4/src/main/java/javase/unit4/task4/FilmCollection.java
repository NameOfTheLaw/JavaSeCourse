package javase.unit4.task4;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class of film collection with methods to edit, save and load serialized collections.
 */
public class FilmCollection implements Serializable {

    private Set<Film> films = new HashSet<>();

    /**
     * Loads an existing film collection from the file with given file name.
     *
     * @param fileName file with serialized film collection.
     * @return deserialized film collection.
     * @throws IOException if where is no such a file or it is somehow broken.
     * @throws ClassNotFoundException if deserialized film collection have another version of itself.
     */
    public static FilmCollection load(String fileName) throws IOException, ClassNotFoundException {
        File loadingFile = new File(fileName);
        if (!loadingFile.exists()) {
            throw new FileNotFoundException();
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            return (FilmCollection) inputStream.readObject();
        }
    }

    /**
     * Saves current film collection to a file with the given file name.
     * @param fileName file for serialize film collection to.
     * @throws IOException if a file is somehow broken.
     */
    public void save(String fileName) throws IOException {
        File creatingFile = new File(fileName);
        creatingFile.delete();

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(creatingFile))) {
            outputStream.writeObject(this);
            creatingFile.createNewFile();
        }
    }

    /**
     * Returns a set of all actors of all of the films in the collection.
     *
     * @return
     */
    public Set<Actor> getActors() {
        return films.stream()
                .map(Film::getActors)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a number of the films in the collection.
     *
     * @return
     */
    public int size() {
        return films.size();
    }

    /**
     * Adds a given film into the collection.
     *
     * @param film
     */
    public void add(Film film) {
        Objects.requireNonNull(film);

        films.add(film);
    }

    /**
     * Removes a given film from the collection.
     *
     * @param film
     */
    public void remove(Film film) {
        Objects.requireNonNull(film);

        films.remove(film);
    }

    /**
     * Returns a set of all of the films in collection.
     *
     * @return
     */
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
