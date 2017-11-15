package com.equation.accounts.common.postings;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoCommonAccount {
	Statement stm;

	public InsertDataIntoCommonAccount(Statement stm) {
		this.stm = stm;
	}

	/**
	 * 
	 * @param tablename The name of the table to point to
	 * @param receiptInvoiceNumber The invoice or receipt number of a transaction
	 * @param withdrawal Amount to be withdrawn
	 * @param deposit Amount to be deposited
	 * @param date Effective Date
	 */
	public void InsertData(String tablename, String receiptInvoiceNumber, double withdrawal, double deposit,
			String date) {
		String insertQuery = "INSERT INTO " + tablename + "(receiptInvoiceNumber,withdrawal,deposit,date)VALUES('"
				+ receiptInvoiceNumber + "', '" + withdrawal + "', '" + deposit + "','" + date + "')";
		try {
			stm.execute(insertQuery);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}