package javase.unit7.task1;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Paths;

public class SynchronizedTransfersLoaderTest extends TransfersLoaderTest {

    @Before
    public void setUp() {
        super.setUp();
        loader = new SynchronizedTransfersLoader(testPath);
    }

    @Test(expected = FileNotFoundException.class)
    public void testCreateLoaderFromNonExistingFile() {
        loader = new SynchronizedTransfersLoader(Paths.get("testData\\nonExistingFile"));
    }

    @Test(expected = NotDirectoryException.class)
    public void testCreateLoaderFromNotAFile() {
        loader = new SynchronizedTransfersLoader(Paths.get("testData"));
    }

    @Test(expected = BadTransferFormatException.class)
    public void testCreateLoaderIfFileHasBadFormattedTransfers() {
        loader = new SynchronizedTransfersLoader(Paths.get("testData\\testTransfersWithBadFormat.txt"));
    }

    @Test(expected = IllegalTransferException.class)
    public void testCreateLoaderIfFileHasIllegalTransfers() {
        loader = new SynchronizedTransfersLoader(Paths.get("testData\\testTransfersWithIllegalTransfers.txt"));
    }
}
