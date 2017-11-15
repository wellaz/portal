package com.equation.utils.date;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class AllYears {

	public static ArrayList<String> makeYears() {
		ArrayList<String> data = new ArrayList<>();

		for (int init = Integer.parseInt(new DateUtility().getYear()); init >= 2010; init--) {
			data.add(String.valueOf(init));
		}
		return data;
	}
}