package com.equation.school.details.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoSchoolContactDetails {
	Statement stm;

	public InsertDataIntoSchoolContactDetails(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String phone, String cell, String email, String website, String physicalAddress,
			String postalAddress, String other, String schoolID) {
		String insertQuery = "INSERT INTO schoolcontactdetails(phone,cell,email,website,physicalAddress,postalAddress,other,schoolID)VALUES('"
				+ phone + "','" + cell + "','" + email + "','" + website + "','" + physicalAddress + "','"
				+ postalAddress + "','" + other + "','" + schoolID + "')";
		try {
			stm.execute(insertQuery);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}