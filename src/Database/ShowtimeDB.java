package Database;

import java.util.ArrayList;
import java.util.Map;
import Model.Showtime;
import Model.Seat;
import DatabaseBoundary.*;

/**
 * A class for interfacing Showtime database
 */
public class ShowtimeDB {
    /** To store the cineplexes, cinemas, showtimes, movies, and seats */
    private Map<Map<String, ArrayList<String>>, Map<String, Map<Showtime, Seat[]>>> showtimes;

    /** Empty Constructor */
    public ShowtimeDB() {
    }

    /**
     * Gets the showtime hashmap
     * 
     * @return A Hash map representing the cineplexes, cinemas, showtimes, movies,
     *         and seats
     */
    public Map<Map<String, ArrayList<String>>, Map<String, Map<Showtime, Seat[]>>> getShowtimes() {
        return this.showtimes;
    }

    /**
     * Creates a new showtime to the database
     * 
     * @param movieTitle A String representing the movie title
     * @param cineplexID A String representing the cineplex ID
     * @param cinemaID   A String representing the cinema ID
     * @param date       A String representing the date
     * @param day        A String representing the day
     * @param time       A String representing the time
     */
    public static void createShowtimes(String movieTitle, String cineplexID, String cinemaID, String date, String day,
            String time) {
        Seat[] seats = new Seat[100];
        DatabaseWriter.createShowtime(movieTitle, cineplexID, cinemaID, date, day, time, seats);
    }

    /**
     * Updates the showtime of the database
     * 
     * @param movieTitle A String representing the movie title
     * @param cineplexID A String representing the cineplex ID
     * @param cinemaID   A String representing the cinema ID
     * @param date       A String representing the date
     * @param day        A String representing the day
     * @param time       A String representing the time
     * @param seats      An array of Seat objects
     */
    public static void updateShowtimes(String movieTitle, String cineplexID, String cinemaID, String date, String day,
            String time, Seat[] seats) {
        DatabaseWriter.updateShowtimeSeats(movieTitle, cineplexID, cinemaID, date, day, time, seats);
    }

    /**
     * Removes the showtime of the database
     * 
     * @param movieTitle A String representing the movie title
     * @param cineplexID A String representing the cineplex ID
     * @param cinemaID   A String representing the cinema ID
     * @param date       A String representing the date
     * @param day        A String representing the day
     * @param time       A String representing the time
     */
    public static void removeShowtimes(String movieTitle, String cineplexID, String cinemaID, String date, String day,
            String time) {
        DatabaseWriter.removeShowtimes(movieTitle, cineplexID, cinemaID, date, day, time);
    }
}
