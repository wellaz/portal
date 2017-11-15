package com.equation.school.classes.collection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class AllClasses {

	Statement stm;
	ResultSet rs;

	public AllClasses(Statement stm, ResultSet rs) {
		this.rs = rs;
		this.stm = stm;
	}

	public ArrayList<String> classesCollection(String schoolname) {
		ArrayList<String> array = new ArrayList<>();
		String query = "SELECT classname FROM classes,schools WHERE schools.schoolName = '" + schoolname
				+ "' OR schools.schoolID ='" + schoolname + "'";
		try {
			rs = stm.executeQuery(query);
			if (rs.next()) {
				do {
					String classname = rs.getString(1);
					array.add(classname);
				} while (rs.next());
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return array;
	}

	public String getClassIdFor(String classname) {
		String classid = null;
		String query = "SELECT classID FROM classes WHERE classname = '" + classname + "'";
		try {
			rs = stm.executeQuery(query);
			rs.next();
			classid = rs.getString(1);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return classid;
	}
}