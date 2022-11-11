package DatabaseBoundary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Database.*;
import Model.*;

/**
 * A class for reading database txt files
 */
public class DatabaseReader {
    private static final String cineplexDatabasePath = DatabaseConstants.CINEPLEX_DATABASE_PATH;
    private static final String cinemaDatabasePath = DatabaseConstants.CINEMA_DATABASE_PATH;
    private static final String adminDatabasePath = DatabaseConstants.ADMIN_DATABASE_PATH;
    private static final String customerDatabasePath = DatabaseConstants.CUSTOMER_DATABASE_PATH;
    private static final String movieDatabasePath = DatabaseConstants.MOVIE_DATABASE_PATH;
    private static final String ratingDatabasePath = DatabaseConstants.RATING_DATABASE_PATH;
    private static final String transactionDatabasePath = DatabaseConstants.TRANSACTION_DATABASE_PATH;
    private static final String showtimesDatabasePath = DatabaseConstants.SHOWTIMES_DATABASE_PATH;
    private static final String rankingVisibilityDatabasePath = DatabaseConstants.RANKING_VISIBILITY_DATABASE_PATH;

    /**
     * Returns all lines in a txt file.
     * 
     * @param txtPath The path to the txt file
     * @return An array list of strings
     */
    public static ArrayList<String> readtxt(String txtPath) {
        while (true) {
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

            } catch (FileNotFoundException e) {
                try {
                    File bla = new File(txtPath);
                    bla.getParentFile().mkdirs();
                    bla.createNewFile();
                    FileWriter writer = new FileWriter(bla);
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);
                    bufferedWriter.write("");
                    bufferedWriter.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return new ArrayList<String>();
                }
            }

