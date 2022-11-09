package Database;

import DatabaseBoundary.DatabaseWriter;
import Model.Seat;

public class ShowtimeDB {
    public static void updateShowtimes(String movieTitle, String cineplexID, String cinemaID, String date, String day,
    String time, Seat[] seats) {
        DatabaseWriter.updateShowtimes(movieTitle, cineplexID, cinemaID, date, day, time, seats);
    }
}
