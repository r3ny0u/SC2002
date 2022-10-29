import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Thanks https://www.baeldung.com/java-csv
 */
public class CSVUtil {
    public static String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(t -> escapeSpecialCharacters(t))
                .collect(Collectors.joining(","));
    }

    public static String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public static void givenDataArray_whenConvertToCSV_thenOutputCreated(String csv_file_name, List<String[]> dataLines)
            throws IOException {
        File csvOutputFile = new File(csv_file_name);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(t -> convertToCSV(t))
                    .forEach(pw::println);
        }
    }

    public static String[] readCSV(String filePath) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filePath));
        sc.useDelimiter(",|\\n");

        ArrayList<String> data = new ArrayList<String>();

        while (sc.hasNext()) {
            data.add(sc.next());
        }
        sc.close();

        String[] ret = new String[data.size()];
        data.toArray(ret);

        return ret;
    }

    public static void main(String[] args) throws IOException {
        // Test
        List<String[]> dataLines = new ArrayList<>();
        dataLines.add(new String[] { "John", "Doe", "38", "Comment Data\nAnother line of comment data" });
        dataLines.add(new String[] { "Jane", "Doe, Jr.", "19", "She said \"I'm being quoted\"" });
        givenDataArray_whenConvertToCSV_thenOutputCreated("./data/output.csv", dataLines);
    }
}
