package com.equation.system.users;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.equation.equate.utils.allclasses.AllClasses;
import com.equation.school.subjects.AllSchoolSubjects;
import com.equation.student.banner.StudentTopBanner;
import com.equation.student.inclass.assessment.InClassTestContinuousAssessment;
import com.equation.student.inclasstest.view.StudentInClassTestView;
import com.equation.student.payments.myreports.MyPaymentsReport;
import com.equation.student.questions.game.MyActivityReport;
import com.equation.student.questions.game.StudentQuestionGameMainView;
import com.equation.system.pagetitles.PageTitles;
import com.equation.utils.application.basepath.ApplicationBasePath;
import com.equation.utils.date.DateUtility;
import com.equation.utils.date.MyDateConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
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
public class Student extends CustomComponent implements View {

	ResultSet rs, rs1;
	Statement stm, stmt;
	TabSheet tabs;
	String studentname, studentID, currentClass, schoolID;
	private Button playQuestionGame;
	private Button inclass;
	private Button myreport;
	private Button inclassContinuousAssessment;
	private Button myPaymentReport;
	boolean canIhide = false;

	public Student(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, String studentname, String studentID,
			String currentClass, String schoolID) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.studentname = studentname;
		this.studentID = studentID;
		this.currentClass = currentClass;
		this.schoolID = schoolID;

		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSpacing(true);

		Button hide = new Button();
		hide.setIcon(VaadinIcons.ARROW_CIRCLE_UP_O);
		hide.setDescription("Hide Toolbar");
		hide.addStyleName(ValoTheme.BUTTON_QUIET);

		HorizontalLayout toolbar = new HorizontalLayout(createToolBar(), hide);
		toolbar.setSpacing(true);

		hide.addClickListener((e) -> {
			if (!canIhide) {
				toolbar.removeAllComponents();
				toolbar.addComponent(hide);
				hide.setIcon(VaadinIcons.ARROW_CIRCLE_DOWN);
				hide.setDescription("Show Toolbar");
				canIhide = true;
			} else {
				toolbar.addComponents(createToolBar(), hide);
				hide.setIcon(VaadinIcons.ARROW_CIRCLE_UP);
				hide.setDescription("Hide Toolbar");
				canIhide = false;
			}
		});

		toolbar.addStyleName(ValoTheme.LAYOUT_WELL);
		toolbar.addStyleName("transaparent_layout");

		tabs = new TabSheet();
		tabs.addTab(createFirstPage(), "WELCOME", VaadinIcons.THUMBS_UP);
		tabs.setResponsive(true);
		tabs.addStyleName(ValoTheme.TABSHEET_ONLY_SELECTED_TAB_IS_CLOSABLE);
		VerticalLayout mainContent = new VerticalLayout(tabs);
		mainContent.addStyleName(ValoTheme.LAYOUT_CARD);

		// HorizontalSplitPanel horizontalSplitPanel = new
		// HorizontalSplitPanel();
		// horizontalSplitPanel.setFirstComponent(createLeftTabSheet());
		// horizontalSplitPanel.setSecondComponent(mainContent);
		// horizontalSplitPanel.setSplitPosition(10, Sizeable.UNITS_PERCENTAGE);

		verticalLayout.addComponent(createTopBanner());
		verticalLayout.addComponent(toolbar);
		verticalLayout.addComponent(mainContent);

		verticalLayout.setComponentAlignment(mainContent, Alignment.MIDDLE_CENTER);
		verticalLayout.setMargin(false);
		this.setCompositionRoot(verticalLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle(PageTitles.maintitle + "-" + studentname);
	}

	public HorizontalLayout createTopBanner() {
		return new StudentTopBanner(studentname, studentID);
	}

	public VerticalLayout createFirstPage() {
		VerticalLayout layout = new VerticalLayout();
		FileResource resource = new FileResource(
				new File(ApplicationBasePath.basePath() + "/WEB-INF/images/systemlogo.png"));
		// Show the image in the application
		FileResource resource2 = new FileResource(
				new File(ApplicationBasePath.basePath() + "/WEB-INF/images/zim_logo.png"));
		Image image = new Image("", resource);
		image.addStyleName("zim_logo");

		Label eq4 = new Label("\u2211QUATION");
		eq4.addStyleName("eq4");

		Label descripton = new Label("My Portal! ! !");
		descripton.addStyleName("eq4_descripton");

		Image image2 = new Image("", resource2);
		image2.setWidth("250px");
		image2.setHeight("250px");
		image.setWidth("250px");
		image.setHeight("250px");

		// layout.addComponent(eq4);
		// layout.addComponent(descripton);

		HorizontalLayout horizontalLayout = new HorizontalLayout(image, eq4, image2);
		horizontalLayout.setSpacing(true);

		layout.addComponent(horizontalLayout);
		layout.addComponent(descripton);

		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);

