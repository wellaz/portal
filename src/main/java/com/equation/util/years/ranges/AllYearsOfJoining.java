package com.equation.util.years.ranges;

import java.util.ArrayList;

import com.equation.utils.date.DateUtility;

/**
 *
 * @author Wellington
 */
public class AllYearsOfJoining {

	public AllYearsOfJoining() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<String> allYears() {
		ArrayList<String> array = new ArrayList<>();
		String thisYear = new DateUtility().getYear();
		for (int i = 1980, finalYear = Integer.parseInt(thisYear); i <= finalYear; i++)
			array.add(Integer.toString(i));

		return array;
	}
}