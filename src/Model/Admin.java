package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import Database.AdminDB;
import Database.CinemaDB;
import Database.CineplexDB;
import Database.MovieDB;
import Database.MovieTicketConfig;
import Database.TransactionDB;
import DatabaseBoundary.DatabaseReader;
import DatabaseBoundary.DatabaseWriter;

/**
 * A class for Admin account
 */
public class Admin extends Account {
    public Admin(String username, String password, String accountID) {
        this.username = username;
        this.password = password;
        this.accountID = accountID;
    }

    /**
     * Check if admin with current username and password exists in database
     * 
     * @return true if admin exists
     */
    public static boolean checkPassword(String username, String password) {
        return AdminDB.getAdminFromUsername(username, password) != null;
    }

    /**
     * The main menu for Admin
     */
    public void adminMenu() {
        if (!checkPassword(username, password))
            return;

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            printAdminMenuOptions();

            choice = scanner.nextInt();

            int movieChoiceInt;
            Movie movieChoice;
            MovieDB movies = new MovieDB();
            movies.sortByAlphabet();
            Movie[] movieArray = movies.getMovies();

            switch (choice) {
                case 1:
                    // Create new movie
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    createMovieListing();
                    break;

                case 2:
                    // Show movie titles, let admin choose movie to edit details
                    movies = new MovieDB();
                    movies.sortByAlphabet();
                    movieArray = movies.getMovies();

                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("\n=================== Movie Titles =====================");
                    MovieDB.printAllMovieList();
                    System.out.printf("%2d. Quit\n", movieArray.length + 1);
                    System.out.println("========================================================\n");

                    // Update movie corresponding to the movie choice
                    System.out.print("Please choose a movie to update: ");
                    movieChoiceInt = scanner.nextInt();

                    if (movieChoiceInt == movieArray.length + 1)
                        break;

                    if (movieChoiceInt <= 0 || movieChoiceInt > movieArray.length + 1)
                        break;

                    movieChoice = movieArray[movieChoiceInt - 1];

                    System.out.printf("Editing \"%s\"...\n", movieChoice.getTitle());
                    System.out.println("\n================== Movie Details =====================");
                    System.out.println(" 1. Title");
                    System.out.println(" 2. Showing Status");
                    System.out.println(" 3. Synopsis");
                    System.out.println(" 4. Director");
                    System.out.println(" 5. Cast");
                    System.out.printf(" 6. Quit\n");
                    System.out.println("========================================================\n");

                    // Update movie corresponding to the movie choice
                    System.out.print("Please choose a detail to update: ");
                    movieChoiceInt = scanner.nextInt(); // I'm just gonna use this var to hold the user choice
                    String title = movieChoice.getTitle(), showingStatus = movieChoice.status,
                            synopsis = movieChoice.synopsis, director = movieChoice.director, cast;
                    ArrayList<String> casts = movieChoice.casts;

                    scanner.nextLine(); // Clear input buffer

                    if (movieChoiceInt <= 0 || movieChoiceInt >= 6)
                        break;

                    switch (movieChoiceInt) {
                        case 1:
                            System.out.printf("New title: ");
                            title = scanner.nextLine();
                            break;
                        case 2:
                            System.out.printf("New showing status: ");
                            showingStatus = scanner.nextLine();
                            break;
                        case 3:
                            System.out.printf("New synopsis: ");
                            synopsis = scanner.nextLine();
                            break;
                        case 4:
                            System.out.printf("New director: ");
                            director = scanner.nextLine();
                            break;
                        case 5:
                            casts.clear();
                            System.out.printf("New casts: (enter 'exit' to complete entry)");
                            while (true) {
                                cast = scanner.nextLine();
                                if (cast.toLowerCase().compareTo("exit") == 0)
                                    break;
                                casts.add(cast);
                            }
                            break;
                        default:
                            break;
                    }
                    DatabaseWriter.updateMovieDetails(movieChoice, title, showingStatus, synopsis, director, casts);
                    System.out.printf("\nEditing \"%s\"...\n", movieChoice.getTitle());

                    System.out.println("========================================================");
                    System.out.println("Press <Enter> to Exit View");
                    scanner.nextLine();
                    break;

                case 3:
                    // Print movie listings, and let admin choose movie to remove
                    // Show movie listings
                    movies = new MovieDB();
                    movies.sortByAlphabet();
                    movieArray = movies.getMovies();

                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("\n=================== Movie Titles =====================");
                    MovieDB.printAllMovieList();
                    System.out.printf("%2d. Quit\n", movieArray.length + 1);
                    System.out.println("========================================================\n");

                    // Remove movie corresponding to the movie choice
                    System.out.print("Please choose a movie to remove: ");
                    movieChoiceInt = scanner.nextInt();

                    if (movieChoiceInt == movieArray.length + 1)
                        break;

                    if (movieChoiceInt <= 0 || movieChoiceInt > movieArray.length + 1)
                        break;

                    movieChoice = movieArray[movieChoiceInt - 1];

                    System.out.printf("Removed \"%s\"\n", movieChoice.getTitle());

                    DatabaseWriter.removeMovieByTitle(movieChoice.title);

                    System.out.println("========================================================\n");
                    System.out.println("Press <Enter> to Exit View");
                    scanner.nextLine();
                    scanner.nextLine();

                    break;

                case 4:
                    // Create showtimes
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("Enter the movie title you wish to add showtimes and shit: ");
                    createShowtimes();
                    break;

                case 5:
                    // Update showtimes
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("Enter the movie title you wish to udpate showtimes and shit: ");
                    updateShowtimes();
                    break;

                case 6:
                    // Remove showtimes
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("Enter the movie title you wish to remove showtimes and shit: ");
                    removeShowtimes();
                    break;

                case 7:
                    // Configure ticket markups
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    configSys();
                    break;

                case 8:
                    // Set visibility ranking
                    System.out.print("\nMake Ranking by ticket sales visible? (Y/N): ");
                    String choice1 = scanner.next();
                    System.out.print("\nMake Ranking by overall reviewers' ratings? (Y/N): ");
                    String choice2 = scanner.next();
                    DatabaseWriter.setRankingVisibility(choice1, choice2);
                    System.out.println("=====================================================");
                    System.out.println("Press <Enter> to Exit View");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;

                case 9:
                    // Print sysem configurations
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("=====================================================");
                    MovieTicketConfig.printConfigDetails();
                    System.out.println("----------");
                    String s = DatabaseReader.readRankingVisibilityDatabase();
                    String c1, c2;
                    if (s.equals("1")) {
                        c1 = "Y";
                        c2 = "Y";
                    } else if (s.equals("2")) {
                        c1 = "Y";
                        c2 = "N";
                    } else if (s.equals("3")) {
                        c1 = "N";
                        c2 = "Y";
                    } else {
                        c1 = "N";
                        c2 = "N";
                    }
                    System.out.println("Ranking by ticket sales visible: " + c1);
                    System.out.println("Ranking by overall reviewers' ratings: " + c2);
                    System.out.println("=====================================================");
                    System.out.println("Press <Enter> to Exit View");

                    scanner.nextLine();
                    scanner.nextLine(); // Wait for user to press enter
                    break;

                case 10:
                    // Print movie listings, and let admin choose details, i copied this from
                    // customer side
                    do {
                        // Show movie listings
                        System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                        System.out.flush();
                        System.out.println("\n=================== Movie Titles =====================");
                        MovieDB.printAllMovieList();
                        System.out.printf("%2d. Quit\n", movieArray.length + 1);
                        System.out.println("========================================================\n");

                        // Show movie details corresponding to the movie choice
                        System.out.print("Please choose a movie: ");
                        movieChoiceInt = scanner.nextInt();

                        if (movieChoiceInt == movieArray.length + 1)
                            break;

                        if (movieChoiceInt <= 0 || movieChoiceInt > movieArray.length + 1)
                            continue;

                        movieChoice = movieArray[movieChoiceInt - 1];

                        System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                        System.out.flush();
                        movieChoice.printMovieDetails();

                        System.out.println("Press <Enter> to Exit View");
                        scanner.nextLine();
                        scanner.nextLine();
                    } while (choice != movieArray.length + 1);

                    break;

                case 11:
                    // Show cineplex and cinemas (easier to debug)
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("\n==================== Cineplexes ======================");
                    CineplexDB.printCineplexDBDetails();
                    System.out.println("========================================================\n");
                    System.out.println("Press <Enter> to Exit View");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;

                case 12:
                    // Show top 5 movies based on ticket sales
                    movieArray = movies.getMovies();
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
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("\n=============== Movie Titles by Sales ================");
                    for (String str : sortedMovMap.keySet()) {
                        System.out.printf("%2d. %s: %d tickets sold\n", k++, str,
                                sortedMovMap.get(str));
                        if (k > 5)
                            break;
                    }
                    System.out.println("========================================================\n");

                    System.out.println("Press <Enter> to Exit View");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;

                case 13:
                    // Show top 5 movies based on ratings
                    MovieDB movieDB = new MovieDB();
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
                    System.out.println("========================================================\n");
                    System.out.println("Press <Enter> to Exit View");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;

                case 14:
                    // Exit
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("\u001B[36mGoodbye... ~~ " + username + " ~~ :)\n\u001B[0m");
                    return;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 14);

        return;
    }

