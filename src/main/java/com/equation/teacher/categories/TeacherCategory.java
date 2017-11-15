package com.equation.teacher.categories;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Wellington
 */
public class TeacherCategory {
	public static String TRAINED = "Trained";
	public static String COLLEGE = "College Student";
	public static String ZINTEC = "Zintec";
	public static String UNTRAINED = "Untrained";

	public static ArrayList<String> allTeacherCategories() {
		ArrayList<String> array = new ArrayList<>();
		array.addAll(Arrays.asList(TRAINED.toUpperCase(), COLLEGE.toUpperCase(), ZINTEC.toUpperCase(),
				UNTRAINED.toUpperCase()));
		return array;
	}
}