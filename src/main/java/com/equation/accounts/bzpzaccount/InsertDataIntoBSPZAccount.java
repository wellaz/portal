package com.equation.accounts.bzpzaccount;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoBSPZAccount {
	Statement stm;

	public InsertDataIntoBSPZAccount(Statement stm) {
		this.stm = stm;
	}

	public void postBSPZTransaction(String receiptInvoiceNumber, double withdrawal, double deposit, String date,
			String time, String schoolID) {
		String insertQuery = "INSERT INTO bspz_acc(receiptInvoiceNumber,withdrawal,deposit,date,time,schoolID)VALUES('"
				+ receiptInvoiceNumber + "','" + withdrawal + "','" + deposit + "','" + date + "','" + time + "','"
				+ schoolID + "')";
		try {
			stm.execute(insertQuery);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}