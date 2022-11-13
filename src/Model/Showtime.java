package Model;

/**
 * A Class for showtime
 */
public class Showtime {
    /** A String representing the date */
    protected String date;
    /** A String representing the day */
    protected String day;
    /** A String representing the time */
    protected String time;

    /**
     * Constructor
     * 
     * @param date A String representing the date
     * @param day  A String representing the day
     * @param time A String representing the time
     */
    public Showtime(String date, String day, String time) {
        this.date = date;
        this.day = day;
        this.time = time;
    }
}
