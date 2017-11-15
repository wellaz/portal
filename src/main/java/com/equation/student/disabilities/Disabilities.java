package com.equation.student.disabilities;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class Disabilities {

	static String[] list = { "None", "Physically Handicapped", "Visual Impairment", "Hearing Impairment ",
			"Speech Impairment", "Mentally Challanged" };

	public Disabilities() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<String> allDisabilities() {
		ArrayList<String> array = new ArrayList<>();
		for (String d : list)
			array.add(d);
		return array;
	}
}