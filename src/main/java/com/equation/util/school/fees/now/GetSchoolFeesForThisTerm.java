package com.equation.util.school.fees.now;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class GetSchoolFeesForThisTerm {

	Statement stm;
	ResultSet rs;

	public GetSchoolFeesForThisTerm(Statement stm, ResultSet rs) {
		this.rs = rs;
		this.stm = stm;
	}

	public double getSchoolFees(String schoolID) {
		double fees = 0;
		String query = "SELECT total FROM feestructure WHERE schoolID ='" + schoolID + "'";
		try {
			rs = stm.executeQuery(query);
			if (rs.last())
				fees = rs.getDouble(1);
			else
				fees = 0;
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return fees;
	}
}