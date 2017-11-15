package com.eqauation.assessment.tests.insert;

import java.sql.SQLException;
import java.sql.Statement;

public class InsertTestsDetails {

	public InsertTestsDetails() {
		// TODO Auto-generated constructor stub
	}

	public void insertData(Statement stm, String topic, String class_id, String total_mark, String subject_id,
			String term, String year, String date_posted, String time, String type, String due, String duration) {
		String query = "INSERT INTO tests_details(topic,class_id,total_mark,subject_id,term,year,date_posted,time,type,due,duration)VALUES('"
				+ topic + "','" + class_id + "','" + total_mark + "','" + subject_id + "','" + term + "','" + year
				+ "','" + date_posted + "','" + time + "','" + type + "','" + due + "','" + duration + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}