package com.equation.teacher.current.classname;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class TeacherCurrentClass {

	ResultSet rs, rs1;
	Statement stm, stmt;

	public TeacherCurrentClass(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public String className(String ecNumber, String schoolID) {
		String currentClass = null;
		String query = "SELECT classname FROM classes WHERE ecNumber = '" + ecNumber + "' AND schoolID = '" + schoolID
				+ "' ";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow();
			if (rows > 0) {
				String query1 = "SELECT classname FROM classes WHERE ecNumber = '" + ecNumber + "' AND schoolID = '"
						+ schoolID + "' ";
				rs = stm.executeQuery(query1);
				rs.next();
				currentClass = rs.getString(1);
			} else {
				currentClass = "";
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return currentClass;

	}

}
