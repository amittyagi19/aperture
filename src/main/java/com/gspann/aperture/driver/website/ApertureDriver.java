package com.gspann.aperture.driver.website;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import com.gspann.aperture.utils.CommonUtils;
import com.gspann.aperture.utils.NewExcelReader;
import com.gspann.aperture.utils.PropertyFile;

import io.cucumber.java.Scenario;
//import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;

public class ApertureDriver implements WebDriver {
	public static WebDriver driver;
	private PropertyFile propertyFile;
	String browser;
	String defaultProps = "environments/defaultenv.properties";

	//private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
	public ApertureDriver(PropertyFile property) {
		this.propertyFile = property;
		propertyFile.loadProps(defaultProps);
	}
	private static final Logger logger = LogManager.getLogger(ApertureDriver.class.getName());

	public synchronized void loadApplication() {
		browser = System.getProperty("browser");
		if (StringUtils.isEmpty(browser)) {
			browser = propertyFile.getProperty("browser").trim();
			System.out.println("Browser value is "+browser);
		}
		if(browser.equalsIgnoreCase("firefox")){
			//FirefoxProfile prof = new FirefoxProfile();
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("disable-popup-blocking");
			driver = new FirefoxDriver(options);
		} 
		if(browser.equalsIgnoreCase("safari"))	{ 
			//System.setProperty("webdriver.safari.noinstall", "false");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			//			capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
			// for clearing cache
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			capabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
			capabilities.setCapability(CapabilityType.ENABLE_PERSISTENT_HOVERING, true);
			capabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
			capabilities.setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS, true);
			capabilities.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
			driver = new SafariDriver(capabilities);
			pauseExecutionFor(2000);
			System.out.println("Safari browser launched");
		}
		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

		} else if (browser.equalsIgnoreCase("ie")) {
			InternetExplorerOptions options = new InternetExplorerOptions();
			System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
			options.setCapability("disable-popup-blocking", true); 
			options.setCapability("ignoreProtectedModeSettings", true);
			options.setCapability("ignoreZoomSetting", true);
			options.setCapability("requireWindowFocus", true);				 
			options.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
			options.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
			options.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, true);
			options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			//				options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			options.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
			options.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
			options.setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS, true);
			options.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
			options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR ,"accept");
			options.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			this.driver = new InternetExplorerDriver(options);
		}
//		WebDriverManager.firefoxdriver().setup();
//		WebDriverManager.edgedriver().setup();
//		WebDriverManager.operadriver().setup();
//		WebDriverManager.phantomjs().setup();
//		WebDriverManager.iedriver().setup();
//		WebDriverManager.chromiumdriver().setup();
		driver.manage().window().maximize();
	}
	public void pauseExecutionFor(long lTimeInMilliSeconds) {
		try {
			Thread.sleep(lTimeInMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static String takeSnapShotAndRetPath(String screenshotName,boolean isScreenshotFolderCreated) throws Exception {
		String FullSnapShotFilePath = "";
		try {
			logger.info("Taking Screenshot");
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			logger.info("Taken Screenshot");
			String sFilename = null;
//			if (isScreenshotFolderCreated == false) {
//				CommonUtils.createFolder("output//Screenshots");
//			}
			sFilename = "//output//Screenshots//" +"//" + screenshotName + ".png";
			FullSnapShotFilePath = System.getProperty("user.dir") + sFilename;
			FileUtils.copyFile(scrFile, new File(FullSnapShotFilePath));
		} catch (Exception e) {
			//logger.info("Exception in Screenshot "+e.getMessage());
		}

		return FullSnapShotFilePath;
	}
	public void createAndCleanFolder(String folderPath) {
		CommonUtils.createAndCleanFolder(folderPath);
	}
	public String getAdminURL() {
		return propertyFile.getProperty("sephora.zuno.admin.url");
	}
	public WebDriver getWebdriver() {
		return driver;
	}
	public void setWebdriver(WebDriver webdriver) {
		this.driver = webdriver;
	}
	public void getURL(String url) {
		driver.get(url);
		pauseExecutionFor(10000);

	}
	public void quitBrowser() {
		driver.quit();

	}

	@Override
	public String getCurrentUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElements(By by) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement findElement(By by) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPageSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void quit() {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<String> getWindowHandles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWindowHandle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TargetLocator switchTo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Navigation navigate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Options manage() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void get(String url) {
		// TODO Auto-generated method stub
		
	}

}
