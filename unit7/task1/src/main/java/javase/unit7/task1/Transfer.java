package javase.unit7.task1;

import java.util.Objects;

public class Transfer {
    private Account accountFrom;
    private Account accountTo;
    private int sum;

    public Transfer(Account accountFrom, Account accountTo, int sum) {
        Objects.requireNonNull(accountFrom);
        Objects.requireNonNull(accountTo);
        if (accountFrom.equals(accountTo)) {
            throw new IllegalTransferException(
                    String.format("Accounts %d must be different.", accountFrom.getId()));
        }
        if (sum <= 0) {
            throw new IllegalTransferException(
                    String.format("Sum %d should be bigger than 0.", sum));
        }

        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.sum = sum;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public int getSum() {
        return sum;
    }
}
