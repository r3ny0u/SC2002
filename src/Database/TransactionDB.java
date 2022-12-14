package Database;

import Model.Transaction;
import DatabaseBoundary.*;

/**
 * A class for interfacing Transaction database
 */
public class TransactionDB {
    /** An array of Transaction objects */
    private Transaction[] transactions;

    /**
     * Gets the transaction array
     * 
     * @return An array of Transaction objects
     */
    public Transaction[] getTransactions() {
        return this.transactions;
    }

    /**
     * Adds a new transaction
     * 
     * @param transaction A Transaction object representing the new transaction
     */
    public static void addNewTransaction(Transaction transaction) {
        DatabaseWriter.addNewTransaction(transaction);
    }

    /** Constructor */
    public TransactionDB() {
        transactions = DatabaseReader.readTransactionDatabase();
    }
}
