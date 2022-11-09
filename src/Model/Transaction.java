package Model;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;

public class Transaction {

    // d: just following the class diagram, pls edit if necessary ;-;

    private String transactionID;
    private String time; // ??
    private String age; // shouldnt age be int?
    private String cinemaType;
    private String movieType;
    private String dayOfWeek;
    private Movie movie;
    private String customerID;
    private String seatID;
    private String bookingTime;
    private float ticketPrice;
    private boolean isPH = false;

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

    public Transaction(String time, String age, Cinema cinema, String date, Movie movie, String customerID,
            String seatID) {
        // Wow nice job b(°-°)d
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
                isPH = true;
                break;
            }
        }
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

    public float getTicketPrice() {
        MovieTicket movieTicket = new MovieTicket();

        if (getCinemaType() == "Normal")
            movieTicket.setCinemaClass(CinemaClass.NORMAL);
        else if (getCinemaType() == "Platinum")
            movieTicket.setCinemaClass(CinemaClass.PLATINUM);

        if (getMovieType() == "3D")
            movieTicket.setMovieType(MovieType.MOVIE3D);
        else if (getMovieType() == "2D")
            movieTicket.setMovieType(MovieType.MOVIE2D);

        if (Integer.parseInt(getAge()) <= 12)
            movieTicket.setAge(Age.CHILD);
        else if (Integer.parseInt(getAge()) >= 55)
            movieTicket.setAge(Age.SENIOR_CITIZEN);
        else if (Integer.parseInt(getAge()) > 12 && Integer.parseInt(getAge()) < 55)
            movieTicket.setAge(Age.ADULT);

        if (getDayOfWeek().toLowerCase().compareTo("saturday") == 0 || getDayOfWeek().toLowerCase().compareTo("sunday") == 0)
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
