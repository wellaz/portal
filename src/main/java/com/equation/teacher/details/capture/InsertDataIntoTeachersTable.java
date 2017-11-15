package com.equation.teacher.details.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoTeachersTable {
	Statement stm;

	public InsertDataIntoTeachersTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String firstName, String surname, String gender, String dateOfBirth, String ecNumber,
			String nationalID, String nationality, String dOJS, String maritalStatus, String mainSubjects,
			String middleName, String otherName, String dateOfJoiningSchool, String teacher_category, String delegation,
			String status) {
		String insertQuery = "INSERT INTO teachers(firstName,surname,gender,dateOfBirth,ecNumber,nationalID,nationality,dOJS,maritalStatus,mainSubjects,middleName,otherName,dateOfJoiningSchool,teacher_category,delegation,status)VALUES('"
				+ firstName + "','" + surname + "','" + gender + "','" + dateOfBirth + "','" + ecNumber + "','"
				+ nationalID + "','" + nationality + "','" + dOJS + "','" + maritalStatus + "','" + mainSubjects + "','"
				+ middleName + "','" + otherName + "','" + dateOfJoiningSchool + "','" + teacher_category + "','"
				+ delegation + "','" + status + "')";
		try {
			stm.execute(insertQuery);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}