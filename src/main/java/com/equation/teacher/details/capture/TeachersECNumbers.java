package com.equation.teacher.details.capture;

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
public class TeachersECNumbers {
	ResultSet rs;
	Statement stm;

	public TeachersECNumbers(ResultSet rs, Statement stm) {
		this.rs = rs;
		this.stm = stm;
	}

	public ArrayList<String> teacherNames() {
		ArrayList<String> data = new ArrayList<>();
		String query = "SELECT firstName,surname FROM teachers";
		try {
			rs = stm.executeQuery(query);
			if (rs.next()) {
				do {
					data.add(rs.getString(1) + " " + rs.getString(2));
				} while (rs.next());

			} else {

			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return data;
	}

	public ArrayList<String> ecNumbers() {
		ArrayList<String> ecnumbers = new ArrayList<>();
		String query = "SELECT ecNumber FROM teachers";
		try {
			rs = stm.executeQuery(query);
			if (rs.next()) {
				do {
					String ecnumber = rs.getString(1);
					ecnumbers.add(ecnumber);
				} while (rs.next());

			} else {

			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return ecnumbers;
	}

	public String getECNumberForThis(String name) {
		String data = null;
		String query = "SELECT ecNumber FROM teachers WHERE firstName='" + name.split(" ")[0] + "' AND surname = '"
				+ name.split(" ")[1] + "'";
		try {
			rs = stm.executeQuery(query);
			if (rs.next())
				data = rs.getString(1);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return data;

	}

	public boolean isFoundECNumber(String emis) {
		List<String> ecnumbers = null;
		int arrsize = ecNumbers().size();
		String[] data = new String[arrsize];
		for (int i = 0; i < arrsize; i++)
			data[i] = ecNumbers().get(i);

		ecnumbers = Arrays.asList(data);
		return ecnumbers.stream().anyMatch(t -> emis.equals(t));
	}
}