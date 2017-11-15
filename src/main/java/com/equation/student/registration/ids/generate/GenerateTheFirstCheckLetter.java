package com.equation.student.registration.ids.generate;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class GenerateTheFirstCheckLetter {
	private int index = 0;

	public GenerateTheFirstCheckLetter() {
		// TODO Auto-generated constructor stub
	}

	public String getFirstCheckLetter(String fiveDigitValue,int quotient,int product) {
		ArrayList<String> alphabetArray = Alphabet.alphabetArray();

		setIndex(returnTheDifference(fiveDigitValue, product));

		return returnTheCheckLetter(alphabetArray, getIndex());

	}

	public int returnTheQuotient(String fiveDigitValue, int arraySize) {
		return (int) Math.floor(Integer.parseInt(fiveDigitValue) / arraySize);
	}

	public int returnTheProduct(int quotient, int arraySize) {
		return quotient * arraySize;
	}

	public int returnTheDifference(String fiveDigitValue, int product) {
		return Math.abs(Integer.parseInt(fiveDigitValue) - product);
	}

	public String returnTheCheckLetter(ArrayList<String> alphabetArray, int index) {
		return alphabetArray.get(index);
	}

	public int getIndex() {
		return index;
	}

	private void setIndex(int index) {
		this.index = index;
	}
}