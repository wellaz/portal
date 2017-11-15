package com.equation.util.years.ranges;

import java.util.ArrayList;

import com.equation.utils.date.DateUtility;

/**
 *
 * @author Wellington
 */
public class PaymentHistoryYears {

	public PaymentHistoryYears() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<String> allYears() {
		ArrayList<String> array = new ArrayList<>();
		String thisYear = new DateUtility().getYear();
		int finalYearr = Integer.parseInt(thisYear);
		int min = finalYearr - 12;
		for (int finalYear = finalYearr, i = min; finalYear >= i; finalYear--)
			array.add("" + finalYear);

		return array;
	}
}