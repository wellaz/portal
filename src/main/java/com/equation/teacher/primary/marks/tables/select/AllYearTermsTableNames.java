package com.equation.teacher.primary.marks.tables.select;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class AllYearTermsTableNames {
	public static String[] alltables = { "term1markspri", "term2markspri", "term3markspri" };

	public AllYearTermsTableNames() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<String> allPrimaryMarksTableNames() {
		ArrayList<String> list = new ArrayList<>();
		for (String s : alltables)
			list.add(s);
		return list;
	}
}