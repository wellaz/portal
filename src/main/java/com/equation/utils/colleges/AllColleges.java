package com.equation.utils.colleges;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class AllColleges {

	static String[] allcolleges = { "None", "National University of Science And Technology", "University of Zimbabwe",
			"Midlands States University", "Great Zimbabwe University", "Harare Institute of Technology",
			"Africa University", "Zimbabwe Open University", "Lupane State University",
			"Catholic University of Zimbabwe", "Women's University of Zimababwe",
			"Bindura University of Science and Technology", "Chinhoyi University of Technology",
			"Ezekiel Guti University", "Hillside Teachers College", "Mutare Teachers college",
			"Masvingo Teachers College", "Belvedere Technical Teachers College", "Mkoba Teachers College",
			"United College of Education", "Gwanda Teachers College", "Marymount Teachers College",
			"Seke Teachers College", "Morgan Teachers College", "Nyadire Teachers College", "Bontolfi Teachers College",
			"Mogernster Teachers College", "Madziwa Teachers College", "Other"

	};

	public AllColleges() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<String> colleges() {
		ArrayList<String> array = new ArrayList<>();
		for (String p : allcolleges)
			array.add(p);
		return array;
	}
}
