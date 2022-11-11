package Database;

import Model.Movie;

import java.util.Arrays;
import java.util.Comparator;

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

    /**
     * Prints out movie titles
     */
    public static void printMovieList() {
        // Sorry dayna got conflict >_<
        MovieDB movieDB = new MovieDB();
        movieDB.sortByAlphabet();
        Movie[] movies = movieDB.getMovies();
        int j = 1;
        for (int i = 0; i < movies.length; i++) {
            // if (movies[i].getStatus().toLowerCase().compareTo("end of showing") == 0) {
            // j--;
            // continue;
            // }
            System.out.printf("%2d. %-30s: %s\n", i + j, movies[i].getTitle(), movies[i].getStatus().toUpperCase());
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
        System.out.println("================================================");
        for (Movie movie : new MovieDB().getMovies()) {
            movie.printMovieDetails();
            System.out.println("================================================");
        }
    }

    public void sortByRating() {
        Arrays.sort(this.movies, new Comparator<Movie>() {

            @Override
            public int compare(Movie o1, Movie o2) {
                return (int) (o2.getRating() - o1.getRating());
            }

        });
    }

    public void sortByAlphabet() {
        Arrays.sort(this.movies, new Comparator<Movie>() {

            @Override
            public int compare(Movie o1, Movie o2) {
                return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase());
            }

        });
    }

    public void sortBySales() {
        Arrays.sort(movies, new Comparator<Movie>() {

            @Override
            public int compare(Movie o1, Movie o2) {
                return o1.getSalesCount() - o2.getSalesCount();
            }

        });
    }
}