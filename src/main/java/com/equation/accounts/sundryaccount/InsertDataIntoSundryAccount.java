package com.equation.accounts.sundryaccount;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoSundryAccount {
	Statement stm;

	public InsertDataIntoSundryAccount(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String receiptInvoiceNumber, double withdrawal, double deposit, String description,
			String date, String cashier, String receivedfrom, String schoolID) {
		String myQuery = "INSERT INTO sundry_acc(receiptInvoiceNumber,withdrawal,deposit,description,date,cashier,receivedfrom,schoolID) VALUES('"
				+ receiptInvoiceNumber + "','" + withdrawal + "','" + deposit + "','" + description + "','" + date
				+ "','" + cashier + "','" + receivedfrom + "','" + schoolID + "')";
		try {
			stm.execute(myQuery);
		} catch (SQLException ex) {
			ex.printStackTrace();

		}
	}
}
