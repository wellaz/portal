package com.equation.student.name.retrieve;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class RetrieveStudentName {

	ResultSet rs;
	Statement stm;

	public RetrieveStudentName(ResultSet rs, Statement stm) {
		this.rs = rs;
		this.stm = stm;
	}

	public String fetchStudentName(String studentID) {
		String name = null;
		String query = "SELECT sFirstName,sSurname FROM students WHERE studentID = '" + studentID + "'";
		try {
			rs = stm.executeQuery(query);
			if (rs.next())
				name = rs.getString(1) + " " + rs.getString(2);
			else
				name = "unknown";
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return name.toUpperCase();
	}
}