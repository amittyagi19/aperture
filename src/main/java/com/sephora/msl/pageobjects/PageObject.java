package com.sephora.msl.pageobjects;

import java.awt.AWTException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.FindElements;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.sephora.msl.driver.website.MSLSephoraDriver;

public class PageObject {

	private static WebDriver webdriver;
	Logger logger = LogManager.getLogger(PageObject.class);
//	public WebdriverWait webdriverWait;
	private WebDriverWait wait;
	private JavascriptExecutor jsExecutor;
	private int DEFAULT_TIMEOUT = 90;
	private int POLLING_TIME = 200;

	public PageObject(MSLSephoraDriver setupBrowser) {
		logger.info("PageObject initiated...");
		this.webdriver = setupBrowser.driver;
		//BaseSteps basestep = new BaseSteps(setupBrowser);
		if (this.webdriver == null) {
			logger.info("WebDriver is still null....");
		}
//		PageFactory.initElements(this.webdriver, this);
//        this.webdriverWait = new WebdriverWait(this.webdriver);
		this.wait = new WebDriverWait(this.webdriver, 5);
		jsExecutor = (JavascriptExecutor) this.webdriver;
	}

	private static String screenshotName;
	public static Actions action;

	public WebDriver getWebdriver() {
		return webdriver;
	}

	public void setWebdriver(WebDriver webdriver) {
		this.webdriver = webdriver;
	}

	/**********************************************************************************
	 ** CLICK METHODS
	 * 
	 * @throws IOException
	 **********************************************************************************/
	public void waitAndClickElement(WebElement element) throws InterruptedException, IOException {
		boolean clicked = false;
		int attempts = 0;
		while (!clicked && attempts < 10) {
			try {
				this.wait.until(ExpectedConditions.elementToBeClickable(element)).click();
				logger.debug("Successfully clicked on the WebElement: " + "<" + element.toString() + ">");
				clicked = true;
			} catch (Exception e) {
				logger.error("Unable to wait and click on WebElement, Exception: " + e.getMessage());
				Assert.fail(
						"Unable to wait and click on the WebElement, using locator: " + "<" + element.toString() + ">");
			}
			attempts++;
		}
	}

	public void waitAndClickElementsUsingByLocator(By by) throws InterruptedException {
		boolean clicked = false;
		int attempts = 0;
		while (!clicked && attempts < 10) {
			try {
				this.wait.until(ExpectedConditions.elementToBeClickable(by)).click();
				System.out
						.println("Successfully clicked on the element using by locator: " + "<" + by.toString() + ">");
				clicked = true;
			} catch (Exception e) {
				logger.error("Unable to wait and click on the element using the By locator, Exception: " + e.getMessage());
				Assert.fail("Unable to wait and click on the element using the By locator, element: " + "<"
						+ by.toString() + ">");
			}
			attempts++;
		}
	}

	public void clickOnTextFromDropdownList(WebElement list, String textToSearchFor) throws Exception {
		Wait<WebDriver> tempWait = new WebDriverWait(webdriver, 30);
		try {
			tempWait.until(ExpectedConditions.elementToBeClickable(list)).click();
			list.sendKeys(textToSearchFor);
			list.sendKeys(Keys.ENTER);
			logger.debug("Successfully sent the following keys: " + textToSearchFor + ", to the following WebElement: "
					+ "<" + list.toString() + ">");
		} catch (Exception e) {
			logger.error("Unable to send the following keys: " + textToSearchFor + ", to the following WebElement: " + "<"
					+ list.toString() + ">");
			Assert.fail("Unable to select the required text from the dropdown menu, Exception: " + e.getMessage());
		}
	}
	public void selectValueDropdownList(WebElement list, String textToSelect) throws Exception {
		Wait<WebDriver> tempWait = new WebDriverWait(webdriver, 30);
		try {
			tempWait.until(ExpectedConditions.elementToBeClickable(list));
			Select select = new Select(list);
			select.selectByVisibleText(textToSelect);
			logger.debug("Successfully sent the following keys: " + textToSelect + ", to the following WebElement: "
					+ "<" + list.toString() + ">");
		} catch (Exception e) {
			logger.error("Unable to send the following keys: " + textToSelect + ", to the following WebElement: " + "<"
					+ list.toString() + ">");
			Assert.fail("Unable to select the required text from the dropdown menu, Exception: " + e.getMessage());
		}
	}

