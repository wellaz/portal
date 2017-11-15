package com.equation.accounts.vouchers.capture;

import java.sql.ResultSet;
import java.sql.Statement;

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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial" })
public class CaptureNewVoucherDetails extends CustomComponent implements View {
	TextField voucherNumber, preparedBy, authorisedBy, paidBy;
	ComboBox<String> paymentMethod;

	Statement stm;
	ResultSet rs;
	TabSheet tabs;
	private TextField amountField;
	String schoolID;

	public CaptureNewVoucherDetails(Statement stm, ResultSet rs, TabSheet tabs, String schoolID) {
		this.rs = rs;
		this.stm = stm;
		this.tabs = tabs;
		this.schoolID = schoolID;

		voucherNumber = new TextField("Enter Voucher Number");
		voucherNumber.setRequiredIndicatorVisible(true);

		preparedBy = new TextField("Prepared By");
		preparedBy.setRequiredIndicatorVisible(true);

		authorisedBy = new TextField("Authorized By");
		authorisedBy.setRequiredIndicatorVisible(true);

		amountField = new TextField("Amount");
		amountField.setRequiredIndicatorVisible(true);
		// amountField.addValidator(new DoubleValidator("Enter Amount"));

		paidBy = new TextField("Paid By");
		paidBy.setRequiredIndicatorVisible(true);

		paymentMethod = new ComboBox<>("Payment Method");
		paymentMethod.setRequiredIndicatorVisible(true);
		paymentMethod.setItems(PaymentMethods.paymentMethodsArray());

		Button submit = new Button("Submit");
		submit.addStyleName("voucher_submit_button");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.addClickListener((e) -> {
			String voucherNumberr = voucherNumber.getValue();
			String amountString = amountField.getValue();

			String paymentMethodd = (String) paymentMethod.getValue();
			String preparedByy = preparedBy.getValue();
			String authorisedByy = authorisedBy.getValue();
			String paidByy = paidBy.getValue();
			String date = new DateUtility().getDate();

			if (!(voucherNumberr.equals("") || paymentMethodd.equals("") || preparedByy.equals("")
					|| authorisedByy.equals("") || paidByy.equals("") || amountString.equals(""))) {
				// int voucherNumberrr = Integer.parseInt(voucherNumberr);
				InsertDataIntoVouchersTable dataIntoVouchersTable = new InsertDataIntoVouchersTable(stm);
				double amount = Double.parseDouble(amountString);
				String status = "Open";

				dataIntoVouchersTable.insertData(voucherNumberr, paymentMethodd, preparedByy, authorisedByy, paidByy,
						date, amount, schoolID, status);
				new Notification("Success", "Voucher Number " + voucherNumberr + " was<br/>successfully saved!S",
						Notification.Type.HUMANIZED_MESSAGE, true).show(Page.getCurrent());
			} else {
				new Notification("<h1>A blank field has been detected and the record<br/>cannot be submitted<h1/>", "",
						Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			}

		});

		FormLayout formLayout = new FormLayout(voucherNumber, amountField, paymentMethod, preparedBy, authorisedBy,
				paidBy, submit);
		formLayout.setCaption("Enter New Voucher Details");
		formLayout.setSpacing(true);
		formLayout.setIcon(VaadinIcons.ARCHIVE);

		VerticalLayout verticalLayout = new VerticalLayout();

		HorizontalLayout gridLayout = new HorizontalLayout();
		gridLayout.setSpacing(true);
		gridLayout.addComponents(formLayout);

		verticalLayout.addComponent(gridLayout);
		verticalLayout.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);

		this.setCompositionRoot(verticalLayout);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}