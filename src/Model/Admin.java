package Model;

import java.util.Scanner;

import Database.AdminDB;
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

            switch (choice) {
                case 1:
                    // Create new movie
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("Enter the movie title you wish to create: ");
                    createMovieListing();
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
                    // Print movie details
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("Details of " + new MovieDB().getMovies().length + " Movies:");
                    MovieDB.printMovieDBDetails();
                    System.out.println("Press <Enter> to Exit View");

                    scanner.nextLine();
                    scanner.nextLine(); // Wait for user to press enter
                    break;

                case 10:
                    // Exit
                    System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
                    System.out.flush();
                    System.out.println("\u001B[36mGoodbye... ~~ " + username + " ~~ :)\n\u001B[0m");
                    return;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 10);

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

            System.out.println("Which settings to udpate?");
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
                    System.out.printf("Updating 2D Movie Markup from %-2.2f %% to ",
                            MovieTicketConfig.get2DMoviePercentage());
                    newValue = scanner.nextFloat();
                    MovieTicketConfig.update2DMoviePercentage(newValue);
                    break;

                case 5:
                    System.out.printf("Updating 3D Movie markup from %-2.2f %% to ",
                            MovieTicketConfig.get3DMoviePercentage());
                    newValue = scanner.nextFloat();
                    MovieTicketConfig.update3DMoviePercentage(newValue);
                    break;

                case 6:
                    System.out.printf("Updating Normal Cinema Markup from %-2.2f %% to ",
                            MovieTicketConfig.getNormalCinemaPercentage());
                    newValue = scanner.nextFloat();
                    MovieTicketConfig.updateNormalCinemaPercentage(newValue);
                    break;

                case 7:
                    System.out.printf("Updating Platinum Cinema Markup from %-2.2f %% to ",
                            MovieTicketConfig.getPlatinumCinemaPercentage());
                    newValue = scanner.nextFloat();
                    MovieTicketConfig.updatePlatinumCinemaPercentage(newValue);
                    break;

                case 8:
                    System.out.printf("Updating Adult Markup from %-2.2f %% to ",
                            MovieTicketConfig.getAdultPercentage());
                    newValue = scanner.nextFloat();
                    MovieTicketConfig.updateAdultPercentage(newValue);
                    break;

                case 9:
                    System.out.printf("Updating Senior Markup from %-2.2f %% to ",
                            MovieTicketConfig.getSeniorPercentage());
                    newValue = scanner.nextFloat();
                    MovieTicketConfig.updateSeniorPercentage(newValue);
                    break;

                case 10:
                    System.out.printf("Updating Child Markup from %-2.2f %% to ",
                            MovieTicketConfig.getChildPercentage());
                    newValue = scanner.nextFloat();
                    MovieTicketConfig.updateChildPercentage(newValue);
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
        System.out.println("10. Quit");
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