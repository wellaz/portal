package com.equation.teacher.multiplechoice.questions.set;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.equation.school.classes.collection.AllClasses;
import com.equation.school.subjects.AllSchoolSubjects;
import com.equation.teacher.multiplechoice.records.InsertMultipleChoiceQuestion;
import com.equation.teacher.multiplechoice.records.InsertNewInClassRecord;
import com.equation.utils.date.DateUtility;
import com.equation.utils.date.ExamPeriodUnit;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial", "deprecation" })
public class SetMultipleChoiceExam extends CustomComponent implements View {

	ResultSet rs, rs1;
	Statement stm, stmt;
	String tablename = null;
	TabSheet tabs;
	String schoolname;

	private ComboBox<String> subjects;
	private ComboBox<String> grades;
	private TextField numberOfQuestions;
	private Button go;

	public SetMultipleChoiceExam(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, TabSheet tabs,
			String schoolname) {
		this.rs = rs;
		this.stm = stm;

		this.rs1 = rs1;
		this.stmt = stmt;

		this.tabs = tabs;
		this.schoolname = schoolname;

		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		VerticalLayout layout2 = new VerticalLayout();
		layout2.setSpacing(true);

		subjects = new ComboBox<>("<h1>Select Subject<h1/>");
		subjects.setCaptionAsHtml(true);
		subjects.setRequiredIndicatorVisible(true);
		subjects.setEmptySelectionAllowed(false);
		subjects.setItems(new AllSchoolSubjects(rs, rs1, stm, stmt).allSchoolSubjects());

		grades = new ComboBox<>("<h1>Select Class<h1/>");
		grades.setCaptionAsHtml(true);
		grades.setRequiredIndicatorVisible(true);
		grades.setEmptySelectionAllowed(false);
		grades.setItems(new AllClasses(stmt, rs1).classesCollection(schoolname));

		numberOfQuestions = new TextField("<h1>Total Number of Questions<h1/>");
		numberOfQuestions.setCaptionAsHtml(true);
		numberOfQuestions.setRequiredIndicatorVisible(true);
		// numberOfQuestions.addValidator(new IntegerValidator("An integer is
		// required! Eg 100"));
		go = new Button("Go");
		go.addStyleName("continuous_search_button");
		go.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		go.setIcon(VaadinIcons.PLAY);
		go.addClickListener((e) -> {
			layout2.removeAllComponents();
			if (!(subjects.getValue().equals("") || grades.getValue().equals("")
					|| numberOfQuestions.getValue().equals(""))) {
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
					correctAnswerField[i].setIcon(VaadinIcons.CHECK_CIRCLE_O);
					distractor1[i] = new TextField("Distractor 1");
					distractor1[i].setRequiredIndicatorVisible(true);
					distractor1[i].setIcon(VaadinIcons.CLOSE);
					distractor2[i] = new TextField("Distractor 2");
					distractor2[i].setRequiredIndicatorVisible(true);
					distractor2[i].setIcon(VaadinIcons.CLOSE);
					distractor3[i] = new TextField("Distractor 3");
					distractor3[i].setRequiredIndicatorVisible(true);
					distractor3[i].setIcon(VaadinIcons.CLOSE);

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
					ArrayList<String> origQuestions = new ArrayList<>();
					for (int a = 0; a < totalNumber; a++) {
						origQuestions.add(questionSheet[a].getValue());
					}
					Set<String> questionsSet = new HashSet<>(origQuestions);
					Set<String> track = new HashSet<>();
					if (questionsSet.size() < origQuestions.size()) {
						ArrayList<Integer> numbers = new ArrayList<>();
						ArrayList<Integer> frequencies = new ArrayList<>();
						for (int t = 0; t < origQuestions.size(); t++) {
							String question = origQuestions.get(t);
							if (track.add(question)) {
								numbers.add(t + 1);
								frequencies.add(Collections.frequency(origQuestions, question));

							}

						}
						Table table = new Table();
						table.addContainerProperty("Quection Number", String.class, null);
						table.addContainerProperty("Frequency", String.class, null);
						for (int f = 0; f < frequencies.size(); f++) {
							int count = frequencies.get(f);
							if (count > 1) {
								String q = "Question " + numbers.get(f);
								String fq = "" + count + " times";
								table.addItem(new Object[] { q, fq }, new Integer(f));
							}
						}
						table.setSizeFull();
						table.setColumnCollapsingAllowed(true);
						table.setSelectable(false);
						table.setFooterVisible(true);
						table.setPageLength(table.size());
						Label lbl = new Label(
								"<h1><center>These questions have been appeared more than once!<center/><h1/>",
								ContentMode.HTML);
						lbl.setCaptionAsHtml(true);

						Window window = new Window();
						window.setModal(false);
						window.setWidth("100%");
						window.setHeight("70%");

						HorizontalLayout horizontalLayout = new HorizontalLayout(table);

						VerticalLayout duplayout = new VerticalLayout(lbl, horizontalLayout);
						duplayout.addStyleName("duplicateslayout");
						duplayout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
						duplayout.setComponentAlignment(lbl, Alignment.TOP_CENTER);
						window.setContent(duplayout);
						UI.getCurrent().addWindow(window);

					} else {
						for (int i = 0; i < totalNumber; i++) {
							String actualQuestion = questionSheet[i].getValue();
							String actualAnswer = correctAnswerField[i].getValue();
							String d1 = distractor1[i].getValue();
							String d2 = distractor2[i].getValue();
							String d3 = distractor3[i].getValue();

							if (!(actualQuestion.equals("") || actualAnswer.equals("") || d1.equals("") || d2.equals("")
									|| d3.equals(""))) {
								List<String> originalAnswers = new ArrayList<>(Arrays.asList(actualAnswer, d1, d2, d3));
								Set<String> set = new HashSet<String>(originalAnswers);
								if (set.size() < originalAnswers.size()) {
									new Notification(
											"<h1 style = 'color:white;'>Check Question Number " + (i + 1)
													+ ".<br/>There could be some answers duplication detected!<h1/>",
											"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
									break;
								} else {
									check++;
								}

							} else {
								new Notification(
										"<h1 style = 'color:white;'>Check Question Number " + (i + 1)
												+ ".<br/>There could be some empty field(s) detected!<h1/>",
										"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
								break;
							}
						}
						if (check == totalNumber) {
							DateUtility dateUtility = new DateUtility();
							String date = dateUtility.getDate();
							String time = dateUtility.getTime();
							String subject = (String) subjects.getValue().toString();
							String class_name = (String) grades.getValue().toString();
							String subject_id = new AllSchoolSubjects(rs, rs1, stm, stmt).getSubjectIDFor(subject);
							String classID = new AllClasses(stmt, rs1).getClassIdFor(class_name);
							UI.getCurrent().addWindow(createConfirmationWindow(subject_id, classID, totalNumber, date,
									time, questionSheet, correctAnswerField, distractor1, distractor2, distractor3));
						}
					}
				});

				layout2.addComponent(submit);

			} else {
				new Notification(
						"<h1 style = 'color:white;'>A Blank field has been detected!<br/>Fill in all required fields!<h1/>",
						"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			}
		});

		HorizontalLayout horizontalLayout2 = new HorizontalLayout(subjects, grades, numberOfQuestions, go);
		horizontalLayout2.setSpacing(true);
		horizontalLayout2.addStyleName("questions_answers_panel_settings");

		layout.addComponents(horizontalLayout2, layout2);
		layout.setComponentAlignment(layout2, Alignment.MIDDLE_CENTER);
		this.setCompositionRoot(layout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	public Window createConfirmationWindow(String subject, String class_name, int number_of_questions, String date,
			String time, RichTextArea[] questionSheet, TextField[] correctAnswerField, TextField[] distractor1,
			TextField[] distractor2, TextField[] distractor3) {
		Window window = new Window("Confirm");
		TextField totalmark = new TextField("Total Mark");
		totalmark.setIcon(VaadinIcons.PIE_CHART);
		totalmark.setRequiredIndicatorVisible(true);
		// totalmark.addValidator(new IntegerValidator("Only Digits are
		// required"));

		RadioButtonGroup<String> single = new RadioButtonGroup<>("Examimation Period is in");
		single.setItems(ExamPeriodUnit.MINUTES, ExamPeriodUnit.HOURS);
		single.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);

		TextField testperiod = new TextField("Examimation Period");
		testperiod.setRequiredIndicatorVisible(true);
		testperiod.setIcon(VaadinIcons.CLOCK);
		// testperiod.addValidator(new DoubleValidator("Only Digits are
		// required"));
		TextField preparedBy = new TextField("Prepared By");
		preparedBy.setRequiredIndicatorVisible(true);
		preparedBy.setIcon(VaadinIcons.USER);

		TextField topicField = new TextField("Topic");
		topicField.setRequiredIndicatorVisible(true);
		topicField.setIcon(VaadinIcons.DESKTOP);

		DateField toField = new DateField("Due Date");
		toField.setIcon(VaadinIcons.COPY);
		toField.setRequiredIndicatorVisible(true);
		toField.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1));
		toField.setDateFormat("yyyy-MM-dd");

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
			InsertNewInClassRecord inClassRecord = new InsertNewInClassRecord(stm, rs);
			if (!(totalmark.getValue().equals("") || testperiod.getValue().equals("")
					|| preparedBy.getValue().equals("") || topicField.getValue().equals(""))) {
				if (!single.getSelectedItem().equals("")) {
					int total_mark = Integer.parseInt(totalmark.getValue());
					String test_period = testperiod.getValue();
					String prepared_by = preparedBy.getValue();
					String topic = topicField.getValue();
					String due = String.format("%1$tY-%1$tm-%1$td", toField.getValue());
					int pr = (single.getSelectedItem().equals(ExamPeriodUnit.MINUTES)) ? Integer.parseInt(test_period)
							: Integer.parseInt(test_period) * 60;
					inClassRecord.insertData(subject, topic, class_name, number_of_questions, total_mark,
							Integer.toString(pr), prepared_by, date, time, due);
					try {
						Thread.currentThread();
						Thread.sleep(500);
					} catch (InterruptedException ee) {
						ee.printStackTrace();
					}
					int test_id = inClassRecord.getRowID(subject, class_name, number_of_questions, total_mark,
							Integer.toString(pr));
					InsertMultipleChoiceQuestion insertMultipleChoiceQuestion = new InsertMultipleChoiceQuestion(stm);

					for (int i = 0; i < number_of_questions; i++) {
						String actualQuestion = questionSheet[i].getValue();
						String actualAnswer = correctAnswerField[i].getValue();
						String d1 = distractor1[i].getValue();
						String d2 = distractor2[i].getValue();
						String d3 = distractor3[i].getValue();
						insertMultipleChoiceQuestion.insertData(test_id, actualQuestion, actualAnswer, d1, d2, d3);
					}
					UI.getCurrent().removeWindow(window);
					window.close();
					new Notification(
							"<h4>Number of questions :" + number_of_questions + "<br/>Total Mark :" + total_mark
									+ "<br/>Examination Period :" + pr + " minutes.<br/>TEACHER +" + prepared_by
									+ "<br/>In Class Test Submitted</h4>",
							"", Notification.Type.HUMANIZED_MESSAGE, true).show(Page.getCurrent());
				} else {
					new Notification("<h1>We need to know whether the examination period is in minutes or hours!<h1/>",
							"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
				}
			} else {
				new Notification("<h1>A blank field has been detected!<br/>Fill in required fields<h1/>", "",
						Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			}
		});
		HorizontalLayout horizontalLayout1 = new HorizontalLayout(submit, cancel);
		horizontalLayout1.setSpacing(true);
		FormLayout formLayout = new FormLayout(toField, topicField, totalmark, new HorizontalLayout(testperiod, single),
				preparedBy, horizontalLayout1);
		formLayout.setSpacing(true);
		window.setWidth("100%");
		window.setHeight("80%");
		window.center();
		window.setModal(true);
		HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
		VerticalLayout layout = new VerticalLayout(horizontalLayout);
		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
		Panel p = new Panel(layout);
		p.setCaption("<h3 style='text-decoration:underline;color:blue'>Set In Class Test Specifications<h3/>");
		p.setCaptionAsHtml(true);
		p.setIcon(VaadinIcons.SUN_DOWN);
		window.setContent(p);
		return window;
	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Multiple Choice Test")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Multiple Choice Test", VaadinIcons.QUESTION);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}
}