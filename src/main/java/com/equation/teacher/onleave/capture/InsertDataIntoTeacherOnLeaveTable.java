package com.equation.teacher.onleave.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoTeacherOnLeaveTable {
	Statement stm;

	public InsertDataIntoTeacherOnLeaveTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(int schoolID, String ecNumber, String leaveType, String fromDate, String toDate,
			String replacement) {
		String insertQuery = "INSERT INTO teachersonleave(schoolID,ecNumber,leaveType,fromDate,toDate,replacement)VALUES('"
				+ schoolID + "',	'" + ecNumber + "',	'" + leaveType + "','" + fromDate + "','" + toDate + "','"
				+ replacement + "')";
		try {
			stm.execute(insertQuery);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}