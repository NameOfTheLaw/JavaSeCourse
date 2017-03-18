package javase.unit7.task1;

import java.util.Objects;

public class Transfer {
    private Account accountFrom;
    private Account accountTo;
    private int sum;

    public Transfer(Account accountFrom, Account accountTo, int sum) {
        Objects.requireNonNull(accountFrom);
        Objects.requireNonNull(accountTo);

        validateTransfer(accountFrom, accountTo, sum);

        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.sum = sum;
    }

    private void validateTransfer(Account accountFrom, Account accountTo, int sum) {
        if (accountFrom.equals(accountTo)) {
            throw new IllegalTransferException(
                    String.format("Accounts %d must be different.", accountFrom.getId()));
        }
        if (sum <= 0) {
            throw new IllegalTransferException(
                    String.format("Sum %d should be bigger than 0.", sum));
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transfer transfer = (Transfer) o;

        if (sum != transfer.sum) return false;
        if (accountFrom != null ? !accountFrom.equals(transfer.accountFrom) : transfer.accountFrom != null)
            return false;
        return accountTo != null ? accountTo.equals(transfer.accountTo) : transfer.accountTo == null;
    }

    @Override
    public int hashCode() {
        int result = accountFrom != null ? accountFrom.hashCode() : 0;
        result = 31 * result + (accountTo != null ? accountTo.hashCode() : 0);
        result = 31 * result + sum;
        return result;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                ", sum=" + sum +
                '}';
    }
}
