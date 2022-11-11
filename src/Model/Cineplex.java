package Model;

import java.util.ArrayList;

/**
 * A class for Cineplex
 */
public class Cineplex {
    private String cineplexID;
    private ArrayList<Cinema> cinemas;

    public Cineplex(String cineplexID, ArrayList<Cinema> cinemas) {
        this.cineplexID = cineplexID;
        this.cinemas = cinemas;
    }

    /**
     * Gets the cineplex ID
     * 
     * @return A String representing the cineplex ID
     */
    public String getCineplexID() {
        return this.cineplexID;
    }

    /**
     * Gets the cinemas associated witht the cineplex
     * 
     * @return An ArrayList of Cinemas object
     */
    public ArrayList<Cinema> getCinemas() {
        return this.cinemas;
    }

    /**
     * Prints the cineplex details
     */
    public void printCineplexDetails() {
        System.out.println("Cineplex ID: " + cineplexID);
        for (Cinema cinema : cinemas) {
            System.out.printf("  - Cinema ID: %s (%s)\n", cinema.getCinemaID(), cinema.getCinemaType());
        }
    }

    /**
     * Finds the cinema using the ID of the cinema
     * 
     * @param cinemaID A String representing the cinema ID
     * @return A Cinema object associated with the cinema ID
     */
    public Cinema findCinema(String cinemaID) {
        for (Cinema cinema : cinemas) {
            if (cinema.getCinemaID().toLowerCase().compareTo(cinemaID.toLowerCase()) == 0)
                return cinema;
        }
        return null;
    }
}
