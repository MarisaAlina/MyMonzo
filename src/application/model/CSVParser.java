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

//        String string = "09 May 18";
//        SimpleDateFormat format = new SimpleDateFormat("dd MMM yy", Locale.UK);
//        Date date = format.parse(string);
//        System.out.println(date);

//        // 09 May 18
//        String dateFormat_1 = "[0-9]{1,2}\\s[a-zA-Z]{3}\\s[0-9]{2}";
//        // 09/05/18 17:56
//        String dateFormat_2 = "([0-9]{2})\\/[0-9]{2}\\/([0-9]{2})(((\\s[0-9]{2}:[0-9]{2}))?)";
//
//        String[] partsFormat_1 = description.split(dateFormat_1);
//        String frontInFormat_1 = partsFormat_1[0];
//
//        String[] partsFormat_2 = description.split(dateFormat_2);
//        String frontInFormat_2 = partsFormat_2[0];
//
//        String descriptionAfterDate = "";
//
//        String[] patterns = {"dd MMM yy", "yy/MM/dd HH:mm"};
//        String splitOnDate = "";
//
//        for (String pattern : patterns) {
//            try {
//                SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.UK);
//                Date date = formatter.parse(description);
//                if (date.equals(frontInFormat_1)) {
//                    descriptionAfterDate = partsFormat_1[1];
//                } else if (date.equals(frontInFormat_2)) {
//                    descriptionAfterDate = partsFormat_2[1];
//                }
////                splitOnDate = formatter.format(description);
//                break;
//            } catch (ParseException e) {
//                LOGGER.info("Could not parse date for {} ", description);
//            }
//        }

        String descriptionAfterDate = description.substring(description.lastIndexOf("8")+1);
        return descriptionAfterDate.trim();
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
