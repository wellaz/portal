/**
 *
 * @author Wellington
 */
package com.equation.equate.results.analysis.pergrade.passed;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Wellington
 *
 */
public class StudentsWhoPassedAtLeastOneArray {
	Statement stm, stmt;
	ResultSet rs, rs1;

	/**
	 * 
	 */
	public StudentsWhoPassedAtLeastOneArray(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public ArrayList<String> passedStudents(String classname, String term, String year) {
		ArrayList<String> data = new ArrayList<>();
		String query = "SELECT student_name FROM record_subjects_passed WHERE classname LIKE '" + classname + "%"
				+ "' AND term = '" + term + "' AND year = '" + year + "' GROUP BY student_name";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow();
			if (rows > 0) {
				String query1 = "SELECT student_name FROM record_subjects_passed WHERE classname LIKE '" + classname
						+ "%" + "' AND term = '" + term + "' AND year = '" + year + "'  GROUP BY student_name";
				rs = stm.executeQuery(query1);
				while (rs.next()) {
					data.add(rs.getString(1));
				}
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return data;

	}

}
