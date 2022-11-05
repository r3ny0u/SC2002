package DatabaseBoundary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import Database.*;
import Model.*;

/**
 * Returns the different types of database.
 */
public class DatabaseReader {
    private static final String cineplexDatabasePath = DatabaseConstants.CINEPLEX_DATABASE_PATH;
    private static final String cinemaDatabasePath = DatabaseConstants.CINEMA_DATABASE_PATH;
    private static final String adminDatabasePath = DatabaseConstants.ADMIN_DATABASE_PATH;
    private static final String customerDatabasePath = DatabaseConstants.CUSTOMER_DATABASE_PATH;
    private static final String movieDatabasePath = DatabaseConstants.MOVIE_DATABASE_PATH;
    private static final String ratingDatabasePath = DatabaseConstants.RATING_DATABASE_PATH;
    private static final String transactionDatabasePath = DatabaseConstants.TRANSACTION_DATABASE_PATH;

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

        // CInemaDB text file has only three information per account
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

        // CustomerDB text file has only five information per account
        // Username, Password, accountID
        int numOfAccounts = strings.size() / 5;
        Customer[] customerAccounts = new Customer[numOfAccounts];
        Customer temp;
        for (int i = 0; i < customerAccounts.length; i++) {
            temp = new Customer(strings.get(i * 5 + 0), strings.get(i * 5 + 1), strings.get(i * 5 + 2),
                    strings.get(i * 5 + 3), strings.get(i * 5 + 4));
            customerAccounts[i] = temp;
        }
        return customerAccounts;
    }

    public static Transaction[] readTransactionDatabase() {
        ArrayList<String> strings = readtxt(transactionDatabasePath);

        // TransactionDB text file has only eight information per transaction
        // TransactionID, customer ID, time, age, cinema type, movie type, day of week,
        // movie title
        int numOfTransactions = strings.size() / 8;
        Transaction[] transactions = new Transaction[numOfTransactions];
        Transaction temp;
        for (int i = 0; i < transactions.length; i++) {
            temp = new Transaction(strings.get(i * 8 + 0), strings.get(i * 8 + 1), strings.get(i * 8 + 2),
                    strings.get(i * 8 + 3), strings.get(i * 8 + 4), strings.get(i * 8 + 5),
                    MovieDB.getMovieFromTitle(strings.get(i * 8 + 6)), strings.get(i * 8 + 7));
            transactions[i] = temp;
        }
        return transactions;
    }

    // public static Cinema[] readCinemaDatabase() {
    // ArrayList<String> strings = readtxt(cinemaDatabasePath);

    // // CInemaDB text file has only three information per account
    // // Username, Password, accountID
    // int numOfCinemas = strings.size() / 5;
    // Cinema[] cinemas = new Cinema[numOfCinemas];
    // Cinema temp;
    // for (int i = 0; i < cinemas.length; i++) {
    // temp = new Cinema(strings.get(i * 5 + 0), strings.get(i * 5 + 1),
    // strings.get(i * 5 + 2),
    // new ArrayList<String>(Arrays.asList(strings.get(i * 5 + 3).split(","))));
    // cinemas[i] = temp;
    // }
    // return cinemas;
    // }

    public static Cineplex[] readCineplexDatabase() {
        ArrayList<String> strings = readtxt(cineplexDatabasePath);

        // Cineplex text file has only three information per account
        // CineplexID, cinemas
        int numOfCineplex = strings.size() / 2;
        Cineplex[] cineplexes = new Cineplex[numOfCineplex];
        Cineplex temp;

        for (int i = 0; i < cineplexes.length; i++) {
            ArrayList<String> cinemaIDs = new ArrayList<String>(Arrays.asList(strings.get(i * 2 + 1).split(",")));
            ArrayList<Cinema> cinemas = new ArrayList<Cinema>();
            for (String cinemaID : cinemaIDs) {
                Cinema cinema = CinemaDB.getCinemaFromID(cinemaID);
                if (cinema != null)
                    cinemas.add(cinema);
            }

            temp = new Cineplex(strings.get(i * 2 + 0), cinemas);
            cineplexes[i] = temp;
        }
        return cineplexes;
    }

    public static Rating[] readRatingDatabase() {
        ArrayList<String> strings = readtxt(ratingDatabasePath);

        // MovieDB text file has only five information per movie
        // Title, Showing Status, Synopsis, Director, Cast
        // Ratings and reviews err that one, i think can just get from the ratings DB
        int numOfRatings = strings.size() / 5;
        Rating[] ratings = new Rating[numOfRatings];
        Rating temp;
        for (int i = 0; i < ratings.length; i++) {
            temp = new Rating(strings.get(i * 5 + 0), Float.parseFloat(strings.get(i * 5 + 1)), strings.get(i * 5 + 2),
                    strings.get(i * 5 + 3), strings.get(i * 5 + 4));
            ratings[i] = temp;
        }
        return ratings;
    }
}
