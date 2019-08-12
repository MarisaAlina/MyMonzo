package application.model;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVParser.class);

    private String splitByComma = ",";

    private List<LineItem> lineItemsFromCSV;

    private List<LineItem> categorizedLineItems;


    public List<LineItem> parseCSV(String PATH) {

        lineItemsFromCSV = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH))) {
            LOGGER.info("Start parsing file at PATH: {}", PATH);

            String headerLine = bufferedReader.readLine();
            LOGGER.info("headerLine: {}", headerLine);

            String nextLine;

            while ((nextLine = bufferedReader.readLine()) != null) {

                String[] currentLine = nextLine.split(splitByComma);

                LOGGER.info("===============================");

                String date = currentLine[0];
                String description = currentLine[1];
                String transactionType = currentLine[2];
                String moneyOut = currentLine[4];
                Category notYetAssigned = Category.NOT_YET_ASSIGNED;

                double amount = parseAmount(moneyOut);
                String trimmedDescription = trimDescriptionFieldOfDate(description);

                LineItem lineItem = new LineItem(date, trimmedDescription, amount, notYetAssigned);
                LOGGER.info("Parsed lineItemObject: {}", lineItem.toString());

                lineItemsFromCSV.add(lineItem);
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return lineItemsFromCSV;
    }


    public List<LineItem> processLineItems(List<LineItem> lineItemsFromCSV) {

        categorizedLineItems = new ArrayList<>();

        for (LineItem lineItem : lineItemsFromCSV) {
            lineItem.classifier(lineItem);
            LOGGER.info("======================= END PARSING CSV=================");
            LOGGER.info("Processed lineItem with category: {}", lineItem.toString());
            categorizedLineItems.add(lineItem);
        }

        return categorizedLineItems;
    }

    private String trimDescriptionFieldOfDate(String description) {
        String trimmedDescription = description.substring(description.lastIndexOf("8")+1);
        return trimmedDescription.trim();
    }

    private Double parseAmount(String moneyOut) {

        double amount = 0.0;

        try {
            amount = Double.parseDouble(moneyOut);
        } catch (NumberFormatException nfe) {
            if (moneyOut.toCharArray().length == 1) {
                LOGGER.info("Cell is char, exception: {}", nfe);
            } else {
                LOGGER.info("First row is description. Exception: {} ", nfe);
            }
        }

        return amount;
    }



}
