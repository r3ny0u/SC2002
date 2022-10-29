public class Transaction {

    // d: just following the class diagram, pls edit if necessary ;-;

    private String transactionID;
    private String time; // ??
    private String age;
    private String cinemaType;
    private String movieType;
    private String dayOfWeek;
    private Movie movie;
    private String customerID;

    Transaction() {
    }

    public String getTransactionId() {
        return this.transactionID;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCinemaType() {
        return this.cinemaType;
    }

    public void setCinemaType(String cinemaType) {
        this.cinemaType = cinemaType;
    }

    public String getMovieType() {
        return this.movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public String getDayOfWeek() {
        return this.dayOfWeek;
    }

    public void setDayOfWeek(String day) {
        this.dayOfWeek = day;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getCustomerID() {
        return this.customerID;
    }

    public void setCustomerID(String custID) {
        this.customerID = custID;
    }

}
