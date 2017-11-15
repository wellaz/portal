package com.equation.accounts.expenditure.search;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.v7.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("deprecation")
public class SearchExpenditureByReceiptNumber {
	ResultSet rs, rs1;
	Statement stm, stmt;
	private Table grid;

	public SearchExpenditureByReceiptNumber(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public Window createContentPanel(String schoolID, String receiptNumber) {
		Window window = new Window("search Results");
		window.setModal(false);
		window.setSizeFull();
		window.center();
		String query = "SELECT receiptNumber,voucherNumber,amount,description,supplier,date,time FROM expenditure,schools WHERE schools.schoolID = '"
				+ schoolID + "'  AND receiptNumber = '" + receiptNumber + "' OR voucherNumber = '" + receiptNumber
				+ "'";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow(), i = 0;
			if (rows > 0) {

				String query1 = "SELECT receiptNumber,voucherNumber,amount,description,supplier,date,time FROM expenditure,schools WHERE schools.schoolID = '"
						+ schoolID + "'  AND receiptNumber = '" + receiptNumber + "' OR voucherNumber = '"
						+ receiptNumber + "'";

				rs = stm.executeQuery(query1);

				grid = new Table();
				grid.addContainerProperty("Receipt Number", String.class, null);
				grid.addContainerProperty("Voucher Number", String.class, null);
				grid.addContainerProperty("Amount", Double.class, null);
				grid.addContainerProperty("Description", String.class, null);
				grid.addContainerProperty("Supplier", String.class, null);
				grid.addContainerProperty("Date", String.class, null);
				grid.setSelectable(true);
				double totalExpenses = 0;
				while (rs.next()) {
					String receiptNumberr = rs.getString(1);
					String voucherNumber = rs.getString(2);
					double amount = rs.getDouble(3);
					String description = rs.getString(4);
					String supplier = rs.getString(5);
					String date = rs.getString(6) + " " + rs.getString(7);
					totalExpenses += amount;

					grid.addItem(new Object[] { receiptNumberr, voucherNumber, amount, description, supplier, date },
							new Integer(i));
					i++;
				}
				grid.setColumnCollapsingAllowed(true);
				grid.setFooterVisible(true);
				grid.setColumnFooter("Receipt Number", String.valueOf(grid.size() + " Records"));
				grid.setColumnFooter("Amount", "Total Amount $" + String.valueOf(totalExpenses));
				grid.setSizeFull();
				grid.setPageLength(grid.size());
				grid.addStyleName("students_gender_table");

				VerticalLayout layout = new VerticalLayout(new ExpenditureResultsControls(window, grid), grid);
				layout.setSpacing(true);
				window.setContent(layout);
			} else {
				new Notification("Information", "No search Results", Notification.TYPE_WARNING_MESSAGE, true)
						.show(Page.getCurrent());
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		return window;
	}

}
