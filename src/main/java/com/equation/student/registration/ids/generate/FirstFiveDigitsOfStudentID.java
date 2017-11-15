package com.equation.student.registration.ids.generate;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class FirstFiveDigitsOfStudentID {
	public FirstFiveDigitsOfStudentID() {
		// TODO Auto-generated constructor stub
	}

	public String generateFirstFiveDigits(int nextAutoIncrementValue, String year) {
		String threeDigitValue = new ConvertValueToThreeDigits().generateThreeDigitValue(nextAutoIncrementValue);
		String lastTwoDigitsOfYear = new LastTwoDigitsOfYear().getLastTwoDigitsOfYear(year);
		return lastTwoDigitsOfYear + threeDigitValue;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GenerateTheFirstCheckLetter generateTheFirstCheckLetter = new GenerateTheFirstCheckLetter();
		FirstFiveDigitsOfStudentID firstFiveDigitsOfStudentID = new FirstFiveDigitsOfStudentID();
		LastTwoDigitsOfYear lastTwoDigitsOfYear = new LastTwoDigitsOfYear();
		GenerateSecondCheckLetter generateSecondCheckLetter = new GenerateSecondCheckLetter();
		ConvertValueToThreeDigits convertValueToThreeDigits = new ConvertValueToThreeDigits();

		String year = "2012";
		int nextAutoIncrementValue = 347;
		String emis = "1981";

		ArrayList<String> alphabetArray = Alphabet.alphabetArray();
		int arraySize = alphabetArray.size();

		String fiveDigitValue = firstFiveDigitsOfStudentID.generateFirstFiveDigits(nextAutoIncrementValue, year);

		int quotient = generateTheFirstCheckLetter.returnTheQuotient(fiveDigitValue, arraySize);

		int product = generateTheFirstCheckLetter.returnTheProduct(quotient, arraySize);

		int index = generateTheFirstCheckLetter.returnTheDifference(fiveDigitValue, product);

		String firstCheckLetter = generateTheFirstCheckLetter.getFirstCheckLetter(fiveDigitValue,quotient,product);

		int firstIndex = index;

		String studentIDd = firstCheckLetter + lastTwoDigitsOfYear.getLastTwoDigitsOfYear(year)
				+ convertValueToThreeDigits.generateThreeDigitValue(nextAutoIncrementValue)
				+ generateSecondCheckLetter.getSecondCheckLetter(emis, firstIndex);

		System.out.println("      The student number is " + studentIDd);
	}
}