package application.model;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class CSVWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVWriter.class);

    // todo has to process List of LineItems to become one big String
    // Use OpenCSV?

    public static void createCSVString(List<LineItem> data) {

        // get each field and convert to normal string, comma separated
        // separate rows

        String csv = data.stream()
                .map(LineItem::toString)
                .collect(Collectors.joining(","));

        LOGGER.info("csv1: {}", csv);

        String csv2 = data.stream()
                .map(lineItem -> lineItem.toString())
                .reduce("", String::concat);

        LOGGER.info("csv2: {}", csv2);
        // needs "forEach" or LineItem::getField()

        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("bank-statements.csv"), "UTF-8"));

            bw.write(csv);
            bw.flush();
            bw.close();

        } catch (FileNotFoundException f) {
            LOGGER.info("Could find CSV file");
            f.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("Could not write lineItems to CSV file");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeToCSVFile() {

    }
}


