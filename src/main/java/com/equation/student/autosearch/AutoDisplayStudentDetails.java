package com.equation.student.autosearch;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.accounts.payments.view.FeesPaymentsView;
import com.equation.student.name.retrieve.RetrieveStudentName;
import com.equation.student.payments.reports.IndividualPaymentReportPDF;
import com.equation.system.users.teacher.getname.FetchStuffMemberName;
import com.equation.utils.application.basepath.ApplicationBasePath;
import com.equation.utils.print.currentpage.PrintCurrentPage;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.v7.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.ui.Table;

@SuppressWarnings("deprecation")
public class AutoDisplayStudentDetails {
	ResultSet rs, rs1;
	Statement stm, stmt;
	VerticalLayout layout;
	private Table grid;
	private Window window;

	String schoolID, userID, userLevel;
	TabSheet tabs;

	public AutoDisplayStudentDetails(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String schoolID,
			TabSheet tabs, String userID, String userLevel, VerticalLayout layout) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.schoolID = schoolID;
		this.tabs = tabs;
		this.userID = userID;
		this.userLevel = userLevel;
		this.layout = layout;
	}

	VerticalLayout displayTransactionDetails(String studentID, TextField barcodeField, CheckBox mouseFreeMode,
			String schoolID) {
		VerticalLayout vlayout = new VerticalLayout();
		vlayout.setIcon(VaadinIcons.CHECK_CIRCLE);
		vlayout.addStyleName("mytransactio_layout");

		String query = "SELECT 	studentID,sFirstName,sSurname,classname,sgender,pSurname FROM students,parents,classes,schools WHERE students.studentID = '"
				+ studentID + "' AND schools.schoolID = '" + schoolID + "' OR schools.schoolName = '" + schoolID + "'";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow();
			if (rows > 0) {
				String query1 = "SELECT sFirstName,sSurname,classname,sgender,pSurname,pCell,pEmail,pAdress FROM students,parents,classes,schools WHERE students.studentID = '"
						+ studentID + "' AND schools.schoolID = '" + schoolID + "' OR schools.schoolName = '" + schoolID
						+ "'";
				rs = stm.executeQuery(query1);

				grid = new Table("Students details ");
				grid.addContainerProperty("Property", String.class, null);
				grid.addContainerProperty("Value", String.class, null);

				// grid.setSizeFull();
				grid.setSelectable(true);
				String studentName = null;
				if (rs.next()) {
					studentName = rs.getString(1) + " " + rs.getString(2);
					String classname = rs.getString(3);
					String sgender = rs.getString(4);
					String parentname = rs.getString(5);
					String parentcontacts = "Cell:" + rs.getString(6) + " Email:" + rs.getString(7);
					String parentAddress = rs.getString(8);

					grid.addItem(new Object[] { "Student ID", studentID }, 0);
					grid.addItem(new Object[] { "Student Name", studentName }, 1);
					grid.addItem(new Object[] { "Class Name", classname }, 2);
					grid.addItem(new Object[] { "Gender", sgender }, 3);
					grid.addItem(new Object[] { "Parent Name", parentname }, 4);
					grid.addItem(new Object[] { "Parent Contacts", parentcontacts }, 5);
					grid.addItem(new Object[] { "Home Address", parentAddress }, 6);
				} else {

				}
				grid.setFooterVisible(true);
				grid.setSizeFull();
				grid.setPageLength(grid.size());
				grid.addStyleName("students_studentID_table");
				vlayout.setCaption("  " + studentName + " Found");

				/*
				 * Button chechin = new Button("Check In");
				 * chechin.setIcon(VaadinIcons.CHECK_CIRCLE_O);
				 * chechin.addStyleName(ValoTheme.BUTTON_PRIMARY);
				 */

				Button close = new Button("Close");
				close.setIcon(VaadinIcons.CLOSE);
				close.addStyleName(ValoTheme.BUTTON_DANGER);
				close.addClickListener((e) -> {
					this.layout.removeAllComponents();
					clearAndFocusBarcodeField(barcodeField);
				});

				// new AutoDetermineCheckInButtonCondition(rs, rs1, stm,
				// stmt).checkit(studentID, chechin, close,
				// mouseFreeMode, barcodeField);

				HorizontalLayout horizontalLayout = new HorizontalLayout(close);
				horizontalLayout.setSpacing(true);

				Image studentImage = new Image(studentName, new FileResource(
						new File(ApplicationBasePath.basePath() + "/WEB-INF/images/" + studentID + ".png")));
				studentImage.setWidth("200px");
				studentImage.setHeight("100px");

				Button paymentReport = new Button("Payment Report");
				paymentReport.setIcon(VaadinIcons.MONEY);
				paymentReport.addStyleName(ValoTheme.BUTTON_PRIMARY);
				paymentReport.addClickListener((e) -> {

					try {
						String query3 = "SELECT receiptNumber,userID,amount,date,time,paidBy FROM feesreceived,schools WHERE  studentID = '"
								+ studentID + "' AND schools.schoolID = '" + schoolID + "'";
						this.rs1 = this.stmt.executeQuery(query3);
						this.rs1.last();
						int numberOfRows = this.rs1.getRow(), i = 0;
						if (numberOfRows > 0) {
							window = new Window();
							setWindow(window);
							getWindow().center();
							getWindow().setModal(false);
							getWindow().setSizeFull();
							getWindow().setCaption(
									new RetrieveStudentName(rs, stm).fetchStudentName(studentID).toUpperCase()
											+ " Payment Report");

							Button closeButton = new Button("Close");
							closeButton.addStyleName(ValoTheme.BUTTON_DANGER);
							closeButton.setIcon(VaadinIcons.CLOSE);

							Button print = new Button("Print");
							print.addStyleName(ValoTheme.BUTTON_PRIMARY);
							print.setIcon(VaadinIcons.PRINT);
							print.addClickListener((ee) -> {
								PrintCurrentPage.print();
							});

							Button download = new Button("Download");
							download.addStyleName(ValoTheme.BUTTON_FRIENDLY);
							download.setIcon(VaadinIcons.DOWNLOAD);

							closeButton.addClickListener((ev) -> {
								UI.getCurrent().removeWindow(getWindow());
								getWindow().close();
							});

							HorizontalLayout hLayout = new HorizontalLayout(download, print, closeButton);
							hLayout.setSpacing(true);
							hLayout.setSizeFull();
							hLayout.addStyleName("student_report_sub_window");

							Table table = new Table();
							table.addContainerProperty("Receipt Number", Integer.class, null);
							table.addContainerProperty("Amount", Double.class, null);
							table.addContainerProperty("Term", String.class, null);
							table.addContainerProperty("Year", String.class, null);
							table.addContainerProperty("Balance", Double.class, null);
							table.addContainerProperty("Dated", String.class, null);
							table.addContainerProperty("Payee", String.class, null);

							table.addContainerProperty("Cashier", String.class, null);
							FetchStuffMemberName fetchUserFirstNane = new FetchStuffMemberName(this.rs, this.rs1,
									this.stm, this.stmt);

							String query4 = "SELECT receiptNumber,userID,amount,date,time,paidBy,balance,term,year FROM feesreceived,schools WHERE studentID = '"
									+ studentID + "' AND schools.schoolID = '" + schoolID
									+ "' ORDER BY receiptNumber DESC ";
							this.rs = this.stm.executeQuery(query4);
							double sum = 0;
							while (this.rs.next()) {
								int receitNumber = this.rs.getInt(1);
								String userID = this.rs.getString(2);
								double amount = this.rs.getDouble(3);
								String timestamp = this.rs.getString(4) + " " + this.rs.getString(5);
								String paidby = this.rs.getString(6);
								double balance = this.rs.getDouble(7);
								String termm = this.rs.getString(8);
								int yearr = this.rs.getInt(9);

								String cashiername = fetchUserFirstNane.getActualName(userID);

								table.addItem(new Object[] { receitNumber, amount, termm, String.valueOf(yearr),
										balance, timestamp, paidby, cashiername }, new Integer(i));
								sum += amount;
								i++;
							}
							double size1 = table.size();

							table.setFooterVisible(true);
							table.setColumnFooter("Receipt Number",
									"Total " + String.valueOf(Math.floor(size1)) + " Records");
							table.setColumnFooter("Amount", "Sum $" + String.valueOf(sum));

							table.setSizeFull();
							table.setPageLength((int) size1);
							table.addStyleName("payments_table");
							table.setSelectable(true);
							table.setColumnCollapsingAllowed(true);

							VerticalLayout mylayout = new VerticalLayout();
							mylayout.setSpacing(true);

							mylayout.addComponents(hLayout, table);
							getWindow().setContent(mylayout);
							UI.getCurrent().addWindow(getWindow());
							String studentname = new RetrieveStudentName(this.rs1, this.stmt)
									.fetchStudentName(studentID);
							new IndividualPaymentReportPDF().generatePDF(studentID, studentname, "All Terms",
									"From Inception", table, download);
						} else {
							new Notification("Warning", "No Payment Records found for student number " + studentID,
									Notification.TYPE_WARNING_MESSAGE, true).show(Page.getCurrent());
						}
					} catch (SQLException ee) {
						ee.printStackTrace();
					}

				});
				Button assessment = new Button("Continuous Assessment");
				assessment.setIcon(VaadinIcons.BOOK);
				assessment.addStyleName(ValoTheme.BUTTON_PRIMARY);
				assessment.addClickListener((e) -> {

				});

				Button payfees = new Button("Pay Fees");
				payfees.setIcon(VaadinIcons.BOOK);
				payfees.addStyleName(ValoTheme.BUTTON_PRIMARY);
				payfees.addClickListener((e) -> {
					FeesPaymentsView feesPaymentsView = new FeesPaymentsView(stm, stmt, rs, rs1, schoolID, tabs, userID,
							userLevel);
					feesPaymentsView.insertTab();
					feesPaymentsView.captureStudentPaymentDetails.studentID.setValue(studentID);
					feesPaymentsView.captureStudentPaymentDetails.amount.focus();

				});

				GridLayout gridLayout = new GridLayout(2, 2, paymentReport, assessment, payfees);
				gridLayout.setSpacing(true);

				VerticalLayout leftLayout = new VerticalLayout(studentImage, gridLayout);
				leftLayout.setSpacing(true);
				leftLayout.setComponentAlignment(studentImage, Alignment.TOP_LEFT);
				leftLayout.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);

				VerticalLayout rightLayout = new VerticalLayout(grid);
				rightLayout.setSpacing(true);

				HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();

				splitPanel.setFirstComponent(leftLayout);
				splitPanel.setSplitPosition(50, Unit.PERCENTAGE);
				splitPanel.setSecondComponent(rightLayout);

				VerticalLayout layout = new VerticalLayout(horizontalLayout, splitPanel);
				layout.setComponentAlignment(splitPanel, Alignment.MIDDLE_CENTER);
				layout.setComponentAlignment(horizontalLayout, Alignment.BOTTOM_CENTER);
				layout.setSpacing(true);
				vlayout.addComponent(layout);

				vlayout.setSizeFull();
				/*
				 * chechin.addClickListener((e) -> {
				 * 
				 * clearAndFocusBarcodeField(barcodeField); });
				 */

			} else {

			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		return vlayout;
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	private void clearAndFocusBarcodeField(TextField barcodeField) {
		ClearAndFocusInputField.clearAndFocusBarcodeField(barcodeField);
	}
}