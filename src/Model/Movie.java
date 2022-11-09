package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Database.RatingDB;
import DatabaseBoundary.DatabaseReader;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Movie {
    protected String title;
    protected String status;
    protected String synopsis;
    protected String director;
    protected ArrayList<String> casts;
    protected double overallRating;
    protected int ratingCount;
    protected ArrayList<Rating> reviews = new ArrayList<Rating>();
    protected int salesCount = 0;
    protected Map<String, ArrayList<String>> showingPlaces = new HashMap<String, ArrayList<String>>(); // Cineplex ->
                                                                                                       // Cinema
    protected Map<String, Map<Showtime, Seat[]>> seats; // cinemaID->showtimes->seats

    // DO NOT MODIFY THIS CONSTRUCTOR, MAKE ANOTHER IF YOU NEED ANOTHER CONSTRUCTOR
    public Movie(String title, String status, String synopsis, String director, ArrayList<String> casts) {
        this.title = title;
        this.status = status;
        this.synopsis = synopsis;
        this.director = director;
        this.casts = casts;
        this.loadRatingsAndReviews();
        this.loadShowtimes();
    }

    public Movie(String title, String status, String synopsis, String director, ArrayList<String> casts,
            ArrayList<String> cinemaNames) {
        this.title = title;
        this.status = status;
        this.synopsis = synopsis;
        this.director = director;
        this.casts = casts;
        this.overallRating = 0.0;
        this.reviews = new ArrayList<Rating>();
        this.ratingCount = 0;
        this.salesCount = 0;
        this.loadRatingsAndReviews();
        this.loadShowtimes();
    }

    public Seat[] getSeats(String cinemaID, String date, String day, String time) {
        Showtime showtime = null;
        for (Showtime show : seats.get(cinemaID).keySet()) {
            if ((show.date.toLowerCase().compareTo(date) == 0) && (show.day.toLowerCase().compareTo(day) == 0)
                    && (show.time.toLowerCase().compareTo(time) == 0)) {
                showtime = show;
                break;
            }
        }
        return seats.get(cinemaID).get(showtime);
    }

    public String getStatus() {
        return this.status;
    }

    public String getTitle() {
        return this.title;
    }

    public int getSalesCount() {
        return this.salesCount;
    }

    public void printMovieDetails() {
        this.loadRatingsAndReviews();
        System.out.println("\n================== Movie Details =======================");
        System.out.println("Title     : " + title);
        System.out.println("Status    : " + status);
        System.out.println("Synopsis  : " + synopsis);
        System.out.println("Director  : " + director);
        System.out.println("Cast      : " + String.join(", ", casts));
        System.out.printf("Ratings   : %.2f / 5.0 STARS\n", this.overallRating);

        if (this.reviews.size() != 0) {
            System.out.println("\nReviews   : ");
            for (int i = 0; i < this.reviews.size(); i++) {
                Rating rating = this.reviews.get(i);
                System.out.printf("%2d. %-10s: %.2f\n", i + 1, rating.getUsername(), rating.getRating());
                System.out.println("       " + rating.getReview());
            }
        }

        System.out.println("========================================================\n");
    }

    public void addShowtimes(String cinemaID, String date, ArrayList<String> showtimes) {
        int i = 0, j = 0, n = 0;
        Seat[] s = new Seat[100];
        for (j = 1; j <= 100; j++) {
            if (j % 10 == 0)
                i++;
            String row = "" + (char) (65 + i);
            s[j - 1] = new Seat(row + (j - 1));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate d = LocalDate.parse(date, formatter);
        String day = DayOfWeek.from(d).name();

        for (String st : showtimes) {
            Showtime showtime = new Showtime(date, day, st);
            Map<Showtime, Seat[]> temp = new HashMap<>();
            temp.put(showtime, s);
            if (seats == null) {
                seats = new HashMap<>();
                seats.put(cinemaID, temp);
            }

            else if (seats.containsKey(cinemaID)) {
                seats.computeIfAbsent(cinemaID, k -> new HashMap()).put(showtime, s);
            }

            else {
                seats.put(cinemaID, temp);
            }

        }
    }

    public void printAllShowtimes() {
        String oldDate = "";
        for (String cineplexID : showingPlaces.keySet()) {
            System.out.println(
                    "========================================================\n\nCineplex   : " + cineplexID);

            for (String cinemaID : showingPlaces.get(cineplexID)) {
                System.out.println("Cinema     : " + cinemaID);

                for (Showtime st : seats.get(cinemaID).keySet()) {
                    if (oldDate != st.date) {
                        oldDate = st.date;
                        System.out.print(oldDate + " : " + st.time + " ");
                    } else {
                        System.out.print(st.time + " ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println("========================================================");
    }

    public void printSeats(String cinemaID, String date, String showtime) {
        int i, j;
        int[][] seatMatrix = new int[10][10];
        i = j = 0;

        for (Showtime st : seats.get(cinemaID).keySet()) {
            if (st.date != date && st.time != showtime)
                continue;

            for (Seat s : seats.get(cinemaID).get(st)) {
                if (s.assigned)
                    seatMatrix[i][j] = 1;
                else
                    seatMatrix[i][i] = 0;
                j++;

                if (j == 10) {
                    j = 0;
                    i++;
                }
            }
        }

        System.out.println("================ Screen ================\n");

        System.out.println("   1   2   3   4   5   6   7   8   9   10");
        for (i = 0; i < 10; i++) {
            System.out.print((char) (65 + i) + " ");
            for (j = 0; j < 10; j++) {
                if (seatMatrix[i][j] == 1)
                    System.out.print("|x| ");
                else
                    System.out.print("|O| ");
            }
            System.out.println();
        }
        System.out.println("================ Entrance ===============\n");
        System.out.println("Legend\n|x| = taken\n|O| = available\n");
    }

    public Seat[] assignSeat(String cinemaID, String date, String day, String time, String seatID, String customerID) {
        Showtime showtime = null;
        for (Showtime show : seats.get(cinemaID).keySet()) {
            if ((show.date.toLowerCase().compareTo(date) == 0) && (show.day.toLowerCase().compareTo(day) == 0)
                    && (show.time.toLowerCase().compareTo(time) == 0)) {
                showtime = show;
                break;
            }
        }
        if (showtime == null) {
            return null;
        }
        Seat[] s = seats.get(cinemaID).get(showtime);
        int row = seatID.charAt(0);
        row -= 65;
        int col = Integer.parseInt(String.valueOf(seatID.substring(1))) - 1;
        this.salesCount++;
        s[(row * 10) + col].assign(customerID);
        return s;
    }

    public boolean checkSeat(String cinemaID, String date, String day, String time, String seatID) {
        Showtime showtime = null;
        // TODO: make sure this works with multiple show times
        for (Showtime show : seats.get(cinemaID).keySet()) {
            if ((show.date.toLowerCase().compareTo(date) == 0) && (show.day.toLowerCase().compareTo(day) == 0)
                    && (show.time.toLowerCase().compareTo(time) == 0)) {
                showtime = show;
                break;
            }
        }
        if (showtime == null) {
            return false;
        }
        Seat[] s = seats.get(cinemaID).get(showtime);
        int row = seatID.charAt(0);
        row -= 65;
        int col = Integer.parseInt(String.valueOf(seatID.charAt(1)));
        return s[(row * 10) + col].assigned;
    }

    public void addReviews(String customerID, String review, float rating) {
        Rating newRating = new Model.Rating(customerID, review, rating);
        reviews.add(newRating);
        this.ratingCount++;
        overallRating = ((overallRating * review.length()) + rating) /
                (review.length() + 1);
    }

    public double getRating() {
        if (ratingCount >= 1)
            return overallRating;
        else
            return 0.0f;
    }

    public void addShowingPlaces(String cineplexID, String cinemaID) {
        if (showingPlaces == null) {
            showingPlaces = new HashMap<>();
            ArrayList<String> temp = new ArrayList<String>();
            temp.add(cinemaID);
            showingPlaces.put(cineplexID, temp);
        }

        else if (showingPlaces.containsKey(cineplexID)) {
            showingPlaces.computeIfAbsent(cineplexID, k -> new ArrayList<String>()).add(cinemaID);
        } else {
            ArrayList<String> temp = new ArrayList<String>();
            temp.add(cinemaID);
            showingPlaces.put(cineplexID, temp);
        }
    }

    public void printShowingPlaces() {
        for (String key : showingPlaces.keySet()) {
            System.out.println(
                    "\n=====================================================================\nCineplex: " + key);
            System.out.print("Cinemas: ");
            for (String str : showingPlaces.get(key)) {
                System.out.print(str + "    ");
            }
        }
        System.out.println("\n=====================================================================");
    }

    public void loadRatingsAndReviews() {
        // Get all ratings, then match rating movie title to this.title, if the same
        // then we know that the rating belongs to this movie, and then we add the
        // rating to this movie
        // Shit now i confuse rating and reviews, rating is number, revies is string
        Rating[] allRatings = new RatingDB().getRatings();
        double ratingSum = 0;
        this.ratingCount = 0;
        this.reviews = new ArrayList<Rating>();
        for (Rating rating : allRatings) {
            if (rating.getMovie().compareTo(this.getTitle()) == 0) {
                this.reviews.add(rating);
                this.ratingCount++;
                ratingSum += rating.getRating();
            }
        }
        this.overallRating = this.ratingCount == 0 ? 0 : ratingSum / ratingCount;
    }

    public void loadShowtimes() {
        Map<Map<String, ArrayList<String>>, Map<String, Map<Showtime, Seat[]>>> bla = DatabaseReader
                .readShowtime(title);
        this.showingPlaces = null;
        this.seats = null;
        for (Map<String, ArrayList<String>> bla2 : bla.keySet()) {
            this.showingPlaces = bla2;
            this.seats = bla.get(bla2);
        }
    }
}
