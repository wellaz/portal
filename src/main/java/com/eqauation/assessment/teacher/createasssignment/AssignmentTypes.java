package com.eqauation.assessment.teacher.createasssignment;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Wellington
 */
public class AssignmentTypes {
	public static String ONLINE = "ONLINE";
	public static String DOCUMENT = "DOCUMENT";

	public static ArrayList<String> types() {
		return new ArrayList<>(Arrays.asList(DOCUMENT, ONLINE));
	}
}
