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
public class GetAbsentStudentsForClass {

	ResultSet rs;
	Statement stm;

	public GetAbsentStudentsForClass(ResultSet rs, Statement stm) {
		this.rs = rs;
		this.stm = stm;

	}

	public int getAbsentNumber(String class_name,String subject) {
		int number = 0;
		String query = "SELECT COUNT(student_name) FROM absent_students WHERE class_name = '" + class_name
				+ "' AND subject = '" + subject + "' GROUP BY class_name,subject";
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
