package com.equation.student.currentclass.retrieve;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class GetStudentClass {

	ResultSet rs;
	Statement stm;

	public GetStudentClass(ResultSet rs, Statement stm) {
		this.rs = rs;
		this.stm = stm;
	}

	public String getClassName(String schoolID, String studentID) {
		String classname = null;
		String query = "SELECT classname FROM students,classes,schools WHERE schools.schoolID = '" + schoolID
				+ "' AND studentID = '" + studentID + "'";
		try {
			rs = stm.executeQuery(query);
			if (rs.next()) {
				classname = rs.getString(1);
			} else {
				classname = "Not Found";
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		return classname;
	}
}