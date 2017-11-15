package com.equation.accounts.bankaccount;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.accounts.vouchers.capture.IncrementVoucherNumber;
import com.equation.accounts.vouchers.capture.InsertDataIntoVouchersTable;
import com.equation.utils.date.DateUtility;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial", "deprecation" })
public class CaptureBankAccountWithdrawal extends CustomComponent implements View {
	Statement stm, stmt;
	ResultSet rs, rs1;
	// ComboBox destinationAccounts;
	TextField principalAmount, invoiceNumber;
	TabSheet tabs;

	TextArea narration;
	String userID, schoolID;

	public CaptureBankAccountWithdrawal(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, TabSheet tabs,
			String userID, String schoolID) {
		this.stm = stm;
		this.rs = rs;
		this.stmt = stmt;
		this.rs1 = rs1;
		this.tabs = tabs;
		this.userID = userID;
		this.schoolID = schoolID;

		// destinationAccounts = new ComboBox("Source Account");
		// destinationAccounts.setRequired(true);

		// destinationAccounts.addItems(
		// AllSchoolAccounts.allAccountsArray().subList(1,
		// AllSchoolAccounts.allAccountsArray().size() - 1));

		narration = new TextArea("Narration");
		narration.setRequiredIndicatorVisible(true);

		principalAmount = new TextField("Principal Amount");
		principalAmount.setRequiredIndicatorVisible(true);
		// principalAmount.addValidator(new DoubleValidator("Correct the
		// amount!"));

		invoiceNumber = new TextField("Receipt / Invoice Number");
		invoiceNumber.setRequiredIndicatorVisible(true);
		invoiceNumber.setValue("" + new IncrementVoucherNumber(rs, rs1, stm, stmt).returnLastVoucherNumber());
		invoiceNumber.setReadOnly(true);

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_DANGER);
		submit.addClickListener((e) -> {
			String sourceaccount = "Revenue Suspense Account";
			// String destinationaccount = (String)
			// destinationAccounts.getValue();

			String date = new DateUtility().getDate();
			String time = new DateUtility().getTime();
			String description = narration.getValue();
			String principalAmountt = principalAmount.getValue();
			if (!(principalAmountt.equals(""))) {
				double principalAmounttt = Double.parseDouble(principalAmountt);
				RetrieveBankAccountBalance accountBalance = new RetrieveBankAccountBalance(rs, stm);
				double balance = accountBalance.getBalance();
				if (principalAmounttt < balance) {

					String receiptInvoiceNumber = invoiceNumber.getValue();
					UI.getCurrent().addWindow(createTransationDetailsConfirmationWindow(receiptInvoiceNumber,
							sourceaccount, principalAmounttt, description, date, time, schoolID));
				} else {
					new Notification("Error",
							"Revenue Suspense Float Balance $" + balance
									+ "<br/>You cannot perform a transaction above the float balance!",
							Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
				}
			} else {
				new Notification("Error", "A blank field has been detected and a<br/>withdrawal cannot be posted!",
						Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
			}

		});
		FormLayout formLayout = new FormLayout(invoiceNumber, principalAmount, narration, submit);
		formLayout.setCaption("Post Revenue Suspense Account Withdrawal");
		formLayout.setSpacing(true);
		formLayout.setIcon(FontAwesome.BRIEFCASE);

		VerticalLayout verticalLayout = new VerticalLayout();

		HorizontalLayout gridLayout = new HorizontalLayout();
		gridLayout.setSpacing(true);
		gridLayout.addComponents(formLayout);

		verticalLayout.addComponent(gridLayout);
		verticalLayout.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);

		this.setCompositionRoot(verticalLayout);

	}

	/**
	 * 
	 * @param receiptInvoiceNumber
	 *            The invoice or Receipt Number
	 * @param sourceaccount
	 *            The Source Account
	 * @param destinationaccount
	 * @param principalAmount
	 * @param description
	 * @param date
	 * @return
	 */

	private Window createTransationDetailsConfirmationWindow(String receiptInvoiceNumber, String sourceaccount,
			double principalAmount, String description, String date, String time, String schoolID) {

		Window window = new Window("Confirm Submition");
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setMargin(true);
		window.setContent(verticalLayout);
		window.setModal(true);
		window.center();

		Label lbl1 = new Label("Source Account");
		Label lbl2 = new Label(sourceaccount);
		// Label lbl3 = new Label("Destination Account");
		// Label lbl4 = new Label(destinationaccount);
		Label lbl5 = new Label("Princial Amount");
		Label lbl6 = new Label("$" + principalAmount);

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.addClickListener((e) -> {
			InsertDataIntoBankAccount dataIntoBankAccount = new InsertDataIntoBankAccount(stm);
			dataIntoBankAccount.postBankAccountTransaction(description, principalAmount, 0, date, time, schoolID);

			InsertDataIntoVouchersTable dataIntoVouchersTable = new InsertDataIntoVouchersTable(stm);
			dataIntoVouchersTable.insertData(receiptInvoiceNumber, "Cash", userID, "pending approval", userID, date,
					principalAmount, schoolID, "Open");

			// String tablename = new
			// MatchAccountWithItsTable().accountTableName(destinationaccount);
			// InsertDataIntoCommonAccount account = new
			// InsertDataIntoCommonAccount(stm);
			// account.InsertData(tablename, receiptInvoiceNumber,
			// principalAmount, 0, date);

			window.close();
			new Notification("Success", "Revenue Suspense withdrawal successfully posted!",
					Notification.TYPE_HUMANIZED_MESSAGE, true).show(Page.getCurrent());
			// destinationAccounts.clear();
			this.principalAmount.clear();
			invoiceNumber.clear();
			narration.clear();
		});
		Button cancel = new Button("Cancel");
		cancel.addStyleName(ValoTheme.BUTTON_DANGER);
		cancel.addClickListener((e) -> {
			window.close();
		});

		HorizontalLayout buttonhorizontalLayout = new HorizontalLayout(submit, cancel);
		buttonhorizontalLayout.setSpacing(true);

		GridLayout gridLayout = new GridLayout(2, 4);
		gridLayout.addComponent(lbl1, 0, 0);
		gridLayout.addComponent(lbl2, 1, 0);
		// gridLayout.addComponent(lbl3, 0, 1);
		// gridLayout.addComponent(lbl4, 1, 1);
		gridLayout.addComponent(lbl5, 0, 1);
		gridLayout.addComponent(lbl6, 1, 1);
		gridLayout.addComponent(buttonhorizontalLayout, 1, 3);

		window.setContent(gridLayout);

		return window;
	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Bank Account Withdrawal")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Bank Account Withdrawal", FontAwesome.MONEY);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
