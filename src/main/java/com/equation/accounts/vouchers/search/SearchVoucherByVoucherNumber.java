package com.equation.accounts.vouchers.search;

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
public class SearchVoucherByVoucherNumber {
	ResultSet rs, rs1;
	Statement stm, stmt;
	private Table grid;

	public SearchVoucherByVoucherNumber(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public Window createContentPanel(String schoolID, String voucherNumber) {
		Window window = new Window("search Results");
		window.setModal(false);
		window.setSizeFull();
		window.center();
		String query = "SELECT voucherNumber,paymentMethod,preparedBy,authorisedBy,paidBy,date,amount,status FROM voucherstable,schools WHERE schools.schoolID = '"
				+ schoolID + "'  AND voucherNumber = '" + voucherNumber + "'";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow(), i = 0;
			if (rows > 0) {

				String query1 = "SELECT voucherNumber,paymentMethod,preparedBy,authorisedBy,paidBy,date,amount,status FROM voucherstable,schools WHERE schools.schoolID = '"
						+ schoolID + "'  AND voucherNumber = '" + voucherNumber + "'";

				rs = stm.executeQuery(query1);

				grid = new Table();
				grid.addContainerProperty("Voucher Number", Integer.class, null);
				grid.addContainerProperty("Payment Method", String.class, null);
				grid.addContainerProperty("Prepared By", String.class, null);
				grid.addContainerProperty("Authorised By", String.class, null);
				grid.addContainerProperty("Paid By", String.class, null);
				grid.addContainerProperty("Date", String.class, null);
				grid.addContainerProperty("Amount", Double.class, null);
				grid.addContainerProperty("Status", String.class, null);
				grid.setSelectable(true);
				double totatAmount = 0;
				while (rs.next()) {
					int voucherNumberr = rs.getInt(1);
					String paymentMethod = rs.getString(2);
					String preparedBy = rs.getString(3);
					String authorisedBy = rs.getString(4);
					String paidBy = rs.getString(5);
					String date = rs.getString(6);
					double amountt = rs.getDouble(7);
					String status = rs.getString(8);
					totatAmount += amountt;

					grid.addItem(new Object[] { voucherNumberr, paymentMethod, preparedBy, authorisedBy, paidBy, date,
							amountt, status }, new Integer(i));
					i++;
				}
				grid.setColumnCollapsingAllowed(true);
				grid.setFooterVisible(true);
				grid.setColumnFooter("Voucher Number", String.valueOf(grid.size() + " Records"));
				grid.setColumnFooter("Amount", "Total $" + String.valueOf(totatAmount));
				grid.setSizeFull();
				grid.setPageLength(grid.size());
				grid.addStyleName("students_gender_table");

				VerticalLayout layout = new VerticalLayout(
						new VouchersSearchWindowControlButtons(window, grid, stm, stmt, rs, rs1, schoolID), grid);
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
