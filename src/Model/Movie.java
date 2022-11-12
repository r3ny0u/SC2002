package Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import Database.CinemaDB;
import Database.RatingDB;
import DatabaseBoundary.DatabaseReader;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Scanner;

/**
 * A class for movie
 */
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
    protected String movieAgeRating;

    public Movie(String title, String status, String synopsis, String director, ArrayList<String> casts) {
        this.title = title;
        this.status = status;
        this.synopsis = synopsis;
        this.director = director;
        this.casts = casts;
        this.loadRatingsAndReviews();
        this.loadShowtimes();
        this.movieAgeRating = title.substring(title.lastIndexOf(" ") + 2, title.length() - 1);
    }

    /**
     * Gets the seat of the movie for the showtime and cinema provided
     * 
     * @param cinemaID A String representing the cinema ID
     * @param date     A String representing the date
     * @param day      A String representing the day
     * @param time     A String representing the time
     * @return An array of Seat objects
     */
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

    /**
     * Gets the status of the movie
     * 
     * @return A String representing the movie status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Gets the title of the movie
     * 
     * @return A String representing the movie title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the sale count of the movie
     * 
     * @return An integer representing the sales count
     */
    public int getSalesCount() {
        return this.salesCount;
    }

    /**
     * Gets the rating count of the movie
     * 
     * @return An integer representing the rating count
     */
    public int getRatingCount() {
        return this.ratingCount;
    }

    /**
     * Prints the details of the movie
     */
    public void printMovieDetails() {
        this.loadRatingsAndReviews();
        System.out.println("\n================== Movie Details =======================");
        System.out.println("Title     : " + title);
        System.out.println("Status    : " + status);
        System.out.println("Synopsis  : " + synopsis);
        System.out.println("Director  : " + director);
        System.out.println("Cast      : " + String.join(", ", casts));
        if (ratingCount == 0) {
            System.out.printf("Rating    : NA / 5.0 STARS\n");
        } else {
            System.out.printf("Rating    : %.1f / 5.0 STARS\n", this.overallRating);
        }

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

    @Deprecated
    /**
     * @deprecated
     *             Adds a new showtime to the movie
     * @param cinemaID  A String representing the cinema ID
     * @param date      A String representing the date
     * @param showtimes An ArrayList representing the showtimes
     */
    public void addShowtimes(String cinemaID, String date, ArrayList<String> showtimes) {
        int i = 0, j = 0;
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

    /**
     * Prints all the showtimes available for the movie
     */
    public void printAllShowtimes() {

        CinemaDB cinemaDB = new CinemaDB();

        String oldDate = "date", oldCinemaID = "ID";

        for (String cineplexID : showingPlaces.keySet()) {
            System.out.println(
                    "--------------------\n\nCineplex: " + cineplexID);

            for (String cinemaID : showingPlaces.get(cineplexID)) {
                if (cinemaID.equals(oldCinemaID) == false) {
                    oldCinemaID = cinemaID;
                    System.out.println(
                            "\nCinema: " + cinemaID + " (" + cinemaDB.getCinemaFromID(cinemaID).getCinemaType() + ")");
                }
                ArrayList<Showtime> showtimeAL = new ArrayList<>(seats.get(cinemaID).keySet());

                showtimeAL.sort(new Comparator<Showtime>() {

                    @Override
                    public int compare(Showtime s1, Showtime s2) {
                        return (s1.date + s1.time).compareTo(s2.date + s2.time);
                    }

                });

                for (Showtime st : showtimeAL) {
                    if (st.date.equals(oldDate) == false) {
                        oldDate = st.date;
                        System.out.println();
                        System.out.print("        " + oldDate + " - " + st.time + " ");
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

    /**
     * Print the seats available
     * 
     * @param cinemaID A String representing the cinema ID
     * @param date     A String representing the date
     * @param showtime A String representing the day
     */
    public void printSeats(String cinemaID, String date, String showtime) {
        int i, j;
        int[][] seatMatrix = new int[10][10];
        i = j = 0;

        for (Showtime st : seats.get(cinemaID).keySet()) {
            if (st.date.compareTo(date) != 0 || st.time.compareTo(showtime) != 0)
                continue;

            for (Seat s : seats.get(cinemaID).get(st)) {
                if (s.assigned)
                    seatMatrix[i][j] = 1;
                else
                    seatMatrix[i][j] = 0;
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

    /**
     * Assign a new seat
     * 
     * @param cinemaID   A String representing the cinema ID
     * @param date       A String representing the date
     * @param time       A String representing the time
     * @param seatID     A String representing the seat ID
     * @param customerID A String representing the customer ID
     * @return An array of Seat objects
     */
    public Seat[] assignSeat(String cinemaID, String date, String time, String seatID, String customerID) {
        Showtime showtime = null;
        for (Showtime show : seats.get(cinemaID).keySet()) {
            if ((show.date.toLowerCase().compareTo(date) == 0) && (show.time.toLowerCase().compareTo(time) == 0)) {
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

    /**
     * Checks whether the seat is alrady taken
     * 
     * @param cinemaID A String representing the cinema ID
     * @param date     A String representing the date
     * @param time     A String representing the time
     * @param seatID   A String representing the seat ID
     * @return true if seat choice is not occupied
     */
    public boolean checkSeat(String cinemaID, String date, String time, String seatID) {
        if (!(('A' < seatID.charAt(0) && seatID.charAt(0) < 'K')
                && (1 <= Integer.parseInt(seatID.substring(1))
                        && Integer.parseInt(seatID.substring(1)) <= 10)))
            return false;
        Showtime showtime = null;
        this.loadShowtimes();
        for (Showtime show : seats.get(cinemaID).keySet()) {
            if ((show.date.toLowerCase().compareTo(date) == 0) && (show.time.toLowerCase().compareTo(time) == 0)) {
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
        int col = Integer.parseInt(String.valueOf(seatID.substring(1)));
        return s[(row * 10) + col - 1].assigned;
    }

    @Deprecated
    public void addReviews(String customerID, String review, float rating) {
        Rating newRating = new Model.Rating(customerID, review, rating);
        reviews.add(newRating);
        this.ratingCount++;
        overallRating = ((overallRating * review.length()) + rating) /
                (review.length() + 1);
    }

    /**
     * Gets the rating of the movie
     * 
     * @return A double representing the rating of the movie
     */
    public double getRating() {
        if (ratingCount >= 1)
            return overallRating;
        else
            return 0.0f;
    }

    @Deprecated
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

    @Deprecated
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

    /**
     * Loads the ratings and reviews for this movie
     */
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

    /**
     * Loads the showtimes for this movie
     */
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

    /**
     * Gets the movie age rating
     * 
     * @return A String representing the movie age rating
     */
    public String getMovieAgeRating() {
        return movieAgeRating;
    }

    /**
     * Sets the movie age rating
     * 
     * @param movieAgeRating A String representing the movie age rating
     */
    public void setMovieRating(String movieAgeRating) {
        this.movieAgeRating = movieAgeRating;
    }

    /**
     * Ask user to choose the cineplex for this moive
     * 
     * @return A String representing the cineplex ID
     */
    public String chooseCineplex() {
        System.out.println("Cineplexes");
        int count = 1;
        int cineplexChoice;
        for (String cineplexID : showingPlaces.keySet()) {
            System.out.println("\t" + count + ": " + cineplexID);
            count++;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please choose a cineplex (enter a number): ");
        while (!scanner.hasNextInt()) {
            System.out.print("Error, Invalid choice!! Try again: ");
            scanner.next();
        }
        cineplexChoice = scanner.nextInt();
        count = 0;
        for (String cineplexID : showingPlaces.keySet()) {
            count++;
            if (count == cineplexChoice) {
                return cineplexID;
            }
        }
        return "ooooooooooooooooooooooooooooooooooooof";
    }

    /**
     * Ask user to choose the cinema for this movie
     * 
     * @param cineplexID A String representing which cineplex the cinema is from
     * @return A String representing the cinema ID
     */
    public String chooseCinema(String cineplexID) {
        CinemaDB cinemaDB = new CinemaDB();
        Scanner scanner = new Scanner(System.in);
        int count = 1;
        System.out.println("Cinemas");
        for (String cinemaID : showingPlaces.get(cineplexID)) {
            System.out.println(
                    "\t" + count + ": " + cinemaID + " (" + cinemaDB.getCinemaFromID(cinemaID).getCinemaType() + ")");
            count++;
        }
        System.out.print("Please choose a cinema (enter a number): ");
        while (!scanner.hasNextInt()) {
            System.out.print("Error, Invalid choice!! Try again: ");
            scanner.next();
        }
        int choice = scanner.nextInt();
        count = 0;
        for (String cinemaID : showingPlaces.get(cineplexID)) {
            count++;
            if (count == choice)
                return cinemaID;
        }
        String idk = "This is the final message from Ren You. Gluck have fun ok bye.";
        if (choice == 12345) {
            System.out.println(idk);
        }
        return idk;
    }

    /**
     * Ask user to choose the date for this movie
     * 
     * @param cinemaID A String representing the cinema the movie is in
     * @return A String representing the date
     */
    public String chooseDate(String cinemaID) {
        System.out.println("Showdates");
        ArrayList<Showtime> showtimeAL = new ArrayList<>(seats.get(cinemaID).keySet());
        String oldDate = "date", oldCinemaID = "ID";
        showtimeAL.sort(new Comparator<Showtime>() {

            @Override
            public int compare(Showtime s1, Showtime s2) {
                return (s1.date + s1.time).compareTo(s2.date + s2.time);
            }

        });

        int count = 1;
        for (Showtime st : showtimeAL) {
            if (st.date.equals(oldDate) == false) {
                oldDate = st.date;
                System.out.print("\n\t" + count + ": " + oldDate + " - " + st.time + " ");
                count++;
            } else {
                System.out.print(st.time + " ");
            }
        }

        oldDate = "date";
        oldCinemaID = "ID";

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nPlease choose a showing date (enter a number): ");

        while (!scanner.hasNextInt()) {
            System.out.print("Error, Invalid choice!! Try again: ");
            scanner.next();
        }

        int choice = scanner.nextInt();
        count = 0;

        for (Showtime st : showtimeAL) {
            if (st.date.equals(oldDate) == false) {
                oldDate = st.date;
                count++;
            }
            if (count == choice)
                return oldDate;
        }
        return null;
    }

    /**
     * Ask user to choose the time for the this movie
     * 
     * @param dateChoice A String representing the date for the movie
     * @param cinemaID   A String representing the cinema the movie is in
     * @return A String representing the time
     */
    public String chooseTime(String dateChoice, String cinemaID) {
        ArrayList<Showtime> showtimeAL = new ArrayList<>(seats.get(cinemaID).keySet());
        System.out.println("Showtimes: ");
        String oldDate = "date", oldCinemaID = "ID";
        showtimeAL.sort(new Comparator<Showtime>() {

            @Override
            public int compare(Showtime s1, Showtime s2) {
                return (s1.date + s1.time).compareTo(s2.date + s2.time);
            }

        });

        int count = 1;
        Scanner scanner = new Scanner(System.in);
        for (Showtime st : showtimeAL) {
            if (st.date.compareTo(dateChoice) == 0) {
                System.out.println("\t" + count + ": " + st.time);
                count++;
            }
        }

        System.out.print("Please choose the showtime (enter a number): ");
        while (!scanner.hasNextInt()) {
            System.out.print("Error, Invalid choice!! Try again: ");
            scanner.next();
        }
        int choice = scanner.nextInt();

        count = 0;
        for (Showtime st : showtimeAL) {
            if (st.date.compareTo(dateChoice) == 0) {
                count++;
            }
            if (count == choice) {
                return st.time;
            }
        }
        return "oooooooooooooooooof";
    }
}
