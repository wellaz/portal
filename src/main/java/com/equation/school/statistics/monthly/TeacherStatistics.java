package com.equation.school.statistics.monthly;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class TeacherStatistics {
	Statement stm, stmt;
	ResultSet rs, rs1;

	public TeacherStatistics(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public int getTeachersCount(String gender, String category, String status, String schoolID) {
		int value = 0;
		String query = "SELECT COUNT(ecNumber) FROM  teachers,schools WHERE teacher_category = '" + category
				+ "'AND gender = '" + gender + "' AND status <> '" + status + "' AND schools.schoolID = '" + schoolID
				+ "'";
		try {
			rs = stm.executeQuery(query);
			if (rs.next())
				value = rs.getInt(1);
			else
				value = 0;
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return value;
	}
}