    /**
     * Allows Admin to configure movie ticket configuration
     */
    private void configSys() {
        // Configure ticket price and other things
        Scanner scanner = new Scanner(System.in);
        int userChoice = 11;
        float newValue = 69.420f;

        do {
            System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
            System.out.flush();

            System.out.println("Which settings to update?");
            System.out.println("=====================================================");
            MovieTicketConfig.printConfigDetails();
            System.out.println("12. Quit");
            System.out.println("=====================================================");
            System.out.print("Enter choice: ");

            userChoice = scanner.nextInt();
            System.out.print("\n");

            switch (userChoice) {
                case 1:
                    System.out.printf("Updating Weekday Pricing from SGD %-2.2f to ",
                            MovieTicketConfig.getWeekdayPrice());
                    newValue = scanner.nextFloat();
                    MovieTicketConfig.updateWeekdayPrice(newValue);
                    break;

                case 2:
                    System.out.printf("Updating Weekend Pricing from SGD %-2.2f to ",
                            MovieTicketConfig.getWeekendPrice());
                    newValue = scanner.nextFloat();
                    MovieTicketConfig.updateWeekendPrice(newValue);
                    break;

                case 3:
                    System.out.printf("Updating PH Pricing from SGD %-2.2f to ",
                            MovieTicketConfig.getPHPrice());
                    newValue = scanner.nextFloat();
                    MovieTicketConfig.updatePHPrice(newValue);
                    break;

                case 4:
                    System.out.printf("Updating 2D Movie Markup from SGD %-2.2f to ",
                            MovieTicketConfig.get2DMovieMarkup());
                    newValue = scanner.nextFloat();
                    MovieTicketConfig.update2DMovieMarkup(newValue);
                    break;

                case 5:
                    System.out.printf("Updating 3D Movie markup from SGD %-2.2f to ",
                            MovieTicketConfig.get3DMovieMarkup());
                    newValue = scanner.nextFloat();
                    MovieTicketConfig.update3DMovieMarkup(newValue);
                    break;

                case 6:
                    System.out.printf("Updating Normal Cinema Markup from SGD %-2.2f to ",
                            MovieTicketConfig.getNormalCinemaMarkup());
                    newValue = scanner.nextFloat();
                    MovieTicketConfig.updateNormalCinemaMarkup(newValue);
                    break;

                case 7:
                    System.out.printf("Updating Platinum Cinema Markup from SGD %-2.2f to ",
                            MovieTicketConfig.getPlatinumCinemaMarkup());
                    newValue = scanner.nextFloat();
                    MovieTicketConfig.updatePlatinumCinemaMarkup(newValue);
                    break;

                case 8:
                    System.out.printf("Updating Adult Markup from SGD %-2.2f to ",
                            MovieTicketConfig.getAdultMarkup());
                    newValue = scanner.nextFloat();
                    MovieTicketConfig.updateAdultMarkup(newValue);
                    break;

                case 9:
                    System.out.printf("Updating Senior Markup from SGD %-2.2f to ",
                            MovieTicketConfig.getSeniorMarkup());
                    newValue = scanner.nextFloat();
                    MovieTicketConfig.updateSeniorMarkup(newValue);
                    break;

                case 10:
                    System.out.printf("Updating Child Markup from SGD %-2.2f to ",
                            MovieTicketConfig.getChildMarkup());
                    newValue = scanner.nextFloat();
                    MovieTicketConfig.updateChildMarkup(newValue);
                    break;

                default:
                    System.out.println("Invalid input...");
                    break;
            }

        } while (userChoice != 11);
    }

