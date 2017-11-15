package com.equation.ancillary.details.capture;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class AncillaryAlphabet {
	static String[] aphabetcharacters = { "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q",
			"R", "S", "T", "V", "W", "X", "Y", "Z"};

	public AncillaryAlphabet() {

	}
	public static ArrayList<String> alphabetArray() {
		ArrayList<String> array = new ArrayList<>();
		for (String s : aphabetcharacters)
			array.add(s);
		return array;
	}
}
