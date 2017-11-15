package com.equation.administrator.users.create;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class SystemUserLevels {
	private static String admin = "Administrator";
	private static String admin2 = "Administrator2";
	public static String getAdmin2() {
		return admin2;
	}

	private static String head = "Head";
	private static String teacher = "Teacher";
	private static String bursar = "Bursar";
	private static String accountsclerk = "Accounts Clerk";
	private static String schooltreasurer = "School Treasurer";
	private static String sdcchairman = "SDC Chairman";
	private static String sdcsecretary = "SDC Secretary";
	private static String sdctreasurer = "SDC Treasurer";
	private static String student = "Student";
	private static String defaultuser = "DEFAULT";
	private static String caretaker = "Caretaker";
	private static String Bursar = "Bursar";
	private static String Caretaker = "Caretaker";
	private static String Accountant = "Accountant ";
	private static String Secretary = "Secretary";
	private static String Driver = "Driver";
	private static String Security = "Security";
	private static String WorkShop = "WorkShop";
	private static String kitchen = "kitchen";
	private static String Boarding = "Boarding Master / Mistress";
	private static String Health = "Health Nurse";
	private static String Clerk = "Clerk";

	static String[] alluserlevels = { admin, head, teacher, bursar, accountsclerk, schooltreasurer, sdcchairman,
			sdcsecretary, sdctreasurer, student, defaultuser, caretaker, Bursar, Caretaker, Accountant, Secretary,
			Driver, Security, WorkShop, kitchen, Boarding, Health, Clerk };

	public SystemUserLevels() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<String> allUserLevelsArray() {
		ArrayList<String> array = new ArrayList<>();
		for (String s : alluserlevels)
			array.add(s);
		return array;
	}

	public static String getAdmin() {
		return admin;
	}

	public static String getHead() {
		return head;
	}

	public static String getTeacher() {
		return teacher;
	}

	public static String getBursar() {
		return bursar;
	}

	public static String getAccountsclerk() {
		return accountsclerk;
	}

	public static String getSchooltreasurer() {
		return schooltreasurer;
	}

	public static String getSdcchairman() {
		return sdcchairman;
	}

	public static String getSdcsecretary() {
		return sdcsecretary;
	}

	public static String getSdctreasurer() {
		return sdctreasurer;
	}

	public static String getStudent() {
		return student;
	}

	public String[] getAlluserlevels() {
		return alluserlevels;
	}

	public static String getDefaultuser() {
		return defaultuser;
	}

}