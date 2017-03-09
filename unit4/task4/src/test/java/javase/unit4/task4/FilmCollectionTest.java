package javase.unit4.task4;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;

import static org.junit.Assert.*;

public class FilmCollectionTest {

    String fileName = "serialCollection";
    FilmCollection expectedFilmCollectionWithTestData = formFilmCollectionWithTestData();

    @Test
    public void testLoad() {
        createAndSaveFilmCollectionWithTestData();

        filmCollection = FilmCollection.load(fileName);

        assertEquals(expectedFilmCollectionWithTestData, filmCollection);
    }

    @Test(expected = FileNotFoundException.class)
    public void testLoadIfWhereIsNoFile() {
        deleteFilmCollectionWithTestData();

        FilmCollection.load(fileName);
    }

    @Test(expected = NullPointerException.class)
    public void testLoadWithNull() {
        new FilmCollection().load(null);
    }

    @Test
    public void testSave() {
        deleteFilmCollectionWithTestData();

        FilmCollection filmCollection = formFilmCollectionWithTestData();
        filmCollection.save(fileName);

        filmCollection = FilmCollection.load(fileName);

        assertEquals(expectedFilmCollectionWithTestData, filmCollection);
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void testSaveIfFileIsAlreadyExists() {
        createAndSaveFilmCollectionWithTestData();

        FilmCollection filmCollection = formFilmCollectionWithTestData();
        filmCollection.save(fileName);
    }

    @Test(expected = NullPointerException.class)
    public void testSaveWithNull() {
        new FilmCollection().save(null);
    }

    @Test
    public void testGetActors() {
        FilmCollection filmCollection = formFilmCollectionWithTestData();

        Set<Actor> actors = filmCollection.getActors();

        assertEquals(3, actors.size());
    }

    @Test
    public void testGetActorsIfWhereIsNoFilms() {
        FilmCollection filmCollection = new FilmCollection();

        Set<Actor> actors = filmCollection.getActors();

        assertEquals(actors.isEmpty());
    }

    @Test
    public void testAddFilm() {
        FilmCollection filmCollection = new FilmCollection();
        assertEquals(0, filmCollection.size());

        filmCollection.add(new Film("film1", new Actor("actor")));
        assertEquals(1, filmCollection.size());

        filmCollection.add(new Film("film2", new Actor("actor")));
        assertEquals(2, filmCollection.size());
    }

    @Test(expected = NullPointerException.class)
    public void testAddFilmWithNull() {
        new FilmCollection().add(null);
    }

    @Test
    public void testAddFilmWithTheFilmAlreadyExists() {
        FilmCollection filmCollection = new FilmCollection();
        assertEquals(0, filmCollection.size());

        Film film = new Film("film", new Actor("actor"));

        filmCollection.add(film);
        assertEquals(1, filmCollection.size());

        filmCollection.add(film);
        assertEquals(1, filmCollection.size());
    }

    @Test
    public void testRemoveFilm() {
        FilmCollection filmCollection = new FilmCollection();
        assertEquals(0, filmCollection.size());

        Film film = new Film("film", new Actor("actor"));

        filmCollection.add(film);
        assertEquals(1, filmCollection.size());

        filmCollection.remove(film);
        assertEquals(0, filmCollection.size());
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveFilmWithNull() {
        new FilmCollection().remove(null);
    }

    @Test
    public void testRemoveFilmTwice() {
        FilmCollection filmCollection = new FilmCollection();
        assertEquals(0, filmCollection.size());

        Film film = new Film("film", new Actor("actor"));

        filmCollection.add(film);
        assertEquals(1, filmCollection.size());

        filmCollection.remove(film);
        assertEquals(0, filmCollection.size());

        filmCollection.remove(film);
        assertEquals(0, filmCollection.size());
    }

    private FilmCollection formFilmCollectionWithTestData() {
        FilmCollection filmCollection = new FilmCollection();

        filmCollection.add(new Film("Film Name 1", new Actor("Main Actor 1")));
        filmCollection.add(new Film("Film Name 2", new Actor("Main Actor 2")));
        filmCollection.add(new Film("Film Name 3", new Actor("Main Actor 2")));
        filmCollection.add(new Film("Film Name 4", new Actor("Main Actor 3")));
        filmCollection.add(new Film("Film Name 5", new Actor("Main Actor 3")));

        return filmCollection;
    }

    private void createAndSaveFilmCollectionWithTestData() {
        deleteFilmCollectionWithTestData();

        FilmCollection filmCollection = formFilmCollectionWithTestData();
        filmCollection.save(fileName);
    }

    private void deleteFilmCollectionWithTestData() {
        File file = new File(fileName);
        file.delete();
    }
}
