package com.eqauation.assessment.assignments.insert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertAssignmentDetails {

	public InsertAssignmentDetails() {
		// TODO Auto-generated constructor stub
	}

	public void insertData(Statement stm, String topic, String class_id, String total_mark, String subject_id,
			String term, String year, String date_posted, String due, String type) {
		String query = "INSERT INTO assignment_details(topic,class_id,total_mark,subject_id,term,year,date_posted,due,type)VALUES(	'"
				+ topic + "','" + class_id + "','" + total_mark + "','" + subject_id + "','" + term + "','" + year
				+ "','" + date_posted + "','" + due + "','" + type + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}

	public int getRowID(ResultSet rs, Statement stm, String topic, String class_id, String total_mark,
			String subject_id, String term, String year, String date_posted, String due, String type) {
		int id = 0;
		String query = "SELECT assinment_id FROM assignment_details WHERE topic = '" + topic + "' AND class_id = '"
				+ class_id + "'AND total_mark = '" + total_mark + "' AND subject_id = '" + subject_id
				+ "' AND date_posted = '" + date_posted + "' AND due = '" + due + "' AND type = '" + type + "' ";
		try {
			rs = stm.executeQuery(query);
			if (rs.last()) {
				id = rs.getInt(1);
			} else {

			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		return id;

	}
}