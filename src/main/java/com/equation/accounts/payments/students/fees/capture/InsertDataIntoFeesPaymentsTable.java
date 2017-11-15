package com.equation.accounts.payments.students.fees.capture;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoFeesPaymentsTable {
	Statement stm, stmt;
	ResultSet rs, rs1;

	public InsertDataIntoFeesPaymentsTable(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.stm = stm;
		this.stmt = stmt;
		this.rs = rs;
		this.rs1 = rs1;
	}

	public void insertData(int receiptNumber, String studentID, String userID, double amount, String term, int year,
			String paidBy, String schoolID, String date, String time, double balance, String description) {

		String query = "INSERT INTO feesreceived(receiptNumber,studentID,userID,amount,term,year,paidBy,schoolID,date,time,balance,description)VALUES('"
				+ receiptNumber + "','" + studentID + "','" + userID + "','" + amount + "','" + term + "','" + year
				+ "','" + paidBy + "','" + schoolID + "','" + date + "','" + time + "','" + balance + "','"
				+ description + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}