package Database;

import Model.Cineplex;
import DatabaseBoundary.*;

/**
 * A class for interfacing Cineplex database
 */
public class CineplexDB {
    /** An array of Cineplex objects */
    private Cineplex[] cineplexes;

    /** Constructor */
    public CineplexDB() {
        this.cineplexes = DatabaseReader.readCineplexDatabase2();
    }

    /**
     * Gets the Cineplex array
     * 
     * @return An array of cineplexes
     */
    public Cineplex[] getCineplexes() {
        return this.cineplexes;
    }

    /**
     * Adds a new cineplex
     */
    public static void addNewCineplex() {
        DatabaseWriter.addNewCineplex();
    }

    /**
     * Removes a cineplex
     */
    public static void removeCineplex() {
        DatabaseWriter.removeCineplex();
    }

    /**
     * Gets a cineplex from its ID
     * 
     * @param cineplexID A String representing the cineplex ID
     * @return A Cineplex object
     */
    public static Cineplex getCineplexFromID(String cineplexID) {
        for (Cineplex cineplex : new CineplexDB().getCineplexes()) {
            if (cineplex.getCineplexID().compareTo(cineplexID) == 0) {
                return cineplex;
            }
        }
        return null;
    }

    /**
     * Prints the details of each cineplex
     */
    public static void printCineplexDBDetails() {
        for (Cineplex cineplex : new CineplexDB().getCineplexes()) {
            cineplex.printCineplexDetails();
            System.out.print("\n");
        }
    }
}
