package com.equation.utils.religions;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class AllReligions {

	static String[] list = { "African Traditional Religions", "Judhaism", "Islam", "Hinduism", "Bhuddism",
			"Christianity", "Bahaism", "Confucianism", "Shintoism", "Jainism" };

	public AllReligions() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<String> allReligionsCollecion() {
		ArrayList<String> array = new ArrayList<>();
		for (String f : list)
			array.add(f);
		return array;
	}
}