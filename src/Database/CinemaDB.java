package Database;

import Model.Cinema;
import DatabaseBoundary.*;

public class CinemaDB {
    private Cinema[] cinemas;

    public CinemaDB() {
        this.cinemas = DatabaseReader.readCinemaDatabase();
    }

    public Cinema[] getCinemas() {
        return this.cinemas;
    }

    public static Cinema getCinemaFromID(String cinemaID) {
        for (Cinema cinema : new CinemaDB().getCinemas()) {
            if (cinema.getCinemaID().compareTo(cinemaID) == 0) {
                return cinema;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        CinemaDB a = new CinemaDB();
        for (Cinema Cinema : a.getCinemas()) {
            Cinema.printCinemaDetails();
            System.out.println("\n");
        }
    }
}
