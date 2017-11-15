package com.equation.system.users;

import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;

import com.eqauation.assessment.teacher.createasssignment.CreateAssignment;
import com.equation.equate.views.main.MyPrimaryEOTMainView;
import com.equation.shool.classes.payment.report.ClassPaymentReport;
import com.equation.student.currentclass.retrieve.GetStudentClass;
import com.equation.student.inclass.assessment.AssignmentsContinuousAssessment;
import com.equation.student.inclass.assessment.InClassTestContinuousAssessment;
import com.equation.student.name.retrieve.RetrieveStudentName;
import com.equation.student.payments.reports.IndividualStudentPaymentReport;
import com.equation.teacher.assignments.performance.AssignmentPerfornamceReport;
import com.equation.teacher.inclass.performance.InClassPerformanceReport;
import com.equation.teacher.insertquestions.CaptureNewQuestion;
import com.equation.teacher.multiplechoice.questions.set.SetMultipleChoiceExam;
import com.equation.teacher.question.view.ViewAllQuestions;
import com.equation.teacher.secondary.marks.capture.SecondarySchoolMarksEntryForm;
import com.equation.user.session.attributes.UserSessionAttributes;
import com.equation.utils.application.basepath.ApplicationBasePath;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
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
@SuppressWarnings("serial")
public class TeacherMainView extends CustomComponent implements View {

	private Button newassignmentbtn, newtestbtn, newregbtn, assignmentmarksbtn, testmarksbtn, alltestbtn, allassignbtn,
			studentsinclassassessmentreportsbtn, inClassReport;
	TabSheet tabs;
	private Button endoftermmarksentryform;
	ResultSet rs, rs1;
	Statement stm, stmt, stmt1;
	String schoolID, userID, schoolname;
	private Button newquestion;
	private Button viewQuestions;
	private Button activityAssessmentReport;
	private Button studentassignContinuousAssess;
	private Button classPaymentReport;
	private Button studentPaymentreport;
	boolean canIhide = false;

	public TeacherMainView(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, Statement stmt1, String schoolID,
			String userID, String schoolname) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.stmt1 = stmt1;
		this.schoolID = schoolID;
		this.userID = userID;
		this.schoolname = schoolname;

		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSpacing(true);
		// HorizontalLayout banner = new
		// com.equation.banners.design.TopBanner();

		Button hide = new Button();
		hide.setIcon(VaadinIcons.ARROW_CIRCLE_UP_O);
		hide.setDescription("Hide Toolbar");
		hide.addStyleName(ValoTheme.BUTTON_QUIET);

		HorizontalLayout toolbar = new HorizontalLayout(createToolBar(), hide);
		toolbar.setSpacing(true);
		toolbar.addStyleName("toolbar");

		hide.addClickListener((e) -> {
			if (!canIhide) {
				toolbar.removeAllComponents();
				toolbar.addComponent(hide);
				hide.setIcon(VaadinIcons.ARROW_CIRCLE_DOWN_O);
				hide.setDescription("Show Toolbar");
				canIhide = true;
			} else {
				toolbar.addComponents(createToolBar(), hide);
				hide.setIcon(VaadinIcons.ARROW_CIRCLE_UP_O);
				hide.setDescription("Hide Toolbar");
				canIhide = false;
			}
		});
		//toolbar.addStyleName(ValoTheme.LAYOUT_WELL);
		tabs = new TabSheet();
		tabs.addTab(createFirstPage(), "WELCOME", VaadinIcons.USER);
		tabs.setResponsive(true);
		tabs.addStyleName(ValoTheme.TABSHEET_ONLY_SELECTED_TAB_IS_CLOSABLE);
		VerticalLayout mainContent = new VerticalLayout(tabs);
		mainContent.addStyleName(ValoTheme.LAYOUT_CARD);
		// HorizontalSplitPanel horizontalSplitPanel = new
		// HorizontalSplitPanel();
		// horizontalSplitPanel.setFirstComponent(createLeftTabSheet());
		// horizontalSplitPanel.setSecondComponent(mainContent);
		// horizontalSplitPanel.setSplitPosition(10, Sizeable.UNITS_PERCENTAGE);
	
		//verticalLayout.addComponent(createTopBanner());
		//verticalLayout.addComponent(toolbar);
		HorizontalLayout horizontalLayout = new HorizontalLayout(toolbar);
		horizontalLayout.setStyleName("top_lay");
		verticalLayout.addComponent(horizontalLayout);
		verticalLayout.addComponent(mainContent);

