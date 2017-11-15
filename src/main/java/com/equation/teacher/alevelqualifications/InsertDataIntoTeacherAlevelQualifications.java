package com.equation.teacher.alevelqualifications;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoTeacherAlevelQualifications {
	Statement stm;

	public InsertDataIntoTeacherAlevelQualifications(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String ecNumber, String subject, String symbol, String school, String year) {
		String query = "INSERT INTO alevelteacheracademicqualifications(ecNumber,subject,symbol,school,year)VALUES('" + ecNumber + "','" + subject + "','"
				+ symbol + "'	,'" + school + "','" + year + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}