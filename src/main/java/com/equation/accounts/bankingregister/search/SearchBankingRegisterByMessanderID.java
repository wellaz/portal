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
public class SearchBankingRegisterByMessanderID {

	ResultSet rs, rs1;
	Statement stm, stmt;
	private Table grid;

	public SearchBankingRegisterByMessanderID(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public Window createContentPanel(String schoolID, String messangerid) {
		Window window = new Window("search Results");
		window.setModal(false);
		window.setSizeFull();
		window.center();
		String query = "SELECT depositNumber,amount,messagerID,messangerIDSign,senderID,senderIDSign,comments,date FROM banking_register,schools WHERE schools.schoolID = '"
				+ schoolID + "'  AND messagerID = '" + messangerid + "'";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow(), i = 0;
			if (rows > 0) {

				String query1 = "SELECT depositNumber,amount,messagerID,messangerIDSign,senderID,senderIDSign,comments,date FROM banking_register,schools WHERE schools.schoolID = '"
						+ schoolID + "'  AND messagerID = '" + messangerid + "'";
				rs = stm.executeQuery(query1);

				grid = new Table();
				grid.addContainerProperty("Deposit Number", Double.class, null);
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
					String depositNumber = rs.getString(1);
					double amount = rs.getDouble(2);
					String messangeridd = rs.getString(3);
					String messangername = rs.getString(4);
					String senderid = rs.getString(5);
					String sendername = rs.getString(6);
					String comments = rs.getString(7);
					String date = rs.getString(8);
					total += amount;

					grid.addItem(new Object[] { depositNumber, amount, messangeridd, messangername, senderid,
							sendername, comments, date }, new Integer(i));
					i++;
				}
				grid.setColumnCollapsingAllowed(true);
				grid.setFooterVisible(true);
				grid.setColumnFooter("Amount", "Total $");
				grid.setColumnFooter("Messanger ID", String.valueOf(total));
				grid.setColumnFooter("Deposit Number", String.valueOf(grid.size() + " Records"));
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
