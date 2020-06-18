package com.gspann.aperture.steps.login;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gspann.aperture.base.BaseConfiguration;
import com.gspann.aperture.pageobjects.login.AppLoginPage;
import com.gspann.aperture.steps.ParentSteps;
import com.gspann.aperture.utils.PropertyFile;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginPageSteps extends BaseConfiguration {

	private Scenario scenario;
	Logger logger = LogManager.getLogger(LoginPageSteps.class);
	protected AppLoginPage applLoginPage;

	public LoginPageSteps(AppLoginPage applLoginPage) {
		super();
		logger.debug("in the constructor of: " + this.getClass().getName());
		this.applLoginPage = applLoginPage;
	}
	
	@When("User login to admin site as {string} and {string}")
	public void User_login_to_admin_site(String userName,String password) throws Throwable {
		userName = propertyFile.getProperty(userName);
		password = propertyFile.getProperty(password);
		driver.getURL(propertyFile.getProperty("sephora.zuno.admin.url"));
		applLoginPage.login(userName,password);
	}
	@And("User logout from admin site$")
	public void User_logout__from_admin_site() throws Throwable {
		applLoginPage.logout();
	}
	@Given("User login to application as {string} and {string}")
	public void user_login_to_application_as(String userName, String password) throws Exception {
		userName = propertyFile.getProperty(userName);
		password = propertyFile.getProperty(password);
		driver.getURL(propertyFile.getProperty("sephora.end.user.url"));
		applLoginPage.login(userName,password);
	}
	@And("User logout from application$")
	public void User_logout_from_application() throws Throwable {
		applLoginPage.logout();
	}

	@After
	public void after() {
//		TearDown td = new TearDown(setupBrowser.driver);
//		td.quitDriver(scenario);
	}
}
