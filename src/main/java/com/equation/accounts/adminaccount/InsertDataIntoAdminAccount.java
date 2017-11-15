package com.equation.accounts.adminaccount;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoAdminAccount {
	Statement stm;

	public InsertDataIntoAdminAccount(Statement stm) {
		this.stm = stm;
	}

	public void postAdministrationAccountTransaction(String receiptInvoiceNumber, double withdrawal, double deposit,
			String date, String time, String schoolID) {
		String query = "INSERT INTO admin_acc(receiptInvoiceNumber,withdrawal,deposit,date,time,schoolID)VALUES('"
				+ receiptInvoiceNumber + "','" + withdrawal + "','" + deposit + "','" + date + "','" + time + "','"
				+ schoolID + "')";
		try {
			stm.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}