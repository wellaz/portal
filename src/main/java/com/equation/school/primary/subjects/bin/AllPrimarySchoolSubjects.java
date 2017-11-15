package com.equation.school.primary.subjects.bin;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class AllPrimarySchoolSubjects {

	public static String[] list = { "English", "Shona", "Mathematics", "General Paper", "Agriculture" };

	public static ArrayList<String> allSubjectsArray() {
		ArrayList<String> data = new ArrayList<>();
		for (String s : list) {
			data.add(s);
		}
		return data;
	}

}
