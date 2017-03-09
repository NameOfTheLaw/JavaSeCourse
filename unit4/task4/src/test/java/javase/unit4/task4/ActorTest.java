package javase.unit4.task4;

import org.junit.Test;

import static org.junit.Assert.*;

public class ActorTest {

    @Test
    public void testCreate() {
        Actor actor = new Actor("name");

        assertEquals("name", actor.getName());
    }

    @Test
    public void testEquals() {
        Actor actor1 = new Actor("name");
        Actor actor2 = new Actor("name");

        assertTrue(actor1.equals(actor2));
    }

    @Test
    public void testEqualsWithItself() {
        Actor actor1 = new Actor("name");

        assertTrue(actor1.equals(actor1));
    }

    @Test
    public void testNonEqualsWithDifferentName() {
        Actor actor1 = new Actor("name1");
        Actor actor2 = new Actor("name2");

        assertFalse(actor1.equals(actor2));
    }
}
