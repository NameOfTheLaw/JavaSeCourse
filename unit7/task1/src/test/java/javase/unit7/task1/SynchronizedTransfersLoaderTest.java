package javase.unit7.task1;

import java.io.IOException;
import java.nio.file.Paths;

public class SynchronizedTransfersLoaderTest extends TransfersLoaderTest {

    public void setUp() throws IOException {
        super.setUp();
        loader = new SynchronizedTransfersLoader(testPath);
    }

    public void testCreateLoaderFromNonExistingFile() throws IOException {
        loader = new SynchronizedTransfersLoader(Paths.get("testData\\nonExistingFile"));
    }

    public void testCreateLoaderFromNotAFile() throws IOException {
        loader = new SynchronizedTransfersLoader(Paths.get("testData"));
    }

    public void testCreateLoaderIfFileHasBadFormattedTransfers() throws IOException {
        loader = new SynchronizedTransfersLoader(Paths.get("testData\\testTransfersWithBadFormat.txt"));
    }

    public void testCreateLoaderIfFileHasIllegalTransfers() throws IOException {
        loader = new SynchronizedTransfersLoader(Paths.get("testData\\testTransfersWithIllegalTransfers.txt"));
    }
}
