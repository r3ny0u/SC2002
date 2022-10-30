package DatabaseBoundary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import Database.*;
import Model.*;

/**
 * Returns the different types of database.
 */
public class DatabaseReader {
    private static String cineplexDatabasePath = DatabaseConstants.CINEPLEX_DATABASE_PATH;
    private static String adminDatabasePath = DatabaseConstants.ADMIN_DATABASE_PATH;
    private static String customerDatabasePath = DatabaseConstants.CUSTOMER_DATABASE_PATH;
    private static String movieDatabasePath = DatabaseConstants.MOVIE_DATABASE_PATH;
    private static String ratingDatabasePath = DatabaseConstants.RATING_DATABASE_PATH;
    private static String transactionDatabasePath = DatabaseConstants.TRANSACTION_DATABASE_PATH;

    /**
     * Returns all lines in a txt file.
     * 
     * @param txtPath The path to the txt file
     * @return An array list of strings
     */
    public static ArrayList<String> readtxt(String txtPath) {
        try {
            // Read array list of strings from txt file
            String s;
            ArrayList<String> strings = new ArrayList<String>();
            FileReader reader = new FileReader(txtPath);
            BufferedReader bufferedReader = new BufferedReader(reader);
            while ((s = bufferedReader.readLine()) != null) {
                strings.add(s);
            }

            bufferedReader.close();

            return strings;

        } catch (Exception e) {
            // If somehow got error, return empty array list
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }

    public static Movie[] readMovieDatabase() {
        ArrayList<String> strings = readtxt(movieDatabasePath);

        // MovieDB text file has only five information per movie
        // Title, Showing Status, Synopsis, Director, Cast
        // Ratings and reviews err that one, i think can just get from the ratings DB
        int numOfMovies = strings.size() / 5;
        Movie[] movies = new Movie[numOfMovies];
        Movie temp;
        for (int i = 0; i < movies.length; i++) {
            temp = new Movie(strings.get(i * 5 + 0), strings.get(i * 5 + 1), strings.get(i * 5 + 2),
                    strings.get(i * 5 + 3),
                    new ArrayList<String>(Arrays.asList(strings.get(i * 5 + 4).split(","))));
            movies[i] = temp;
        }
        return movies;
    }

    public static Admin[] readAdminDatabase() {
        ArrayList<String> strings = readtxt(adminDatabasePath);

        // MovieDB text file has only three information per movie
        // Username, Password, accountID
        int numOfAccounts = strings.size() / 3;
        Admin[] adminAccounts = new Admin[numOfAccounts];
        Admin temp;
        for (int i = 0; i < adminAccounts.length; i++) {
            temp = new Admin(strings.get(i * 3 + 0), strings.get(i * 3 + 1), strings.get(i * 3 + 2));
            adminAccounts[i] = temp;
        }
        return adminAccounts;
    }

    public static Customer[] readCustomerDatabase() {
        ArrayList<String> strings = readtxt(customerDatabasePath);

        // MovieDB text file has only three information per movie
        // Username, Password, accountID
        int numOfAccounts = strings.size() / 3;
        Customer[] customerAccounts = new Customer[numOfAccounts];
        Customer temp;
        for (int i = 0; i < customerAccounts.length; i++) {
            temp = new Customer(strings.get(i * 3 + 0), strings.get(i * 3 + 1), strings.get(i * 3 + 2));
            customerAccounts[i] = temp;
        }
        return customerAccounts;
    }
}
