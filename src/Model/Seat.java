package Model;
public class Seat {
    public String seatID;
    public boolean assigned;
    public String customerID;

    public Seat(String seatID)
    {
        this.seatID = seatID;
    }

    public void assign(String customerID)
    {
        if (assigned == true)
        {
            System.out.println("Seat is already taken!");
            return;
        }
        else
        {
            this.customerID = customerID;
            this.assigned = true;
        }
    }
    
}
