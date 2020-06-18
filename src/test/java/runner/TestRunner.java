package runner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sephora.msl.base.BaseConfiguration;
import com.sephora.msl.driver.website.MSLSephoraDriver;

import io.cucumber.java.Scenario;

import io.cucumber.java.Scenario;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(tags= {"@smartGroup"},glue = "com.sephora.msl.steps", plugin = { "json:target/json-cucumber-reports/cukejson.json",
		"com.sephora.msl.base.BaseConfiguration","testng:target/testng-cucumber-reports/cuketestng.xml" }, features = "src/test/resources/features")
@Test
public class TestRunner extends AbstractTestNGCucumberParallelTests {

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
}
 