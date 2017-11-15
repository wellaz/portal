/**
 *
 * @author Wellington
 */
package com.equation.equate.results.analysis.pergrade.passed;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Wellington
 *
 */
public class AllStudentsPerGrade {
	Statement stm, stmt;
	ResultSet rs, rs1;

	/**
	 * 
	 */
	public AllStudentsPerGrade(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public ArrayList<String> gradeStudents(String classname, String term, String year) {
		ArrayList<String> data = new ArrayList<>();
		String query = "SELECT student_name FROM thistermmarks WHERE class_name LIKE '" + classname + "%"
				+ "' AND term= '" + term + "' AND year = '" + year + "' GROUP BY student_name";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow();
			if (rows > 0) {
				String query1 = "SELECT student_name FROM thistermmarks WHERE class_name LIKE '" + classname + "%"
						+ "' AND term= '" + term + "' AND year = '" + year + "'  GROUP BY student_name";
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

	public boolean isValidStudentNumber(String student_name, String class_name, String term, String year) {
		ArrayList<String> array = gradeStudents(class_name, term, year);
		List<String> imgTypes = null;
		int arrsize = array.size();
		String[] dat = new String[arrsize];
		for (int i = 0; i < arrsize; i++)
			dat[i] = array.get(i);

		imgTypes = Arrays.asList(dat);
		return imgTypes.stream().anyMatch(t -> student_name.equals(t));
	}

}
