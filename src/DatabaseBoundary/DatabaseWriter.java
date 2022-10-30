package DatabaseBoundary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class DatabaseWriter {
    private static String cineplexDatabasePath = DatabaseConstants.CINEPLEX_DATABASE_PATH;
    private static String adminDatabasePath = DatabaseConstants.ADMIN_DATABASE_PATH;
    private static String customerDatabasePath = DatabaseConstants.CUSTOMER_DATABASE_PATH;
    private static String movieDatabasePath = DatabaseConstants.MOVIE_DATABASE_PATH;
    private static String ratingDatabasePath = DatabaseConstants.RATING_DATABASE_PATH;
    private static String transactionDatabasePath = DatabaseConstants.TRANSACTION_DATABASE_PATH;

    public static void addNewMovieToDatabase() {
        try {
            FileWriter writer = new FileWriter(movieDatabasePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            // I'm not to sure whether want to put the query part here or put it into
            // another method
            Scanner sc = new Scanner(System.in);
            String title, synopsis, director, cast;
            ArrayList<String> casts = new ArrayList<String>();

            // Asking time!!
            System.out.println("Adding new movie...\n");

            System.out.println("Title: ");
            title = sc.nextLine();

            System.out.println("Synopsis: ");
            synopsis = sc.nextLine();

            System.out.println("Director: ");
            director = sc.nextLine();

            System.out.println("Casts (enter exit to complete entry): ");

            while (true) {
                cast = sc.nextLine();
                if (cast.toLowerCase().compareTo("exit") == 0)
                    break;
                casts.add(cast);
            }

            bufferedWriter.write(title + "\n");
            bufferedWriter.write("Not showing");
            bufferedWriter.write(synopsis + "\n");
            bufferedWriter.write(director + "\n");
            bufferedWriter.write(String.join(",", casts) + "\n");

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addNewAdminAccount() {
        try {
            FileWriter writer = new FileWriter(adminDatabasePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            // I'm not to sure whether want to put the query part here or put it into
            // another method
            Scanner sc = new Scanner(System.in);
            String username, password, confirmPassword;

            // Asking time!!
            System.out.println("Adding new admin account...\n");

            System.out.println("Username: ");
            username = sc.nextLine();

            System.out.println("Password: ");
            password = sc.nextLine();

            do {
                System.out.println("Confirm password: ");
                confirmPassword = sc.nextLine();
            } while (confirmPassword.compareTo(password) != 0);

            bufferedWriter.write(username + "\n");
            bufferedWriter.write(password + "\n");
            bufferedWriter.write(UUID.randomUUID().toString().replace("-", "") + "\n");

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addNewCustomerAccount() {
        try {
            FileWriter writer = new FileWriter(customerDatabasePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            // I'm not to sure whether want to put the query part here or put it into
            // another method
            Scanner sc = new Scanner(System.in);
            String username, password, confirmPassword;

            // Asking time!!
            System.out.println("Adding new customer account...\n");

            System.out.println("Username: ");
            username = sc.nextLine();

            System.out.println("Password: ");
            password = sc.nextLine();

            do {
                System.out.println("Confirm password: ");
                confirmPassword = sc.nextLine();
            } while (confirmPassword.compareTo(password) != 0);

            bufferedWriter.write(username + "\n");
            bufferedWriter.write(password + "\n");
            bufferedWriter.write(UUID.randomUUID().toString().replace("-", "") + "\n");

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // addNewMovieToDatabase();
        // addNewAdminAccount();
        addNewCustomerAccount();
    }
}
