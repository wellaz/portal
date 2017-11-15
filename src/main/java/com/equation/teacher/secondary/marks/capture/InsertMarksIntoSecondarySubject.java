package com.equation.teacher.secondary.marks.capture;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertMarksIntoSecondarySubject {
	Statement stm;

	public InsertMarksIntoSecondarySubject(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String tablename, String studentID, String paper1, String paper2, String term, String year) {
		String query = "INSERT INTO " + tablename + "(studentID,paper1,paper2,term,year)VALUES('" + studentID + "','"
				+ paper1 + "','" + paper2 + "','" + term + "','" + year + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}
