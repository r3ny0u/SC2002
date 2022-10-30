package Model;
import java.util.HashMap;
import java.util.Map;

/**
 * I thought can use string and Cinema object to get the cinema
 * Like Python dictionar -> "some cinema name", Cinema object
 */
public class Cineplex {
    private Map<String, Cinema> cinemas;

    public Cineplex() {
        this.cinemas = new HashMap<String, Cinema>();
    }

    public Map<String, Cinema> getCinemas() {
        return this.cinemas;
    }

    public void setCinemas(Map<String, Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    public Cinema findCinema(String cinemaName)
    {
        return cinemas.get(cinemaName);
    }

    // More functions to be added next year
}
