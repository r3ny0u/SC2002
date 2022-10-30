package DatabaseBoundary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import Database.AdminDB;
import Database.CustomerDB;
import Database.MovieDB;
import Model.Transaction;

public class DatabaseWriter {
    private static String cineplexDatabasePath = DatabaseConstants.CINEPLEX_DATABASE_PATH;
    private static String adminDatabasePath = DatabaseConstants.ADMIN_DATABASE_PATH;
    private static String customerDatabasePath = DatabaseConstants.CUSTOMER_DATABASE_PATH;
    private static String movieDatabasePath = DatabaseConstants.MOVIE_DATABASE_PATH;
    private static String ratingDatabasePath = DatabaseConstants.RATING_DATABASE_PATH;
    private static String transactionDatabasePath = DatabaseConstants.TRANSACTION_DATABASE_PATH;

    public static void addNewMovie() {
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
            bufferedWriter.write("Not showing\n");
            bufferedWriter.write(synopsis + "\n");
            bufferedWriter.write(director + "\n");
            bufferedWriter.write(String.join(",", casts) + "\n");

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeMovie() {
        String title;

        Scanner sc = new Scanner(System.in);
        System.out.println("Removing movie...\n");
        System.out.println("Please enter title: ");
        title = sc.nextLine();

        if (MovieDB.getMovieFromTitle(title) == null) {
            System.out.println("Oops movie does not exist :(");
            return;
        }

        // Remove account from txt file idk how, can just rewrite the entire file
        ArrayList<String> movies = DatabaseReader.readtxt(movieDatabasePath);
        int lineNo = 0;
        for (String string : movies) {
            if (string.toLowerCase().strip().compareTo(title.toLowerCase().strip()) == 0) {
                break;
            }
            lineNo++;
        }

        try {
            FileWriter writer = new FileWriter(movieDatabasePath);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (int i = 0; i < movies.size() - 1; i++) {
                if (lineNo <= i && i < lineNo + 5) {
                    continue;
                }
                bufferedWriter.write(movies.get(i) + "\n");
            }

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

    public static void removeAdminAccount() {
        String username, password;
        Scanner sc = new Scanner(System.in);
        System.out.println("Removing account...\n");
        System.out.println("Please enter username: ");
        username = sc.nextLine();
        System.out.println("Please enter password: ");
        password = sc.nextLine();

        if (AdminDB.getAdminFromUsername(username, password) == null) {
            System.out.println("Oops username or password is incorrect :(");
            return;
        }

        // Remove account from txt file idk how, can just rewrite the entire file
        ArrayList<String> admins = DatabaseReader.readtxt(adminDatabasePath);
        int lineNo = 0;
        for (String string : admins) {
            if (string.compareTo(username) == 0) {
                break;
            }
            lineNo++;
        }

        try {
            FileWriter writer = new FileWriter(adminDatabasePath);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (int i = 0; i < admins.size() - 1; i++) {
                if (lineNo <= i && i < lineNo + 3) {
                    continue;
                }
                bufferedWriter.write(admins.get(i) + "\n");
            }

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
            String username, password, confirmPassword, email, mobile;

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

            System.out.println("Email: ");
            email = sc.nextLine();

            System.out.println("Mobile: ");
            mobile = sc.nextLine();

            bufferedWriter.write(username + "\n");
            bufferedWriter.write(password + "\n");
            bufferedWriter.write(UUID.randomUUID().toString().replace("-", "") + "\n");
            bufferedWriter.write(email + "\n");
            bufferedWriter.write(mobile + "\n");

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeCustomerAccount() {
        String username, password;
        Scanner sc = new Scanner(System.in);
        System.out.println("Removing account...\n");
        System.out.println("Please enter username: ");
        username = sc.nextLine();
        System.out.println("Please enter password: ");
        password = sc.nextLine();

        if (CustomerDB.getCustomerFromUsername(username, password) == null) {
            System.out.println("Oops username or password is incorrect :(");
            return;
        }

        // Remove account from txt file idk how, can just rewrite the entire file
        ArrayList<String> customers = DatabaseReader.readtxt(customerDatabasePath);
        int lineNo = 0;
        for (String string : customers) {
            if (string.compareTo(username) == 0) {
                break;
            }
            lineNo++;
        }

        try {
            FileWriter writer = new FileWriter(customerDatabasePath);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (int i = 0; i < customers.size() - 1; i++) {
                if (lineNo <= i && i < lineNo + 5) {
                    continue;
                }
                bufferedWriter.write(customers.get(i) + "\n");
            }

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addNewTransaction(Transaction transaction) {
        try {
            FileWriter writer = new FileWriter(transactionDatabasePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(transaction.getTransactionId() + "\n");
            bufferedWriter.write(transaction.getTime() + "\n");
            bufferedWriter.write(transaction.getAge() + "\n");
            bufferedWriter.write(transaction.getCinemaType() + "\n");
            bufferedWriter.write(transaction.getMovieType() + "\n");
            bufferedWriter.write(transaction.getDayOfWeek() + "\n");
            bufferedWriter.write(transaction.getMovie().getTitle() + "\n");
            bufferedWriter.write(transaction.getCustomerID() + "\n");

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addNewRanking() {
        // I lazy
    }

    public static void addNewCineplex() {
        // No i lazy
    }


    public static void main(String[] args) {
        // addNewMovie();
        removeMovie();
    }
}
