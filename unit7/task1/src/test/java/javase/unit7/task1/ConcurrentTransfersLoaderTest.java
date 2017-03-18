package javase.unit7.task1;

import java.io.IOException;
import java.nio.file.Paths;

public class ConcurrentTransfersLoaderTest extends TransfersLoaderTest {

    public void setUp() throws IOException {
        super.setUp();
        loader = new ConcurrentTransfersLoader(testPath);
    }

    public void testCreateLoaderFromNonExistingFile() throws IOException {
        loader = new ConcurrentTransfersLoader(Paths.get("testData\\nonExistingFile"));
    }

    public void testCreateLoaderFromNotAFile() throws IOException {
        loader = new ConcurrentTransfersLoader(Paths.get("testData"));
    }

    public void testCreateLoaderIfFileHasBadFormattedTransfers() throws IOException {
        loader = new ConcurrentTransfersLoader(Paths.get("testData\\testTransfersWithBadFormat.txt"));
    }

    public void testCreateLoaderIfFileHasIllegalTransfers() throws IOException {
        loader = new ConcurrentTransfersLoader(Paths.get("testData\\testTransfersWithIllegalTransfers.txt"));
    }
}
