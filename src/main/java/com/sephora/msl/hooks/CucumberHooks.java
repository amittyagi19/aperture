package com.sephora.msl.hooks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.sephora.msl.driver.website.MSLSephoraDriver;
import com.sephora.msl.pageobjects.login.AppLoginPage;
import com.sephora.msl.utils.BaseUtil;
import com.sephora.msl.utils.HtmlLogger;
import com.sephora.msl.utils.PropertyFile;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class CucumberHooks extends BaseUtil {
Logger logger = LogManager.getLogger(CucumberHooks.class.getName());
    
	protected String screenshotDestinationFolder;
	protected AppLoginPage applLoginPage;
	//protected Scenario scenario;
	private PropertyFile propFile;
	private BaseUtil base;
	String fileName = "defaultenv.properties";

	public CucumberHooks(AppLoginPage applLoginPage,BaseUtil base) {
		super();
		logger.debug("in the constructor of: " + this.getClass().getName());
		propFile = new PropertyFile();
		propFile.loadProps(fileName);
		this.base = base;
		this.applLoginPage = applLoginPage;
	}
	@Before
	public void beforeHook(Scenario scenario) throws Exception {
		logger.info("\n******************************************************************************************************************************"+
				"\n\t\t\t\t\tTEST SCENARIO NAME:    "+ scenario.getName()+
				"\n******************************************************************************************************************************");
		applLoginPage.login(propFile.getProperty("Username"), propFile.getProperty("Password"));
	}
	@After
	public void afterHook(Scenario scenario) throws Exception {
		System.out.println("inside hooks After");
		if(scenario.isFailed()==false) {
			logger.info("[TEST IS SUCCESS -------- Test case " + scenario.getName()	+ " has passed]");	
		}
		if(scenario.isFailed()) {
			logger.info("[TEST IS FAILED -------- Test case " + scenario.getName()	+ " has failed]");
			try {
				String sScreenshotPath = MSLSephoraDriver.takeSnapShotAndRetPath(scenario.getName(),false);
				logger.info("Snapshot Path :<a href='" + sScreenshotPath + "'>"+ sScreenshotPath+"</a>\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		System.out.println("hooks After ends");
		applLoginPage.logout();
		//MSLSephoraDriver.driver.close();
		//logger.debug("browser is closed");
		//new HtmlLogger().createHtmlLogFile();
	}

}
