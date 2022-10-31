package Database;

/**
 * Tests whether can read from database and print out correctly or not
 */
public class DatabaseTest_1 {
    public static void main(String[] args) {
        System.out.println("Admin Database Details: ");
        AdminDB.printAdminDBDetails();

        System.out.println("Customer Database Details: ");
        CustomerDB.printCustomerDBDetails();

        System.out.println("Cineplex Database Details: ");
        CineplexDB.printCineplexDBDetails();

        System.out.println("Cinema Database Details: ");
        CinemaDB.printCinemaDBDetails();

        System.out.println("Movie Database Details: ");
        MovieDB.printMovieDBDetails();

        System.out.println("Rating Database Details: ");
        RatingDB.printRatingDBDetails();

        System.out.println("Transaction Database Details: ");
        TransactionDB.printTransactionDBDetails();
    }
}
