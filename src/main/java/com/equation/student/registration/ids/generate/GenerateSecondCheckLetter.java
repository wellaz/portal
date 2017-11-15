package com.equation.student.registration.ids.generate;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class GenerateSecondCheckLetter {

	public GenerateSecondCheckLetter() {
		// TODO Auto-generated constructor stub
	}

	public String getSecondCheckLetter(String emis, int firstIndex) {
		ArrayList<String> myArray = Alphabet.alphabetArray();
		int arraySize = myArray.size();

		int newValue = returnnewValue(emis, firstIndex);
		int quotient = returnQuotient(newValue, arraySize);
		int product = returnProduct(quotient, arraySize);
		int index = returnDifference(newValue, product);

		return returnSecondCheckLetter(myArray, index);
	}

	private int returnQuotient(int newValue, int arraySize) {
		return (int) Math.floor(newValue / arraySize);

	}

	private int returnProduct(int quotient, int arraySize) {
		return quotient * arraySize;
	}

	private int returnDifference(int newValue, int product) {
		return (newValue - product) ;
	}

	private String returnSecondCheckLetter(ArrayList<String> myArray, int index) {
		return myArray.get(index);
	}

	private int returnnewValue(String emis, int firstIndex) {
		return firstIndex + Integer.parseInt(emis);
	}

	public static void main(String[] args) {
		GenerateSecondCheckLetter checkLetter = new GenerateSecondCheckLetter();
		String emis = "980";
		int firstIndex = 3;
		System.out.println("Second Check leter  " + checkLetter.getSecondCheckLetter(emis, firstIndex));

	}
}
