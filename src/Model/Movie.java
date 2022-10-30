package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Movie {
    protected String title;
    protected String status;
    protected String synopsis;
    protected String director;
    protected ArrayList<String> casts;
    protected double overallRating;
    protected ArrayList<Rating> reviews;

    public Movie(String title, String status, String synopsis, String director, ArrayList<String> casts) {
        this.title = title;
        this.status = status;
        this.synopsis = synopsis;
        this.director = director;
        this.casts = casts;
        this.overallRating = 5.0;
        this.reviews = new ArrayList<Rating>();
    }

    public void printMovieDetails() {
        System.out.println("Title: " + title);
        System.out.println("Status: " + status);
        System.out.println("Synopsis:");
        System.out.println(synopsis);
        System.out.println("Director: " + director);
        System.out.println("Cast: " + String.join(", ", casts));
        System.out.println("Ratings: " + overallRating + " / 5.0 stars");
        for (Rating rating : reviews) {
            // Haven't made this method thing yet
            // rating.printRating()
        }
    }

    // public void addReviews(String customerID, String review, double rating) {
    // overallRating = ((overallRating * review.length()) + rating) /
    // (review.length() + 1);
    // reviews.put(customerID, review);
    // }
}
