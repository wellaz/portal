package com.equation.teacher.leave.onleave.leavetypes;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Wellington
 */
public class TeacherLeaveTypes {
	public static String SICK_LEAVE = "Sick Leave";
	public static String ON_VACATION = "Vacation Leave";
	public static String MATERNITY_LEAVE = "Maternity Leave";
	public static String ON_STUDIES = "Studies Leave";
	public static String OTHER_LEAVE = "Other";
	public static String MDL_LEAVE = "MDL LEAVE";

	public static ArrayList<String> leaveTypes() {
		ArrayList<String> data = new ArrayList<>();
		data.addAll(Arrays.asList(SICK_LEAVE, ON_VACATION, MATERNITY_LEAVE, MDL_LEAVE, ON_STUDIES, OTHER_LEAVE));
		return data;
	}
}