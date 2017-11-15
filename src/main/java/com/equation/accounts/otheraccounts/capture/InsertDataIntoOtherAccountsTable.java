package com.equation.accounts.otheraccounts.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoOtherAccountsTable {
	Statement stm;

	public InsertDataIntoOtherAccountsTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String accountName, double amount, String description, String date, String time,
			String schoolID) {
		String query = "INSERT INTO other_accounts(accountName,amount,description,date,time,schoolID)VALUES('"
				+ accountName + "','" + amount + "','" + description + "','" + date + "','" + time + "','" + schoolID
				+ "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}

}
