/**
 *
 * @author Wellington
 */
package com.equation.equate.utils.file.receiver;

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
public class ScanForDuplicates {
	ResultSet rs, rs1;
	Statement stm, stmt;
	String term, year;

	/**
	 * 
	 */
	public ScanForDuplicates(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, String term, String year) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.term = term;
		this.year = year;
		// allStudents();
	}

	public ArrayList<String> allStudents() {
		ArrayList<String> data = new ArrayList<>();
		String searchQuery = "SELECT student_name FROM paper1markschedule WHERE term = '" + term + "' AND year = '"
				+ year + "' GROUP BY student_name ";
		try {
			rs1 = stmt.executeQuery(searchQuery);
			rs1.last();
			if (rs1.getRow() > 0) {
				String searchQuery1 = "SELECT student_name FROM paper1markschedule WHERE term = '" + term
						+ "' AND year = '" + year + "' GROUP BY student_name  ";
				rs = stm.executeQuery(searchQuery1);
				while (rs.next()) {
					data.add(rs.getString(1));
				}
			} else {
				data.add("nnnot fffoudnn");
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		return data;
	}

	public boolean isStudentPosted(String student_name, ArrayList<String> data) {
		ArrayList<String> array = data;
		List<String> imgTypes = null;
		int arrsize = array.size();
		String[] dat = new String[arrsize];
		for (int i = 0; i < arrsize; i++)
			dat[i] = array.get(i);
		imgTypes = Arrays.asList(dat);
		return imgTypes.stream().anyMatch(t -> student_name.equals(t));

	}

}
