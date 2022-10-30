package Database;

import Model.Movie;
import DatabaseBoundary.*;

public class MovieDB {
    private Movie[] movies;

    public MovieDB() {
        this.movies = DatabaseReader.readMovieDatabase();
    }

    public Movie[] getMovies() {
        return this.movies;
    }

    public static Movie getMovieFromTitle(String title) {
        for (Movie movie : new MovieDB().getMovies()) {
            if (movie.getTitle().toLowerCase().compareTo(title.toLowerCase()) == 0) {
                return movie;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        MovieDB m = new MovieDB();
        for (Movie movie : m.getMovies()) {
            movie.printMovieDetails();
            System.out.println("\n");
        }
    }
}
