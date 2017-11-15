package com.equation.system.users;

import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.accounts.bankaccount.CaptureBankAccountWithdrawal;
import com.equation.accounts.bankingregister.CaptureBankingRegisterDetails;
import com.equation.accounts.cashreport.view.CashReportView;
import com.equation.accounts.expenditure.view.ExpenditureView;
import com.equation.accounts.payments.view.FeesPaymentsView;
import com.equation.accounts.search.console.SearchAccountData;
import com.equation.accounts.sundryaccount.view.SundryAccoutView;
import com.equation.accounts.vouchers.view.VouchersMainPanel;
import com.equation.ancillary.details.capture.CatureAncillaryDetails;
import com.equation.ancillary.details.search.RetrieveAncillaryDetails;
import com.equation.books.reports.BooksView;
import com.equation.school.assets.details.capture.CaptureSchoolAsset;
import com.equation.school.classes.view.SchoolClassesView;
import com.equation.school.furniture.details.capture.CaptureSchoolFurniture;
import com.equation.school.furniture.search.RetrieveFurnutureDetails;
import com.equation.school.statistics.students.StudentStatisticsByClass;
import com.equation.school.vehicles.capture.CaptureSchoolVehicleDetails;
import com.equation.shool.classes.payment.report.ClassPaymentReport;
import com.equation.student.autosearch.StudentAutoSearchView;
import com.equation.student.details.capture.CaptureStudentDetails;
import com.equation.student.payments.reports.IndividualStudentPaymentReport;
import com.equation.student.reports.RetrieveStudentDetails;
import com.equation.teacher.details.capture.CaptureTeacherDetails;
import com.equation.teacher.reports.RetrieveTeacherDetails;
import com.equation.utils.application.basepath.ApplicationBasePath;
import com.equation.utils.msoffice.OpenPackages;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class BursarMainView extends CustomComponent implements View {

	TabSheet tabs;
	Statement stm, stmt;
	ResultSet rs, rs1;
	private Button teacher_registration;
	private Button student_registration;
	private Button anscillary_registration;
	private Button register_classes;
	private Button newvoucher;
	private Button bankaccwithdrawal;
	private Button bankingregister;
	private Button sundryaccountentry;
	private Button accountsquery;
	private Button addvehiclebtn;
	private Button addschoolbook;
	private Button otherAssets;
	private Button addfurniture;
	private Button searchStudent;
	private Button searchTeacher;
	private Button searchFurniture;
	private Button searchAncillary;
	String emisNumber, userID, userLevel;
	private Button studentPaymentreport;
	private Button classPaymentReport;
	private Button classStats;
	private Button cashReportButtton;
	private Button expenses;
	private Button feesPayment;
	private Button auto_search;
	boolean canIhide = false;

	public BursarMainView(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String emisNumber, String userID,
			String userLevel) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.emisNumber = emisNumber;
		this.userID = userID;
		this.userLevel = userLevel;

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
		toolbar.addStyleName(ValoTheme.LAYOUT_WELL);
		tabs = new TabSheet();
		tabs.addTab(createFirstPage(), "WELCOME", VaadinIcons.USER);
		tabs.setResponsive(true);
		tabs.addStyleName(ValoTheme.TABSHEET_ONLY_SELECTED_TAB_IS_CLOSABLE);
		VerticalLayout mainContent = new VerticalLayout(tabs);
		mainContent.addStyleName(ValoTheme.LAYOUT_CARD);
		verticalLayout.addComponent(createTopBanner());
		verticalLayout.addComponent(toolbar);
		verticalLayout.addComponent(mainContent);

		verticalLayout.setComponentAlignment(mainContent, Alignment.MIDDLE_CENTER);
		verticalLayout.setMargin(false);
		this.setCompositionRoot(verticalLayout);

	}

	public TabSheet createLeftTabSheet() {
		TabSheet pane = new TabSheet();
		pane.setStyleName(ValoTheme.TABSHEET_FRAMED);

		Tab tab = pane.addTab(new OpenPackages(), "MS Office", VaadinIcons.COFFEE);
		tab.setClosable(false);

		return pane;
	}

	public TabSheet createToolBar() {
		TabSheet pane = new TabSheet();
		pane.setStyleName(ValoTheme.TABSHEET_ONLY_SELECTED_TAB_IS_CLOSABLE);

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

		anscillary_registration = new Button("Ancillary Registration");
		anscillary_registration.addStyleName(ValoTheme.BUTTON_QUIET);
		anscillary_registration.addClickListener((e) -> {
			CatureAncillaryDetails ancillaryDetails = new CatureAncillaryDetails(stm, tabs, rs);
			ancillaryDetails.insertTab();
		});
		register_classes = new Button("Classes");
		register_classes.addStyleName(ValoTheme.BUTTON_QUIET);
		register_classes.addClickListener((e) -> {
			SchoolClassesView classesView = new SchoolClassesView(stm, stmt, rs, rs1, emisNumber, tabs, userID);
			classesView.insertTab();
		});

		newvoucher = new Button("Vouchers");
		newvoucher.addStyleName(ValoTheme.BUTTON_QUIET);
		newvoucher.addClickListener((e) -> {
			VouchersMainPanel vouchersMainPanel = new VouchersMainPanel(stm, stmt, rs, rs1, emisNumber, tabs, userID);
			vouchersMainPanel.insertTab();
		});

		bankaccwithdrawal = new Button("Bank Account");
		bankaccwithdrawal.addStyleName(ValoTheme.BUTTON_QUIET);
		bankaccwithdrawal.addClickListener((e) -> {
			CaptureBankAccountWithdrawal bankAccountWithdrawal = new CaptureBankAccountWithdrawal(stm, stmt, rs, rs1,
					tabs, userID, emisNumber);
			bankAccountWithdrawal.insertTab();
		});

		bankingregister = new Button("Banking Register");
		bankingregister.addStyleName(ValoTheme.BUTTON_QUIET);
		bankingregister.addClickListener((e) -> {
			CaptureBankingRegisterDetails captureBankingRegisterDetails = new CaptureBankingRegisterDetails(stm, stmt,
					rs, rs1, tabs, emisNumber);
			captureBankingRegisterDetails.insertTab();
		});

		sundryaccountentry = new Button("Sundry Account");
		sundryaccountentry.addStyleName(ValoTheme.BUTTON_QUIET);
		sundryaccountentry.addClickListener((e) -> {
			SundryAccoutView sundryAccoutView = new SundryAccoutView(stm, stmt, rs, rs1, emisNumber, tabs, userID);
			sundryAccoutView.insertTab();
		});

		accountsquery = new Button("Accounts Queries");
		accountsquery.addStyleName(ValoTheme.BUTTON_QUIET);
		accountsquery.setIcon(VaadinIcons.SEARCH);
		accountsquery.addClickListener((e) -> {

			SearchAccountData searchAccountData = new SearchAccountData(rs, rs1, stm, stmt, tabs);
			searchAccountData.insertTab();
		});

		addvehiclebtn = new Button("Add Vehicle");
		addvehiclebtn.setIcon(VaadinIcons.CAR);
		addvehiclebtn.addStyleName(ValoTheme.BUTTON_QUIET);
		addvehiclebtn.addClickListener((e) -> {
			CaptureSchoolVehicleDetails captureSchoolVehicleDetails = new CaptureSchoolVehicleDetails(stm, tabs);
			captureSchoolVehicleDetails.insertTab();
		});

		addschoolbook = new Button("Books");
		addschoolbook.setIcon(VaadinIcons.BOOK);
		addschoolbook.addStyleName(ValoTheme.BUTTON_QUIET);
		addschoolbook.addClickListener((e) -> {
			BooksView books = new BooksView(stm, stmt, rs, rs1, emisNumber, tabs, userID);
			books.insertTab();
		});

		otherAssets = new Button("Add Other Assets");
		otherAssets.addStyleName(ValoTheme.BUTTON_QUIET);
		otherAssets.setIcon(VaadinIcons.ABSOLUTE_POSITION);
		otherAssets.addClickListener((e) -> {
			CaptureSchoolAsset asset = new CaptureSchoolAsset(stm, tabs);
			asset.insertTab();
		});
		addfurniture = new Button("Add Furniture");
		addfurniture.addStyleName(ValoTheme.BUTTON_QUIET);
		addfurniture.setIcon(VaadinIcons.TABLE);
		addfurniture.addClickListener((e) -> {
			CaptureSchoolFurniture furniture = new CaptureSchoolFurniture(rs, stm, tabs);
			furniture.insertTab();
		});

		searchStudent = new Button("Search Student");
		searchStudent.addStyleName(ValoTheme.BUTTON_QUIET);
		searchStudent.addClickListener((e) -> {
			RetrieveStudentDetails details = new RetrieveStudentDetails(rs, rs1, stm, stmt, tabs);
			details.insertTab();
		});
		searchTeacher = new Button("Search Teacher");
		searchTeacher.addStyleName(ValoTheme.BUTTON_QUIET);
		searchTeacher.addClickListener((e) -> {
			RetrieveTeacherDetails details = new RetrieveTeacherDetails(rs, rs1, stm, stmt, tabs);
			details.insertTab();
		});

		searchFurniture = new Button("Search");
		searchFurniture.addStyleName(ValoTheme.BUTTON_QUIET);
		searchFurniture.setIcon(VaadinIcons.SEARCH);
		searchFurniture.addClickListener((e) -> {
			RetrieveFurnutureDetails details = new RetrieveFurnutureDetails(rs, rs1, stm, stmt, tabs);
			details.insertTab();
		});
		searchAncillary = new Button("Search");
		searchAncillary.addStyleName(ValoTheme.BUTTON_QUIET);
		searchAncillary.setIcon(VaadinIcons.SEARCH);
		searchAncillary.addClickListener((e) -> {
			RetrieveAncillaryDetails details = new RetrieveAncillaryDetails(rs, rs1, stm, stmt, tabs);
			details.insertTab();
		});

		studentPaymentreport = new Button("Payment Report");
		studentPaymentreport.addStyleName(ValoTheme.BUTTON_QUIET);
		studentPaymentreport.setIcon(VaadinIcons.DIAMOND);
		studentPaymentreport.addClickListener((e) -> {
			IndividualStudentPaymentReport paymentReport = new IndividualStudentPaymentReport(rs, rs1, stm, stmt,
					emisNumber, tabs);
			paymentReport.insertTab();
		});
		classPaymentReport = new Button("Class Payment Report");
		classPaymentReport.addStyleName(ValoTheme.BUTTON_QUIET);
		classPaymentReport.setIcon(VaadinIcons.MONEY);
		classPaymentReport.addClickListener((e) -> {
			ClassPaymentReport classPaymentRepor = new ClassPaymentReport(rs, rs1, stm, stmt, emisNumber, tabs);
			classPaymentRepor.insertTab();
		});

		classStats = new Button("Class Statistics");
		classStats.addStyleName(ValoTheme.BUTTON_QUIET);
		classStats.setIcon(VaadinIcons.CHART);
		classStats.addClickListener((e) -> {
			StudentStatisticsByClass statisticsByClass = new StudentStatisticsByClass(rs, rs1, stm, stmt, emisNumber,
					tabs);
			statisticsByClass.insertTab();
		});
		cashReportButtton = new Button("Cash Report");
		cashReportButtton.addStyleName(ValoTheme.BUTTON_QUIET);
		cashReportButtton.setIcon(VaadinIcons.MONEY);
		cashReportButtton.addClickListener((e) -> {
			CashReportView cashReportView = new CashReportView(stm, stmt, rs, rs1, emisNumber, tabs, userID);
			cashReportView.insertTab();
		});
		expenses = new Button("Expenses");
		expenses.addStyleName(ValoTheme.BUTTON_QUIET);
		expenses.setIcon(VaadinIcons.EXCLAMATION);
		expenses.addClickListener((e) -> {
			ExpenditureView expenditureView = new ExpenditureView(stm, stmt, rs, rs1, emisNumber, tabs, userID);
			expenditureView.insertTab();
		});
		feesPayment = new Button("Fees Payments");
		feesPayment.setIcon(VaadinIcons.MONEY);
		feesPayment.addStyleName(ValoTheme.BUTTON_QUIET);
		feesPayment.addClickListener((e) -> {
			FeesPaymentsView feesPaymentsView = new FeesPaymentsView(stm, stmt, rs, rs1, emisNumber, tabs, userID,
					userLevel);
			feesPaymentsView.insertTab();
		});
		auto_search = new Button("Auto-Search");
		auto_search.setIcon(VaadinIcons.BARCODE);
		auto_search.addStyleName(ValoTheme.BUTTON_QUIET);
		auto_search.addClickListener((e) -> {
			// in this button we ar goiing to pkresent what the barcoode is
			// ginng to be presenting
			StudentAutoSearchView autoSearchView = new StudentAutoSearchView(stm, stmt, rs, rs1, emisNumber, tabs,
					userID, userLevel);
			autoSearchView.insertTab(tabs);
		});

		HorizontalLayout teachers = new HorizontalLayout(teacher_registration, searchTeacher);
		HorizontalLayout students = new HorizontalLayout(student_registration, searchStudent, studentPaymentreport,
				auto_search);
		HorizontalLayout accounts = new HorizontalLayout(feesPayment, cashReportButtton, accountsquery, newvoucher,
				bankaccwithdrawal, bankingregister, sundryaccountentry, expenses);
		HorizontalLayout assets = new HorizontalLayout(addvehiclebtn, otherAssets);
		HorizontalLayout school = new HorizontalLayout(register_classes);
		HorizontalLayout ancillary = new HorizontalLayout(anscillary_registration, searchAncillary);

		HorizontalLayout books = new HorizontalLayout(addschoolbook);
		HorizontalLayout frniture = new HorizontalLayout(addfurniture, searchFurniture);
		HorizontalLayout classes = new HorizontalLayout(classPaymentReport, classStats);

		// these lines ad tabs to the actual toolbar
		pane.addTab(school, "School", VaadinIcons.SPINNER);
		pane.addTab(students, "Students", VaadinIcons.USER);
		pane.addTab(accounts, "Accounts", VaadinIcons.MONEY_WITHDRAW);
		pane.addTab(ancillary, "Ancillary", VaadinIcons.USERS);
		pane.addTab(assets, "Assets", VaadinIcons.BUILDING);
		pane.addTab(teachers, "Teachers", VaadinIcons.CLIPBOARD);
		pane.addTab(books, "Books", VaadinIcons.BOOK);
		pane.addTab(frniture, "Furniture", VaadinIcons.DESKTOP);
		pane.addTab(classes, "Classes", VaadinIcons.DESKTOP);

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