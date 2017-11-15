/**
 *
 * @author Wellington
 */
package com.equation.equate.results.analysis.perclass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.equation.equate.generate.units.GenerateMarkUnit;
import com.equation.equate.utils.print.PrintCurrentPage;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.ui.Table;

/**
 * @author Wellington
 *
 */
@SuppressWarnings("deprecation")
public class GeneratedAnalysisTable {

	Statement stm, stmt;
	ResultSet rs, rs1;

	/**
	 * 
	 */
	public GeneratedAnalysisTable(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public void createAnalysisWindow(String classname, int limit, String term, String year) {
		Window window = new Window();
		window.center();
		window.setModal(true);
		window.setSizeFull();

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

		Table table = new Table();

		HorizontalLayout horizontalLayout = new HorizontalLayout(download, print, closeButton);
		horizontalLayout.setSpacing(true);
		horizontalLayout.setSizeFull();
		horizontalLayout.addStyleName("student_report_sub_window");
		ArrayList<String> students = new ArrayList<>();
		ArrayList<Integer> counter = new ArrayList<>();
		String query = "SELECT student_name,COUNT(*) FROM record_subjects_passed WHERE  classname = '" + classname
				+ "' AND term = '" + term + "' AND year ='" + year + "' GROUP BY student_name ";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();

			int rows = rs1.getRow();
			if (rows > 0) {
				String query1 = "SELECT student_name,COUNT(*) FROM record_subjects_passed WHERE classname = '"
						+ classname + "' AND term = '" + term + "' AND year ='" + year + "' GROUP BY student_name ";
				rs = stm.executeQuery(query1);
				while (rs.next()) {
					String student_name = rs.getString(1);
					int count = rs.getInt(2);
					students.add(student_name);
					counter.add(count);
				}

				GenerateMarkUnit unit = new GenerateMarkUnit();

				table.addContainerProperty("Number", String.class, null);
				table.addContainerProperty("Student Name", String.class, null);
				table.addContainerProperty("English Paper1", String.class, null);
				table.addContainerProperty("English Paper2", String.class, null);
				table.addContainerProperty("English Total", String.class, null);
				table.addContainerProperty("English Unit", String.class, null);
				table.addContainerProperty("Shona Paper1", String.class, null);
				table.addContainerProperty("Shona Paper2", String.class, null);
				table.addContainerProperty("Shona Total", String.class, null);
				table.addContainerProperty("Shona Unit", String.class, null);
				table.addContainerProperty("Maths Paper1", String.class, null);
				table.addContainerProperty("Maths Paper2", String.class, null);
				table.addContainerProperty("Maths Total", String.class, null);
				table.addContainerProperty("Maths Unit", String.class, null);
				table.addContainerProperty("General Paper1", String.class, null);
				table.addContainerProperty("General Paper2", String.class, null);
				table.addContainerProperty("General Paper Total", String.class, null);
				table.addContainerProperty(" GPUnit", String.class, null);
				table.addContainerProperty("Agric Paper1", String.class, null);
				table.addContainerProperty("Agric Paper2", String.class, null);
				table.addContainerProperty("Agric Total", String.class, null);
				table.addContainerProperty(" Agric Unit", String.class, null);
				table.addContainerProperty("Total Marks", String.class, null);
				table.addContainerProperty("Total Units", String.class, null);

				int j = 0;
				for (int i = 0; i < students.size(); i++) {
					String student_name = students.get(i);
					int value = counter.get(i);
					if (value == limit) {
						String query3 = "SELECT student_name,math1,math2,matht,eng1,eng2,engt,sho1,sho2,shot,gen1,gen2,gent,ag1,ag2,agt,totalmarks FROM thistermmarks WHERE class_name = '"
								+ classname + "' AND student_name = '" + student_name + "' AND term = '" + term
								+ "' AND year ='" + year + "' ORDER BY totalmarks DESC";
						rs1 = stmt.executeQuery(query3);
						rs1.next();

						String math1 = rs1.getString(2);
						String math2 = rs1.getString(3);
						String matht = rs1.getString(4);
						String mathU = Integer.toString(unit.getUnit(Double.parseDouble(matht)));
						String eng1 = rs1.getString(5);
						String eng2 = rs1.getString(6);
						String engt = rs1.getString(7);
						String engU = Integer.toString(unit.getUnit(Double.parseDouble(engt)));
						String sho1 = rs1.getString(8);
						String sho2 = rs1.getString(9);
						String shot = rs1.getString(10);
						String shoU = Integer.toString(unit.getUnit(Double.parseDouble(shot)));
						String gen1 = rs1.getString(11);
						String gen2 = rs1.getString(12);
						String gent = rs1.getString(13);
						String genU = Integer.toString(unit.getUnit(Double.parseDouble(gent)));
						String ag1 = rs1.getString(14);
						String ag2 = rs1.getString(15);
						String agt = rs1.getString(16);
						String agU = Integer.toString(unit.getUnit(Double.parseDouble(agt)));
						String totalmarks = rs1.getString(17);

						int totalUnits = Integer.parseInt(mathU) + Integer.parseInt(engU) + Integer.parseInt(shoU)
								+ Integer.parseInt(genU) + Integer.parseInt(agU);

						table.addItem(new Object[] { String.valueOf(j + 1), student_name, String.valueOf(eng1),
								String.valueOf(eng2), String.valueOf(engt), String.valueOf(engU), String.valueOf(sho1),
								String.valueOf(sho2), String.valueOf(shot), String.valueOf(shoU), String.valueOf(math1),
								String.valueOf(math2), String.valueOf(matht), String.valueOf(mathU),
								String.valueOf(gen1), String.valueOf(gen2), String.valueOf(gent), String.valueOf(genU),
								String.valueOf(ag1), String.valueOf(ag2), String.valueOf(agt), String.valueOf(agU),
								String.valueOf(totalmarks), String.valueOf(totalUnits) }, new Integer(j));
						j++;

					}
				}
				new ClassAnalysisPDF().generatePDF(classname, table, download, Integer.toString(limit));
				double size = table.size();

				table.setFooterVisible(true);
				table.setColumnFooter("Student Name", "Total " + String.valueOf(size) + " Students found");
				table.setSizeFull();
				table.setPageLength((int) size);
				table.setSelectable(true);
				table.setColumnCollapsingAllowed(true);

				VerticalLayout layout = new VerticalLayout(horizontalLayout, table);

				layout.setSpacing(true);
				window.setContent(layout);

				UI.getCurrent().addWindow(window);

			} else {
				new Notification("Error", "No Data is found for analysis!", Notification.TYPE_ERROR_MESSAGE, true)
						.show(Page.getCurrent());

			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

	}
}