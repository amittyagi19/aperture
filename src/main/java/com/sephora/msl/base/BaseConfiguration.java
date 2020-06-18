package com.sephora.msl.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sephora.msl.driver.website.MSLSephoraDriver;
import com.sephora.msl.utils.HtmlLogger;
import com.sephora.msl.utils.PropertyFile;

import io.cucumber.java.Scenario;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EmbedEvent;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.SnippetsSuggestedEvent;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import io.cucumber.plugin.event.TestSourceRead;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;
import io.cucumber.plugin.event.WriteEvent;

public class BaseConfiguration implements ConcurrentEventListener{
	protected String defaultProps = "environments/defaultenv.properties";
	protected String testDataProps = "data/TestData.properties";
	protected static PropertyFile propertyFile = new PropertyFile();
	private static final Logger logger = LogManager.getLogger(BaseConfiguration.class.getName());
	protected MSLSephoraDriver driver = new MSLSephoraDriver(propertyFile);
	String folderpath = "output//Screenshots";
	private Scenario scenario;
	
	@Override
	public void setEventPublisher(final EventPublisher publisher) {
		publisher.registerHandlerFor(TestRunStarted.class, suiteBefore);
		publisher.registerHandlerFor(TestCaseStarted.class, scenarioBefore);
		publisher.registerHandlerFor(TestStepStarted.class, stepBefore);
		publisher.registerHandlerFor(TestStepFinished.class, stepAfter);
		publisher.registerHandlerFor(TestCaseFinished.class, scenarioAfter);
		publisher.registerHandlerFor(TestRunFinished.class, suiteAfter);
		publisher.registerHandlerFor(TestSourceRead.class, sourceRead);
		publisher.registerHandlerFor(WriteEvent.class, writeEvent);
		publisher.registerHandlerFor(SnippetsSuggestedEvent.class, snippetsSuggestedEvent);
		publisher.registerHandlerFor(EmbedEvent.class, embedEvent);
	}
	private EventHandler<TestRunStarted> suiteBefore = event -> {
		try {
			driver.createAndCleanFolder(folderpath);
			 driver.loadApplication();
			 propertyFile.loadProps(defaultProps);
			 propertyFile.loadProps(testDataProps);
			// driver.getURL(propertyFile.getProperty("sephora.end.user.url"));
			// logger.info("Application url is opened in before suite");
		} catch (Exception e) {
			logger.error("Error Occured in suite setup");
			e.printStackTrace();
		}		
	};

	private EventHandler<TestRunFinished> suiteAfter = event -> {
		try {	
			driver.quitBrowser();
			logger.info("Browser is closed in after suite");
			//new HtmlLogger().createHtmlLogFile();
		} catch (Exception e1) {
			logger.error("Error Occured in suite teardown");
			e1.printStackTrace();
		}
	};

	private EventHandler<TestCaseStarted> scenarioBefore = event -> {
		try {
				//System.out.println("Scenario name in scenario before "+scenario.getName());		
		} catch (Exception e2) {
			logger.error("Error Occured in scenario setup");
			e2.printStackTrace();
		}
	};

	private EventHandler<TestCaseFinished> scenarioAfter = event -> {		
		try {
								
		} catch (Exception e3) {
			logger.error("Error Occured in scenario teardown");
			e3.printStackTrace();
		}
	};

	private EventHandler<TestSourceRead> sourceRead = event -> {		
		//setLogLevel(envParamBean.getLogLevel());
		//logger.info("Logger initialized successfully");
	};

	private EventHandler<TestStepFinished> stepAfter = event -> {       
		try {
			
		} catch (Exception e4) {
			logger.error("Error Occured in capturing screenshot");
			e4.printStackTrace();
		}
	};

	private EventHandler<TestStepStarted> stepBefore = event -> {};
	private EventHandler<WriteEvent> writeEvent = event -> {};
	private EventHandler<SnippetsSuggestedEvent> snippetsSuggestedEvent = event -> {};
	private EventHandler<EmbedEvent> embedEvent = event -> {};

	}
