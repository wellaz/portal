/**
 *
 * @author Wellington
 */
package com.equation.equate.rawmarks;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Wellington
 *
 */
public class GetStudentRawMark {
	Statement stm;
	ResultSet rs;

	public GetStudentRawMark(Statement stm, ResultSet rs) {
		this.rs = rs;
		this.stm = stm;
	}

	public int getRawMark(String tablename, String student_name, String class_name, String subject,String term,String year) {
		int value = 0;
		String query = "SELECT raw_mark FROM " + tablename + " WHERE student_name = '" + student_name
				+ "' AND class_name = '" + class_name + "' AND subject = '" + subject + "' AND term = '" + term
				+ "' AND year ='" + year + "'";
		try {
			rs = stm.executeQuery(query);
			rs.next();
			value = rs.getInt(1);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return value;
	}
}
