package com.equation.student.inclasstest.view;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import com.equation.student.banner.StudentInClassWritingLogo;
import com.equation.student.inclass.postmark.PostInclassOverallMark;
import com.equation.student.inclasstest.getquestions.GetInClassTestQuestions;
import com.equation.utils.application.urls.ApplicationURLS;
import com.equation.utils.date.DateUtility;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.RichTextArea;
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
@SuppressWarnings({ "serial", "deprecation" })
public class StudentInClassTestView extends CustomComponent implements View {
	String studentname, subject, totalQuestions, totalMark, peroid, teacher, topic, classname, due, testid;
	ResultSet rs1, rs;
	Statement stm, stmt;
	ArrayList<String> questions;
	ArrayList<String> answers;
	ArrayList<String> unsorted;
	ArrayList<String> dummy1;
	ArrayList<String> dummy2;
	ArrayList<String> dummy3;
	private RichTextArea[] area;
	private ComboBox<String>[] answersCombo;
	private Button submit;
	Label labelTime = new Label();
	Timer timer = new Timer();
	VerticalLayout questionSteet;

	@SuppressWarnings({ "unchecked" })
	public StudentInClassTestView(String studentname, String subject, String totalQuestions, String totalMark,
			String peroid, String teacher, String topic, String classname, String due, ResultSet rs1, ResultSet rs,
			Statement stm, Statement stmt, String testid) {
		this.studentname = studentname;
		this.subject = subject;
		this.totalQuestions = totalQuestions;
		this.totalMark = totalMark;
		this.peroid = peroid;
		this.teacher = teacher;
		this.topic = topic;
		this.classname = classname;
		this.due = due;
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.testid = testid;

		questionSteet = new VerticalLayout();
		questionSteet.setSpacing(true);
		questionSteet.addStyleName(ValoTheme.LAYOUT_WELL);

		submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_LARGE);
		submit.addStyleName("answers_submit_button");

		GetInClassTestQuestions testQuestions = new GetInClassTestQuestions(this.testid, this.stm, this.rs);
		questions = testQuestions.getQuestions();
		answers = testQuestions.getAnswers();
		unsorted = testQuestions.getUnsorted();
		dummy1 = testQuestions.getDummy1();
		dummy2 = testQuestions.getDummy2();
		dummy3 = testQuestions.getDummy3();
		ArrayList<String> dummies = new ArrayList<>();

