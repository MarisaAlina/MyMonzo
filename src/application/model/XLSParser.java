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
import java.util.Iterator;
import java.util.List;

public class XLSParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(XLSParser.class);

    private List<LineItem> lineItemsFromCSV;
    private List<LineItem> categorizedLineItems;

    public List<LineItem> parseXLS(String path) {

        try {
            FileInputStream excelFile =  new FileInputStream(new File(path));
            Workbook xssfWorkbook = new XSSFWorkbook(excelFile);
            Sheet dataSheet = xssfWorkbook.getSheetAt(0);

            LOGGER.info("Parsing xlsxFile at: {} ", path);

            for (Row row :dataSheet) {
                for (Cell cell : row) {
                    CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                    LOGGER.info("Cell Ref: {}", cellRef.formatAsString());

                    switch (cell.getCellType()) {
                        case CellType.STRING:
                            LOGGER.info(cell.getRichStringCellValue().getString());
                            break;
                    }

                }
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
    }

}
