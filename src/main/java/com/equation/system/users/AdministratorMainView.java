package com.equation.system.users;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import com.equation.accounts.administrator.fees.structure.set.FeesStrctureView;
import com.equation.accounts.administrator.receiptsbooks.view.ReceiptBooksViewer;
import com.equation.accounts.otheraccounts.view.OtherAccountsView;
import com.equation.ancillary.details.capture.CatureAncillaryDetails;
import com.equation.checkin.ancillary.AdminAncillaryCorrection;
import com.equation.checkin.ancillary.reports.AncillaryTimeSheetSearchView;
import com.equation.checkin.teacher.reports.TimeSheetSearchView;
import com.equation.checkin.teachers.AdminCorrection;
import com.equation.checkin.teachers.UserLevels;
import com.equation.school.classes.capture.CaptureSchoolClasses;
import com.equation.school.details.capture.CaptureSchoolDetails;
import com.equation.school.statistics.monthly.PresentationWindow;
import com.equation.school.subjects.CaptureSchoolSubjects;
import com.equation.student.details.capture.CaptureStudentDetails;
import com.equation.teacher.details.capture.CaptureTeacherDetails;
import com.equation.teacher.onleave.capture.CaptureTeacherOnLeave;
import com.equation.utils.application.basepath.ApplicationBasePath;
import com.equation.utils.msoffice.OpenPackages;
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
public class AdministratorMainView extends CustomComponent implements View {

	TabSheet tabs;
	Statement stm, stmt, stmt1;
	ResultSet rs, rs1;
	private Button school_registration;
	private Button teacher_registration;
	private Button student_registration;
	private Button anscillary_registration;
	private Button register_classes;
	private Button add_vehicle;
	private Button setfeesstructure;
	private Button captureteacheronleave;
	String emisNumber, userID, userLevel, schoolname, nameOfDistrict;
	private Button receiptBooks;
	private Button teachersMode;
	private Button bursarMode;
	private Button otherAccounts;
	private Button monthlyreturn;
	private Button checkinCorrection;
	private Button checkinReports;
	boolean canIhide = false;
	private Button uploadsubjects;

	public AdministratorMainView(Statement stm, Statement stmt, Statement stmt1, ResultSet rs, ResultSet rs1,
			String emisNumber, String userID, String userLevel, String schoolname, String nameOfDistrict) {
		this.stm = stm;
		this.rs = rs;
		this.stmt = stmt;
		this.stmt1 = stmt1;
		this.rs1 = rs1;
		this.emisNumber = emisNumber;
		this.userID = userID;
		this.userLevel = userLevel;
		this.schoolname = schoolname;
		this.nameOfDistrict = nameOfDistrict;

		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSpacing(true);
		// HorizontalLayout banner = new
		// com.equation.banners.design.TopBanner();
		Button hide = new Button();
		hide.setIcon(VaadinIcons.ARROW_CIRCLE_UP_O);
		hide.setDescription("Hide Toolbar");
		hide.addStyleName(ValoTheme.BUTTON_QUIET);

		HorizontalLayout toolbar = new HorizontalLayout(createToolbar(), hide);
		toolbar.setSpacing(true);

		hide.addClickListener((e) -> {
			if (!canIhide) {
				toolbar.removeAllComponents();
				toolbar.addComponent(hide);
				hide.setIcon(VaadinIcons.ARROW_CIRCLE_DOWN_O);
				hide.setDescription("Show Toolbar");
				canIhide = true;
			} else {
				toolbar.addComponents(createToolbar(), hide);
				hide.setIcon(VaadinIcons.ARROW_CIRCLE_UP_O);
				hide.setDescription("Hide Toolbar");
				canIhide = false;
			}
		});
		toolbar.addStyleName(ValoTheme.LAYOUT_WELL);
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

		verticalLayout.addComponent(createTopBanner());
		verticalLayout.addComponent(toolbar);
		verticalLayout.addComponent(mainContent);

		verticalLayout.setComponentAlignment(mainContent, Alignment.MIDDLE_CENTER);

		// new RemindAdministratorForReceiptLeft(this.rs, this.rs1, this.stm,
		// this.stmt, this.emisNumber)
		// .scanForReceiptsLeft();
		verticalLayout.setMargin(false);
		this.setCompositionRoot(verticalLayout);

	}

