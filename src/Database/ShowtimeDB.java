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

    public static void updateShowtimes() {

    }

    public static void removeShowtimes() {

    }
}
