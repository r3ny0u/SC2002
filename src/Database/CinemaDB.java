package Database;

import Model.Cinema;
import DatabaseBoundary.*;

public class CinemaDB {
    private Cinema[] cinemas;

    public CinemaDB() {
        // readCinemaDatabase has not been implemented
        this.cinemas = DatabaseReader.readCinemaDatabase2();
    }

    public Cinema[] getCinemas() {
        return this.cinemas;
    }

    public static void addNewCinema() {
        DatabaseWriter.addNewCinema();
    }

    public static void removeCinema() {
        DatabaseWriter.removeCinema();
    }

    public static Cinema getCinemaFromID(String cinemaID) {
        for (Cinema cinema : new CinemaDB().getCinemas()) {
            if (cinema.getCinemaID().toLowerCase().compareTo(cinemaID.toLowerCase()) == 0) {
                return cinema;
            }
        }
        return null;
    }

    public static void printCinemaDBDetails() {
        for (Cinema cinema : new CinemaDB().getCinemas()) {
            cinema.printCinemaDetails();
            System.out.println("\n");
        }
    }

    public static void printCinemaDBID() {
        for (Cinema cinema : new CinemaDB().getCinemas()) {
            System.out.println(cinema.getCinemaID());
        }
    }
}
