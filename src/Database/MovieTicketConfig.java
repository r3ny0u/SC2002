package Database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import DatabaseBoundary.DatabaseReader;

public class MovieTicketConfig {
    private static final String[] headers = { "Weekday Price", "Weekend Price", "PH Price", "2D Movie", "3D Movie", "Normal Cinema",
            "Platinum Cinema", "Adult", "Senior", "Child" };
    private static final String movieTicketConfigFilePath = "src/Database/MovieTicketConfig.txt";
    ArrayList<String> movieTicketConfigContent = new ArrayList<String>();

    public static ArrayList<String> readMovieTicketConfig() {
        return DatabaseReader.readtxt(movieTicketConfigFilePath);
    }

    public static void updateMovieTicketConfig(ArrayList<String> updatedConfigs) {
        try {
            FileWriter writer = new FileWriter(movieTicketConfigFilePath);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (String config : updatedConfigs) {
                bufferedWriter.write(config + "\n");
            }

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static float getWeekdayPrice() {
        return Float.parseFloat(readMovieTicketConfig().get(0));
    }

    public static float getWeekendPrice() {
        return Float.parseFloat(readMovieTicketConfig().get(1));
    }

    public static float getPHPrice() {
        return Float.parseFloat(readMovieTicketConfig().get(2));
    }

    public static float get2DMoviePercentage() {
        return Float.parseFloat(readMovieTicketConfig().get(3));
    }

    public static float get3DMoviePercentage() {
        return Float.parseFloat(readMovieTicketConfig().get(4));
    }

    public static float getNormalCinemaPercentage() {
        return Float.parseFloat(readMovieTicketConfig().get(5));
    }

    public static float getPlatinumCinemaPercentage() {
        return Float.parseFloat(readMovieTicketConfig().get(6));
    }

    public static float getAdultPercentage() {
        return Float.parseFloat(readMovieTicketConfig().get(7));
    }

    public static float getSeniorPercentage() {
        return Float.parseFloat(readMovieTicketConfig().get(8));
    }

    public static float getChildPercentage() {
        return Float.parseFloat(readMovieTicketConfig().get(9));
    }

    public static void updateWeekdayPrice(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(0, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    public static void updateWeekendPrice(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(1, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    public static void updatePHPrice(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(2, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    public static void update2DMoviePercentage(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(3, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    public static void update3DMoviePercentage(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(4, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    public static void updateNormalCinemaPercentage(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(5, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    public static void updatePlatinumCinemaPercentage(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(6, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    public static void updateAdultPercentage(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(7, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    public static void updateSeniorPercentage(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(8, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    public static void updateChildPercentage(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(9, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    public static void printConfigDetails() {
        for (int i = 0; i < headers.length; i++) {
            System.out.println(headers[i] + ": " + MovieTicketConfig.readMovieTicketConfig().get(i));
        }
        System.out.println("\n");
    }

    /**
     * Testing testing 123
     * <p>
     * Sound check 12 check 12
     * @param args
     */
    public static void main(String[] args) {
        printConfigDetails();

        // Changing some values
        float newPHPrice = 69.420f;
        
        updatePHPrice(newPHPrice);

        printConfigDetails();

        newPHPrice = 35f;

        printConfigDetails();
    }
}
