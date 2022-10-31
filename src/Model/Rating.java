package Model;

public class Rating {

    // d: followed class diagram

    private String movie;
    private float rating;
    private String review;
    private String ratingID;
    private String username;

    public Rating(String movie, float rating, String review, String ratingID, String username) {
        this.movie = movie;
        this.rating = rating;
        this.review = review;
        this.ratingID = ratingID;
        this.username = username;
    }

    public String getMovie() {
        return this.movie;
    }

    public void setMovie(String movie) {
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

    public void printRatingDetails() {
        System.out.println("Movie: " + this.movie);
        System.out.println("Rating: " + this.rating);
        System.out.println("Review: " + this.review);
        System.out.println("Rating ID: " + this.ratingID);
        System.out.println("Username: " + this.username);
        System.out.println("\n");
    }
}
