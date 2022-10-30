package Model;
import java.util.Scanner;

public class Customer extends Account{
    private String name;
    private String email;
    private String mobile;
    private Booking[] bookings;
    private MovieTicket[] movieTickets;

    public Customer() {
    }

    public Customer(String username, String password, String accountID) {
        this.username = username;
        this.password = password;
        this.accountID = accountID;
        // this.name = name;
        // this.email = email;
        // this.mobile = mobile;
        // this.bookings = bookings;
        // this.movieTickets = movieTickets;
    }

    public void customerInit() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\nWelcome new customer :3\n");

        System.out.printf("Please enter your name: ");
        this.name = sc.nextLine();

        System.out.printf("Please enter your email: ");
        this.email = sc.nextLine();

        System.out.printf("Please enter your mobile phone number: ");
        this.mobile = sc.nextLine();

        System.out.printf("Please enter your customerID: ");
        this.username = sc.nextLine();

        System.out.printf("Hello %s! Welcome to MOBLIMA, the world most advanced cinema booking system.\n",
                this.name);
    }

    public void printCustomerDetails() {
        // System.out.println("\nHere are your details UwU\n");

        // System.out.println("Name: " + this.name);
        // System.out.println("Email: " + this.email);
        // System.out.println("Mobile: " + this.mobile);
        
        System.out.println("Username: " + this.username);
        System.out.println("Password: " + this.password);
        System.out.println("AccountID: " + this.accountID);

        // System.out.println("\n");
    }

    public void queryPurpose() {
        System.out.println("What would you like to do today?");
        System.out.println("1. View movie listings");
        System.out.println("2. View movie details");
        System.out.println("3. Check seat availability");
        System.out.println("4. Make a booking");
        System.out.println("5. Check my booking history");
        System.out.println("6. List the Top 5 ranking by ticket sales");
        System.out.println("7. List the Top 5 ranking by overall reviewers’ ratings");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                // show movie listings
                break;
            case 2:
                // show movie details
                break;
            case 3:
                // display movie list
                // let customer choose a movie, then display seat availability
                break;
            case 4:
                // make a booking
                break;
            case 5:
                // check booking history
                break;
            case 6:
                // show top 5 based on ticket sales
                break;
            case 7:
                // show top 5 based on rating
            default:
                do {
                    System.out.println("Invalid. Try again.");
                    System.out.print("Enter choice: ");
                    choice = scanner.nextInt();
                } while (choice < 1 || choice > 7);
        }
    }

    public static void main(String[] args) {
        Customer c = new Customer();
        c.customerInit();
        c.printCustomerDetails();
        c.queryPurpose();
    }
}

// Test inputs here
// Snoop Dogg
// snoopdogg@gmail.com
// 98769420
// smokeWeedEveryDay