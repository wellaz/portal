package com.equation.ancillary.details.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoAncillaryTable {
	Statement stm;

	public InsertDataIntoAncillaryTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String firstName, String surname, String gender, String dob, String nationalID,
			String nationality, String dojs, String qualification, String maritalStatus) {
		String query = "INSERT INTO acillary(workerID,firstName,surname,gender,dob,nationalID,nationality,dojs,qualification,maritalStatus)VALUES('"
				+ firstName + "','" + surname + "','" + gender + "','" + dob + "','" + nationalID + "','" + nationality
				+ "','" + dojs + "','" + qualification + "','" + maritalStatus + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}