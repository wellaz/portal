/**
 *
 * @author Wellington
 */
package com.equation.equate.markschedule.overal.perclass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.equate.generate.units.GenerateMarkUnit;
import com.equation.equate.markschedule.passrate.perclass.OveralPassRate;
import com.equation.equate.markschedule.passrate.perclass.SubjectsPassrate;
import com.equation.equate.rawmarks.GetStudentRawMark;
import com.equation.equate.utils.print.PrintCurrentPage;
import com.equation.equate.utils.subjects.AllSubjects;
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
 * @author Wellington
 *
 */
@SuppressWarnings("deprecation")
public class CreateRawMarkScheduleTable {

	Statement stm, stmt;
	ResultSet rs, rs1;

	public CreateRawMarkScheduleTable(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
	}

	@SuppressWarnings("unused")
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

		GetStudentRawMark rawMark = new GetStudentRawMark(stmt, rs1);
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
				+ class_name + "' AND term='" + term + "' AND year = '" + year + "'  ORDER BY totalmarks DESC";
		try {
			rs = stm.executeQuery(query);
			int i = 0;
			GenerateMarkUnit unit = new GenerateMarkUnit();
			while (rs.next()) {
				String student_name = rs.getString(1);
				int mathp1 = rawMark.getRawMark(Tablename.PAPER_ONE, student_name, class_name, AllSubjects.MATHEMATICS,
						term, year);
				int mathp2 = rawMark.getRawMark(Tablename.PAPER_TWO, student_name, class_name, AllSubjects.MATHEMATICS,
						term, year);
				int mathstr = mathp1 + mathp2;
				String math1 = rs.getString(2);
				String math2 = rs.getString(3);
				String matht = rs.getString(4);
				String mathU = Integer.toString(unit.getUnit(Double.parseDouble(matht)));
				int engp1 = rawMark.getRawMark(Tablename.PAPER_ONE, student_name, class_name, AllSubjects.ENGLISH, term,
						year);
				int engp2 = rawMark.getRawMark(Tablename.PAPER_TWO, student_name, class_name, AllSubjects.ENGLISH, term,
						year);
				int engtr = engp1 + engp2;
				String eng1 = rs.getString(5);
				String eng2 = rs.getString(6);
				String engt = rs.getString(7);
				String engU = Integer.toString(unit.getUnit(Double.parseDouble(engt)));
				int shop1 = rawMark.getRawMark(Tablename.PAPER_ONE, student_name, class_name, AllSubjects.SHONA, term,
						year);
				int shop2 = rawMark.getRawMark(Tablename.PAPER_TWO, student_name, class_name, AllSubjects.SHONA, term,
						year);
				int shotr = shop1 + shop2;
				String sho1 = rs.getString(8);
				String sho2 = rs.getString(9);
				String shot = rs.getString(10);
				String shoU = Integer.toString(unit.getUnit(Double.parseDouble(shot)));
				int genp1 = rawMark.getRawMark(Tablename.PAPER_ONE, student_name, class_name, AllSubjects.GENERAL_PAPER,
						term, year);
				int genp2 = rawMark.getRawMark(Tablename.PAPER_TWO, student_name, class_name, AllSubjects.GENERAL_PAPER,
						term, year);
				int gentr = genp1 + genp2;
				String gen1 = rs.getString(11);
				String gen2 = rs.getString(12);
				String gent = rs.getString(13);
				String genU = Integer.toString(unit.getUnit(Double.parseDouble(gent)));
				int agp1 = rawMark.getRawMark(Tablename.PAPER_ONE, student_name, class_name, AllSubjects.AGRICULTURE,
						term, year);
				int agp2 = rawMark.getRawMark(Tablename.PAPER_TWO, student_name, class_name, AllSubjects.AGRICULTURE,
						term, year);
				int agtr = agp1 + agp2;
				String ag1 = rs.getString(14);
				String ag2 = rs.getString(15);
				String agt = rs.getString(16);
				String agU = Integer.toString(unit.getUnit(Double.parseDouble(agt)));
				String totalmarks = rs.getString(17);

				int totalUnits = Integer.parseInt(mathU) + Integer.parseInt(engU) + Integer.parseInt(shoU)
						+ Integer.parseInt(genU) + Integer.parseInt(agU);

				int totalmarksr = mathstr + engtr + shotr + gentr + agtr;

				table.addItem(new Object[] { String.valueOf(i + 1), student_name, String.valueOf(engp1),
						String.valueOf(engp2), String.valueOf(engtr), String.valueOf(engU), String.valueOf(shop1),
						String.valueOf(shop2), String.valueOf(shotr), String.valueOf(shoU), String.valueOf(mathp1),
						String.valueOf(mathp2), String.valueOf(mathstr), String.valueOf(mathU), String.valueOf(genp1),
						String.valueOf(genp2), String.valueOf(gentr), String.valueOf(genU), String.valueOf(agp1),
						String.valueOf(agp2), String.valueOf(agtr), String.valueOf(agU), String.valueOf(totalmarksr),
						String.valueOf(totalUnits), String.valueOf(i + 1) }, new Integer(i));
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

			new ClassMarkSchedulePDF().generatePDF("Raw", class_name, table, passrates, overall, download);

			VerticalLayout layout = new VerticalLayout(horizontalLayout, table, passrates, overall);
			layout.setSpacing(true);
			window.setContent(layout);

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		return window;
	}

}
