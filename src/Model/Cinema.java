package Model;
import java.util.HashMap;
import java.util.Map;

public class Cinema {
    private Map<String, Movie> movies;
    String code;
    private Seat[] seats;

    public Cinema() {
        movies = new HashMap<String, Movie>();
        seats = new Seat[69];
    }

    public Map<String, Movie> getMovies() {
        return this.movies;
    }

    public void setMovies(Map<String, Movie> movies) {
        this.movies = movies;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Seat[] getSeats() {
        return this.seats;
    }

    public void setSeats(Seat[] seats) {
        this.seats = seats;
    }

}
