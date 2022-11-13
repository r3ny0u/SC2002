package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import Database.CineplexDB;
import Database.CustomerDB;
import Database.MovieDB;
import Database.RatingDB;
import Database.ShowtimeDB;
import Database.TransactionDB;
import DatabaseBoundary.DatabaseReader;

/**
 * A class for Customer
 */
public class Customer extends Account {
    /** A String representing the email */
    private String email;
    /** A String representing the mobile */
    private String mobile;
    /** An ArrayList of Transactions object associated with this customer account */
    private ArrayList<Transaction> transactions = new ArrayList<Transaction>();

    /**
     * Constructor
     * 
     * @param username  A String representing the username
     * @param password  A String representing the password
     * @param accountID A String representing the account ID
     * @param email     A String representing the email
     * @param mobile    A String representing the mobile
     */
    public Customer(String username, String password, String accountID, String email, String mobile) {
        this.username = username;
        this.password = password;
        this.accountID = accountID;
        this.email = email;
        this.mobile = mobile;
        this.loadCustomerTransaction();
    }

    /**
     * Loads the customer's transaction history
     */
    public void loadCustomerTransaction() {
        this.transactions.clear();
        TransactionDB transactionDB = new TransactionDB();
        for (Transaction transaction : transactionDB.getTransactions()) {
            if (transaction.getCustomerID().compareTo(this.accountID.toLowerCase()) == 0) {
                this.transactions.add(transaction);
            }
        }
    }

    /**
     * Prints out the customer's transaction history
     */
    public void printCustomerTransactionHistory() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
        System.out.flush();
        System.out.println("\n=================== Transactions =====================");

        if (this.transactions.size() == 0) {
            System.out.println("No transactions recorded");
        } else {
            for (Transaction transaction : this.transactions) {
                System.out.printf("  %s - %.2f - %s\n", transaction.getTransactionId(), transaction.getTicketPrice(),
                        transaction.getMovie().getTitle());
            }
        }

