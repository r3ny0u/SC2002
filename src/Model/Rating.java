package Model;

/**
 * A class for Rating
 */
public class Rating {
    /** A String representing the movie title */
    private String movie;
    /** A float representing the rating (out of 5) */
    private float rating;
    /** A String representing the movie review */
    private String review;
    /** A String representing the raing ID */
    private String ratingID;
    /** A String representing the username of the reviewer */
    private String username;

    /**
     * Constructor
     * 
     * @param movie    A String representing the movie title
     * @param rating   A float representing the rating (out of 5)
     * @param review   A String representing the movie review
     * @param ratingID A String representing the raing ID
     * @param username A String representing the username of the reviewer
     */
    public Rating(String movie, float rating, String review, String ratingID, String username) {
        this.movie = movie;
        this.rating = rating;
        this.review = review;
        this.ratingID = ratingID;
        this.username = username;
    }

    /**
     * Get the movie title
     * 
     * @return A String representing the movie title
     */
    public String getMovie() {
        return this.movie;
    }

    /**
     * Sets the movie title
     * 
     * @param movie A String representing the movie title
     */
    public void setMovie(String movie) {
        this.movie = movie;
    }

    /**
     * Gets the rating
     * 
     * @return Sets the rating
     */
    public float getRating() {
        return this.rating;
    }

    /**
     * Sets the rating
     * 
     * @param rating A float representing the rating
     */
    public void setRating(float rating) {
        this.rating = rating;
    }

    /**
     * Gets the review
     * 
     * @return A String representing the review
     */
    public String getReview() {
        return this.review;
    }

    /**
     * Sets the review
     * 
     * @param review A String representing the review
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * Prints our the rating details
     */
    public void printRatingDetails() {
        System.out.println("Movie: " + this.movie);
        System.out.println("Rating: " + this.rating);
        System.out.println("Review: " + this.review);
        System.out.println("Rating ID: " + this.ratingID);
        System.out.println("Username: " + this.username);
        System.out.println("\n");
    }

    /**
     * Sets the username
     * 
     * @param username A String representing the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the username
     * 
     * @return A String representing the username
     */
    public String getUsername() {
        return this.username;

    }
}
