package com.equation.accounts.expenditure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class PrincipalVoucherAmout {

	ResultSet rs;
	Statement stm;

	public PrincipalVoucherAmout(ResultSet rs, Statement stm) {
		this.rs = rs;
		this.stm = stm;
	}

	public double getPrincipalVoucherAmount(String voucherNumber, String schoolID) {
		double value = 0;
		String query = "SELECT amount FROM voucherstable,schools WHERE schools.schoolID = '" + schoolID
				+ "' AND voucherNumber = '" + voucherNumber + "'";
		try {
			rs = stm.executeQuery(query);
			if (rs.next()) {
				value = rs.getDouble(1);
			} else {
				value = 0;
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return value;
	}
}