            catch (Exception e) {
                // If somehow got error, return empty array list
                e.printStackTrace();
                return new ArrayList<String>();
            }
        }
    }

    public static String readRankingVisibilityDatabase() {
        ArrayList<String> strings = readtxt(rankingVisibilityDatabasePath);
        return strings.get(0);
    }

    /**
     * Reads the movie database
     * 
     * @return An array of Movie
     * @see Model.Movie
     */
    public static Movie[] readMovieDatabase() {
        ArrayList<String> strings = readtxt(movieDatabasePath);

        // MovieDB text file has only five information per movie
        // Title, Showing Status, Synopsis, Director, Cast
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

    /**
     * Reads the admin database
     * 
     * @return An array of Admin
     * @see Model.Admin
     */
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

    /**
     * Reads the customer database
     * 
     * @return An array of Customer
     * @see Model.Customer
     */
    public static Customer[] readCustomerDatabase() {
        ArrayList<String> strings = readtxt(customerDatabasePath);

        // CustomerDB text file has only five information per account
        // Username, Password, accountID, email, mobile
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

    /**
     * Reads the transaction database
     * 
     * @return An array of Transaction
     * @see Model.Transaction
     */
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

    /**
     * Reads the cineplex database
     * 
     * @return An array of Cineplex
     * @see Model.Cineplex
     */

    /**
     * Reads the rating database
     * 
     * @return An array of Rating
     * @see Model.Rating
     */
    public static Rating[] readRatingDatabase() {
        ArrayList<String> strings = readtxt(ratingDatabasePath);

        // Rating text file has only five information per movie
        // movie title, rating, review, ratingID, username
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

    /**
     * Reads the cineplex database
     * 
     * @return An array of cineplex
     * @see Model.Cineplex
     */
    public static Cineplex[] readCineplexDatabase2() {
        ArrayList<String> strings = readtxt(cineplexDatabasePath);

        int numOfCineplex = strings.size() / 2;
        Cineplex[] cineplexes = new Cineplex[numOfCineplex];
        Cineplex tempCineplex;
        ArrayList<Cinema> cinemas;

        // Contains CineplexID, and cinemaIDs
        for (int i = 0; i < cineplexes.length; i++) {
            cinemas = new ArrayList<Cinema>();
            for (String cinemaID : strings.get(i * 2 + 1).split(",")) {
                cinemas.add(CinemaDB.getCinemaFromID(cinemaID));
            }
            tempCineplex = new Cineplex(strings.get(i * 2 + 0), cinemas);
            cineplexes[i] = tempCineplex;
        }

        return cineplexes;
    }

    /**
     * Reads the cinema database
     * 
     * @return An array of Cinema
     * @see Model.Cinema
     */
    public static Cinema[] readCinemaDatabase() {
        ArrayList<String> strings = readtxt(cinemaDatabasePath);

        int numOfCinemas = strings.size() / 2;
        Cinema[] cinemas = new Cinema[numOfCinemas];

        // Contains CineplexID, and cinemaIDs
        for (int i = 0; i < cinemas.length; i++) {
            cinemas[i] = new Cinema(strings.get(i * 2 + 0), strings.get(i * 2 + 1));
        }

        return cinemas;
    }

    /**
     * Reads the showtime database, considers only specific movie titles
     * 
     * @param movieTitle A String representing the movie title to be considered
     * @return A HashMap that contains, a HashMap that contains Cineplex ID String
     *         to Cinema ID String pairs, to a Hashmap that contains, a Cinema ID
     *         String, to a Hashmap that contains a Showtime object to Seat object
     *         array pairs pairs pairs
     * @see Model.Showtime
     */
    public static Map<Map<String, ArrayList<String>>, Map<String, Map<Showtime, Seat[]>>> readShowtime(
            String movieTitle) {
        // IDK
        ArrayList<String> strings = readtxt(showtimesDatabasePath);
        int numOfShowtimes = strings.size();
        String cinemaID;
        Map<Map<String, ArrayList<String>>, Map<String, Map<Showtime, Seat[]>>> result = new HashMap<Map<String, ArrayList<String>>, Map<String, Map<Showtime, Seat[]>>>();

        // Cineplexes -> Cinemas
        ArrayList<String> cinemas = new ArrayList<String>();
        Map<String, ArrayList<String>> cineplexesAndCinemas = new HashMap<>();

        for (int i = 0; i < numOfShowtimes; i++) {
            String[] temp = strings.get(i).split(",");
            if (!cinemas.contains(temp[2])) {
                cinemas.add(temp[2]);
            }
            if (temp[0].toLowerCase().compareTo(movieTitle.toLowerCase()) == 0) {
                if (cineplexesAndCinemas.containsKey(temp[1])) {
                    if (!cineplexesAndCinemas.get(temp[1]).contains(temp[2]))
                        cineplexesAndCinemas.get(temp[1]).add(temp[2]);
                } else {
                    ArrayList<String> bla = new ArrayList<String>();
                    bla.add(temp[2]);
                    cineplexesAndCinemas.put(temp[1], bla);
                }
            }
        }

        // Cinemas -> (Showtime -> Seat[])
        Map<String, Map<Showtime, Seat[]>> cinemasAndShowtimes = new HashMap<>();

        for (int i = 0; i < numOfShowtimes; i++) {
            Seat[] seats = new Seat[100];
            String[] temp = strings.get(i).split(",");
            cinemaID = temp[2];
            Map<Showtime, Seat[]> showtimesAndSeat = new HashMap<>();
            if (temp[0].toLowerCase().compareTo(movieTitle.toLowerCase()) == 0) {
                Showtime showtime = new Showtime(temp[3], temp[4], temp[5]);
                for (int j = 0; j < seats.length; j++) {
                    if (temp[6].charAt(j) == '1') {
                        seats[j] = new Seat(String.format("%c%d", (char) (65 + j / 10), (j % 10 + 1)), true);
                    } else {
                        seats[j] = new Seat(String.format("%c%d", (char) (65 + j / 10), (j % 10 + 1)), false);
                    }
                }
                showtimesAndSeat.put(showtime, seats);

                if (cinemasAndShowtimes.containsKey(cinemaID)) {
                    cinemasAndShowtimes.get(cinemaID).put(showtime, seats);
                    continue;
                }
                cinemasAndShowtimes.put(cinemaID, showtimesAndSeat);
            }
        }

        result.put(cineplexesAndCinemas, cinemasAndShowtimes);

        return result;
    }
}
