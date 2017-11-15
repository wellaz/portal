package com.equation.accounts.sundryaccount;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.accounts.bankaccount.InsertDataIntoBankAccount;
import com.equation.accounts.payments.print.support.PrintSupport;
import com.equation.accounts.payments.students.fees.capture.IncrementReceiptNumber;
import com.equation.accounts.payments.students.fees.capture.InsertDataIntoFeesPaymentsTable;
import com.equation.accounts.sundryaccount.receipt.PrintSundryReceipt;
import com.equation.accounts.vouchers.capture.IncrementVoucherNumber;
import com.equation.school.details.retrieve.RetrieveSchoolDetails;
import com.equation.system.users.teacher.getname.FetchStuffMemberName;
import com.equation.user.session.attributes.UserSessionAttributes;
import com.equation.utils.date.DateUtility;
import com.equation.utils.date.AllTerms;
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
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class CaptureSundryEntries extends CustomComponent implements View {
	Statement stm, stmt;
	ResultSet rs, rs1;
	TextField amount, receivedfrom;
	ComboBox<String> transactionType;
	TextArea description;
	TabSheet tabs;
	String schoolID;

	public CaptureSundryEntries(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, TabSheet tabs,
			String schoolID) {
		this.stm = stm;
		this.stmt = stmt;
		this.rs = rs;
		this.rs1 = rs1;
		this.tabs = tabs;
		this.schoolID = schoolID;

		transactionType = new ComboBox<>("Select Transaction Type");
		transactionType.setRequiredIndicatorVisible(true);
		transactionType.setItems("Deposit", "Withdrawal");
		transactionType.setEmptySelectionAllowed(false);
		transactionType.setValue("Deposit");

		amount = new TextField("Enter transaction Amount");
		amount.setRequiredIndicatorVisible(true);

		description = new TextArea("Type Transaction Details");
		description.setDescription(
				"Description of the Transaction, \n ie. Where the money is coming from or where the money is going, For Example, \nTuckshop Collections");

		receivedfrom = new TextField("Received From");
		receivedfrom.setRequiredIndicatorVisible(true);

		transactionType.addValueChangeListener((e) -> {
			String value = (String) e.getValue().toString();
			if (value.equals("Withdrawal")) {
				receivedfrom.setVisible(false);
			} else {
				receivedfrom.setVisible(true);
			}
		});

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.addClickListener((e) -> {
			String transactionTypee = (String) transactionType.getValue();
			String amountt = amount.getValue();
			String descriptionn = description.getValue();
			String date = new DateUtility().getDate();
			String receivedfromm = receivedfrom.getValue();
			InsertDataIntoSundryAccount insertDataIntoSundryAccount = new InsertDataIntoSundryAccount(stm);

			if (!(amountt.equals("") || descriptionn.equals("") || transactionTypee.equals(""))) {
				String cashier = (String) getSession().getAttribute(UserSessionAttributes.USER_ID);
				double amount = Double.parseDouble(amountt);
				if (amount > 0) {
					if (transactionTypee.equals("Deposit")) {
						int receiptInvoiceNumber = new IncrementReceiptNumber(rs, rs1, stm, stmt).nextReceiptNumberr();
						if (receiptInvoiceNumber > 0) {
							insertDataIntoSundryAccount.insertData(Integer.toString(receiptInvoiceNumber), 0, amount,
									descriptionn, date, cashier, receivedfromm, schoolID);
							makeReceipt(receiptInvoiceNumber, amount, cashier, receivedfromm, descriptionn);
							new Notification("Information",
									descriptionn.substring(0, 10) + "...<br/>Transaction as posted successfully!",
									Notification.Type.TRAY_NOTIFICATION, true).show(Page.getCurrent());
							this.amount.clear();
							this.description.clear();
							this.receivedfrom.clear();
							this.amount.focus();
						}
					}
					if (transactionTypee.equals("Withdrawal")) {
						int vouchernumber = new IncrementVoucherNumber(rs, rs1, stm, stmt).nextVoucherNumberr();
						String from = "Sundry Account";
						if (vouchernumber > 0) {
							insertDataIntoSundryAccount.insertData(Integer.toString(vouchernumber), amount, 0,
									descriptionn, date, cashier, from, schoolID);
						}
					}
				} else {
					new Notification("Warning", "$" + amountt + " cannot be effected",
							Notification.Type.WARNING_MESSAGE, true).show(Page.getCurrent());
				}
			} else {
				new Notification("<h1>Blank Field(s) detected, Please Enter all the required Fields<h1/>", "",
						Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			}

		});
		FormLayout formLayout = new FormLayout(amount, transactionType, receivedfrom, description, submit);
		formLayout.setCaption("Capture all Sundry Entries");
		formLayout.setSpacing(true);
		formLayout.setIcon(VaadinIcons.BRIEFCASE);

		VerticalLayout verticalLayout = new VerticalLayout();

		HorizontalLayout gridLayout = new HorizontalLayout();
		gridLayout.setSpacing(true);
		// gridLayout.addComponents(formLayout);

		verticalLayout.addComponent(gridLayout);
		verticalLayout.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);

		this.setCompositionRoot(verticalLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

	public void makeReceipt(int receiptNumber, double amountTendered, String userID, String receivedFrom,
			String description) {
		RetrieveSchoolDetails schoolDetails = new RetrieveSchoolDetails(rs, rs1, stm, stmt, schoolID);
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
				schoolID);

		new InsertDataIntoFeesPaymentsTable(stm, stmt, rs, rs1).insertData(receiptNumber, receivedFrom, userID,
				amountTendered, term, year, receivedFrom, schoolID, date, time, 0,
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
}