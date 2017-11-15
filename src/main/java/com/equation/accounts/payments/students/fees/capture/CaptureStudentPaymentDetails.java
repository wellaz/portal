package com.equation.accounts.payments.students.fees.capture;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.equation.accounts.administrator.receiptsbooks.RemindBursarOfReceiptLeft;
import com.equation.accounts.bankaccount.InsertDataIntoBankAccount;
import com.equation.accounts.otheraccounts.collection.OtherAccountsCollection;
import com.equation.accounts.payments.print.support.PrintSupport;
import com.equation.accounts.sundryaccount.InsertDataIntoSundryAccount;
import com.equation.accounts.sundryaccount.receipt.PrintSundryReceipt;
import com.equation.school.details.retrieve.RetrieveSchoolDetails;
import com.equation.student.name.retrieve.RetrieveStudentName;
import com.equation.system.users.teacher.getname.FetchStuffMemberName;
import com.equation.utils.application.basepath.ApplicationBasePath;
import com.equation.utils.date.DateUtility;
import com.equation.utils.date.AllTerms;
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
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial" })
public class CaptureStudentPaymentDetails extends CustomComponent implements View {

	int year;

	public TextField amount, paidBy, studentID;
	Statement stm, stmt;
	ResultSet rs, rs1;
	String emisNumber;
	String userID;
	FeesCalculator calculator;

	private ComboBox<String> transactionType;

	public CaptureStudentPaymentDetails(Statement stm, ResultSet rs, Statement stmt, ResultSet rs1, String emisNumber,
			String userID) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.emisNumber = emisNumber;
		this.userID = userID;
		calculator = new FeesCalculator(this.rs, this.rs1, this.stm, this.stmt);

		transactionType = new ComboBox<>("Description");
		transactionType.setEmptySelectionAllowed(false);
		transactionType.setRequiredIndicatorVisible(true);
		ArrayList<String> array = new OtherAccountsCollection(this.rs1, this.stmt).otherAccounts(this.emisNumber);
		transactionType.setItems(array);
		transactionType.setValue(array.get(0));

		studentID = new TextField("Enter Student ID:");
		studentID.setRequiredIndicatorVisible(true);

		transactionType.addValueChangeListener((e) -> {
			String value = (String) e.getValue().toString();
			if (!value.equals(array.get(0))) {
				studentID.setVisible(false);
			} else {
				studentID.setVisible(true);
			}
		});

		amount = new TextField("Enter Amount");
		amount.setRequiredIndicatorVisible(true);
		// amount.addValidator(new DoubleValidator("Entered value not valid"));

