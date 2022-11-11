package Database;

import Model.Transaction;
import DatabaseBoundary.*;

/**
 * A class for interfacing Transaction database
 */
public class TransactionDB {
    private Transaction[] transactions;

    /**
     * Gets the transaction array
     * @return
     */
    public Transaction[] getTransactions() {
        return this.transactions;
    }

    /**
     * Adds a new transaction
     * @param transaction A Transaction object representing the new transaction
     */
    public static void addNewTransaction(Transaction transaction) {
        DatabaseWriter.addNewTransaction(transaction);
    }

    public TransactionDB() {
        transactions = DatabaseReader.readTransactionDatabase();
    }
}
