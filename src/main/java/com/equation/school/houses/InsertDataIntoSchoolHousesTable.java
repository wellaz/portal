package com.equation.school.houses;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoSchoolHousesTable {
	Statement stm;

	public InsertDataIntoSchoolHousesTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String schoolID, String houseName) {
		String query = "INSERT INTO schoolhouses(schoolID,houseName)VALUES('" + schoolID + "','" + houseName + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}