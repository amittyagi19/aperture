package com.gspann.aperture.pageobjects.login;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.gspann.aperture.pageobjects.PageObject;


public class AppLoginPage {

	private PageObject pageObject;
	Logger logger = LogManager.getLogger(AppLoginPage.class.getName());

	public AppLoginPage(PageObject pageObject) {
		this.pageObject = pageObject;
		PageFactory.initElements(this.pageObject.getWebdriver(), this);
	}

	@FindBy(xpath = "")
	public WebElement loginBtn;

	@FindBy(xpath = "")
	public WebElement usernameEle;

	@FindBy(xpath = "")
	public WebElement typePasswordBtn;

	@FindBy(xpath = "")
	public WebElement passwordEle;

	@FindBy(xpath = "")
	public WebElement selectProfileEle;
	
	@FindBy(xpath = "")
	private WebElement settingIcon;
	
	@FindBy(xpath = "")
	private WebElement logoutBtn;

	public void login(String username, String password) throws Exception {
		logout();
		pageObject.WaitUntilWebElementIsVisible(loginBtn);
		pageObject.waitAndClickElement(loginBtn);
		//loginBtn.click();
		usernameEle.sendKeys(username);
		usernameEle.submit();
		pageObject.waitAndClickElement(typePasswordBtn);
		pageObject.sendKeysToWebElement(passwordEle, password);
		pageObject.pauseExecutionFor(3000);
		passwordEle.submit();
		pageObject.waitForPageLoad();
		pageObject.pauseExecutionFor(10000);
		logger.info("Login to end user application is successful");
		//pageObject.waitAndClickElement(selectProfileEle);
		//pageObject.isElementClickable(searchBtn);
	}
	public void logout() throws Exception {
		if(pageObject.isElementVisible(settingIcon, 10)) {
		pageObject.waitAndClickElement(settingIcon);
		pageObject.pauseExecutionFor(2000);
		pageObject.waitAndClickElement(logoutBtn);
		pageObject.pauseExecutionFor(5000);
		logger.info("Logout to end user application is successful");
		}
	}
	
}
