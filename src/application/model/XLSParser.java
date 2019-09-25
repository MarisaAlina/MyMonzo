package application.model;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class XLSParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(XLSParser.class);

    private List<LineItem> lineItemsFromXLS;

    public List<LineItem> parseXLS(String path) {

        try {
            FileInputStream excelFile =  new FileInputStream(new File(path));
            Workbook xssfWorkbook = new XSSFWorkbook(excelFile);
            Sheet dataSheet = xssfWorkbook.getSheetAt(0);

            LOGGER.info("Parsing xlsxFile at: {} ", path);

            for (int rowIndex = 0; rowIndex <= dataSheet.getLastRowNum(); rowIndex++) {

                Row row = dataSheet.getRow(rowIndex);

                if (row != null) {
                    Cell cell = row.getCell(colIndex);

                    if (cell != null) {
                        // Found column and there is value in the cell.
                       String cellValueMaybeNull = cell.getStringCellValue();
                        // Do something with the cellValueMaybeNull here ...
                    }
                }
            }

            for (Row row :dataSheet) {
                String currentCell = "";
                double num = 0.0;
                LineItem lineItem = new LineItem();

                for (Cell cell : row) {
                    CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                    LOGGER.info("Cell Ref: {}", cellRef.formatAsString());

                    switch (cell.getCellType()) {
                        case STRING:
                            LOGGER.info(cell.getRichStringCellValue().getString());
                            currentCell = cell.getRichStringCellValue().getString();
                            break;
                        case NUMERIC:
                            LOGGER.info(String.valueOf(cell.getNumericCellValue()));
                            num = cell.getNumericCellValue();
                            break;
                    }

                    lineItem = new LineItem(currentCell,
                            formatDescriptionWhenDate(currentCell),
                            parseAmount(currentCell),
                            Category.valueOf(currentCell)
                    );
                }

                lineItemsFromXLS.add(lineItem);
            }

//            Iterator<Row> rowIterator = dataSheet.iterator();
//            while (rowIterator.hasNext()) {
//                Row currentRow = rowIterator.next();
//                Iterator<Cell> cellIterator = currentRow.iterator();
//
//                while (cellIterator.hasNext()) {
//                    Cell currentCell = cellIterator.next();
//
//                }
//            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineItemsFromXLS;
    }


//    public List<LineItem> parseXLS(String path) {
//
//        try {
//            FileInputStream excelFile =  new FileInputStream(new File(path));
//            Workbook xssfWorkbook = new XSSFWorkbook(excelFile);
//            Sheet dataSheet = xssfWorkbook.getSheetAt(0);
//
//            LOGGER.info("Parsing xlsxFile at: {} ", path);
//
//            for (Row row :dataSheet) {
//                String currentCell = "";
//                double num = 0.0;
//                LineItem lineItem = new LineItem();
//
//                for (Cell cell : row) {
//                    CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
//                    LOGGER.info("Cell Ref: {}", cellRef.formatAsString());
//
//                    switch (cell.getCellType()) {
//                        case STRING:
//                            LOGGER.info(cell.getRichStringCellValue().getString());
//                            currentCell = cell.getRichStringCellValue().getString();
//                            break;
//                        case NUMERIC:
//                            LOGGER.info(String.valueOf(cell.getNumericCellValue()));
//                            num = cell.getNumericCellValue();
//                            break;
//                    }
//
//                    lineItem = new LineItem(currentCell,
//                            formatDescriptionWhenDate(currentCell),
//                            parseAmount(currentCell),
//                            Category.valueOf(currentCell)
//                            );
//                }
//
//                lineItemsFromXLS.add(lineItem);
//            }
//
////            Iterator<Row> rowIterator = dataSheet.iterator();
////            while (rowIterator.hasNext()) {
////                Row currentRow = rowIterator.next();
////                Iterator<Cell> cellIterator = currentRow.iterator();
////
////                while (cellIterator.hasNext()) {
////                    Cell currentCell = cellIterator.next();
////
////                }
////            }
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return lineItemsFromXLS;
//    }

    private String formatDescriptionWhenDate(String description) {
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
