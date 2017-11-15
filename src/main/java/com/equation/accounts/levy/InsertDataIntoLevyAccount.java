package com.equation.accounts.levy;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoLevyAccount {
	Statement stm;

	public InsertDataIntoLevyAccount(Statement stm) {
		this.stm = stm;
	}

	public void postData(String receiptInvoiceNumber, double withdrawal, double deposit, String date, String time,
			String schoolID) {
		String insertQuery = "INSERT INTO levi_acc(receiptInvoiceNumber,withdrawal,deposit,date,time,schoolID)VALUES('"
				+ receiptInvoiceNumber + "','" + withdrawal + "','" + deposit + "','" + date + "','" + time + "','"
				+ schoolID + "')";
		try {
			stm.execute(insertQuery);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}