package com.equation.util.primary.subjects;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class PrimarySubjects {

	static String[] subjects = { "Mathematics", "English", "Shona", "Agriculture", "General Paper" };

	public static ArrayList<String> allPrimarySubjects() {
		ArrayList<String> list = new ArrayList<>();
		for (String w : subjects)
			list.add(w);
		return list;
	}

}
