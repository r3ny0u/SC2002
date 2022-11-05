package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cinema {
    private ArrayList<Movie> movies;
    private String cinemaID;
    private String cinemaType;

    // ISTG STOP MODIFYING THE CONSTRUCTOR, JUST ADD ANOTHER IF NEED!! >:(
    public Cinema(String cinemaID, String cinemaType, ArrayList<Movie> movies) {
        this.movies = movies;
        this.cinemaID = cinemaID;
        this.cinemaType = cinemaType;
    }

    public String getCinemaID() {
        return this.cinemaID;
    }

    public void setCinemaID(String cinemaID) {
        this.cinemaID = cinemaID;
    }

    public void printCinemaDetails() {
        System.out.println(cinemaID);
        System.out.println(cinemaType);
        for (int i = 0; i < movies.size(); i++) {
            System.out.println(movies.get(i).getTitle());
        }
        // for (String movie : movie) {
        // System.out.println(movie);
        // for (String showtime : showtimes.get(movie.indexOf(movie))) {
        // System.out.print(showtime + ", ");
        // }
        // System.out.println();
        // }
        // System.out.print("Showtime: ");
        // for (String showtime : showtimes) {
        // System.out.print(showtime + " ");
        // }
        System.out.print("\n");
    }

    public void addMovieToCinema(Movie movieName) {
        movies.add(movieName);
    }

    public void removeMovieFromCinema(String movieName) {
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getTitle().toLowerCase().compareTo(movieName.toLowerCase()) == 0) {
                movies.remove(i);
            }
        }
    }

}