		return layout;
	}

	public TabSheet createToolBar() {
		TabSheet pane = new TabSheet();
		playQuestionGame = new Button("Play Question Game");
		playQuestionGame.addStyleName(ValoTheme.BUTTON_QUIET);
		playQuestionGame.setIcon(VaadinIcons.PLAY_CIRCLE);
		playQuestionGame.addClickListener((e) -> {
			String url = "attempting_questions";
			StudentQuestionGameMainView gameMainView = new StudentQuestionGameMainView(studentID, stm, stmt, rs, rs1);
			UI.getCurrent().getNavigator().addView(url, gameMainView);
			UI.getCurrent().getNavigator().navigateTo(url);
		});

		inclass = new Button("In Class Test");
		inclass.addStyleName(ValoTheme.BUTTON_QUIET);
		inclass.addClickListener((e) -> {
			Window window = new Window();
			ComboBox<String> subjects = new ComboBox<>("Select Subject");
			subjects.setRequiredIndicatorVisible(true);
			subjects.setEmptySelectionAllowed(false);
			subjects.setItems(new AllSchoolSubjects(rs, rs1, stm, stmt).allSchoolSubjects());

			ComboBox<String> grades = new ComboBox<>();
			grades.setRequiredIndicatorVisible(true);
			grades.setEmptySelectionAllowed(false);
			grades.setItems(new AllClasses(stm, rs).allClasses());
			grades.setValue(currentClass);

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
				DateUtility dateUtility = new DateUtility();
				String today = dateUtility.getDate();
				if (!(subjects.getValue().toString().equals("") || grades.getValue().toString().equals(""))) {
					String subject = (String) subjects.getValue().toString();
					String grade = (String) grades.getValue().toString();

					String query = "SELECT due FROM  inclasstestrecords WHERE subject = '" + subject
							+ "' AND class_name = '" + grade + "'";
					try {
						rs1 = stmt.executeQuery(query);
						rs1.last();
						int rows = rs1.getRow();
						if (rows > 0) {

							Table table = new Table("<h1><h1/>");
							table.setCaptionAsHtml(true);
							table.addContainerProperty("Due Date", String.class, null);
							table.addContainerProperty("Topic", String.class, null);
							table.addContainerProperty("Number Of Questions", String.class, null);
							table.addContainerProperty("Total Mark", String.class, null);
							table.addContainerProperty("Period", String.class, null);
							table.addContainerProperty("Prepared By", String.class, null);
							String query1 = "SELECT due,topic,class_name,number_of_questions,total_mark,test_period,prepared_by,test_id FROM  inclasstestrecords WHERE subject = '"
									+ subject + "' AND class_name = '" + grade + "'";
							rs = stm.executeQuery(query1);
							ArrayList<String> ids = new ArrayList<>();
							int t = 0;
							while (rs.last()) {
								String due = rs.getString(1);
								String topic = rs.getString(2);
								// String classname = rs.getString(3);
								String totalQuestions = rs.getString(4);
								String totalMark = rs.getString(5);
								String peroid = rs.getString(6);
								String teacher = rs.getString(7);
								String testid = rs.getString(8);

								String qurtu1 = "SELECT studentID FROM inclassmarkschedule WHERE test_id = '" + testid
										+ "'";
								rs1 = stmt.executeQuery(qurtu1);
								if (!rs1.next()) {
									table.addItem(
											new Object[] { due, topic, String.valueOf(totalQuestions),
													String.valueOf(totalMark), String.valueOf(peroid), teacher },
											new Integer(t));
									ids.add(testid);
								} else {

								}
								t++;
							}
							table.setPageLength(table.size());
							table.setFooterVisible(true);
							table.setColumnCollapsingAllowed(true);
							table.setSelectable(true);
							UI.getCurrent().removeWindow(window);
							window.close();

							Window mywindow = new Window("Double-Click on a record in order to iew the Test");
							mywindow.setModal(true);
							mywindow.setSizeFull();

							VerticalLayout layout = new VerticalLayout(table);
							layout.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
							mywindow.setContent(layout);

							table.addContextClickListener((ee) -> {
								int row = (int) table.getValue();
								Item item = table.getItem(row);
								String testid = ids.get(row - 1);
								String totalQuestions = (String) item.getItemProperty("Number Of Questions").getValue()
										.toString();
								String totalMark = (String) item.getItemProperty("Total Mark").getValue().toString();
								String peroid = (String) item.getItemProperty("Period").getValue().toString();
								String teacher = (String) item.getItemProperty("Prepared By").getValue().toString();
								String topic = (String) item.getItemProperty("Topic").getValue().toString();
								String classname = grade;
								String due = (String) item.getItemProperty("Due Date").getValue().toString();

								Date now = MyDateConverter.getDate(today);
								Date dueDate = MyDateConverter.getDate(due);
								if (dueDate.after(now) || dueDate.equals(now)) {
									Label lbl = new Label("<h1 style ='color:red;'>Time Period: " + peroid
											+ "<br/>You are about to start an exam in for " + peroid
											+ " minutes.<br/>Click Start if you are ready!<h1/>");
									lbl.setCaptionAsHtml(true);

									Window window2 = new Window(
											"<h1 style = 'text-decoration:underline;color:red;'>Start<h1/>");
									window2.setCaptionAsHtml(true);
									window2.center();
									window2.setWidth("100%");
									window2.setHeight("60%");

									Button start = new Button("Start");
									start.addStyleName(ValoTheme.BUTTON_FRIENDLY);
									start.setIcon(VaadinIcons.PLAY);
									start.addClickListener((eee) -> {
										String url = subject.toLowerCase() + "_" + topic.toLowerCase() + "_inclass";

										Page.getCurrent().setTitle(
												PageTitles.maintitle + " - " + studentname + "-In Class Test");
										StudentInClassTestView testView = new StudentInClassTestView(studentID, subject,
												totalQuestions, totalMark, peroid, teacher, topic, classname, due, rs1,
												rs, stm, stmt, testid);
										UI.getCurrent().getNavigator().addView(url, testView);
										UI.getCurrent().getNavigator().navigateTo(url);
										UI.getCurrent().removeWindow(window);
										window.close();
										UI.getCurrent().removeWindow(window2);
										window2.close();
									});

									VerticalLayout verticalLayout = new VerticalLayout(lbl, start);
									verticalLayout.setComponentAlignment(lbl, Alignment.MIDDLE_CENTER);
									verticalLayout.setComponentAlignment(start, Alignment.BOTTOM_CENTER);
									window2.setContent(verticalLayout);

									UI.getCurrent().addWindow(window2);

								} else {
									new Notification(
											"<h1>" + studentname + ", your " + subject
													+ " in class test is long over due!<br/><br/>You may try to talk to the Teacher to extend the due date!<br/>We wish you all the best dear!</h1>",
											"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());

								}

							});

							UI.getCurrent().addWindow(mywindow);

						} else {
							new Notification(
									"<h4>" + studentname + ", we could not find any in class test for " + subject
											+ ".<br/>You can try to look for an in class test for another subject!</h4>",
									"", Notification.Type.WARNING_MESSAGE, true).show(Page.getCurrent());
						}

					} catch (SQLException ee) {
						ee.printStackTrace();
					}

				} else {
					new Notification(
							"<h2>Please, " + studentname
									+ ", do not leave any blank field!<br/><br/>I want to help you find an iin class test that is not yet overdue</h2>",
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
			UI.getCurrent().addWindow(window);

		});

		myreport = new Button("My Performance Report");
		myreport.addStyleName(ValoTheme.BUTTON_QUIET);
		myreport.addClickListener((e) -> {
			MyActivityReport activityReport = new MyActivityReport(stm, stmt, rs, rs1, studentID);
			UI.getCurrent().addWindow(activityReport);
		});

		inclassContinuousAssessment = new Button("Continuous Assessment");
		inclassContinuousAssessment.addStyleName(ValoTheme.BUTTON_QUIET);
		inclassContinuousAssessment.addClickListener((e) -> {
			InClassTestContinuousAssessment assessment = new InClassTestContinuousAssessment(stm, stmt, rs, rs1,
					currentClass);
			assessment.fetchRepotrt(studentID, studentname);
		});
		myPaymentReport = new Button("Payment Report");
		myPaymentReport.addClickListener((e) -> {
			new MyPaymentsReport(rs, rs1, stm, stmt, schoolID, studentID);
		});

		HorizontalLayout games = new HorizontalLayout(playQuestionGame);
		HorizontalLayout inclasses = new HorizontalLayout(inclass);
		HorizontalLayout performancereports = new HorizontalLayout(inclassContinuousAssessment, myreport);
		HorizontalLayout paymentReports = new HorizontalLayout(myPaymentReport);

		pane.addTab(games, "Question Game".toUpperCase(), VaadinIcons.GAMEPAD);
		pane.addTab(inclasses, "Tests And Assignments".toUpperCase(), VaadinIcons.PAPERCLIP);
		pane.addTab(performancereports, "Performance ".toUpperCase(), VaadinIcons.PAPERCLIP);
		pane.addTab(paymentReports, "PAYMENTS", VaadinIcons.PAPERCLIP);

		return pane;
	}
}