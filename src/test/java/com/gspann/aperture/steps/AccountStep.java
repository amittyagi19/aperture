package com.gspann.aperture.steps;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import com.gspann.aperture.base.BaseConfiguration;
import com.gspann.aperture.driver.website.ApertureDriver;
import com.gspann.aperture.pageobjects.login.AppLoginPage;
import com.gspann.aperture.utils.CreateDataListFromExcel;
import com.gspann.aperture.utils.PropertyFile;

import gherkin.formatter.model.DataTableRow;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class AccountStep  {

	@Given("This is my first feature")
	public void this_is_my_first_feature() {
		System.out.println("This is my first feature");
		Assert.assertEquals(5, 6);
	}	
}
