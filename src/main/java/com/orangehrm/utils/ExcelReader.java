package com.orangehrm.utils;

import com.orangehrm.constants.FrameworkConstants;
import com.orangehrm.exceptions.FrameworkException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Reads an .xlsx sheet into a list of row-maps (header -> cell value). Handy for
 * Scenario Outlines whose Examples are maintained by business / QA in Excel.
 */
public final class ExcelReader {

    private static final DataFormatter FORMATTER = new DataFormatter();

    private ExcelReader() { }

    public static List<Map<String, String>> readSheet(String fileName, String sheetName) {
        List<Map<String, String>> rows = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(FrameworkConstants.TEST_DATA_PATH + fileName);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new FrameworkException("Sheet not found: " + sheetName);
            }
            Row header = sheet.getRow(0);
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;
                Map<String, String> map = new LinkedHashMap<>();
                for (int c = 0; c < header.getLastCellNum(); c++) {
                    Cell key = header.getCell(c);
                    Cell val = row.getCell(c);
                    map.put(FORMATTER.formatCellValue(key), FORMATTER.formatCellValue(val));
                }
                rows.add(map);
            }
        } catch (IOException e) {
            throw new FrameworkException("Failed reading Excel: " + fileName, e);
        }
        return rows;
    }
}
