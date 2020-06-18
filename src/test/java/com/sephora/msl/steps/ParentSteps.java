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

	@Given("This is my first feature")
	public void this_is_my_first_feature() {
		System.out.println("This is my first feature");
	}
	
}
