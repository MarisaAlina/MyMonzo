package application.model;

import com.j256.ormlite.logger.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class XLSWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(XLSWriter.class);
    private static String[] columns = {"Date", "Description", "Transaction Type", "Money In", "Money Out", "Balance", "Category"};

    public static void exportToXLS(List<LineItem> data, File file) {

        Workbook xssfWorkbook = new XSSFWorkbook();
        CreationHelper createHelper = xssfWorkbook.getCreationHelper();
        Sheet sheet = xssfWorkbook.createSheet("Budget");

        // styling header
        Font headerFont = xssfWorkbook.createFont();
        headerFont.setBold(true);
        CellStyle cellStyle = xssfWorkbook.createCellStyle();
        cellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(columns[i]);
            headerCell.setCellStyle(cellStyle);
        }

        LOGGER.info("Created header row.");

        int rowNum = 1;
        String placeholder = "";

        for (LineItem lineItem : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(lineItem.getDate());
            row.createCell(1).setCellValue(lineItem.getDescription());
            row.createCell(2).setCellValue(placeholder);
            row.createCell(3).setCellValue(placeholder);
            row.createCell(4).setCellValue(lineItem.getAmount());
            row.createCell(5).setCellValue(placeholder);
            row.createCell(6).setCellValue(lineItem.getCategory().getCategoryByName());
        }

        LOGGER.info(" {} Lineitems written to xlsx", data.size());

        for(int i=0; i<columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        try (OutputStream fileOut = new FileOutputStream(file.getPath())) {
            xssfWorkbook.write(fileOut);
            xssfWorkbook.close();
        } catch (IOException e) {
            LOGGER.error("Could not write to file");
            e.printStackTrace();
        }
    }

}
