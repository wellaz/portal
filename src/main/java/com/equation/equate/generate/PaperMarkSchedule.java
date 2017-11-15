package com.equation.equate.generate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.equate.generate.units.GenerateMarkUnit;
import com.equation.equate.utils.print.PrintCurrentPage;
import com.equation.equate.utils.tablename.Tablename;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
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
public class PaperMarkSchedule {
	ResultSet rs, rs1;
	Statement stm, stmt;

	public PaperMarkSchedule(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;

	}

	public Window prepareMarkSchedule(String class_name, String subject, String paper, String tablename) {
		Window window = new Window();
		window.setModal(false);
		window.setSizeFull();
		window.center();

		Button closeButton = new Button("Close");
		closeButton.addStyleName(ValoTheme.BUTTON_DANGER);
		closeButton.setIcon(VaadinIcons.CLOSE);

		Button print = new Button("Print");
		print.addStyleName(ValoTheme.BUTTON_PRIMARY);
		print.setIcon(VaadinIcons.PRINT);
		print.addClickListener((e) -> {
			PrintCurrentPage.print();
		});

		Button download = new Button("Download");
		download.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		download.setIcon(VaadinIcons.DOWNLOAD);

		closeButton.addClickListener((ev) -> {
			UI.getCurrent().removeWindow(window);
			window.close();
		});

		HorizontalLayout horizontalLayout = new HorizontalLayout(download, print, closeButton);
		horizontalLayout.setSpacing(true);
		horizontalLayout.setSizeFull();
		horizontalLayout.addStyleName("student_report_sub_window");

		Table table = new Table("Final " + subject + " paper" + paper + " Mark Schedule");
		table.addContainerProperty("STUDENT NAME", String.class, null);
		table.addContainerProperty("RAW MARK", String.class, null);
		table.addContainerProperty("ADJUSTED MARK", String.class, null);

		String query = "SELECT 	out_of,percentage,student_name,raw_mark,adjusted_mark FROM " + tablename
				+ " WHERE class_name = '" + class_name + "' AND subject = '" + subject
				+ "' ORDER BY adjusted_mark DESC";
		String out_of = null, percentage = null;
		try {
			rs = stm.executeQuery(query);
			int i = 0;
			while (rs.next()) {
				out_of = rs.getString(1);
				percentage = rs.getString(2);
				String student_name = rs.getString(3);
				String raw_mark = rs.getString(4);
				String adjusted_mark = rs.getString(5);

				table.addItem(new Object[] { student_name, String.valueOf(raw_mark), String.valueOf(adjusted_mark) },
						new Integer(i));
				i++;
			}
			double size = table.size();

			table.setFooterVisible(true);
			table.setColumnFooter("STUDENT NAME", "Total " + String.valueOf(size) + " Students found");
			table.setSizeFull();
			table.setPageLength((int) size);
			table.setSelectable(true);
			table.setColumnCollapsingAllowed(true);
			new PaperMarkSchedulePDF().generatePDF(class_name, subject, paper, out_of, percentage, table, download);

			VerticalLayout layout = new VerticalLayout(horizontalLayout, table);
			layout.setSpacing(true);
			window.setContent(layout);

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		return window;
	}

	public Window prepareAllPapersMarkSchedule(String class_name, String subject) {
		Window window = new Window();
		window.setModal(false);
		window.setSizeFull();
		window.center();

		Button closeButton = new Button("Close");
		closeButton.addStyleName(ValoTheme.BUTTON_DANGER);
		closeButton.setIcon(VaadinIcons.CLOSE);

		Button print = new Button("Print");
		print.addStyleName(ValoTheme.BUTTON_PRIMARY);
		print.setIcon(VaadinIcons.PRINT);
		print.addClickListener((e) -> {
			PrintCurrentPage.print();
		});

		Button download = new Button("Download");
		download.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		download.setIcon(VaadinIcons.DOWNLOAD);

		closeButton.addClickListener((ev) -> {
			UI.getCurrent().removeWindow(window);
			window.close();
		});

		HorizontalLayout horizontalLayout = new HorizontalLayout(download, print, closeButton);
		horizontalLayout.setSpacing(true);
		horizontalLayout.setSizeFull();
		horizontalLayout.addStyleName("student_report_sub_window");

		Table table = new Table("Final " + subject + " Mark Schedule");
		table.addContainerProperty("STUDENT NAME", String.class, null);
		table.addContainerProperty("PAPER ONE RAW MARK", String.class, null);
		table.addContainerProperty("PAPER ONE ADJUSTED MARK", String.class, null);
		table.addContainerProperty("PAPER TWO RAW MARK", String.class, null);
		table.addContainerProperty("PAPER TWO ADJUSTED MARK", String.class, null);
		table.addContainerProperty("PERCENTAGE TOTAL", String.class, null);
		table.addContainerProperty("GRADING / UNIT", String.class, null);

		String query = "SELECT out_of,percentage,student_name,raw_mark,adjusted_mark FROM " + Tablename.PAPER_ONE
				+ " WHERE class_name = '" + class_name + "' AND subject = '" + subject + "'";
		String out_of = null, percentage = null;
		String out_of2 = null, percentage2 = null;

		try {
			rs = stm.executeQuery(query);
			int i = 0;
			while (rs.next()) {
				out_of = rs.getString(1);
				percentage = rs.getString(2);
				System.out.println(percentage);
				String student_name = rs.getString(3);
				String raw_mark = rs.getString(4);
				String adjusted_mark = rs.getString(5);

				String query1 = "SELECT out_of,percentage,raw_mark,adjusted_mark FROM " + Tablename.PAPER_TWO
						+ " WHERE class_name = '" + class_name + "' AND subject = '" + subject
						+ "' AND student_name = '" + student_name + "'";
				rs1 = stmt.executeQuery(query1);
				String raw_mark2 = null, adjusted_mark2 = null;
				if (rs1.next()) {
					out_of2 = rs1.getString(1);
					percentage2 = rs1.getString(2);
					raw_mark2 = rs1.getString(3);
					adjusted_mark2 = rs1.getString(4);
				} else {
					raw_mark2 = "0";
					adjusted_mark2 = "0";
				}
				double total1 = Double.parseDouble(adjusted_mark) + Double.parseDouble(adjusted_mark2);
				double total = (double) Math.round(total1 * 100) / 100;
				int unit = new GenerateMarkUnit().getUnit(total);

				table.addItem(new Object[] { student_name, String.valueOf(raw_mark), String.valueOf(adjusted_mark),
						String.valueOf(raw_mark2), String.valueOf(adjusted_mark2), String.valueOf(total),
						String.valueOf(unit) }, new Integer(i));
				i++;
			}
			double size = table.size();
			System.out.println(percentage + "   " + percentage2);
			int perc = Integer.parseInt(percentage) + Integer.parseInt(percentage2);
			int outtotal = Integer.parseInt(out_of) + Integer.parseInt(out_of2);
			table.setFooterVisible(true);
			table.setColumnFooter("STUDENT NAME", "Total " + String.valueOf(size) + " Students found");
			table.setSizeFull();
			table.setPageLength((int) size);
			table.setSelectable(true);
			table.setColumnCollapsingAllowed(true);
			new PaperMarkSchedulePDF().generatePDF(class_name, subject, "One And Two", Integer.toString(outtotal),
					Integer.toString(perc) + "%", table, download);

			VerticalLayout layout = new VerticalLayout(horizontalLayout, table);
			layout.setSpacing(true);
			window.setContent(layout);

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		return window;
	}

}