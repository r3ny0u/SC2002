package Model;

import java.util.ArrayList;
import java.util.Scanner;

import Database.AdminDB;
import Database.CineplexDB;
import Database.MovieDB;
import Database.MovieTicketConfig;
import DatabaseBoundary.DatabaseWriter;

public class Admin extends Account {
    // DO NOT MODIFY THIS CONSTRUCTOR, MAKE ANOTHER IF YOU NEED ANOTHER CONSTRUCTOR
    public Admin(String username, String password, String accountID) {
        this.username = username;
        this.password = password;
        this.accountID = accountID;
    }

    /**
     * Check if admin with current username and password exists in database.
     * 
     * @return true if admin exists
     */
    public static boolean checkPassword(String username, String password) {
        return AdminDB.getAdminFromUsername(username, password) != null;
    }

    public void adminMenu() {
        if (!checkPassword(username, password))
            return;

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            printAdminOptions();

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
                    System.out.println("Enter the movie title you wish to create: ");
                    createMovieListing();
                    break;

                case 2:
                    // Show movie titles, let admin choose movie to edit details
                    // shit so messy, just close your eyes when working on this
                    movies = new MovieDB();
                    movies.sortByAlphabet();
                    movieArray = movies.getMovies();

                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("\n=================== Movie Titles =====================");
                    MovieDB.printMovieList();
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
                            System.out.printf("New casts: (enter exit to complete entry)");
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
                    System.out.printf("Editing \"%s\"...\n", movieChoice.getTitle());

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
                    MovieDB.printMovieList();
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
                    // Configure system settings
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    configSys();
                    break;

                case 8:
                    // Print sysem configurations
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("-----------------------------------------------------");
                    MovieTicketConfig.printConfigDetails();
                    System.out.println("-----------------------------------------------------");
                    System.out.println("Press <Enter> to Exit View");

                    scanner.nextLine();
                    scanner.nextLine(); // Wait for user to press enter
                    break;

                case 9:
                    // Print movie listings, and let admin choose details, i copied this from
                    // customer side
                    do {
                        // Show movie listings
                        System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                        System.out.flush();
                        System.out.println("\n=================== Movie Titles =====================");
                        MovieDB.printMovieList();
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

                case 10:
                    // Show cineplex and cinemas (easier to debug lol)
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("\n==================== Cineplexes ======================");
                    CineplexDB.printCineplexDBDetails();
                    System.out.println("========================================================\n");
                    System.out.println("Press <Enter> to Exit View");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;

                case 11:
                    // Exit
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("\u001B[36mGoodbye... ~~ " + username + " ~~ :)\n\u001B[0m");
                    return;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 11);

        return;
    }

    private void configSys() {
        // Configure ticket price and other things
        Scanner scanner = new Scanner(System.in);
        int userChoice = 11;
        float newValue = 69.420f;

        do {
            System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
            System.out.flush();

            System.out.println("Which settings to update?");
            System.out.println("-----------------------------------------------------");
            MovieTicketConfig.printConfigDetails();
            System.out.println("11. Quit");
            System.out.println("-----------------------------------------------------");
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

    private void removeShowtimes() {

    }

    private void updateShowtimes() {

    }

    private void createShowtimes() {

    }

    private void removeMovieListing() {
        // Remove movie
        MovieDB.removeMovie();
    }

    private void updateMovieListing() {
        // find the movie in db
        // check which part needs to be updated
        // update that part
    }

    private void createMovieListing() {
        // Add new movie
        MovieDB.addNewMovie();
    }

    // TODO: Maybe can add more menu?? Like add cineplex or remove cinemas?? what am
    // i even talking about -b
    private void printAdminOptions() {
        System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
        System.out.flush();

        System.out.println("\u001B[36mHello ~~ " + username + " ~~ :)\n\u001B[0m");

        System.out.println("What would you like to do as ADMIN?");
        System.out.println("-----------------------------------------------------");
        System.out.println(" 1. Create movie listing");
        System.out.println(" 2. Update movie listing");
        System.out.println(" 3. Remove movie listing");
        System.out.println(" 4. Create cinema showtimes and the movies to be shown");
        System.out.println(" 5. Update cinema showtimes and the movies to be shown");
        System.out.println(" 6. Remove cinema showtimes and the movies to be shown");
        System.out.println(" 7. Configure Movie Ticket Prices and System Settings");
        System.out.println(" 8. View Movie Ticket Prices and System Settings");
        System.out.println(" 9. List Movies");
        System.out.println("10. List cineplex ID and cinema ID");
        System.out.println("11. Quit");
        System.out.println("-----------------------------------------------------");
        System.out.print("Enter choice: ");
    }

    // TODO: Remove this method after debuggging
    public void printAdminDetails() {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("AccountID: " + accountID);
    }
}