		int size = questions.size();
		// int dummysiz = dummies.size();
		if (size > 0) {
			area = new RichTextArea[size];
			answersCombo = new ComboBox[size];
			for (int i = 0; i < size; i++) {
				area[i] = new RichTextArea();
				area[i].addStyleName("game_questions_area");
				String quection = questions.get(i);
				area[i].setValue(quection);
				area[i].setReadOnly(true);
				area[i].setSizeFull();
				answersCombo[i] = new ComboBox<>("Select An Answer:");
				answersCombo[i].setEmptySelectionAllowed(false);
				answersCombo[i].setRequiredIndicatorVisible(true);

				dummies.add(dummy1.get(i));
				dummies.add(dummy2.get(i));
				dummies.add(dummy3.get(i));
				dummies.add(answers.get(i));

				Collections.shuffle(dummies);

				answersCombo[i].setItems(dummies);
				answersCombo[i].setEmptySelectionAllowed(false);
				dummies.clear();

				Label lbl = new Label("  Question " + (i + 1) + ".");
				lbl.addStyleName(ValoTheme.LABEL_H2);
				HorizontalLayout horizontalLayout = new HorizontalLayout(lbl, area[i], answersCombo[i]);
				horizontalLayout.setSpacing(true);

				questionSteet.addComponent(horizontalLayout);
			}
			questionSteet.addComponent(submit);
			submit.addClickListener((e) -> {
				timer.cancel();
				timer.purge();
				double size1 = questions.size();
				Table table = new Table();
				table.addContainerProperty("Question #", String.class, null);
				table.addContainerProperty("Correct Answers", String.class, null);
				table.addContainerProperty("My Answers", String.class, null);
				table.addContainerProperty("Score", CheckBox.class, null);
				Button proceed = new Button("Proceed");
				double totalmark = 0, percentage = 0;
				for (int i = 0; i < size1; i++) {
					// String question = questions.get(i);
					String correctAnswer = answers.get(i);
					int questionNumber = i + 1;
					String myaswers = (String) answersCombo[i].getValue();
					String myrealAnswer = (myaswers != null && !myaswers.isEmpty()) ? myaswers : "not attempted";
					CheckBox mark = new CheckBox("");
					if (myrealAnswer.equals(correctAnswer)) {
						totalmark += 1;
						mark.setValue(true);

					} else {
						mark.setValue(false);
						totalmark += 0;
					}
					mark.setEnabled(false);
					// studentAnswers.add(ans);
					table.addItem(new Object[] { "" + questionNumber, correctAnswer, myrealAnswer, mark },
							new Integer(i));
				}

				percentage = (totalmark / size) * 100;
				String[] val = studentname.split(" ");

				// InsertDataIntoActivityTable activityTable = new
				// InsertDataIntoActivityTable(stm);
				// activityTable.insertData(val[0].toLowerCase().trim(),
				// subject, grade, percentage);
				DateUtility dateUtility = new DateUtility();
				String date = dateUtility.getDate();
				String time = dateUtility.getTime();

				PostInclassOverallMark postInclassOverallMark = new PostInclassOverallMark(this.stm);
				postInclassOverallMark.insertData(val[0].toLowerCase().trim(), this.testid, (int) totalmark, date,
						time);

				// System.out.println(totalmark + " p " + percentage);
				if (percentage >= 50) {
					proceed.setCaption("EXCELLENT");
					proceed.addStyleName(ValoTheme.BUTTON_FRIENDLY);
					proceed.setIcon(VaadinIcons.THUMBS_UP);
				} else {
					proceed.setCaption("WELL DONE");
					proceed.setIcon(VaadinIcons.THUMBS_DOWN);
					proceed.addStyleName(ValoTheme.BUTTON_DANGER);
				}

				table.setFooterVisible(true);
				table.setColumnFooter("Question #", "Total");
				table.setColumnFooter("My Answers", "" + (int) totalmark + " OUT OF " + (int) size);
				table.setColumnFooter("Score", "" + percentage + "%");
				table.setPageLength(table.size());
				table.setSelectable(true);
				table.setSizeFull();

				// table.addStyleName("students_gender_table");

				Window answerWindow = new Window("Here Are The Results");
				HorizontalLayout horizontalLayout = new HorizontalLayout();
				table.addContextClickListener((ee) -> {
					horizontalLayout.removeAllComponents();
					int row = (int) table.getValue();
					Item item = table.getItem(row);
					String questionNumber = (String) item.getItemProperty("Question #").getValue().toString();

					String questionText = questions.get(row);
					RichTextArea area = new RichTextArea();
					area.setValue(questionText);
					area.setReadOnly(true);
					VerticalLayout vlayout = new VerticalLayout(area);
					vlayout.setSpacing(true);

					Panel p = new Panel("Question Number " + questionNumber);
					p.setContent(vlayout);

					PopupView popup = new PopupView(null, p);
					horizontalLayout.addComponent(popup);
					popup.setPopupVisible(true);

					popup.addPopupVisibilityListener(event -> {
						if (event.isPopupVisible()) {
							vlayout.removeAllComponents();
							vlayout.addComponent(area);
						}
					});
				});

				horizontalLayout.addStyleName("poppupviewcontainer");
				VerticalLayout subContent = new VerticalLayout(horizontalLayout, table, proceed);
				subContent.setMargin(true);
				subContent.setSpacing(true);
				answerWindow.setWidth("50%");
				answerWindow.setHeight("50%");
				answerWindow.setContent(subContent);
				answerWindow.center();
				answerWindow.setModal(true);
				answerWindow.setClosable(false);
				UI.getCurrent().addWindow((answerWindow));

				proceed.addClickListener((ev) -> {
					questionSteet.removeAllComponents();
					UI.getCurrent().removeWindow(answerWindow);
					answerWindow.close();

					UI.getCurrent().getNavigator().navigateTo(
							(String) UI.getCurrent().getSession().getAttribute(ApplicationURLS.STUDENT_MAIN_VIEW));

				});

			});
		} else {
			new Notification(
					"<h1>" + subject.toUpperCase()
							+ " does not have any questions for now.<br/>Come back next time.<br/>Bye<h1/>",
					"", Notification.Type.HUMANIZED_MESSAGE, true).show(Page.getCurrent());
		}

