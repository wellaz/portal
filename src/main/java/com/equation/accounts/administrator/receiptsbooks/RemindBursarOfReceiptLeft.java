package com.equation.accounts.administrator.receiptsbooks;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

/**
 *
 * @author Wellington
 */
public class RemindBursarOfReceiptLeft {
	ResultSet rs, rs1;
	Statement stm, stmt;
	String schoolID;

	public RemindBursarOfReceiptLeft(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, String schoolID) {
		this.rs1 = rs1;
		this.rs = rs;
		this.stm = stm;
		this.stmt = stmt;
		this.schoolID = schoolID;
	}

	@SuppressWarnings("deprecation")
	public void scanForReceiptsLeft() {
		String query = "SELECT receiptNumber FROM feesreceived,schools WHERE schools.schoolID = '" + schoolID + "'";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow();
			if (rows > 0) {
				String query1 = "SELECT receiptNumber FROM feesreceived,schools WHERE schools.schoolID = '" + schoolID
						+ "'";
				rs = stm.executeQuery(query1);
				rs.last();
				int receiptNumber = rs.getInt(1);
				String query2 = "SELECT finalReceptNumber FROM receiptbooks,schools WHERE schools.schoolID = '"
						+ schoolID + "'";
				rs = stm.executeQuery(query2);
				rs.last();
				int finalReceiptNumber = rs.getInt(1);

				int receiptsLeft = finalReceiptNumber - receiptNumber;
				if (receiptsLeft <= 100) {
					new Notification("Warning",
							"<br/>There are " + receiptsLeft + " left."
									+ ".<br/><br/>You may need to inform the administrator to<br/>enter a new receipt book any time soon.",
							Notification.TYPE_TRAY_NOTIFICATION, true).show(Page.getCurrent());
				}
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}

}
