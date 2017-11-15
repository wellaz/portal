package com.equation.accounts.cashreport.endofday;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.utils.date.DateUtility;

/**
 *
 * @author Wellington
 */
public class CashSpentToday {

	ResultSet rs, rs1;
	Statement stm, stmt;

	public CashSpentToday(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.stm = stm;

	}

	public double getTodaysTotalWithdrawal() {
		double balance = 0;
		String today = new DateUtility().getDate();

		String text = "SELECT withdrawal FROM bank_acc WHERE  date ='" + today + "'";
		try {
			rs1 = stmt.executeQuery(text);
			rs1.last();
			int rows = rs1.getRow();

			if (rows > 0) {

				String text1 = "SELECT withdrawal FROM bank_acc WHERE date ='" + today + "'";
				rs = stm.executeQuery(text1);
				while (rs.next()) {
					double withdrawal = rs.getDouble(1);

					balance += withdrawal;
				}
			} else {
				balance = 0;
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return balance;
	}
}