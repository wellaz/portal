package com.equation.school.names.collection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class SchoolNames {

	Statement stm;
	ResultSet rs;

	public SchoolNames(Statement stm, ResultSet rs) {
		this.rs = rs;
		this.stm = stm;
	}

	public ArrayList<String> schoolCollection() {
		ArrayList<String> array = new ArrayList<>();
		String query = "SELECT schoolName FROM schools";
		try {
			rs = stm.executeQuery(query);
			if (rs.next()) {
				do {
					String schoolame = rs.getString(1);
					array.add(schoolame);
				} while (rs.next());
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return array;
	}
}