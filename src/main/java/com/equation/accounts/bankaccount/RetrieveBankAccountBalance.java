package com.equation.accounts.bankaccount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class RetrieveBankAccountBalance {
	ResultSet rs;
	Statement stm;

	public RetrieveBankAccountBalance(ResultSet rs, Statement stm) {
		this.rs = rs;
		this.stm = stm;
	}

	public double getBalance() {
		double balance = 0;
		String myQuery = "SELECT withdrawal,deposit FROM bank_acc";
		try {
			rs = stm.executeQuery(myQuery);
			if (rs.next()) {
				do {
					double withdrawal = rs.getDouble(1);
					double deposit = rs.getDouble(2);
					balance = balance + deposit - withdrawal;
				} while (rs.next());
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return balance;
	}

	public double getOpeningBalance() {
		double balance = 0;
		String query = "SELECT withdrawal,deposit FROM bank_acc WHERE DATE date <> CURDATE()";
		try {
			rs = stm.executeQuery(query);
			if (rs.first()) {
				do {
					double withdrawal = rs.getDouble(1);
					double deposit = rs.getDouble(2);
					balance = balance + deposit - withdrawal;
				} while (rs.next());
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return balance;
	}
}