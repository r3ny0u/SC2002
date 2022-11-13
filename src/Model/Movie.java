package Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import Database.CinemaDB;
import Database.RatingDB;
import DatabaseBoundary.DatabaseReader;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Scanner;

/**
 * A class for movie
 */
public class Movie {
    /** A String representing the movie title */
    protected String title;
    /** A String representing the movie status */
    protected String status;
    /** A String representing the movie synopsis */
    protected String synopsis;
    /** A String representing the movie director */
    protected String director;
    /** An ArrayList of Strings representing the casts */
    protected ArrayList<String> casts;
    /** A double representing the overall movie rating */
    protected double overallRating;
    /** An int representing the count of rating */
    protected int ratingCount;
    /** An ArrayList of Strings representing the reviews for the movie */
    protected ArrayList<Rating> reviews = new ArrayList<Rating>();
    /** An int representing the sales count */
    protected int salesCount = 0;
    /** A Hasp map that maps the Cineplex ID to an ArrayList of Cinema IDs */
    protected Map<String, ArrayList<String>> showingPlaces = new HashMap<String, ArrayList<String>>(); // Cineplex ->
                                                                                                       // Cinema
    /**
     * A Hash map that maps the Cinema ID to a Hash map that maps a Showtime object
     * to and array of Seat objects
     */
    protected Map<String, Map<Showtime, Seat[]>> seats; // cinemaID->showtimes->seats
    /** A String representing the movie age rating */
    protected String movieAgeRating;

    /**
     * Constructor
     * 
     * @param title    A String representing the movie title
     * @param status   A String representing the movie status
     * @param synopsis A String representing the movie synopsis
     * @param director A String representing the movie director
     * @param casts    An ArrayList of Strings representing the casts
     */
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

