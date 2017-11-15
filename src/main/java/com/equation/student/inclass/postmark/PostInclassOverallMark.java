package com.equation.student.inclass.postmark;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class PostInclassOverallMark {
	Statement stm;

	public PostInclassOverallMark(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String studentID, String test_id, int mark, String date, String time) {
		String query = "INSERT INTO inclassmarkschedule(studentID,test_id,mark,date,time)VALUES('" + studentID + "','"
				+ test_id + "','" + mark + "','" + date + "','" + time + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}