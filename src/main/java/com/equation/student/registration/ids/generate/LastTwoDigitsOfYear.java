package com.equation.student.registration.ids.generate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Wellington
 */
public class LastTwoDigitsOfYear {
	public LastTwoDigitsOfYear() {
		// TODO Auto-generated constructor stub
	}

	public String getLastTwoDigitsOfYear(String year) {
		String twoDigits = null;
		String regex = "(\\w+)(\\d\\d)";
		try {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(year);
			matcher.find();
			twoDigits = matcher.group(2);
		} catch (IllegalStateException ee) {
			ee.printStackTrace();
		}

		return twoDigits;
	}
}