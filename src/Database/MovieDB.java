package Database;

import Model.Movie;

import java.util.Arrays;
import java.util.Comparator;

import DatabaseBoundary.*;

/**
 * A class for interfacing the Movie database
 */
public class MovieDB {
    private Movie[] movies;

    public MovieDB() {
        this.movies = DatabaseReader.readMovieDatabase();
    }

    /**
     * Gets the movie array
     * 
     * @return
     */
    public Movie[] getMovies() {
        return this.movies;
    }

    /**
     * Adds a new movie
     */
    public static void addNewMovie() {
        DatabaseWriter.addNewMovie();
    }

    /**
     * Removes a movie
     */
    public static void removeMovie() {
        DatabaseWriter.removeMovie();
    }

    /**
     * Prints out titles of each movie
     */
    public static void printMovieList() {
        MovieDB movieDB = new MovieDB();
        movieDB.sortByAlphabet();
        Movie[] movies = movieDB.getMovies();
        int j = 1;
        for (int i = 0; i < movies.length; i++) {
            if (movies[i].getStatus().toLowerCase().compareTo("end of showing") == 0) {
                j--;
                continue;
            }
            System.out.printf("%2d. %-30s: %s\n", i + j, movies[i].getTitle(), movies[i].getStatus().toUpperCase());
        }
    }

    public static void printAllMovieList() {
        // Sorry dayna got conflict >_<
        MovieDB movieDB = new MovieDB();
        movieDB.sortByAlphabet();
        Movie[] movies = movieDB.getMovies();
        for (int i = 0; i < movies.length; i++) {
            System.out.printf("%2d. %-30s: %s\n", i + 1, movies[i].getTitle(), movies[i].getStatus().toUpperCase());
        }
    }

    /**
     * Gets the movie by its title
     * 
     * @param title A String representing the title of the movie
     * @return A Movie object with the title
     */

    public static Movie getMovieFromTitle(String title) {
        for (Movie movie : new MovieDB().getMovies()) {
            if (movie.getTitle().strip().toLowerCase().compareTo(title.strip().toLowerCase()) == 0) {
                return movie;
            }
        }
        return null;
    }

    /**
     * Prints the movie details of each movie
     */
    public static void printMovieDBDetails() {
        System.out.println("================================================");
        for (Movie movie : new MovieDB().getMovies()) {
            movie.printMovieDetails();
            System.out.println("================================================");
        }
    }

    /**
     * Sorts this.movies in descending order by the movies' ratings
     */
    public void sortByRating() {
        Arrays.sort(this.movies, new Comparator<Movie>() {

            @Override
            public int compare(Movie o1, Movie o2) {
                return (int) (o2.getRating() - o1.getRating());
            }

        });
    }

    /**
     * Sorts this.movies in alphabetical order
     */
    public void sortByAlphabet() {
        Arrays.sort(this.movies, new Comparator<Movie>() {

            @Override
            public int compare(Movie o1, Movie o2) {
                return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase());
            }

        });
    }

    /**
     * Sorts this.movies in descending order by the movies' sales
     */
    public void sortBySales() {
        Arrays.sort(movies, new Comparator<Movie>() {

            @Override
            public int compare(Movie o1, Movie o2) {
                return o1.getSalesCount() - o2.getSalesCount();
            }

        });
    }
}