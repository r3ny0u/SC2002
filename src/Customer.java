import java.util.Scanner;

public class Customer {
    private String customerID;
    private String name;
    private String email;
    private String mobile;
    private Booking[] bookings;
    private MovieTicket[] movieTickets;

    public Customer() {
    }

    public Customer(String customerID, String name, String email, String mobile, Booking[] bookings,
            MovieTicket[] movieTickets) {
        this.customerID = customerID;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.bookings = bookings;
        this.movieTickets = movieTickets;
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
        this.customerID = sc.nextLine();

        System.out.printf("Hello %s! Welcome to MOBLIMA, the world most advanced cinema booking system.\n",
                this.name);
    }

    public void printCustomerDetails() {
        System.out.println("\nHere are your details UwU\n");

        System.out.println("Name: " + this.name);
        System.out.println("Email: " + this.email);
        System.out.println("Mobile: " + this.mobile);
        System.out.println("ID: " + this.customerID);

        System.out.println("\n");
    }

    public static void main(String[] args) {
        Customer c = new Customer();
        c.customerInit();
        c.printCustomerDetails();
    }
}

// Test inputs here
// Snoop Dogg
// snoopdogg@gmail.com
// 98769420
// smokeWeedEveryDay