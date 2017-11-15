/**
 *
 * @author Wellington
 */
package com.equation.equate.settings.passmark;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Wellington
 *
 */
public class InsertPassMark {
	ResultSet rs, rs1;
	Statement stm, stmt;

	/**
	 * 
	 */
	public InsertPassMark(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public void insertData(String mark, int id, String term, String year, String date) {
		String query = "INSERT INTO pass_mark(mark,term,year,date) VALUES('" + mark + "','" + term + "','" + year
				+ "','" + date + "')";
		String selectQuery = "SELECT mark FROM pass_mark";
		try {
			rs1 = stmt.executeQuery(selectQuery);
			rs1.last();
			int rows = rs1.getRow();
			if (rows > 0) {
				String updateQuery = "UPDATE pass_mark SET mark = '" + mark + "' WHERE record_id = '" + id + "'";
				stm.executeUpdate(updateQuery);
			} else
				stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}

}
