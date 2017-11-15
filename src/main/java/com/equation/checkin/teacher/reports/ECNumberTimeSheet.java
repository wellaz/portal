package com.equation.checkin.teacher.reports;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.utils.print.currentpage.PrintCurrentPage;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("deprecation")
public class ECNumberTimeSheet {
	ResultSet rs, rs1;
	Statement stm, stmt;

	public ECNumberTimeSheet(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public void search(String ecNum, String schoolName) {
		String check = "SELECT * FROM checkin WHERE ecNumber = '" + ecNum + "'";
		try {
			rs1 = stmt.executeQuery(check);
			if (rs1.next()) {
				Table table = new Table(ecNum + " Time Sheet");
				table.addContainerProperty("Name", String.class, null);
				table.addContainerProperty("Time In", String.class, null);
				table.addContainerProperty("Errand Out", String.class, null);
				table.addContainerProperty("Errand", String.class, null);
				table.addContainerProperty("Errand In", String.class, null);
				table.addContainerProperty("Approved", String.class, null);
				table.addContainerProperty("Time Out", String.class, null);
				table.addContainerProperty("Signature", String.class, null);
				int i = 0;

				String query = "SELECT firstName,surname,time_in,time_out,checkin.date FROM teachers,checkin WHERE checkin.ecNumber = '"
						+ ecNum + "' AND checkin.ecNumber = teachers.ecNumber";
				rs = stm.executeQuery(query);
				while (rs.next()) {
					String name = rs.getString(1) + " " + rs.getString(2);
					String time_in = rs.getString(3);
					String time_out = rs.getString(4);
					String date = rs.getString(5);

					String errandQuery = "SELECT trip,time_out,time_in FROM errand WHERE ecNumber = '" + ecNum
							+ "' AND date = '" + date + "'";
					String err_out = null, err = null, err_in = null;
					rs1 = stmt.executeQuery(errandQuery);
					if (rs1.next()) {
						err = rs1.getString(1);
						err_out = rs1.getString(2);
						err_in = rs1.getString(3);

					} else {
						err = "-";
						err_out = "-";
						err_in = "-";
					}
					table.addItem(new Object[] { name, time_in, err_out, err, err_in, "-", time_out, "-" },
							new Integer(i));
					i++;

				}

				table.setPageLength(table.size());
				table.setColumnCollapsingAllowed(true);
				table.setSizeFull();

				Window window = new Window();
				window.setModal(true);
				window.setSizeFull();
				window.center();
				window.setCaption("CheckIn And CheckOut Report");

				Button download = new Button("Download");
				download.setIcon(VaadinIcons.DOWNLOAD);
				download.addStyleName(ValoTheme.BUTTON_FRIENDLY);

				Button print = new Button("Print");
				print.setIcon(VaadinIcons.PRINT);
				print.addStyleName(ValoTheme.BUTTON_PRIMARY);
				print.addClickListener((e) -> {
					PrintCurrentPage.print();
				});

				Button close = new Button("Close");
				close.setIcon(VaadinIcons.CLOSE);
				close.addStyleName(ValoTheme.BUTTON_DANGER);
				close.addClickListener((e) -> {
					UI.getCurrent().removeWindow(window);
					window.close();
				});

				HorizontalLayout horizontalLayout = new HorizontalLayout(download, print, close);
				horizontalLayout.setSpacing(true);

				VerticalLayout layout = new VerticalLayout(horizontalLayout, table);
				layout.setSpacing(true);
				layout.setComponentAlignment(horizontalLayout, Alignment.TOP_CENTER);
				layout.setComponentAlignment(table, Alignment.MIDDLE_CENTER);

				new TimeSheetPDF().generatePDF(schoolName, "_  EC Number:" + ecNum, table, download);

				window.setContent(layout);
				UI.getCurrent().addWindow(window);
			} else {
				new Notification("Notification", "No Report Found For This EC Number :" + ecNum,
						Notification.Type.WARNING_MESSAGE, true).show(Page.getCurrent());
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

	}

}
