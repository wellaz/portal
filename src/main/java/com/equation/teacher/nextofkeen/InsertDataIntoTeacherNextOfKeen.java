package com.equation.teacher.nextofkeen;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoTeacherNextOfKeen {
	Statement stm;

	public InsertDataIntoTeacherNextOfKeen(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String ecNumber, String kFirstName, String kSurname, String kNationalID, String kCell,
			String kEmail, String kAdress, String kOther, String kRelationship) {
		String query = "INSERT INTO teachernextofkeen(ecNumber,kFirstName,kSurname,kNationalID,kCell,kEmail,kAdress,kOther,kRelationship)VALUES('"
				+ ecNumber + "','" + kFirstName + "','" + kSurname + "','" + kNationalID + "','" + kCell + "','"
				+ kEmail + "','" + kAdress + "','" + kOther + "',	'" + kRelationship + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}