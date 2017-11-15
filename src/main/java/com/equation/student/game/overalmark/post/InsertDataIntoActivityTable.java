package com.equation.student.game.overalmark.post;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoActivityTable {
	Statement stm;

	public InsertDataIntoActivityTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String firstName, String subject, String grade, double percentage) {
		String query = "INSERT INTO studentactivities(firstName,subject,grade,percentage)VALUES('" + firstName + "', '"
				+ subject + "', '" + grade + "', '" + percentage + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}