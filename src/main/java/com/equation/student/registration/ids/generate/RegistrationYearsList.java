package com.equation.student.registration.ids.generate;

import java.util.ArrayList;

import com.equation.utils.date.DateUtility;

/**
 *
 * @author Wellington
 */
public class RegistrationYearsList {

	static String[] yearslist = { "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020",
			"2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" };

	public RegistrationYearsList() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<String> yearsList() {
		ArrayList<String> array = new ArrayList<>();
		for (String s : yearslist)
			array.add(s);
		return array;
	}

	public static String thisYear() {
		return new DateUtility().getYear();
	}
}