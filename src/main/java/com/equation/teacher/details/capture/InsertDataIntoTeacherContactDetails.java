package com.equation.teacher.details.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoTeacherContactDetails {
	Statement stm;

	public InsertDataIntoTeacherContactDetails(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String ecNumber, String phone, String cell, String email, String other,
			String physicalAddress, String postalAddress) {
		String query = "INSERT INTO teachercontactdetails(ecNumber,phone,cell,email,other,physicalAddress,postalAddress)VALUES('"
				+ ecNumber + "', '" + phone + "', '" + cell + "', '" + email + "', '" + other + "','" + physicalAddress
				+ "', '" + postalAddress + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}