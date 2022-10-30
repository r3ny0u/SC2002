package Model;
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

    public float getTicketPrice() {
        return this.ticketPrice;
    }

    public void setTicketPrice(float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void calculateTicketPrice() {
        float basePrice;

        if (getDayOfWeek() == DayOfWeek.WEEKDAY)
            basePrice = MovieTicketCSVUtil.getCSVData(Constants.MOVIE_TICKET_CSV_FILEPATH, DataType.WEEKDAY_PRICE);
        else if (getDayOfWeek() == DayOfWeek.WEEKEND)
            basePrice = MovieTicketCSVUtil.getCSVData(Constants.MOVIE_TICKET_CSV_FILEPATH, DataType.WEEKEND_PRICE);
        else
            basePrice = MovieTicketCSVUtil.getCSVData(Constants.MOVIE_TICKET_CSV_FILEPATH, DataType.PH_PRICE);

        if (getMovieType() == MovieType.MOVIE3D)
            basePrice *= MovieTicketCSVUtil.getCSVData(Constants.MOVIE_TICKET_CSV_FILEPATH, DataType.MOVIE3D);

        if (getCinemaClass() == CinemaClass.PLATINUM)
            basePrice *= MovieTicketCSVUtil.getCSVData(Constants.MOVIE_TICKET_CSV_FILEPATH, DataType.PLATINUM);

        if (getAge() == Age.SENIOR_CITIZEN)
            basePrice *= MovieTicketCSVUtil.getCSVData(Constants.MOVIE_TICKET_CSV_FILEPATH, DataType.SENIOR_CITIZEN);
        else if (getAge() == Age.CHILD)
            basePrice *= MovieTicketCSVUtil.getCSVData(Constants.MOVIE_TICKET_CSV_FILEPATH, DataType.CHILD);

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