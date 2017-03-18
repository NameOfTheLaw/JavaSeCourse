package javase.unit7.task1;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class TransfersLoaderTest {

    TransfersLoader loader;
    Path testPath = Paths.get("testData\\testTransfers.txt");
    List<Transfer> expectedTransfers;

    @Before
    public void setUp() {
        expectedTransfers = new ArrayList<>();
        expectedTransfers.add(new Transfer(new Account(1), new Account(2), 100));
        expectedTransfers.add(new Transfer(new Account(2), new Account(1), 200));
        expectedTransfers.add(new Transfer(new Account(2), new Account(3), 300));
        expectedTransfers.add(new Transfer(new Account(3), new Account(1), 400));
    }

    @Test(expected = FileNotFoundException.class)
    public abstract void testCreateLoaderFromNonExistingFile();

    @Test(expected = NotDirectoryException.class)
    public abstract void testCreateLoaderFromNotAFile();

    @Test(expected = BadTransferFormatException.class)
    public abstract void testCreateLoaderIfFileHasBadFormattedTransfers();

    @Test(expected = IllegalTransferException.class)
    public abstract void testCreateLoaderIfFileHasIllegalTransfers();

    @Test
    public void testGet() {
        List<Transfer> resultedTransfers = loader.get();

        assertEquals(expectedTransfers, resultedTransfers);
    }
}
