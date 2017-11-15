package com.equation.teacher.assinment.set;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertNewAssignmentRecord {
	Statement stm;
	ResultSet rs;

	public InsertNewAssignmentRecord(Statement stm, ResultSet rs) {
		this.rs = rs;
		this.stm = stm;
	}

	public void insertData(String subject, String topic, String class_name, int number_of_questions, int total_mark,
			String prepared_by, String date, String time, String due) {
		String query = "INSERT INTO assignmentrecords(subject,topic,class_name,number_of_questions,total_mark,prepared_by,date,time,due)VALUES('"
				+ subject + "','" + topic + "','" + class_name + "','" + number_of_questions + "','" + total_mark
				+ "','" + prepared_by + "','" + date + "','" + time + "','" + due + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}

	public int getRowID(String subject, String class_name, int number_of_questions, int total_mark, String date,
			String time) {
		int id = 0;
		String query = "SELECT record_id FROM assignmentrecords WHERE subject = '" + subject + "' AND class_name = '"
				+ class_name + "'AND number_of_questions = '" + number_of_questions + "' AND total_mark = '"
				+ total_mark + "' AND date = '" + date + "' AND time = '" + time + "' ";
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
