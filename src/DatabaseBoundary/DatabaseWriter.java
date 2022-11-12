package DatabaseBoundary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import java.util.Base64;
import java.util.Base64.Encoder;

import Database.AdminDB;
import Database.CinemaDB;
import Database.CineplexDB;
import Database.CustomerDB;
import Database.MovieDB;
import Model.Cinema;
import Model.Movie;
import Model.Seat;
import Model.Transaction;

/**
 * A class for writing and updating data into database txt files.
 */
public class DatabaseWriter {
    private static final String cineplexDatabasePath = DatabaseConstants.CINEPLEX_DATABASE_PATH;
    private static final String cinemaDatabasePath = DatabaseConstants.CINEMA_DATABASE_PATH;
    private static final String adminDatabasePath = DatabaseConstants.ADMIN_DATABASE_PATH;
    private static final String customerDatabasePath = DatabaseConstants.CUSTOMER_DATABASE_PATH;
    private static final String movieDatabasePath = DatabaseConstants.MOVIE_DATABASE_PATH;
    private static final String ratingDatabasePath = DatabaseConstants.RATING_DATABASE_PATH;
    private static final String transactionDatabasePath = DatabaseConstants.TRANSACTION_DATABASE_PATH;
    private static final String showtimesDatabasePath = DatabaseConstants.SHOWTIMES_DATABASE_PATH;
    private static final String rankingVisibilityDatabasePath = DatabaseConstants.RANKING_VISIBILITY_DATABASE_PATH;

