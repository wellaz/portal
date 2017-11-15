package com.equation.equate.utils.subjects;

import java.util.ArrayList;

public class AllSubjects {
	public static String MATHEMATICS = "Mathematics";
	public static String ENGLISH = "English";
	public static String SHONA = "Shona";
	public static String GENERAL_PAPER = "General Paper";
	public static String AGRICULTURE = "Agriculture";

	public static String[] allsubs = { MATHEMATICS, ENGLISH, SHONA, GENERAL_PAPER, AGRICULTURE };

	public static ArrayList<String> allSubjects() {
		ArrayList<String> data = new ArrayList<>();
		for (String s : allsubs)
			data.add(s);

		return data;
	}

}