    /**
     * Allows Admin to remove showtimes
     */
    private void removeShowtimes() {
        // Print menu to user
        Scanner scanner = new Scanner(System.in);
        int movieChoiceInt = -69;
        MovieDB movieDB = new MovieDB();
        movieDB.sortByAlphabet();
        Movie[] movieArray = movieDB.getMovies();
        Movie movieChoice = null;
        String movieTitle;

        System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
        System.out.flush();
        System.out.println("\n=================== Movie Titles =====================");
        MovieDB.printAllMovieList();
        System.out.println("========================================================\n");

        System.out.println("================= Removing showtimes ===================");
        System.out.print("\nMovie No.   : ");

        movieChoiceInt = scanner.nextInt();

        if (movieChoiceInt == movieArray.length + 1) {
            System.out.println("Movie not found");
            System.out.println("========================================================\n");
            System.out.println("Press <Enter> to Exit View");
            scanner.nextLine();
            return;
        }

        if (movieChoiceInt <= 0 || movieChoiceInt > movieArray.length + 1) {
            System.out.println("Movie not found");
            System.out.println("========================================================\n");
            System.out.println("Press <Enter> to Exit View");
            scanner.nextLine();
            return;
        }

        movieChoice = movieArray[movieChoiceInt - 1];
        movieTitle = movieChoice.getTitle();
        scanner.nextLine();

        movieChoice.printAllShowtimes();

        System.out.println();
        System.out.println("----------");
        System.out.println();
        String cineplexID = movieChoice.chooseCineplex();

        if (CineplexDB.getCineplexFromID(cineplexID) == null) {
            System.out.println("Cineplex not found");
            System.out.println("========================================================\n");
            System.out.println("Press <Enter> to Exit View");
            scanner.nextLine();
            return;
        }

        System.out.println();
        System.out.println("----------");
        System.out.println();
        String cinemaID = movieChoice.chooseCinema(cineplexID);

        if (CinemaDB.getCinemaFromID(cinemaID) == null) {
            System.out.println("Cinema not found");
            System.out.println("========================================================\n");
            System.out.println("Press <Enter> to Exit View");
            scanner.nextLine();
            return;
        }

        System.out.println();
        System.out.println("----------");
        System.out.println();
        String date = movieChoice.chooseDate(cinemaID);

        System.out.println();
        System.out.println("----------");
        System.out.println();
        String time = movieChoice.chooseTime(date, cinemaID);

        // Check whether show time exists already
        Showtime showtime = null;
        for (Map<String, Map<Showtime, Seat[]>> bla : DatabaseReader.readShowtime(movieTitle).values()) {
            if (bla.keySet().size() == 0)
                break;
            if (!bla.keySet().contains(cinemaID))
                continue;
            for (Showtime show : bla.get(cinemaID).keySet()) {
                if (show == null)
                    break;
                if ((show.date.toLowerCase().compareTo(date) == 0)
                        && (show.time.toLowerCase().compareTo(time) == 0)) {
                    showtime = show;
                    break;
                }
            }
        }
        if (showtime == null) {
            System.out.println("Showtime does not exist");
            System.out.println("========================================================\n");
            System.out.println("Press <Enter> to Exit View");
            scanner.nextLine();
            return;
        }

        // Make sure admin wants to delete
        System.out.println("Are you sure? (type 'Y' to proceed)");
        if (scanner.next().toLowerCase().compareTo("Y") == 0) {
            DatabaseWriter.removeShowtimes(movieTitle, cineplexID, cinemaID, date, "not important", time);

            System.out.println("Showtime removed!");
            System.out.println("========================================================\n");
            System.out.println("Press <Enter> to Exit View");
            scanner.nextLine();
            return;
        } else {
            System.out.println("Showtime remove operation cancelled!");
            System.out.println("========================================================\n");
            System.out.println("Press <Enter> to Exit View");
            scanner.nextLine();
            return;
        }
    }

