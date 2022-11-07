package Database;

import Model.Cineplex;
import DatabaseBoundary.*;

public class CineplexDB {
    private Cineplex[] cineplexes;

    public CineplexDB() {
        this.cineplexes = DatabaseReader.readCineplexDatabase2();
    }

    public Cineplex[] getCineplexes() {
        return this.cineplexes;
    }

    public static void addNewCineplex() {
        DatabaseWriter.addNewCineplex();
    }

    public static void removeCineplex() {
        DatabaseWriter.removeCineplex();
    }

    public static void addNewCinemaToCineplex() {
        System.out.println("Cinemas available:");
        CinemaDB.printCinemaDBID();
        DatabaseWriter.addNewCinemaToCineplex();
    }

    public static void removeCinemaFromCineplex() {
        System.out.println("Cinemas available:");
        CinemaDB.printCinemaDBID();
        DatabaseWriter.removeCinemaFromCineplex();
    }

    public static Cineplex getCineplexFromID(String cineplexID) {
        for (Cineplex cineplex : new CineplexDB().getCineplexes()) {
            if (cineplex.getCineplexID().compareTo(cineplexID) == 0) {
                return cineplex;
            }
        }
        return null;
    }

    public static void printCineplexDBDetails() {
        for (Cineplex cineplex : new CineplexDB().getCineplexes()) {
            cineplex.printCineplexDetails();
            System.out.print("\n");
        }
    }
}
