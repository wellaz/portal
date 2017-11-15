package com.equation.accounts.expenditure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class VoucherNumbersFilter {
	ResultSet rs;
	Statement stm;

	public VoucherNumbersFilter(ResultSet rs, Statement stm) {
		this.rs = rs;
		this.stm = stm;
	}

	public ArrayList<Double> voucherNumbersCollection() {
		ArrayList<Double> list = new ArrayList<>();

		String query = "SELECT SUM(amount) FROM expenditure GROUP BY voucherNumber ";
		try {
			rs = stm.executeQuery(query);
			if (rs.next()) {
				do {
					list.add(rs.getDouble(1));
				} while (rs.next());
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return list;
	}
}