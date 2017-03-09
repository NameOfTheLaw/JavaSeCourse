package javase.unit4.task4;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class FilmTest {

    @Test
    public void testCreate() {
        Film film = new Film("film", new Actor("actor"));

        assertEquals("film", film.getName());

        Set<Actor> expectedActors = new HashSet<>();
        expectedActors.add(new Actor("actor"));

        assertEquals(expectedActors, film.getActors());
    }

    @Test
    public void testCreateWithMultiplyActors() {
        Film film = new Film("film", new Actor("actor1"), new Actor("actor2"));

        assertEquals("film", film.getName());

        Set<Actor> expectedActors = new HashSet<>();
        expectedActors.add(new Actor("actor1"));
        expectedActors.add(new Actor("actor2"));

        assertEquals(expectedActors, film.getActors());
    }

    @Test(expected = NullPointerException.class)
    public void testCreateWithNullArg1() {
        new Film(null, new Actor("actor"));
    }

    @Test(expected = NullPointerException.class)
    public void testCreateWithNullArg2() {
        new Film("film", null);

    }

    @Test(expected = NullPointerException.class)
    public void testCreateWithNullArgs() {
        new Film(null, null);
    }

    @Test
    public void testEqualsWithItself() {
        Film film1 = new Film("film", new Actor("actor1"));

        assertTrue(film1.equals(film1));
    }

    @Test
    public void testEquals() {
        Film film1 = new Film("film", new Actor("actor"));
        Film film2 = new Film("film", new Actor("actor"));

        assertTrue(film1.equals(film2));
    }

    @Test
    public void testNonEqualsWithDifferentFilmsNames() {
        Film film1 = new Film("film1", new Actor("actor"));
        Film film2 = new Film("film2", new Actor("actor"));

        assertFalse(film1.equals(film2));
    }

    @Test
    public void testNonEqualsWithDifferentActorsNames() {
        Film film1 = new Film("film", new Actor("actor1"));
        Film film2 = new Film("film", new Actor("actor2"));

        assertFalse(film1.equals(film2));
    }
}
