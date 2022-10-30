package Database;

import Model.Transaction;
import DatabaseBoundary.*;

public class TransactionDB {
    private Transaction[] transactions;

    public Transaction[] getTransactions() {
        return this.transactions;
    }

    public TransactionDB() {
        transactions = DatabaseReader.readTransactionDatabase();
    }

    public static void main(String[] args) {
        for (Transaction transaction : new TransactionDB().getTransactions()) {
            System.out.println(transaction.toString());
        }
    }
}
