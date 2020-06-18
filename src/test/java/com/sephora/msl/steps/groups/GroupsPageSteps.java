package com.sephora.msl.steps.groups;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sephora.msl.base.BaseConfiguration;
import com.sephora.msl.pageobjects.groups.GroupsPage;
import com.sephora.msl.pageobjects.login.AppLoginPage;
import com.sephora.msl.steps.ParentSteps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GroupsPageSteps extends BaseConfiguration {

	private Scenario scenario;
	private GroupsPage groupsPage;
	Logger logger = LogManager.getLogger(GroupsPageSteps.class);
	
	public GroupsPageSteps(GroupsPage groupsPage) {
		super();
		logger.debug("in the constructor of: " + this.getClass().getName());
		this.groupsPage = groupsPage;
	}

	@Before
	public void before(Scenario scenario) {
		this.scenario = scenario;
	}

	@When("^User click on users button$")
	public void user_click_on_users_button() throws Throwable {
		groupsPage.clickUsersBtn();
		Thread.sleep(3000);
	}

	@When("^User click on groups button$")
	public void user_click_on_groups_button() throws Throwable {
		groupsPage.clickUsersBtn();
		Thread.sleep(3000);
	}

}
