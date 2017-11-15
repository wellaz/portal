package com.equation.accounts.other;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoOtherAccount {
	Statement stm;

	public InsertDataIntoOtherAccount(Statement stm) {
		this.stm = stm;
	}

	public void postData(String receiptInvoiceNumber, double withdrawal, double deposit, String date, String time,
			String schoolID) {
		String insertQuery = "INSERT INTO other_acc(receiptInvoiceNumber,withdrawal,deposit,date,time,schoolID)VALUES('"
				+ receiptInvoiceNumber + "','" + withdrawal + "','" + deposit + "','" + date + "','" + time + "','"
				+ schoolID + "')";
		try {
			stm.execute(insertQuery);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}