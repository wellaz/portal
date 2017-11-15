/**
 *
 * @author Wellington
 */
package com.equation.equate.absent;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Wellington
 *
 */
public class InsertAbsentStudents {
	Statement stm;

	/**
	 * 
	 */
	public InsertAbsentStudents(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String student_name, String class_name, String subject, String paper, String term,
			String year, String date) {
		String query = "INSERT INTO absent_students(student_name,class_name,subject,paper,term,year,date)VALUES('"
				+ student_name + "','" + class_name + "','" + subject + "','" + paper + "','" + term + "','" + year
				+ "','" + date + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}