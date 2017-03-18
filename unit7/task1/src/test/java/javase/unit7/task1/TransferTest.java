package javase.unit7.task1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransferTest {

    private Account account1;
    private Account account2;
    private int transferSum;

    @Before
    public void setUp() {
        account1 = new Account(1);
        account2 = new Account(2);
        transferSum = 100;
    }

    @Test
    public void testCreateTransfer() {
        Transfer transfer = new Transfer(account1, account2, transferSum);
        assertEquals(account1, transfer.getAccountFrom());
        assertEquals(account2, transfer.getAccountTo());
        assertEquals(transferSum, transfer.getSum());
    }

    @Test(expected = NullPointerException.class)
    public void testCreateTransferIfAccount1IsNull() {
        Transfer transfer = new Transfer(null, account2, transferSum);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateTransferIfAccount2IsNull() {
        Transfer transfer = new Transfer(account1, null, transferSum);
    }

    @Test(expected = IllegalTransferException.class)
    public void testCreateTransferIfAccountsIsTheSame() {
        Transfer transfer = new Transfer(account1, account1, transferSum);
    }

    @Test(expected = IllegalTransferException.class)
    public void testCreateTransferIfTransferSumIsZero() {
        Transfer transfer = new Transfer(account1, account2, 0);
    }

    @Test(expected = IllegalTransferException.class)
    public void testCreateTransferIfTransferSumIsBelowZero() {
        Transfer transfer = new Transfer(account1, account2, -1);
    }
}
