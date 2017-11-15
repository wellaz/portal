package com.equation.accounts.bankingregister.search;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("deprecation")
public class SearchBankingRegisterByDepositNumber {

	ResultSet rs, rs1;
	Statement stm, stmt;
	private Table grid;

	public SearchBankingRegisterByDepositNumber(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public Window createContentPanel(String schoolID, String depositnumber) {
		Window window = new Window("search Results");
		window.setModal(false);
		window.setSizeFull();
		window.center();
		String query = "SELECT amount,messagerID,messangerIDSign,senderID,senderIDSign,comments,date FROM banking_register,schools WHERE schools.schoolID = '"
				+ schoolID + "'  AND depositNumber = '" + depositnumber + "'";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow(), i = 0;
			if (rows > 0) {
				String query1 = "SELECT amount,messagerID,messangerIDSign,senderID,senderIDSign,comments,date FROM banking_register,schools WHERE schools.schoolID = '"
						+ schoolID + "'  AND depositNumber = '" + depositnumber + "'";
				rs = stm.executeQuery(query1);

				grid = new Table();
				grid.addContainerProperty("Amount", Double.class, null);
				grid.addContainerProperty("Messanger ID", String.class, null);
				grid.addContainerProperty("Messanger Sign", String.class, null);
				grid.addContainerProperty("Sender ID", String.class, null);
				grid.addContainerProperty("Sender Sign", String.class, null);
				grid.addContainerProperty("Comments", String.class, null);
				grid.addContainerProperty("Date", String.class, null);
				grid.setSelectable(true);
				double total = 0;
				while (rs.next()) {
					double amount = rs.getDouble(1);
					String messangerid = rs.getString(2);
					String messangername = rs.getString(3);
					String senderid = rs.getString(4);
					String sendername = rs.getString(5);
					String comments = rs.getString(6);
					String date = rs.getString(7);
					total += amount;

					grid.addItem(
							new Object[] { amount, messangerid, messangername, senderid, sendername, comments, date },
							new Integer(i));
					i++;
				}
				grid.setColumnCollapsingAllowed(true);
				grid.setFooterVisible(true);
				grid.setColumnFooter("Amount", "Total $");
				grid.setColumnFooter("Messanger ID", String.valueOf(total));
				grid.setSizeFull();
				grid.setPageLength(grid.size());
				grid.addStyleName("students_gender_table");
				VerticalLayout layout = new VerticalLayout(
						new BankingRegisterSearchResultsControlButtons(window, grid));
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