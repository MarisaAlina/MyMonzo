package application;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.io.*;

// Reference: https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/

public class CSVParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVParser.class);

    String PATH = "/Users/Marisa/Desktop/Transactions2018.csv";
    String line = "";
    String splitByComma = ",";

    public void parseCSV() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH))) {
            LOGGER.info("Start parsing file... {}", PATH);

            // converts bytes into chars efficiently via above wrapping
            // returns a String of characters = one line of the file
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineItem = line.split(splitByComma);
                for (String word : lineItem) {
                    LOGGER.info("CSV split Test: {}", word);
                }
               //  LOGGER.info("Test print second word", lineItem[1]);
            }

            LOGGER.info("Finished parsing.");

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