    /**
     * Allows Admin to update showtime
     */
    private void updateShowtimes() {
        // Print menu to user
        Scanner scanner = new Scanner(System.in);
        int movieChoiceInt = -69;
        MovieDB movieDB = new MovieDB();
        movieDB.sortByAlphabet();
        Movie[] movieArray = movieDB.getMovies();
        Movie movieChoice = null;
        String movieTitle;

        System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
        System.out.flush();
        System.out.println("\n=================== Movie Titles =====================");
        MovieDB.printAllMovieList();
        System.out.println("========================================================\n");

        System.out.println("================= Updating showtimes ===================");
        System.out.print("Movie No.   : ");

        movieChoiceInt = scanner.nextInt();

        if (movieChoiceInt == movieArray.length + 1) {
            System.out.println("Movie not found");
            System.out.println("========================================================\n");
            System.out.println("Press <Enter> to Exit View");
            scanner.nextLine();
            return;
        }

        if (movieChoiceInt <= 0 || movieChoiceInt > movieArray.length + 1) {
            System.out.println("Movie not found");
            System.out.println("========================================================\n");
            System.out.println("Press <Enter> to Exit View");
            scanner.nextLine();
            return;
        }

        movieChoice = movieArray[movieChoiceInt - 1];
        movieTitle = movieChoice.getTitle();
        scanner.nextLine();

        movieChoice.printAllShowtimes();

        System.out.println();
        System.out.println("----------");
        System.out.println();
        String cineplexID = movieChoice.chooseCineplex();

        if (CineplexDB.getCineplexFromID(cineplexID) == null) {
            System.out.println("Cineplex not found");
            System.out.println("========================================================\n");
            System.out.println("Press <Enter> to Exit View");
            scanner.nextLine();
            return;
        }

        System.out.println();
        System.out.println("----------");
        System.out.println();
        String cinemaID = movieChoice.chooseCinema(cineplexID);

        if (CinemaDB.getCinemaFromID(cinemaID) == null) {
            System.out.println("Cinema not found");
            System.out.println("========================================================\n");
            System.out.println("Press <Enter> to Exit View");
            scanner.nextLine();
            return;
        }

        System.out.println();
        System.out.println("----------");
        System.out.println();
        String date = movieChoice.chooseDate(cinemaID);

        System.out.println();
        System.out.println("----------");
        System.out.println();
        String time = movieChoice.chooseTime(date, cinemaID);

        // Check whether show time exists already
        Showtime showtime = null;
        for (Map<String, Map<Showtime, Seat[]>> bla : DatabaseReader.readShowtime(movieTitle).values()) {
            if (bla.keySet().size() == 0)
                break;
            if (!bla.keySet().contains(cinemaID))
                continue;
            for (Showtime show : bla.get(cinemaID).keySet()) {
                if (show == null)
                    break;
                if ((show.date.toLowerCase().compareTo(date) == 0)
                        && (show.time.toLowerCase().compareTo(time) == 0)) {
                    showtime = show;
                    break;
                }
            }
        }
        if (showtime == null) {
            System.out.println("Showtime does not exist");
            System.out.println("========================================================\n");
            System.out.println("Press <Enter> to Exit View");
            scanner.nextLine();
            return;
        }

        // Ask user which one to change
        while (true) {
            System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
            System.out.flush();

            System.out.println("Enter new info (enter exit as movie title to cancel): ");

            System.out.printf("Movie Title @ %-15s: ", movieTitle);
            String newMovieTitle = scanner.nextLine();
            if (newMovieTitle.toLowerCase().compareTo("exit") == 0) {
                break;
            }

            System.out.printf("Cineplex ID @ %-15s: ", cineplexID);
            String newCineplexID = scanner.nextLine();

            System.out.printf("Cinema ID   @ %-15s: ", cinemaID);
            String newCinemaID = scanner.nextLine();

            System.out.printf("Date        @ %-15s: ", date);
            String newDate = scanner.nextLine();

            System.out.printf("Day         @ %-15s: ", showtime.day);
            String day = scanner.nextLine();

            System.out.printf("Time        @ %-15s: ", time);
            String newTime = scanner.nextLine();

            if (MovieDB.getMovieFromTitle(newMovieTitle) == null) {
                System.out.println("Movie not found");
                System.out.println("========================================================\n");
                System.out.println("Press <Enter> to Exit View");
                scanner.nextLine();
                continue;
            }

            if (CineplexDB.getCineplexFromID(newCineplexID) == null) {
                System.out.println("Cineplex not found");
                System.out.println("========================================================\n");
                System.out.println("Press <Enter> to Exit View");
                scanner.nextLine();
                continue;
            }

            if (CinemaDB.getCinemaFromID(cinemaID) == null) {
                System.out.println("Cinema not found");
                System.out.println("========================================================\n");
                System.out.println("Press <Enter> to Exit View");
                scanner.nextLine();
                continue;
            }

            showtime = null;
            for (Map<String, Map<Showtime, Seat[]>> bla : DatabaseReader.readShowtime(newMovieTitle).values()) {
                if (bla.keySet().size() == 0)
                    break;
                if (!bla.keySet().contains(newCinemaID))
                    continue;
                for (Showtime show : bla.get(newCinemaID).keySet()) {
                    if (show == null)
                        break;
                    if ((show.date.toLowerCase().compareTo(newDate) == 0)
                            && (show.time.toLowerCase().compareTo(newTime) == 0)) {
                        showtime = show;
                        break;
                    }
                }
            }
            if (showtime != null) {
                System.out.println("Showtime already exists");
                System.out.println("========================================================\n");
                System.out.println("Press <Enter> to Exit View");
                scanner.nextLine();
                continue;
            }

            DatabaseWriter.updateShowtimeInfo(movieTitle, cineplexID, cinemaID, date, day, time, newMovieTitle,
                    newCineplexID, newCinemaID, newDate, newDate, newTime);

            System.out.println("Showtime updated");
            System.out.println("========================================================\n");
            System.out.println("Press <Enter> to Exit View");
            scanner.nextLine();
            break;
        }
    }

