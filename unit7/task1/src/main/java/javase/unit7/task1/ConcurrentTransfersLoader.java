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
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public ConcurrentTransfersLoader(Path path) throws FileNotFoundException, NotDirectoryException {
        Objects.requireNonNull(path);
        if (!path.toFile().exists()) {
            throw new FileNotFoundException();
        }
        if (path.toFile().isDirectory()) {
            throw new NotDirectoryException(
                    String.format("%s is a directory.", path.getFileName()));
        }

        List<Future<Transfer>> futureBuffer = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Future<Transfer> transferFuture = executorService.submit(
                        new TransferFormer(line));
                futureBuffer.add(transferFuture);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!futureBuffer.isEmpty()) {
            for (ListIterator<Future<Transfer>> it = futureBuffer.listIterator(); it.hasNext(); ) {
                Future<Transfer> transferFuture = it.next();
                if (transferFuture.isDone()) {
                    try {
                        transfers.add(transferFuture.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        throw (RuntimeException) e.getCause();
                    } finally {
                        it.remove();
                    }
                }
            }
        }
    }

    @Override
    public List<Transfer> get() {
        return transfers;
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
                return new Transfer(
                        new Account(Integer.valueOf(matcher.group(1))),
                        new Account(Integer.valueOf(matcher.group(2))),
                        Integer.valueOf(matcher.group(3)));
            } else {
                throw new BadTransferFormatException(
                        String.format("Bad format: %s. Should be %s.", input, inputPattern.pattern()));
            }
        }
    }
}
