package com.equation.school.electricity.details.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoSchoolElectrificationTable {
	Statement stm;

	public InsertDataIntoSchoolElectrificationTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String schoolID, String electified) {
		String query = "INSERT INTO electricity(schoolID,electified)VALUES('" + schoolID + "','" + electified + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}