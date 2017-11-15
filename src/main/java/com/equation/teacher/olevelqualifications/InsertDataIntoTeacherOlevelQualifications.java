package com.equation.teacher.olevelqualifications;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoTeacherOlevelQualifications {
	Statement stm;

	public InsertDataIntoTeacherOlevelQualifications(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String ecNumber, String subject, String symbol, String school, String year) {
		String query = "INSERT INTO olevelteacheracademicqualifications(ecNumber,subject,symbol,school,year)VALUES('"
				+ ecNumber + "','" + subject + "','" + symbol + "','" + school + "','" + year + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}