		VerticalLayout layout = new VerticalLayout(createTopBanner(), questionSteet);
		layout.setSpacing(true);
		setCompositionRoot(layout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		timer.schedule(new TimerTask() {

			int second = Integer.parseInt(peroid);

			@Override
			public void run() {
				UI.getCurrent().access(() -> labelTime.setValue("Application will close in " + second + " seconds."));
				int howmanyAttempted = answersCombo.length;
				if (second <= 0) {
					int length = answersCombo.length;
					for (int i = 0; i < length; i++) {
						answersCombo[i].setEnabled(false);
						String value = answersCombo[i].getValue();
						if (value != null && !value.isEmpty()) {

						} else {
							howmanyAttempted -= 1;
						}

					}
					timer.cancel();
					timer.purge();
					String attempted = (howmanyAttempted < answersCombo.length) ? "" + howmanyAttempted : "all";
					UI.getCurrent()
							.access(() -> new Notification(
									"<h1>Thank you for writing this online test!<br/>You have attempted " + attempted
											+ " questions!<br/>Click here to view your results.<h1/>",
									"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent()));
					procesResults();
				}

				second--;
			}

		}, 0, 60000);

	}

	HorizontalLayout createTopBanner() {
		return new StudentInClassWritingLogo(studentname, subject, totalQuestions, totalMark, peroid, teacher, topic,
				classname, due);
	}

	private void procesResults() {
		double size1 = questions.size();
		Table table = new Table();
		table.addContainerProperty("Question #", String.class, null);
		table.addContainerProperty("Correct Answers", String.class, null);
		table.addContainerProperty("My Answers", String.class, null);
		table.addContainerProperty("Score", CheckBox.class, null);
		Button proceed = new Button("Proceed");
		double totalmark = 0, percentage = 0;
		for (int i = 0; i < size1; i++) {
			// String question = questions.get(i);
			String correctAnswer = answers.get(i);
			int questionNumber = i + 1;
			String myaswers = (String) answersCombo[i].getValue();
			String myrealAnswer = (myaswers != null && !myaswers.isEmpty()) ? myaswers : "not attempted";
			CheckBox mark = new CheckBox("");
			if (myrealAnswer.equals(correctAnswer)) {
				totalmark += 1;
				mark.setValue(true);

			} else {
				mark.setValue(false);
				totalmark += 0;
			}
			mark.setEnabled(false);
			// studentAnswers.add(ans);
			table.addItem(new Object[] { "" + questionNumber, correctAnswer, myrealAnswer, mark }, new Integer(i));
		}

		percentage = (totalmark / size1) * 100;
		String[] val = studentname.split(" ");

		// InsertDataIntoActivityTable activityTable = new
		// InsertDataIntoActivityTable(stm);
		// activityTable.insertData(val[0].toLowerCase().trim(),
		// subject, grade, percentage);
		DateUtility dateUtility = new DateUtility();
		String date = dateUtility.getDate();
		String time = dateUtility.getTime();

		PostInclassOverallMark postInclassOverallMark = new PostInclassOverallMark(this.stm);
		postInclassOverallMark.insertData(val[0].toLowerCase().trim(), this.testid, (int) totalmark, date, time);

		// System.out.println(totalmark + " p " + percentage);
		if (percentage >= 50) {
			proceed.setCaption("EXCELLENT");
			proceed.addStyleName(ValoTheme.BUTTON_FRIENDLY);
			proceed.setIcon(VaadinIcons.THUMBS_UP);
		} else {
			proceed.setCaption("WELL DONE");
			proceed.setIcon(VaadinIcons.THUMBS_DOWN);
			proceed.addStyleName(ValoTheme.BUTTON_DANGER);
		}

		table.setFooterVisible(true);
		table.setColumnFooter("Question #", "Total");
		table.setColumnFooter("My Answers", "" + (int) totalmark + " OUT OF " + (int) size1);
		table.setColumnFooter("Score", "" + percentage + "%");
		table.setPageLength(table.size());
		table.setSelectable(true);
		table.setSizeFull();

		// table.addStyleName("students_gender_table");

		Window answerWindow = new Window("Here Are The Results");
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		table.addContextClickListener((ee) -> {
			horizontalLayout.removeAllComponents();
			int row = (int) table.getValue();
			Item item = table.getItem(row);
			String questionNumber = (String) item.getItemProperty("Question #").getValue().toString();

			String questionText = questions.get(row);
			RichTextArea area = new RichTextArea();
			area.setValue(questionText);
			area.setReadOnly(true);
			VerticalLayout vlayout = new VerticalLayout(area);
			vlayout.setSpacing(true);

			Panel p = new Panel("Question Number " + questionNumber);
			p.setContent(vlayout);

			PopupView popup = new PopupView(null, p);
			horizontalLayout.addComponent(popup);
			popup.setPopupVisible(true);

			popup.addPopupVisibilityListener(event -> {
				if (event.isPopupVisible()) {
					vlayout.removeAllComponents();
					vlayout.addComponent(area);
				}
			});
		});

		horizontalLayout.addStyleName("poppupviewcontainer");
		VerticalLayout subContent = new VerticalLayout(horizontalLayout, table, proceed);
		subContent.setMargin(true);
		subContent.setSpacing(true);
		answerWindow.setWidth("50%");
		answerWindow.setHeight("50%");
		answerWindow.setContent(subContent);
		answerWindow.center();
		answerWindow.setModal(true);
		answerWindow.setClosable(false);
		UI.getCurrent().addWindow((answerWindow));

		proceed.addClickListener((ev) -> {
			questionSteet.removeAllComponents();
			UI.getCurrent().removeWindow(answerWindow);
			answerWindow.close();

			UI.getCurrent().getNavigator()
					.navigateTo((String) UI.getCurrent().getSession().getAttribute(ApplicationURLS.STUDENT_MAIN_VIEW));

		});
	}

}
