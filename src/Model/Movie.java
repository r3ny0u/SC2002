package Model;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Movie {
    protected String title;
    protected String status;
    protected String synopsis;
    protected String director;
    protected String[] cast;
    protected double overallRating;
    protected Map<String, String> reviews;

    protected Movie(String title, String status, String synopsis, String director, String[] cast,
            double overallRating) {
        this.title = title;
        this.status = status;
        this.synopsis = synopsis;
        this.director = director;
        this.cast = cast;
        this.overallRating = overallRating;
        this.reviews = new HashMap<String, String>();
    }

    public void printMovieDetails() {
        System.out.println("Title: " + title);
        System.out.println("Status: " + status);
        System.out.println("Synopsis");
        System.out.println(synopsis);
        System.out.println("\n Cast: " + Arrays.toString(cast));
        System.out.println("Ratings: " + overallRating + "/5 stars");
        for (String key : reviews.keySet())
            System.out.println(key + ": " + reviews.get(key));
    }

    public void addReviews(String customerID, String review, double rating) {
        overallRating = ((overallRating * review.length()) + rating) / (review.length() + 1);
        reviews.put(customerID, review);
    }
}
