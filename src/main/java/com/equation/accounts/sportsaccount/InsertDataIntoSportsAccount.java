package com.equation.accounts.sportsaccount;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoSportsAccount {
	Statement stm;

	public InsertDataIntoSportsAccount(Statement stm) {
		this.stm = stm;
	}

	public void postSportsAccountTransaction(String receiptInvoiceNumber, double withdrawal, double deposit,
			String date, String time, String schoolID) {
		String insertQuery = "INSERT INTO sports_acc(receiptInvoiceNumber,withdrawal,deposit,date,time,schoolID)VALUES('"
				+ receiptInvoiceNumber + "','" + withdrawal + "','" + deposit + "','" + date + "','" + time + "','"
				+ schoolID + "')";
		try {
			stm.execute(insertQuery);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}