        System.out.println("======================================================\n");
        System.out.println("Press <Enter> to Exit View");
        scanner.nextLine();
    }

    /**
     * Check if customer with current username and password exists in database
     * 
     * @param username A String representing the username
     * @param password A String representing the passwrod
     * @return true if customer exists
     */
    public static boolean checkPassword(String username, String password) {
        return CustomerDB.getCustomerFromUsername(username, password) != null;
    }

    /**
     * Checks the age of the customer to match with the movie age rating
     * 
     * @param age    A String representing the age of the customer
     * @param rating A String representing the movie age rating
     * @return true if customer is able to watch the movie of the age rating
     */
    public boolean checkAge(String age, String rating) {
        if (rating.toLowerCase().compareTo("g") == 0 || rating.toLowerCase().compareTo("pg") == 0) {
            return true;
        } else {
            if (Integer.valueOf(age) < Integer.valueOf(rating.substring(rating.length() - 2, rating.length()))) {
                return false;
            }
            return true;
        }
    }

    /**
     * Main menu for customer
     */
    public void customerMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = 69420;

        String cinemaChoice, cineplexChoice, seatID, showtimeChoice, dateChoice;
        Cineplex cineplex;
        Cinema cinema;
        int movieChoiceInt;
        Movie movieChoice;

        do {
            MovieDB movieDB = new MovieDB();
            movieDB.sortByAlphabet();
            Movie[] movieArray = movieDB.getMovies();
            int numOfMovie = movieArray.length;

            for (int i = 0; i < numOfMovie; i++) {
                if (movieArray[i].getStatus().toLowerCase().compareTo("end of showing") == 0) {
                    for (int j = i; j < movieArray.length - 1; j++) {
                        movieArray[j] = movieArray[j + 1];
                    }
                    i--;
                    numOfMovie--;
                }
            }

            for (int i = movieArray.length - 1; i >= numOfMovie; i--) {
                movieArray[i] = null;
            }

            Movie[] movieArrayEmpty;

            System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
            System.out.flush();

            System.out.println("\u001B[36mHello ~~ " + username + " ~~ :)\n\u001B[0m");

            printCustomerMenuOptions();
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Error, please enter a number!!");
                scanner.next();
            }
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Show movie listings from MovieDB by alphabetical order
                    // Movies with END OF SHOWING status would not be shown
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("\n=================== Movie Titles =====================");
                    MovieDB.printMovieList();
                    System.out.println("========================================================\n");
                    System.out.println("Press <Enter> to Exit View");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;

                case 2:
                    // Show movie listings, and then ask customer to select a movie from the list
                    // I think we don't go back to customer menu after done looking at movie details
                    // After customer press enter, it will still show the movie listings unless
                    // customer choose to quit
                    do {
                        // Show movie listings
                        System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                        System.out.flush();
                        System.out.println("\n=================== Movie Titles =====================");
                        MovieDB.printMovieList();
                        System.out.printf("%2d. Quit\n", numOfMovie + 1);
                        System.out.println("========================================================\n");

                        // Show movie details corresponding to the movie choice

                        while (true) {
                            System.out.print("Please choose a movie (enter a number): ");
                            if (!scanner.hasNextInt()) {

                                System.out.print("Error, Invalid choice!! Try again\n");
                                System.out.print(String.format("\033[2A"));
                                System.out.print("\033[2K");
                                scanner.next();
                                continue;
                            }

                            movieChoiceInt = scanner.nextInt();

                            if (movieChoiceInt <= 0 || movieChoiceInt > numOfMovie + 1) {
                                System.out.print("Error, Invalid choice!! Try again\n");
                                System.out.print(String.format("\033[2A"));
                                System.out.print("\033[2K");
                                continue;
                            }
                            break;
                        }

                        if (movieChoiceInt == numOfMovie + 1)
                            break;

                        movieChoice = movieArray[movieChoiceInt - 1];

                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        movieChoice.printMovieDetails();

                        System.out.println("========================================================");
                        System.out.println("Press <Enter> to Exit View");
                        scanner.nextLine();
                        scanner.nextLine();
                    } while (choice != numOfMovie + 1);

                    break;

                case 3:
                    // display movie list
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("\n=================== Movie Titles =====================");
                    MovieDB.printMovieList();
                    System.out.printf("%2d. Quit\n", numOfMovie + 1);
                    System.out.println("========================================================\n");

                    // Show movie details corresponding to the movie choice
                    while (true) {
                        System.out.print("Please choose a movie to view seating (enter a number): ");
                        if (!scanner.hasNextInt()) {

                            System.out.print("Error, Invalid choice!! Try again\n");
                            System.out.print(String.format("\033[2A"));
                            System.out.print("\033[2K");
                            scanner.next();
                            continue;
                        }

                        movieChoiceInt = scanner.nextInt();

                        if (movieChoiceInt <= 0 || movieChoiceInt > numOfMovie + 1) {
                            System.out.print("Error, Invalid choice!! Try again\n");
                            System.out.print(String.format("\033[2A"));
                            System.out.print("\033[2K");
                            continue;
                        }
                        break;
                    }

                    if (movieChoiceInt == numOfMovie + 1)
                        break;

                    movieChoice = movieArray[movieChoiceInt - 1];
                    scanner.nextLine();

                    if (movieChoice.getStatus().toLowerCase().compareTo("now showing") == 0
                            || movieChoice.getStatus().toLowerCase().compareTo("preview") == 0) {

                        // Print our movie detail for user to see before adding review
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        movieChoice.printMovieDetails();

                        // Print all showtimes for customer to see, then let customer choose cineplex,
                        // cinema, date and time
                        movieChoice.printAllShowtimes();

                        System.out.println();
                        cineplexChoice = movieChoice.chooseCineplex();
                        System.out.print("\033[2K");
                        if (cineplexChoice.compareTo("exit") == 0) {
                            // System.out.println("========================================================");
                            // System.out.println("Press <Enter> to Exit View");
                            // scanner.nextLine();
                            break;
                        }

                        cineplex = CineplexDB.getCineplexFromID(cineplexChoice);

                        if (cineplex == null) {
                            System.out.println("Cineplex does not exist...");
                            System.out.println("========================================================");
                            System.out.println("Press <Enter> to Exit View");
                            scanner.nextLine();
                            break;
                        }
                        System.out.println();
                        System.out.println("----------");
                        System.out.println();
                        cinemaChoice = movieChoice.chooseCinema(cineplexChoice);
                        System.out.print("\033[2K");
                        if (cinemaChoice.compareTo("exit") == 0) {
                            // System.out.println("========================================================");
                            // System.out.println("Press <Enter> to Exit View");
                            // scanner.nextLine();
                            break;
                        }
                        cinema = cineplex.findCinema(cinemaChoice);

                        if (cinema == null) {
                            System.out.println("Cinema does not exist...");
                            System.out.println("Press <Enter> to Exit View");
                            scanner.nextLine();
                            break;
                        }

                        // User to input date and showtime, to get seats
                        System.out.println();
                        System.out.println("----------");
                        System.out.println();
                        dateChoice = movieChoice.chooseDate(cinemaChoice);
                        System.out.print("\033[2K");
                        if (dateChoice.compareTo("exit") == 0) {
                            // System.out.println("========================================================");
                            // System.out.println("Press <Enter> to Exit View");
                            // scanner.nextLine();
                            break;
                        }

                        // if (dateChoice == null) {
                        // System.out.println("Show time does not exist");
                        // System.out.println("========================================================");
                        // System.out.println("Press <Enter> to Exit View");
                        // scanner.nextLine();
                        // break;
                        // }

                        System.out.println();
                        System.out.println("----------");
                        System.out.println();
                        showtimeChoice = movieChoice.chooseTime(dateChoice, cinemaChoice);
                        if (showtimeChoice.compareTo("exit") == 0) {
                            // System.out.println("========================================================");
                            // System.out.println("Press <Enter> to Exit View");
                            // scanner.nextLine();
                            break;
                        }

                        System.out.println("======================== Seats ==========================\n");
                        movieChoice.printSeats(cinema.getCinemaID(), dateChoice, showtimeChoice);

                        System.out.println("========================================================");
                        System.out.println("Press <Enter> to Exit View");
                        scanner.nextLine();
                    }

                    else {
                        System.out.println("\nSorry, viewing of seats for this movie is not available.");
                        System.out.println("========================================================");
                        System.out.println("Press <Enter> to Exit View");
                        scanner.nextLine();
                        break;
                    }

                    break;

                case 4:
                    // Make a booking: Print out movie list, let customer choose movie, cineplex,
                    // and cinema (gotten from the moive object itself), then the showing time, and
                    // seats, before choose each of the things, print out the available options for
                    // customer
                    // Extra can do: If in any point of choosing, customer type in exit or other
                    // phrase, system would break into customer menu page
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("\n=================== Movie Titles =====================");
                    MovieDB.printMovieList();
                    System.out.printf("%2d. Quit\n", numOfMovie + 1);
                    System.out.println("========================================================\n");

                    // Show movie details corresponding to the movie choice
                    System.out.print("Please choose a movie to book (enter a number): ");
                    do {
                        while (!scanner.hasNextInt()) {
                            System.out.print("Error, invalid choice!! Try again: ");
                            scanner.next();
                        }
                        movieChoiceInt = scanner.nextInt();

                        if (movieChoiceInt > numOfMovie + 1 || movieChoiceInt <= 0) {
                            System.out.print("Error, invalid choice!! Try again: ");
                        }
                    } while (movieChoiceInt > numOfMovie + 1 || movieChoiceInt <= 0);

                    if (movieChoiceInt == numOfMovie + 1) {
                        scanner.nextLine();
                        System.out.println("========================================================");
                        System.out.println("Press <Enter> to Exit View");
                        scanner.nextLine();
                        break;
                    }

                    movieChoice = movieArray[movieChoiceInt - 1];
                    scanner.nextLine();

                    // Print out movie detail for user to see before booking
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    movieChoice.printMovieDetails();

                    if (movieChoice.getStatus().toLowerCase().compareTo("now showing") == 0
                            || movieChoice.getStatus().toLowerCase().compareTo("preview") == 0) {
                        System.out.println("======================= Booking ========================\n");

                        // Print all showtimes for customer to see, then let customer choose cineplex,
                        // cinema, date and time

                        if (movieChoice.showingPlaces.size() == 0) {
                            System.out.println("Sorry, the movie is not available for booking.");
                            System.out.println("========================================================");
                            System.out.println("Press <Enter> to Exit View");
                            scanner.nextLine();
                            break;
                        }

                        // User to input age
                        System.out.printf("Please enter your age: ");
                        String age = scanner.nextLine();

                        if (!checkAge(age, movieChoice.getMovieAgeRating())) {
                            System.out.printf("Sorry, this movie is %s.\n", movieChoice.getMovieAgeRating());
                            System.out.println("========================================================");
                            System.out.println("Press <Enter> to Exit View");
                            scanner.nextLine();
                            break;
                        }

                        System.out.println();
                        System.out.println("========================================================\n");
                        movieChoice.printAllShowtimes();

                        System.out.println();
                        cineplexChoice = movieChoice.chooseCineplex();
                        System.out.print("\033[2K");
                        if (cineplexChoice.compareTo("exit") == 0) {
                            // System.out.println("========================================================");
                            // System.out.println("Press <Enter> to Exit View");
                            // scanner.nextLine();
                            break;
                        }

                        cineplex = CineplexDB.getCineplexFromID(cineplexChoice);

                        if (cineplex == null) {
                            System.out.println("Cineplex does not exist...");
                            System.out.println("========================================================");
                            System.out.println("Press <Enter> to Exit View");
                            scanner.nextLine();
                            break;
                        }

                        System.out.println();
                        System.out.println("----------");
                        System.out.println();
                        cinemaChoice = movieChoice.chooseCinema(cineplexChoice);
                        System.out.print("\033[2K");
                        if (cinemaChoice.compareTo("exit") == 0) {
                            // System.out.println("========================================================");
                            // System.out.println("Press <Enter> to Exit View");
                            // scanner.nextLine();
                            break;
                        }
                        cinema = cineplex.findCinema(cinemaChoice);

                        if (cinema == null) {
                            System.out.println("Cinema does not exist...");
                            System.out.println("Press <Enter> to Exit View");
                            scanner.nextLine();
                            break;
                        }

                        // User to input date and showtime, to get seats
                        System.out.println();
                        System.out.println("----------");
                        System.out.println();
                        dateChoice = movieChoice.chooseDate(cinemaChoice);
                        System.out.print("\033[2K");
                        if (dateChoice.compareTo("exit") == 0) {
                            // System.out.println("========================================================");
                            // System.out.println("Press <Enter> to Exit View");
                            // scanner.nextLine();
                            break;
                        }

                        System.out.println();
                        System.out.println("----------");
                        System.out.println();
                        showtimeChoice = movieChoice.chooseTime(dateChoice, cinemaChoice);
                        if (showtimeChoice.compareTo("exit") == 0) {
                            // System.out.println("========================================================");
                            // System.out.println("Press <Enter> to Exit View");
                            // scanner.nextLine();
                            break;
                        }

                        // Check if showtime exists
                        Showtime showtime = null;
                        for (Map<String, Map<Showtime, Seat[]>> bla : DatabaseReader
                                .readShowtime(movieChoice.getTitle()).values()) {
                            if (bla.keySet().size() == 0)
                                break;
                            if (!bla.keySet().contains(cinemaChoice))
                                continue;
                            for (Showtime show : bla.get(cinemaChoice).keySet()) {
                                if (show == null)
                                    break;
                                if ((show.date.toLowerCase().compareTo(dateChoice) == 0)
                                        && (show.time.toLowerCase().compareTo(showtimeChoice) == 0)) {
                                    showtime = show;
                                    break;
                                }
                            }
                        }

                        if (showtime == null) {
                            System.out.println("Showtime does not exist");
                            System.out.println("========================================================");
                            System.out.println("Press <Enter> to Exit View");
                            scanner.nextLine();
                            break;
                        }

                        movieChoice.printSeats(cinema.getCinemaID(), dateChoice, showtimeChoice);

                        do {
                            // User to choose seats they want (check for empty seats)
                            System.out.printf("Select the seat you want (eg. A1): ");
                            seatID = scanner.next();
                            if (!seatID.matches("[A-J][0-9]{1,2}") || (Integer.parseInt(seatID.substring(1)) < 1
                                    || Integer.parseInt(seatID.substring(1)) > 10)) {
                                System.out.print("Error, invalid seat!! Try again\n");
                                System.out.print(String.format("\033[2A"));
                                System.out.print("\033[2K");
                                continue;
                            }

                        } while (!seatID.matches("[A-J][0-9]{1,2}") || (Integer.parseInt(seatID.substring(1)) < 1
                                || Integer.parseInt(seatID.substring(1)) > 10));

                        showtime = new Showtime(dateChoice, "not important", showtimeChoice);

                        boolean assigned = movieChoice.checkSeat(cinemaChoice, dateChoice, showtimeChoice, seatID);

                        while (assigned) {
                            System.out.print("Seat choice is already assigned or is not valid!");
                            System.out.print(String.format("\033[1A")); // Move up 1
                            System.out.print("\033[1K\033[1K"); // Erase line content
                            do {
                                System.out.print("\rPlease choose another seat (eg. A1): ");
                                seatID = scanner.next();
                                if (!seatID.matches("[A-J][0-9]{1,2}") || (Integer.parseInt(seatID.substring(1)) < 1
                                        || Integer.parseInt(seatID.substring(1)) > 10)) {
                                    System.out.print("Error, invalid seat!! Try again\n");
                                    System.out.print(String.format("\033[2A"));
                                    System.out.print("\033[2K");
                                    continue;
                                }
                            } while (!seatID.matches("[A-J][0-9]{1,2}") || (Integer.parseInt(seatID.substring(1)) < 1
                                    || Integer.parseInt(seatID.substring(1)) > 10));

                            assigned = movieChoice.checkSeat(cinemaChoice, dateChoice, showtimeChoice, seatID);
                        }
                        System.out.print("\033[2K");

                        Seat[] newSeats = movieChoice.assignSeat(cinemaChoice, dateChoice, showtimeChoice, seatID,
                                this.accountID);

                        // Booking process done, creating transaction
                        Transaction newTrans = new Transaction(showtimeChoice, age, cinema, dateChoice, movieChoice,
                                accountID, seatID);

                        System.out.println("\nPrice of ticket : $" + newTrans.getTicketPrice());

                        transactions.add(newTrans);

                        TransactionDB.addNewTransaction(newTrans);
                        ShowtimeDB.updateShowtimes(movieChoice.getTitle(), cineplexChoice, cinemaChoice, dateChoice,
                                "day",
                                showtimeChoice, newSeats);
                        movieChoice.loadShowtimes();

                        // Continue booking progress
                        System.out.println("\nBooking successful :) ...");

                        // Sleep for 1.5s then return to customer menu page
                        /*
                         * try {
                         * TimeUnit.MILLISECONDS.sleep(1500);
                         * } catch (Exception e) {
                         * e.printStackTrace();
                         * }
                         */
                        scanner.nextLine();
                        System.out.println("========================================================");
                        System.out.println("Press <Enter> to Exit View");
                        scanner.nextLine();
                        break;
                    }

                    else {
                        System.out.println("Sorry, the movie is not available for booking.");
                        System.out.println("========================================================");
                        System.out.println("Press <Enter> to Exit View");
                        scanner.nextLine();
                        break;
                    }

                case 5:
                    // check booking history
                    this.printCustomerTransactionHistory();
                    break;

                case 6:
                    // Show top 5 movies based on ticket sales
                    if (DatabaseReader.readRankingVisibilityDatabase().equals("3")
                            || DatabaseReader.readRankingVisibilityDatabase().equals("4")) {
                        // System.out.print("\033[H\033[2J");
                        // System.out.flush();
                        System.out.println("Sorry, the ranking is currently not visible.");
                        try {
                            TimeUnit.MILLISECONDS.sleep(1500);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    movieArray = movieDB.getMovies();
                    TransactionDB transactions = new TransactionDB();
                    Transaction[] txnArr = transactions.getTransactions();

                    Map<String, Integer> movMap = new HashMap<String, Integer>();

                    // initialise map
                    for (Movie mov : movieArray) {
                        if (mov == null)
                            continue;
                        movMap.put(mov.getTitle(), 0);
                    }

                    // update sales count
                    for (Transaction txn : txnArr) {
                        movMap.put(txn.getMovie().getTitle(), movMap.get(txn.getMovie().getTitle()) + 1);
                    }

                    LinkedHashMap<String, Integer> sortedMovMap = new LinkedHashMap<>();
                    ArrayList<Integer> list = new ArrayList<>();

                    // sort map in descending order of key
                    for (Map.Entry<String, Integer> entry : movMap.entrySet()) {
                        list.add(entry.getValue());
                    }
                    Collections.sort(list, Collections.reverseOrder());
                    for (int num : list) {
                        for (Entry<String, Integer> entry : movMap.entrySet()) {
                            if (entry.getValue().equals(num)) {
                                sortedMovMap.put(entry.getKey(), num);
                            }
                        }
                    }

                    // print top 5 movies by sales
                    int k = 1;
                    System.out.println("\n=============== Movie Titles by Sales ================");
                    for (String str : sortedMovMap.keySet()) {
                        System.out.printf("%2d. %s: %d tickets sold\n", k++, str,
                                sortedMovMap.get(str));
                        if (k > 5)
                            break;
                    }
                    System.out.println("========================================================");
                    System.out.println("Press <Enter> to Exit View");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;

                case 7:
                    // Show top 5 movies based on ratings

                    if (DatabaseReader.readRankingVisibilityDatabase().equals("2")
                            || DatabaseReader.readRankingVisibilityDatabase().equals("4")) {
                        // System.out.print("\033[H\033[2J");
                        // System.out.flush();
                        System.out.println("Sorry, the ranking is currently not visible.");
                        try {
                            TimeUnit.MILLISECONDS.sleep(1500);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    movieDB = new MovieDB();
                    movieDB.sortByRating();
                    Movie[] moviesArr = movieDB.getMovies();

                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("\n============== Movie Titles by Ratings ===============");
                    for (int i = 0; i < 5; i++) {

                        if (moviesArr[i] == null)
                            break;

                        moviesArr[i].loadRatingsAndReviews();

                        if (moviesArr[i].getRatingCount() == 0) {
                            System.out.printf("%2d. (NA / 5.0 STARS) %s\n", i + 1, moviesArr[i].getTitle());
                        } else {
                            System.out.printf("%2d. (%.1f / 5.0 Stars) %s\n", i + 1, moviesArr[i].getRating(),
                                    moviesArr[i].getTitle());
                        }
                    }
                    System.out.println("========================================================");
                    System.out.println("Press <Enter> to Exit View");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;

                case 8:
                    // Show movie listings, let user choose movie, and add ratings
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("\n=================== Movie Titles =====================");
                    MovieDB.printMovieList();
                    System.out.printf("%2d. Quit\n", numOfMovie + 1);
                    System.out.println("========================================================\n");

                    // Show movie details corresponding to the movie choice
                    while (true) {
                        System.out.print("Please choose a movie to add review (enter a number): ");
                        if (!scanner.hasNextInt()) {

                            System.out.print("Error, Invalid choice!! Try again\n");
                            System.out.print(String.format("\033[2A"));
                            System.out.print("\033[2K");
                            scanner.next();
                            continue;
                        }

                        movieChoiceInt = scanner.nextInt();

                        if (movieChoiceInt <= 0 || movieChoiceInt > numOfMovie + 1) {
                            System.out.print("Error, Invalid choice!! Try again\n");
                            System.out.print(String.format("\033[2A"));
                            System.out.print("\033[2K");
                            continue;
                        }
                        break;
                    }

                    if (movieChoiceInt == numOfMovie + 1)
                        break;

                    movieChoice = movieArray[movieChoiceInt - 1];
                    scanner.nextLine();

                    if (movieChoice.getStatus().toLowerCase().compareTo("now showing") == 0
                            || movieChoice.getStatus().toLowerCase().compareTo("preview") == 0) {
                        boolean canReview = false;
                        for (Transaction trans : this.transactions) {
                            if (trans.getMovie().getTitle().compareTo(movieChoice.getTitle()) == 0) {
                                canReview = true;
                                break;
                            }
                        }
                        if (!canReview) {
                            System.out.println("\nSorry you have to watch the movie before adding a review.");
                            System.out.println("============================================================");
                            System.out.println("Press <Enter> to Exit View");
                            scanner.nextLine();
                            break;
                        }
                        // Print our movie detail for user to see before adding review
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        movieChoice.printMovieDetails();
                        System.out.println("===================== Add a Review =====================\n");

                        // Add review to movie, after added, wait a while then return to menu
                        RatingDB.addNewRating(movieChoice.getTitle(), username);
                        System.out.println("\nReview added...");

                        try {
                            TimeUnit.MILLISECONDS.sleep(1500);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    else {
                        System.out.println("Sorry, the movie is not available for ratings yet.");
                        System.out.println("Press <Enter> to Exit View");
                        scanner.nextLine();
                        break;
                    }

                case 9:
                    break;

                default:
                    break;
            }
        } while (choice != 9);
    }

    /**
     * Prints the main menu options
     */
    public static void printCustomerMenuOptions() {
        System.out.println("What would you like to do today?");
        System.out.println("====================================================");
        System.out.println(" 1. View movie listings");
        System.out.println(" 2. View movie details");
        System.out.println(" 3. Check seat availability");
        System.out.println(" 4. Make a booking");
        System.out.println(" 5. Check my booking history");
        System.out.println(" 6. List the Top 5 ranking by ticket sales");
        System.out.println(" 7. List the Top 5 ranking by overall reviewers' ratings");
        System.out.println(" 8. Add review for a movie");
        System.out.println(" 9. Quit");
        System.out.println("====================================================");
    }
}