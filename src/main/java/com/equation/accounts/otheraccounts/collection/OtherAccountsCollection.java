package com.equation.accounts.otheraccounts.collection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class OtherAccountsCollection {
	ResultSet rs;
	Statement stm;

	public OtherAccountsCollection(ResultSet rs, Statement stm) {
		this.rs = rs;
		this.stm = stm;
	}

	public ArrayList<String> otherAccounts(String schoolID) {
		ArrayList<String> array = new ArrayList<>();
		String query = " SELECT accountName FROM other_accounts,schools WHERE schools.schoolID = '" + schoolID + "'";
		try {
			rs = stm.executeQuery(query);
			if (rs.next()) {
				do {
					String accountnane = rs.getString(1);
					array.add(accountnane);
				} while (rs.next());
			} else {
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return array;
	}
}