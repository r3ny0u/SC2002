package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cinema {
    private ArrayList<Movie> movies;
    private String cinemaID;
    private String cinemaType;

    public Cinema(String cinemaID, String cinemaType, ArrayList<Movie> movies) {
        this.movies = movies;
        this.cinemaID = cinemaID;
        this.cinemaType = cinemaType;
    }

    public Cinema(String cinemaID, String cinemaType) {
        this.cinemaID = cinemaID;
        this.cinemaType = cinemaType;
    }

    public String getCinemaID() {
        return this.cinemaID;
    }

    public void setCinemaID(String cinemaID) {
        this.cinemaID = cinemaID;
    }

    public String getCinemaType() {
        return this.cinemaType;
    }

    public void printCinemaDetails() {
        System.out.println(cinemaID);
        System.out.println(cinemaType);
        // for (int i = 0; i < movies.size(); i++) {
        //     System.out.println(movies.get(i).getTitle());
        // }
        System.out.print("\n");
    }

    public void addMovieToCinema(Movie movie) {
        movies.add(movie);
    }

    public void removeMovieFromCinema(String movieName) {
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getTitle().toLowerCase().compareTo(movieName.toLowerCase()) == 0) {
                movies.remove(i);
            }
        }
    }

}
