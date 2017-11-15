package com.equation.school.cocurricular.subjects;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class AllCocurricularSubjects {

	static String[] allsubs = { "Cycling", "Judo", "Wrestling", "Karate", "Boxing", "Cockfighting", "KickBoxing",
			"artial Arts", "Sword Fighting", "Wushu", "Snooker", "Fishing", "Golf", "Paralympic Footbal",
			"Paralympic Athletics", "Rugby", "Tag of war", "Hunting", "Kite Fighting", "Darts", "Hockey",
			"Hide and seek", "Walking", "Weight Lifting", "Body Building", "Mator Cycle Racing", "Debate", "Quizz",
			"Checkers", "Hot Air", "Soccer", "Chess", "Handball", "Volleyball", "Netball", "Atheletics", "Tennis",
			"Cricket", "Beach Volleyball", "Basketball", "Wheelchair Basketball", "Baseball", "Water Basketball",
			"Soft Ball", "British Baseball", "Gymnastics", "Cheerleading", "Dancing" };

	public static ArrayList<String> allHobbies() {
		ArrayList<String> array = new ArrayList<>();
		for (String w : allsubs)
			array.add(w);
		return array;
	}
}