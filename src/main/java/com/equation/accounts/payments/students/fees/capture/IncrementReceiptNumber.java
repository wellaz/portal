package com.equation.accounts.payments.students.fees.capture;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class IncrementReceiptNumber {
	ResultSet rs, rs1;
	Statement stm, stmt;

	public IncrementReceiptNumber(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.stm = stm;
		this.stmt = stmt;
		this.rs = rs;
		this.rs1 = rs1;
	}

	private int returnLastReceiptNumber() {
		String Query = "SELECT 	finalReceptNumber FROM receiptbooks";
		int finalReceiptNumber = 0;
		try {
			rs = stm.executeQuery(Query);
			rs.last();
			finalReceiptNumber = rs.getInt(1);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return finalReceiptNumber;

	}

	public int nextReceiptNumberr() {
		String selectQuery = "SELECT receiptNumber FROM feesreceived";
		int receiptNumber = 0;
		try {
			rs1 = stmt.executeQuery(selectQuery);
			if (rs1.last()) {
				receiptNumber = rs1.getInt(1) + 1;
				int finalReceiptNumber = returnLastReceiptNumber();
				if (receiptNumber > finalReceiptNumber) {
					receiptNumber = 0;
				}
			} else {
				String selectFirstReceiptQuery = "SELECT firstReceiptNumber FROM receiptbooks";
				rs = stm.executeQuery(selectFirstReceiptQuery);
				if (rs.last()) {
					receiptNumber = rs.getInt(1);

				} else {
					receiptNumber = 0;
				}
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return receiptNumber;
	}
}