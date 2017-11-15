/**
 *
 * @author Wellington
 */
package com.equation.equate.settings.marks.allocation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Wellington
 *
 */
public class UpdateMarkAllocation {
	Statement stm, stmt;
	ResultSet rs, rs1;

	/**
	 * 
	 */
	public UpdateMarkAllocation(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.stm = stm;
		this.stmt = stmt;
		this.rs = rs;
		this.rs1 = rs1;
	}

	public void updateMarks(String p1raw, String p1contribution, String p2raw, String p2contribution, String subject,
			String term, String year, String date, String class_name) {
		String query = "UPDATE subject_marks SET p1raw = '" + p1raw + "',p1contribution = '" + p1contribution
				+ "',p2raw = '" + p2raw + "',p2contribution = '" + p2contribution + "' WHERE subject = '" + subject
				+ "' WHERE term = '" + term + "' AND year = '" + year + "' AND class_name='" + class_name + "'";
		try {
			String selectQuery = "SELECT * FROM subject_marks WHERE term = '" + term + "' AND year = '" + year
					+ "' AND subject = '" + subject + "' AND class_name = '" + class_name + "'";
			rs1 = stmt.executeQuery(selectQuery);
			if (rs1.next()) {
				stm.executeUpdate(query);
			} else {
				String insertQuery = "INSERT INTO subject_marks(subject,p1raw,p1contribution,p2raw,p2contribution,term,year,date,class_name)VALUES('"
						+ subject + "','" + p1raw + "','" + p1contribution + "','" + p2raw + "','" + p2contribution
						+ "','" + term + "','" + year + "','" + date + "','" + class_name + "')";
				stm.execute(insertQuery);
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}

}
