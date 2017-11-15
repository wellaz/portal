package com.equation.accounts.vouchers.search.expenses.relevant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial", "deprecation" })
public class VoucherExpenditureList extends VerticalLayout {

	Statement stm, stmt;
	ResultSet rs, rs1;
	String voucherNumber;
	String schoolID;

	public VoucherExpenditureList(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String voucherNumber,
			String schoolID) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.voucherNumber = voucherNumber;
		this.schoolID = schoolID;

		String query = "SELECT 	receiptNumber,voucherNumber,amount,description,supplier,date,time FROM expenditure,schools WHERE voucherNumber = '"
				+ this.voucherNumber + "' AND schools.schoolID = '" + this.schoolID + "'";
		try {
			this.rs1 = this.stmt.executeQuery(query);
			this.rs1.last();
			int rows = this.rs1.getRow(), i = 0;
			if (rows > 0) {
				Table grid = new Table();
				grid.addContainerProperty("Receipt Number", String.class, null);
				grid.addContainerProperty("Voucher Number", String.class, null);
				grid.addContainerProperty("Amount", Double.class, null);
				grid.addContainerProperty("Description", String.class, null);
				grid.addContainerProperty("Supplier", String.class, null);
				grid.addContainerProperty("Date", String.class, null);
				grid.setSelectable(true);

				String query1 = "SELECT 	receiptNumber,voucherNumber,amount,description,supplier,date,time FROM expenditure,schools WHERE voucherNumber = '"
						+ this.voucherNumber + "' AND schools.schoolID = '" + this.schoolID + "'";

				this.rs = this.stm.executeQuery(query1);
				double totalExpenses = 0;
				while (this.rs.next()) {
					String receiptNumberr = this.rs.getString(1);
					String voucherNumberr = this.rs.getString(2);
					double amountt = this.rs.getDouble(3);
					String description = this.rs.getString(4);
					String supplier = this.rs.getString(5);
					String date = this.rs.getString(6) + " " + this.rs.getString(7);
					totalExpenses += amountt;

					grid.addItem(new Object[] { receiptNumberr, voucherNumberr, amountt, description, supplier, date },
							new Integer(i));
					i++;
				}
				int size = grid.size();
				String narrate = (size > 1) ? " Records" : " Record";
				grid.setColumnCollapsingAllowed(true);
				grid.setFooterVisible(true);
				grid.setColumnFooter("Receipt Number", String.valueOf(size) + narrate);
				grid.setColumnFooter("Amount", "Total Amount $" + String.valueOf(totalExpenses));
				// grid.setSizeFull();
				grid.setPageLength(grid.size());
				grid.addStyleName("students_gender_table");

				this.addComponent(grid);

			} else {
				this.addComponent(new Label("Voucher Number " + this.voucherNumber + " does not have any expenses"));
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}