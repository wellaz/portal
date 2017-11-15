package com.equation.accounts.vouchers.capture;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

/**
 *
 * @author Wellington
 */
public class IncrementVoucherNumber {
	ResultSet rs, rs1;
	Statement stm, stmt;

	public IncrementVoucherNumber(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.stm = stm;
		this.stmt = stmt;
		this.rs = rs;
		this.rs1 = rs1;
	}

	public int returnLastVoucherNumber() {
		String Query = "SELECT 	voucherNumber FROM voucherstable";
		int finalReceiptNumber = 0;
		try {
			rs = stm.executeQuery(Query);
			rs.last();
			finalReceiptNumber = rs.getRow() + 1;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return finalReceiptNumber;

	}

	@SuppressWarnings("deprecation")
	public int nextVoucherNumberr() {
		String selectQuery = "SELECT voucherNumber FROM voucherstable";
		int voucherNumber = 0;
		try {
			rs1 = stmt.executeQuery(selectQuery);
			if (rs1.last()) {
				voucherNumber = rs1.getInt(1) + 1;
				int finalReceiptNumber = returnLastVoucherNumber();
				if (voucherNumber <= finalReceiptNumber) {

				} else {
					voucherNumber = 0;
					new Notification("Error",
							"Current receipt book exhausted.<br/>Elevate this message to the Administrator!",
							Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
				}
			} else {
				String selectFirstReceiptQuery = "SELECT firstVoucherNumber FROM vouchernumbers";
				rs = stm.executeQuery(selectFirstReceiptQuery);
				if (rs.last()) {
					voucherNumber = rs.getInt(1);

				} else {
					new Notification("Error",
							"No Receipt Book found in the system.<br/>Please notify the ADMINISTRATOR to load a new Receipt Book!",
							Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
				}
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return voucherNumber;
	}
}
