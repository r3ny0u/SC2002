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

    // ISTG STOP MODIFYING THE CONSTRUCTOR, JUST ADD ANOTHER IF NEED!! >:(
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
        // for (String movie : movie) {
        //     System.out.println(movie);
        //     for (String showtime : showtimes.get(movie.indexOf(movie))) {
        //         System.out.print(showtime + ", ");
        //     }
        //     System.out.println();
        // }
        System.out.print("Showtime: ");
        for (String showtime : showtimes) {
            System.out.print(showtime + " ");
        }
        System.out.print("\n");
    }

    public void addMovies(String movieName) {
        
    }

}
