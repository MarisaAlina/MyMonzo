package application.model;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
                Category notYetAssigned = Category.UNDEFINED;

                double amount = parseAmount(moneyOut);
                String trimmedDescription = trimDescriptionFieldOfDate(description);

                LineItem lineItem = new LineItem(date, trimmedDescription, amount, notYetAssigned);
                LOGGER.info("Parsed lineItemObject: {}", lineItem.toString());

                lineItemsFromCSV.add(lineItem);
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        LOGGER.info("======================= END PARSING CSV=================");
        return lineItemsFromCSV;
    }

    // TODO: rewrite classifier, currently doesn't work as expected
    public List<LineItem> processLineItems(List<LineItem> lineItemsFromCSV) {

        LOGGER.info("\n\n======================= CATEGORY AUTO-ASSIGNMENT=================\n");
        categorizedLineItems = new ArrayList<>();

        for (LineItem lineItem : lineItemsFromCSV) {
            lineItem.classifier(lineItem);
            LOGGER.info("Processed category for lineItem: {} - {}", lineItem.getDescription(), lineItem.getCategory());
            categorizedLineItems.add(lineItem);
        }

        LOGGER.info("\n\n======================= END OF PROCESSING LINEITEMS =================\n");
        return categorizedLineItems;
    }

    private String trimDescriptionFieldOfDate(String description) {

        String[] patterns = {"dd MMM yy", "dd/MM/yy HH:mm"};

        for (String pattern : patterns) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.UK);
                Date date = formatter.parse(description.substring(0, pattern.length()));
                LOGGER.info("Parsed date: {}", date);
                return description.substring(pattern.length()).trim();
            } catch (ParseException e) {
                LOGGER.info("Could not parse date for {}", description);
            }
        }
        return description.trim();
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
