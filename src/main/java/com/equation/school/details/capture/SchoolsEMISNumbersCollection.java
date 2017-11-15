package com.equation.school.details.capture;

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
public class SchoolsEMISNumbersCollection {
	ResultSet rs;
	Statement stm;

	public SchoolsEMISNumbersCollection(ResultSet rs, Statement stm) {
		this.rs = rs;
		this.stm = stm;
	}

	public ArrayList<Integer> schoolEMISNumbers() {
		ArrayList<Integer> collection = new ArrayList<>();
		String query = "SELECT schoolID FROM schools";
		try {
			rs = stm.executeQuery(query);
			if (rs.next()) {
				do {
					int emisnumber = rs.getInt(1);
					collection.add(emisnumber);
				} while (rs.next());
			} else {

			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return collection;
	}

	public boolean isFoundEmis(String emis) {
		List<String> emisnumbers = null;
		int arrsize = schoolEMISNumbers().size();
		String[] data = new String[arrsize];
		for (int i = 0; i < arrsize; i++)
			data[i] = Integer.toString(schoolEMISNumbers().get(i));

		emisnumbers = Arrays.asList(data);
		return emisnumbers.stream().anyMatch(t -> emis.equals(t));
	}
}
