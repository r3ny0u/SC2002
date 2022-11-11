package Database;

import Model.Cinema;
import DatabaseBoundary.*;

/**
 * A class for interfacing Cinema database
 */
public class CinemaDB {
    private Cinema[] cinemas;

    public CinemaDB() {
        this.cinemas = DatabaseReader.readCinemaDatabase();
    }

    /**
     * Gets the Cinema array
     */
    public Cinema[] getCinemas() {
        return this.cinemas;
    }

    /**
     * Adds a new cinema
     */
    public static void addNewCinema() {
        DatabaseWriter.addNewCinema();
    }

    /**
     * Removes a cinema
     */
    public static void removeCinema() {
        DatabaseWriter.removeCinema();
    }

    /**
     * Gets the Cinema Object
     * 
     * @param cinemaID
     * @return
     */
    public static Cinema getCinemaFromID(String cinemaID) {
        for (Cinema cinema : new CinemaDB().getCinemas()) {
            if (cinema.getCinemaID().toLowerCase().compareTo(cinemaID.toLowerCase()) == 0) {
                return cinema;
            }
        }
        return null;
    }

    /**
     * Prints the details of each cinema
     */
    public static void printCinemaDBDetails() {
        for (Cinema cinema : new CinemaDB().getCinemas()) {
            cinema.printCinemaDetails();
            System.out.println("\n");
        }
    }

    /**
     * Prints the ID of each cinema
     */
    public static void printCinemaDBID() {
        for (Cinema cinema : new CinemaDB().getCinemas()) {
            System.out.println(cinema.getCinemaID());
        }
    }
}
