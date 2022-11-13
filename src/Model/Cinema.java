package Model;

/**
 * A class for Cinema
 */
public class Cinema {
    /** A String representing the Cinema ID */
    private String cinemaID;
    /** A String representing the Cinema Type */
    private String cinemaType;

    /**
     * Constructor
     * 
     * @param cinemaID   A String representing the Cinema ID
     * @param cinemaType A String representing the Cinema Type
     */
    public Cinema(String cinemaID, String cinemaType) {
        this.cinemaID = cinemaID;
        this.cinemaType = cinemaType;
    }

    /**
     * Gets the cinema ID
     * 
     * @return A String representing the cinema ID
     */
    public String getCinemaID() {
        return this.cinemaID;
    }

    /**
     * Sets the cinema ID
     * 
     * @param cinemaID A String representing the cinema ID
     */
    public void setCinemaID(String cinemaID) {
        this.cinemaID = cinemaID;
    }

    /**
     * Gets the cinema type
     * 
     * @return A String representing the cinema type
     */
    public String getCinemaType() {
        return this.cinemaType;
    }

    /**
     * Prints the cinema details
     */
    public void printCinemaDetails() {
        System.out.println(cinemaID);
        System.out.println(cinemaType);
        System.out.print("\n");
    }
}
