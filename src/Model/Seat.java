package Model;

/**
 * A class for seat in cinemas
 */
public class Seat {
    /** A String representing the seat ID */
    public String seatID;
    /** A boolean representing whether the seat has been assigned */
    public boolean assigned;
    /** A String representing the customer ID of the seat */
    public String customerID;

    /**
     * Constructor
     * 
     * @param seatID   A String representing the seat ID
     * @param assigned A boolean representing whether the seat has been assigned
     */
    public Seat(String seatID, boolean assigned) {
        this.seatID = seatID;
        this.assigned = assigned;
    }

    /**
     * Assigns the seat's customer ID
     * 
     * @param customerID A String representing the customer ID
     * @return true if assignment is successful
     */
    public boolean assign(String customerID) {
        if (assigned == true) {
            return false;
        } else {
            this.customerID = customerID;
            this.assigned = true;
            return true;
        }
    }

    /**
     * Check whether seat is available
     * 
     * @return true is seat is available
     */
    public boolean checkAvail() {
        return !assigned;
    }
}
