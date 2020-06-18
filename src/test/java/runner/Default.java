package runner;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(glue = "com.sephora.msl.steps", plugin = { "json:target/json-cucumber-reports/cukejson.json",
		"com.gspann.aperture.base.BaseConfiguration","testng:target/testng-cucumber-reports/cuketestng.xml" }, features = "src/test/java/com/sephora/msl/features/Zunos_search.feature")
public class Default extends AbstractTestNGCucumberParallelTests {

	private static long duration;

	@BeforeClass
	public static void before() {
		duration = System.currentTimeMillis();
	}

	@AfterClass
	public static void after() {
		duration = System.currentTimeMillis() - duration;
		System.out.println("DURATION - " + duration);
	}
}
