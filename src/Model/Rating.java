package Model;
public class Rating {

    // d: followed class diagram

    private Movie movie;
    private float rating;
    private String review;
    private String ratingID;
    private String customerID;

    public Rating(String customerID, String review, float rating) {
        this.customerID = customerID;
        this.review = review;
        this.rating = rating;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public float getRating() {
        return this.rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReview() {
        return this.review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
    public String getCustomerID(){
        return this.customerID;
    }
}
