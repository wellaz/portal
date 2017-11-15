package com.equation.school.furniture.details.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoSchoolFurnitureTable {
	Statement stm;

	public InsertDataIntoSchoolFurnitureTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(int schoolID, String type, int quantity, String status) {
		String query = "INSERT INTO furniture(schoolID,type,quantity,status)VALUES('" + schoolID + "',	'" + type
				+ "','" + quantity + "','" + status + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}