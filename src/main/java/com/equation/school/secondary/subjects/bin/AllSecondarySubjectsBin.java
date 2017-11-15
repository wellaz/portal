package com.equation.school.secondary.subjects.bin;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class AllSecondarySubjectsBin {

	public static String[] allsubjects = { "Accounts", "Agriculture", "Art", "Biology", "Building", "Business Studies",
			"Chemistry", "ChiShona", "Commerce", "Computer Studies", "Divinity", "Economics", "English Language",
			"English Literature", "Fashion And Fabrics", "Food Science", "Guidance And Councelling", "Geography",
			"History", "Integrated Science", "IsiNdebele", "Mathematics", "Metal Work", "Management Of Business",
			"Music", "Philosophy", "Physics", "Psychology", "Religious Education", "Sociology", "Technical Graphics",
			"Wood Work" };

	public static ArrayList<String> allSubjectsArray() {
		ArrayList<String> data = new ArrayList<>();
		for (String s : allsubjects) {
			data.add(s);
		}
		return data;
	}
}