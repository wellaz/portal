package com.equation.suppliers.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoSuppliersTable {
	Statement stm;

	public InsertDataIntoSuppliersTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String companyName, String email, String cell, String directLine, String physicalAddress,
			String postalAddress, String category) {
		String query = "INSERT INTO (companyName,email,cell,directLine,physicalAddress,postalAddress,category)VALUES('"
				+ companyName + "','" + email + "','" + cell + "','" + directLine + "','" + physicalAddress + "','"
				+ postalAddress + "','" + category + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}