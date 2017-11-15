package com.equation.accounts.cashreport;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoCashReportTable {
	Statement stm;

	public InsertDataIntoCashReportTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(double openingBalance, double cashorchequeReceived, int firstReceiptNumber,
			int lastReceiptNumber, double cashBanked, String preparedBy, String approvedBy, String date,
			String schoolID) {
		String insertQuery = "INSERT INTO cash_report(openingBalance,cashorchequeReceived,firstReceiptNumber,lastReceiptNumber,cashBanked,preparedBy,approvedBy,date,schoolID)VALUES('"
				+ openingBalance + "','" + cashorchequeReceived + "','" + firstReceiptNumber + "','" + lastReceiptNumber
				+ "','" + cashBanked + "','" + preparedBy + "','" + approvedBy + "','" + date + "','" + schoolID + "')";
		try {
			stm.execute(insertQuery);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}