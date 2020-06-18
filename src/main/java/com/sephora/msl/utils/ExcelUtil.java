/**
 * 
 */
package com.sephora.msl.utils;

import static org.testng.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

/**
 * @author GSPANN
 * 
 */
public class ExcelUtil {

	static FileInputStream fileIn;
	static XSSFWorkbook workbook;

	public static void openFile(String path) {
		try {
			fileIn = new FileInputStream(new File(path));
			workbook = new XSSFWorkbook(fileIn);
		} catch (Exception e) {
			Assert.fail(e.toString());
		}

	}
	
	public static void createExcelFile(String filePath) {
		try {
			workbook = new XSSFWorkbook();
			File f = new File(filePath);
			if(f.exists()) {
				f.delete();
			}
			FileOutputStream fout = new FileOutputStream(filePath);
			workbook.write(fout);
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}
	
	public static void createSheet(String filePath, String sheetName) {
		openFile(filePath);
		workbook.createSheet(sheetName);
		closeFile(filePath);
	}

	public static void openWorkbookFile(String excelFileName) {
		try {
			FileInputStream is = new FileInputStream(excelFileName);
			workbook = new XSSFWorkbook(is);
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}

	public static void closeFile(String path) {
		FileOutputStream fileOut;
		try {
			fileOut = CommonUtils.getFileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}

	public static void updateCell(int sheetId, int colId, int rowId,
			String newVal) {
		XSSFSheet sheet = workbook.getSheetAt(sheetId);
		sheet.getRow(rowId).getCell(colId).setCellValue(newVal);
	}
	
	
	public static void writeIntoCell(String sheetName, int colId, int rowId,
			String newVal) {
		XSSFSheet sheet = workbook.getSheet(sheetName);
		XSSFRow sheetRow = sheet.getRow(rowId);
		if(sheetRow == null) {
			sheetRow = sheet.createRow(rowId);
		}
		XSSFCell cell = sheetRow.getCell(colId);
		if(cell == null) {
			cell = sheetRow.createCell(colId);
		}
		cell.setCellValue(newVal);
	}
	
	public static void writeIntoCell(int sheetId, int colId, int rowId,
			String newVal) {
		XSSFSheet sheet = workbook.getSheetAt(sheetId);
		XSSFRow sheetRow = sheet.getRow(rowId);
		if(sheetRow == null) {
			sheetRow = sheet.createRow(rowId);
		}
		XSSFCell cell = sheetRow.getCell(colId);
		if(cell == null) {
			cell = sheetRow.createCell(colId);
		}
		cell.setCellValue(newVal);
//		XSSFRow row = sheet.createRow(rowId);
//		XSSFCell cell = row.getCell(colId, org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
////		XSSFCell cell = sheet.getRow(rowId).getCell(colId);
//		if(cell == null) {
//			System.out.println(rowId+"$$"+colId);
//			cell =  row.createCell(colId);
//			cell.setCellValue(newVal);
////			cell.setCellValue(newVal);
//		}else {
//			System.out.println(rowId+"**"+colId);
//			cell = sheet.getRow(rowId).getCell(colId);
//			cell.setCellValue(newVal);
//		}
	}
	
	public static void updateCell(String sheetName, int colId, int rowId,
			String newVal) {
		XSSFSheet sheet = workbook.getSheet(sheetName);
		sheet.getRow(rowId).getCell(colId).setCellValue(newVal);
	}
	public static void updatExcelCell(String path, String sheetName, int colId,
			int rowId, String newVal) {
		openFile(path);
		updateCell(sheetName, colId, rowId, newVal);
		closeFile(path);
	}

	public static void updatExcelCell(String path, int sheetId, int colId,
			int rowId, String newVal) {
		openFile(path);
		updateCell(sheetId, colId, rowId, newVal);
		closeFile(path);
	}
	
	public static void writeIntoExcelCell(String path, int sheetId, int colId,
			int rowId, String newVal) {
		openFile(path);
		writeIntoCell(sheetId, colId, rowId, newVal);
		closeFile(path);
	}
	
	public static void writeIntoExcelCell(String path, String sheetName, int colId,
			int rowId, String newVal) {
		openFile(path);
		writeIntoCell(sheetName, colId, rowId, newVal);
		closeFile(path);
	}

	public static void updatExcelCol(String path, int sheetId, int colId,
			String newVal) {
		openFile(path);
		XSSFSheet sheet = workbook.getSheetAt(sheetId);
		Iterator<Row> rowIter = sheet.rowIterator();
		rowIter.next();
		while (rowIter.hasNext()) {
			Row row = (Row) rowIter.next();
			Cell cell = row.getCell(colId);
			cell.setCellValue(newVal);
		}
		closeFile(path);
	}

	/**
	 * Update values in MS Excel file in defined sheets/columns
	 * 
	 * @param sourceFile
	 *            source file name
	 * @param targetFile
	 *            target file name
	 * @param sheetColumnPairs
	 *            sheet name/column name pairs to be updated to the new value
	 * @param newValue
	 *            new value
	 * @return void
	 */
	public static void updateExcelValues(String sourceFile, String targetFile,
			String[][] sheetColumnPairs, String newValue) {
		updateExcelValues(sourceFile, targetFile, sheetColumnPairs, null,
				newValue);
	}

	public static void updateExcelValues(String sourceFile, String targetFile,
			String[][] sheetColumnPairs, String oldValue, String newValue) {
		openWorkbookFile(sourceFile);

		try {
			for (String[] pair : sheetColumnPairs) {

				String sheetName = pair[0];
				String columnName = pair[1];

				XSSFSheet sheet = workbook.getSheet(sheetName);

				Row targetHeader = sheet.getRow(0);
				int columnIndex = 0;
				Iterator<Cell> targetCellIter = targetHeader.cellIterator();
				while (targetCellIter.hasNext()) {
					Cell headerCell = targetCellIter.next();
					if (headerCell.getStringCellValue().equalsIgnoreCase(
							columnName)) {
						columnIndex = headerCell.getColumnIndex();
					}
				}

				Iterator<Row> rowIter = sheet.rowIterator();
				rowIter.next();
				while (rowIter.hasNext()) {
					Row row = (Row) rowIter.next();
					Cell cell = row.getCell(columnIndex);
					if (cell != null)
						if (oldValue == null) {
							cell.setCellValue(newValue);
						} else {
							cell.setCellValue(cell.getStringCellValue()
									.replace(oldValue, newValue));
						}
				}
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
		closeFile(targetFile);
	}

	public static InputStream getExcelFullPathName(String className)
			throws IOException {
		String fileName;
		if (className.endsWith(".xlsx"))
			fileName = className;
		else
			fileName = className.replace('.', '/') + ".xlsx";

		InputStream is;
		try {
			is = CommonUtils.loadInputStream("/" + fileName, "./test/java/"
					+ fileName);
		} catch (FileNotFoundException e) {
			fileName = new File(fileName).getName();
			is = CommonUtils.loadInputStream("/" + fileName,
					"./test/resources/selenium/" + fileName);
		}
		return is;
	}

	public static String parseStringCellValue(Workbook workBook, Cell cell) {
		FormulaEvaluator evaluator = workBook.getCreationHelper()
				.createFormulaEvaluator();
		String cellValue = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				cellValue = "";
				break;
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getRichStringCellValue().getString();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					cellValue = cell.getDateCellValue().toString();
				} else {
					DecimalFormat df = new DecimalFormat("0.00");
					cellValue = df.format(
							new Double(cell.getNumericCellValue())).toString();
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = new Boolean(cell.getBooleanCellValue()).toString();
				break;
			case Cell.CELL_TYPE_FORMULA:
				cellValue = evaluator.evaluate(cell).formatAsString();
				cellValue = cellValue.replaceAll("\"", "");
				break;
			}
		}
		if (cellValue != null && cellValue.equals("$empty")) {
			cellValue = "";
		} else if (cellValue != null && cellValue.equals("null")) {
			cellValue = null;
		}
		return cellValue;
	}

	public static Object parseObjectCellValue(Workbook workBook, Cell cell) {
		FormulaEvaluator evaluator = workBook.getCreationHelper()
				.createFormulaEvaluator();
		Object cellValue = null;
		if (cell != null) {
			boolean formulaFlag = false;
			int cellType = cell.getCellType();
			if (cellType == Cell.CELL_TYPE_FORMULA) {
				cellType = evaluator.evaluate(cell).getCellType();
				formulaFlag = true;
			}
			switch (cellType) {
			case Cell.CELL_TYPE_STRING:
				if (formulaFlag) {
					cellValue = evaluator.evaluate(cell).formatAsString();
					cellValue = ((String) cellValue).replaceAll("\"", "");
				} else {
					cellValue = cell.getStringCellValue();
				}
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					cellValue = cell.getDateCellValue();
				} else {
					cellValue = formulaFlag ? evaluator.evaluate(cell)
							.getNumberValue() : cell.getNumericCellValue();
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = formulaFlag ? evaluator.evaluate(cell)
						.getBooleanValue() : cell.getBooleanCellValue();
				break;
			}
		}

		if (cellValue != null && cellValue instanceof String) {
			String tmpValue = (String) cellValue;

			if (tmpValue.equals("$empty")) {
				cellValue = "";
			} else if (tmpValue.equals("null")) {
				cellValue = null;
			}
		}
		return cellValue;
	}

	public static HashMap<String, String> readExcelColToMap(String className,
			String colName) throws Exception {
		XSSFWorkbook workBook = new XSSFWorkbook(
				getExcelFullPathName(className));
		XSSFSheet sheet = workBook.getSheetAt(0);

		HashMap<String, String> map = new HashMap<String, String>();
		Row headers = sheet.getRow(0);
		Iterator<Cell> cellIter = headers.cellIterator();
		int colIndex = 0;
		while (cellIter.hasNext()) {
			Cell cell = (Cell) cellIter.next();
			String cellValue = parseStringCellValue(workBook, cell);
			if (cellValue.equals(colName)) {
				break;
			}
			colIndex++;
		}

		Iterator<Row> rowIter = sheet.rowIterator();
		int rowIndex = 0;
		while (rowIter.hasNext()) {
			Row row = (Row) rowIter.next();
			if (rowIndex != 0) {
				Cell keyCell = row.getCell(0);
				Cell valCell = row.getCell(colIndex);
				String keyCellValue = parseStringCellValue(workBook, keyCell);
				String valCellValue = parseStringCellValue(workBook, valCell);
				map.put(keyCellValue, valCellValue);
			}
			rowIndex++;
		}
		return map;
	}
}
