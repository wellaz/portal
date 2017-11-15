package com.equation.student.parent.details.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoParentsTable {
	Statement stm;

	public InsertDataIntoParentsTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String pFirstName, String pSurname, String pNationalID, String pCell, String pEmail,
			String pAdress, String pOther) {
		String insertQuery = "INSERT INTO parents(pFirstName,pSurname,pNationalID,pCell,pEmail,pAdress,pOther)VALUES('"
				+ pFirstName + "','" + pSurname + "','" + pNationalID + "','" + pCell + "','" + pEmail + "','" + pAdress
				+ "','" + pOther + "')";
		try {
			stm.execute(insertQuery);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}