    public static void setRankingVisibility(String choice1, String choice2) {
        try {

            FileWriter writer = new FileWriter(rankingVisibilityDatabasePath);
            writer.write("");
            writer.close();

            writer = new FileWriter(rankingVisibilityDatabasePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            String visibility;

            if (choice1.equals("Y") & choice2.equals("Y")) {
                visibility = "1";
            } else if (choice1.equals("Y") & choice2.equals("N")) {
                visibility = "2";
            } else if (choice1.equals("N") & choice2.equals("Y")) {
                visibility = "3";
            } else
                visibility = "4";

            bufferedWriter.write(visibility);
            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds new Movie into movie database via user inputs
     * 
     * @see Model.Movie
     */
    public static void addNewMovie() {
        try {
            FileWriter writer = new FileWriter(movieDatabasePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            Scanner sc = new Scanner(System.in);
            String title, synopsis, director, cast;
            ArrayList<String> casts = new ArrayList<String>();

            System.out.println("Adding new movie...");

            System.out.println("\nTitle: ");
            title = sc.nextLine();

            System.out.println("\nSynopsis: ");
            synopsis = sc.nextLine();

            System.out.println("\nDirector: ");
            director = sc.nextLine();

            System.out.println("\nCasts (enter 'exit' to complete entry): ");

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

    /**
     * Updates the details of a Movie object from the Movie database
     * 
     * @param originalMovieObject A Movie object that details are being updated
     * @param title               A String representing the new title of the movie
     * @param status              A String representing the new status of the movie
     * @param synopsis            A String representing the new synopsis of the
     *                            movie
     * @param director            A String representing the new director of the
     *                            movie
     * @param casts               A String representing the new casts of the movie
     * @see Model.Movie
     */
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
     * Removes Movie from movie database by movie title
     * 
     * @param title A String representing the title of the movie
     * @see Model.Movie
     */
    public static void removeMovieByTitle(String title) {
        if (MovieDB.getMovieFromTitle(title) == null) {
            System.out.println("Oops movie does not exist :(");
            return;
        }

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

            Scanner sc = new Scanner(System.in);
            String username, password, confirmPassword;

            System.out.println("Adding new admin account...\n");

            System.out.println("\nUsername: ");
            username = sc.nextLine();

            System.out.println("\nPassword: ");
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

            Encoder encoder = Base64.getEncoder();
            String encodedPassword = encoder.encodeToString(password.getBytes());

            bufferedWriter.write(username + "\n");
            bufferedWriter.write(encodedPassword + "\n");
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

            Scanner sc = new Scanner(System.in);
            String username, password, confirmPassword, email, mobile;

            System.out.println("Adding new customer account...\n");

            System.out.print("\nUsername: ");
            username = sc.nextLine();

            System.out.print("\nPassword: ");
            password = sc.nextLine();

            do {
                System.out.print("\nConfirm password: ");
                confirmPassword = sc.nextLine();
            } while (confirmPassword.compareTo(password) != 0);

            Encoder encoder = Base64.getEncoder();
            String encodedPassword = encoder.encodeToString(password.getBytes());

            if (AdminDB.isUsernameExist(username) || CustomerDB.isUsernameExist(username)) {
                System.out.println("Oops... Username is used");
                bufferedWriter.close();
                return;
            }

            System.out.print("\nEmail: ");
            email = sc.nextLine();

            System.out.print("\nMobile: ");
            mobile = sc.nextLine();

            bufferedWriter.write(username + "\n");
            bufferedWriter.write(encodedPassword + "\n");
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
     * @param transaction A Transaction object to be added into transaction database
     * @see Model.Transaction
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
     * @param movie    The title of the movie associated with rating
     * @param username The username associated with the rating
     * @see Model.Rating
     */
    public static void addNewRating(String movie, String username) {
        try {
            FileWriter writer = new FileWriter(ratingDatabasePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            Scanner sc = new Scanner(System.in);
            float rating;
            String review;

            // Asking time!!
            System.out.println("Adding new rating...\n");

            System.out.print("\nRating (out of 5): ");
            rating = Float.parseFloat(sc.nextLine());

            System.out.print("\nReview: ");
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
     * Adds new Cineplex into cineplex database via user inputs
     * 
     * @see Model.Cineplex
     */
    public static void addNewCineplex() {
        try {
            FileWriter writer = new FileWriter(cineplexDatabasePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            Scanner sc = new Scanner(System.in);
            String cineplexName;

            System.out.println("Adding new cinplex...\n");

            System.out.println("\nCineplex name: ");
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
        System.out.println("\nPlease enter cineplex ID: ");
        cineplexID = sc.nextLine();

        if (CineplexDB.getCineplexFromID(cineplexID) == null) {
            System.out.println("Oops cineplex does not exist :(");
            return;
        }

        System.out.println("\nPlease enter cinema ID (e.g. XXX): ");
        cinemaID = sc.nextLine();

        if (CinemaDB.getCinemaFromID(cinemaID) == null) {
            System.out.println("Oops cinema does not exist :(");
            return;
        }

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

            Scanner sc = new Scanner(System.in);
            String cinemaID, cinemaType;

            // Asking time!!
            System.out.println("Adding new cinema...\n");

            System.out.println("\nCinema ID: ");
            cinemaID = sc.nextLine();

            System.out.println("\nCinema type: ");
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

    /**
     * Updates the Seats corresponding to the Showtime
     * 
     * @param movieTitle A String representing the movie title associated to the
     *                   showtime
     * @param cineplexID A String representing the cineplex ID associated to the
     *                   showtime
     * @param cinemaID   A String representing the cinema ID associated to the
     *                   showtime
     * @param date       A String representing the date associated to the showtime
     * @param day        A String representing the day associated to the showtime
     * @param time       A String representing the time associated to the showtime
     * @param seats      An array of Seat object representing the new seats
     *                   associated to the showtime
     */
    public static void updateShowtimeSeats(String movieTitle, String cineplexID, String cinemaID, String date,
            String day,
            String time, Seat[] seats) {
        ArrayList<String> strings = DatabaseReader.readtxt(showtimesDatabasePath);
        int lineCount = 0;
        for (String string : strings) {
            String[] s = string.split(",");
            if (s[0].toLowerCase().compareTo(movieTitle.toLowerCase()) == 0 &&
                    s[1].toLowerCase().compareTo(cineplexID.toLowerCase()) == 0 &&
                    s[2].toLowerCase().compareTo(cinemaID.toLowerCase()) == 0 &&
                    s[3].toLowerCase().compareTo(date.toLowerCase()) == 0 &&
                    s[5].toLowerCase().compareTo(time.toLowerCase()) == 0) {
                break;
            }
            lineCount++;
        }
        try {
            FileWriter writer = new FileWriter(showtimesDatabasePath);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (int i = 0; i < strings.size(); i++) {
                if (i == lineCount) {
                    bufferedWriter.write(
                            String.format("%s,%s,%s,%s,%s,%s,", movieTitle, cineplexID, cinemaID, date, day, time));
                    String[] seatStringArray = new String[100];
                    for (int j = 0; j < seatStringArray.length; j++) {
                        seatStringArray[j] = seats[j].assigned ? "1" : "0";
                    }
                    bufferedWriter.write(String.join("", seatStringArray));
                    bufferedWriter.write("\n");
                    continue;
                }
                bufferedWriter.write(strings.get(i) + "\n");
            }

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the info of the Showtime
     * 
     * @param movieTitle    A String representing the movie title associated to the
     *                      showtime
     * @param cineplexIDA   A String representing the cineplex ID associated to the
     *                      showtime
     * @param cinemaIDA     A String representing the cinema ID associated to the
     *                      showtime
     * @param dateA         A String representing the date associated to the
     *                      showtime
     * @param dayA          A String representing the day associated to the showtime
     * @param timeA         A String representing the time associated to the
     *                      showtime
     * @param newMovieTitle A String representing the new movie title associated to
     *                      the showtime
     * @param newCineplexID A String representing the new cineplex ID associated to
     *                      the showtime
     * @param newCinemaID   A String representing the new cinema ID associated to
     *                      the showtime
     * @param newDate       A String representing the new date associated to the
     *                      showtime
     * @param newDay        A String representing the new day associated to the
     *                      showtime
     * @param newTime       A String representing the new time associated to the
     *                      showtime
     */
    public static void updateShowtimeInfo(String movieTitle, String cineplexID, String cinemaID, String date,
            String day, String time, String newMovieTitle, String newCineplexID, String newCinemaID, String newDate,
            String newDay,
            String newTime) {
        ArrayList<String> strings = DatabaseReader.readtxt(showtimesDatabasePath);
        int lineCount = 0;
        for (String string : strings) {
            String[] s = string.split(",");
            if (s[0].toLowerCase().compareTo(movieTitle.toLowerCase()) == 0 &&
                    s[1].toLowerCase().compareTo(cineplexID.toLowerCase()) == 0 &&
                    s[2].toLowerCase().compareTo(cinemaID.toLowerCase()) == 0 &&
                    s[3].toLowerCase().compareTo(date.toLowerCase()) == 0 &&
                    s[5].toLowerCase().compareTo(time.toLowerCase()) == 0) {
                break;
            }
            lineCount++;
        }
        try {
            FileWriter writer = new FileWriter(showtimesDatabasePath);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (int i = 0; i < strings.size(); i++) {
                if (i == lineCount) {
                    bufferedWriter.write(
                            String.format("%s,%s,%s,%s,%s,%s,", newMovieTitle, newCineplexID, newCinemaID, newDate,
                                    newDay, newTime));
                    bufferedWriter.write(strings.get(i).split(",")[6] + "\n");
                    continue;
                }
                bufferedWriter.write(strings.get(i) + "\n");
            }

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new Showtime to the showtime database
     * 
     * @param movieTitle A String representing the movie title of the new showtime
     * @param cineplexID A String representing the cineplex ID of the new showtime
     * @param cinemaID   A String representing the cinema ID of the new showtime
     * @param date       A String representing the date of the new showtime
     * @param day        A String representing the day of the new showtime
     * @param time       A String representing the time of the new showtime
     * @param seats      An array of Seat objects representing seats of the new
     *                   showtime
     */
    public static void createShowtime(String movieTitle, String cineplexID, String cinemaID, String date, String day,
            String time, Seat[] seats) {
        ArrayList<String> strings = DatabaseReader.readtxt(showtimesDatabasePath);

        try {
            FileWriter writer = new FileWriter(showtimesDatabasePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(
                    String.format("%s,%s,%s,%s,%s,%s,", movieTitle, cineplexID, cinemaID, date, day, time));
            String[] seatStringArray = new String[100];
            for (int j = 0; j < seatStringArray.length; j++) {
                seatStringArray[j] = seats[j].assigned ? "1" : "0";
            }
            bufferedWriter.write(String.join("", seatStringArray));
            bufferedWriter.write("\n");

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes the Showtime from the showtime database
     * 
     * @param movieTitle A String representing the movie title of the new showtime
     * @param cineplexID A String representing the cineplex ID of the new showtime
     * @param cinemaID   A String representing the cinema ID of the new showtime
     * @param date       A String representing the date of the new showtime
     * @param day        A String representing the day of the new showtime
     * @param time       A String representing the time of the new showtime
     */
    public static void removeShowtimes(String movieTitle, String cineplexID, String cinemaID, String date, String day,
            String time) {
        ArrayList<String> strings = DatabaseReader.readtxt(showtimesDatabasePath);
        int lineCount = 0;
        for (String string : strings) {
            String[] s = string.split(",");
            if (s[0].toLowerCase().compareTo(movieTitle.toLowerCase()) == 0 &&
                    s[1].toLowerCase().compareTo(cineplexID.toLowerCase()) == 0 &&
                    s[2].toLowerCase().compareTo(cinemaID.toLowerCase()) == 0 &&
                    s[3].toLowerCase().compareTo(date.toLowerCase()) == 0 &&
                    s[5].toLowerCase().compareTo(time.toLowerCase()) == 0) {
                break;
            }
            lineCount++;
        }
        try {
            FileWriter writer = new FileWriter(showtimesDatabasePath);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (int i = 0; i < strings.size(); i++) {
                if (i == lineCount) {
                    continue;
                }
                bufferedWriter.write(strings.get(i) + "\n");
            }

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
