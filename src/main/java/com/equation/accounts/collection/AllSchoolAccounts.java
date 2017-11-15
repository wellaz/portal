package com.equation.accounts.collection;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class AllSchoolAccounts {
	static String[] allaccounts = { "Revenue Suspense", "Administration", "B S P Z", "Sports", "Building", "Tuition",
			"General Purpose", "Levy", "Other" };

	public AllSchoolAccounts() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<String> allAccountsArray() {
		ArrayList<String> array = new ArrayList<>();
		for (String s : allaccounts)
			array.add(s);
		return array;
	}
}