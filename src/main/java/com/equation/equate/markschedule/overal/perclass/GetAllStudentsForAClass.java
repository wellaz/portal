package com.equation.equate.markschedule.overal.perclass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GetAllStudentsForAClass {
	Statement stm, stmt;
	ResultSet rs, rs1;

	public GetAllStudentsForAClass(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;

	}

	public ArrayList<String> getAllStudentForThisClass(String myClass, String term, String year) {
		ArrayList<String> data = new ArrayList<>();
		String query = "SELECT student_name FROM paper1markschedule WHERE class_name = '" + myClass + "'  AND term='"
				+ term + "' AND year = '" + year + "'  GROUP BY student_name";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int ros = rs1.getRow();
			if (ros > 0) {
				String query1 = "SELECT student_name FROM paper1markschedule WHERE class_name = '" + myClass
						+ "' AND term='" + term + "' AND year = '" + year + "'  GROUP BY student_name";
				rs = stm.executeQuery(query1);
				while (rs.next()) {
					String student_name = rs.getString(1);
					data.add(student_name);
				}
			} else {

			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return data;
	}
}
