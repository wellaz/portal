/**
 *
 * @author Wellington
 */
package com.equation.equate.markschedule.overal.perclass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.equation.equate.generate.units.GenerateMarkUnit;
import com.equation.equate.markschedule.passrate.perclass.OveralPassRate;
import com.equation.equate.markschedule.passrate.perclass.SubjectsPassrate;
import com.equation.equate.utils.print.PrintCurrentPage;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
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
public class CreateMarkScheduleTable {

	Statement stm, stmt;
	ResultSet rs, rs1;

	public CreateMarkScheduleTable(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
	}

	public Window createWindow(String class_name, String term, String year, String date) {
		Window window = new Window();
		window.setSizeFull();
		window.center();
		window.setModal(true);

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

		Table table = new Table();
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
		table.addContainerProperty("Class Position", String.class, null);

		String query = "SELECT student_name,math1,math2,matht,eng1,eng2,engt,sho1,sho2,shot,gen1,gen2,gent,ag1,ag2,agt,totalmarks FROM thistermmarks WHERE class_name = '"
				+ class_name + "' AND term='" + term + "' AND year = '" + year + "' ORDER BY totalmarks DESC";
		try {
			rs = stm.executeQuery(query);
			int i = 0, position = 0;
			GenerateMarkUnit unit = new GenerateMarkUnit();
			ArrayList<Integer> data = new ArrayList<>();
			while (rs.next()) {
				String student_name = rs.getString(1);
				String math1 = rs.getString(2);
				String math2 = rs.getString(3);
				String matht = rs.getString(4);
				String mathU = Integer.toString(unit.getUnit(Double.parseDouble(matht)));
				String eng1 = rs.getString(5);
				String eng2 = rs.getString(6);
				String engt = rs.getString(7);
				String engU = Integer.toString(unit.getUnit(Double.parseDouble(engt)));
				String sho1 = rs.getString(8);
				String sho2 = rs.getString(9);
				String shot = rs.getString(10);
				String shoU = Integer.toString(unit.getUnit(Double.parseDouble(shot)));
				String gen1 = rs.getString(11);
				String gen2 = rs.getString(12);
				String gent = rs.getString(13);
				String genU = Integer.toString(unit.getUnit(Double.parseDouble(gent)));
				String ag1 = rs.getString(14);
				String ag2 = rs.getString(15);
				String agt = rs.getString(16);
				String agU = Integer.toString(unit.getUnit(Double.parseDouble(agt)));
				String totalmarks = rs.getString(17);
				if (i == 0) {
					data.add(Integer.parseInt(totalmarks));
					position = i + 1;
				} else {
					if (data.get(data.size() - 1) == Integer.parseInt(totalmarks)) {
						data.add(Integer.parseInt(totalmarks));
						for (int t = 0; t < data.size(); t++) {
							int val = data.get(t);
							if (val == Integer.parseInt(totalmarks)) {
								position = t + 1;
								break;
							} else {
								position = data.size() - 1;
							}
						}
						// position = data.size() - 1;
					} else {
						data.add(Integer.parseInt(totalmarks));
						position = i + 1;
					}

				}

				int totalUnits = Integer.parseInt(mathU) + Integer.parseInt(engU) + Integer.parseInt(shoU)
						+ Integer.parseInt(genU) + Integer.parseInt(agU);

				table.addItem(
						new Object[] { String.valueOf(i + 1), student_name, String.valueOf(eng1), String.valueOf(eng2),
								String.valueOf(engt), String.valueOf(engU), String.valueOf(sho1), String.valueOf(sho2),
								String.valueOf(shot), String.valueOf(shoU), String.valueOf(math1),
								String.valueOf(math2), String.valueOf(matht), String.valueOf(mathU),
								String.valueOf(gen1), String.valueOf(gen2), String.valueOf(gent), String.valueOf(genU),
								String.valueOf(ag1), String.valueOf(ag2), String.valueOf(agt), String.valueOf(agU),
								String.valueOf(totalmarks), String.valueOf(totalUnits), String.valueOf(position) },
						new Integer(i));
				i++;

			}
			double size = table.size();

			table.setFooterVisible(true);
			table.setColumnFooter("Student Name", "Total " + String.valueOf(size) + " Students found");
			table.setSizeFull();
			table.setPageLength((int) size);
			table.setSelectable(true);
			table.setColumnCollapsingAllowed(true);

			Table passrates = new SubjectsPassrate(rs, rs1, stm, stmt).createSubjectsPassrate(class_name, term, year,
					date);
			Table overall = new OveralPassRate(rs, rs1, stm, stmt).createPassrateTable(class_name, term, year);

			new ClassMarkSchedulePDF().generatePDF("Adjusted", class_name, table, passrates, overall, download);

			VerticalLayout layout = new VerticalLayout(horizontalLayout, table, passrates, overall);
			layout.setSpacing(true);
			window.setContent(layout);

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		return window;
	}
}