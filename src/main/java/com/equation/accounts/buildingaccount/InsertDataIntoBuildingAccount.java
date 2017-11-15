package com.equation.accounts.buildingaccount;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoBuildingAccount {
	Statement stm;

	public InsertDataIntoBuildingAccount(Statement stm) {
		this.stm = stm;
	}

	public void postBuildingAccountTransaction(String receiptInvoiceNumber, double withdrawal, double deposit,
			String date, String time, String schoolID) {
		String insertQuery = "INSERT INTO building_acc(receiptInvoiceNumber,withdrawal,deposit,date,time,schoolID)VALUES('"
				+ receiptInvoiceNumber + "','" + withdrawal + "','" + deposit + "','" + date + "','" + time + "','"
				+ schoolID + "')";
		try {
			stm.execute(insertQuery);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}