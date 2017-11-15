package com.equation.accounts.collection;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class AllAccountsTables {
	static String[] allaccountstables = { "bank_acc", "admin_acc", "bspz_acc", "sports_acc", "building_acc",
			"tution_acc", "generalpurpose_acc", "levi_acc", "other_acc" };

	public AllAccountsTables() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<String> allAccountsTablesArray() {
		ArrayList<String> array = new ArrayList<>();
		for (String s : allaccountstables)
			array.add(s);
		return array;
	}
}