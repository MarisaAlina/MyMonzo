package application;

import application.model.LineItem;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.io.*;

// Reference: https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/

public class CSVParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVParser.class);

    String PATH = "/Users/Marisa/Desktop/Transactions2018.csv";
    String line = "";
    String splitByComma = ",";

    private LineItem lineItem;

    public LineItem parseCSV() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH))) {
            LOGGER.info("Start parsing file at PATH: {}", PATH);
            String[] lineItemPerRow = new String[0];

            // converts bytes from CSV into chars
            // then returns arrays of Lineitems; word = one cell per row
            // new String[] for each row
            while ((line = bufferedReader.readLine()) != null) {
                lineItemPerRow = line.split(splitByComma);

               /* for (String word : lineItems) {
                    LOGGER.info("CSV splitter test - word: {}", word);
                }

                for (int i = 0; i < lineItemsPerRow.length; i++) {
                    LOGGER.info("word on position {}: {} ", i, lineItemsPerRow[i]);
                    System.out.println(lineItemsPerRow.toString());
                } */


                /*lineItem = returnLineItemsFromFile(lineItemsPerRow);*/

                LOGGER.info("Finished parsing one row.");

                String date = lineItemPerRow[0];
                String description = lineItemPerRow[1];
                String transactionType = lineItemPerRow[2];
                String moneyOut = lineItemPerRow[4];

                double amount = 0.0;

                try {
                    amount = Double.parseDouble(moneyOut);
                } catch (NumberFormatException nfe) {
                    if (moneyOut.toCharArray().length == 1) {
                        LOGGER.info("Cell is char, exception: {}", nfe);
                    } else {
                        LOGGER.info("First round exception: {} ", nfe);
                    }
                }

                lineItem = new LineItem(date, description, transactionType, amount);
                LOGGER.info("Parsed lineItemObject: {}", lineItem);
                LOGGER.info("Value: {}", lineItem.getValue());
                LOGGER.info("Ref: {}", lineItem.getDescription());
            }


        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return lineItem;
    }


    public LineItem createLineItemFromParsedFile(String[] lineItemPerRow) {
        String date = lineItemPerRow[0];
        String description = lineItemPerRow[1];
        String transactionType = lineItemPerRow[2];
        String moneyOut = lineItemPerRow[3];
        double amount = Double.parseDouble(moneyOut);

        lineItem = new LineItem(date, description, transactionType, amount);
        return lineItem;
    }

    /* private double validateAmount(String cell) {
        try {
            double amount = Double.parseDouble(cell);
        } catch (NumberFormatException nfe) {
            if (cell.toCharArray().length == 1) {
                LOGGER.info("Cell is char, exception: {}", nfe);
            } else {
                LOGGER.info("Other exception: ", nfe);
            }
        }
    }*/

}
