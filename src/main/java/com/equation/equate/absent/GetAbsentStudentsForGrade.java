/**
 *
 * @author Wellington
 */
package com.equation.equate.absent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Wellington
 *
 */
public class GetAbsentStudentsForGrade {
	ResultSet rs;
	Statement stm;

	public GetAbsentStudentsForGrade(ResultSet rs, Statement stm) {
		this.rs = rs;
		this.stm = stm;

	}

	public int getAbsentNumber(String grade, String subject) {
		int number = 0;
		String query = "SELECT COUNT(student_name) FROM absent_students WHERE class_name LIKE '" + grade + "%"
				+ "' AND subject = '" + subject + "' ";
		try {
			rs = stm.executeQuery(query);
			if (rs.next()) {
				number = rs.getInt(1);
			} else {
				number = 0;
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return number;

	}
}