package Database;

import Model.Cineplex;
import DatabaseBoundary.*;

public class CineplexDB {
    private Cineplex[] cineplexes;

    public CineplexDB() {
        this.cineplexes = DatabaseReader.readCineplexDatabase();
    }

    public Cineplex[] getCineplexes() {
        return this.cineplexes;
    }

    public static Cineplex getCineplexFromID(String cineplexID) {
        for (Cineplex cineplex : new CineplexDB().getCineplexes()) {
            if (cineplex.getCineplexID().compareTo(cineplexID) == 0) {
                return cineplex;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        CineplexDB a = new CineplexDB();
        for (Cineplex cineplex : a.getCineplexes()) {
            cineplex.printCineplexDetails();
            System.out.println("\n");
        }
    }
}
