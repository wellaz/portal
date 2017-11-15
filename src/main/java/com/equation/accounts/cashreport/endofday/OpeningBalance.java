package com.equation.accounts.cashreport.endofday;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.utils.date.DateUtility;

/**
 *
 * @author Wellington
 */
public class OpeningBalance {

	ResultSet rs, rs1;
	Statement stm, stmt;

	public OpeningBalance(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.stm = stm;

	}

	public double getBalance() {
		double balance = 0;
		String today = new DateUtility().getDate();
		// String yesterday = new DateUtility().getYesterdayDate();

		String query = "SELECT openingBalance,cashorchequeReceived,cashBanked FROM cash_report";

		try {

			rs1 = stmt.executeQuery(query);
			rs1.last();
			int value = rs1.getRow();
			if (value > 0) {
				String query1 = "SELECT openingBalance,cashorchequeReceived,cashBanked FROM cash_report";
				rs = stmt.executeQuery(query1);
				rs.last();
				double openigBalance = rs.getDouble(1);
				double cashReceived = rs.getDouble(2);
				double cashBanked = rs.getDouble(3);
				balance = (openigBalance + cashReceived) - cashBanked;
			} else {
				String text = "SELECT withdrawal,deposit FROM bank_acc WHERE date <='" + today + "' ORDER BY date ASC";
				rs1 = stmt.executeQuery(text);
				rs1.last();
				int rows = rs1.getRow();
				if (rows > 0) {
					String text1 = "SELECT withdrawal,deposit FROM bank_acc WHERE date <='" + today
							+ "' ORDER BY date ASC";
					rs = stm.executeQuery(text1);
					while (rs.next()) {
						double debit = rs.getDouble(1);
						double credit = rs.getDouble(2);

						balance += (credit - debit);
					}
				} else {
					balance = 0;
				}
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return balance;
	}
}