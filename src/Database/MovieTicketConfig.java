package Database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import DatabaseBoundary.DatabaseReader;

/**
 * A static class for interfacing movie ticket configs
 */
public class MovieTicketConfig {
    private static final String movieTicketConfigFilePath = "src/Database/MovieTicketConfig.txt";

    /**
     * An ArrayList representing the movie ticket configs, which contains the
     * following information
     * 
     * Weekday price, weekend price, public holiday price, 2D movie markup, 3D movie
     * markup, normal cinema markup, platinum cinema markup, adult price markup,
     * senior price markup, and child price markup
     */
    ArrayList<String> movieTicketConfigContent = new ArrayList<String>();

    /**
     * Reads the movie ticket configs file
     * 
     * @return
     */
    public static ArrayList<String> readMovieTicketConfig() {
        return DatabaseReader.readtxt(movieTicketConfigFilePath);
    }

    /**
     * Updates the movie ticket config file
     * 
     * @param updatedConfigs An ArrayList representing the updated configs
     */
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

    /**
     * Gets the weekday price
     * 
     * @return A float representing the weekday price
     */
    public static float getWeekdayPrice() {
        return Float.parseFloat(readMovieTicketConfig().get(0));
    }

    /**
     * Gets the weekend price
     * 
     * @return A float representing the weekday price
     */
    public static float getWeekendPrice() {
        return Float.parseFloat(readMovieTicketConfig().get(1));
    }

    /**
     * Gets the public holiday price
     * 
     * @return A float representing the public holiday price
     */
    public static float getPHPrice() {
        return Float.parseFloat(readMovieTicketConfig().get(2));
    }

    /**
     * Gets the 2D movie price markup
     * 
     * @return A float representing the 2D movie price markup
     */
    public static float get2DMovieMarkup() {
        return Float.parseFloat(readMovieTicketConfig().get(3));
    }

    /**
     * Gets the 3D movie price markup
     * 
     * @return A float representing the 3D movie price markup
     */
    public static float get3DMovieMarkup() {
        return Float.parseFloat(readMovieTicketConfig().get(4));
    }

    /**
     * Gets the normal cinema price markup
     * 
     * @return A float representing the normal cinema price markup
     */
    public static float getNormalCinemaMarkup() {
        return Float.parseFloat(readMovieTicketConfig().get(5));
    }

    /**
     * Gets the normal cinema price markup
     * 
     * @return A float representing the platinum cinema price markup
     */
    public static float getPlatinumCinemaMarkup() {
        return Float.parseFloat(readMovieTicketConfig().get(6));
    }

    /**
     * Gets the adult price markup
     * 
     * @return A float representing the adult price markup
     */
    public static float getAdultMarkup() {
        return Float.parseFloat(readMovieTicketConfig().get(7));
    }

    /**
     * Gets the senior price markup
     * 
     * @return A float representing the senior price markup
     */
    public static float getSeniorMarkup() {
        return Float.parseFloat(readMovieTicketConfig().get(8));
    }

    /**
     * Gets the child price markup
     * 
     * @return A float representing the child price markup
     */
    public static float getChildMarkup() {
        return Float.parseFloat(readMovieTicketConfig().get(9));
    }

    /**
     * Updates the weekday price
     * 
     * @param value A float representing the weekday price
     */
    public static void updateWeekdayPrice(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(0, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    /**
     * Updates the weekend price
     * 
     * @param value A float representing the weekend price
     */
    public static void updateWeekendPrice(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(1, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    /**
     * Updates the public holiday price
     * 
     * @param value A float representing the public holiday price
     */
    public static void updatePHPrice(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(2, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    /**
     * Updates the 2D movie price markup
     * 
     * @param value A float representing the 2D movie price markup
     */
    public static void update2DMovieMarkup(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(3, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    /**
     * Updates the 3D movie price markup
     * 
     * @param value A float representing the 3D movie price markup
     */
    public static void update3DMovieMarkup(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(4, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    /**
     * Updates the normal cinema price markup
     * 
     * @param value A float representing the normal cinema price markup
     */
    public static void updateNormalCinemaMarkup(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(5, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    /**
     * Updates the platinum cinema price markup
     * 
     * @param value A float representing the platinum cinema price markup
     */
    public static void updatePlatinumCinemaMarkup(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(6, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    /**
     * Updates the adult price markup
     * 
     * @param value A float representing the adult price markup
     */
    public static void updateAdultMarkup(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(7, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    /**
     * Updates the senior price markup
     * 
     * @param value A float representing the senior price markup
     */
    public static void updateSeniorMarkup(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(8, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    /**
     * Updates the child price markup
     * 
     * @param value A float representing the child price markup
     */
    public static void updateChildMarkup(float value) {
        ArrayList<String> bla = readMovieTicketConfig();
        bla.set(9, Float.toString(value));
        updateMovieTicketConfig(bla);
    }

    /**
     * Print out the config details
     */
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
}
