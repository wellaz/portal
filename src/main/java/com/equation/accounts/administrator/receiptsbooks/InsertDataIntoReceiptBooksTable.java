package com.equation.accounts.administrator.receiptsbooks;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoReceiptBooksTable {
	Statement stm;

	public InsertDataIntoReceiptBooksTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String schoolID, String bookNumber, String firstReceiptNumber, String finalReceptNumber,
			String date, String time, String status, String userID) {
		String insertQuery = "INSERT INTO receiptbooks(schoolID,bookNumber,firstReceiptNumber,finalReceptNumber,date,time,status,userID)VALUES('"
				+ schoolID + "','" + bookNumber + "','" + firstReceiptNumber + "','" + finalReceptNumber + "','" + date
				+ "','" + time + "','" + status + "','" + userID + "')";
		try {
			stm.execute(insertQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}