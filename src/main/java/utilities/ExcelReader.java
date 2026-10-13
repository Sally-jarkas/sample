package utilities;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;


    public class ExcelReader {

        private static XSSFSheet ExcelWSheet;

        private static XSSFWorkbook ExcelWBook;

        private static XSSFCell Cell;

        private static XSSFRow Row;

        public FileInputStream fis = null;

        public static String path_TestData;

        public ExcelReader(String path) {

            path_TestData = path;
            try {
                fis = new FileInputStream(path);
                ExcelWBook = new XSSFWorkbook(fis);
                ExcelWSheet = ExcelWBook.getSheetAt(0);
                fis.close();
            } catch (Exception e) {

                e.printStackTrace();
            }

        }


        String getCellData(String sheetName, int colNum, int rowNum) {
            try {
                if (rowNum <= 0)
                    return "";

                int index = ExcelWBook.getSheetIndex(sheetName);

                if (index == -1)
                    return "";

                ExcelWSheet = ExcelWBook.getSheetAt(index);
                Row = ExcelWSheet.getRow(rowNum - 1);
                if (Row == null)
                    return "";

                Cell = Row.getCell(colNum);
                if (Cell == null)
                    return "";

                else
                    return Cell.getStringCellValue();
            } catch (Exception e) {

                e.printStackTrace();
                return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
            }
        }

        boolean isSheetExist(String sheetName) {
            int index = ExcelWBook.getSheetIndex(sheetName);
            if (index == -1) {
                index = ExcelWBook.getSheetIndex(sheetName.toUpperCase());
                return index != -1;
            } else
                return true;
        }

        protected int getRowCount(String sheetName) {
            int index = ExcelWBook.getSheetIndex(sheetName);
            if (index == -1)
                return 0;
            else {
                ExcelWSheet = ExcelWBook.getSheetAt(index);
                return ExcelWSheet.getLastRowNum() + 1;
            }
        }

        protected int getColumnCount(String sheetName) {
            if (!isSheetExist(sheetName))
                return -1;

            ExcelWSheet = ExcelWBook.getSheet(sheetName);
            Row = ExcelWSheet.getRow(0);

            if (Row == null)
                return -1;

            return Row.getLastCellNum();
        }
}
