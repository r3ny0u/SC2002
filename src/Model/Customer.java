package Model;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// import javax.sound.midi.Soundbank;

import Database.CineplexDB;
import Database.CustomerDB;
import Database.MovieDB;
import Database.RatingDB;

public class Customer extends Account {
    private String name;
    private String email;
    private String mobile;
    private ArrayList<Transaction> transactions;
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

    public Customer(String customerID, String name, String email, String mobile, ArrayList<Transaction> transactions,
            MovieTicket[] movieTickets) {
        this.accountID = customerID;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.transactions = transactions;
        this.movieTickets = movieTickets;
    }

    /**
     * Check if customer with current username and password exists in database.
     * 
     * @return true if customer exists
     */
    public static boolean checkPassword(String username, String password) {
        return CustomerDB.getCustomerFromUsername(username, password) != null;
    }

    public void printCustomerDetails() {
        System.out.println("Username: " + this.username);
        System.out.println("Password: " + this.password);
        System.out.println("AccountID: " + this.accountID);
        System.out.println("Email: " + this.email);
        System.out.println("Mobile: " + this.mobile);
    }

    // I change the name to customerMenu ah to be consistent with the adminMenu
    // Also I changed it to a do while loop
    public void customerMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = 69420;

        String cinemaChoice, cineplexChoice, seatID, showtimeChoice, dateChoice;
        Cineplex cineplex;
        Cinema cinema;
        int movieChoiceInt;
        Movie movieChoice;

