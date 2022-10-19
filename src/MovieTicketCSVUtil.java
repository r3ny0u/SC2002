import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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

    public static float getCSVData(String filePath, DataType dt) {
        String[] data;
        try {
            data = readCSV(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        }

        return Float.valueOf(data[dataHeader.length + dt.ordinal() - 1]);
    }

    public static void writeCSVData(String filePath, String[] values) throws IOException {
        List<String[]> dataLines = new ArrayList<>();
        dataLines.add(dataHeader);
        dataLines.add(values);
        givenDataArray_whenConvertToCSV_thenOutputCreated(filePath, dataLines);
    }

    public static void main(String[] args) {
        String filepath = "./csv/MovieTicket.csv";
        try {
            String[] values = {
                    "20",
                    "30",
                    "35",
                    "1",
                    "1.05",
                    "1",
                    "1.05",
                    "1",
                    "0.8",
                    "0.9"
            };
            writeCSVData(filepath, values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(getCSVData(filepath, DataType.WEEKEND_PRICE));
    }
}
