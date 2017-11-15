package com.equation.utils.primary.subjects;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class AllPrimarySubjects {
	public static String[] allsubjects = { "Maths", "English", "Shona", "Ndebele", "Home Economics",
			"Environmental Science", "Social Studies", "Religious and Moral Education", "Physical Education", "Music",
			"Agriculture", "Health And Life Skills", "Family And Religious Education", "Heritage Studies",
			"Information And Communication Technology", "Science And Technology", "Visual And Performing Arts", "Art" };

	public static ArrayList<String> alSubjects() {
		ArrayList<String> array = new ArrayList<>();
		for (String s : allsubjects)
			array.add(s);
		return array;
	}

}
