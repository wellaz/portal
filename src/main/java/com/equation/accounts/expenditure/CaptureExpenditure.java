package com.equation.accounts.expenditure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.accounts.collection.AllSchoolAccounts;
import com.equation.accounts.collection.MatchAccountWithItsTable;
import com.equation.accounts.common.postings.InsertDataIntoCommonAccount;
import com.equation.utils.date.DateUtility;
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
@SuppressWarnings({ "serial" })
public class CaptureExpenditure extends CustomComponent implements View {
	Statement stm, stmt;
	ResultSet rs, rs1;
	String schoolID, userID;
	TabSheet tabs;
	private TextField receiptNumber;
	private ComboBox<String> voucherNumber;
	private TextField amount;
	private TextArea description;
	private TextField supplier;
	private Button submit;
	private ComboBox<String> source;

	public CaptureExpenditure(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String schoolID,
			TabSheet tabs, String userID) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.schoolID = schoolID;
		this.tabs = tabs;
		this.userID = userID;

		receiptNumber = new TextField("Receipt Number");
		receiptNumber.setRequiredIndicatorVisible(true);

		voucherNumber = new ComboBox<>("Voucher Number");
		voucherNumber.setItems(
				new OpenVoucherNumbers(this.stm, this.stmt, this.rs, this.rs1).getOpenVouchers("Open", this.schoolID));
		voucherNumber.setRequiredIndicatorVisible(true);
		voucherNumber.setEmptySelectionAllowed(false);

		amount = new TextField("Amount");
		amount.setRequiredIndicatorVisible(true);
		// amount.addValidator(new DoubleValidator("Enter Correct Amount!"));

		source = new ComboBox<>("Source Account");
		source.setRequiredIndicatorVisible(true);
		source.setEmptySelectionAllowed(false);
		source.setItems(
				AllSchoolAccounts.allAccountsArray().subList(1, AllSchoolAccounts.allAccountsArray().size() - 1));

		description = new TextArea("Description");
		description.setRequiredIndicatorVisible(true);

		supplier = new TextField("Supplier");
		supplier.setRequiredIndicatorVisible(true);

		submit = new Button("Submit");
		submit.setIcon(VaadinIcons.UPLOAD);
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.addClickListener((e) -> {
			String receiptNumberr = receiptNumber.getValue();
			String voucherNumberr = (String) voucherNumber.getValue().toString();
			String amountString = amount.getValue();
			String descriptionn = description.getValue();
			String supplierr = supplier.getValue();

			if (!(receiptNumberr.equals("") || voucherNumberr.equals("") || amountString.equals("")
					|| descriptionn.equals("") || supplierr.equals(""))) {

				double amountentered = Double.parseDouble(amountString);
				if (amountentered > 0) {
					double principalAmount = new PrincipalVoucherAmout(this.rs, this.stm)
							.getPrincipalVoucherAmount(voucherNumberr, this.schoolID);
					if (amountentered < principalAmount) {
						double amountSpent = new SingleVoucherExpenses(this.rs, this.stm)
								.getSingleVoucherExpenses(voucherNumberr, this.schoolID);
						double change = principalAmount - amountSpent;
						if (change >= amountentered) {

							DateUtility dateUtility = new DateUtility();
							String date = dateUtility.getDate();
							String time = dateUtility.getTime();

							String sourceAccount = (String) source.getValue();

							String tablename = new MatchAccountWithItsTable().accountTableName(sourceAccount);
							InsertDataIntoCommonAccount account = new InsertDataIntoCommonAccount(stm);
							account.InsertData(tablename, voucherNumberr, amountentered, 0, date);

							InsertDataIntoExpenditureTable dataIntoExpenditureTable = new InsertDataIntoExpenditureTable(
									stm);
							dataIntoExpenditureTable.insertData(receiptNumberr, voucherNumberr, amountentered,
									descriptionn, supplierr, date, time, schoolID, sourceAccount);
							if (change == amountentered) {
								String newStatus = "Closed";
								String updateQuery = "UPDATE voucherstable SET status '" + newStatus
										+ "' WHERE voucherNumber = '" + voucherNumberr + "' AND schools.schoolID = '"
										+ schoolID + "'";
								try {
									this.stm.executeUpdate(updateQuery);
								} catch (SQLException ee) {
									ee.printStackTrace();
								}
							}
							new Notification("Success", "Expense successfully posted!",
									Notification.Type.HUMANIZED_MESSAGE, true).show(Page.getCurrent());

						} else {
							new Notification(
									"<h1>Voucher Number " + voucherNumberr + " only requires $"
											+ (double) Math.round(change * 100) / 100
											+ " in order for it to balance!<br/>Post an expense that is up to $"
											+ (double) Math.round(change * 100) / 100 + "<h1/>",
									"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
							amount.focus();
						}

					} else {
						new Notification(
								"<h1>$" + amount + " is way greater than $" + principalAmount
										+ " original voucher's principal amount!<br/>Correct the entered amount!!!<h1/>",
								"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
						amount.focus();
					}

				} else {
					new Notification("<h1>Enter amount that is more than $0.00<h1/>", "",
							Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
					amount.focus();
				}

			} else {
				new Notification("<h1>A Blank Field has been detected<h1/>", "", Notification.Type.ERROR_MESSAGE, true)
						.show(Page.getCurrent());
			}
		});

		FormLayout formLayout = new FormLayout(receiptNumber, voucherNumber, amount, source, supplier, description,
				submit);
		formLayout.setSpacing(true);
		formLayout.setCaption("Expenditure Entry Form");

		HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
		VerticalLayout layout = new VerticalLayout(horizontalLayout);
		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);

		this.setCompositionRoot(layout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}