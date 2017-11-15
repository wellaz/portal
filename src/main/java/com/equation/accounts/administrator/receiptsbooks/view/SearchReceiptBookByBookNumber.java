package com.equation.accounts.administrator.receiptsbooks.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.system.users.teacher.getname.FetchStuffMemberName;
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
public class SearchReceiptBookByBookNumber {
	ResultSet rs, rs1;
	Statement stm, stmt;
	private Table grid;

	public SearchReceiptBookByBookNumber(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public Window createContentPanel(String schoolID, String bookNumber) {
		Window window = new Window("search Results");
		window.setModal(false);
		window.setSizeFull();
		window.center();
		String query = "SELECT bookNumber,firstReceiptNumber,finalReceptNumber,date,time,status,userID FROM receiptbooks,schools WHERE schools.schoolID = '"
				+ schoolID + "'  AND bookNumber = '" + bookNumber + "'";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow(), i = 0;
			if (rows > 0) {

				String query1 = "SELECT bookNumber,firstReceiptNumber,finalReceptNumber,date,time,status,userID FROM receiptbooks,schools WHERE schools.schoolID = '"
						+ schoolID + "'  AND bookNumber = '" + bookNumber + "'";

				rs = stm.executeQuery(query1);

				grid = new Table();
				grid.addContainerProperty("Book Number", String.class, null);
				grid.addContainerProperty("First Receipt Number", String.class, null);
				grid.addContainerProperty("Last Receipt Number", String.class, null);
				grid.addContainerProperty("Date Posted", String.class, null);
				grid.addContainerProperty("Status", String.class, null);
				grid.addContainerProperty("Posted By", String.class, null);
				grid.setSelectable(true);
				while (rs.next()) {
					String bookNumberr = rs.getString(1);
					String firstReceiptNumber = rs.getString(2);
					String finalReceptNumber = rs.getString(3);
					String date = rs.getString(4) + " " + rs.getString(5);
					String status = rs.getString(6);
					String userID = rs.getString(7);

					String postedBy = new FetchStuffMemberName(rs, rs1, stm, stmt).getActualName(userID);

					grid.addItem(
							new Object[] { bookNumberr, firstReceiptNumber, finalReceptNumber, date, status, postedBy },
							new Integer(i));
					i++;
				}

				int size = grid.size();
				String ren = (size > 1) ? " Records" : " Record";
				grid.setColumnCollapsingAllowed(true);
				grid.setFooterVisible(true);
				grid.setColumnFooter("Book Number", String.valueOf(size) + ren);
				grid.setSizeFull();
				grid.setPageLength(grid.size());
				grid.addStyleName("students_gender_table");

				VerticalLayout layout = new VerticalLayout(new ReceiptBookControlButtons(window, grid), grid);
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
