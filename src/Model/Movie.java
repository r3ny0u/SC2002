package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Movie{
    protected String title;
    protected String status;
    protected String synopsis;
    protected String director;
    protected ArrayList<String> casts;
    protected double overallRating;
    protected int ratingCount;
    protected ArrayList<Rating> reviews;
    protected Map<String, Seat[]> seats;
    protected int salesCount;

    // DO NOT MODIFY THIS CONSTRUCTOR, MAKE ANOTHER IF YOU NEED ANOTHER CONSTRUCTOR
    public Movie(String title, String status, String synopsis, String director, ArrayList<String> casts) {
        this.title = title;
        this.status = status;
        this.synopsis = synopsis;
        this.director = director;
        this.casts = casts;
    }

    public Movie(String title, String status, String synopsis, String director, ArrayList<String> casts, ArrayList<String> cinemaNames) {
        this.title = title;
        this.status = status;
        this.synopsis = synopsis;
        this.director = director;
        this.casts = casts;
        this.overallRating = 5.0;
        this.reviews = new ArrayList<Rating>();
        this.ratingCount = 0;
        this.salesCount = 0;
        
        int n=0,i=0,j=0;
        Seat[] s = new Seat[100];
        for(n=0; n < cinemaNames.size(); n++)
        {
            for(j=1; j<=100; j++)
            {   
                if(j%10 == 0)
                    i++;
                String row = "" + (char)(65+i);
                s[j-1] = new Seat(row + (j-1));
                
            }
            seats.put(cinemaNames.get(n),s);
        }
    }

    public String getTitle() {
        return this.title;
    }

    public int getSalesCount(){
        return this.salesCount;
    }

    public void printMovieDetails() {
        System.out.println("Title: " + title);
        System.out.println("Status: " + status);
        System.out.println("Synopsis:");
        System.out.println(synopsis);
        System.out.println("Director: " + director);
        System.out.println("Cast: " + String.join(", ", casts));
        System.out.println("Ratings: " + overallRating + " / 5.0 stars");
        // for (Rating rating : reviews) {
        //     // Haven't made this method thing yet
        //     // rating.printRating()
        // }
    }

    public void printSeats(String cinemaName) {
        int i,j;
        int[][] seatMatrix = new int[10][10];
        i = j = 0;

        for (Seat s : seats.get(cinemaName)) {
            if(s.assigned)
                seatMatrix[i][j] = 1;
            else
                seatMatrix[i][i] = 0;
            j++;

            if(j == 9)
            {
                j=0;
                i++;
            }
            System.out.println("\n");
        }

        System.out.println("=================Screen=================\n");
        for(i = 0; i < 10; i++)
        {
            System.out.println((char)(65+i));
            for(j = 0; j < 10; j++)
            {
                if (seatMatrix[i][j] == 1)
                    System.out.println("|x| ");
                else    
                    System.out.println("|O| ");
            }
            System.out.println();
        }
        System.out.println("=================Entrance================\n");
        System.out.println("Legend\n|x| = taken\n|O| = available\n");
    }

    public boolean assignSeat(String cinemaName, String seatID, String customerID)
    {
        Seat[] s = seats.get(cinemaName);
        int row = seatID.charAt(0);
        row -= 65;
        int col = Integer.parseInt(String.valueOf(seatID.charAt(1)));
        this.salesCount++;
        return s[(row*10)+col].assign(customerID);
    }
    
    public boolean checkSeat(String cinemaName, String seatID)
    {
        Seat[] s = seats.get(cinemaName);
        int row = seatID.charAt(0);
        row -= 65;
        int col = Integer.parseInt(String.valueOf(seatID.charAt(1)));
        return s[(row*10)+col].assigned;
    }

    public void addReviews(String customerID, String review, float rating) {
        Rating newRating = new Model.Rating(customerID,review,rating);
        reviews.add(newRating);
        this.ratingCount++;
        overallRating = ((overallRating * review.length()) + rating) /
        (review.length() + 1);
    }
    public double getRating(){
        if (ratingCount>1)
            return overallRating;
        else
            return -1;
    }
}
