package com.equation.school.classes.capture;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class AllSchoolClasses {

	ResultSet rs;
	Statement stm;
	String schoolID;

	public AllSchoolClasses(ResultSet rs, Statement stm, String schoolID) {
		this.rs = rs;
		this.stm = stm;
		this.schoolID = schoolID;
	}

	public ArrayList<String> allClasses() {
		ArrayList<String> classes = new ArrayList<>();
		String query = "SELECT classname FROM classes WHERE schoolID = '" + schoolID + "'";
		try {
			rs = stm.executeQuery(query);
			if (rs.next()) {
				do {
					String schoolclass = rs.getString(1);
					classes.add(schoolclass);
				} while (rs.next());
			} else {

			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return classes;
	}

	public int getClassID(String classname) {
		String selectQuery = "SELECT classID FROM classes WHERE classname = '" + classname + "' AND schoolID = '"
				+ schoolID + "'";
		int classID = 0;
		try {
			rs = stm.executeQuery(selectQuery);
			rs.next();
			classID = rs.getInt(1);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return classID;
	}
}