package Model;

/**
 * A class for Rating
 */
public class Rating {
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

    private String customerID;

    public Rating(String customerID, String review, float rating) {
        this.customerID = customerID;
        this.review = review;
        this.rating = rating;
    }

    /**
     * Get the movie title
     * @return A String representing the movie title
     */
    public String getMovie() {
        return this.movie;
    }

    /**
     * Sets the movie title
     * @param movie A String representing the movie title
     */
    public void setMovie(String movie) {
        this.movie = movie;
    }

    /**
     * Gets the rating
     * @return Sets the rating
     */
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;

    }
}
