package Model;

import java.util.Date;
import java.util.Locale;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * A class for transactions
 */
public class Transaction {
    private String transactionID;
    private String time;
    private String age;
    private String cinemaType;
    private String movieType;
    private String dayOfWeek;
    private Movie movie;
    private String customerID;
    private String seatID;
    private String bookingTime;
    private float ticketPrice;
    private boolean isPH = false;

    public Transaction(String transactionID, String time, String age, String cinemaType, String movieType,
            String dayOfWeek, Movie movie, String customerID) {
        this.transactionID = transactionID;
        this.time = time;
        this.age = age;
        this.cinemaType = cinemaType;
        this.movieType = movieType;
        this.dayOfWeek = dayOfWeek;
        this.movie = movie;
        this.customerID = customerID;
    }

    public Transaction(String time, String age, Cinema cinema, String date, Movie movie, String customerID,
            String seatID) {

        this.transactionID = cinema.getCinemaID().substring(0, 3).toUpperCase()
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

        this.time = date + " : " + time;
        this.age = age;
        this.cinemaType = cinema.getCinemaType();

        if (movie.title.substring(0, 2) == "3D")
            this.movieType = "3D";
        else
            this.movieType = "2D";

        Date date2 = new Date(date);
        DateFormat formatter = new SimpleDateFormat("EEEE", Locale.UK);
        this.dayOfWeek = formatter.format(date2);

        this.movie = movie;
        this.customerID = customerID;
        this.seatID = seatID;
        this.bookingTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        for (Showtime show : movie.seats.get(cinema.getCinemaID()).keySet()) {
            if ((show.date.toLowerCase().compareTo(date) == 0) && (show.time.toLowerCase().compareTo(time) == 0)) {
                if (show.day.toLowerCase().compareTo("ph") == 0)
                    isPH = true;
                break;
            }
        }
    }

    /**
     * Prints out the transaction details
     */
    public void printTransactionDetails() {
        System.out.println("Transaction ID: " + transactionID);
        System.out.println("Time: " + time);
        System.out.println("Age: " + age);
        System.out.println("Cinema Type: " + cinemaType);
        System.out.println("Movie Type: " + movieType);
        System.out.println("Day of Week: " + dayOfWeek);
        System.out.println("Movie: " + movie);
        System.out.println("Customer ID: " + customerID);
    }

    /**
     * Gets the ticket price based on the type of the movie tickets
     * 
     * @return A float representing the price of the movie ticket
     */
    public float getTicketPrice() {
        MovieTicket movieTicket = new MovieTicket();

        if (getCinemaType().toLowerCase().compareTo("normal") == 0)
            movieTicket.setCinemaClass(CinemaClass.NORMAL);
        else if (getCinemaType().toLowerCase().compareTo("platinum") == 0)
            movieTicket.setCinemaClass(CinemaClass.PLATINUM);

        if (getMovieType().toLowerCase().compareTo("3d") == 0)
            movieTicket.setMovieType(MovieType.MOVIE3D);
        else if (getMovieType().toLowerCase().compareTo("2d") == 0)
            movieTicket.setMovieType(MovieType.MOVIE2D);

        if (Integer.parseInt(getAge()) <= 12)
            movieTicket.setAge(Age.CHILD);
        else if (Integer.parseInt(getAge()) >= 55)
            movieTicket.setAge(Age.SENIOR_CITIZEN);
        else if (Integer.parseInt(getAge()) > 12 && Integer.parseInt(getAge()) < 55)
            movieTicket.setAge(Age.ADULT);

        if (getDayOfWeek().toLowerCase().compareTo("saturday") == 0
                || getDayOfWeek().toLowerCase().compareTo("sunday") == 0)
            movieTicket.setDayOfWeek(Model.DayOfWeek.WEEKEND);
        else
            movieTicket.setDayOfWeek(Model.DayOfWeek.WEEKDAY);

        if (isPH) {
            movieTicket.setDayOfWeek(Model.DayOfWeek.PUBLIC_HOLIDAY);
        }

        movieTicket.calculateTicketPrice();
        this.ticketPrice = movieTicket.getTicketPrice();

        return ticketPrice;
    }

    /**
     * Gets the transaction ID
     * 
     * @return A String representing the transaction ID
     */
    public String getTransactionId() {
        return this.transactionID;
    }

    /**
     * Gets the time
     * 
     * @return A String representing the time
     */
    public String getTime() {
        return this.time;
    }

    /**
     * Sets the time
     * 
     * @param time A String representing the time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Gets the age
     * 
     * @return A String representing the age
     */
    public String getAge() {
        return this.age;
    }

    /**
     * Sets the age
     * 
     * @param age A String representing the age
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * Gets the cinema type
     * 
     * @return A String representing the cinema type
     */
    public String getCinemaType() {
        return this.cinemaType;
    }

    /**
     * Sets the cinema type
     * 
     * @param cinemaType A String representing the cinema type
     */
    public void setCinemaType(String cinemaType) {
        this.cinemaType = cinemaType;
    }

    /**
     * Gets the movie type
     * 
     * @return A String representing the movie type
     */
    public String getMovieType() {
        return this.movieType;
    }

    /**
     * Sets the movie type
     * 
     * @param movieType A String representing the movie type
     */
    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    /**
     * Gets the day of week
     * 
     * @return A Strign representing the day of week
     */
    public String getDayOfWeek() {
        return this.dayOfWeek;
    }

    /**
     * Sets the day of week
     * 
     * @param day A String representing the day of week
     */
    public void setDayOfWeek(String day) {
        this.dayOfWeek = day;
    }

    /**
     * Gets the movie object
     * 
     * @return A Movie object of the transaction
     */
    public Movie getMovie() {
        return this.movie;
    }

    /**
     * Sets the movie object
     * 
     * @param movie A Movie object for the transaction
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    /**
     * Gets the customer ID
     * 
     * @return A String representing the customer ID
     */
    public String getCustomerID() {
        return this.customerID;
    }

    /**
     * Sets the customer ID
     * 
     * @param custID A String representing the customer ID
     */
    public void setCustomerID(String custID) {
        this.customerID = custID;
    }
}
