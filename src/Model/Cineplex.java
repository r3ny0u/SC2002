package Model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * I thought can use string and Cinema object to get the cinema
 * Like Python dictionar -> "some cinema name", Cinema object
 */
public class Cineplex {
    private String cineplexID;
    private ArrayList<Cinema> cinemas;

    // DO NOT MODIFY THIS CONSTRUCTOR, MAKE ANOTHER IF YOU NEED ANOTHER CONSTRUCTOR
    public Cineplex(String cineplexID, ArrayList<Cinema> cinemas) {
        this.cineplexID = cineplexID;
        this.cinemas = cinemas;
    }

    public String getCineplexID() {
        return this.cineplexID;
    }

    public ArrayList<Cinema> getCinemas() {
        return this.cinemas;
    }
    
    public void printCineplexDetails() {
        System.out.println("Cineplex ID: " + cineplexID);
        for (Cinema cinema : cinemas) {
            System.out.println("  - " + cinema.getCinemaID());
        }
    }
}
