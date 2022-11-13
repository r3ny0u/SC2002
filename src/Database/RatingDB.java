package Database;

import DatabaseBoundary.*;
import Model.Rating;

/**
 * A class for interfacing Rating database
 */
public class RatingDB {
    /** An array of Rating objects */
    private Rating[] ratings;

    /** Constructor */
    public RatingDB() {
        this.ratings = DatabaseReader.readRatingDatabase();
    }

    /**
     * Adds a new rating
     * 
     * @param movie    A String representing the name of the movie
     * @param username A String representing the username of the customer adding the
     *                 ratings
     */
    public static void addNewRating(String movie, String username) {
        DatabaseWriter.addNewRating(movie, username);
    }

    /**
     * Gets the rating array
     * 
     * @return An array of Ratings
     */
    public Rating[] getRatings() {
        return this.ratings;
    }

    /**
     * Prints the details of each rating
     */
    public static void printRatingDBDetails() {
        for (Rating rating : new RatingDB().getRatings()) {
            rating.printRatingDetails();
            System.out.println("\n");
        }
    }
}
