package com.sephora.msl.steps;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sephora.msl.base.BaseConfiguration;
import com.sephora.msl.driver.website.MSLSephoraDriver;
import com.sephora.msl.pageobjects.login.AppLoginPage;
import com.sephora.msl.utils.CreateDataListFromExcel;
import com.sephora.msl.utils.PropertyFile;

import gherkin.formatter.model.DataTableRow;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class ParentSteps  {

	protected boolean screenshotOnFailure;
	Logger logger = LogManager.getLogger(ParentSteps.class.getName());
    
	protected String screenshotDestinationFolder;
	protected AppLoginPage applLoginPage;
	protected Scenario scenario;
	protected CreateDataListFromExcel createDataList;
	protected List<List<String>> firstExcelRows;
	protected List<List<String>> secondExcelRows;

	public ParentSteps(AppLoginPage applLoginPage) {
		logger.debug("in the constructor of: " + this.getClass().getName());
		this.applLoginPage = applLoginPage;
	}
	@Given("Create list of data for first excel {string}")
	public void create_list_of_data_for_first_excel(String filePath) {
		createDataList=new CreateDataListFromExcel();
		firstExcelRows = createDataList.transform(filePath);    
	}
	@Given("Create list of data for second excel {string}")
	public void create_list_of_data_for_second_excel(String filePath) {
		createDataList=new CreateDataListFromExcel();
		secondExcelRows = createDataList.transform(filePath);   
	}
	
}