    /**
     * Prints all the showtimes available for the movie
     */
    public void printAllShowtimes() {

        CinemaDB cinemaDB = new CinemaDB();

        String oldDate = "date", oldCinemaID = "ID";

        ArrayList<String> cineplexAL = new ArrayList<>(showingPlaces.keySet());
        cineplexAL.sort(new Comparator<String>() {
            @Override
            public int compare(String cx1, String cx2) {
                return cx1.compareTo(cx2);
            }
        });

        for (String cineplexID : cineplexAL) {
            System.out.println(
                    "Cineplex: " + cineplexID);

            ArrayList<String> cinemaAL = new ArrayList<>(showingPlaces.get(cineplexID));
            cinemaAL.sort(new Comparator<String>() {
                @Override
                public int compare(String ca1, String ca2) {
                    return ca1.compareTo(ca2);
                }
            });

            for (String cinemaID : cinemaAL) {
                if (cinemaID.equals(oldCinemaID) == false) {
                    oldCinemaID = cinemaID;
                    System.out.println(
                            "\nCinema  : " + cinemaID + " (" + cinemaDB.getCinemaFromID(cinemaID).getCinemaType()
                                    + ")");
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
                        System.out.print("          " + oldDate + " ("
                                + new SimpleDateFormat("EEEE", Locale.UK).format((new Date(st.date))) + ")" + " - "
                                + st.time + " ");
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

        System.out.println("========================= Screen =========================\n");

        System.out.println("\t 1   2   3   4   5\t 6   7   8   9   10");
        for (i = 0; i < 10; i++) {
            System.out.print((char) (65 + i) + "\t");
            for (j = 0; j < 10; j++) {
                if (seatMatrix[i][j] == 1)
                    System.out.print("|x| ");
                else
                    System.out.print("|O| ");

                if (j == 4) {
                    System.out.print("\t");
                }
            }
            System.out.print(" \t" + (char) (65 + i));
            System.out.println();
        }
        System.out.println("\n======================== Entrance ========================\n");
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
        if (!(('A' <= seatID.charAt(0) && seatID.charAt(0) < 'K')
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
        System.out.println("Cineplexes\n");
        int count = 1;
        int cineplexChoice;

        ArrayList<String> cineplexAL = new ArrayList<>(showingPlaces.keySet());
        cineplexAL.sort(new Comparator<String>() {
            @Override
            public int compare(String cx1, String cx2) {
                return cx1.compareTo(cx2);
            }
        });

        for (String cineplexID : cineplexAL) {
            System.out.println("\t" + count + ": " + cineplexID);
            count++;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("\t" + count + ": Exit");

        while (true) {
            System.out.print("\nPlease choose a cineplex (enter a number): ");
            if (!scanner.hasNextInt()) {

                System.out.print("Error, Invalid choice!! Try again\n");
                System.out.print(String.format("\033[2A"));
                System.out.print("\033[2K");
                scanner.next();
                continue;
            }

            cineplexChoice = scanner.nextInt();
            if (cineplexChoice == count) {
                return "exit";
            }
            if (cineplexChoice < 1 || cineplexChoice > count) {
                System.out.print("Error, Invalid choice!! Try again\n");
                System.out.print(String.format("\033[2A"));
                System.out.print("\033[2K");
                continue;
            }
            break;
        }

        count = 0;
        for (String cineplexID : cineplexAL) {
            count++;
            if (count == cineplexChoice) {
                return cineplexID;
            }
        }

        return "oof";
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
        int cinemaChoice;
        System.out.println("Cinemas\n");

        ArrayList<String> cinemaAL = new ArrayList<>(showingPlaces.get(cineplexID));
        cinemaAL.sort(new Comparator<String>() {
            @Override
            public int compare(String ca1, String ca2) {
                return ca1.compareTo(ca2);
            }
        });

        for (String cinemaID : cinemaAL) {
            System.out.println(
                    "\t" + count + ": " + cinemaID + " (" + cinemaDB.getCinemaFromID(cinemaID).getCinemaType() + ")");
            count++;
        }
        System.out.println("\t" + count + ": Exit");

        while (true) {
            System.out.print("\nPlease choose a cinema (enter a number): ");
            if (!scanner.hasNextInt()) {

                System.out.print("Error, Invalid choice!! Try again\n");
                System.out.print(String.format("\033[2A"));
                System.out.print("\033[2K");
                scanner.next();
                continue;
            }

            cinemaChoice = scanner.nextInt();
            if (cinemaChoice == count) {
                return "exit";
            }
            if (cinemaChoice < 1 || cinemaChoice > count) {
                System.out.print("Error, Invalid choice!! Try again\n");
                System.out.print(String.format("\033[2A"));
                System.out.print("\033[2K");
                continue;
            }
            break;
        }

        count = 0;
        for (String cinemaID : cinemaAL) {
            count++;
            if (count == cinemaChoice)
                return cinemaID;
        }
        return "0";
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
        int dateChoice;
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
                System.out.print("\n\t" + count + ": " + oldDate + " ("
                        + new SimpleDateFormat("EEEE", Locale.UK).format((new Date(st.date))) + ")" + " - " + st.time
                        + " ");
                count++;
            } else {
                System.out.print(st.time + " ");
            }
        }
        System.out.println("\n\t" + count + ": Exit");
        System.out.println();
        oldDate = "date";
        oldCinemaID = "ID";
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Please choose a showdate (enter a number): ");
            if (!scanner.hasNextInt()) {

                System.out.print("Error, Invalid choice!! Try again\n");
                System.out.print(String.format("\033[2A"));
                System.out.print("\033[2K");
                scanner.next();
                continue;
            }

            dateChoice = scanner.nextInt();
            if (dateChoice == count) {
                return "exit";
            }
            if (dateChoice < 1 || dateChoice > count) {
                System.out.print("Error, Invalid choice!! Try again\n");
                System.out.print(String.format("\033[2A"));
                System.out.print("\033[2K");
                continue;
            }
            break;
        }

        count = 0;

        for (Showtime st : showtimeAL) {
            if (st.date.equals(oldDate) == false) {
                oldDate = st.date;
                count++;
            }
            if (count == dateChoice)
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
        System.out.println("Showtimes\n");
        String oldDate = "date", oldCinemaID = "ID";
        int timeChoice;
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
        System.out.println("\t" + count + ": Exit");
        System.out.println();
        while (true) {
            System.out.print("Please choose a showtime (enter a number): ");
            if (!scanner.hasNextInt()) {

                System.out.print("Error, Invalid choice!! Try again\n");
                System.out.print(String.format("\033[2A"));
                System.out.print("\033[2K");
                scanner.next();
                continue;
            }

            timeChoice = scanner.nextInt();
            if (timeChoice == count) {
                return "exit";
            }
            if (timeChoice < 1 || timeChoice > count) {
                System.out.print("Error, Invalid choice!! Try again\n");
                System.out.print(String.format("\033[2A"));
                System.out.print("\033[2K");
                continue;
            }
            break;
        }

        count = 0;
        for (Showtime st : showtimeAL) {
            if (st.date.compareTo(dateChoice) == 0) {
                count++;
            }
            if (count == timeChoice) {
                return st.time;
            }
        }
        return "oooooooooooooooooof";
    }
}
