package com.equation.accounts.cashreport.search;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.accounts.bankingregister.search.BankingRegisterSearchResultsControlButtons;
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
public class SearchCashReportByDate {

	ResultSet rs, rs1;
	Statement stm, stmt;
	private Table grid;

	public SearchCashReportByDate(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public Window createContentPanel(String schoolID, String from, String to) {
		Window window = new Window("search Results");
		window.setModal(false);
		window.setSizeFull();
		window.center();
		String query = "SELECT openingBalance,cashorchequeReceived,firstReceiptNumber,lastReceiptNumber,cashBanked,preparedBy,approvedBy,date FROM cash_report,schools WHERE schools.schoolID = '"
				+ schoolID + "'   AND date >= '" + from + "' AND date <='" + to + "' ORDER BY date ASC";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow(), i = 0;
			if (rows > 0) {

				String query1 = "SELECT openingBalance,cashorchequeReceived,firstReceiptNumber,lastReceiptNumber,cashBanked,preparedBy,approvedBy,date FROM cash_report,schools WHERE schools.schoolID = '"
						+ schoolID + "'   AND date >= '" + from + "' AND date <='" + to + "' ORDER BY date ASC";

				rs = stm.executeQuery(query1);

				grid = new Table();
				grid.addContainerProperty("Opening Balance", Double.class, null);
				grid.addContainerProperty("Cash Or Cheque Received", Double.class, null);
				grid.addContainerProperty("First Receipt Number", String.class, null);
				grid.addContainerProperty("Last Receipt Number", String.class, null);
				grid.addContainerProperty("Cash Banked", Double.class, null);
				grid.addContainerProperty("Prepared By", String.class, null);
				grid.addContainerProperty("Approved by", String.class, null);
				grid.addContainerProperty("Date", String.class, null);
				grid.setSelectable(true);
				while (rs.next()) {
					double openingBalance = rs.getDouble(1);
					double cashorchequeReceived = rs.getDouble(2);
					String firstReceiptNumber = rs.getString(3);
					String lastReceiptNumber = rs.getString(4);
					double cashBanked = rs.getDouble(5);
					String preparedBy = rs.getString(6);
					String approvedBy = rs.getString(7);
					String date = rs.getString(8);

					grid.addItem(new Object[] { openingBalance, cashorchequeReceived, firstReceiptNumber,
							lastReceiptNumber, cashBanked, preparedBy, approvedBy, date }, new Integer(i));
					i++;
				}
				grid.setColumnCollapsingAllowed(true);
				grid.setFooterVisible(true);
				grid.setColumnFooter("Opening Balance", String.valueOf(grid.size() + " Records"));
				grid.setSizeFull();
				grid.setPageLength(grid.size());
				grid.addStyleName("students_gender_table");

				VerticalLayout layout = new VerticalLayout(new BankingRegisterSearchResultsControlButtons(window, grid),
						grid);
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
