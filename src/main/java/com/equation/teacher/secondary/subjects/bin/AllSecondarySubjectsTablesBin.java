package com.equation.teacher.secondary.subjects.bin;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class AllSecondarySubjectsTablesBin {

	public static String[] allsubjectstables = { "accounts", "agriculture", "art", "biology", "building", "business",
			"chemistry", "chishona", "commerce", "computers", "divinity", "economics", "englishlanguage",
			"englishliterature", "fashion", "food", "gc", "geography", "history", "integratedscience", "isindebele",
			"mathematics", "metalwork", "mob", "music", "philosophy", "physics", "psychology", "re", "sociology", "tg",
			"woodwork" };

	public AllSecondarySubjectsTablesBin() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<String> allSubjectsTablesArray() {
		ArrayList<String> data = new ArrayList<>();
		for (String s : allsubjectstables) {
			data.add(s);
		}
		return data;
	}

}
