package com.equation.teacher.assignments.performance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.equation.school.classes.collection.AllClasses;
import com.equation.school.subjects.AllSchoolSubjects;
import com.equation.teacher.inclass.assignedsubjects.GetAllAssignedSubjects;
import com.equation.teacher.inclass.performance.AssignmentPerformanceTable;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.data.Item;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("deprecation")
public class AssignmentPerfornamceReport {
	Statement stm, stmt;
	ResultSet rs, rs1;
	private String subject;
	private String grade;
	String schoolname;

	public AssignmentPerfornamceReport(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String schoolname) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.schoolname = schoolname;
	}

	public Window createSearchWindow() {
		Window window = new Window();
		ComboBox<String> subjects = new ComboBox<>("Select Subject");
		subjects.setRequiredIndicatorVisible(true);
		subjects.setEmptySelectionAllowed(false);
		subjects.setItems(new AllSchoolSubjects(rs, rs1, stm, stmt).allSchoolSubjects());
		ComboBox<String> grades = new ComboBox<>("Select Class:");
		grades.setRequiredIndicatorVisible(true);
		grades.setEmptySelectionAllowed(false);
		grades.setItems(new AllClasses(stm, rs).classesCollection(schoolname));

		Button cancel = new Button("Cancel");
		cancel.addStyleName(ValoTheme.BUTTON_DANGER);
		cancel.setIcon(VaadinIcons.CLOSE);
		cancel.addClickListener((ee) -> {
			UI.getCurrent().removeWindow(window);
			window.close();
		});

		Button submit = new Button("Submit");
		submit.setIcon(VaadinIcons.SEARCH);
		submit.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		submit.addClickListener((er) -> {
			if (!(subjects.getValue().toString().equals("") || grades.getValue().toString().equals(""))) {
				subject = (String) subjects.getValue().toString();
				grade = (String) grades.getValue().toString();

				String query = "SELECT * FROM  assignment_details,school_subjects,classes WHERE school_subjects.subject = '"
						+ subject + "' AND classes.classname = '" + grade + "'";
				try {
					rs1 = stmt.executeQuery(query);
					rs1.last();
					int rows = rs1.getRow();
					if (rows > 0) {
						UI.getCurrent().removeWindow(window);
						window.close();
						UI.getCurrent().addWindow(createContentWindow());

					} else {
						new Notification(
								"<h1>We could not find any in class test for " + subject
										+ " you know.<br/>You can try to look for an in class test for another subject!</h1>",
								"", Notification.Type.WARNING_MESSAGE, true).show(Page.getCurrent());
					}

				} catch (SQLException ee) {
					ee.printStackTrace();
				}

			} else {
				new Notification(
						"<h1>Please, do not leave any blank field!<br/><br/>I want to help you find an iin class test that is not yet overdue<h1/>",
						"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			}

		});
		HorizontalLayout horizontalLayout = new HorizontalLayout(submit, cancel);
		horizontalLayout.setSpacing(true);
		FormLayout formLayout = new FormLayout(subjects, grades, horizontalLayout);
		formLayout.setSpacing(true);
		formLayout.addStyleName(ValoTheme.LAYOUT_WELL);

		VerticalLayout layout = new VerticalLayout(formLayout);
		window.setContent(layout);
		window.center();
		window.setWidth("40%");
		window.setHeight("40%");
		window.setModal(true);
		return window;
	}

	Window createContentWindow() {
		GetAllAssignedSubjects allTestedSubjects = new GetAllAssignedSubjects(stm, stmt, rs, rs1, subject, grade);

		Table table = new Table();
		table.addContainerProperty("#", String.class, null);
		table.addContainerProperty("Topic", String.class, null);
		// table.addContainerProperty("Number of Questions", String.class,
		// null);
		table.addContainerProperty("Total Mark", String.class, null);
		// table.addContainerProperty("Prepared By", String.class, null);
		table.addContainerProperty("Dated", String.class, null);
		table.addContainerProperty("Due Date", String.class, null);
		table.addContainerProperty("Assignment Type", String.class, null);

		ArrayList<String> topics = allTestedSubjects.getTopics();
		ArrayList<String> testids = allTestedSubjects.getTestids();
		// ArrayList<String> numberOfQuestions =
		// allTestedSubjects.getNumberOfQuestions();
		ArrayList<String> totalMarks = allTestedSubjects.getTotalMarks();
		// ArrayList<String> teachers = allTestedSubjects.getTeachers();
		ArrayList<String> dates = allTestedSubjects.getDates();
		ArrayList<String> dues = allTestedSubjects.getDues();
		ArrayList<String> types = allTestedSubjects.getTypes();

		int sizz = topics.size();
		for (int i = 0; i < sizz; i++) {
			String test_id = testids.get(i);
			String topic = topics.get(i);
			// String number_of_questions = numberOfQuestions.get(i);
			String total_mark = totalMarks.get(i);
			// String prepared_by = teachers.get(i);
			String dated = dates.get(i);
			String due = dues.get(i);
			String type = types.get(i);

			table.addItem(new Object[] { String.valueOf(test_id),
					topic, /* String.valueOf(number_of_questions) */
					String.valueOf(total_mark), /* prepared_by, */ dated, due, type }, new Integer(i));
		}

		table.setFooterVisible(true);
		int tableSize = table.size();
		String narration = (tableSize > 1) ? " Records" : " Record";
		table.setColumnFooter("#", String.valueOf(tableSize) + narration);

		table.setPageLength(tableSize);
		table.setSelectable(true);
		table.setColumnCollapsingAllowed(true);
		table.addContextClickListener((e) -> {
			int row = (int) table.getValue();
			Item item = table.getItem(row);
			String test_id = (String) item.getItemProperty("#").getValue().toString();
			AssignmentPerformanceTable classPerformanceTable = new AssignmentPerformanceTable(rs, rs1, stm, stmt);
			classPerformanceTable.searchPerformanceReport(test_id);
		});

		Window window = new Window();
		window.center();
		window.setSizeFull();
		window.setContent(table);

		return window;
	}

}
