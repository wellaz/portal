package com.equation.school.current.emis.collect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class GetCurrentSchoolEMISNumber {

	ResultSet rs;
	Statement stm;

	public GetCurrentSchoolEMISNumber(ResultSet rs, Statement stm) {
		this.rs = rs;
		this.stm = stm;
	}

	public String currentSchoolEMISNumber() {
		String emis = null;
		String query = "SELECT schoolID FROM schools";
		try {
			rs = stm.executeQuery(query);
			if (rs.next())
				emis = rs.getString(1);
			else
				emis = "School EMIS DO NOT HONOR";
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return emis;
	}
}