package application;

import application.model.Category;
import application.model.LineItem;
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

                String[] lineItemPerRow = nextLine.split(splitByComma);

                LOGGER.info("===============================");

                String date = lineItemPerRow[0];
                String description = lineItemPerRow[1];
                String transactionType = lineItemPerRow[2];
                String moneyOut = lineItemPerRow[4];
                Category notYetAssigned = Category.NOT_YET_ASSIGNED;

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

                LineItem lineItem = new LineItem(date, description, transactionType, amount, notYetAssigned);
                LOGGER.info("Parsed lineItemObject: {}", lineItem.toString());

                lineItemsFromCSV.add(lineItem);
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return lineItemsFromCSV;
    }


    public List<LineItem> processLineItems(String PATH) {

        categorizedLineItems = new ArrayList<>();
        List<LineItem> lineItems = parseCSV(PATH);

        for (LineItem lineItem : lineItems) {
            lineItem.classifier(lineItem);
            LOGGER.info("Processed lineItem with category: {}", lineItem.toString());
            categorizedLineItems.add(lineItem);
        }

        return categorizedLineItems;
    }
}
