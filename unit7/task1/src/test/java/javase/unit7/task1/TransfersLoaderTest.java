package javase.unit7.task1;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class TransfersLoaderTest {

    TransfersLoader loader;
    Path testPath = Paths.get("testData\\testTransfers.txt");
    List<Transfer> expectedTransfers;

    @Before
    public void setUp() throws FileNotFoundException, NotDirectoryException {
        expectedTransfers = new ArrayList<>();
        expectedTransfers.add(new Transfer(new Account(1), new Account(2), 100));
        expectedTransfers.add(new Transfer(new Account(2), new Account(1), 200));
        expectedTransfers.add(new Transfer(new Account(2), new Account(3), 300));
        expectedTransfers.add(new Transfer(new Account(3), new Account(1), 400));
    }

    @Test(expected = FileNotFoundException.class)
    public abstract void testCreateLoaderFromNonExistingFile() throws FileNotFoundException, NotDirectoryException;

    @Test(expected = NotDirectoryException.class)
    public abstract void testCreateLoaderFromNotAFile() throws FileNotFoundException, NotDirectoryException;

    @Test(expected = BadTransferFormatException.class)
    public abstract void testCreateLoaderIfFileHasBadFormattedTransfers() throws FileNotFoundException, NotDirectoryException;

    @Test(expected = IllegalTransferException.class)
    public abstract void testCreateLoaderIfFileHasIllegalTransfers() throws FileNotFoundException, NotDirectoryException;

    @Test
    public void testGet() {
        List<Transfer> resultedTransfers = loader.get();

        expectedTransfers.sort(Comparator.comparing(Transfer::getSum));
        resultedTransfers.sort(Comparator.comparing(Transfer::getSum));

        assertEquals(expectedTransfers, resultedTransfers);
    }
}
