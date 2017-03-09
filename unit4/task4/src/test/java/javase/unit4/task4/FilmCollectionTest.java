package javase.unit4.task4;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Set;

import static org.junit.Assert.*;

public class FilmCollectionTest {

    String fileName = "serialCollection";
    FilmCollection expectedFilmCollectionWithTestData = formFilmCollectionWithTestData();

    @Test
    public void testLoad() throws IOException, ClassNotFoundException {
        createAndSaveFilmCollectionWithTestData();

        FilmCollection filmCollection = FilmCollection.load(fileName);

        assertEquals(expectedFilmCollectionWithTestData, filmCollection);
    }

    @Test(expected = FileNotFoundException.class)
    public void testLoadIfWhereIsNoFile() throws IOException, ClassNotFoundException {
        deleteFilmCollectionWithTestData();

        FilmCollection.load(fileName);
    }

    @Test(expected = NullPointerException.class)
    public void testLoadWithNull() throws IOException, ClassNotFoundException {
        new FilmCollection().load(null);
    }

    @Test
    public void testSave() throws IOException, ClassNotFoundException {
        deleteFilmCollectionWithTestData();

        FilmCollection filmCollection = formFilmCollectionWithTestData();
        filmCollection.save(fileName);

        filmCollection = FilmCollection.load(fileName);

        assertEquals(expectedFilmCollectionWithTestData, filmCollection);
    }

    @Test(expected = NullPointerException.class)
    public void testSaveWithNull() throws IOException {
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

        assertTrue(actors.isEmpty());
    }

    @Test
    public void testGetFilms() {
        FilmCollection filmCollection = formFilmCollectionWithTestData();

        Set<Film> actors = filmCollection.getFilms();

        assertEquals(5, actors.size());
    }

    @Test
    public void testGetFilmsIfWhereIsNoFilms() {
        FilmCollection filmCollection = new FilmCollection();

        Set<Film> actors = filmCollection.getFilms();

        assertTrue(actors.isEmpty());
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

        filmCollection.add(new Film("film", new Actor("actor")));
        assertEquals(1, filmCollection.size());

        filmCollection.add(new Film("film", new Actor("actor")));
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

    private void createAndSaveFilmCollectionWithTestData() throws IOException {
        deleteFilmCollectionWithTestData();

        FilmCollection filmCollection = formFilmCollectionWithTestData();
        filmCollection.save(fileName);
    }

    private void deleteFilmCollectionWithTestData() {
        File file = new File(fileName);
        file.delete();
    }
}
