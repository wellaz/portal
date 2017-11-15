package com.equation.utils.numberofsubjects;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class NumberOfSubjects {

	public NumberOfSubjects() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<String> numbersArray() {
		ArrayList<String> array = new ArrayList<>();
		for (int i = 0; i < 26; i++)
			array.add("" + i);
		return array;
	}
}
