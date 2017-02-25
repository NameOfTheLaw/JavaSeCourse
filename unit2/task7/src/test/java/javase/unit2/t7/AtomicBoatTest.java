package javase.unit2.t7;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by andrey on 25.02.2017.
 */
public class AtomicBoatTest {

    private AtomicBoat atomicBoat;

    @Before
    public void beforeMethod() {
        atomicBoat = new AtomicBoat();
    }

    @Test
    public void testOfUsage() throws Exception {
        assertFalse(atomicBoat.isMoving());
        atomicBoat.on();
        assertFalse(atomicBoat.isMoving());
        atomicBoat.move();
        assertTrue(atomicBoat.isMoving());
        atomicBoat.stop();
        assertFalse(atomicBoat.isMoving());
        atomicBoat.off();
        assertFalse(atomicBoat.isMoving());
    }

}