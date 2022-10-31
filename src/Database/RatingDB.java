package Database;

import DatabaseBoundary.*;
import Model.Rating;

public class RatingDB {
    private Rating[] ratings;

    public RatingDB() {
        this.ratings = DatabaseReader.readRatingDatabase();
    }

    public Rating[] getRatings() {
        return this.ratings;
    }

    public static void main(String[] args) {
        RatingDB m = new RatingDB();
        for (Rating rating : m.getRatings()) {
            rating.printRatingDetails();
            System.out.println("\n");
        }
    }
}
