package com.equation.utils.application.folders;

import java.io.File;

import com.equation.utils.application.basepath.ApplicationBasePath;

/**
 *
 * @author Wellington
 */
public class Folder {

	public static String makeUploadsFolder() {
		File theDir = new File(ApplicationBasePath.basePath() + "/WEB-INF/uploads");

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + theDir.getName());
			boolean result = false;

			try {
				theDir.mkdir();
				result = true;
			} catch (SecurityException se) {
				se.printStackTrace();
			}
			if (result) {
				System.out.println("DIR created");
			}
		}
		//System.out.println(theDir.getAbsolutePath());
		return theDir.getAbsolutePath();
	}

	public static String makeTeachersAssignmentsUploadsFolder(String classname) {
		File theDir = new File(ApplicationBasePath.basePath() + "/WEB-INF/assignments/" + classname);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + theDir.getName());
			boolean result = false;

			try {
				theDir.mkdir();
				result = true;
			} catch (SecurityException se) {
				se.printStackTrace();
			}
			if (result) {
				System.out.println("DIR created");
			}
		}
		return theDir.getAbsolutePath();

	}

}
