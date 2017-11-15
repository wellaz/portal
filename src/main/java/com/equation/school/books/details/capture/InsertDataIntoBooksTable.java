package com.equation.school.books.details.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoBooksTable {
	Statement stm;

	public InsertDataIntoBooksTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String schoolID, String title, String subject, String iSBN, String status, String quantity) {
		String query = "INSERT INTO books(schoolID,title,subject,iSBN,status,quantity)VALUES('" + schoolID + "','"
				+ title + "','" + subject + "','" + iSBN + "','" + status + "','" + quantity + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}