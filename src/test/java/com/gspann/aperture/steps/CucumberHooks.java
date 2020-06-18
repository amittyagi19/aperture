package com.gspann.aperture.steps;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.gspann.aperture.base.BaseConfiguration;
import com.gspann.aperture.driver.website.ApertureDriver;
import com.gspann.aperture.pageobjects.login.AppLoginPage;
import com.gspann.aperture.utils.BaseUtil;
import com.gspann.aperture.utils.ExtentReport;
import com.gspann.aperture.utils.HtmlLogger;
import com.gspann.aperture.utils.PropertyFile;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class CucumberHooks extends BaseConfiguration{
Logger logger = LogManager.getLogger(CucumberHooks.class.getName());
private static ExtentReport extRep;
private static boolean isReporterrunning;
    
	protected String screenshotDestinationFolder;
	protected AppLoginPage applLoginPage;
	//private PropertyFile propFile;
	String fileName = "environments/defaultenv.properties";
	String extentReportLocation = System.getProperty("user.dir")+"//"+"ExtentReports"+"//"+"report.html";
	String screenshotFileName;
	boolean loginType = false;

	public CucumberHooks(AppLoginPage applLoginPage) {//,BaseUtil base) {
		//super();
		logger.debug("in the constructor of: " + this.getClass().getName());
		//propFile = new PropertyFile();
		//propFile.loadProps(fileName);
		this.applLoginPage = applLoginPage;
	}
	@Before
	public void beforeHook(Scenario scenario) throws Exception {
		List<String> sourceTags = (List<String>) scenario.getSourceTagNames();
		if(!isReporterrunning) {
			extRep = new ExtentReport(extentReportLocation);
			isReporterrunning = true;
		}
		logger.info("\n******************************************************************************************************************************"+
				"\n\t\t\t\t\tTEST SCENARIO NAME:    "+ scenario.getName()+
				"\n******************************************************************************************************************************");
			//driver.getURL(propertyFile.getProperty("sephora.end.user.url"));
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		driver.get("https://google.com");
		//applLoginPage.login(propertyFile.getProperty("Username"), propertyFile.getProperty("Password"));
		}
	@After
	public void afterHook(Scenario scenario) throws Exception {
		screenshotFileName = "//ExtentScreenShot//"+scenario.getName();
		System.out.println("inside hooks After");
		if(scenario.isFailed()==false) {
			logger.info("[TEST IS SUCCESS -------- Test case " + scenario.getName()	+ " has passed]");	
		}
		if(scenario.isFailed()) {
			logger.info("[TEST IS FAILED -------- Test case " + scenario.getName()	+ " has failed]");
			try {
				//String sScreenshotPath = MSLSephoraDriver.takeSnapShotAndRetPath(screenshotFileName,false);
				//logger.info("Snapshot Path :<a href='" + sScreenshotPath + "'>"+ sScreenshotPath+"</a>\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
		}
		//extRep.createTest(scenario, screenshotFileName);
		//extRep.writeToReport();
		//applLoginPage.logout();
	}

}