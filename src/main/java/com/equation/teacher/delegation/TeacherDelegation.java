package com.equation.teacher.delegation;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Wellington
 */
public class TeacherDelegation {
	public static String HEAD = "Headmaster";
	public static String D_HEAD = "Deputy Head";
	public static String SENIOR_TEACHER = "Senior Teacher";
	public static String TECHER = "Teacher";
	public static String TIC = "Tic";

	public static ArrayList<String> allTeacherDeleation() {
		ArrayList<String> array = new ArrayList<>();
		array.addAll(Arrays.asList(HEAD.toUpperCase(), D_HEAD.toUpperCase(), SENIOR_TEACHER.toUpperCase(),
				TECHER.toUpperCase(), TIC.toUpperCase()));
		return array;
	}
}