package com.equation.accounts.sundryaccount.search;

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
public class SearchSundryByDate {
	ResultSet rs, rs1;
	Statement stm, stmt;
	private Table grid;

	public SearchSundryByDate(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public Window createContentPanel(String schoolID, String from, String to) {
		Window window = new Window("Search Results");
		window.setModal(false);
		window.setSizeFull();
		window.center();
		String query = "SELECT 	receiptInvoiceNumber,withdrawal,deposit,description,date,cashier,receivedfrom FROM sundry_acc,schools WHERE schools.schoolID = '"
				+ schoolID + "'  AND date >= '" + from + "' AND date <='" + to + "' ORDER BY date ASC";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow(), i = 0;
			if (rows > 0) {

				String query1 = "SELECT 	receiptInvoiceNumber,withdrawal,deposit,description,date,cashier,receivedfrom FROM sundry_acc,schools WHERE schools.schoolID = '"
						+ schoolID + "'  AND date >= '" + from + "' AND date <='" + to + "' ORDER BY date ASC";

				rs = stm.executeQuery(query1);

				grid = new Table();
				grid.addContainerProperty("Receipt Or Invoice Number", String.class, null);
				grid.addContainerProperty("Withdrawal", Double.class, null);
				grid.addContainerProperty("Deposit", Double.class, null);
				grid.addContainerProperty("Description", String.class, null);
				grid.addContainerProperty("Date", String.class, null);
				grid.addContainerProperty("Cashier", String.class, null);
				grid.addContainerProperty("Received From", String.class, null);
				grid.setSelectable(true);
				double totalDeposits = 0, totalwithdrawals = 0;
				while (rs.next()) {
					String receiptInvoiceNumber = rs.getString(1);
					double withdrawal = rs.getDouble(2);
					double deposit = rs.getDouble(3);
					String description = rs.getString(4);
					String date = rs.getString(5);
					String cashier = rs.getString(6);
					String receivedfrom = rs.getString(7);
					totalDeposits += deposit;
					totalwithdrawals += withdrawal;

					grid.addItem(new Object[] { receiptInvoiceNumber, withdrawal, deposit, description, date, cashier,
							receivedfrom }, new Integer(i));
					i++;
				}
				grid.setColumnCollapsingAllowed(true);
				grid.setFooterVisible(true);
				grid.setColumnFooter("Receipt Or Invoice Number", String.valueOf(grid.size() + " Records"));
				grid.setColumnFooter("Withdrawal", "Total $" + String.valueOf(totalwithdrawals));
				grid.setColumnFooter("Deposit", "Total $" + String.valueOf(totalDeposits));
				grid.setSizeFull();
				grid.setPageLength(grid.size());
				grid.addStyleName("students_gender_table");

				VerticalLayout layout = new VerticalLayout(new SundryControlButtons(window, grid), grid);
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