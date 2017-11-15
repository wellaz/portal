package com.equation.school.vehicles.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoSchoolVehicles {
	Statement stm;

	public InsertDataIntoSchoolVehicles(Statement stm) {
		this.stm = stm;
	}

	public void insertData(int schoolID, String type, String model, String regNumber, String status) {
		String insertQuery = "INSERT INTO vehicles(schoolID,type,model,regNumber,status)VALUES('" + schoolID + "','"
				+ type + "','" + model + "','" + regNumber + "','" + status + "')";
		try {
			stm.execute(insertQuery);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}