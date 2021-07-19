package com.automation.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import com.automation.Exceptions.FileNotFoundException;
import com.automation.Exceptions.InvalidValueException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils { // Do not change the class name

    public static void main(String[] s) {
        String fileName = "data/customer_registration.xlsx";
        Object[][] abc = ExcelUtils.readExcelData(fileName, "customer_valid");
        abc.hashCode();
    }

    public static Object[][] readExcelData(String fileN, String sheetN) {

        File file = new File(fileN);
        Workbook workbook = null;
        try {
            InputStream fileInputStream = ClassLoader.getSystemResourceAsStream(fileN);
            workbook = fileN.endsWith("xlsx") ? new XSSFWorkbook(fileInputStream) : new HSSFWorkbook(fileInputStream);
        } catch (Exception e) {
            throw new FileNotFoundException("File not found : " + fileN);
        }
        Sheet worksheet = workbook.getSheet(sheetN);
        if(worksheet==null)
            throw new InvalidValueException("Sheet not found in file : " + sheetN);

        Row Row = worksheet.getRow(0);
        int RowNum = worksheet.getPhysicalNumberOfRows();
        int ColNum = Row.getLastCellNum();

        Object testData[][] = new Object[RowNum - 1][1];
        ArrayList<String> headerList = new ArrayList<>();

    // to get and store all column headers
        Row headerRow = worksheet.getRow(0);
        for (int c = 0; c < ColNum; c++) // Loop work for colNum
        {
            Cell cell = headerRow.getCell(c);
            if (cell == null)
                headerList.add("");
            else {
                String value = cell.getStringCellValue();
                headerList.add(value);
            }
        }

        for (int r = 0; r < RowNum-1 ; r++) {
            HashMap<String, String> map = new HashMap<>();
            Row row = worksheet.getRow(r+1);

            for (int c = 0; c < ColNum; c++) // Loop work for colNum
            {

                Cell cell = row.getCell(c);
                if (cell == null)
                    map.put(headerList.get(c), "");
                else {
                    String value = cell.getStringCellValue();
                    map.put(headerList.get(c), value);
                }
            }
            Object[] objectRow = new Object[1];
            objectRow[0] = map;
            testData[r] = objectRow;
        }

        return testData;
    }

}