    /**
     * Allows Admin to create showtimes
     */
    private void createShowtimes() {
        // Print menu to user
        Scanner scanner = new Scanner(System.in);
        int movieChoiceInt = -69;
        MovieDB movieDB = new MovieDB();
        movieDB.sortByAlphabet();
        Movie[] movieArray = movieDB.getMovies();
        Movie movieChoice = null;
        String movieTitle;

        System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
        System.out.flush();
        System.out.println("\n=================== Movie Titles =====================");
        MovieDB.printAllMovieList();
        System.out.println("========================================================\n");

        System.out.println("================= Creating showtimes ===================");
        System.out.print("\nMovie No.   : ");

        movieChoiceInt = scanner.nextInt();
        scanner.nextLine();

        if (movieChoiceInt == movieArray.length + 1) {
            System.out.println("Movie not found");
            System.out.println("========================================================\n");
            System.out.println("Press <Enter> to Exit View");
            scanner.nextLine();
            return;
        }

        if (movieChoiceInt <= 0 || movieChoiceInt > movieArray.length + 1) {
            System.out.println("Movie not found");
            System.out.println("========================================================\n");
            System.out.println("Press <Enter> to Exit View");
            scanner.nextLine();
            return;
        }

        movieChoice = movieArray[movieChoiceInt - 1];
        movieTitle = movieChoice.getTitle();

        if (MovieDB.getMovieFromTitle(movieTitle) == null) {
            System.out.println("Movie not found");
            System.out.println("========================================================\n");
            System.out.println("Press <Enter> to Exit View");
            scanner.nextLine();
            return;
        }

        System.out.print("\nCineplex ID : ");
        String cineplexID = scanner.nextLine();

        if (CineplexDB.getCineplexFromID(cineplexID) == null) {
            System.out.println("Cineplex not found");
            System.out.println("========================================================\n");
            System.out.println("Press <Enter> to Exit View");
            scanner.nextLine();
            return;
        }

        System.out.print("\nCinema ID   : ");
        String cinemaID = scanner.nextLine();

        if (CinemaDB.getCinemaFromID(cinemaID) == null) {
            System.out.println("Cinema not found");
            System.out.println("========================================================\n");
            System.out.println("Press <Enter> to Exit View");
            scanner.nextLine();
            return;
        }

        System.out.print("\nDate        : ");
        String date = scanner.nextLine();

        System.out.print("\nDay         : ");
        String day = scanner.nextLine();

        System.out.print("\nTime        : ");
        String time = scanner.nextLine();

        // Check whether show time exists already
        Showtime showtime = null;
        for (Map<String, Map<Showtime, Seat[]>> bla : DatabaseReader.readShowtime(movieTitle).values()) {
            if (bla.keySet().size() == 0)
                break;
            if (!bla.keySet().contains(cinemaID))
                continue;
            for (Showtime show : bla.get(cinemaID).keySet()) {
                if (show == null)
                    break;
                if ((show.date.toLowerCase().compareTo(date) == 0)
                        && (show.time.toLowerCase().compareTo(time) == 0)) {
                    showtime = show;
                    break;
                }
            }
        }
        if (showtime != null) {
            System.out.println("Showtime already exists");
            System.out.println("========================================================\n");
            System.out.println("Press <Enter> to Exit View");
            scanner.nextLine();
            return;
        }

        Seat[] newSeats = new Seat[100];
        for (int i = 0; i < newSeats.length; i++) {
            newSeats[i] = new Seat(String.format("%c%d", 65 + i / 10, i % 10 + 1), false);
        }
        DatabaseWriter.createShowtime(movieTitle, cineplexID, cinemaID, date, day, time, newSeats);

        System.out.println("New showtime created!");
        System.out.println("========================================================\n");
        System.out.println("Press <Enter> to Exit View");
        scanner.nextLine();
    }