		paidBy = new TextField("Name of Payor");
		paidBy.setRequiredIndicatorVisible(true);

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_DANGER);
		submit.addStyleName("fees_payment_submit_button");
		submit.addClickListener((e) -> {
			String studentIDd = (String) studentID.getValue();
			String paidByy = paidBy.getValue();
			String amm = amount.getValue();
			String descriptionAccount = (String) transactionType.getValue();

			String studentname = new RetrieveStudentName(rs1, stmt).fetchStudentName(studentIDd);

			String date = new DateUtility().getDate();
			String time = new DateUtility().getTime();

			if (!(studentIDd.equals("") || amm.equals("") || paidByy.equals("") || descriptionAccount.equals(""))) {
				double amountt = Double.parseDouble(amm);
				if (descriptionAccount.equals(
						new OtherAccountsCollection(this.rs1, this.stmt).otherAccounts(this.emisNumber).get(0))) {
					String query = "SELECT term,year,balance FROM feesreceived,schools WHERE studentID = '" + studentIDd
							+ "' AND schools.schoolID = '" + emisNumber + "'";
					try {
						this.rs1 = this.stmt.executeQuery(query);
						if (this.rs1.last()) {
							String term = this.rs1.getString(1);
							String year = this.rs1.getString(2);
							double currentBalance = this.rs1.getDouble(3);

							if (amountt >= 1) {
								UI.getCurrent()
										.addWindow(createConfirmationWindow(amountt, currentBalance, term,
												Integer.parseInt(year), studentIDd, emisNumber, userID, paidByy, date,
												time, studentID, amount, paidBy, studentname));

							} else {
								new Notification(
										"<h1><br/>Amount rejected!<br/><br/>An Amount that is less than $" + 1.00
												+ " cannot be posted!<br/><br/>Type in the correct amount.<h1/>",
										"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
							}
						} else {
							String thisterm = new AllTerms().thisTerm();
							int thisyear = Integer.parseInt(new DateUtility().getYear());
							// double currentBalance = 0;
							// String lastTerm = new
							// ReturnCurrentTermTerms().lastTerm(thisterm,
							// thisyear).split(",")[0];
							// String lastYear = new
							// ReturnCurrentTermTerms().lastTerm(thisterm,
							// thisyear).split(",")[1];
							double currentBalance = calculator.getStructureTotal(thisyear, emisNumber);

							if (amountt > 0) {
								UI.getCurrent()
										.addWindow(createConfirmationWindow(amountt, currentBalance, thisterm, thisyear,
												studentIDd, emisNumber, userID, paidByy, date, time, studentID, amount,
												paidBy, studentname));
							}

						}

					} catch (SQLException ee) {
						ee.printStackTrace();
					}
				} else {

					int receiptInvoiceNumber = new IncrementReceiptNumber(rs, rs1, stm, stmt).nextReceiptNumberr();
					if (receiptInvoiceNumber > 0) {
						new InsertDataIntoSundryAccount(this.stm).insertData(Integer.toString(receiptInvoiceNumber), 0,
								amountt, descriptionAccount, date, userID, paidByy, emisNumber);
						makeReceipt(receiptInvoiceNumber, amountt, userID, paidByy, descriptionAccount);
						new Notification("Information",
								descriptionAccount.substring(0, 20) + "...<br/>Transaction as posted successfully!",
								Notification.Type.TRAY_NOTIFICATION, true).show(Page.getCurrent());
					}
				}
			} else {
				new Notification("<h1>Blank fields cannot be submitted!<h1/>", "", Notification.Type.ERROR_MESSAGE,
						true).show(Page.getCurrent());
			}
		});

		FormLayout formLayout = new FormLayout();
		formLayout.addStyleName("students_payments_formlayout");
		formLayout.setCaption("Submit School Fees Payment Details");
		formLayout.setSpacing(true);
		formLayout.addComponents(transactionType, studentID, amount, paidBy, submit);

		VerticalLayout verticalLayout = new VerticalLayout();

		HorizontalLayout gridLayout = new HorizontalLayout();
		gridLayout.setSpacing(true);
		gridLayout.addComponents(formLayout);

		verticalLayout.addComponent(gridLayout);
		verticalLayout.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);
		this.setCompositionRoot(verticalLayout);

	}

	private Window createConfirmationWindow(double amountTendered, double currentBalance, String term, int year,
			String studentID, String schoolID, String userID, String paidBy, String date, String time,
			TextField studentIDField, TextField amountField, TextField paidByField, String studentname) {
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setMargin(true);
		Window subWindow = new Window("Confirm Payment", verticalLayout);

		FileResource resource = new FileResource(
				new File(ApplicationBasePath.basePath() + "/WEB-INF/images/" + studentID + ".png"));
		Image image = new Image();
		image.setSource(resource);
		image.setWidth("250px");
		image.setHeight("150px");

		Label namelbl = new Label("Student Name");
		Label studentidlbl = new Label("Student ID");
		Label amountlbl = new Label("Cash Tendered");
		Label termlbl = new Label("For ");
		// Label receiptlbl = new Label("Receipt Number");
		Label name = new Label(studentname);
		Label studentid = new Label(studentID);
		Label amountt = new Label("$" + amountTendered);
		Label termm = new Label(term + " " + year);

		Button submit = new Button("Submit");
		submit.setIcon(VaadinIcons.UPLOAD);
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.addStyleName("subwindow_fees_payment_submit_button");
		submit.addClickListener((e) -> {

			calculator.calculateFees(amountTendered, currentBalance, term, year, studentID, schoolID, userID, paidBy,
					date, time);
			RemindBursarOfReceiptLeft remindBursarOfReceiptLeft = new RemindBursarOfReceiptLeft(this.rs, this.rs1,
					this.stm, this.stmt, this.emisNumber);
			remindBursarOfReceiptLeft.scanForReceiptsLeft();

			// InsertDataIntoFeesPaymentsTable dataIntoFeesPaymentsTable = new
			// InsertDataIntoFeesPaymentsTable(stm);
			// dataIntoFeesPaymentsTable.insertData(receiptNumber, studentID,
			// userID, amount, term, year, paidBy,
			// emisNumber, date, time);
			subWindow.close();
			studentIDField.clear();
			amountField.clear();
			paidByField.clear();
			new Notification("<h1>Payment was successfully submitted!<h1/>", "", Notification.Type.HUMANIZED_MESSAGE,
					true).show(Page.getCurrent());
		});
		Button cancel = new Button("Cancel");
		cancel.setIcon(VaadinIcons.CLOSE);
		cancel.addStyleName(ValoTheme.BUTTON_DANGER);
		cancel.addClickListener((e) -> {
			UI.getCurrent().removeWindow(subWindow);
			subWindow.close();
		});

		GridLayout gridLayout = new GridLayout(2, 6, namelbl, name, studentidlbl, studentid, amountlbl, amountt,
				termlbl, termm, submit, cancel);
		gridLayout.setSpacing(true);
		verticalLayout.addComponents(image, gridLayout);
		verticalLayout.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);

		subWindow.setWidth("40%");
		subWindow.setHeight("80%");
		// subWindow.setSizeFull();
		subWindow.setModal(true);
		subWindow.center();
		return subWindow;
	}

	public void makeReceipt(int receiptNumber, double amountTendered, String userID, String receivedFrom,
			String description) {
		RetrieveSchoolDetails schoolDetails = new RetrieveSchoolDetails(rs, rs1, stm, stmt, emisNumber);
		String schoolName = schoolDetails.getSchoolName();
		String schoolPostal = schoolDetails.getSchoolPostal();
		String schoolPhone = schoolDetails.getSchoolPhone();
		String schoolEmail = schoolDetails.getSchoolEmail();
		String schoolCell = schoolDetails.getSchoolCell();
		String tellerName = new FetchStuffMemberName(rs, rs1, stm, stmt).getActualName(userID);
		String term = new AllTerms().thisTerm();
		String termm = term + " " + new DateUtility().getYear();
		String date = new DateUtility().getDate();
		String time = new DateUtility().getTime();
		int year = Integer.parseInt(new DateUtility().getYear());

		new InsertDataIntoBankAccount(stm).postBankAccountTransaction(description, 0, amountTendered, date, time,
				emisNumber);

		new InsertDataIntoFeesPaymentsTable(stm, stmt, rs, rs1).insertData(receiptNumber, receivedFrom, userID,
				amountTendered, term, year, receivedFrom, emisNumber, date, time, 0,
				description.substring(0, (description.length() < 50) ? description.length() : 50));

		PrinterJob pj = PrinterJob.getPrinterJob();
		pj.setPrintable(new PrintSundryReceipt(receiptNumber, amountTendered, schoolName, tellerName, schoolPostal,
				schoolPhone, schoolCell, schoolEmail, termm, receivedFrom, description),
				PrintSupport.getPageFormat(pj, 0));

		boolean ok = pj.printDialog();
		if (ok) {
			try {
				pj.print();
			} catch (PrinterException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}