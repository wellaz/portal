package com.equation.accounts.expenditure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class SingleVoucherExpenses {
	ResultSet rs;
	Statement stm;

	public SingleVoucherExpenses(ResultSet rs, Statement stm) {
		this.rs = rs;
		this.stm = stm;
	}

	public double getSingleVoucherExpenses(String voucherNumber, String schoolID) {
		double value = 0;
		String query = "SELECT SUM(amount) FROM expenditure,schools WHERE schools.schoolID = '" + schoolID
				+ "' AND voucherNumber = '" + voucherNumber + "' GROUP BY voucherNumber";
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