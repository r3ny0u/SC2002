package DatabaseBoundary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class ReadWriteTxt {
    public static void main(String[] args) {
        // Basically override the entire file
        try {
            FileWriter writer = new FileWriter("./csv/idk.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write("SCSE Depression\n"); // Movie Title
            bufferedWriter.write("Jason McGay\n"); // Director
            bufferedWriter.write(
                    "Join Jason on a journey to the NTU's most depressed school, SCSE.\n"); // Summary
            // Like this add movie details to the txt file ba
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String s;
            ArrayList<String> strings = new ArrayList<String>();
            FileReader reader = new FileReader("./csv/idk.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            while ((s = bufferedReader.readLine()) != null) {
                strings.add(s);
            }

            for (String st : strings) {
                System.out.println(st);
            }

            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
