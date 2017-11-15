package com.equation.student.questions.game;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import com.equation.school.secondary.subjects.bin.AllSecondarySubjectsBin;
import com.equation.student.banner.StudentWelcomeBanner;
import com.equation.student.game.overalmark.post.InsertDataIntoActivityTable;
import com.equation.student.questions.generate.GenerateQuestions;
import com.equation.system.pagetitles.PageTitles;
import com.equation.user.session.attributes.UserSessionAttributes;
import com.equation.utils.primary.grades.AllPrimaryGrades;
import com.equation.utils.primary.subjects.AllPrimarySubjects;
import com.equation.utils.questions.levels.QuestionLevelNumbers;
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
import com.vaadin.ui.TextField;
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
public class StudentQuestionGameMainView extends CustomComponent implements View {
	String studebtname;
	Statement stm, stmt;
	ResultSet rs, rs1;
	ComboBox<String> subjects, grades, levelOnequestionsCommbo;
	private Button go;
	private Button submit;
	private ComboBox<String>[] answersCombo;
	private RichTextArea[] area;
	private GenerateQuestions generateQuestions;
	ArrayList<String> studentAnswers;
	private ArrayList<String> questions;
	private ArrayList<String> correctAnswers;
	int limit = 0;
	private String subject;
	private String grade;
	// ClickPlayer clickPlayer;
	// private CheckBox music;

