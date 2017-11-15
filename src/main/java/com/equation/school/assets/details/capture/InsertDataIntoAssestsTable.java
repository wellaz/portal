package com.equation.school.assets.details.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoAssestsTable {
	Statement stm;

	public InsertDataIntoAssestsTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String schoolID, String type, String quantity, String status) {
		String insertQuery = "INSERT INTO assets(schoolID,type,quantity,status)VALUES('" + schoolID + "', '" + type
				+ "', '" + quantity + "', '" + status + "')";
		try {
			stm.execute(insertQuery);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}