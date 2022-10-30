import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import Model.Cineplex;

/**
 * Returns the different types of database.
 */
public class DatabaseReader {
    private static String cineplexDatabasePath = "./CineplexDB.txt";
    private static String adminDatabasePath = "./AdminDB.txt";
    private static String customerDatabasePath = "./CustomerDB.txt";
    private static String movieDatabasePath = "./MovieDB.txt";
    private static String ratingDatabasePath = "./RatingDB.txt";
    private static String transactionDatabasePath = "./TransactionDB.txt";

    /**
     * Returns all lines in a txt file.
     * 
     * @param txtPath The path to the txt file
     * @return An array list of strings
     */
    public static ArrayList<String> readtxt(String txtPath) {
        try {
            // Read array list of strings from txt file
            String s;
            ArrayList<String> strings = new ArrayList<String>();
            FileReader reader = new FileReader(txtPath);
            BufferedReader bufferedReader = new BufferedReader(reader);
            while ((s = bufferedReader.readLine()) != null) {
                strings.add(s);
            }

            bufferedReader.close();

            return strings;

        } catch (Exception e) {
            // If somehow got error, return empty array list
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }

    /**
     * Returns an array of Cineplex objects read from the admin database
     * 
     * @return An array of Cineplex objects
     */
    public static Cineplex[] readAdminDatabase() {
        ArrayList<String> strings = readtxt(adminDatabasePath);

        // From the arraylist of strings, create cineplex object
        // For now just return empty cinplex array
        return new Cineplex[1];
    }
}
