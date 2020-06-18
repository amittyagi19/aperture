/**
 * 
 */
package com.sephora.msl.utils;

import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.asserts.IAssert;
import org.testng.collections.Maps;

import com.sephora.msl.driver.website.MSLSephoraDriver;

import io.cucumber.java.Scenario;

public class SoftAssert extends org.testng.asserts.SoftAssert {
	private static final Logger logger = LogManager
			.getLogger(SoftAssert.class.getName());

	private final Map<AssertionError, IAssert>	m_errors= Maps.newHashMap();
	private static boolean isAssertFailure = false;
	private static boolean isScreenshotFolderCreated = false;

	public SoftAssert() {
		isAssertFailure = false;
		isScreenshotFolderCreated = false;
		m_errors.clear();
	}

	@Override
	public void executeAssert(IAssert a)
	{
		try {
			a.doAssert();
		} catch (AssertionError ex) {
			m_errors.put(ex, a);
			onAssertFailure(a, ex);
		}
	}

	@Override
	public void assertAll()
	{

		if (!m_errors.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			boolean first = true;
			int i=1;
			for (Map.Entry<AssertionError, IAssert> ae : m_errors.entrySet()) {
				if (first) {
					first = false;
				} else {
					sb.append("\n ");
				}
				sb.append((i++)+"."+ae.getValue().getMessage()+"\n");
			}

			throw new AssertionError(sb.toString());

		}
	}

	public static boolean isAssertFailure(){
		return isAssertFailure;
	}

	public static boolean isScreenshotFolderCreated(){
		return isScreenshotFolderCreated;
	}

	public void onAssertFailure(IAssert a, AssertionError ex){	
		try {
			isAssertFailure = true;		
			logger.info("[FUNCTIONAL FAILURE - VERFICATION ERROR " + ex.getMessage());
			String sScreenshotPath= MSLSephoraDriver.takeSnapShotAndRetPath(ex.getMessage(),isScreenshotFolderCreated);
			logger.info("Snapshot Path :<a href='" + sScreenshotPath + "'>"+ sScreenshotPath+"</a>\n");			
			m_errors.put(ex, a);
			isScreenshotFolderCreated = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isEqual(int iExpected, int iActual)
	{
		boolean flag = false;

		if (iExpected == iActual) {
			flag = true;
		}
		return flag;
	}

	public void clearSoftAssertMap(){
		m_errors.clear();
		logger.info("Soft Assert map cleaned");
	}

}