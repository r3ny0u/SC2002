package Model;

import Database.MovieTicketConfig;

/**
 * Class for MovieTicket
 */
public class MovieTicket {
    /** A float representing the ticket price */
    private float ticketPrice;
    /** A MovieType Enum representing the movie type (2D or 3D) */
    private MovieType movieType;
    /** A Cinema Class Enum representing the cinema class (Normal or Platinum) */
    private CinemaClass cinemaClass;
    /** An Age Enum representing the age group (Adult, Senior Citizen or Child) */
    private Age age;
    /**
     * A Day Of Week Enum representing the day of week (Weekday, Weekend or Public
     * Holiday)
     */
    private DayOfWeek dayOfWeek;

    /** Constructor */
    public MovieTicket() {

    };

    /**
     * Gets the ticket pirce
     * 
     * @return A float representing the ticket price
     */
    public float getTicketPrice() {
        return this.ticketPrice;
    }

    /**
     * Sets the ticket price
     * 
     * @param ticketPrice A float representing the ticket price
     */
    public void setTicketPrice(float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    /**
     * Calculate the ticket price
     */
    public void calculateTicketPrice() {
        float basePrice;

        // Get base price based on day of week
        if (getDayOfWeek() == DayOfWeek.WEEKDAY)
            basePrice = MovieTicketConfig.getWeekdayPrice();
        else if (getDayOfWeek() == DayOfWeek.WEEKEND)
            basePrice = MovieTicketConfig.getWeekendPrice();
        else
            basePrice = MovieTicketConfig.getPHPrice();

        // Markup price based on movie type
        if (getMovieType() == MovieType.MOVIE3D)
            basePrice += MovieTicketConfig.get3DMovieMarkup();
        else
            basePrice += MovieTicketConfig.get2DMovieMarkup();

        // Markup price based on cinema type
        if (getCinemaClass() == CinemaClass.PLATINUM)
            basePrice += MovieTicketConfig.getPlatinumCinemaMarkup();
        else
            basePrice += MovieTicketConfig.getNormalCinemaMarkup();

        // Discount based on age
        if (getAge() == Age.SENIOR_CITIZEN)
            basePrice += MovieTicketConfig.getSeniorMarkup();
        else if (getAge() == Age.CHILD)
            basePrice += MovieTicketConfig.getChildMarkup();
        else
            basePrice += MovieTicketConfig.getAdultMarkup();

        setTicketPrice(basePrice);
    }

    /**
     * Get the movie type
     * 
     * @return Movie Type
     */
    public MovieType getMovieType() {
        return this.movieType;
    }

    /**
     * Sets the movie type
     * 
     * @param movieType Movie Type
     */
    public void setMovieType(MovieType movieType) {
        this.movieType = movieType;
    }

    /**
     * Gets the cinema class
     * 
     * @return Cinema class
     */
    public CinemaClass getCinemaClass() {
        return this.cinemaClass;
    }

    /**
     * Sets the cinema class
     * 
     * @param cinemaClass Cinema class
     */
    public void setCinemaClass(CinemaClass cinemaClass) {
        this.cinemaClass = cinemaClass;
    }

    /**
     * Gets the age
     * 
     * @return Age
     */
    public Age getAge() {
        return this.age;
    }

    /**
     * Sets the age
     * 
     * @param age Age Enum 
     */
    public void setAge(Age age) {
        this.age = age;
    }

    /**
     * Gets the day of week
     * 
     * @return Day of week
     */
    public DayOfWeek getDayOfWeek() {
        return this.dayOfWeek;
    }

    /**
     * Sets the day of week
     * 
     * @param dayOfWeek Day of week
     */
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Print the ticket price
     */
    public void printTicketPrice() {
        System.out.printf("Ticket Price is %.2f", getTicketPrice());
    }
}