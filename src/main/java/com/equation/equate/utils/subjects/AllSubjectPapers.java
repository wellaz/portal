package com.equation.equate.utils.subjects;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class AllSubjectPapers {
	public static String[] papers = { "1", "2" };

	public static ArrayList<String> allPapers() {
		ArrayList<String> data = new ArrayList<>();
		for (String s : papers)
			data.add(s);
		return data;
	}

}
