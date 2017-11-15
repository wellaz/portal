package com.equation.equate.insertdata;

import java.sql.SQLException;
import java.sql.Statement;

public class InsertDataIntoMarkSchedule {

	public void insertData(String tablename, String class_name, String subject, String out_of, String percentage,
			String student_name, String raw_mark, String adjusted_mark, Statement stm, String term, String year,
			String date) {
		String query = "INSERT INTO " + tablename
				+ "(class_name,subject,out_of,percentage,student_name,raw_mark,adjusted_mark,term,year,date)VALUES('"
				+ class_name + "','" + subject + "'	,	'" + out_of + "',	'" + percentage + "',	'" + student_name
				+ "','" + raw_mark + "','" + adjusted_mark + "','" + term + "','" + year + "','" + date + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}