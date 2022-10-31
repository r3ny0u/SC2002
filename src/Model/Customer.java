package Model;

import java.util.Scanner;

import javax.sound.midi.Soundbank;

import Database.CineplexDB;
import Database.MovieDB;

public class Customer extends Account {
    private String name;
    private String email;
    private String mobile;
    private Booking[] bookings;
    private MovieTicket[] movieTickets;

    public Customer() {
    }

    // DO NOT MODIFY THIS CONSTRUCTOR, MAKE ANOTHER IF YOU NEED ANOTHER CONSTRUCTOR
    public Customer(String username, String password, String accountID, String email, String mobile) {
        this.username = username;
        this.password = password;
        this.accountID = accountID;
        this.email = email;
        this.mobile = mobile;
    }

    public Customer(String customerID, String name, String email, String mobile, Booking[] bookings,
            MovieTicket[] movieTickets) {
        this.accountID = customerID;
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
        this.accountID = sc.nextLine();

        System.out.printf("Hello %s! Welcome to MOBLIMA, the world most advanced cinema booking system.\n",
                this.name);
    }

    public void printCustomerDetails() {
        // System.out.println("\nHere are your details UwU\n");

        // System.out.println("Name: " + this.name);
        // System.out.println("ID: " + this.accountID);

        // System.out.println("\n");
        System.out.println("Username: " + this.username);
        System.out.println("Password: " + this.password);
        System.out.println("AccountID: " + this.accountID);
        System.out.println("Email: " + this.email);
        System.out.println("Mobile: " + this.mobile);
    }

    /*
     * public void queryPurpose() {
     * MovieDB movies = new MovieDB();
     * String movieChoiceString, cinemaChoice, cineplexChoice, seatID;
     * Model.Movie movieChoice;
     * 
     * 
     * System.out.println("What would you like to do today?");
     * System.out.println("1. View movie listings");
     * System.out.println("2. View movie details");
     * System.out.println("3. Check seat availability");
     * System.out.println("4. Make a booking");
     * System.out.println("5. Check my booking history");
     * System.out.println("6. List the Top 5 ranking by ticket sales");
     * System.out.println("7. List the Top 5 ranking by overall reviewers’ ratings"
     * );
     * System.out.println("8. Add your review for a movie");
     * Scanner scanner = new Scanner(System.in);
     * int choice = scanner.nextInt();
     * switch (choice) {
     * case 1:
     * // show movie listings
     * // read from db
     * movies.printMovieList();
     * break;
     * case 2:
     * // show movie details
     * // read from db
     * movies.printMovieList();
     * System.out.println("Please choose the movie");
     * movieChoiceString = scanner.next();
     * movieChoice = MovieDB.getMovieFromTitle(movieChoiceString);
     * movieChoice.printMovieDetails();
     * break;
     * case 3:
     * // display movie list
     * movies.printMovieList();
     * // let customer choose a movie, then display seat availability
     * System.out.println("Please choose the movie");
     * movieChoiceString = scanner.next();
     * movieChoice = MovieDB.getMovieFromTitle(movieChoiceString);
     * // find movie from movies[]
     * System.out.println("Please choose the cineplex");
     * cineplexChoice = scanner.next();
     * System.out.println("Please choose the cinema");
     * cinemaChoice = scanner.next();
     * // use db to find cineplex
     * Cineplex cineplex = CineplexDB.findCineplex(cineplexChoice);
     * Cinema cinema = cineplex.findCinema(cinemaChoice);
     * cinema.findMovie(movieChoiceString).printSeats(cinemaChoice);
     * 
     * break;
     * case 4:
     * // make a booking
     * // display movie list
     * movies.printMovieList();
     * // let customer select their movie
     * System.out.println("Which movie do you want to book for?");
     * movieChoiceString = scanner.next();
     * // let customer select from the available cinemas
     * System.out.println("Please choose the cineplex");
     * cineplexChoice = scanner.next();
     * System.out.println("Please choose the cinema");
     * cinemaChoice = scanner.next();
     * Cineplex cineplex = CineplexDB.findCineplex(cineplexChoice);
     * Cinema cinema = cineplex.findCinema(cinemaChoice);
     * Movie movie = cinema.findMovie(movieChoiceString);
     * movie.printSeats(cinemaChoice);
     * 
     * System.out.println("Select the seat you want (eg. A1)");
     * seatID = scanner.next();
     * boolean avail = movie.checkSeat(cinemaChoice,seatID);
     * while(!avail)
     * {
     * System.out.println("Seat is already taken!");
     * System.out.println("Please choose another seat");
     * seatID = scanner.next();
     * avail = movie.checkSeat(cinemaChoice,seatID);
     * }
     * 
     * //do bookings
     * 
     * break;
     * case 5:
     * // check booking history
     * System.out.println("Your bookings:" + this.bookings);
     * break;
     * case 6:
     * // show top 5 based on ticket sales
     * break;
     * case 7:
     * // show top 5 based on rating
     * case 8:
     * System.out.println("Which movie would you like to add a review for?");
     * movies.printMovieList();
     * movieChoiceString = scanner.next();
     * movieChoice = MovieDB.getMovieFromTitle(movieChoiceString);
     * System.out.println("How many stars out of 5 would you give this movie?");
     * int stars = scanner.nextInt();
     * System.out.println("What is your review for this movie?");
     * String review = scanner.nextLine();
     * movieChoice.addReviews(this.accountID,review,stars);
     * default:
     * do {
     * System.out.println("Invalid. Try again.");
     * System.out.print("Enter choice: ");
     * choice = scanner.nextInt();
     * } while (choice < 1 || choice > 8);
     * }
     * }
     */
    public static void main(String[] args) {
        Customer c = new Customer();
        c.customerInit();
        c.printCustomerDetails();
        // c.queryPurpose();
    }
}