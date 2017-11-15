package com.equation.utils.primary.grades;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Wellington
 */
public class AllPrimaryGrades {

	public static ArrayList<String> allGrades() {
		ArrayList<String> array = new ArrayList<>();
		for (int i = 1; i < 8; i++) {
			array.add("" + i);
		}
		return array;
	}

	public static ArrayList<String> allForms() {
		ArrayList<String> array = new ArrayList<>();
		for (int i = 1; i < 7; i++) {
			array.add("Form " + i);
		}
		return array;
	}

	public static ArrayList<String> allECD() {
		ArrayList<String> array = new ArrayList<>();
		array.addAll(Arrays.asList("ECDA", "ECDB"));
		return array;
	}

}