package com.equation.student.registration.ids.generate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class AutoIncrementStudentID {
	ResultSet rs;
	Statement stm;

	public AutoIncrementStudentID(ResultSet rs, Statement stm) {
		this.rs = rs;
		this.stm = stm;
	}

	public int generateNextValue(String lastTwoDigits) {
		int value = 0;
		String query = "SELECT studentID FROM students WHERE studentID LIKE '" + "_" + lastTwoDigits + "%" + "'";
		try {
			rs = stm.executeQuery(query);
			rs.last();
			int rows = rs.getRow();
			value = rows + 1;
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return value;
	}
}