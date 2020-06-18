package com.gspann.aperture.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import cucumber.api.DataTable;
import cucumber.api.Transformer;
import cucumber.runtime.ParameterInfo;
import cucumber.runtime.table.TableConverter;
import cucumber.runtime.xstream.LocalizedXStreams;
import gherkin.formatter.model.Comment;
import gherkin.formatter.model.DataTableRow;

public class CreateDataListFromExcel {
	private static final Logger logger = LogManager.getLogger(CreateDataListFromExcel.class
			.getName());

	public List<List<String>>  transform(String filePath) {
		NewExcelReader reader = new NewExcelReader.ExcelReaderBuilder()
				.setFileLocation(filePath)
				.setSheet(0)
				.build();
		
		List<List<String>> excelData = getExcelData(reader);	
		//List<DataTableRow> dataTableRows = getDataTableRows(excelData);
		//DataTable table = getDataTable(dataTableRows);
		
		return excelData;
	}
	private List<List<String>> getExcelData(NewExcelReader reader) {
		List<List<String>> excelData = new LinkedList<>();
		
		try {
			excelData = reader.getSheetDataAt();
		} catch (InvalidFormatException | IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		return excelData;
	}
//	private List<DataTableRow> getDataTableRows(List<List<String>> excelData) {
//		List<DataTableRow> dataTableRows = new LinkedList<>();
//		int line = 1;
//		
//		for(List<String> list : excelData){
//			Comment commnet = new Comment("", line);
//			DataTableRow tableRow = new DataTableRow(Arrays.asList(commnet), list, line++);
//			dataTableRows.add(tableRow);
//		}
//		return dataTableRows;
//	}
//	private DataTable getDataTable(List<DataTableRow> dataTableRows) {
//		ParameterInfo parameterInfo = new ParameterInfo(null, null, null, null);
//	    TableConverter tableConverter = new TableConverter(new LocalizedXStreams(Thread.currentThread().getContextClassLoader()).get(Locale.getDefault()), parameterInfo);
//		
//		DataTable table = new DataTable(dataTableRows, tableConverter);
//		return table;
//	}

}
