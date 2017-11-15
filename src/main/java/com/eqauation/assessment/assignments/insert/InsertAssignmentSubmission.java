package com.eqauation.assessment.assignments.insert;

import java.sql.SQLException;
import java.sql.Statement;

public class InsertAssignmentSubmission {

	public InsertAssignmentSubmission() {
		// TODO Auto-generated constructor stub
	}

	public void insertData(Statement stm, String student_id, String date_submitted, String time_submited,
			String assignment_id) {
		String query = "INSERT INTO assignment_submissions(student_id,date-submitted,time_submited,assignment_id)VALUES('"
				+ student_id + "','" + date_submitted + "','" + time_submited + "','" + assignment_id + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}

}