        do {
            MovieDB movies = new MovieDB();
            movies.sortByAlphabet();
            Movie[] movieArray = movies.getMovies();
            Movie[] movieArrayEmpty;

            System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
            System.out.flush();

            System.out.println("\u001B[36mHello ~~ " + username + " ~~ :)\n\u001B[0m");

            printMenu();

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Show movie listings from MovieDB by alphabetical order
                    // Movies with NOT SHOWING status would not be shown
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

                case 3:
                    // display movie list
                    MovieDB.printMovieList();
                    // let customer choose a movie, then display seat availability
                    System.out.printf("Please choose the movie: ");
                    movieChoiceInt = scanner.nextInt();
                    movieChoice = movieArray[movieChoiceInt - 1];
                    // find movie from movies[]
                    movieChoice.printAllShowtimes();
                    System.out.printf("Please choose the cineplex: ");
                    cineplexChoice = scanner.next();
                    System.out.printf("Please choose the cinema: ");
                    cinemaChoice = scanner.next();
                    // use db to find cineplex
                    cineplex = CineplexDB.getCineplexFromID(cineplexChoice);
                    cinema = cineplex.findCinema(cinemaChoice);
                    // let customer choose showtime
                    System.out.printf("Please choose a showing date (eg. 13/11/2022): ");
                    dateChoice = scanner.next();
                    System.out.printf("Please choose the showtime: ");
                    showtimeChoice = scanner.next();
                    // let customer check seat availability
                    System.out.println("Seat availablity is as follows: ");
                    movieChoice.printSeats(cinema.getCinemaID(), dateChoice, showtimeChoice);
                    break;

                case 4:
                    // make a booking
                    // display movie list
                    MovieDB.printMovieList();
                    // let customer select their movie
                    System.out.println("Which movie do you want to book for?");
                    movieChoiceInt = scanner.nextInt();
                    movieChoice = movieArray[movieChoiceInt - 1];
                    // let customer select from the available cinemas
                    movieChoice.printAllShowtimes();
                    System.out.printf("Please choose the cineplex: ");
                    cineplexChoice = scanner.next();
                    System.out.printf("Please choose the cinema: ");
                    cinemaChoice = scanner.next();
                    cineplex = CineplexDB.getCineplexFromID(cineplexChoice);
                    cinema = cineplex.findCinema(cinemaChoice);
                    // let customer choose showtime
                    System.out.printf("Please choose a showing date (eg. 13/11/2022): ");
                    dateChoice = scanner.next();
                    System.out.printf("Please choose the showtime: ");
                    showtimeChoice = scanner.next();
                    movieChoice.printSeats(cinema.getCinemaID(), dateChoice, showtimeChoice);

                    System.out.println("Select the seat you want (eg. A1): ");
                    seatID = scanner.next();
                    boolean avail = movieChoice.checkSeat(cinemaChoice, showtimeChoice, seatID);
                    while (!avail) {
                        System.out.println("Seat is already taken!");
                        System.out.println("Please choose another seat: ");
                        seatID = scanner.next();
                        avail = movieChoice.checkSeat(cinemaChoice, showtimeChoice, seatID);
                    }
                    movieChoice.assignSeat(cinemaChoice, showtimeChoice, seatID, this.name);

                    // do bookings
                    System.out.println("Please enter your age");
                    String age = scanner.next();
                    Transaction newTrans = new Transaction(showtimeChoice, age, cinema, dateChoice, movieChoice,
                            accountID, seatID);
                    System.out.println("Price of ticket : " + newTrans.getTicketPrice());
                    transactions.add(newTrans);
                    System.out.println("Booking Successful! :)");
                    break;

                case 5:
                    // check booking history
                    System.out.println("Your bookings:" + this.transactions);
                    break;

                case 6:
                    // show top 5 based on ticket sales
                    movies.sortBySales();
                    movieArrayEmpty = movies.getMovies();
                    for (int i = 0; i < 5; i++) {
                        System.out.println((i + 1) + ". " + movieArrayEmpty[i].getTitle());
                    }
                    movies.sortByAlphabet();
                    break;

                case 7:
                    // show top 5 based on rating
                    movies.sortByRating();
                    movieArrayEmpty = movies.getMovies();
                    for (int i = 0; i < 5; i++) {
                        System.out.println((i + 1) + ". " + movieArrayEmpty[i].getTitle());
                    }
                    movies.sortByAlphabet();
                    break;

                case 8:
                    // Show movie listings, let user choose movie, and add ratings
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("\n=================== Movie Titles =====================");
                    MovieDB.printMovieList();
                    System.out.printf("%2d. Quit\n", movieArray.length + 1);
                    System.out.println("========================================================\n");

                    // Show movie details corresponding to the movie choice
                    System.out.print("Please choose a movie to add review: ");
                    movieChoiceInt = scanner.nextInt();

                    if (movieChoiceInt == movieArray.length + 1)
                        break;

                    if (movieChoiceInt <= 0 || movieChoiceInt > movieArray.length + 1)
                        continue;

                    movieChoice = movieArray[movieChoiceInt - 1];

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

                    // System.out.println("Which movie would you like to add a review for?");
                    // MovieDB.printMovieList();
                    // System.out.printf("Enter your movie choice: ");
                    // movieChoiceInt = scanner.nextInt();
                    // movieChoice = movieArray[movieChoiceInt - 1];
                    // System.out.println("How many stars out of 5 would you give this movie?");
                    // System.out.printf("Enter your rating: ");
                    // int stars = scanner.nextInt();
                    // System.out.println("What is your review for this movie?");
                    // System.out.printf("Enter your review: ");
                    // String review = scanner.nextLine();
                    // movieChoice.addReviews(this.accountID, review, stars);
                    break;

                case 9:
                    break;

                default:
                    break;
            }
        } while (choice != 9);
    }

    public static void printMenu() {
        System.out.println("What would you like to do today?");
        System.out.println("-----------------------------------------------------");
        System.out.println(" 1. View movie listings");
        System.out.println(" 2. View movie details");
        System.out.println(" 3. Check seat availability");
        System.out.println(" 4. Make a booking");
        System.out.println(" 5. Check my booking history");
        System.out.println(" 6. List the Top 5 ranking by ticket sales");
        System.out.println(" 7. List the Top 5 ranking by overall reviewers' ratings");
        System.out.println(" 8. Add review for a movie");
        System.out.println(" 9. Quit");
        System.out.println("-----------------------------------------------------");
    }
}