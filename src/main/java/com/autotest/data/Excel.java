package com.autotest.data;

import com.autotest.utils.LogRecord;

import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCell;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by batcl on 11/22/2016.
 */
public class Excel {

    private Logger logger = LogRecord.getLogger();

    private String excel_file = "src/com/auto/data/testcase.xlsx";

//    private Workbook workbook = null;
//    private Sheet sheet = null;
    private XSSFWorkbook workbook = null;
    private XSSFSheet sheet = null;

    public Excel(Object sheet) throws Exception {
        clear();

        openExcel();
        openSheet(sheet);
    }

    public Excel(String excel_file, Object sheet) throws Exception {
        clear();

        this.excel_file = excel_file;
        openExcel();
        openSheet(sheet);
    }

    private void openExcel() {
        try {
            FileInputStream inputStream = new FileInputStream(this.excel_file);
            this.workbook = new XSSFWorkbook(inputStream);
//            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
        } catch (FileNotFoundException e) {
            logger.error("Not found the excel file!\n" + e.getMessage());
        } catch (IOException e) {
            logger.error("Open file stream error!\n" + e.getMessage());
        }
//        catch (InvalidFormatException e) {
//            logger.error("Not found the excel file!\n" + e.getMessage());
//        }
    }

    private void openSheet(Object sheet) {
        if (sheet.getClass().getSimpleName().equals("String")) {
            this.sheet = this.workbook.getSheet((String) sheet);
        } else {
            this.sheet = this.workbook.getSheetAt((int) sheet);
        }
    }

    public void clear() throws Exception {
        if (this.sheet != null) {
            this.sheet = null;
        }

        if (this.workbook != null) {
            this.workbook.close();
            this.workbook = null;
        }
    }

    // First row is title, so readCell plus 1
    public Object[] readColumn(int column) {
        int maxRow = getMaxRowNumber();
        Object[] object = new Object[maxRow-1];

        for (int i = 0; i < maxRow; i++) {
            object[i] = readCell(i+1, column);
        }

        return object;
    }

    public Object[] readRow(int row) {
        int maxColumn = getMaxColumnNumber();
        Object[] object = new Object[maxColumn];

        for (int i = 0; i < maxColumn; i++) {
            object[i] = readCell(row, i);
        }

        return object;
    }

    public String readCell(int row, int column) {
        String cellContent;
        XSSFCell cell = this.sheet.getRow(row).getCell(column);
        if (cell == null) {
            cellContent = "null";
            return cellContent;
        }

//        switch (cell.getCellType()) {
//            case Cell.CELL_TYPE_BLANK:
////                System.out.print("I'm blank: ");
//                cellContent = "blank";
//                break;
//            case Cell.CELL_TYPE_BOOLEAN:
////                System.out.print("I'm boolean: ");
//                cellContent = Boolean.toString(cell.getBooleanCellValue());
//                break;
//            case Cell.CELL_TYPE_NUMERIC: // normal number, Date are here
//                if (DateUtil.isCellDateFormatted(cell)) {
////                    System.out.print("I'm date: ");
//                    cellContent = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue()).toString();
//                } else {
////                    System.out.print("I'm numeric: ");
//                    cellContent = String.valueOf(cell.getNumericCellValue());
//                }
//                break;
//            case Cell.CELL_TYPE_FORMULA:
////                System.out.print("I'm formula: ");
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                cellContent = cell.getStringCellValue();
//                break;
//            case Cell.CELL_TYPE_ERROR:
////                System.out.print("I'm error: ");
//                cellContent = "error";
//                break;
//            default:
//                System.out.print("I'm String: ");
//                cellContent = cell.getStringCellValue().trim();
//                break;
//        }

        CellType cellType = cell.getCellTypeEnum();

        switch (cellType) {
            case BLANK:
//                System.out.print("I'm blank: ");
                cellContent = "blank";
                break;
            case BOOLEAN:
//                System.out.print("I'm boolean: ");
                cellContent = Boolean.toString(cell.getBooleanCellValue());
                break;
            case NUMERIC: // normal number, Date are here
                if (DateUtil.isCellDateFormatted(cell)) {
//                    System.out.print("I'm date: ");
                    cellContent = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue()).toString();
                } else {
//                    System.out.print("I'm numeric: ");
                    cellContent = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case FORMULA:
//                System.out.print("I'm formula: ");
//                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellType(CellType.STRING);
                cellContent = cell.getStringCellValue();
                break;
            case ERROR:
//                System.out.print("I'm error: ");
                cellContent = "error";
                break;
            default:
                System.out.print("I'm String: ");
                cellContent = cell.getStringCellValue().trim();
                break;
        }
        return cellContent;
    }

    public void writeCell(String content, int row, int column) {
        Row cellRow = this.sheet.getRow(row);
        if (cellRow == null) {
            cellRow = this.sheet.createRow(row);
        }

        Cell cell = cellRow.getCell(column);
        if (cell == null) {
            cell = cellRow.createCell(column);
        }

        cell.setCellValue(content);

        flushExcel();
    }

    public void flushExcel() {
        try {
            FileOutputStream outputStream= new FileOutputStream(this.excel_file);
            this.workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            logger.error("Not found the excel file!\n" + e.getMessage());
        } catch (IOException e) {
            logger.error("Write to excel file error!\n" + e.getMessage());
        }
    }


    private int getMaxColumnNumber() {
        int rows = getMaxRowNumber();
        int maxCol = 0;
        for (int i = 0; i < rows; i++) {
            Row row = this.sheet.getRow(i);
            if (row != null && row.getFirstCellNum() > -1) {
                if (row.getLastCellNum() > maxCol) {
                    maxCol = this.sheet.getRow(i).getLastCellNum();
                }
            } else {
                continue;
            }
        }

        System.out.println("max column: " + maxCol);
        return maxCol;
    }

    // Row returns index, so need to plus 1
    // And row's value always right, no matter there is blank cell
    private int getMaxRowNumber() {
        System.out.println("max row----real: " + (this.sheet.getPhysicalNumberOfRows()));
        System.out.println("max row----cell: " + (1 + this.sheet.getLastRowNum()));
        return 1 + this.sheet.getLastRowNum();

    }






}
