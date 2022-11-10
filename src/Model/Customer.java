package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import Database.CinemaDB;

// import javax.sound.midi.Soundbank;

import Database.CineplexDB;
import Database.CustomerDB;
import Database.MovieDB;
import Database.RatingDB;
import Database.ShowtimeDB;
import Database.TransactionDB;
import DatabaseBoundary.DatabaseReader;

public class Customer extends Account {
    private String name;
    private String email;
    private String mobile;
    private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
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
        this.loadCustomerTransaction();
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

    public void loadCustomerTransaction() {
        this.transactions.clear();
        TransactionDB transactionDB = new TransactionDB();
        for (Transaction transaction : transactionDB.getTransactions()) {
            if (transaction.getCustomerID().compareTo(this.accountID.toLowerCase()) == 0) {
                this.transactions.add(transaction);
            }
        }
    }

    public void printCustomerTransactionHistory() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\033[H\033[2J"); // Clear screen and flush output buffer
        System.out.flush();
        System.out.println("\n=================== Transactions =====================");

        if (this.transactions.size() == 0) {
            System.out.println("No transactions recorded");
        } else {
            for (Transaction transaction : this.transactions) {
                System.out.printf("  %s - %.2f - %s\n", transaction.getTransactionId(), transaction.getTicketPrice(),
                        transaction.getMovie().getTitle());
            }
        }

        System.out.println("======================================================\n");
        System.out.println("Press <Enter> to Exit View");
        scanner.nextLine();
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
            MovieDB movieDB = new MovieDB();
            movieDB.sortByAlphabet();
            Movie[] movieArray = movieDB.getMovies();
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

                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        movieChoice.printMovieDetails();

                        System.out.println("Press <Enter> to Exit View");
                        scanner.nextLine();
                        scanner.nextLine();
                    } while (choice != movieArray.length + 1);

                    break;

                case 3:
                    // display movie list
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("\n=================== Movie Titles =====================");
                    MovieDB.printMovieList();
                    System.out.printf("%2d. Quit\n", movieArray.length + 1);
                    System.out.println("========================================================\n");

                    // Show movie details corresponding to the movie choice
                    System.out.print("Please choose a movie to book: ");
                    movieChoiceInt = scanner.nextInt();

                    if (movieChoiceInt == movieArray.length + 1)
                        break;

                    if (movieChoiceInt <= 0 || movieChoiceInt > movieArray.length + 1)
                        continue;

                    movieChoice = movieArray[movieChoiceInt - 1];
                    scanner.nextLine();

                    // Print our movie detail for user to see before adding review
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    movieChoice.printMovieDetails();
                    System.out.println("======================== Seats ==========================\n");

                    // Print all showtimes for customer to see, then let customer choose cineplex,
                    // cinema, date and time
                    movieChoice.printAllShowtimes();
                    System.out.printf("Please choose a cineplex : ");
                    cineplexChoice = scanner.nextLine();

                    cineplex = CineplexDB.getCineplexFromID(cineplexChoice);

                    if (cineplex == null) {
                        System.out.println("Cineplex does not exist...");
                        System.out.println("Press <Enter> to Exit View");
                        scanner.nextLine();
                        break;
                    }

                    System.out.printf("Please choose a cinema   : ");
                    cinemaChoice = scanner.nextLine();
                    cinema = cineplex.findCinema(cinemaChoice);

                    if (cinema == null) {
                        System.out.println("Cinema does not exist...");
                        System.out.println("Press <Enter> to Exit View");
                        scanner.nextLine();
                        break;
                    }

                    // User to input date and showtime, to get seats
                    System.out.printf("\nPlease choose a showing date (eg. 2022/11/13) : ");
                    dateChoice = scanner.nextLine();

                    System.out.printf("Please choose the showtime                    : ");
                    showtimeChoice = scanner.nextLine();

                    movieChoice.printSeats(cinema.getCinemaID(), dateChoice, showtimeChoice);

                    System.out.println("Press <Enter> to Exit View");
                    scanner.nextLine();

                    // find movie from movies[]
                    // movieChoice.printAllShowtimes();
                    // System.out.printf("Please choose the cineplex: ");
                    // cineplexChoice = scanner.next();
                    // System.out.printf("Please choose the cinema: ");
                    // cinemaChoice = scanner.next();
                    // // use db to find cineplex
                    // cineplex = CineplexDB.getCineplexFromID(cineplexChoice);
                    // cinema = cineplex.findCinema(cinemaChoice);
                    // // let customer choose showtime
                    // System.out.printf("Please choose a showing date (eg. 13/11/2022): ");
                    // dateChoice = scanner.next();
                    // System.out.printf("Please choose the showtime: ");
                    // showtimeChoice = scanner.next();
                    // // let customer check seat availability
                    // System.out.println("Seat availablity is as follows: ");
                    // movieChoice.printSeats(cinema.getCinemaID(), dateChoice, showtimeChoice);
                    break;

                case 4:
                    // TODO: Check for date and time is valid, if got error see can put into while
                    // TODO: loop or not
                    // Make a booking: Print out movie list, let customer choose movie, cineplex,
                    // and cinema (gotten from the moive object itself), then the showing time, and
                    // seats, before choose each of the things, print out the available options for
                    // customer
                    // Extra can do: If in any point of choosing, customer type in exit or other
                    // phrase, system would break into customer menu page
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("\n=================== Movie Titles =====================");
                    MovieDB.printMovieList();
                    System.out.printf("%2d. Quit\n", movieArray.length + 1);
                    System.out.println("========================================================\n");

                    // Show movie details corresponding to the movie choice
                    System.out.print("Please choose a movie to book: ");
                    /*
                    movieChoiceInt = scanner.nextInt();

                    if (movieChoiceInt == movieArray.length + 1){
                        System.out.print("Error, invalid choice!! ");
                        System.out.println("Press <Enter> to Exit View");
                        scanner.nextLine();
                        break;
                    }*/
                    do{
                        while (!scanner.hasNextInt()){
                            System.out.print("Error, invalid choice!! Try again: ");
                            scanner.next();
                        }
                        movieChoiceInt = scanner.nextInt();
                        if (movieChoiceInt > movieArray.length + 1 || movieChoiceInt<=0){
                            System.out.print("Error, invalid choice!! Try again: ");
                        }
                    }while(movieChoiceInt > movieArray.length + 1 || movieChoiceInt<=0);

                    if (movieChoiceInt == movieArray.length + 1){
                        scanner.nextLine();
                        System.out.println("Press <Enter> to Exit View");
                        scanner.nextLine();
                        break;
                    }

                    //if (movieChoiceInt <= 0 || movieChoiceInt > movieArray.length + 1)
                        //continue;

                    movieChoice = movieArray[movieChoiceInt - 1];
                    scanner.nextLine();

                    // Print our movie detail for user to see before adding review
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    movieChoice.printMovieDetails();
                    System.out.println("======================= Booking ========================\n");

                    // Print all showtimes for customer to see, then let customer choose cineplex,
                    // cinema, date and time
                    movieChoice.printAllShowtimes();
                    if (movieChoice.showingPlaces.size() == 0) {
                        System.out.println("Sorry, the movie is not available for booking.");
                        System.out.println("Press <Enter> to Exit View");
                        scanner.nextLine();
                        break;
                    }
                    System.out.printf("Please choose a cineplex : ");
                    cineplexChoice = scanner.nextLine();

                    cineplex = CineplexDB.getCineplexFromID(cineplexChoice);

                    if (cineplex == null) {
                        System.out.println("Cineplex does not exist...");
                        System.out.println("Press <Enter> to Exit View");
                        scanner.nextLine();
                        break;
                    }

                    System.out.printf("Please choose a cinema   : ");
                    cinemaChoice = scanner.nextLine();
                    cinema = cineplex.findCinema(cinemaChoice);

                    if (cinema == null) {
                        System.out.println("Cinema does not exist...");
                        System.out.println("Press <Enter> to Exit View");
                        scanner.nextLine();
                        break;
                    }

                    // User to input date and showtime, to get seats
                    System.out.printf("\nPlease choose a showing date (eg. 2022/11/13) : ");
                    dateChoice = scanner.nextLine();

                    System.out.printf("Please choose the showtime                    : ");
                    showtimeChoice = scanner.nextLine();

                    // Check if showtime exists
                    Showtime showtime = null;
                    for (Map<String, Map<Showtime, Seat[]>> bla : DatabaseReader.readShowtime(movieChoice.getTitle()).values()) {
                        if (bla.keySet().size() == 0)
                            break;
                        if (!bla.keySet().contains(cinemaChoice))
                            continue;
                        for (Showtime show : bla.get(cinemaChoice).keySet()) {
                            if (show == null)
                                break;
                            if ((show.date.toLowerCase().compareTo(dateChoice) == 0)
                                    && (show.time.toLowerCase().compareTo(showtimeChoice) == 0)) {
                                showtime = show;
                                break;
                            }
                        }
                    }
                    if (showtime == null) {
                        System.out.println("Show time does not exist");
                        System.out.println("Press <Enter> to Exit View");
                        scanner.nextLine();
                        break;
                    }

                    movieChoice.printSeats(cinema.getCinemaID(), dateChoice, showtimeChoice);

                    // User to choose seats they want (check for empty seats)
                    System.out.printf("Select the seat you want (eg. A1): ");
                    seatID = scanner.next();

                    // Second choice idk what's it for
                    showtime = new Showtime(dateChoice, "day", showtimeChoice);

                    boolean assigned = movieChoice.checkSeat(cinemaChoice, dateChoice, showtimeChoice, seatID);
                    while (assigned) {
                        System.out.print("Seat is already taken!");
                        // System.out.print("\033[1K\033[1K"); // Erase line content
                        // System.out.print(String.format("\033[1A")); // Move up 1
                        System.out.print("Please choose another seat (eg. A1): ");
                        seatID = scanner.next();
                        assigned = movieChoice.checkSeat(cinemaChoice, dateChoice, showtimeChoice, seatID);
                    }

                    Seat[] newSeats = movieChoice.assignSeat(cinemaChoice, dateChoice, showtimeChoice, seatID,
                            this.accountID);

                    // User to input age
                    System.out.printf("Please enter your age: ");
                    scanner.nextLine();
                    String age = scanner.nextLine();

                    // Booking process done, creating transaction
                    Transaction newTrans = new Transaction(showtimeChoice, age, cinema, dateChoice, movieChoice,
                            accountID, seatID);

                    System.out.println("Price of ticket : " + newTrans.getTicketPrice());

                    transactions.add(newTrans);

                    TransactionDB.addNewTransaction(newTrans);
                    ShowtimeDB.updateShowtimes(movieChoice.getTitle(), cineplexChoice, cinemaChoice, dateChoice, "day",
                            showtimeChoice, newSeats);
                    movieChoice.loadShowtimes();

                    // Continue booking progress
                    System.out.println("\nBooking successful :) ...");

                    // Sleep for 1.5s then return to customer menu page
                    try {
                        TimeUnit.MILLISECONDS.sleep(1500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 5:
                    // check booking history
                    this.printCustomerTransactionHistory();
                    break;

                case 6:
                    // Show top 5 movies based on ticket sales
                    movieArrayEmpty = new Movie[5];
                    movieDB.sortBySales();
                    movieArrayEmpty = Arrays.copyOfRange(movieDB.getMovies(), 0, 5);

                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("\n=============== Movie Titles by Sales ================");
                    for (int i = 0; i < 5; i++) {
                        if (movieArrayEmpty[i] == null)
                            break;
                        System.out.printf("%2d. %s: %d tickets sold\n", i + 1, movieArrayEmpty[i].getTitle(),
                                movieArrayEmpty[i].getSalesCount());
                    }
                    System.out.println("========================================================\n");
                    movieDB.sortByAlphabet();

                    System.out.println("Press <Enter> to Exit View");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;

                case 7:
                    // Show top 5 movies based on ratings
                    movieDB = new MovieDB();
                    movieDB.sortByRating();
                    Movie[] movies = movieDB.getMovies();
                    movieArrayEmpty = movies.clone();

                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("\n============== Movie Titles by Ratings ===============");
                    for (int i = 0; i < 5; i++) {
                        if (movieArrayEmpty[i] == null)
                            break;
                        System.out.printf("%2d. (%.1f / 5.0 Stars) %s\n", i + 1, movieArrayEmpty[i].getRating(),
                                movieArrayEmpty[i].getTitle());
                    }
                    System.out.println("========================================================\n");

                    System.out.println("Press <Enter> to Exit View");
                    scanner.nextLine();
                    scanner.nextLine();
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
        System.out.println("====================================================");
        System.out.println(" 1. View movie listings");
        System.out.println(" 2. View movie details");
        System.out.println(" 3. Check seat availability");
        System.out.println(" 4. Make a booking");
        System.out.println(" 5. Check my booking history");
        System.out.println(" 6. List the Top 5 ranking by ticket sales");
        System.out.println(" 7. List the Top 5 ranking by overall reviewers' ratings");
        System.out.println(" 8. Add review for a movie");
        System.out.println(" 9. Quit");
        System.out.println("====================================================");
    }
}