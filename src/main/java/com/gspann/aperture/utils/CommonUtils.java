package com.gspann.aperture.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import com.gspann.aperture.driver.website.ApertureDriver;


/**
 * Utility class for common functions.
 * 
 * @author GSPANN
 * 
 */
public class CommonUtils {
	protected static ApertureDriver driver;

	public static FileOutputStream getFileOutputStream(final String fileName) throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName);
		return fos;
	}

	public static InputStream loadInputStream(final String classpathLocation, final String fileSystemLocation)
			throws IOException {
		InputStream in = null;

		in = CommonUtils.class.getResourceAsStream(classpathLocation);
		if (in == null) {
			in = new FileInputStream(fileSystemLocation);
		}
		return in;
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns test dat file path attached at test class
	 * 
	 * @return the path string
	 */
	public static String getTestDataFilePath(final String className) {
		String fileSep = File.separator;
		return System.getProperty("user.dir") + fileSep + "test"+fileSep+"resources"+fileSep+"testdata"+fileSep + className + ".xlsx";
	}
	public static void createAndCleanFolder(String folderNameWithLocation){	
		try {
			System.out.println("folder location is "+System.getProperty("user.dir")+"//"+folderNameWithLocation);
			File fDir = new File(System.getProperty("user.dir")+"//"+folderNameWithLocation);
			if (!fDir.exists()) {
				fDir.mkdirs();
			}
			if(folderNameWithLocation.contains("logs")==false)
				FileUtils.cleanDirectory(fDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}