/**
 *
 * @author Wellington
 */
package com.equation.equate.utils.grades;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Wellington
 *
 */
public class AllGrades {
	public static String GRADE_THREE = "Grade 3";
	public static String GRADE_FOUR = "Grade 4";
	public static String GRADE_FIVE = "Grade 5";
	public static String GRADE_SIX = "Grade 6";
	public static String GRADE_SEVEN = "Grade 7";

	public static ArrayList<String> allGrades() {
		ArrayList<String> data = new ArrayList<>();
		data.addAll(Arrays.asList(GRADE_THREE, GRADE_FOUR, GRADE_FIVE, GRADE_SIX, GRADE_SEVEN));
		return data;
	}

}