	// this method is for creating the toolbar
	@SuppressWarnings("deprecation")
	public TabSheet createToolbar() {
		TabSheet pane = new TabSheet();
		pane.setStyleName(ValoTheme.TABSHEET_ONLY_SELECTED_TAB_IS_CLOSABLE);

		school_registration = new Button("School Registration");
		school_registration.addStyleName(ValoTheme.BUTTON_QUIET);
		school_registration.setDescription("Register a new school in the system!");
		school_registration.addClickListener((e) -> {
			CaptureSchoolDetails captureSchoolContactDetails = new CaptureSchoolDetails(stm, rs, tabs);
			captureSchoolContactDetails.insertTab();

		});

		teacher_registration = new Button("Teachers Registration");
		teacher_registration.addStyleName(ValoTheme.BUTTON_QUIET);
		teacher_registration.addClickListener((e) -> {
			CaptureTeacherDetails captureTeacherDetails = new CaptureTeacherDetails(stm, rs, tabs);
			captureTeacherDetails.insertTab();
		});

		student_registration = new Button("Students Registration");
		student_registration.addStyleName(ValoTheme.BUTTON_QUIET);
		student_registration.addClickListener((e) -> {
			CaptureStudentDetails captureStudentDetails = new CaptureStudentDetails(stm, rs, tabs, emisNumber);
			captureStudentDetails.insertTab();
		});

		anscillary_registration = new Button("Ancillary");
		anscillary_registration.addStyleName(ValoTheme.BUTTON_QUIET);
		anscillary_registration.addClickListener((e) -> {
			CatureAncillaryDetails ancillaryDetails = new CatureAncillaryDetails(stm, tabs, rs);
			ancillaryDetails.insertTab();
		});
		register_classes = new Button("Register Classes");
		register_classes.addStyleName(ValoTheme.BUTTON_QUIET);
		register_classes.addClickListener((e) -> {
			CaptureSchoolClasses captureSchoolClasses = new CaptureSchoolClasses(rs, stm, tabs);
			captureSchoolClasses.insertTab();
		});

		setfeesstructure = new Button("Fees Structure");
		setfeesstructure.setIcon(VaadinIcons.SUN_DOWN);
		setfeesstructure.addStyleName(ValoTheme.BUTTON_QUIET);
		setfeesstructure.addClickListener((e) -> {
			FeesStrctureView feesStrctureView = new FeesStrctureView(stm, stmt, rs, rs1, emisNumber, tabs, userID);
			feesStrctureView.insertTab();
		});
		captureteacheronleave = new Button("Add Teacher Leave");
		captureteacheronleave.addStyleName(ValoTheme.BUTTON_QUIET);
		captureteacheronleave.addClickListener((e) -> {
			CaptureTeacherOnLeave captureTeacherOnLeave = new CaptureTeacherOnLeave(stm, rs, tabs);
			captureTeacherOnLeave.insertTab();
		});

		add_vehicle = new Button("Add  Vehicle");
		add_vehicle.addStyleName(ValoTheme.BUTTON_QUIET);

		receiptBooks = new Button("Receipt Books");
		receiptBooks.addStyleName(ValoTheme.BUTTON_QUIET);
		receiptBooks.addClickListener((e) -> {
			ReceiptBooksViewer receiptBooksViewer = new ReceiptBooksViewer(stm, stmt, rs, rs1, emisNumber, tabs,
					userID);
			receiptBooksViewer.insertTab();
		});
		teachersMode = new Button("Teacher Mode");
		teachersMode.addStyleName(ValoTheme.BUTTON_QUIET);
		teachersMode.addClickListener((e) -> {
			String address = "admin_teacher_view";
			TeacherMainView teacherMainView = new TeacherMainView(rs, rs1, stm, stmt, stmt1, emisNumber, userID,
					schoolname);

			getUI().getNavigator().addView(address, teacherMainView);
			getUI().getNavigator().navigateTo(address);
		});
		bursarMode = new Button("Bursar Mode");
		bursarMode.addStyleName(ValoTheme.BUTTON_QUIET);
		bursarMode.addClickListener((e) -> {
			String address = "admin_bursar_view";
			BursarMainView bursarMainView = new BursarMainView(stm, stmt, rs, rs1, emisNumber, userID, userLevel);
			getUI().getNavigator().addView(address, bursarMainView);
			getUI().getNavigator().navigateTo(address);
		});
		otherAccounts = new Button("Other Accounts");
		otherAccounts.addStyleName(ValoTheme.BUTTON_QUIET);
		otherAccounts.addClickListener((e) -> {
			OtherAccountsView accountsView = new OtherAccountsView(stm, stmt, rs, rs1, emisNumber, tabs, userID);
			accountsView.insertTab();
		});
		monthlyreturn = new Button("Monthly Return");
		monthlyreturn.addStyleName(ValoTheme.BUTTON_QUIET);
		monthlyreturn.setIcon(VaadinIcons.BRIEFCASE);
		monthlyreturn.addClickListener((e) -> {
			UI.getCurrent().addWindow(
					new PresentationWindow(rs, rs1, stm, stmt, emisNumber, schoolname, nameOfDistrict).present());
		});
		checkinReports = new Button("Time Sheets");
		checkinReports.addStyleName(ValoTheme.BUTTON_QUIET);
		checkinReports.setIcon(VaadinIcons.BRIEFCASE);
		checkinReports.addClickListener((e) -> {
			Window confirm = new Window();
			confirm.setModal(true);
			confirm.setWidth("40%");
			confirm.setHeight("30%");
			confirm.center();
			confirm.setCaption("Correct User CheckIn And CheckOut");

			ComboBox<String> box = new ComboBox<>("Select User Level",
					Arrays.asList(UserLevels.TEACHER, UserLevels.ANCILLARY));

			Button go = new Button("Go");
			go.addStyleName(ValoTheme.BUTTON_PRIMARY);
			go.setIcon(VaadinIcons.SEARCH);
			go.addClickListener((eee) -> {
				String which = (String) box.getValue().toString();
				if (!which.equals("")) {
					if (which.equals(UserLevels.TEACHER)) {
						TimeSheetSearchView searchView = new TimeSheetSearchView(stm, stmt, rs, rs1, schoolname);
						searchView.insertTab(tabs);
					} else {
						AncillaryTimeSheetSearchView searchView = new AncillaryTimeSheetSearchView(stm, stmt, rs, rs1,
								schoolname);
						searchView.insertTab(tabs);
					}
					UI.getCurrent().removeWindow(confirm);
					confirm.close();
				} else {
					box.focus();
				}

			});
			FormLayout form = new FormLayout(box, go);
			form.setSpacing(true);
			HorizontalLayout horizontal = new HorizontalLayout(form);
			horizontal.setSpacing(true);
			VerticalLayout vlayoutv = new VerticalLayout(horizontal);
			vlayoutv.setSpacing(true);
			vlayoutv.setComponentAlignment(horizontal, Alignment.MIDDLE_CENTER);
			confirm.setContent(vlayoutv);
			UI.getCurrent().addWindow(confirm);
		});

		checkinCorrection = new Button("Correct User CheckIn");
		checkinCorrection.addStyleName(ValoTheme.BUTTON_QUIET);
		checkinCorrection.setIcon(VaadinIcons.BRIEFCASE);
		checkinCorrection.addClickListener((e) -> {
			Window confirm = new Window();
			confirm.setModal(true);
			confirm.setWidth("40%");
			confirm.setHeight("30%");
			confirm.center();
			confirm.setCaption("Correct User CheckIn And CheckOut");

			ComboBox<String> box = new ComboBox<>("Select User Level",
					Arrays.asList(UserLevels.TEACHER, UserLevels.ANCILLARY));

			Button go = new Button("Go");
			go.addStyleName(ValoTheme.BUTTON_PRIMARY);
			go.setIcon(VaadinIcons.SEARCH);
			go.addClickListener((eee) -> {
				String which = (String) box.getValue().toString();
				if (!which.equals("")) {
					if (which.equals(UserLevels.TEACHER)) {
						Window window = new Window();
						window.setModal(true);
						window.setWidth("40%");
						window.setHeight("30%");
						window.center();
						window.setCaption("Correct Teacher CheckIn And CheckOut");

						com.vaadin.v7.ui.TextField ecnumber = new com.vaadin.v7.ui.TextField("Enter EC Number");
						ecnumber.setDescription("Enter EC Number");
						ecnumber.focus();
						Button submit = new Button("Search");
						submit.setDescription("Search the record");
						submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
						submit.addClickListener((ee) -> {
							String value = ecnumber.getValue();
							System.out.println("Entered " + value);
							if (!value.equals("")) {
								String validate = "SELECT firstName FROM teachers WHERE ecNumber = '"
										+ value.toUpperCase() + "'";
								try {
									rs = stm.executeQuery(validate);
									if (rs.next()) {
										System.out.println("Name is " + rs.getString(1));
										UI.getCurrent().removeWindow(window);
										window.close();
										new AdminCorrection(stm, stmt, rs, rs1).correct(value.toUpperCase(), window,
												ecnumber);
									} else {
										new Notification("<h3>Error<h3/>", "EC Number " + value + " does not exist!!!",
												Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
										ecnumber.selectAll();
									}
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							} else {
								ecnumber.focus();
							}
						});

						FormLayout formLayout = new FormLayout(ecnumber, submit);
						formLayout.setSpacing(true);
						HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
						horizontalLayout.setSpacing(true);
						VerticalLayout vlayout = new VerticalLayout(horizontalLayout);
						vlayout.setSpacing(true);
						vlayout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
						window.setContent(vlayout);
						UI.getCurrent().addWindow(window);

					} else {
						Window window = new Window();
						window.setModal(true);
						window.setWidth("40%");
						window.setHeight("30%");
						window.center();
						window.setCaption("Correct Ancillary CheckIn And CheckOut");

						TextField ecnumber = new TextField("Enter Worker ID");
						ecnumber.setDescription("Enter Worker ID");
						ecnumber.focus();
						Button submit = new Button("Search");
						submit.setDescription("Search the record");
						submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
						submit.addClickListener((ee) -> {
							String value = ecnumber.getValue();
							System.out.println("Entered " + value);
							if (!value.equals("")) {
								String validate = "SELECT firstName FROM acillary WHERE workerID = '"
										+ value.toUpperCase() + "'";
								try {
									rs = stm.executeQuery(validate);
									if (rs.next()) {
										System.out.println("Name is " + rs.getString(1));
										UI.getCurrent().removeWindow(window);
										window.close();
										new AdminAncillaryCorrection(stm, stmt, rs, rs1).correct(value.toUpperCase(),
												window, ecnumber);
									} else {
										new Notification("<h3>Error<h3/>", "EC Number " + value + " does not exist!!!",
												Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
										ecnumber.selectAll();
									}
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							} else {
								ecnumber.focus();
							}
						});

						FormLayout formLayout = new FormLayout(ecnumber, submit);
						formLayout.setSpacing(true);
						HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
						horizontalLayout.setSpacing(true);
						VerticalLayout vlayout = new VerticalLayout(horizontalLayout);
						vlayout.setSpacing(true);
						vlayout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
						window.setContent(vlayout);
						UI.getCurrent().addWindow(window);

					}
					UI.getCurrent().removeWindow(confirm);
					confirm.close();
				} else {
					box.focus();
				}
			});
			FormLayout form = new FormLayout(box, go);
			form.setSpacing(true);
			HorizontalLayout horizontal = new HorizontalLayout(form);
			horizontal.setSpacing(true);
			VerticalLayout vlayoutv = new VerticalLayout(horizontal);
			vlayoutv.setSpacing(true);
			vlayoutv.setComponentAlignment(horizontal, Alignment.MIDDLE_CENTER);
			confirm.setContent(vlayoutv);
			UI.getCurrent().addWindow(confirm);

		});

		uploadsubjects = new Button("Uplaoad Subjects");
		uploadsubjects.addStyleName(ValoTheme.BUTTON_QUIET);
		uploadsubjects.setIcon(VaadinIcons.BOOK);
		uploadsubjects.addClickListener((e) -> {
			CaptureSchoolSubjects captureSchoolSubjects = new CaptureSchoolSubjects(stm, stmt, rs, rs1);
			captureSchoolSubjects.insertTab(tabs);
		});

		HorizontalLayout modes = new HorizontalLayout(bursarMode, teachersMode);

		HorizontalLayout schools = new HorizontalLayout(school_registration, register_classes, uploadsubjects);

		HorizontalLayout students = new HorizontalLayout(student_registration);

		HorizontalLayout accounts = new HorizontalLayout(setfeesstructure, receiptBooks, otherAccounts);

		HorizontalLayout assets = new HorizontalLayout(/* add_vehicle */);

		HorizontalLayout teachers = new HorizontalLayout(checkinReports, checkinCorrection, teacher_registration,
				captureteacheronleave);

		HorizontalLayout ancillary = new HorizontalLayout(anscillary_registration);
		HorizontalLayout monthlyReports = new HorizontalLayout(monthlyreturn);

		pane.addTab(schools, "School", VaadinIcons.RSS);
		pane.addTab(students, "Students", VaadinIcons.USER);
		pane.addTab(accounts, "Accounts", VaadinIcons.MONEY);
		pane.addTab(assets, "Assets", VaadinIcons.BUILDING);
		pane.addTab(teachers, "Teachers", VaadinIcons.CLIPBOARD);
		pane.addTab(ancillary, "Ancillary", VaadinIcons.BOOK);
		pane.addTab(modes, "Switch Mode", VaadinIcons.PLUG);
		pane.addTab(monthlyReports, "Monthly Reports", VaadinIcons.ARCHIVE);

		return pane;
	}

	public TabSheet createLeftTabSheet() {
		TabSheet pane = new TabSheet();
		pane.setStyleName(ValoTheme.TABSHEET_FRAMED);

		pane.addTab(new OpenPackages(), "MS Office", VaadinIcons.COFFEE);

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

		Label descripton = new Label("School Management And Financial Administration System ! ! !");
		descripton.addStyleName("eq4_descripton");

		// layout.addComponent(eq4);
		// layout.addComponent(descripton);

		HorizontalLayout horizontalLayout = new HorizontalLayout(anotherImage, eq4, image);
		horizontalLayout.setSpacing(true);

		layout.addComponent(horizontalLayout);
		layout.addComponent(descripton);

		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);

		return layout;
	}

	public HorizontalLayout createTopBanner() {
		return new com.equation.main.banners.design.TopBanner(rs, rs, stm, stm, userID);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
}