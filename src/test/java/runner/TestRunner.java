package runner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.gspann.aperture.base.BaseConfiguration;
import com.gspann.aperture.driver.website.ApertureDriver;

import cucumber.api.testng.AbstractTestNGCucumberTests;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.Scenario;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(tags= {"@Account"},glue = "com.gspann.aperture.steps", plugin = { "json:target/json-cucumber-reports/cukejson.json",
		"com.gspann.aperture.base.BaseConfiguration","testng:target/testng-cucumber-reports/cuketestng.xml" }, features = "src/test/resources/features")
//@Test
public class TestRunner //extends AbstractTestNGCucumberTests 
    extends AbstractTestNGCucumberParallelTests 
{

	private static long duration;

	@BeforeClass
	public static void before() {
		duration = System.currentTimeMillis();
	}

	@AfterClass
	public static void after() {
		duration = System.currentTimeMillis() - duration;
		System.out.println("Test DURATION - " + duration);
		
		//"io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm"
		//tags = {"@groupMark"}
	}
	
	@Test
	public static void runtest()
	{
		System.out.println("Run Test Aperture");
	}		
}
 