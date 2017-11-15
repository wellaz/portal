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
public class PassMark {
	ResultSet rs, rs1;
	Statement stm, stmt;

	public PassMark(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public int getPassMark() {
		int m = 0;
		String query = "SELECT mark FROM pass_mark";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow();
			if (rows > 0) {
				String query1 = "SELECT mark FROM pass_mark";
				rs = stm.executeQuery(query1);
				rs.next();
				m = rs.getInt(1);
			} else
				m = 0;

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return m;
	}

}
