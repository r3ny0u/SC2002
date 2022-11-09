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

    public static void createShowtimes() {

    }
    
    public static void updateShowtimes(String movieTitle, String cineplexID, String cinemaID, String date, String day,
    String time, Seat[] seats) {
        DatabaseWriter.updateShowtimes(movieTitle, cineplexID, cinemaID, date, day, time, seats);
    }

    public static void removeShowtimes() {

    }
}
