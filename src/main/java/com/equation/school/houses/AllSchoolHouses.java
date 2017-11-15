package com.equation.school.houses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class AllSchoolHouses {
	Statement stm;
	ResultSet rs;

	public AllSchoolHouses(Statement stm, ResultSet rs) {
		this.stm = stm;
		this.rs = rs;
	}

	public ArrayList<String> allSchoolHouses(String schoolname) {
		ArrayList<String> array = new ArrayList<>();
		String query = "SELECT houseName FROM schoolhouses,schools WHERE schools.schoolName = '" + schoolname + "'";
		try {
			rs = stm.executeQuery(query);
			if (rs.next()) {
				do {
					String housename = rs.getString(1);
					array.add(housename);
				} while (rs.next());
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return array;
	}

	public ArrayList<String> allSchoolHousesByEmis(String emisNumber) {
		ArrayList<String> array = new ArrayList<>();
		String query = "SELECT houseName FROM schoolhouses,schools WHERE schools.schoolID = '" + emisNumber + "'";
		try {
			rs = stm.executeQuery(query);
			if (rs.next()) {
				do {
					String housename = rs.getString(1);
					array.add(housename);
				} while (rs.next());
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return array;
	}
}