package Model;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieTicketCSVUtil extends CSVUtil {
    private static String[] dataHeader = {
            "Weekday Price",
            "Weekend Price",
            "PH Price",
            "2D Movie",
            "3D Movie",
            "Normal Cinema",
            "Platinum Cinema",
            "Adult",
            "Senior",
            "Child" };

    public static float getCSVData(String filePath, DataType dType) {
        String[] data;
        try {
            data = readCSV(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        }

        return Float.valueOf(data[dataHeader.length + dType.ordinal()]);
    }

    public static void writeCSVData(String filePath, String[] values) throws IOException {
        List<String[]> dataLines = new ArrayList<>();
        dataLines.add(dataHeader);
        dataLines.add(values);
        givenDataArray_whenConvertToCSV_thenOutputCreated(filePath, dataLines);
    }

    public static void editCSVData(String filePath, DataType dtType, float value) {
        String[] data;
        try {
            data = readCSV(filePath);
            data[dataHeader.length + dtType.ordinal()] = Float.toString(value);
            writeCSVData(filePath, Arrays.copyOfRange(data, data.length / 2, data.length));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Look man i dont know why cant print the word Child, oh yea we need to do the
     * documentation in the end to rip
     */
    public static void printCSVData(String filePath) {
        String[] data;
        try {
            data = readCSV(filePath);
            System.out.printf("\nContents of %s of length %d: \n", filePath, data.length);
            for (int i = 0; i < data.length / 2; i++) {
                System.out.printf("%-20s : %s\n", data[i], data[data.length / 2 + i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        printCSVData(Constants.MOVIE_TICKET_CSV_FILEPATH);
        editCSVData(Constants.MOVIE_TICKET_CSV_FILEPATH, DataType.WEEKDAY_PRICE, 25);
        System.out.println(getCSVData(Constants.MOVIE_TICKET_CSV_FILEPATH, DataType.WEEKDAY_PRICE));
        editCSVData(Constants.MOVIE_TICKET_CSV_FILEPATH, DataType.WEEKDAY_PRICE, 20);
        printCSVData(Constants.MOVIE_TICKET_CSV_FILEPATH);
    }
}

// try {
// String[] values = {
// "20",
// "30",
// "35",
// "1",
// "1.05",
// "1",
// "1.05",
// "1",
// "0.8",
// "0.9"
// };
// writeCSVData(filepath, values);
// } catch (Exception e) {
// e.printStackTrace();
// }

// System.out.println(getCSVData(filepath, DataType.WEEKEND_PRICE));
