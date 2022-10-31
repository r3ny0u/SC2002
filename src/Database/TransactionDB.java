package Database;

import Model.Transaction;
import DatabaseBoundary.*;

public class TransactionDB {
    private Transaction[] transactions;

    public Transaction[] getTransactions() {
        return this.transactions;
    }

    public static void addNewTransaction(Transaction transaction) {
        DatabaseWriter.addNewTransaction(transaction);
    }

    public TransactionDB() {
        transactions = DatabaseReader.readTransactionDatabase();
    }

    public static void printTransactionDBDetails() {
        for (Transaction transaction : new TransactionDB().getTransactions()) {
            transaction.printTransactionDetails();
            System.out.println("\n");
        }
    }
}
