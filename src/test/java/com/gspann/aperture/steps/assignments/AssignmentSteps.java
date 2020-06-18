package com.gspann.aperture.steps.assignments;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gspann.aperture.base.BaseConfiguration;
import com.gspann.aperture.pageobjects.Assignment.AssignmentPage;
import com.gspann.aperture.pageobjects.groups.GroupsPage;
import com.gspann.aperture.steps.groups.GroupsPageSteps;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AssignmentSteps extends BaseConfiguration {
	private Scenario scenario;
	private AssignmentPage assignmentPage;
	Logger logger = LogManager.getLogger(AssignmentSteps.class);
	
	public AssignmentSteps(AssignmentPage assignmentPage) {
		super();
		logger.debug("in the constructor of: " + this.getClass().getName());
		this.assignmentPage = assignmentPage;
	}

	
	@When("User click on drive button")
	public void user_click_on_drive_button() throws Exception {
		assignmentPage.clickElement();
	}
	
}
