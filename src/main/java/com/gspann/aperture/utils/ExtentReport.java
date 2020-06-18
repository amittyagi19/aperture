package com.gspann.aperture.utils;

import java.io.File;
import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.cucumber.java.Scenario;

public class ExtentReport extends BaseUtil {
    String fileName = reportLocation + "//extentReports.html";
   private ExtentHtmlReporter extentHtmlReporter;
   private ExtentReports extentReports;
   
   public ExtentReport(String reportLocation) {
	   extentHtmlReporter = new ExtentHtmlReporter(new File(reportLocation));
	   extentReports = new ExtentReports();
	   extentReports.attachReporter(extentHtmlReporter);
   }
   public void createTest(Scenario scenario,String screenshotFileName) throws IOException {
	   if(scenario != null) {
		  String testName = getScenarioTitle(scenario);
		   switch (scenario.getStatus()) {
		case PASSED:
			extentReports.createTest(testName).pass("Passed");
			break;
		case FAILED:
			extentReports.createTest(testName).fail("Failed");//.addScreenCaptureFromPath(getScreenshotLoc(screenshotFileName));
			break;
		default:
			extentReports.createTest(testName).skip("Skipped");
			
		}
	   }
	   
   }
   public void writeToReport() {
	   if(extentReports != null) {
		   extentReports.flush();
	  }
   }
//   private String getScreenshotLoc(String loc) {
//	   return System.getProperty("user.dir")+loc;
//   }
   private String getScenarioTitle(Scenario scenario) {
	   return scenario.getName().replaceAll(" ", "");
   }
    
    
}
