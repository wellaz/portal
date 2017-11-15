package com.equation.student.registration.ids.generate;

/**
 *
 * @author Wellington
 */
public class ConvertValueToThreeDigits {

	public ConvertValueToThreeDigits() {
		// TODO Auto-generated constructor stub
	}

	public String generateThreeDigitValue(int value) {
		String returnValue = null;
		if (value < 10) {
			returnValue = "00" + String.valueOf(value);
		} else if (value < 100) {
			returnValue = "0" + String.valueOf(value);
		} else {
			returnValue = String.valueOf(value);
		}
		return returnValue;
	}
}