	public void clickOnElementUsingCustomTimeout(WebElement locator, WebDriver webdriver, int timeout) {
		try {
			final WebDriverWait customWait = new WebDriverWait(webdriver, timeout);
			customWait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(locator)));
			locator.click();
			logger.debug("Successfully clicked on the WebElement, using locator: " + "<" + locator + ">"
					+ ", using a custom Timeout of: " + timeout);
		} catch (Exception e) {
			logger.error("Unable to click on the WebElement, using locator: " + "<" + locator + ">"
					+ ", using a custom Timeout of: " + timeout);
			Assert.fail("Unable to click on the WebElement, Exception: " + e.getMessage());
		}
	}

	/**********************************************************************************/
	/**********************************************************************************/

	/**********************************************************************************
	 ** ACTION METHODS
	 **********************************************************************************/

	public void actionMoveAndClick(WebElement element) throws Exception {
		action = new Actions(webdriver);
		try {
			this.wait.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
			action.moveToElement(element).click().build().perform();
			logger.debug("Successfully Action Moved and Clicked on the WebElement, using locator: " + "<"
					+ element.toString() + ">");
		} catch (StaleElementReferenceException elementUpdated) {
			WebElement elementToClick = element;
			Boolean elementPresent = wait.until(ExpectedConditions.elementToBeClickable(elementToClick)).isEnabled();
			if (elementPresent == true) {
				action.moveToElement(elementToClick).click().build().perform();
				logger.debug("(Stale Exception) - Successfully Action Moved and Clicked on the WebElement, using locator: "
						+ "<" + element.toString() + ">");
			}
		} catch (Exception e) {
			logger.error("Unable to Action Move and Click on the WebElement, using locator: " + "<" + element.toString()
					+ ">");
			Assert.fail("Unable to Action Move and Click on the WebElement, Exception: " + e.getMessage());
		}
	}

	public void actionMoveAndClickByLocator(By element) throws Exception {
		action = new Actions(webdriver);
		try {
			Boolean elementPresent = wait.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
			if (elementPresent == true) {
				WebElement elementToClick = webdriver.findElement(element);
				action.moveToElement(elementToClick).click().build().perform();
				logger.debug("Action moved and clicked on the following element, using By locator: " + "<"
						+ element.toString() + ">");
			}
		} catch (StaleElementReferenceException elementUpdated) {
			WebElement elementToClick = webdriver.findElement(element);
			action.moveToElement(elementToClick).click().build().perform();
			System.out
					.println("(Stale Exception) - Action moved and clicked on the following element, using By locator: "
							+ "<" + element.toString() + ">");
		} catch (Exception e) {
			logger.error("Unable to Action Move and Click on the WebElement using by locator: " + "<" + element.toString()
					+ ">");
			Assert.fail(
					"Unable to Action Move and Click on the WebElement using by locator, Exception: " + e.getMessage());
		}
	}

	/**********************************************************************************/
	/**********************************************************************************/

	/**********************************************************************************
	 ** SEND KEYS METHODS /
	 **********************************************************************************/
	public void sendKeysToWebElement(WebElement element, String textToSend) throws Exception {
		try {
			this.WaitUntilWebElementIsVisible(element);
			element.clear();
			element.sendKeys(textToSend);
			logger.debug("Successfully Sent the following keys: '" + textToSend + "' to element: " + "<"
					+ element.toString() + ">");
		} catch (Exception e) {
			logger.error("Unable to locate WebElement: " + "<" + element.toString() + "> and send the following keys: "
					+ textToSend);
			Assert.fail("Unable to send keys to WebElement, Exception: " + e.getMessage());
		}
	}
	public void sendKeysToWebElementOneByOne(WebElement element, String textToSend) throws Exception {
		try {
			this.WaitUntilWebElementIsVisible(element);
			element.clear();
			 for (int i = 0; i < textToSend.length(); i++){
			        char c = textToSend.charAt(i);
			        String s = new StringBuilder().append(c).toString();
			        element.sendKeys(s);
			    }  
			logger.debug("Successfully Sent the following keys one by one: '" + textToSend + "' to element: " + "<"
					+ element.toString() + ">");
		} catch (Exception e) {
			logger.error("Unable to locate WebElement: " + "<" + element.toString() + "> and send the following keys: "
					+ textToSend);
			Assert.fail("Unable to send keys to WebElement, Exception: " + e.getMessage());
		}
	}

	/**********************************************************************************/
	/**********************************************************************************/

	/**********************************************************************************
	 ** JS METHODS & JS SCROLL
	 **********************************************************************************/
	public void scrollToElementByWebElementLocator(WebElement element) {
		try {
			this.wait.until(ExpectedConditions.visibilityOf(element)).isEnabled();
			((JavascriptExecutor) webdriver).executeScript("arguments[0].scrollIntoView();", element);
			((JavascriptExecutor) webdriver).executeScript("window.scrollBy(0, -400)");
			logger.debug("Succesfully scrolled to the WebElement, using locator: " + "<" + element.toString() + ">");
		} catch (Exception e) {
			logger.error("Unable to scroll to the WebElement, using locator: " + "<" + element.toString() + ">");
			Assert.fail("Unable to scroll to the WebElement, Exception: " + e.getMessage());
		}
	}

	public void jsPageScroll(int numb1, int numb2) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) webdriver;
			js.executeScript("scroll(" + numb1 + "," + numb2 + ")");
			logger.debug("Succesfully scrolled to the correct position! using locators: " + numb1 + ", " + numb2);
		} catch (Exception e) {
			logger.error("Unable to scroll to element using locators: " + "<" + numb1 + "> " + " <" + numb2 + ">");
			Assert.fail("Unable to manually scroll to WebElement, Exception: " + e.getMessage());
		}
	}

	public void waitAndclickElementUsingJS(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) webdriver;
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			js.executeScript("arguments[0].click();", element);
			logger.info("Successfully JS clicked on the following WebElement: " + "<" + element.toString() + ">");
		} catch (StaleElementReferenceException elementUpdated) {
			WebElement staleElement = element;
			Boolean elementPresent = wait.until(ExpectedConditions.elementToBeClickable(staleElement)).isEnabled();
			if (elementPresent == true) {
				js.executeScript("arguments[0].click();", elementPresent);
				logger.debug("(Stale Exception) Successfully JS clicked on the following WebElement: " + "<"
						+ element.toString() + ">");
			}
		} catch (NoSuchElementException e) {
			logger.error("Unable to JS click on the following WebElement: " + "<" + element.toString() + ">");
			Assert.fail("Unable to JS click on the WebElement, Exception: " + e.getMessage());
		}
	}

	public void jsClick(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) webdriver;
		js.executeScript("arguments[0].click();", element);
	}

	/**********************************************************************************/
	/**********************************************************************************/

	/**********************************************************************************
	 ** WAIT METHODS
	 **********************************************************************************/
	public boolean WaitUntilWebElementIsVisible(WebElement element) {
		try {
			this.wait.until(ExpectedConditions.visibilityOf(element));
			logger.debug("WebElement is visible using locator: " + "<" + element.toString() + ">");
			return true;
		} catch (Exception e) {
			logger.error("WebElement is NOT visible, using locator: " + "<" + element.toString() + ">");
			Assert.fail("WebElement is NOT visible, Exception: " + e.getMessage());
			return false;
		}
	}
	public void waitForElementToBeVisible(By locator, int timeOut) {
		// scrollToElement(locator);
		try {
			WebDriverWait wait = new WebDriverWait(webdriver, timeOut);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			logger.info("Element is visible now");

		} catch (Exception e) {
			logger.info("Element is not visible after waiting for " + timeOut + " seconds");
		}
	}
	public void waitForElementPresent(By locator, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(webdriver, timeout);
			logger.info("waiting for locator " + locator);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			logger.info("Element found");
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	public boolean isElementVisible(WebElement element, int timeout) {
		boolean visible = false;
		try {
			WebDriverWait wait = new WebDriverWait(webdriver, timeout);
			wait.until(ExpectedConditions.visibilityOf(element));
			logger.info("Element is visible : " + "<" + element.toString() + ">");
			return true;
		} catch (Exception e) {
			logger.info("Element is visible : " + "<" + element.toString() + ">");
			return visible;
		}
	}
	public boolean isElementVisible(By locator, int timeout) {
		try {
			waitForElementToBeVisible(locator, timeout);
			return webdriver.findElement(locator).isDisplayed() ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean WaitUntilWebElementIsVisibleUsingByLocator(By element) {
		try {
			this.wait.until(ExpectedConditions.visibilityOfElementLocated(element));
			logger.debug("Element is visible using By locator: " + "<" + element.toString() + ">");
			return true;
		} catch (Exception e) {
			logger.error("WebElement is NOT visible, using By locator: " + "<" + element.toString() + ">");
			Assert.fail("WebElement is NOT visible, Exception: " + e.getMessage());
			return false;
		}
	}

	public boolean isElementClickable(WebElement element) {
		try {
			this.wait.until(ExpectedConditions.elementToBeClickable(element));
			logger.debug("WebElement is clickable using locator: " + "<" + element.toString() + ">");
			return true;
		} catch (Exception e) {
			logger.error("WebElement is NOT clickable using locator: " + "<" + element.toString() + ">");
			return false;
		}
	}

	public boolean waitUntilPreLoadElementDissapears(By element) {
		return this.wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
	}
	public boolean waitUntilPreLoadElementDissapears(WebElement element) {
		return this.wait.until(ExpectedConditions.invisibilityOf(element));
	}

	/**********************************************************************************/
	/**********************************************************************************/

	public String getCurrentURL() {
		try {
			String url = webdriver.getCurrentUrl();
			logger.debug("Found(Got) the following URL: " + url);
			return url;
		} catch (Exception e) {
			logger.error("Unable to locate (Get) the current URL, Exception: " + e.getMessage());
			return e.getMessage();
		}
	}

	public String waitForSpecificPage(String urlToWaitFor) {
		try {
			String url = webdriver.getCurrentUrl();
			this.wait.until(ExpectedConditions.urlMatches(urlToWaitFor));
			logger.debug("The current URL was: " + url + ", " + "navigated and waited for the following URL: "
					+ urlToWaitFor);
			return urlToWaitFor;
		} catch (Exception e) {
			logger.error("Exception! waiting for the URL: " + urlToWaitFor + ",  Exception: " + e.getMessage());
			return e.getMessage();
		}
	}
//	public boolean waitForPageLoad() {
//		log.debug("Waiting for Page to load");
//		boolean isLoaded = false;
//		long startTime = System.currentTimeMillis();
//		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
//		public Boolean apply(webdriver) {
//		return ((JavascriptExecutor) webdriver).executeScript("return document.readyState").equals("complete");
//		}
//		};
//		wait = new WebDriverWait(webdriver, DEFAULT_TIMEOUT, POLLING_TIME);
//		wait.until(pageLoadCondition);
//		isLoaded = true;
//		long endTime = System.currentTimeMillis();
//		long totalTime = endTime - startTime;
//		log.debug("Finished waiting for page to load after " + totalTime + " milliseconds.", true);
//		return isLoaded;
//		}
	public boolean waitForPageLoad() throws InterruptedException {
		pauseExecutionFor(1000);
		boolean isLoaded = false;
		try {
			logger.info("Waiting For Page load via JS");
			ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver webdriver) {
//					return ((JavascriptExecutor) webdriver.executeScript("return document.readyState")
//							.equals("complete");
					return jsExecutor.executeScript("return document.readyState")
							.equals("complete");
					
				}
			};
			WebDriverWait wait = new WebDriverWait(webdriver, DEFAULT_TIMEOUT);
			wait.until(pageLoadCondition);
			isLoaded = true;
		} catch (Exception e) {
			logger.error("Error Occured waiting for Page Load " + webdriver.getCurrentUrl());
		}
		logger.info("page load complete..");
		return isLoaded;
	}
	public void pauseExecutionFor(long lTimeInMilliSeconds) throws InterruptedException {
		logger.debug("Waiting for " + lTimeInMilliSeconds + "millseconds",true);
		Thread.sleep(lTimeInMilliSeconds);
	}
	public WebElement getWebElementFromXPathString(String xpath,Object argument) throws InterruptedException {
		isElementVisible(By.xpath(String.format(xpath, argument)),10);
		return webdriver.findElement(By.xpath(String.format(xpath, argument)));
	}
	public By getByTypeFromXPathString(String xpath,Object argument) throws InterruptedException {
		//isElementVisible(By.xpath(String.format(xpath, argument)),10);
		return By.xpath(String.format(xpath, argument));
	}
	public void switchToIFrameUsingFrameLocator(WebElement frameElement) {
		webdriver.switchTo().frame(frameElement);
	}
	public void switchToIFrameUsingFrameIndex(int index) {
		webdriver.switchTo().frame(index);
	}
	public void switchToIFrameNameOrId(String frameNameOrId) {
		webdriver.switchTo().frame(frameNameOrId);
	}
	public void switchOutFromAllIFrame() {
		webdriver.switchTo().defaultContent();
	}

	/**********************************************************************************/
	/**********************************************************************************/

	/**********************************************************************************
	 ** ALERT & POPUPS METHODS
	 * 
	 * @throws Exception
	 **********************************************************************************/
	public void closePopups(By locator) throws Exception {
		try {
			List<WebElement> elements = this.wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
			for (WebElement element : elements) {
				if (element.isDisplayed()) {
					element.click();
					this.wait.until(ExpectedConditions.invisibilityOfAllElements(elements));
					logger.debug("The popup has been closed Successfully!");
				}
			}
		} catch (Exception e) {
			logger.error("Exception! - could not close the popup!, Exception: " + e.toString());
			throw (e);
		}
	}

	public boolean checkPopupIsVisible() {
		try {
			@SuppressWarnings("unused")
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			logger.debug("A popup has been found!");
			return true;
		} catch (Exception e) {
			System.err.println("Error came while waiting for the alert popup to appear. " + e.getMessage());
		}
		return false;
	}

	public boolean isAlertPresent() {
		try {
			webdriver.switchTo().alert();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void closeAlertPopupBox() throws AWTException, InterruptedException {
		try {
			Alert alert = this.wait.until(ExpectedConditions.alertIsPresent());
			alert.accept();
		} catch (UnhandledAlertException f) {
			Alert alert = webdriver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			logger.error("Unable to close the popup");
			Assert.fail("Unable to close the popup, Exception: " + e.getMessage());
		}
	}

	/**********************************************************************************/
	/**********************************************************************************/

	/***
	 * EXTENT REPORT
	 ****************************************************************/
	public static String returnDateStamp(String fileExtension) {
		Date d = new Date();
		String date = d.toString().replace(":", "_").replace(" ", "_") + fileExtension;
		return date;
	}

	public static String returnScreenshotName() {
		return (System.getProperty("user.dir") + "/output/imgs/" + screenshotName).toString();
	}

	private static void copyFileUsingStream(File source, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;

		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;

			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}

		} finally {
			is.close();
			os.close();
		}
	}

	public static void copyLatestExtentReport() throws IOException {
		Date d = new Date();
		String date = d.toString().replace(":", "_").replace(" ", "_");
		File source = new File(System.getProperty("user.dir") + "/output/report.html");
		File dest = new File(System.getProperty("user.dir") + "/output/" + date.toString() + ".html");
		copyFileUsingStream(source, dest);
	}

}
