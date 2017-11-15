package com.equation.accounts.vouchers.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoVoucherNumbersTable {
	Statement stm;

	public InsertDataIntoVoucherNumbersTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String schoolID, String bookNumber, int firstVoucherNumber, int finalVoucherNumber,
			String datePosted) {
		String query = "INSERT INTO (schoolID,bookNumber,firstVoucherNumber,finalVoucherNumber,datePosted)VALUES('"
				+ schoolID + "','" + bookNumber + "','" + firstVoucherNumber + "','" + finalVoucherNumber + "','"
				+ datePosted + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}