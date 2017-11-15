package com.equation.util.years.ranges;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class TeachersAgeGroups {

	public TeachersAgeGroups() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<String> allAgeGroups() {
		ArrayList<String> array = new ArrayList<>();
		for (int i = 18, n = 80; i <= n; i++)
			array.add(Integer.toString(i));
		return array;
	}
}