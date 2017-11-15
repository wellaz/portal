package com.equation.accounts.bankingregister;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoBankingRegister {
	Statement stm;

	public InsertDataIntoBankingRegister(Statement stm) {
		this.stm = stm;
	}

	public void insertData(int depositNumber, double amount, String messagerID, String messangerIDSign, String senderID,
			String senderIDSign, String comments, String date, String schoolID) {
		String query = "INSERT INTO banking_register(depositNumber,amount,messagerID,messangerIDSign,senderID,senderIDSign,comments,date,schoolID)VALUES('"
				+ depositNumber + "','" + amount + "','" + messagerID + "','" + messangerIDSign + "','" + senderID
				+ "','" + senderIDSign + "','" + comments + "','" + date + "','" + schoolID + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}