		verticalLayout.setComponentAlignment(mainContent, Alignment.MIDDLE_CENTER);
		verticalLayout.setMargin(false);
		this.setCompositionRoot(verticalLayout);
	}

	public TabSheet createToolBar() {
		TabSheet pane = new TabSheet();

		newassignmentbtn = new Button("New Assignment");
		newassignmentbtn.addStyleName(ValoTheme.BUTTON_QUIET);
		newassignmentbtn.addClickListener((e) -> {
			CreateAssignment create = new CreateAssignment(schoolname, stm, stm, rs, rs);
			create.insertTab(tabs);

		});

		newtestbtn = new Button("New Test");
		newtestbtn.addStyleName(ValoTheme.BUTTON_QUIET);
		newtestbtn.addClickListener((e) -> {
			SetMultipleChoiceExam multipleChoiceExam = new SetMultipleChoiceExam(rs, rs1, stm, stmt, tabs, schoolname);
			multipleChoiceExam.insertTab();
		});

		newregbtn = new Button("New Register");
		newregbtn.addStyleName(ValoTheme.BUTTON_QUIET);
		newregbtn.addClickListener((e) -> {

		});

		assignmentmarksbtn = new Button("Assignment Marks");
		assignmentmarksbtn.addStyleName(ValoTheme.BUTTON_QUIET);
		assignmentmarksbtn.addClickListener((e) -> {

		});

		testmarksbtn = new Button("Test Marks");
		testmarksbtn.addStyleName(ValoTheme.BUTTON_QUIET);
		testmarksbtn.addClickListener((e) -> {

		});

		alltestbtn = new Button("All Test");
		alltestbtn.addStyleName(ValoTheme.BUTTON_QUIET);
		alltestbtn.addClickListener((e) -> {
			InClassPerformanceReport classPerformanceReport = new InClassPerformanceReport(stm, stmt, rs, rs1,
					schoolname);
			UI.getCurrent().addWindow(classPerformanceReport.createSearchWindow());
		});

		allassignbtn = new Button("All Assignments");
		allassignbtn.addStyleName(ValoTheme.BUTTON_QUIET);
		allassignbtn.addClickListener((e) -> {
			AssignmentPerfornamceReport classPerformanceReport = new AssignmentPerfornamceReport(stm, stmt, rs, rs1,
					schoolname);
			UI.getCurrent().addWindow(classPerformanceReport.createSearchWindow());
		});

		studentsinclassassessmentreportsbtn = new Button("Tests Continuous Assessment");
		studentsinclassassessmentreportsbtn.addStyleName(ValoTheme.BUTTON_QUIET);
		studentsinclassassessmentreportsbtn.addClickListener((e) -> {
			TextField area = new TextField("Sudent ID:");
			area.focus();

			Button search = new Button("Search");
			search.setIcon(VaadinIcons.SEARCH);
			search.addStyleName(ValoTheme.BUTTON_PRIMARY);

			Button close = new Button("Close");
			close.setIcon(VaadinIcons.CLOSE);
			close.addStyleName(ValoTheme.BUTTON_DANGER);

			Window window = new Window();
			window.setModal(true);
			window.setWidth("40%");
			window.setHeight("40%");
			window.center();
			close.addClickListener((ee) -> {
				UI.getCurrent().removeWindow(window);
				window.close();
			});

			HorizontalLayout h = new HorizontalLayout(search, close);
			h.setSpacing(true);
			FormLayout form = new FormLayout(area, h);
			form.setSpacing(true);
			search.addClickListener((ee) -> {
				String studentID = area.getValue();
				if (!studentID.equals("")) {
					UI.getCurrent().removeWindow(window);
					window.close();

					InClassTestContinuousAssessment assessment = new InClassTestContinuousAssessment(stm, stmt, rs, rs1,
							getStudentCurrentClass(studentID));
					assessment.fetchRepotrt(studentID, getStudentFullName(studentID));

				} else {
					new Notification("Error", "Empty Student ID", Notification.Type.ERROR_MESSAGE, true)
							.show(Page.getCurrent());
					area.focus();
				}
			});

			window.setContent(form);
			UI.getCurrent().addWindow(window);
		});

		inClassReport = new Button("Tests Performance Report");
		inClassReport.addStyleName(ValoTheme.BUTTON_QUIET);
		inClassReport.addClickListener((e) -> {

			InClassPerformanceReport classPerformanceReport = new InClassPerformanceReport(stm, stmt, rs, rs1,
					schoolname);
			UI.getCurrent().addWindow(classPerformanceReport.createSearchWindow());

		});

		endoftermmarksentryform = new Button("Marks Entry Form");
		endoftermmarksentryform.addStyleName(ValoTheme.BUTTON_QUIET);
		endoftermmarksentryform.addClickListener((e) -> {
			String departmentode = (String) UI.getCurrent().getSession().getAttribute(UserSessionAttributes.DEPT_CODE)
					.toString();
			switch (departmentode) {
			case "3720":
				SecondarySchoolMarksEntryForm fsecform = new SecondarySchoolMarksEntryForm(rs, rs1, stm, stmt, tabs,
						schoolID);
				fsecform.insertTab();
				break;
			case "3730":
				MyPrimaryEOTMainView view = new MyPrimaryEOTMainView(rs, rs1, stm, stmt, stmt1);
				String url = "end_of_term";

				UI.getCurrent().getNavigator().addView(url, view);
				UI.getCurrent().getNavigator().navigateTo(url);
				/*
				 * PrimarySchoolMarksEntryFrom marksEntryFrom = new
				 * PrimarySchoolMarksEntryFrom(rs, rs1, stm, stmt, tabs,
				 * schoolID, userID); marksEntryFrom.insertTab();
				 */
				break;
			default:
				new Notification(
						"<h1>An Error occured whie trying to retrieve a marks entry form.<br/>Please contact the administrator to verify school details!<h1/>",
						"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			}
		});

		newquestion = new Button("Add New Questions");
		newquestion.setIcon(VaadinIcons.QUESTION_CIRCLE);
		newquestion.addStyleName(ValoTheme.BUTTON_QUIET);
		newquestion.addClickListener((e) -> {
			CaptureNewQuestion captureNewQuestion = new CaptureNewQuestion(rs, rs1, stm, stmt, tabs, schoolname);
			captureNewQuestion.insertTab();
		});

		viewQuestions = new Button("View Questions");
		viewQuestions.addStyleName(ValoTheme.BUTTON_QUIET);
		viewQuestions.setIcon(VaadinIcons.FILE_ZIP);
		viewQuestions.addClickListener((e) -> {
			ViewAllQuestions viewAllQuestions = new ViewAllQuestions(stm, stmt, rs, rs1, tabs, schoolname);
			viewAllQuestions.insertTab();
		});
		activityAssessmentReport = new Button("Student Activity Assessment");
		activityAssessmentReport.addStyleName(ValoTheme.BUTTON_QUIET);
		activityAssessmentReport.setIcon(VaadinIcons.FILE_ZIP);
		activityAssessmentReport.addClickListener((ee) -> {
			/*
			 * StudentActivitySummary activitySummary = new
			 * StudentActivitySummary(pool.stm, pool.stmt, pool.rs, pool.rs1,
			 * tabs); activitySummary.insertTab();
			 */
		});
		studentassignContinuousAssess = new Button("Assinments Continuous Assessment");
		studentassignContinuousAssess.addStyleName(ValoTheme.BUTTON_QUIET);
		studentassignContinuousAssess.addClickListener((e) -> {
			TextField area = new TextField("Sudent ID");
			area.focus();

			Button search = new Button("Search");
			search.setIcon(VaadinIcons.SEARCH);
			search.addStyleName(ValoTheme.BUTTON_PRIMARY);

			Button close = new Button("Close");
			close.setIcon(VaadinIcons.CLOSE);
			close.addStyleName(ValoTheme.BUTTON_DANGER);

			Window window = new Window();
			window.setModal(true);
			window.setWidth("40%");
			window.setHeight("40%");
			window.center();
			close.addClickListener((ee) -> {
				UI.getCurrent().removeWindow(window);
				window.close();
			});

			HorizontalLayout h = new HorizontalLayout(search, close);
			h.setSpacing(true);
			FormLayout form = new FormLayout(area, h);
			form.setSpacing(true);
			search.addClickListener((ee) -> {
				String studentID = area.getValue();
				if (!studentID.equals("")) {
					UI.getCurrent().removeWindow(window);
					window.close();
					AssignmentsContinuousAssessment assessment = new AssignmentsContinuousAssessment(stm, stmt, rs, rs1,
							getStudentCurrentClass(studentID));
					assessment.fetchRepotrt(studentID, getStudentFullName(studentID));

				} else {
					new Notification("<h1>Empty Student ID<h1/>", "", Notification.Type.ERROR_MESSAGE, true)
							.show(Page.getCurrent());
					area.focus();
				}
			});

			window.setContent(form);
			UI.getCurrent().addWindow(window);

		});
		classPaymentReport = new Button("Class Payment Report");
		classPaymentReport.addStyleName(ValoTheme.BUTTON_QUIET);
		classPaymentReport.setIcon(VaadinIcons.MONEY);
		classPaymentReport.addClickListener((e) -> {
			ClassPaymentReport classPaymentRepor = new ClassPaymentReport(rs, rs1, stm, stmt, schoolID, tabs);
			classPaymentRepor.insertTab();
		});

		studentPaymentreport = new Button("Student Payment Report");
		studentPaymentreport.addStyleName(ValoTheme.BUTTON_QUIET);
		studentPaymentreport.setIcon(VaadinIcons.DIAMOND);
		studentPaymentreport.addClickListener((e) -> {
			IndividualStudentPaymentReport paymentReport = new IndividualStudentPaymentReport(rs, rs1, stm, stmt,
					schoolID, tabs);
			paymentReport.insertTab();
		});

		HorizontalLayout markslayout = new HorizontalLayout(endoftermmarksentryform, assignmentmarksbtn, testmarksbtn);
		HorizontalLayout classtestlayout = new HorizontalLayout(inClassReport, studentsinclassassessmentreportsbtn,
				studentassignContinuousAssess);
		HorizontalLayout filelayout = new HorizontalLayout(newquestion, newassignmentbtn, newtestbtn, newregbtn);
		HorizontalLayout view = new HorizontalLayout(viewQuestions, alltestbtn, allassignbtn);
		HorizontalLayout payments = new HorizontalLayout(studentPaymentreport, classPaymentReport);

		pane.addTab(filelayout, "FILE", VaadinIcons.ABSOLUTE_POSITION);
		pane.addTab(markslayout, "Student Reports", VaadinIcons.MAGIC);
		pane.addTab(view, "VIEW", VaadinIcons.DESKTOP);
		pane.addTab(classtestlayout, "Test Performance", VaadinIcons.BOOK);
		pane.addTab(payments, "PAYMENTS", VaadinIcons.BOOK);

		return pane;
	}

	public VerticalLayout createFirstPage() {
		VerticalLayout layout = new VerticalLayout();
		FileResource resource = new FileResource(
				new File(ApplicationBasePath.basePath() + "/WEB-INF/images/zim_logo.png"));
		// Show the image in the application
		Image image = new Image("", resource);
		image.setWidth("250px");
		image.setHeight("250px");
		image.addStyleName("zim_logo");
		// layout.addComponent(image);

		FileResource another = new FileResource(
				new File(ApplicationBasePath.basePath() + "/WEB-INF/images/systemlogo.png"));

		Image anotherImage = new Image("", another);
		anotherImage.setWidth("250px");
		anotherImage.setHeight("250px");
		Label eq4 = new Label("\u2211QUATION");
		eq4.addStyleName("eq4");

		Label descripton = new Label("Teacher's Cloud Workbench ! ! !");
		descripton.addStyleName("eq4_descripton");

		HorizontalLayout horizontalLayout = new HorizontalLayout(anotherImage, eq4, image);
		horizontalLayout.setSpacing(true);

		layout.addComponent(horizontalLayout);
		layout.addComponent(descripton);

		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);

		return layout;
	}

	public HorizontalLayout createTopBanner() {
		HorizontalLayout horizontalLayout = new com.equation.main.banners.design.TopBanner(rs, rs, stm, stm, userID);
		// horizontalLayout.setMargin(false);
		horizontalLayout.setWidth("100%");
		// horizontalLayout.setSizeFull();
		return horizontalLayout;
	}

	public String getStudentCurrentClass(String studentID) {
		return new GetStudentClass(rs, stm).getClassName(schoolID, studentID);
	}

	public String getStudentFullName(String studentID) {
		return new RetrieveStudentName(rs, stm).fetchStudentName(studentID);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
