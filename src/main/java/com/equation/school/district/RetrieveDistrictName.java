package com.equation.school.district;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class RetrieveDistrictName {
	ResultSet rs, rs1;
	Statement stm, stmt;

	public RetrieveDistrictName(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public String getDistrictName(String schoolID) {
		String name = null,
				query = "SELECT districtName FROM districts,schools WHERE schools.schoolID = '" + schoolID + "'";
		String query1 = "SELECT districtName FROM districts,schools WHERE schools.schoolID = '" + schoolID + "'";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow();
			if (rows > 0) {
				rs = stm.executeQuery(query1);
				rs.next();
				name = rs.getString(1);
			} else {
				name = "Not Found";
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return name;
	}
}