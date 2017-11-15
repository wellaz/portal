package com.equation.student.ids.collection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Wellington
 */
public class AllStudentIDsBin {
	ResultSet rs;
	Statement stm;
	String schoolID;

	public AllStudentIDsBin(ResultSet rs, Statement stm, String schoolID) {
		this.rs = rs;
		this.stm = stm;
		this.schoolID = schoolID;
	}

	public ArrayList<String> allStudentIDs() {
		ArrayList<String> ids = new ArrayList<>();
		String query = "SELECT studentID FROM students,schools WHERE schools.schoolID = '" + schoolID + "'";
		try {
			rs = stm.executeQuery(query);
			if (rs.next()) {
				do {
					String studentid = rs.getString(1);
					ids.add(studentid);
				} while (rs.next());
			} else {

			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return ids;
	}

	public boolean isFoundStudentID(String studentid) {
		List<String> ecnumbers = null;
		int arrsize = allStudentIDs().size();
		String[] data = new String[arrsize];
		for (int i = 0; i < arrsize; i++)
			data[i] = allStudentIDs().get(i);

		ecnumbers = Arrays.asList(data);
		return ecnumbers.stream().anyMatch(t -> studentid.equals(t));
	}
}