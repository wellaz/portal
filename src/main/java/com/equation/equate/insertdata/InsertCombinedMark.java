package com.equation.equate.insertdata;

import java.sql.SQLException;
import java.sql.Statement;

public class InsertCombinedMark {

	public void insertData(Statement stm, String class_name, String subject, String student_name, String r1, String a1,
			String r2, String a2, String total, int unit) {
		String query = "INSERT INTO combined(class_name,subject,student_name,r1,a1,r2,a2,total,unit)VALUES('"
				+ class_name + "','" + subject + "','" + student_name + "','" + r1 + "','" + a1 + "','" + r2 + "','"
				+ a2 + "','" + total + "','" + unit + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}

	}
}