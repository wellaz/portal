package com.equation.teacher.primary.marks.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoPrimaryMarksTable {
	Statement stm;

	public InsertDataIntoPrimaryMarksTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String tablename, int maths, int english, int shona, int content, int maths2, int english2,
			int shona2, int content2, int agriculture, int agriculture2, int total, String studentID, String term,
			String schoolID, int year) {

		String query = "INSERT INTO " + tablename
				+ "(mathematics,english,shona,content,mathematics2,english2,shona2,content2,agriculture,agriculture2,total,studentID,term,schoolID,year)VALUES('"
				+ maths + "','" + english + "','" + shona + "','" + content + "','" + maths2 + "','" + english2 + "','"
				+ shona2 + "','" + content2 + "','" + agriculture + "','" + agriculture2 + "','" + total + "','"
				+ studentID + "','" + term + "','" + schoolID + "','" + year + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}