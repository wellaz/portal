package com.equation.school.teachers.ecnumbers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class ECNumbersCollection {

	Statement stm;
	ResultSet rs;

	public ECNumbersCollection(Statement stm, ResultSet rs) {
		this.rs = rs;
		this.stm = stm;
	}

	public ArrayList<String> ecNumbers(String schoolname) {
		ArrayList<String> array = new ArrayList<>();
		String query = "SELECT ecNumber FROM teachers,schools WHERE schools.schoolID = '" + schoolname
				+ "' OR schools.schoolName = '" + schoolname + "'";
		try {
			rs = stm.executeQuery(query);
			if (rs.next()) {
				do {
					String ecNumber = rs.getString(1);
					array.add(ecNumber);
				} while (rs.next());
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return array;
	}
}