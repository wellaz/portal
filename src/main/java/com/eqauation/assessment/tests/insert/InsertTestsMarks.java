package com.eqauation.assessment.tests.insert;

import java.sql.SQLException;
import java.sql.Statement;

public class InsertTestsMarks {

	public InsertTestsMarks() {
		// TODO Auto-generated constructor stub
	}

	public void insertData(Statement stm, String student_id, String mark, String submission_date,
			String submission_time) {
		String query = "INSERT INTO tests_marks(student_id,mark,submission_date,submission_time)VALUES('" + student_id
				+ "','" + mark + "','" + submission_date + "','" + submission_time + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}