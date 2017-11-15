package com.equation.accounts.otheraccounts.search;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial", "deprecation" })
public class OtherAccountsSearch extends Panel {
	Statement stm, stmt;
	ResultSet rs, rs1;
	String schoolID, userID;

	public OtherAccountsSearch(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String schoolID) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.schoolID = schoolID;

		Button listAllAccounts = new Button("List All Other Accounts");
		listAllAccounts.addStyleName(ValoTheme.BUTTON_DANGER);
		listAllAccounts.setIcon(VaadinIcons.DOWNLOAD);

		listAllAccounts.addClickListener((e) -> {
			String query = "SELECT accountName,amount,description,date,time FROM other_accounts,schools WHERE schools.schoolID = '"
					+ this.schoolID + "'";
			try {
				this.rs1 = this.stmt.executeQuery(query);
				this.rs1.last();
				int rows = this.rs1.getRow();
				if (rows > 0) {
					Table grid = new Table();
					// Define some columns
					grid.addContainerProperty("Acount Name", String.class, null);
					grid.addContainerProperty("Amount", Double.class, null);
					grid.addContainerProperty("Description", String.class, null);
					grid.addContainerProperty("Date", String.class, null);
					grid.addContainerProperty("Time", String.class, null);
					String query1 = "SELECT accountName,amount,description,date,time FROM other_accounts,schools WHERE schools.schoolID = '"
							+ this.schoolID + "'";

					this.rs = this.stm.executeQuery(query1);
					int i = 0;
					while (this.rs.next()) {
						String accountName = this.rs.getString(1);
						double amount = this.rs.getDouble(2);
						String desription = this.rs.getString(3);
						String date = this.rs.getString(4);
						String time = this.rs.getString(5);

						grid.addItem(new Object[] { accountName, amount, desription, date, time }, new Integer(i));
						i++;
					}
					grid.setSizeFull();
					grid.addStyleName(ValoTheme.TABLE_COMPACT);
					grid.setPageLength(grid.size());

					Window window = new Window("Search Results");
					HorizontalLayout horizontalLayout = new OtherAccountsSearchControlButtons(window, grid);
					VerticalLayout layout = new VerticalLayout(horizontalLayout, grid);
					layout.setSpacing(true);

					window.setSizeFull();
					window.center();
					window.setModal(true);
					window.setContent(layout);
					UI.getCurrent().addWindow(window);

				} else {
					new Notification("<h1>No Other Accounts Found!<h1/>", "", Notification.Type.ERROR_MESSAGE, true)
							.show(Page.getCurrent());
				}

			} catch (SQLException ee) {
				ee.printStackTrace();
			}

		});

		VerticalLayout layout = new VerticalLayout(listAllAccounts);
		layout.setSpacing(true);
		this.setCaption("List Other Accounts");
		this.setContent(layout);
		this.setIcon(VaadinIcons.SEARCH);
	}
}