package com.equation.teacher.multiplechoice.records;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertNewInClassRecord {
	Statement stm;
	ResultSet rs;

	public InsertNewInClassRecord(Statement stm, ResultSet rs) {
		this.rs = rs;
		this.stm = stm;
	}

	public void insertData(String subject, String topic, String class_name, int number_of_questions, int total_mark,
			String test_period, String prepared_by, String date, String time, String due) {
		String query = "INSERT INTO inclasstestrecords(subject,topic,class_name,number_of_questions,total_mark,test_period,prepared_by,date,time,due)VALUES('"
				+ subject + "','" + topic + "','" + class_name + "','" + number_of_questions + "','" + total_mark
				+ "','" + test_period + "','" + prepared_by + "','" + date + "','" + time + "','" + due + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}

	public int getRowID(String subject, String class_name, int number_of_questions, int total_mark,
			String test_period) {
		int id = 0;
		String query = "SELECT record_id FROM inclasstestrecords WHERE subject = '" + subject + "' AND class_name = '"
				+ class_name + "'AND number_of_questions = '" + number_of_questions + "' AND total_mark = '"
				+ total_mark + "' AND test_period = '" + test_period + "' ";
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