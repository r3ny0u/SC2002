package Model;

/**
 * A class for seat in cinemas
 */
public class Seat {
    public String seatID;
    public boolean assigned;
    public String customerID;

    public Seat(String seatID) {
        this.seatID = seatID;
    }

    public Seat(String seatID, boolean assigned) {
        this.seatID = seatID;
        this.assigned = assigned;
    }

    /**
     * Assigns the seat's customer ID
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
     * @return
     */
    public boolean checkAvail() {
        return !assigned;
    }
}
