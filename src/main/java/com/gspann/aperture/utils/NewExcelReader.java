package com.gspann.aperture.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NewExcelReader {
	private static final Logger logger = LogManager.getLogger(NewExcelReader.class
			.getName());


	private String fileName;
	private String sheetName;
	private int sheetIndex;
	private XSSFWorkbook ExcelWBook;

	private NewExcelReader(ExcelReaderBuilder excelReaderBuilder) {
		this.fileName = excelReaderBuilder.fileName;
		this.sheetIndex = excelReaderBuilder.sheetIndex;
		this.sheetName = excelReaderBuilder.sheetName;
	}

	public static class ExcelReaderBuilder {

		private String fileName;
		private String sheetName;
		private int sheetIndex;

		public ExcelReaderBuilder setFileLocation(String location) {
			this.fileName = location;
			return this;
		}

		public ExcelReaderBuilder setSheet(String sheetName) {
			this.sheetName = sheetName;
			return this;
		}

		public ExcelReaderBuilder setSheet(int index) {
			this.sheetIndex = index;
			return this;
		}

		public NewExcelReader build() {
			return new NewExcelReader(this);
		}

	}
	private XSSFWorkbook getWorkBook(String filePath) throws InvalidFormatException, IOException {
		return new XSSFWorkbook(new File(filePath));
	}

	private XSSFSheet getWorkBookSheet(String fileName, String sheetName) throws InvalidFormatException, IOException {
		this.ExcelWBook = getWorkBook(fileName);
		return this.ExcelWBook.getSheet(sheetName);
	}

	private XSSFSheet getWorkBookSheet(String fileName, int sheetIndex) throws InvalidFormatException, IOException {
		this.ExcelWBook = getWorkBook(fileName);
		return this.ExcelWBook.getSheetAt(sheetIndex);
	}

	public List<List<String>> getSheetData() throws IOException{
		XSSFSheet sheet;
		List<List<String>> outerList = new LinkedList<>();
		
		try {
			sheet = getWorkBookSheet(fileName, sheetName);
			outerList = getSheetData(sheet);
		} catch (InvalidFormatException e) {
			throw new RuntimeException(e.getMessage());
		}finally {
			this.ExcelWBook.close();
		}
		return outerList;
	}
	
	public List<List<String>> getSheetDataAt() throws InvalidFormatException, IOException {
		
		XSSFSheet sheet;
		List<List<String>> outerList = new LinkedList<>();
		
		try {
			sheet = getWorkBookSheet(fileName, sheetIndex);
			outerList = getSheetData(sheet);
		} catch (InvalidFormatException e) {
			throw new RuntimeException(e.getMessage());
		}finally {
			this.ExcelWBook.close();
		}
		return outerList;
	}

	private List<List<String>> getSheetData(XSSFSheet sheet) {
		List<List<String>> outerList = new LinkedList<>();
		prepareOutterList(sheet, outerList);
		return Collections.unmodifiableList(outerList);
	}

	private void prepareOutterList(XSSFSheet sheet, List<List<String>> outerList) {
		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
			List<String> innerList = new LinkedList<>();
			XSSFRow xssfRow = sheet.getRow(i);
			for (int j = xssfRow.getFirstCellNum(); j < xssfRow.getLastCellNum(); j++) {
				System.out.println("Value of j is "+j);
				prepareInnerList(innerList, xssfRow, j);
			}
			outerList.add(Collections.unmodifiableList(innerList));
		}
	}
	private void prepareInnerList(List<String> innerList, XSSFRow xssfRow, int j) {
		//switch (xssfRow.getCell(j).getCellTypeEnum()) { 
		DataFormatter objDefaultFormat = new DataFormatter();
		try {
			String CellData = objDefaultFormat.formatCellValue(xssfRow.getCell(j));
			innerList.add(CellData);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
		}

//		case BLANK:
//			innerList.add("");
//			break;
//
//		case STRING:
//			innerList.add(xssfRow.getCell(j).getStringCellValue());
//			break;
//
//		case NUMERIC:
//			innerList.add(xssfRow.getCell(j).getNumericCellValue() + "");
//			break;
//
//		case BOOLEAN:
//			innerList.add(xssfRow.getCell(j).getBooleanCellValue() + "");
//			break;
//			
//		case FORMULA:
//			innerList.add(xssfRow.getCell(j).getf + "");
//
//		default:
//			throw new IllegalArgumentException("Cannot read the column : " + j);
//		}
	}
}
