package com.equation.ancillary.details.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoAnscillaryContactDetails {

	Statement stm;

	public InsertDataIntoAnscillaryContactDetails(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String workerID, String phone, String cell, String email, String physicalAddress,
			String postalAddress, String other) {
		String query = "INSERT INTO ancillarycontactdetails(workerID,phone,cell,email,physicalAddress,postalAddress,other)VALUES(	'"
				+ workerID + "','" + phone + "','" + cell + "','" + email + "','" + physicalAddress + "','"
				+ postalAddress + "','" + other + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}