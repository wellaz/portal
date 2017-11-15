package com.equation.equate.utils.date;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class AllYearTermsBin {
	public static String[] allterms = { "Term One", "Term Two", "Term Three" };

	public AllYearTermsBin() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<String> allTermsArray() {
		ArrayList<String> list = new ArrayList<>();
		for (String s : allterms)
			list.add(s);
		return list;
	}

}
