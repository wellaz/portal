package com.equation.school.statistics.monthly;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class TotalPrimaryStudentsPerClass {
	Statement stm, stmt;
	ResultSet rs, rs1;
	String schoolID;

	public TotalPrimaryStudentsPerClass(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String schoolID) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.schoolID = schoolID;
	}

	public ArrayList<Integer> getOneToSevenTotals(ArrayList<String> classes, String gender, String status) {
		ArrayList<Integer> totals = new ArrayList<>();
		int total = 0;
		for (String grade : classes) {
			String query = "SELECT COUNT(studentID) FROM students,classes,schools WHERE classname LIKE '" + grade + "%"
					+ "' AND sgender = '" + gender + "' AND classes.schoolID = '" + schoolID
					+ "' AND students.status = '" + status + "'";
			try {
				rs = stm.executeQuery(query);
				if (rs.next())
					total = rs.getInt(1);
				else
					total = 0;
				totals.add(total);
			} catch (SQLException ee) {
				ee.printStackTrace();
			}

		}

		return totals;
	}
}