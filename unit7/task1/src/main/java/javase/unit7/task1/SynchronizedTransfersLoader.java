package javase.unit7.task1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parallel implementation of TransferLoader.
 * <p>
 * Uses synchronized block to forming transfers from file.
 */
public class SynchronizedTransfersLoader extends TransfersLoader {

    private List<Transfer> transfers = new ArrayList<>();
    private RuntimeException thrownByHandlersException;

    /**
     * Constructor.
     *
     * @param path path to the file with formatted transfers.
     * @throws IOException if has no rights to write\read from path.
     * @throws IllegalTransferException if found transfers in file handles
     * illegal data.
     * @throws BadTransferFormatException if transfers in file are represented
     * in bad format.
     */
    public SynchronizedTransfersLoader(Path path) throws IOException {
        super(path);

        List<Thread> threads = startTransferFormersThreads(path);

        waitForAllThreadsToComplete(threads);

        handleFormersExceptions();

    }

    @Override
    public List<Transfer> get() {
        return transfers;
    }

    private void handleFormersExceptions() {
        if (thrownByHandlersException != null) {
            throw thrownByHandlersException;
        }
    }

    private void waitForAllThreadsToComplete(List<Thread> threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Thread> startTransferFormersThreads(Path path) throws IOException {
        List<Thread> threads = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Thread handlingThread = new Thread(new TransferFormer(transfers, line));

                handlingThread.setUncaughtExceptionHandler((thread, throwable) -> {
                    if (throwable instanceof RuntimeException) {
                        thrownByHandlersException = (RuntimeException) throwable;
                    }
                });

                handlingThread.start();

                threads.add(handlingThread);
            }
        }
        return threads;
    }

    private static class TransferFormer implements Runnable {
        private static final Pattern inputPattern = Pattern.compile("#(\\d+) #(\\d+) (\\d+)");
        private final String input;
        private final List<Transfer> transfers;

        public TransferFormer(List<Transfer> transfers, String input) {
            this.transfers = transfers;
            this.input = input;
        }

        @Override
        public void run() {
            Matcher matcher = inputPattern.matcher(input.trim());

            if (matcher.matches()) {
                Transfer transfer = getTransfer(matcher);
                synchronized (transfers) {
                    transfers.add(transfer);
                }
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