	@SuppressWarnings({ "unchecked" })
	public StudentQuestionGameMainView(String studebtname, Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.studebtname = studebtname;

		this.stm = stm;
		this.rs = rs;
		this.stmt = stmt;
		this.rs1 = rs1;
		// clickPlayer = new ClickPlayer();
		studentAnswers = new ArrayList<>();

		VerticalLayout layout = new VerticalLayout();

		layout.setSizeFull();
		layout.setSpacing(true);
		layout.addComponent(createTopBanner());
		// music = new CheckBox("Turn music ON or OFF");
		// music.setValue(true);

		/*
		 * music.addValueChangeListener((e) -> { if ((Boolean)
		 * e.getProperty().getValue() == false) { //
		 * clickPlayer.stopBackgroundMusic(); } else { //
		 * clickPlayer.playBackgroundMusic(); } });
		 */
		subjects = new ComboBox<>("Choose Your Subject");
		subjects.setRequiredIndicatorVisible(true);
		subjects.setEmptySelectionAllowed(false);

		grades = new ComboBox<>();
		grades.setEmptySelectionAllowed(false);
		grades.setRequiredIndicatorVisible(true);
		grades.addStyleName(ValoTheme.COMBOBOX_ALIGN_CENTER);

		String departmentode = (String) UI.getCurrent().getSession().getAttribute(UserSessionAttributes.DEPT_CODE)
				.toString();
		switch (departmentode) {
		case "3720":
			subjects.setItems(AllSecondarySubjectsBin.allSubjectsArray());

			grades.setCaption("Select Class (Form)");
			grades.setItems(AllPrimaryGrades.allGrades().subList(0, AllPrimaryGrades.allGrades().size() - 1));

			break;
		case "3730":

			subjects.setItems(AllPrimarySubjects.alSubjects());

			grades.setCaption("Select Class (Grade)");
			grades.setItems(AllPrimaryGrades.allGrades());
			break;
		default:
			new Notification("Error",
					"An Error occured whie trying to retrieve a marks entry form.<br/>Please contact the administrator to verify school details!",
					Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
		}

		levelOnequestionsCommbo = new ComboBox<>("Choose Number of Questions");
		levelOnequestionsCommbo.setRequiredIndicatorVisible(true);
		levelOnequestionsCommbo.setItems(QuestionLevelNumbers.levelOne());

		VerticalLayout questionSteet = new VerticalLayout();
		questionSteet.setSpacing(true);
		Panel panel = new Panel(questionSteet);
		panel.setWidth("90%");
		panel.addStyleName("login_panel");

		// panel.setSizeFull();
		panel.setCaption("Question Paper");
		panel.setIcon(VaadinIcons.QUESTION_CIRCLE);

		Button myreport = new Button("My Performance Report");
		myreport.addStyleName(ValoTheme.BUTTON_LINK);
		myreport.addClickListener((e) -> {
			MyActivityReport activityReport = new MyActivityReport(stm, stmt, rs, rs1, studebtname);
			UI.getCurrent().addWindow(activityReport);
		});

		go = new Button("G O");
		TextField f = new TextField("");
		f.addStyleName("space");
		f.setReadOnly(true);
		HorizontalLayout setings = new HorizontalLayout(f, grades, subjects, go, myreport);
		setings.addStyleName("student_setting_banner");
		setings.setSpacing(true);
		go.addStyleName(ValoTheme.BUTTON_HUGE);
		go.setIcon(VaadinIcons.AUTOMATION);

		submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_LARGE);
		submit.addStyleName("answers_submit_button");

		go.addClickListener((e) -> {
			// clickPlayer.dialogPlayer();
			questionSteet.removeAllComponents();
			grade = (String) grades.getValue();
			subject = (String) subjects.getValue();
			if (limit == 0)
				limit = 5;
			generateQuestions = new GenerateQuestions(rs, stm);

			generateQuestions.questions(subject, Integer.parseInt(grade), limit);
			questions = generateQuestions.getQueestions();
			correctAnswers = generateQuestions.getAnswers();
			ArrayList<String> dummies = new ArrayList<>();
			ArrayList<String> dummy1 = generateQuestions.getDummy1();
			ArrayList<String> dummy2 = generateQuestions.getDummy2();
			ArrayList<String> dummy3 = generateQuestions.getDummy3();

			int size = questions.size();
			// int dummysiz = dummies.size();
			if (size > 0) {
				area = new RichTextArea[size];
				answersCombo = new ComboBox[size];
				for (int i = 0; i < limit; i++) {
					area[i] = new RichTextArea();
					area[i].addStyleName("game_questions_area");
					String quection = questions.get(i);
					area[i].setValue(quection);
					area[i].setReadOnly(true);
					area[i].setSizeFull();
					answersCombo[i] = new ComboBox<>("Select An Answer:");
					answersCombo[i].setEmptySelectionAllowed(false);
					answersCombo[i].setRequiredIndicatorVisible(true);
					answersCombo[i].addValueChangeListener((ed) -> {

						// clickPlayer.buttonPlay();
					});

					dummies.add(dummy1.get(i));
					dummies.add(dummy2.get(i));
					dummies.add(dummy3.get(i));
					dummies.add(correctAnswers.get(i));

					// answersCombo[i].addItem(dummy1.get(i));
					// answersCombo[i].addItem(dummy2.get(i));
					// answersCombo[i].addItem(dummy3.get(i));
					// answersCombo[i].addItem(correctAnswers.get(i));
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
			} else {
				new Notification("Warning",
						subject.toUpperCase()
								+ " does not have any questions for now.<br/>Come back next time.<br/>Bye",
						Notification.TYPE_HUMANIZED_MESSAGE, true).show(Page.getCurrent());
			}
		});
		submit.addClickListener((e) -> {
			// clickPlayer.dialogPlayer();
			double size = questions.size();
			Table table = new Table();
			table.addContainerProperty("Question #", String.class, null);
			table.addContainerProperty("Correct Answers", String.class, null);
			table.addContainerProperty("My Answers", String.class, null);
			table.addContainerProperty("Score", CheckBox.class, null);
			Button proceed = new Button();
			double totalmark = 0, percentage = 0;
			for (int i = 0; i < size; i++) {
				// String question = questions.get(i);
				String correctAnswer = correctAnswers.get(i);
				int questionNumber = i + 1;
				String myaswers = (String) answersCombo[i].getValue();
				CheckBox mark = new CheckBox("");
				if (myaswers.equals(correctAnswer)) {
					totalmark += 1;
					mark.setValue(true);

				} else {
					mark.setValue(false);
					totalmark += 0;
				}
				mark.setEnabled(false);
				// studentAnswers.add(ans);
				table.addItem(new Object[] { "" + questionNumber, correctAnswer, myaswers, mark }, new Integer(i));
			}

			percentage = (totalmark / size) * 100;
			String[] val = studebtname.split(" ");

			InsertDataIntoActivityTable activityTable = new InsertDataIntoActivityTable(stm);
			activityTable.insertData(val[0].toLowerCase().trim(), subject, grade, percentage);

			System.out.println(totalmark + "  p " + percentage);
			if (percentage > 80) {
				limit += 5;
				proceed.setCaption("PROCEED TO LEVEL " + limit / 5);
				proceed.addStyleName(ValoTheme.BUTTON_FRIENDLY);
				proceed.setIcon(VaadinIcons.THUMBS_UP);
				// clickPlayer.startUpTonePlayer();
			} else {
				proceed.setCaption("REPRAT LEVEL " + limit / 5);
				proceed.setIcon(VaadinIcons.THUMBS_DOWN);
				proceed.addStyleName(ValoTheme.BUTTON_DANGER);
				// clickPlayer.crowdboosPlay();
				// limit += 5;
			}
			// percentage = 0;
			// size = 0;
			// totalmark = 0;
			table.setFooterVisible(true);
			table.setColumnFooter("Question #", "Total");
			table.setColumnFooter("My Answers", "" + (int) totalmark + " OUT OF " + (int) size);
			table.setColumnFooter("Score", "" + percentage + "%");
			table.setPageLength(table.size());
			table.setSelectable(true);
			// table.addStyleName("students_gender_table");

			Window answerWindow = new Window("My Answers");
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
			answerWindow.setContent(subContent);
			answerWindow.center();
			answerWindow.setModal(true);
			answerWindow.setClosable(false);
			UI.getCurrent().addWindow((answerWindow));

			proceed.addClickListener((ev) -> {
				questionSteet.removeAllComponents();
				UI.getCurrent().removeWindow(answerWindow);
				// clickPlayer.closeappPlay();
				// answerWindow.close();

			});

		});

		layout.addComponents(setings, panel);
		// layout.setComponentAlignment(setings, Alignment.TOP_CENTER);
		// layout.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

		this.setCompositionRoot(layout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle(PageTitles.maintitle + "-" + studebtname);

	}

	public HorizontalLayout createTopBanner() {
		return new StudentWelcomeBanner(studebtname);
	}
}