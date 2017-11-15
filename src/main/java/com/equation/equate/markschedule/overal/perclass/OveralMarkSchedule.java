package com.equation.equate.markschedule.overal.perclass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import com.equation.equate.utils.allclasses.AllClasses;
import com.equation.equate.utils.date.AllYearTermsBin;
import com.equation.equate.utils.date.AllYears;
import com.equation.equate.utils.date.DateUtility;
import com.equation.equate.utils.date.GetSchoolTerm;
import com.equation.equate.utils.roundoff.RoundOff;
import com.equation.equate.utils.subjects.AllSubjects;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class OveralMarkSchedule extends CustomComponent {
	Statement stm, stmt;
	ResultSet rs, rs1;
	ArrayList<String> allClassStudents;

	public OveralMarkSchedule(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;

		ComboBox <String>classes = new ComboBox<>("<h1>Select Class:<h1/>");
		classes.setCaptionAsHtml(true);
		classes.addStyleName("classes");
		classes.setEmptySelectionAllowed(false);
		classes.setItems(new AllClasses(stm, rs).allClasses());

		ComboBox<String> terms = new ComboBox<>("<h1>Select Term:<h1/>");
		terms.setCaptionAsHtml(true);
		terms.addStyleName("classes");
		terms.setEmptySelectionAllowed(false);
		terms.setItems(AllYearTermsBin.allTermsArray());
		terms.setValue(new GetSchoolTerm().thisTerm());

		ComboBox<String> years = new ComboBox<>("<h1>Select Year:<h1/>");
		years.setCaptionAsHtml(true);
		years.setEmptySelectionAllowed(false);
		years.setItems(AllYears.makeYears());
		years.setValue(new DateUtility().getYear());

		ComboBox<String> selector = new ComboBox<>("<h1>Report Type<h1/>");
		selector.setEmptySelectionAllowed(false);
		selector.setCaptionAsHtml(true);
		selector.setItems(Arrays.asList("Raw Mark Schedule", "Adjusted Mark Schedule"));

		Button generate = new Button("Generate");
		generate.setIcon(VaadinIcons.DOWNLOAD);
		generate.addStyleName(ValoTheme.BUTTON_PRIMARY);

		generate.addClickListener((e) -> {
			String selected = (String) selector.getValue().toString();
			String myClass = (String) classes.getValue().toString();
			String term = (String) terms.getValue();
			String year = (String) years.getValue();
			String date = new DateUtility().getDate();
			if (!(selected.equals("") || myClass.equals("") || term.equals(""))) {
				if (selected.equals("Raw Mark Schedule")) {

					String query = "SELECT class_name FROM thistermmarks WHERE class_name = '" + myClass
							+ "' AND term = '" + term + "' AND year = '" + year + "' GROUP  BY class_name";
					try {
						this.rs1 = this.stmt.executeQuery(query);
						this.rs1.last();
						int rows = this.rs1.getRow();
						if (rows > 0) {
							CreateRawMarkScheduleTable scheduleTable = new CreateRawMarkScheduleTable(stm, stmt, rs,
									rs1);
							UI.getCurrent().addWindow(scheduleTable.createWindow(myClass, term, year, date));
						} else {
							allClassStudents = new GetAllStudentsForAClass(this.stm, this.stmt, this.rs, this.rs1)
									.getAllStudentForThisClass(myClass, term, year);
							GetStudentPaperOneMark paperOneMark = new GetStudentPaperOneMark(this.stm, this.stmt,
									this.rs, this.rs1);
							GetStudentPaperTwoMark paperTwoMark = new GetStudentPaperTwoMark(this.stm, this.stmt,
									this.rs, this.rs1);

							ArrayList<String> subjects = AllSubjects.allSubjects();

							for (int val = 0; val < allClassStudents.size(); val++) {
								String student_name = allClassStudents.get(val);
								ArrayList<Integer> data = new ArrayList<>();
								for (int i = 0; i < subjects.size(); i++) {
									String subject = subjects.get(i);
									int p1mark = RoundOff
											.numbber(paperOneMark.getStudentMark(subject, student_name, term, year));
									int p2mark = RoundOff
											.numbber(paperTwoMark.getStudentMark(subject, student_name, term, year));

									int total = p1mark + p2mark;

									data.add(p1mark);
									data.add(p2mark);
									data.add(total);

								}
								// int size = data.size();
								int math1 = data.get(0);
								int math2 = data.get(1);
								int matht = data.get(2);
								int eng1 = data.get(3);
								int eng2 = data.get(4);
								int engt = data.get(5);
								int sho1 = data.get(6);
								int sho2 = data.get(7);
								int shot = data.get(8);
								int gen1 = data.get(9);
								int gen2 = data.get(10);
								int gent = data.get(11);
								int ag1 = data.get(12);
								int ag2 = data.get(13);
								int agt = data.get(14);

								int totalmarks = matht + engt + shot + gent + agt;

								new InsertDataIntoThisTermMarks().insertData(stmt, myClass, student_name,
										Integer.toString(math1), Integer.toString(math2), Integer.toString(matht),
										Integer.toString(eng1), Integer.toString(eng2), Integer.toString(engt),
										Integer.toString(sho1), Integer.toString(sho2), Integer.toString(shot),
										Integer.toString(gen1), Integer.toString(gen2), Integer.toString(gent),
										Integer.toString(ag1), Integer.toString(ag2), Integer.toString(agt),
										Integer.toString(totalmarks), term, year, date);

							}
							CreateRawMarkScheduleTable scheduleTable = new CreateRawMarkScheduleTable(stm, stmt, rs,
									rs1);
							UI.getCurrent().addWindow(scheduleTable.createWindow(myClass, term, year, date));

						}

					} catch (SQLException ee) {
						ee.printStackTrace();
					}

				} else {

					String query = "SELECT class_name FROM thistermmarks WHERE class_name = '" + myClass
							+ "' AND term = '" + term + "' AND year = '" + year + "' GROUP  BY class_name";
					try {
						this.rs1 = this.stmt.executeQuery(query);
						this.rs1.last();
						int rows = this.rs1.getRow();
						if (rows > 0) {
							CreateMarkScheduleTable scheduleTable = new CreateMarkScheduleTable(stm, stmt, rs, rs1);
							UI.getCurrent().addWindow(scheduleTable.createWindow(myClass, term, year, date));
						} else {
							allClassStudents = new GetAllStudentsForAClass(this.stm, this.stmt, this.rs, this.rs1)
									.getAllStudentForThisClass(myClass, term, year);
							GetStudentPaperOneMark paperOneMark = new GetStudentPaperOneMark(this.stm, this.stmt,
									this.rs, this.rs1);
							GetStudentPaperTwoMark paperTwoMark = new GetStudentPaperTwoMark(this.stm, this.stmt,
									this.rs, this.rs1);

							ArrayList<String> subjects = AllSubjects.allSubjects();

							for (int val = 0; val < allClassStudents.size(); val++) {
								String student_name = allClassStudents.get(val);
								ArrayList<Integer> data = new ArrayList<>();
								for (int i = 0; i < subjects.size(); i++) {
									String subject = subjects.get(i);
									int p1mark = RoundOff
											.numbber(paperOneMark.getStudentMark(subject, student_name, term, year));
									int p2mark = RoundOff
											.numbber(paperTwoMark.getStudentMark(subject, student_name, term, year));

									int total = p1mark + p2mark;

									data.add(p1mark);
									data.add(p2mark);
									data.add(total);

								}
								// int size = data.size();
								int math1 = data.get(0);
								int math2 = data.get(1);
								int matht = data.get(2);
								int eng1 = data.get(3);
								int eng2 = data.get(4);
								int engt = data.get(5);
								int sho1 = data.get(6);
								int sho2 = data.get(7);
								int shot = data.get(8);
								int gen1 = data.get(9);
								int gen2 = data.get(10);
								int gent = data.get(11);
								int ag1 = data.get(12);
								int ag2 = data.get(13);
								int agt = data.get(14);

								int totalmarks = matht + engt + shot + gent + agt;

								new InsertDataIntoThisTermMarks().insertData(stmt, myClass, student_name,
										Integer.toString(math1), Integer.toString(math2), Integer.toString(matht),
										Integer.toString(eng1), Integer.toString(eng2), Integer.toString(engt),
										Integer.toString(sho1), Integer.toString(sho2), Integer.toString(shot),
										Integer.toString(gen1), Integer.toString(gen2), Integer.toString(gent),
										Integer.toString(ag1), Integer.toString(ag2), Integer.toString(agt),
										Integer.toString(totalmarks), term, year, date);

							}
							CreateMarkScheduleTable scheduleTable = new CreateMarkScheduleTable(stm, stmt, rs, rs1);
							UI.getCurrent().addWindow(scheduleTable.createWindow(myClass, term, year, date));

						}

					} catch (SQLException ee) {
						ee.printStackTrace();
					}

				}
			} else {
				new Notification(
						"<h1>Request Rejected!<br/>Empty Fields have been detected<br/>Submit all the required information.<h1/>",
						"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			}
		});

		FormLayout formLayout = new FormLayout(classes, terms, years, selector, generate);
		formLayout.setSpacing(true);
		HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
		VerticalLayout layout = new VerticalLayout(horizontalLayout);
		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
		this.setCompositionRoot(layout);

	}

	public void insertTab(TabSheet tabs) {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Mark Schedule")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Mark Schedule", VaadinIcons.DOWNLOAD);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

}
