package com.sephora.msl.pageobjects.groups;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.sephora.msl.pageobjects.PageObject;

import io.cucumber.java.After;

public class GroupsPage {

	private PageObject pageObject;

	public GroupsPage(PageObject pageObject) {
		this.pageObject = pageObject;
		PageFactory.initElements(this.pageObject.getWebdriver(), this);
	}

	@FindBy(xpath = "")
	public WebElement groupElement;



	public void clickUsersBtn() throws Exception {
		pageObject.waitAndClickElement(groupElement);
	}
}
