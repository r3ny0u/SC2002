package Database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import DatabaseBoundary.DatabaseReader;

public class MovieTicketConfig {
    private static final String[] headers = { "Weekday Price", "Weekend Price", "PH Price", "2D Movie", "3D Movie",
            "Normal Cinema",
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

    public static float get2DMovieMarkup() {
        return Float.parseFloat(readMovieTicketConfig().get(3));
    }

    public static float get3DMovieMarkup() {
        return Float.parseFloat(readMovieTicketConfig().get(4));
    }

    public static float getNormalCinemaMarkup() {
        return Float.parseFloat(readMovieTicketConfig().get(5));
    }

    public static float getPlatinumCinemaMarkup() {
        return Float.parseFloat(readMovieTicketConfig().get(6));
    }

    public static float getAdultMarkup() {
        return Float.parseFloat(readMovieTicketConfig().get(7));
    }

    public static float getSeniorMarkup() {
        return Float.parseFloat(readMovieTicketConfig().get(8));
    }

    public static float getChildMarkup() {
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

    public static void update2DMovieMarkup(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(3, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    public static void update3DMovieMarkup(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(4, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    public static void updateNormalCinemaMarkup(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(5, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    public static void updatePlatinumCinemaMarkup(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(6, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    public static void updateAdultMarkup(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(7, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    public static void updateSeniorMarkup(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(8, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    public static void updateChildMarkup(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(9, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    public static void printConfigDetails() {
        System.out.printf(" 1. Weekday Pricing        @ SGD %-2.2f\n", MovieTicketConfig.getWeekdayPrice());
        System.out.printf(" 2. Weekend Pricing        @ SGD %-2.2f\n", MovieTicketConfig.getWeekendPrice());
        System.out.printf(" 3. PH Pricing             @ SGD %-2.2f\n", MovieTicketConfig.getPHPrice());
        System.out.printf(" 4. 2D Movie Markup        @ SGD %-2.2f\n", MovieTicketConfig.get2DMovieMarkup());
        System.out.printf(" 5. 3D Movie Markup        @ SGD %-2.2f\n", MovieTicketConfig.get3DMovieMarkup());
        System.out.printf(" 6. Normal Cinema Markup   @ SGD %-2.2f\n",
                MovieTicketConfig.getNormalCinemaMarkup());
        System.out.printf(" 7. Platinum Cinema Markup @ SGD %-2.2f\n",
                MovieTicketConfig.getPlatinumCinemaMarkup());
        System.out.printf(" 8. Adult Markup           @ SGD %-2.2f\n", MovieTicketConfig.getAdultMarkup());
        System.out.printf(" 9. Senior Markup          @ SGD %-2.2f\n", MovieTicketConfig.getSeniorMarkup());
        System.out.printf("10. Child Markup           @ SGD %-2.2f\n", MovieTicketConfig.getChildMarkup());
    }

    public static void main(String[] args) {
        printConfigDetails();

        // Changing some values
        float newPHPrice = 69.420f;

        updatePHPrice(newPHPrice);

        printConfigDetails();

        newPHPrice = 35f;

        updatePHPrice(newPHPrice);

        printConfigDetails();
    }
}
