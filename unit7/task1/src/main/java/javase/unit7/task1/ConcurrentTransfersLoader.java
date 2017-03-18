package javase.unit7.task1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConcurrentTransfersLoader implements TransfersLoader {

    private List<Transfer> transfers = new ArrayList<>();

    public ConcurrentTransfersLoader(Path path) throws IOException {
        Objects.requireNonNull(path);

        validatePath(path);

        List<Future<Transfer>> futureBuffer = getTransferFutures(path);

        while (!futureBuffer.isEmpty()) {
            transfers.addAll(getFinishedTransfers(futureBuffer));
        }
    }

    @Override
    public List<Transfer> get() {
        return transfers;
    }

    private List<Transfer> getFinishedTransfers(List<Future<Transfer>> futureBuffer) {
        List<Transfer> transfers = new ArrayList<>();

        for (ListIterator<Future<Transfer>> it = futureBuffer.listIterator(); it.hasNext(); ) {
            Future<Transfer> transferFuture = it.next();
            if (transferFuture.isDone()) {
                try {
                    transfers.add(transferFuture.get());
                } catch (ExecutionException e) {
                    throw (RuntimeException) e.getCause();
                } catch (InterruptedException e) {
                    //ignore
                } finally {
                    it.remove();
                }
            }
        }
        return transfers;
    }

    private List<Future<Transfer>> getTransferFutures(Path path) throws IOException {
        List<Future<Transfer>> futureBuffer = new ArrayList<>();

        ExecutorService executorService = Executors.newCachedThreadPool();

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Future<Transfer> transferFuture = executorService.submit(new TransferFormer(line));
                futureBuffer.add(transferFuture);
            }
        }

        return futureBuffer;
    }

    private void validatePath(Path path) throws FileNotFoundException, NotDirectoryException {
        if (!path.toFile().exists()) {
            throw new FileNotFoundException(
                    String.format("File %s was not found.", path.getFileName()));
        }

        if (path.toFile().isDirectory()) {
            throw new NotDirectoryException(
                    String.format("%s is a directory.", path.getFileName()));
        }
    }

    private static class TransferFormer implements Callable<Transfer> {

        private static final Pattern inputPattern = Pattern.compile("#(\\d+) #(\\d+) (\\d+)");
        private final String input;

        public TransferFormer(String input) {
            this.input = input;
        }

        @Override
        public Transfer call() {
            Matcher matcher = inputPattern.matcher(input.trim());

            if (matcher.matches()) {
                return getTransfer(matcher);
            } else {
                throw new BadTransferFormatException(
                        String.format("Bad format: %s. Should be %s.", input, inputPattern.pattern()));
            }
        }

        private Transfer getTransfer(Matcher matcher) {
            return new Transfer(
                    new Account(Integer.valueOf(matcher.group(1))),
                    new Account(Integer.valueOf(matcher.group(2))),
                    Integer.valueOf(matcher.group(3)));
        }
    }
}
