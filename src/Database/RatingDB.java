package Database;

import DatabaseBoundary.*;
import Model.Rating;

public class RatingDB {
    private Rating[] ratings;

    public RatingDB() {
        this.ratings = DatabaseReader.readRatingDatabase();
    }

    public static void addNewRating(String movie, String username) {
        DatabaseWriter.addNewRating(movie, username);
    }

    @Deprecated
    /**
     * I don't know whether we want to like remove the rating is that necessary idk
     */
    public static void removeRating() {
        DatabaseWriter.removeRating();
    }

    public Rating[] getRatings() {
        return this.ratings;
    }

    public static void printRatingDBDetails() {
        for (Rating rating : new RatingDB().getRatings()) {
            rating.printRatingDetails();
            System.out.println("\n");
        }
    }
}
