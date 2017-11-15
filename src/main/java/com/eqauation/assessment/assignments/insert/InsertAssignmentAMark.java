package com.eqauation.assessment.assignments.insert;

import java.sql.SQLException;
import java.sql.Statement;

public class InsertAssignmentAMark {

	public InsertAssignmentAMark() {
		// TODO Auto-generated constructor stub
	}

	public void insertData(Statement stm, String assignment_id, String student_id, String mark, String date_posted) {
		String query = "INSERT INTO assignment_marks(assignment_id,student_id,mark,date_posted)VALUES('" + assignment_id
				+ "','" + student_id + "','" + mark + "','" + date_posted + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}

	}

}
