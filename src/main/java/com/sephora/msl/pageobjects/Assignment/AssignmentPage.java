package com.sephora.msl.pageobjects.Assignment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.sephora.msl.pageobjects.PageObject;
import com.sephora.msl.pageobjects.login.AppLoginPage;

public class AssignmentPage {
	private PageObject pageObject;
	Logger logger = LogManager.getLogger(AssignmentPage.class.getName());

	public AssignmentPage(PageObject pageObject) {
		this.pageObject = pageObject;
		PageFactory.initElements(this.pageObject.getWebdriver(), this);
	}
	@FindBy(xpath = "")
	public WebElement element;

	
	public void clickElement() throws Exception {
		pageObject.waitAndClickElement(element);
		pageObject.pauseExecutionFor(3000);
	}
}
