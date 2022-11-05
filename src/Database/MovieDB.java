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

    public static void printMovieList() {
        int count = 1;
        for (Movie movie : new MovieDB().getMovies()) {
            System.out.printf("(" + count + ") " + movie.getTitle()); // should just print out the titles instead of
                                                                      // details for all movies
            count++;
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
        System.out.println("================================================");
        for (Movie movie : new MovieDB().getMovies()) {
            movie.printMovieDetails();
            System.out.println("================================================");
        }
    }

    public void sortByRating() {
        Arrays.sort(movies, new Comparator<Movie>() {

            @Override
            public int compare(Movie o1, Movie o2) {
                if (o1.getRating() - o2.getRating() > 0)
                    return 1;
                else
                    return 0;
            }

        });
    }

    public void sortByAlphabet() {
        Arrays.sort(movies, new Comparator<Movie>() {

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