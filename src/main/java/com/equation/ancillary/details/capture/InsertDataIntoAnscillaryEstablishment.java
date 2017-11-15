package com.equation.ancillary.details.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoAnscillaryEstablishment {
	Statement stm;

	public InsertDataIntoAnscillaryEstablishment(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String workerID, String jobDescription, double salary, String employmentType) {
		String insertQuery = "INSERT INTO ancillaryestablishment(workerID,jobDescription,salary,employmentType)VALUES('"
				+ workerID + "','" + jobDescription + "','" + salary + "','" + employmentType + "')";
		try {
			stm.execute(insertQuery);

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}