package com.equation.school.classes.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoClassesTable {
	Statement stm;

	public InsertDataIntoClassesTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String classname, String schoolID, String ecNumber, String room) {
		String insertQuery = "INSERT INTO classes( classname,schoolID,ecNumber,room)VALUES('" + classname + "','"
				+ schoolID + "','" + ecNumber + "','" + room + "')";
		try {
			stm.execute(insertQuery);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}