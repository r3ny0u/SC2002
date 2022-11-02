package Model;

import java.util.Scanner;

import Database.AdminDB;

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
    public boolean checkPassword() {
        return AdminDB.getAdminFromUsername(username, password) != null;
    }

    public int adminMenu() {
        if (!this.checkPassword())
            return 0;

        System.out.println("Welcome");

        Scanner s = new Scanner(System.in);
        int choice = s.nextInt();

        do {
            printAdminOptions();

            switch (choice) {
                case 1:
                    System.out.println("Enter the movie title you wish to create");
                    String movieName = s.next();
                    createMovieListing(movieName);
                    break;

                case 2:
                    updateMovieListing();
                    break;

                case 3:
                    removeMovieListing();
                    break;

                case 4:
                    createShowtimes();
                    break;

                case 5:
                    updateShowtimes();
                    break;

                case 6:
                    removeShowtimes();
                    break;

                case 7:
                    configSys();
                    break;

                case 8:
                    return 1;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 8);

        return 0;

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
        // check whether movie already exisits
        // if not ask for the details and add in

    }

    // TODO: Maybe can add more menu?? Like add cineplex or remove cinemas?? what am
    // i even talking about -b
    private void printAdminOptions() {
        System.out.println("What would you like to do?");
        System.out.println("-----------------------------------------------------");
        System.out.println("1. Create movie listing");
        System.out.println("2. Update movie listing");
        System.out.println("3. Remove movie listing");
        System.out.println("4. Create cinema showtimes and the movies to be shown");
        System.out.println("5. Update cinema showtimes and the movies to be shown");
        System.out.println("6. Remove cinema showtimes and the movies to be shown");
        System.out.println("7. Configure system settings");
        System.out.println("8. Quit");
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