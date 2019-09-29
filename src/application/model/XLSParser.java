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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class XLSParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(XLSParser.class);

    private List<LineItem> lineItemsFromXLS;

    public List<LineItem> parseXLS(String path) {

        lineItemsFromXLS = new ArrayList<>();

        try {
            FileInputStream excelFile = new FileInputStream(new File(path));
            Workbook xssfWorkbook = new XSSFWorkbook(excelFile);
            Sheet dataSheet = xssfWorkbook.getSheetAt(0);

            LOGGER.info("Parsing xlsxFile at: {} ", path);

            for (Row row : dataSheet) {

                String date = "";
                String description = "";
                double moneyOut = 0.0;
                Category category = Category.UNDEFINED;

                if (row.getRowNum() != 0) {
                    LOGGER.info("Skipped header row");

                    for (Cell cell : row) {
                        CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                        LOGGER.info("Cell Ref: {}", cellRef.formatAsString());

                        if (cell.getColumnIndex() == 0) {
                            date = cell.getStringCellValue();
                        } else if (cell.getColumnIndex() == 1) {
                            description = formatDescriptionWhenDate(cell.getStringCellValue());
                        } else if (cell.getColumnIndex() == 4) {
                            moneyOut = cell.getNumericCellValue();
                        } else if (cell.getColumnIndex() == 6 && cell.getStringCellValue() != "") {
                            category = Category.forName(cell.getStringCellValue());
                        }
                    }

                    LineItem lineItem = new LineItem(date, description, moneyOut, category);
                    lineItemsFromXLS.add(lineItem);
                    LOGGER.info("Added item to list: {} ", lineItem.toString());
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineItemsFromXLS;
    }


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

}
