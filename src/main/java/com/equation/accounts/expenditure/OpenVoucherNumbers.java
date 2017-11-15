package com.equation.accounts.expenditure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class OpenVoucherNumbers {

	Statement stm, stmt;
	ResultSet rs, rs1;

	public OpenVoucherNumbers(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.stm = stm;
		this.stmt = stmt;
		this.rs = rs;
		this.rs1 = rs1;
	}

	public ArrayList<String> getOpenVouchers(String status, String schoolID) {
		ArrayList<String> array = new ArrayList<>();
		String query = "SELECT voucherNumber FROM voucherstable,schools WHERE status = '" + status
				+ "' AND schools.schoolID = '" + schoolID + "'";
		try {
			rs1 = stmt.executeQuery(query);
			if (rs1.next()) {
				do {
					int voucherNumber = rs1.getInt(1);
					array.add(Integer.toString(voucherNumber));
				} while (rs1.next());
			} else {

			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return array;
	}
}