package com.equation.equate.markschedule.overal.perclass;

import java.sql.SQLException;
import java.sql.Statement;

public class InsertDataIntoThisTermMarks {

	/**
	 * 
	 */
	public InsertDataIntoThisTermMarks() {
		// TODO Auto-generated constructor stub
	}

	public void insertData(Statement stmt, String class_name, String student_name, String math1, String math2,
			String matht, String eng1, String eng2, String engt, String sho1, String sho2, String shot, String gen1,
			String gen2, String gent, String ag1, String ag2, String agt, String totalmarks, String term, String year,
			String date) {

		String query = "INSERT INTO thistermmarks(class_name,student_name,math1,math2,matht,eng1,eng2,engt,sho1,sho2,shot,gen1,gen2,gent,ag1,ag2,agt,totalmarks,term,year,date)VALUES('"
				+ class_name + "','" + student_name + "','" + math1 + "','" + math2 + "','" + matht + "','" + eng1
				+ "','" + eng2 + "','" + engt + "','" + sho1 + "','" + sho2 + "','" + shot + "','" + gen1 + "','" + gen2
				+ "','" + gent + "','" + ag1 + "','" + ag2 + "','" + agt + "','" + totalmarks + "','" + term + "','"
				+ year + "','" + date + "')";
		try {
			stmt.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}

	}
}
