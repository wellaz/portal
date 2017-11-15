package com.equation.accounts.bankaccount;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoBankAccount {
	Statement stm;

	public InsertDataIntoBankAccount(Statement stm) {
		this.stm = stm;
	}

	public void postBankAccountTransaction(String description, double withdrawal, double deposit, String date,
			String time, String schoolID) {
		String insertQuery = "INSERT INTO bank_acc(description,withdrawal,deposit,date,time,schoolID)VALUES('"
				+ description + "','" + withdrawal + "','" + deposit + "','" + date + "','" + time + "','" + schoolID
				+ "')";
		try {
			stm.execute(insertQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}