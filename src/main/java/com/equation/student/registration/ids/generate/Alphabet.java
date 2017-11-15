package com.equation.student.registration.ids.generate;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class Alphabet {
	static String[] aphabetcharacters = { "T", "H", "E", "Q", "U", "C", "K", "B", "R", "W", "N", "F", "X", "J", "M",
			"P", "S", "V", "L", "A", "Z", "Y", "D", "G" };

	public Alphabet() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<String> alphabetArray() {
		ArrayList<String> array = new ArrayList<>();
		for (String s : aphabetcharacters)
			array.add(s);
		return array;
	}
}