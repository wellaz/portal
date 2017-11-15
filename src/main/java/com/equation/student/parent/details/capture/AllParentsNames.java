package com.equation.student.parent.details.capture;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class AllParentsNames {
	ResultSet rs;
	Statement stm;

	public AllParentsNames(ResultSet rs, Statement stm) {
		this.rs = rs;
		this.stm = stm;
	}

	public ArrayList<String> parentNames() {
		ArrayList<String> names = new ArrayList<>();
		String query = "SELECT pFirstName,pSurname FROM parents";
		try {
			rs = stm.executeQuery(query);
			if (rs.next()) {
				do {
					String parentName = rs.getString(1) + " " + rs.getString(2);
					names.add(parentName);
				} while (rs.next());
			} else {

			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return names;
	}

	public int getParentID(String parentname) {
		int parentID = 0;
		String[] value = parentname.split(" ");
		String firstname = value[0];
		String surname = value[1];
		String selectQuery = "SELECT parentID FROM parents WHERE pFirstName = '" + firstname + "'AND	pSurname='"
				+ surname + "'";
		try {
			rs = stm.executeQuery(selectQuery);
			rs.next();
			parentID = rs.getInt(1);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return parentID;
	}
}