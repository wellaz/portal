package com.equation.accounts.search.console;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.equation.utils.doublevalue.format.DoubleValueFormat;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("deprecation")
public class AccountSearchDaemon {
	ResultSet rs, rs1;
	Statement stm, stmt;
	DoubleValueFormat df;
	// private static final int MAX_CHAR = 25;

	public AccountSearchDaemon(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.stm = stm;
		this.rs = rs;
		this.stmt = stmt;
		this.rs1 = rs1;
		df = new DoubleValueFormat();
	}

	public void search(String tablename, String date, String date1, Table grid) {
		grid.removeAllItems();
		// display.setReadOnly(false);
		// display.clear();
		ArrayList<String> data = new ArrayList<>();
		data.clear();
		String text = "SELECT * FROM " + tablename + " WHERE date >= '" + date + "' AND date <='" + date1
				+ "' ORDER BY date ASC";
		try {
			rs1 = stmt.executeQuery(text);
			rs1.last();
			int rows = rs1.getRow(), i = 0;

			double balance = 0, totalcredit = 0, totaldebit = 0;
			if (rows == 0) {
				new Notification("<h1>The account has been idle between <br/>" + date + " and " + date1 + "<h1/>", "",
						Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			} else {

				String text1 = "SELECT * FROM " + tablename + " WHERE date >= '" + date + "' AND date <='" + date1
						+ "' ORDER BY date ASC";
				rs = stm.executeQuery(text1);
				while (rs.next()) {
					String datt = rs.getString(5);
					double debit = df.format(rs.getDouble(3));
					double credit = df.format(rs.getDouble(4));

					balance = df.format(balance + credit - debit);
					totaldebit = df.format(totaldebit + debit);
					totalcredit = df.format(totalcredit + credit);

					String details = "REF: " + rs.getString(2).toLowerCase();
					// int maxLength = (details.length() < MAX_CHAR) ?
					// details.length() : MAX_CHAR;
					// details = details.substring(0, maxLength);
					String d = null;
					String c = null;
					String b = null;

					d = (debit == 0) ? "" : "$" + debit;
					c = (credit == 0) ? "" : "$" + credit;
					b = (balance == 0) ? "" : "$" + balance;

					grid.addItem(new Object[] { details + "\nDated " + datt, d, c, b }, new Integer(i));

					i++;

				}

				grid.setColumnCollapsingAllowed(true);
				grid.setColumnReorderingAllowed(true);
				grid.setFooterVisible(true);
				grid.setColumnFooter("Description", "Totals".toUpperCase());
				grid.setColumnFooter("Balance", String.valueOf(balance));
				grid.setColumnFooter("Withdral", String.valueOf(totaldebit));
				grid.setColumnFooter("Deposit", String.valueOf(totalcredit));
				grid.setPageLength(grid.size());

				// do {
				// String trans = details + "\nDated " + datt + "\t\t\t\t\t" + d
				// + "\t\t\t" + c + "\t\t\t" + b
				// + "\n\n";
				// display.append(trans);
				// data.add(trans);
				// display.setValue(trans);
				// } while (rs.next());
				// for(String s:data) {
				// display.setValue(s);
				// }
				// display.setValue((data.toString().replace("[",
				// "").replace("]", "").replace(",", "")).trim());
				// display.setReadOnly(true);
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}