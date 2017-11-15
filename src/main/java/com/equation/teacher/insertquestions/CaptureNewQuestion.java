package com.equation.teacher.insertquestions;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.school.classes.collection.AllClasses;
import com.equation.school.subjects.AllSchoolSubjects;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class CaptureNewQuestion extends CustomComponent implements View {

	ResultSet rs, rs1;
	Statement stm, stmt;
	String tablename = null;
	TabSheet tabs;
	String schoolname;
	private RichTextArea richTextArea;
	private TextField answer1;
	private TextField answer2;
	private TextField answer3;
	private TextField answer4;
	private Button submit;
	private ComboBox<String> subjects;
	private ComboBox<String> grades;

	public CaptureNewQuestion(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, TabSheet tabs,
			String schoolname) {
		this.rs = rs;
		this.stm = stm;
		this.schoolname = schoolname;

		this.rs1 = rs1;
		this.stmt = stmt;

		this.tabs = tabs;

		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);

		answer1 = new TextField("Correct Answer");
		answer1.setRequiredIndicatorVisible(true);
		answer2 = new TextField("Distractor 1");
		answer2.setRequiredIndicatorVisible(true);
		answer3 = new TextField("Distractor 2");
		answer3.setRequiredIndicatorVisible(true);
		answer4 = new TextField("Distractor 3");
		answer4.setRequiredIndicatorVisible(true);

		submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.setIcon(VaadinIcons.PLAY);

		VerticalLayout anwers = new VerticalLayout(answer1, answer2, answer3, answer4, submit);
		anwers.setSpacing(true);
		anwers.addStyleName("questions_answers_panel");

		richTextArea = new RichTextArea("Question");
		richTextArea.setSizeFull();
		richTextArea.addStyleName(ValoTheme.TEXTAREA_HUGE);
		richTextArea.addStyleName("questions_textarea");
		richTextArea.setResponsive(true);
		// richTextArea.setReadOnly(true);

		HorizontalLayout horizontalLayout = new HorizontalLayout(richTextArea, anwers);
		horizontalLayout.setSpacing(true);
		horizontalLayout.setSizeFull();

		subjects = new ComboBox<>("Select Subject");
		subjects.setEmptySelectionAllowed(false);
		subjects.setItems(new AllSchoolSubjects(rs, rs1, stm, stmt).allSchoolSubjects());

		grades = new ComboBox<>("Class Name:");
		grades.setEmptySelectionAllowed(false);
		grades.setItems(new AllClasses(stm, rs).classesCollection(schoolname));

		HorizontalLayout horizontalLayout2 = new HorizontalLayout(subjects, grades);
		horizontalLayout2.setSpacing(true);
		horizontalLayout2.addStyleName("questions_answers_panel_settings");
		// horizontalLayout2.setWidth("100%");
		submit.addClickListener((e) -> {
			String subject = (String) subjects.getValue();
			String grade = (String) grades.getValue();
			if (!(subject.equals("") || grade.equals("") || richTextArea.getValue().equals("")
					|| answer1.getValue().equals("") || answer2.getValue().equals("") || answer3.getValue().equals("")
					|| answer4.getValue().equals(""))) {
				String subject_id = new AllSchoolSubjects(rs, rs1, stm, stmt).getSubjectIDFor(subject);

				String content = (String) richTextArea.getValue();
				String tablename = (subject.replace(" ", "_")).toLowerCase();
				InsertDataIntoSubjectTable dataIntoSubjectTable = new InsertDataIntoSubjectTable(stm);
				String correctAnswer = answer1.getValue();
				String destractor1 = answer2.getValue();
				String destractor2 = answer3.getValue();
				String destractor3 = answer4.getValue();
				dataIntoSubjectTable.insertData(tablename, content, correctAnswer, destractor1, destractor2,
						destractor3, grade, subject_id);

				answer1.clear();
				answer2.clear();
				answer3.clear();
				answer4.clear();
				richTextArea.clear();

			} else {
				new Notification("<h1>A blank field has been detected!<br/><br/>The question cannot be submitted!<h1/>",
						"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			}

		});

		layout.addComponents(horizontalLayout2, horizontalLayout);
		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
		this.setCompositionRoot(layout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("New Question")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "New Question", VaadinIcons.QUESTION);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}
}