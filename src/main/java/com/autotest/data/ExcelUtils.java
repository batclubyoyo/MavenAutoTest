package com.autotest.data;

/**
 * Created by 10202 on 2016/11/25.
 */
public class ExcelUtils {

    private static String excel_file = "src/com/auto/data/datasource.xml";

    public static Object[] getRowData(Object sheet, int row) throws Exception {
        Excel excel = new Excel(excel_file, sheet);
        Object[] target = excel.readRow(row);
        excel.clear();
        return target;
    }

    public static Object[] getColumnData(Object sheet, int column) throws Exception {
        Excel excel = new Excel(excel_file, sheet);
        Object[] target =excel.readColumn(column);
        excel.clear();
        return target;
    }

    public static String getCellData(Object sheet, int row, int column) throws Exception {
        Excel excel = new Excel(excel_file, sheet);
        String target = excel.readCell(row, column);
        excel.clear();
        return target;
    }

    public static void writeCellData(Object sheet, int row, int column, String content) throws Exception {
        Excel excel = new Excel(excel_file, sheet);
        excel.writeCell(content, row, column);
        excel.flushExcel();
        excel.clear();
    }

}
