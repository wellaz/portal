package com.equation.accounts.vouchers.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoVouchersTable {
	Statement stm;

	public InsertDataIntoVouchersTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String voucherNumber, String paymentMethod, String preparedBy, String authorisedBy,
			String paidBy, String date, double amount, String schoolID, String status) {
		String insertQuery = "INSERT INTO voucherstable(voucherNumber,paymentMethod,preparedBy,authorisedBy,paidBy,date,amount,schoolID,status)VALUES('"
				+ voucherNumber + "','" + paymentMethod + "','" + preparedBy + "','" + authorisedBy + "','" + paidBy
				+ "','" + date + "','" + amount + "','" + schoolID + "','" + status + "')";
		try {
			stm.execute(insertQuery);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}