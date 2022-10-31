package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    // d: just following the class diagram, pls edit if necessary ;-;

    private String transactionID;
    private String time; // ??
    private String age;
    private String cinemaType;
    private String movieType;
    private String dayOfWeek;
    private Movie movie;
    private String customerID;
    private String seatID;
    private String bookingTime;

    Transaction() {
    }

    // DO NOT MODIFY THIS CONSTRUCTOR, MAKE ANOTHER IF YOU NEED ANOTHER CONSTRUCTOR
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
        this.seatID = seatID;
    }

    public Transaction(String transactionID, String time, String age, String cinemaType, String movieType,
            String dayOfWeek, Movie movie, String customerID, String seatID) {

        this.transactionID = transactionID;
        this.time = time;
        this.age = age;
        this.cinemaType = cinemaType;
        this.movieType = movieType;
        this.dayOfWeek = dayOfWeek;
        this.movie = movie;
        this.customerID = customerID;
        this.seatID = seatID;
        this.bookingTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-mm-yyyy HH:mm:ss"));
    }

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

    public String getTransactionId() {
        return this.transactionID;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCinemaType() {
        return this.cinemaType;
    }

    public void setCinemaType(String cinemaType) {
        this.cinemaType = cinemaType;
    }

    public String getMovieType() {
        return this.movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public String getDayOfWeek() {
        return this.dayOfWeek;
    }

    public void setDayOfWeek(String day) {
        this.dayOfWeek = day;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getCustomerID() {
        return this.customerID;
    }

    public void setCustomerID(String custID) {
        this.customerID = custID;
    }
}