    /**
     * Allows Admin to create movie listing
     */
    private void createMovieListing() {
        // Add new movie
        MovieDB.addNewMovie();
    }

    /**
     * Prints the admin menu options
     */
    private void printAdminMenuOptions() {
        System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
        System.out.flush();

        System.out.println("\u001B[36mHello ~~ " + username + " ~~ :)\n\u001B[0m");

        System.out.println("What would you like to do as ADMIN?");
        System.out.println("=====================================================");
        System.out.println(" 1. Create movie listing");
        System.out.println(" 2. Update movie listing");
        System.out.println(" 3. Remove movie listing");
        System.out.println(" 4. Create cinema showtimes and the movies to be shown");
        System.out.println(" 5. Update cinema showtimes and the movies to be shown");
        System.out.println(" 6. Remove cinema showtimes and the movies to be shown");
        System.out.println(" 7. Configure Movie Ticket Prices");
        System.out.println(" 8. Configure Ranking Visibility");
        System.out.println(" 9. View Movie Ticket Prices and Ranking Visibility");
        System.out.println("10. List Movies");
        System.out.println("11. List cineplex ID and cinema ID");
        System.out.println("12. List the Top 5 ranking by ticket sales");
        System.out.println("13. List the Top 5 ranking by overall reviewers' ratings");
        System.out.println("14. Quit");

        System.out.println("=====================================================");
        System.out.print("Enter choice: ");
    }
}