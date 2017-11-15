package com.equation.teacher.assinment.set;

import java.sql.ResultSet;
import java.sql.Statement;

import com.eqauation.assessment.assignments.insert.InsertAssignmentDetails;
import com.eqauation.assessment.teacher.createasssignment.AssignmentTypes;
import com.equation.utils.date.DateUtility;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial", "deprecation" })
public class SetOnPageAssinment extends Window {

	ResultSet rs, rs1;
	Statement stm, stmt;
	String tablename = null;

	String subject, topic, class_id, total_mark, subject_id, term, year, date_posted, due;
	String grade;
	private TextField numberOfQuestions;
	private Button go;

	public SetOnPageAssinment(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, String subject, String grade,
			String topic, String class_id, String total_mark, String subject_id, String term, String year,
			String date_posted, String due) {
		this.rs = rs;
		this.stm = stm;

		this.rs1 = rs1;
		this.stmt = stmt;

		this.grade = grade;
		this.subject = subject;

		this.topic = topic;
		this.class_id = class_id;
		this.total_mark = total_mark;
		this.subject_id = subject_id;
		this.term = term;
		this.year = year;
		this.date_posted = date_posted;
		this.due = due;

		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		VerticalLayout layout2 = new VerticalLayout();
		layout2.setSpacing(true);

		numberOfQuestions = new TextField("Total Number of Questions");
		numberOfQuestions.setRequiredIndicatorVisible(true);
		// numberOfQuestions.addValidator(new IntegerValidator("An integer is
		// required! Eg 100"));
		go = new Button("Go");
		go.addStyleName("continuous_search_button");
		go.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		go.setIcon(VaadinIcons.PLAY);
		go.addClickListener((e) -> {
			layout2.removeAllComponents();
			if (!numberOfQuestions.getValue().equals("")) {
				int totalNumber = Integer.parseInt(numberOfQuestions.getValue());
				RichTextArea[] questionSheet = new RichTextArea[totalNumber];
				Label[] questionNumberlbl = new Label[totalNumber];
				TextField[] correctAnswerField = new TextField[totalNumber];
				TextField[] distractor1 = new TextField[totalNumber];
				TextField[] distractor2 = new TextField[totalNumber];
				TextField[] distractor3 = new TextField[totalNumber];

				for (int i = 0; i < totalNumber; i++) {
					questionNumberlbl[i] = new Label("Question #" + (i + 1));
					questionSheet[i] = new RichTextArea("Question");
					correctAnswerField[i] = new TextField("Correct Answer");
					correctAnswerField[i].setRequiredIndicatorVisible(true);
					distractor1[i] = new TextField("Distractor 1");
					distractor1[i].setRequiredIndicatorVisible(true);
					distractor2[i] = new TextField("Distractor 2");
					distractor2[i].setRequiredIndicatorVisible(true);
					distractor3[i] = new TextField("Distractor 3");
					distractor3[i].setRequiredIndicatorVisible(true);

					FormLayout formLayout = new FormLayout(correctAnswerField[i], distractor1[i], distractor2[i],
							distractor3[i]);
					formLayout.setSpacing(true);
					HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
					VerticalLayout answersVertical = new VerticalLayout(horizontalLayout);
					answersVertical.setSpacing(true);
					answersVertical.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);

					Panel answersPanel = new Panel("Answers For Question " + (i + 1));
					answersPanel.setContent(answersVertical);
					answersPanel.setIcon(VaadinIcons.PENCIL);

					HorizontalLayout eachQuestionContainer = new HorizontalLayout(questionNumberlbl[i],
							questionSheet[i], answersPanel);
					eachQuestionContainer.setSpacing(true);
					layout2.addComponent(eachQuestionContainer);
				}

				Button submit = new Button("Submit");
				submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
				submit.addClickListener((ee) -> {
					int check = 0;
					for (int i = 0; i < totalNumber; i++) {
						String actualQuestion = questionSheet[i].getValue();
						String actualAnswer = correctAnswerField[i].getValue();
						String d1 = distractor1[i].getValue();
						String d2 = distractor2[i].getValue();
						String d3 = distractor3[i].getValue();

						if (!(actualQuestion.equals("") || actualAnswer.equals("") || d1.equals("") || d2.equals("")
								|| d3.equals(""))) {
							check++;
						} else {
							new Notification("Error",
									"Check Question Number " + (i + 1)
											+ ".<br/>There could be some empty field(s) detected!",
									Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
							break;
						}
					}
					if (check == totalNumber) {
						DateUtility dateUtility = new DateUtility();
						String date = dateUtility.getDate();
						String time = dateUtility.getTime();
						UI.getCurrent().addWindow(createConfirmationWindow(subject, grade, totalNumber, date, time,
								questionSheet, correctAnswerField, distractor1, distractor2, distractor3));
					}
				});

				layout2.addComponent(submit);

			} else {
				new Notification("Error", "A Blank field has been detected!<br/>Fill in all required fields!",
						Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
			}
		});

		HorizontalLayout horizontalLayout2 = new HorizontalLayout(numberOfQuestions, go);
		horizontalLayout2.setSpacing(true);
		horizontalLayout2.addStyleName("questions_answers_panel_settings");

		layout.addComponents(horizontalLayout2, layout2);
		layout.setComponentAlignment(layout2, Alignment.MIDDLE_CENTER);
		this.setSizeFull();
		this.setContent(layout);
	}

	public Window createConfirmationWindow(String subject, String class_name, int number_of_questions, String date,
			String time, RichTextArea[] questionSheet, TextField[] correctAnswerField, TextField[] distractor1,
			TextField[] distractor2, TextField[] distractor3) {
		Window window = new Window("Confirm");

		Button cancel = new Button("Cancel");
		cancel.addStyleName(ValoTheme.BUTTON_DANGER);
		cancel.setIcon(VaadinIcons.CLOSE);
		cancel.addClickListener((e) -> {
			UI.getCurrent().removeWindow(window);
			window.close();
		});
		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.setIcon(VaadinIcons.UPLOAD);
		submit.addClickListener((e) -> {
			InsertAssignmentDetails assignmentDetails = new InsertAssignmentDetails();

			assignmentDetails.insertData(stm, topic, class_id, total_mark, subject_id, term, year, date_posted, due,
					AssignmentTypes.ONLINE);
			try {
				Thread.currentThread();
				Thread.sleep(500);
			} catch (InterruptedException ee) {
				ee.printStackTrace();
			}
			int assignment_id = assignmentDetails.getRowID(rs, stm, time, class_name, time, subject, subject,
					class_name, date, date, AssignmentTypes.ONLINE);
			InsertOnPageAssignment newAssignment = new InsertOnPageAssignment(stm);
			for (int i = 0; i < number_of_questions; i++) {
				String actualQuestion = questionSheet[i].getValue();
				String actualAnswer = correctAnswerField[i].getValue();
				String d1 = distractor1[i].getValue();
				String d2 = distractor2[i].getValue();
				String d3 = distractor3[i].getValue();
				newAssignment.insertData(assignment_id, actualQuestion, actualAnswer, d1, d2, d3);
			}
			UI.getCurrent().removeWindow(window);
			window.close();
			new Notification("<h4>Information</h4>",
					"Subject :" + subject + "<br/>Number of questions :" + number_of_questions + "<br/>Total Mark :"
							+ total_mark + "<br/>Assignment Submitted",
					Notification.Type.TRAY_NOTIFICATION, true).show(Page.getCurrent());

		});
		HorizontalLayout horizontalLayout1 = new HorizontalLayout(submit, cancel);
		horizontalLayout1.setSpacing(true);
		FormLayout formLayout = new FormLayout(horizontalLayout1);
		formLayout.setSpacing(true);
		window.setWidth("50%");
		window.setHeight("60%");
		window.center();
		window.setModal(true);
		HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
		VerticalLayout layout = new VerticalLayout(horizontalLayout);
		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
		Panel p = new Panel(layout);
		p.setCaption("Assignent Attributes");
		p.setIcon(VaadinIcons.SUN_DOWN);
		window.setContent(p);
		return window;
	}

	public void insertTab(TabSheet tabs) {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("New Assignment")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "New Assignment", VaadinIcons.QUESTION);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}
}