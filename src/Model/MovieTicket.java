package Model;

import Database.MovieTicketConfig;

/**
 * Class for MovieTicket
 * <p>
 * I think we also need to add some other attributes to it too
 * <p>
 * o(*￣▽￣*)o
 * <p>
 * When life gives you lemon, throw it back because lemons aren't
 * natural, it's a cross breed made by humans
 * 
 * @apiNote fuck me
 */

public class MovieTicket {
    private float ticketPrice;
    private MovieType movieType;
    private CinemaClass cinemaClass;
    private Age age;
    private DayOfWeek dayOfWeek;

    /**
     * Kinda self-explanatory innit bruv?
     * 
     * @param movieType
     * @param cinemaClass
     * @param age
     * @param dayOfWeek
     */
    public MovieTicket(MovieType movieType, CinemaClass cinemaClass, Age age, DayOfWeek dayOfWeek) {
        this.movieType = movieType;
        this.cinemaClass = cinemaClass;
        this.age = age;
        this.dayOfWeek = dayOfWeek;
        calculateTicketPrice();
    }

    public MovieTicket() {
    };

    public float getTicketPrice() {
        return this.ticketPrice;
    }

    public void setTicketPrice(float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

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

    public MovieType getMovieType() {
        return this.movieType;
    }

    public void setMovieType(MovieType movieType) {
        this.movieType = movieType;
    }

    public CinemaClass getCinemaClass() {
        return this.cinemaClass;
    }

    public void setCinemaClass(CinemaClass cinemaClass) {
        this.cinemaClass = cinemaClass;
    }

    public Age getAge() {
        return this.age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public DayOfWeek getDayOfWeek() {
        return this.dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void printTicketPrice() {
        System.out.printf("Ticket Price is %.2f", getTicketPrice());
    }

    /*
     * Testing movie ticket check if work
     */
    public static void main(String[] args) {
        MovieTicket mt = new MovieTicket(MovieType.MOVIE2D, CinemaClass.PLATINUM, Age.SENIOR_CITIZEN,
                DayOfWeek.WEEKEND);
        mt.printTicketPrice(); // Should be 30 * 1.05 * 0.8 = 25.2
    }
}