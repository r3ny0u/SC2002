package Model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cinema {
    private String movie;
    private String cinemaID;
    private String cinemaType;
    private ArrayList<String> showtimes;
    private Seat[] seats;

    // DO NOT MODIFY THIS CONSTRUCTOR, MAKE ANOTHER IF YOU NEED ANOTHER CONSTRUCTOR
    public Cinema(String cinemaID, String cinemaType, String movie, ArrayList<String> showtimes) {
        this.movie = movie;
        this.cinemaID = cinemaID;
        this.cinemaType = cinemaType;
        this.showtimes = showtimes;
    }

    public String getCinemaID() {
        return this.cinemaID;
    }

    public void setCinemaID(String cinemaID) {
        this.cinemaID = cinemaID;
    }

    public Seat[] getSeats() {
        return this.seats;
    }

    public void setSeats(Seat[] seats) {
        this.seats = seats;
    }

    public void printCinemaDetails() {
        System.out.println(cinemaID);
        System.out.println(cinemaType);
        System.out.println(movie);
        for (String showtime : showtimes) {
            System.out.print(showtime + ", ");
        }
        System.out.print("\n");
    }
}
