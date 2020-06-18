package com.gspann.aperture.listeners;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.gherkin.model.Feature;
import com.gspann.aperture.driver.website.ApertureDriver;
import com.gspann.aperture.utils.ExtentReport;

import io.cucumber.java.Scenario;
/**
 * 
 * @author vinay.malik
 *
 */
public class NGTestListener implements ITestListener {
	Logger logger = LogManager.getLogger(NGTestListener.class.getName());
	protected Scenario scenario;
	private static String scenarioName = null;
	public NGTestListener(Scenario scenario) {
		this.scenario=scenario;
	}

	@Override
	public void onTestStart(ITestResult successResult) {
		scenarioName = 	scenario.getName();
		logger.info("\n******************************************************************************************************************************"+
				"\n\t\t\t\t\tTEST SCENARIO NAME:                  "+ scenarioName+
				"\n******************************************************************************************************************************");
	}

	@Override
	public void onTestSuccess(ITestResult successResult) {
		logger.info("[TEST IS SUCCESS -------- Test case " + scenarioName	+ " has passed]");		
	}

	@Override
	public void onTestFailure(ITestResult failedResult) {
		logger.info("[TEST IS FAILED -------- Test case " + scenarioName	+ " has failed]");
		try {
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult skippedResult) {
		logger.info("[TEST IS SKIPPED -------- Test case " + scenarioName	+ " skipped]");
		//captureScreenshot(skippedResult);
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult failedWithSuccessResult) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext beforeAllTestContext) {
		
		//features.
		
		
	}

	@Override
	public void onFinish(ITestContext afterAllTestContext) {
		System.out.println("All Test cases are executed on QA environment");
		
	}
	public void captureScreenshot(Scenario scenario){
	String browser=null;
	//try{browser=(String) scenario.get;}catch(Exception e){browser="chrome";}
	try {
		String sScreenshotPath = ApertureDriver.takeSnapShotAndRetPath(scenarioName,false);
		logger.info("Snapshot Path :<a href='" + sScreenshotPath + "'>"+ sScreenshotPath+"</a>\n");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

}
