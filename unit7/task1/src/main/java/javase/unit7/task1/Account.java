package javase.unit7.task1;

/**
 * Bank account.
 */
public class Account {
    private final int id;

    /**
     * Constructor.
     *
     * @param id
     */
    public Account(int id) {
        this.id = id;
    }

    /**
     * Returns account id.
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return id == account.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                '}';
    }
}
