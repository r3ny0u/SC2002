package Database;

import java.util.ArrayList;
import java.util.Map;
import Model.Showtime;
import Model.Seat;
import DatabaseBoundary.*;

public class ShowtimeDB {

    private Map<Map<String, ArrayList<String>>, Map<String, Map<Showtime, Seat[]>>> showtimes;

    public ShowtimeDB() {

    }

    public Map<Map<String, ArrayList<String>>, Map<String, Map<Showtime, Seat[]>>> getShowtimes() {
        return this.showtimes;
    }

    /**
     * Create a new showtime by prompting users
     */
    public static void createShowtimes(String movieTitle, String cineplexID, String cinemaID, String date, String day,
    String time) {
        Seat[] seats = new Seat[100];
        DatabaseWriter.createShowtimes(movieTitle, cineplexID, cinemaID, date, day, time, seats);
    }

    /**
     * Edit current showtimes
     */
    public static void editShowtimes(String movieTitle, String cineplexID, String cinemaID, String date, String day,
    String time, Seat[] seats) {
        DatabaseWriter.updateShowtimes(movieTitle, cineplexID, cinemaID, date, day, time, seats);
    }

    public static void updateShowtimes(String movieTitle, String cineplexID, String cinemaID, String date, String day,
            String time, Seat[] seats) {
        DatabaseWriter.updateShowtimes(movieTitle, cineplexID, cinemaID, date, day, time, seats);
    }

    /**
     * Remove showtimes
     */
    public static void removeShowtimes(String movieTitle, String cineplexID, String cinemaID, String date, String day,
    String time) {
        DatabaseWriter.removeShowtimes(movieTitle, cineplexID, cinemaID, date, day, time);
    }
}
