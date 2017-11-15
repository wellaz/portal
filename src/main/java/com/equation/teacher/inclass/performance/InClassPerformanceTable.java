package com.equation.teacher.inclass.performance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
public class InClassPerformanceTable {
	ResultSet rs, rs1;
	Statement stm, stmt;

	public InClassPerformanceTable(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.stm = stm;
		this.stmt = stmt;
		this.rs = rs;
		this.rs1 = rs1;
	}

	public void searchPerformanceReport(String test_id) {
		String query = "SELECT studentID FROM inclassmarkschedule WHERE test_id = '" + test_id + "'";

		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow(), i = 0;
			if (rows > 0) {
				String query1 = "SELECT studentID,mark,total_mark FROM inclassmarkschedule,inclasstestrecords WHERE inclasstestrecords.test_id = '"
						+ test_id + "' ORDER BY mark DESC";
				rs = stm.executeQuery(query1);

				Table table = new Table();
				table.addContainerProperty("Student Name", String.class, null);
				table.addContainerProperty("Total Mark", Integer.class, null);
				table.addContainerProperty("Percentage Mark", Double.class, null);
				table.addContainerProperty("Position", Integer.class, null);
				while (rs.next()) {
					String studentID = rs.getString(1);
					int mark = rs.getInt(2);
					int total_mark = rs.getInt(3);
					int position = (i + 1);

					double percentage = (double) Math.round(((double) mark / (double) total_mark) * 100) / 100;

					table.addItem(new Object[] { studentID, mark, percentage, position }, new Integer(i));

					i++;

				}
				table.setFooterVisible(true);
				int tableSize = table.size();
				String narration = (tableSize > 1) ? " Records" : " Record";
				table.setColumnFooter("Student Name", String.valueOf(tableSize) + narration);

				table.setPageLength(tableSize);
				table.setSelectable(true);
				table.setColumnCollapsingAllowed(true);
				Window window = new Window("In Class Test Results");
				window.center();
				window.setSizeFull();
				window.setModal(true);

				Button close = new Button("Close");
				close.addStyleName(ValoTheme.BUTTON_DANGER);
				close.setIcon(VaadinIcons.CLOSE);
				close.addClickListener((e) -> {
					UI.getCurrent().removeWindow(window);
					window.close();
				});
				Button print = new Button("Print");
				print.setIcon(VaadinIcons.PRINT);
				print.addStyleName(ValoTheme.BUTTON_FRIENDLY);
				print.addClickListener((e) -> {

				});
				Button download = new Button("Download Report");
				download.setIcon(VaadinIcons.DOWNLOAD);
				download.addStyleName(ValoTheme.BUTTON_PRIMARY);
				HorizontalLayout horizontalLayout = new HorizontalLayout(download, print, close);
				horizontalLayout.setSpacing(true);

				VerticalLayout layout = new VerticalLayout(horizontalLayout, table);
				layout.setSpacing(true);
				layout.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
				window.setContent(layout);
				UI.getCurrent().addWindow(window);

			} else {
				new Notification(
						"<h1>It seems that no one has written this in class test!<br/>You may need to remind the kids to write the test before it is due!<h1/>",
						"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

	}
}