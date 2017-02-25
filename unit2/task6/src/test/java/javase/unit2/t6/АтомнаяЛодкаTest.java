package javase.unit2.t6;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by andrey on 25.02.2017.
 */
public class АтомнаяЛодкаTest {

    private АтомнаяЛодка атомнаяЛодка;

    @Before
    public void beforeMethod() {
        атомнаяЛодка = new АтомнаяЛодка();
    }

    @Test
    public void testOfUsage() throws Exception {
        assertFalse(атомнаяЛодка.движется());
        атомнаяЛодка.включить();
        assertFalse(атомнаяЛодка.движется());
        атомнаяЛодка.двигаться();
        assertTrue(атомнаяЛодка.движется());
        атомнаяЛодка.остановиться();
        assertFalse(атомнаяЛодка.движется());
        атомнаяЛодка.выключить();
        assertFalse(атомнаяЛодка.движется());
    }

}