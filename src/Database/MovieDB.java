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


    public static void addNewMovie() {
        DatabaseWriter.addNewMovie();
    }

    public static void removeMovie() {
        DatabaseWriter.removeMovie();
    }

    public static void printMovieList(){
        for (Movie movie : new MovieDB().getMovies()) {
            System.out.println(movie.getTitle());  // should just print out the titles instead of details for all movies
            System.out.println("\n");
        }

    }

    public static Movie getMovieFromTitle(String title) {
        for (Movie movie : new MovieDB().getMovies()) {
            if (movie.getTitle().strip().toLowerCase().compareTo(title.strip().toLowerCase()) == 0) {
                return movie;
            }
        }
        return null;
    }

    public static void printMovieDBDetails() {
        for (Movie movie : new MovieDB().getMovies()) {
            movie.printMovieDetails();
            System.out.println("\n");
        }
    }
}
