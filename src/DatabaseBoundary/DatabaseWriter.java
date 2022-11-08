package DatabaseBoundary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import Database.AdminDB;
import Database.CinemaDB;
import Database.CineplexDB;
import Database.CustomerDB;
import Database.MovieDB;
import Model.Cinema;
import Model.Movie;
import Model.Seat;
import Model.Showtime;
import Model.Transaction;

/**
 * Class for writing and updating data into database txt files.
 */
public class DatabaseWriter {
    private static final String cineplexDatabasePath = DatabaseConstants.CINEPLEX_DATABASE_PATH;
    private static final String cinemaDatabasePath = DatabaseConstants.CINEMA_DATABASE_PATH;
    private static final String adminDatabasePath = DatabaseConstants.ADMIN_DATABASE_PATH;
    private static final String customerDatabasePath = DatabaseConstants.CUSTOMER_DATABASE_PATH;
    private static final String movieDatabasePath = DatabaseConstants.MOVIE_DATABASE_PATH;
    private static final String ratingDatabasePath = DatabaseConstants.RATING_DATABASE_PATH;
    private static final String transactionDatabasePath = DatabaseConstants.TRANSACTION_DATABASE_PATH;

    /**
     * Adds new Movie into movie database via user inputs
     * 
     * @see Model.Movie
     */
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
            bufferedWriter.write("NOT SHOWING\n");
            bufferedWriter.write(synopsis + "\n");
            bufferedWriter.write(director + "\n");
            bufferedWriter.write(String.join(",", casts) + "\n");

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateMovieDetails(Movie originalMovieObject, String title, String status, String synopsis,
            String director, ArrayList<String> casts) {
        try {
            // Remove movie and add in new movie lo, lol im lazy
            FileWriter writer = new FileWriter(movieDatabasePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            removeMovieByTitle(originalMovieObject.getTitle());

            bufferedWriter.write(title + "\n");
            bufferedWriter.write(status + "\n");
            bufferedWriter.write(synopsis + "\n");
            bufferedWriter.write(director + "\n");
            bufferedWriter.write(String.join(",", casts) + "\n");

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes Movie from movie database via user inputs
     * 
     * @see Model.Movie
     */
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

        // Remove movie from txt file idk how, can just rewrite the entire file
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

            for (int i = 0; i < movies.size(); i++) {
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

    public static void removeMovieByTitle(String title) {
        if (MovieDB.getMovieFromTitle(title) == null) {
            System.out.println("Oops movie does not exist :(");
            return;
        }

        // Remove movie from txt file idk how, can just rewrite the entire file
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

            for (int i = 0; i < movies.size(); i++) {
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

    /**
     * Adds new Admin account into admin database via user inputs
     * 
     * @see Model.Admin
     */
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

            if (AdminDB.isUsernameExist(username) || CustomerDB.isUsernameExist(username)) {
                System.out.println("Oops... Username is used");
                bufferedWriter.close();
                return;
            }

            bufferedWriter.write(username + "\n");
            bufferedWriter.write(password + "\n");
            bufferedWriter.write(UUID.randomUUID().toString().replace("-", "") + "\n");

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes Admin account from admin database via user inputs
     * 
     * @see Model.Admin
     */
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

            for (int i = 0; i < admins.size(); i++) {
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

    /**
     * Adds new Customer account into customer database via user inputs
     * 
     * @see Model.Customer
     */
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

            System.out.print("Username: ");
            username = sc.nextLine();

            System.out.print("Password: ");
            password = sc.nextLine();

            do {
                System.out.print("Confirm password: ");
                confirmPassword = sc.nextLine();
            } while (confirmPassword.compareTo(password) != 0);

            if (AdminDB.isUsernameExist(username) || CustomerDB.isUsernameExist(username)) {
                System.out.println("Oops... Username is used");
                bufferedWriter.close();
                return;
            }

            System.out.print("Email: ");
            email = sc.nextLine();

            System.out.print("Mobile: ");
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

    /**
     * Removes Customer account from customer database via user inputs
     * 
     * @see Model.Customer
     */
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

    /**
     * Adds new Transaction into transaction database
     * 
     * @param transaction a transaction object to be added into transaction database
     * @see Model.Rating
     */
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

    /**
     * Adds new Rating into rating database via user inputs
     * 
     * @param movie    the title of the movie associated with rating
     * @param username the username associated with the rating
     * @see Model.Rating
     */
    public static void addNewRating(String movie, String username) {
        try {
            FileWriter writer = new FileWriter(ratingDatabasePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            // I'm not to sure whether want to put the query part here or put it into
            // another method
            Scanner sc = new Scanner(System.in);
            float rating;
            String review;

            // Asking time!!
            System.out.println("Adding new rating...\n");

            System.out.print("Rating (out of 5): ");
            rating = Float.parseFloat(sc.nextLine());

            System.out.print("Review: ");
            review = sc.nextLine();

            bufferedWriter.write(movie + "\n");
            bufferedWriter.write(Float.toString(rating) + "\n");
            bufferedWriter.write(review + "\n");
            bufferedWriter.write(UUID.randomUUID().toString().replace("-", "") + "\n");
            bufferedWriter.write(username + "\n");

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @deprecated
     *             Removes Rating from rating database
     */
    public static void removeRating() {
        // Should this be implemented idk i lazy
        // d: think dn lah kinda mafan LOL
    }

    /**
     * Adds new Cineplex into cineplex database via user inputs
     * 
     * @see Model.Cineplex
     */
    public static void addNewCineplex() {
        try {
            FileWriter writer = new FileWriter(cineplexDatabasePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            // I'm not to sure whether want to put the query part here or put it into
            // another method
            Scanner sc = new Scanner(System.in);
            String cineplexName;

            // Asking time!!
            System.out.println("Adding new cinplex...\n");

            System.out.println("Cineplex name: ");
            cineplexName = sc.nextLine();

            bufferedWriter.write(cineplexName + "\n");
            bufferedWriter.write(" \n");

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes Cineplex from cineplex database via user inputs
     * 
     * @see Model.Cineplex
     */
    public static void removeCineplex() {
        String cineplexID;
        Scanner sc = new Scanner(System.in);
        System.out.println("Removing cineplex...\n");
        System.out.println("Please enter cineplex ID: ");
        cineplexID = sc.nextLine();

        if (CineplexDB.getCineplexFromID(cineplexID) == null) {
            System.out.println("Oops cineplex does not exist :(");
            return;
        }

        // Remove account from txt file idk how, can just rewrite the entire file
        ArrayList<String> cineplexes = DatabaseReader.readtxt(cineplexDatabasePath);
        int lineNo = 0;
        for (String string : cineplexes) {
            if (string.compareTo(cineplexID) == 0) {
                break;
            }
            lineNo++;
        }

        try {
            FileWriter writer = new FileWriter(cineplexDatabasePath);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (int i = 0; i < cineplexes.size(); i++) {
                if (lineNo <= i && i < lineNo + 2) {
                    continue;
                }
                bufferedWriter.write(cineplexes.get(i) + "\n");
            }

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds new Cinema to Cineplex into cineplex database via user inputs
     * 
     * @see Model.Cineplex
     * @see Model.Cinema
     */
    public static void addNewCinemaToCineplex() {
        String cineplexID, cinemaID;
        Scanner sc = new Scanner(System.in);
        System.out.println("Adding cinema to cineplex...\n");
        System.out.println("Please enter cineplex ID: ");
        cineplexID = sc.nextLine();

        if (CineplexDB.getCineplexFromID(cineplexID) == null) {
            System.out.println("Oops cineplex does not exist :(");
            return;
        }

        System.out.println("Please enter cinema ID: ");
        cinemaID = sc.nextLine();

        if (CinemaDB.getCinemaFromID(cinemaID) == null) {
            System.out.println("Oops cinema does not exist :(");
            return;
        }

        // Remove cinema from cinepelex txt file idk how, can just rewrite the entire
        // file
        // Get which line the cineplex is in, then at the next line add new cinema
        ArrayList<String> cineplexes = DatabaseReader.readtxt(cineplexDatabasePath);
        int lineNo = 0;
        for (String string : cineplexes) {
            if (string.compareTo(cineplexID) == 0) {
                break;
            }
            lineNo++;
        }

        try {
            FileWriter writer = new FileWriter(cineplexDatabasePath);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (int i = 0; i < cineplexes.size(); i++) {
                if (i == lineNo + 1) {
                    bufferedWriter.write(cineplexes.get(i));
                    bufferedWriter.write("," + cinemaID + "\n");
                    System.out.println("here");
                    continue;
                }
                bufferedWriter.write(cineplexes.get(i) + "\n");
            }

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes Cinema from Cineplex from cineplex database via user inputs
     * 
     * @see Model.Cineplex
     * @see Model.Cinema
     */
    public static void removeCinemaFromCineplex() {
        String cineplexID, cinemaID;
        Scanner sc = new Scanner(System.in);
        System.out.println("Removing cinema to cineplex...\n");
        System.out.println("Please enter cineplex ID: ");
        cineplexID = sc.nextLine();

        if (CineplexDB.getCineplexFromID(cineplexID) == null) {
            System.out.println("Oops cineplex does not exist :(");
            return;
        }

        System.out.println("Please enter cinema ID: ");
        cinemaID = sc.nextLine();

        // Check if cinema is in cineplex
        boolean cinemaFound = false;
        for (Cinema cinema : CineplexDB.getCineplexFromID(cineplexID).getCinemas()) {
            if (cinema.getCinemaID().compareTo(cinemaID) == 0) {
                cinemaFound = true;
                break;
            }
        }

        if (!cinemaFound) {
            System.out.println("Oops cinema does not exist :(");
            return;
        }

        // Remove cinema from cinepelex txt file idk how, can just rewrite the entire
        // file
        // Get which line the cineplex is in, then at the next line add remove cinema
        ArrayList<String> cineplexes = DatabaseReader.readtxt(cineplexDatabasePath);
        int lineNo = 0;
        for (String string : cineplexes) {
            if (string.compareTo(cineplexID) == 0) {
                break;
            }
            lineNo++;
        }

        try {
            FileWriter writer = new FileWriter(cineplexDatabasePath);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (int i = 0; i < cineplexes.size(); i++) {
                if (i == lineNo + 1) {
                    String temp = cineplexes.get(i).replace(cinemaID + ",", "").replace("," + cinemaID, "")
                            .replace(cinemaID, "");
                    bufferedWriter.write(temp + "\n");
                    continue;
                }
                bufferedWriter.write(cineplexes.get(i) + "\n");
            }

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds new Cinema into cinema database via user inputs
     * 
     * @see Model.Cinema
     */
    public static void addNewCinema() {
        try {
            FileWriter writer = new FileWriter(cinemaDatabasePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            // I'm not to sure whether want to put the query part here or put it into
            // another method
            Scanner sc = new Scanner(System.in);
            String cinemaID, cinemaType;

            // Asking time!!
            System.out.println("Adding new cinema...\n");

            System.out.println("Cinema ID: ");
            cinemaID = sc.nextLine();

            System.out.println("Cinema type: ");
            cinemaType = sc.nextLine();

            bufferedWriter.write(cinemaID + "\n");
            bufferedWriter.write(cinemaType + "\n");
            bufferedWriter.write("no movie\n");
            bufferedWriter.write("no showtime\n");
            bufferedWriter.write("idk seatws hwo la \n");
            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes Cinema from cinema database via user inputs
     * 
     * @see Model.Cinema
     */
    public static void removeCinema() {
        String cinemaID;
        Scanner sc = new Scanner(System.in);
        System.out.println("Removing cinema...\n");
        System.out.println("Please enter cinema ID: ");
        cinemaID = sc.nextLine();

        if (CinemaDB.getCinemaFromID(cinemaID) == null) {
            System.out.println("Oops cinema does not exist :(");
            return;
        }

        // Remove account from txt file idk how, can just rewrite the entire file
        ArrayList<String> cinemas = DatabaseReader.readtxt(cinemaDatabasePath);
        int lineNo = 0;
        for (String string : cinemas) {
            if (string.compareTo(cinemaID) == 0) {
                break;
            }
            lineNo++;
        }

        try {
            FileWriter writer = new FileWriter(customerDatabasePath);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (int i = 0; i < cinemas.size() - 1; i++) {
                if (lineNo <= i && i < lineNo + 5) {
                    continue;
                }
                bufferedWriter.write(cinemas.get(i) + "\n");
            }

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: Update movie showtimes thing, maybe can move to movie.java idk
    public static void updateShowtimes(String movieTitle, String cineplex, String cinema, Showtime showtime,
            Seat[] seats) {

    }
}
