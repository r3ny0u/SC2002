package Model;

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
    

    public boolean assign(String customerID) {
        if (assigned == true) {
            return false;
        } else {
            this.customerID = customerID;
            this.assigned = true;
            return true;
        }
    }

    public boolean checkAvail() {
        if (assigned)
            return false;
        else
            return true;
    }
}
