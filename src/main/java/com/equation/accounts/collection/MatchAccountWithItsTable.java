package com.equation.accounts.collection;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class MatchAccountWithItsTable {
	ArrayList<String> allaccounts = AllSchoolAccounts.allAccountsArray();
	ArrayList<String> allaccountstables = AllAccountsTables.allAccountsTablesArray();
	
	public MatchAccountWithItsTable() {
		// TODO Auto-generated constructor stub
	}

	public String accountTableName(String accountname) {
		String tablename = null;
		int size = allaccounts.size();
		for (int i = 0; i < size; i++) {
			String account = allaccounts.get(i);
			if (accountname.equals(account))
				tablename = allaccountstables.get(i);
		}
		return tablename;
	}
}