package Model;

import java.util.Scanner;

import Database.AdminDB;
import Database.MovieDB;
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
        String temp;

        do {
            printAdminOptions();

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Create new movie
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("Enter the movie title you wish to create: ");
                    String movieName = scanner.next();
                    createMovieListing(movieName);
                    break;

                case 2:
                    // Update movie
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("Enter the movie title you wish to update: ");
                    updateMovieListing();
                    break;

                case 3:
                    // Remove movie
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("Enter the movie title you wish to remove: ");
                    removeMovieListing();
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
                    System.out.println("IDK i didn't think i'll get this far \\_(0.o)_/: ");
                    configSys();
                    break;

                case 8:
                    // Print movie details
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("Movie Details:\n");
                    MovieDB.printMovieDBDetails();
                    System.out.println("Press Enter to exit view");

                    scanner.nextLine();
                    temp = scanner.nextLine(); // Wait for user to press enter
                    break;

                case 9:
                    // Exit
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("\u001B[36mGoodbye... ~~ " + username + " ~~ :)\n\u001B[0m");
                    return;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 9);

        return;

    }

    private void configSys() {

    }

    private void removeShowtimes() {

    }

    private void updateShowtimes() {

    }

    private void createShowtimes() {

    }

    private void removeMovieListing() {
        // find movie in db
        // delete
    }

    private void updateMovieListing() {
        // find the movie in db
        // check which part needs to be updated
        // update that part
    }

    private void createMovieListing(String movieName) {
        MovieDB.addNewMovie();
        // check whether movie already exisits
        // if not ask for the details and add in

    }

    // TODO: Maybe can add more menu?? Like add cineplex or remove cinemas?? what am
    // i even talking about -b
    private void printAdminOptions() {
        System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
        System.out.flush();

        System.out.println("\u001B[36mHello ~~ " + username + " ~~ :)\n\u001B[0m");

        System.out.println("What would you like to do as ADMIN?");
        System.out.println("-----------------------------------------------------");
        System.out.println("1. Create movie listing");
        System.out.println("2. Update movie listing");
        System.out.println("3. Remove movie listing");
        System.out.println("4. Create cinema showtimes and the movies to be shown");
        System.out.println("5. Update cinema showtimes and the movies to be shown");
        System.out.println("6. Remove cinema showtimes and the movies to be shown");
        System.out.println("7. Configure Movie Ticket Prices and System Settings");
        System.out.println("8. List Movies");
        System.out.println("9. Quit");
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