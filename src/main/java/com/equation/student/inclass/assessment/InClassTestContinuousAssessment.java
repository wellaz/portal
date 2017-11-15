package com.equation.student.inclass.assessment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.utils.print.currentpage.PrintCurrentPage;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
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
public class InClassTestContinuousAssessment {

	Statement stm, stmt;
	ResultSet rs, rs1;
	String currentClass;

	public InClassTestContinuousAssessment(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1,
			String currentClass) {
		this.rs = rs;
		this.stm = stm;
		this.stmt = stmt;
		this.rs1 = rs1;
		this.currentClass = currentClass;
	}

	public Window inClassAssessmentWindow(String studentID, String studentName) {
		Window window = new Window();
		String query = "SELECT mark,subject,topic,class_name,total_mark FROM inclasstestrecords,inclassmarkschedule WHERE studentID = '"
				+ studentID + "' ORDER BY subject ASC ";
		String query1 = "SELECT subject,COUNT(subject),SUM(mark),SUM(total_mark),class_name FROM inclasstestrecords,inclassmarkschedule WHERE studentID = '"
				+ studentID + "' GROUP BY subject,mark,total_mark,class_name ORDER BY class_name DESC ";
		try {
			rs = stm.executeQuery(query);
			Table table = new Table();
			table.addContainerProperty("Class Name", String.class, null);
			table.addContainerProperty("Subject", String.class, null);
			table.addContainerProperty("Topic", String.class, null);
			table.addContainerProperty("Total Mark", Integer.class, null);
			table.addContainerProperty("Percentage Mark (%)", Double.class, null);
			int i = 0;
			while (rs.next()) {
				int mark = rs.getInt(1);
				String subject = rs.getString(2);
				String topic = rs.getString(3);
				String class_name = rs.getString(4);
				int total_mark = rs.getInt(5);

				double percentage = (double) Math.round((((double) mark / total_mark) * 100) * 100) / 100;

				table.addItem(new Object[] { class_name, subject, topic, mark, percentage }, new Integer(i));
				i++;
			}
			int size = table.size(), j = 0;
			table.setPageLength(size);
			table.setSelectable(true);
			table.setColumnCollapsingAllowed(true);
			table.setFooterVisible(true);
			String narration = (size > 1) ? " Records Found" : " Record";
			table.setColumnFooter("Subject", String.valueOf(size) + narration);

			rs1 = stmt.executeQuery(query1);
			Table table2 = new Table();
			table2.addContainerProperty("Class Level", String.class, null);
			table2.addContainerProperty("Subject", String.class, null);
			table2.addContainerProperty("Number Of In Class Tests", Integer.class, null);
			table2.addContainerProperty("Average mark", Integer.class, null);
			table2.addContainerProperty("Average Percentage (%)", Double.class, null);
			double sum = 0;
			while (rs1.next()) {
				String subject = rs1.getString(1);
				int count = rs1.getInt(2);
				int mark = rs1.getInt(3);
				int total_mark = rs1.getInt(4);
				String class_name = rs1.getString(5);

				double percentage = (double) Math.round((((double) mark / total_mark) * 100) * 100) / 100;
				sum += percentage;
				table2.addItem(new Object[] { class_name, subject, count, mark, percentage }, new Integer(j));
				j++;

			}
			int size1 = table2.size();
			double average = (double) Math.round(((sum / size)) * 100) / 100;
			table2.setPageLength(size1);
			String narrate = (size1 > 1) ? " Records" : " Record";
			table2.setColumnCollapsingAllowed(true);
			table2.setFooterVisible(true);
			table2.setColumnFooter("Subject", String.valueOf(size1) + narrate);
			table2.setColumnFooter("Average Percentage (%)", "Average = " + String.valueOf(average) + "%");

			HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
			horizontalSplitPanel.setFirstComponent(table);
			horizontalSplitPanel.setSecondComponent(table2);
			horizontalSplitPanel.setSplitPosition(75, Unit.PERCENTAGE);

			Button close = new Button("Close");
			close.addStyleName(ValoTheme.BUTTON_DANGER);
			close.setIcon(VaadinIcons.CLOSE);
			close.addClickListener((e) -> {
				UI.getCurrent().removeWindow(window);
				window.close();
			});

			Button print = new Button("Print");
			print.setIcon(VaadinIcons.PRINT);
			print.addStyleName(ValoTheme.BUTTON_PRIMARY);
			print.addClickListener((e) -> {
				PrintCurrentPage.print();
			});

			Button download = new Button("Downoad Report");
			download.setIcon(VaadinIcons.DOWNLOAD);
			download.addStyleName(ValoTheme.BUTTON_FRIENDLY);

			HorizontalLayout horizontalLayout = new HorizontalLayout(download, print, close);
			horizontalLayout.setSpacing(true);

			VerticalLayout layout = new VerticalLayout(horizontalLayout, horizontalSplitPanel);
			layout.setComponentAlignment(horizontalSplitPanel, Alignment.MIDDLE_CENTER);
			layout.setComponentAlignment(horizontalLayout, Alignment.TOP_CENTER);

			window.center();
			window.setSizeFull();
			window.setModal(true);
			window.setCaption(studentID + " In Class Test Continuout Assessment");
			window.setIcon(VaadinIcons.BRIEFCASE);
			window.setContent(layout);

			new InClassPdf().generatePDF(table, table2, download, studentName, studentID, currentClass);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		return window;
	}

	public void fetchRepotrt(String studentID, String studentName) {
		String query = "SELECT mark,subject,topic,class_name,total_mark FROM inclasstestrecords,inclassmarkschedule WHERE studentID = '"
				+ studentID + "' ORDER BY subject ASC ";
		try {
			rs = stm.executeQuery(query);
			rs.last();
			int rows = rs.getRow();
			if (rows > 0) {
				UI.getCurrent().addWindow(inClassAssessmentWindow(studentID, studentName));

			} else {
				new Notification(
						"<h1>For " + studentID
								+ ", we did not find any perfomance record.<br/>You may need to report this if you are not sure!<h1/>",
						"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}

}
