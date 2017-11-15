/**
 *
 * @author Wellington
 */
package com.equation.equate.results.analysis.pergrade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.equate.generate.units.GenerateMarkUnit;
import com.equation.equate.settings.passmark.PassMark;
import com.equation.equate.utils.date.AllYearTermsBin;
import com.equation.equate.utils.date.AllYears;
import com.equation.equate.utils.date.DateUtility;
import com.equation.equate.utils.date.GetSchoolTerm;
import com.equation.equate.utils.grades.AllGrades;
import com.equation.equate.utils.maximum.subjects.Maximum;
import com.equation.equate.utils.print.PrintCurrentPage;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.ui.Table;

/**
 * @author Wellington
 *
 */
@SuppressWarnings({ "serial", "deprecation" })
public class GradeDataAnalysis extends CustomComponent {

	Statement stm, stmt;
	ResultSet rs, rs1;

	/**
	 * 
	 */
	public GradeDataAnalysis(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;

		ComboBox<String> classnames = new ComboBox<>("<h1>Select Grade:<h1/>");
		classnames.setEmptySelectionAllowed(false);
		classnames.setItems(AllGrades.allGrades());
		classnames.setCaptionAsHtml(true);

		ComboBox<String> terms = new ComboBox<>("<h1>Select Term:<h1/>");
		terms.setCaptionAsHtml(true);
		terms.setEmptySelectionAllowed(false);
		terms.setItems(AllYearTermsBin.allTermsArray());
		terms.setValue(new GetSchoolTerm().thisTerm());

		ComboBox<String> years = new ComboBox<>("<h1>Select Year:<h1/>");
		years.setCaptionAsHtml(true);
		years.setEmptySelectionAllowed(false);
		years.setItems(AllYears.makeYears());
		years.setValue(new DateUtility().getYear());

		ComboBox<String> subjectnumber = new ComboBox<>("<h1>Number Of Subjects Passed:<h1/>");
		subjectnumber.setCaptionAsHtml(true);
		subjectnumber.setEmptySelectionAllowed(false);
		subjectnumber.setItems(Maximum.max());

		Button next = new Button("GO >");
		next.addStyleName(ValoTheme.BUTTON_PRIMARY);

		next.addClickListener((e) -> {
			int passMark = new PassMark(this.rs, this.rs1, this.stm, this.stmt).getPassMark();
			String class_name = (String) classnames.getValue().toString();
			String max = (String) subjectnumber.getValue().toString();

			String term = (String) terms.getValue();
			String year = (String) years.getValue();

			if (!(class_name.equals("") || max.equals(""))) {
				if (max.equals("0")) {
					// ArrayList<String> data = new
					// StudentsWhoPassedAtLeastOneArray(stm, stmt, rs, rs1)
					// .passedStudents(class_name);

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
					print.addClickListener((ee) -> {
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

					// AllStudentsPerGrade perGrade = new
					// AllStudentsPerGrade(stm, stmt, rs, rs1);
					// for (int i = 0; i < data.size(); i++) {
					// String student = data.get(i);
					// if (!perGrade.isValidStudentNumber(student, class_name))
					// {
					String query3 = "SELECT student_name,math1,math2,matht,eng1,eng2,engt,sho1,sho2,shot,gen1,gen2,gent,ag1,ag2,agt,totalmarks FROM thistermmarks WHERE class_name LIKE '"
							+ class_name + "%" + "' AND matht < '" + passMark + "' AND engt < '" + passMark
							+ "' AND shot < '" + passMark + "' AND agt < '" + passMark + "' AND term = '" + term
							+ "' AND year = '" + year + "' ORDER BY totalmarks DESC";
					try {
						this.rs1 = this.stmt.executeQuery(query3);
						int i = 0;
						while (this.rs1.next()) {
							String student_name = this.rs1.getString(1);
							String math1 = this.rs1.getString(2);
							String math2 = this.rs1.getString(3);
							String matht = this.rs1.getString(4);
							String mathU = Integer.toString(unit.getUnit(Double.parseDouble(matht)));
							String eng1 = this.rs1.getString(5);
							String eng2 = this.rs1.getString(6);
							String engt = this.rs1.getString(7);
							String engU = Integer.toString(unit.getUnit(Double.parseDouble(engt)));
							String sho1 = this.rs1.getString(8);
							String sho2 = this.rs1.getString(9);
							String shot = this.rs1.getString(10);
							String shoU = Integer.toString(unit.getUnit(Double.parseDouble(shot)));
							String gen1 = this.rs1.getString(11);
							String gen2 = this.rs1.getString(12);
							String gent = this.rs1.getString(13);
							String genU = Integer.toString(unit.getUnit(Double.parseDouble(gent)));
							String ag1 = this.rs1.getString(14);
							String ag2 = this.rs1.getString(15);
							String agt = this.rs1.getString(16);
							String agU = Integer.toString(unit.getUnit(Double.parseDouble(agt)));
							String totalmarks = this.rs1.getString(17);

							int totalUnits = Integer.parseInt(mathU) + Integer.parseInt(engU) + Integer.parseInt(shoU)
									+ Integer.parseInt(genU) + Integer.parseInt(agU);

							table.addItem(new Object[] { String.valueOf(i + 1), student_name, String.valueOf(eng1),
									String.valueOf(eng2), String.valueOf(engt), String.valueOf(engU),
									String.valueOf(sho1), String.valueOf(sho2), String.valueOf(shot),
									String.valueOf(shoU), String.valueOf(math1), String.valueOf(math2),
									String.valueOf(matht), String.valueOf(mathU), String.valueOf(gen1),
									String.valueOf(gen2), String.valueOf(gent), String.valueOf(genU),
									String.valueOf(ag1), String.valueOf(ag2), String.valueOf(agt), String.valueOf(agU),
									String.valueOf(totalmarks), String.valueOf(totalUnits) }, new Integer(i));
							i++;
						}

					} catch (SQLException ee) {
						ee.printStackTrace();
					}

					// }
					// }
					new GradeAnalysisPDF().generatePDF(class_name, table, download, max);
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
					System.out.println(max + " subjects");

				} else {
					new GenerateGradeAnalysisTable(stm, stmt, rs, rs1).createAnalysisWindow(class_name,
							Integer.parseInt(max), term, year);
				}
			}

		});

		FormLayout formLayout = new FormLayout(classnames, terms, years, subjectnumber, next);
		formLayout.setSpacing(true);
		HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
		VerticalLayout layout = new VerticalLayout(horizontalLayout);
		layout.setSpacing(true);
		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
		this.setCompositionRoot(layout);
	}

	public void insertTab(TabSheet tabs) {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Grade Data Analysis")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Grade Data Analysis", VaadinIcons.UPLOAD);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

}
