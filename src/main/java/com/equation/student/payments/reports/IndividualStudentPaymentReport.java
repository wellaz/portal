package com.equation.student.payments.reports;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.student.name.retrieve.RetrieveStudentName;
import com.equation.system.users.teacher.getname.FetchStuffMemberName;
import com.equation.util.years.ranges.PaymentHistoryYears;
import com.equation.utils.print.currentpage.PrintCurrentPage;
import com.equation.utils.schoolterms.AllYearTermsBin;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
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
public class IndividualStudentPaymentReport extends CustomComponent implements View {

	ResultSet rs, rs1;
	Statement stm, stmt;
	String schoolID;
	private Table table;
	private Window window;
	TabSheet tabs;

	TextField studentIDField;
	ComboBox<String> termCombo, yearCombo;
	private Button search;

	public IndividualStudentPaymentReport(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, String schoolID,
			TabSheet tabs) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.schoolID = schoolID;
		this.tabs = tabs;

		Button closeButton = new Button("Close");
		closeButton.addStyleName(ValoTheme.BUTTON_DANGER);
		closeButton.setIcon(VaadinIcons.CLOSE);

		Button print = new Button("Print");
		print.addStyleName(ValoTheme.BUTTON_PRIMARY);
		print.setIcon(VaadinIcons.PRINT);
		print.addClickListener((e) -> {
			PrintCurrentPage.print();
		});

		Button download = new Button("Download");
		download.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		download.setIcon(VaadinIcons.DOWNLOAD);

		studentIDField = new TextField("Student ID");
		studentIDField.setRequiredIndicatorVisible(true);

		termCombo = new ComboBox<>("Term");
		termCombo.setRequiredIndicatorVisible(true);
		termCombo.setEmptySelectionAllowed(false);
		termCombo.setItems(AllYearTermsBin.allTermsArray());

		yearCombo = new ComboBox<>("Year");
		yearCombo.setRequiredIndicatorVisible(true);
		yearCombo.setEmptySelectionAllowed(false);
		yearCombo.setItems(PaymentHistoryYears.allYears());

		search = new Button("Search");
		search.setIcon(VaadinIcons.SEARCH);
		search.addStyleName(ValoTheme.BUTTON_FRIENDLY);

		window = new Window();
		setWindow(window);
		getWindow().center();
		getWindow().setModal(false);
		getWindow().setSizeFull();

		closeButton.addClickListener((ev) -> {
			UI.getCurrent().removeWindow(getWindow());
			getWindow().close();
		});

		HorizontalLayout horizontalLayout = new HorizontalLayout(download, print, closeButton);
		horizontalLayout.setSpacing(true);
		horizontalLayout.setSizeFull();
		horizontalLayout.addStyleName("student_report_sub_window");

		search.addClickListener((e) -> {
			String studentID = studentIDField.getValue();
			String year = (String) yearCombo.getValue();
			String term = (String) termCombo.getValue();
			if (!(studentID.equals("") || year.equals("") || term.equals(""))) {
				VerticalLayout layout = new VerticalLayout();
				layout.setSpacing(true);

				if (!studentID.equals("")) {
					getWindow().setCaption(new RetrieveStudentName(rs, stm).fetchStudentName(studentID).toUpperCase()
							+ " " + term + " " + year + " Payment Report");
					try {
						String query = "SELECT receiptNumber,userID,amount,date,time,paidBy FROM feesreceived,schools WHERE  studentID = '"
								+ studentID + "' AND schools.schoolID = '" + schoolID + "' AND  term = '" + term
								+ "' AND year = '" + year + "'";
						this.rs1 = this.stmt.executeQuery(query);
						this.rs1.last();
						int numberOfRows = this.rs1.getRow(), i = 0;
						if (numberOfRows > 0) {
							table = new Table();
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

							String query1 = "SELECT receiptNumber,userID,amount,date,time,paidBy,balance,term,year FROM feesreceived,schools WHERE studentID = '"
									+ studentID + "' AND schools.schoolID = '" + schoolID + "' AND  term = '" + term
									+ "' AND year = '" + year + "'";
							this.rs = this.stm.executeQuery(query1);
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
							layout.addComponents(horizontalLayout, table);
							getWindow().setContent(layout);
							UI.getCurrent().addWindow(getWindow());
							String studentname = new RetrieveStudentName(this.rs1, this.stmt)
									.fetchStudentName(studentID);
							new IndividualPaymentReportPDF().generatePDF(studentID, studentname, term, year, table,
									download);
						} else {
							new Notification("Warning",
									"No Records found for student number " + studentID + " for " + term + " " + year,
									Notification.TYPE_WARNING_MESSAGE, true).show(Page.getCurrent());
						}
					} catch (SQLException ee) {
						ee.printStackTrace();
					}
				} else {
					new Notification("Error", "Student ID " + studentID + " not found", Notification.TYPE_ERROR_MESSAGE,
							true).show(Page.getCurrent());
				}
			} else {
				new Notification("Error", "Blank Fields cannot search a record", Notification.TYPE_ERROR_MESSAGE, true)
						.show(Page.getCurrent());
			}
		});

		FormLayout formLayout = new FormLayout(termCombo, yearCombo, studentIDField, search);
		formLayout.setSpacing(true);
		formLayout.setCaption("<h1>Generate Student Payment Report<h1/>");
		formLayout.setCaptionAsHtml(true);

		HorizontalLayout hlayout = new HorizontalLayout(formLayout);
		hlayout.setSpacing(true);

		VerticalLayout horizontalLayout2 = new VerticalLayout(hlayout);
		horizontalLayout2.setSpacing(true);
		horizontalLayout2.setComponentAlignment(hlayout, Alignment.MIDDLE_CENTER);

		this.setCompositionRoot(horizontalLayout2);
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Payments")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Payments", VaadinIcons.QUESTION_CIRCLE);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}