package com.equation.accounts.expenditure;

import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoExpenditureTable {
	Statement stm;

	public InsertDataIntoExpenditureTable(Statement stm) {
		this.stm = stm;
	}

	@SuppressWarnings("deprecation")
	public void insertData(String receiptNumber, String voucherNumber, double amount, String description,
			String supplier, String date, String time, String schoolID, String sourceAccount) {
		String query = "INSERT INTO expenditure(receiptNumber,voucherNumber,amount,description,supplier,date,time,schoolID,sourceAccount) VALUES('"
				+ receiptNumber + "','" + voucherNumber + "','" + amount + "','" + description + "','" + supplier
				+ "','" + date + "','" + time + "','" + schoolID + "','" + sourceAccount + "')";
		try {
			stm.execute(query);
		} catch (SQLException ex) {
			ex.printStackTrace();
			new Notification("Error", "Data was not saved successfully", Notification.TYPE_ERROR_MESSAGE, true)
					.show(Page.getCurrent());
		}
	}
}