package com.equation.school.details.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoSchoolsTable {
	Statement stm;

	public InsertDataIntoSchoolsTable(Statement stm) {
		this.stm = stm;
	}

	/**
	 * 
	 * @param schoolName
	 * @param type
	 * @param emisNumber
	 * @param deptCode
	 * @param stnCode
	 * @param responsibleAuthority
	 * @param boarding
	 * @param yearEstablished
	 * @param centreNumber
	 */
	public void insertData(String schoolName, String type, String schoolID, String deptCode, String stnCode,
			String responsibleAuthority, String boarding, String yearEstablished, String centreNumber) {

		String insertQuery = "INSERT INTO schools( schoolName,  type,  schoolID,  deptCode,  stnCode, responsibleAuthority,  boarding,  yearEstablished,  centreNumber)VALUES('"
				+ schoolName + "', '" + type + "',  '" + schoolID + "',  '" + deptCode + "',  '" + stnCode + "','"
				+ responsibleAuthority + "',  '" + boarding + "',  '" + yearEstablished + "',  '" + centreNumber + "')";
		try {
			stm.execute(insertQuery);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}