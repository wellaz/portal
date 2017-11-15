package com.equation.teacher.professionalqualifications;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoTeacherProfessionalQualification {
	Statement stm;

	public InsertDataIntoTeacherProfessionalQualification(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String ecNumber, String programme, String specialisation, String universityorcollege,
			String profficiency, String year) {
		String query = "INSERT INTO teacherprofessionqualifications(ecNumber,programme,specialisation,universityorcollege,profficiency,year)VALUES('"
				+ ecNumber + "','" + programme + "','" + specialisation + "','" + universityorcollege + "','"
				+ profficiency + "'	,'" + year + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}