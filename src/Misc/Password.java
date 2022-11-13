package Misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// Acknowledgment: http://www.cse.chalmers.se/edu/year/2015/course/TDA602/Eraserlab/pwdmasking.html

/** Class for reading password */
public class Password {

    /** Empty constructor */
    public Password() {
    }

    /**
     * Reads the password from input
     * @param prompt The prompt to display to the user
     * @return The password as entered by the user
     */
    public static String readPassword(String prompt) {
        EraserThread et = new EraserThread(prompt);
        Thread mask = new Thread(et);
        mask.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String password = "";

        try {
            password = in.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        // stop masking
        et.stopMasking();
        // return the password entered by the user
        return password;
    }
}
