package com.equation.student.details.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoStudentsTable {
	Statement stm;

	public InsertDataIntoStudentsTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String studentID, String sFirstName, String sSurname, String sBirthDate, String sBirthEntry,
			int classID, String disability, String sgender, String boarder, String house, String religion,
			String pNationalID, String cocurrucular, String status) {
		String insertQuery = "INSERT INTO students(studentID,sFirstName,sSurname,sBirthDate,sBirthEntry,classID,disability,sgender,boarder,house,religion,pNationalID,cocurrucular,status)VALUES('"
				+ studentID + "','" + sFirstName + "','" + sSurname + "','" + sBirthDate + "','" + sBirthEntry + "','"
				+ classID + "','" + disability + "','" + sgender + "','" + boarder + "','" + house + "','" + religion
				+ "','" + pNationalID + "','" + cocurrucular + "','" + status + "')";
		try {
			stm.execute(insertQuery);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}