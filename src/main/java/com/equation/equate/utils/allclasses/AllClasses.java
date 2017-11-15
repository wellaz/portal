package com.equation.equate.utils.allclasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AllClasses {
	Statement stm;
	ResultSet rs;

	public AllClasses(Statement stm, ResultSet rs) {
		this.stm = stm;
		this.rs = rs;
	}

	public ArrayList<String> allClasses() {
		ArrayList<String> data = new ArrayList<>();
		String query = "SELECT class_name FROM classes";
		try {
			rs = stm.executeQuery(query);
			while (rs.next()) {
				data.add(rs.getString(1));
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return data;
	}

}
