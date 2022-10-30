package Model;
import java.util.Scanner;

public class Admin {
    private String adminID;
    private String pw;

    public Admin(String adminID, String pw) {
        this.adminID = adminID;
        this.pw = pw;
    }

    public int checkPW() {
        if (adminID.compareTo(Constants.ADMIN_ID) == 0 && pw.compareTo(Constants.ADMIN_PASSWORD) == 0) {
            System.out.println("Welcome");

            while (true) {
                printAdminOptions();
                Scanner s = new Scanner(System.in);
                int choice = s.nextInt();

                switch (choice) {
                    case 1:
                        createMovieListing();
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
            }
        }

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

    }

    private void updateMovieListing() {

    }

    private void createMovieListing() {

    }

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

}