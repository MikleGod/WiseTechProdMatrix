package control;

import model.Matrix;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ExcelController {

    public static void workWithExcel(File prodMatrixFile, File readyUpFile, int fileType) throws IOException {

        Iterator<Row> rowIterator = findRowIterator(fileType, prodMatrixFile);

        Matrix matrix = getMatrixFromFile(rowIterator);

        writeInFile(readyUpFile, matrix);

    }


    private static Iterator<Row> findRowIterator(int fileType, File prodMatrixFile) throws IOException {
        if (fileType == WindowController.XLSX_FILE) {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(prodMatrixFile.getAbsolutePath()));
            XSSFSheet sheet = workbook.getSheetAt(0);
            return sheet.iterator();
        } else if (fileType == WindowController.XLS_FILE) {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(prodMatrixFile.getAbsolutePath()));
            HSSFSheet sheet = workbook.getSheetAt(0);
            return sheet.iterator();
        } else {
            return null;
        }
    }

    private static Matrix getMatrixFromFile(Iterator<Row> rowIterator) {
        Matrix matrix = new Matrix();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.iterator();
            if (row.getRowNum() == 0) {
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getColumnIndex() != 0) {
                        matrix.assort.add(cell.getStringCellValue());
                    }
                }
            } else {
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getColumnIndex() == 0) {
                        matrix.matrix.add(new ArrayList<String>());
                        matrix.matrix.get(row.getRowNum() - 1).add(cell.getStringCellValue());
                    } else {
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_NUMERIC: {
                                if (cell.getNumericCellValue() == 1) {
                                    matrix.matrix.get(row.getRowNum() - 1).add(matrix.assort.get(cell.getColumnIndex() - 1));
                                }
                                break;
                            }
                            case Cell.CELL_TYPE_STRING: {
                                break;
                            }
                            case Cell.CELL_TYPE_BOOLEAN: {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return matrix;
    }

    private static void writeInFile(File readyFile, Matrix matrix) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        int prevRows = 0;
        for (int i = 0; i < matrix.matrix.size(); i++) {
            String market = matrix.matrix.get(i).get(0);
            for (int j = 1; j < matrix.matrix.get(i).size(); j++) {
                Row row = sheet.createRow(j + prevRows);
                Cell marketCell = row.createCell(0), productCell = row.createCell(1);
                marketCell.setCellValue(market);
                productCell.setCellValue(matrix.matrix.get(i).get(j));
            }
            prevRows += matrix.matrix.get(i).size() - 1;
        }
        workbook.write(new FileOutputStream(readyFile.getAbsolutePath()));
    }
}
