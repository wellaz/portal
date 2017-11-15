package com.equation.accounts.cashreport;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.user.session.attributes.UserSessionAttributes;
import com.equation.utils.date.DateUtility;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial", "deprecation" })
public class CaptureCashReportDetails extends CustomComponent implements View {
	TextField openingBalance, cashorchequeReceived, firstReceiptNumber, lastReceiptNumber, cashBanked, preparedBy,
			approvedBy;
	Statement stm;
	ResultSet rs;

	public CaptureCashReportDetails(Statement stm, ResultSet rs) {
		this.rs = rs;
		this.stm = stm;

		openingBalance = new TextField("Opening Balance");
		openingBalance.setRequiredIndicatorVisible(true);
		// openingBalance.addValidator(new DoubleValidator("Correct the
		// entry!"));

		cashorchequeReceived = new TextField("Cash Or Cheque Received");
		cashorchequeReceived.setRequiredIndicatorVisible(true);
		// cashorchequeReceived.addValidator(new DoubleValidator("Correct the
		// entry!"));

		firstReceiptNumber = new TextField("First Rceeipt Number");
		firstReceiptNumber.setRequiredIndicatorVisible(true);
		// firstReceiptNumber.addValidator(new DoubleValidator("Correct the
		// entry!"));

		lastReceiptNumber = new TextField("Last Receipt Number");
		lastReceiptNumber.setRequiredIndicatorVisible(true);
		// lastReceiptNumber.addValidator(new DoubleValidator("Correct the
		// entry!"));

		cashBanked = new TextField("Cash Banked");
		cashBanked.setRequiredIndicatorVisible(true);
		// cashBanked.addValidator(new DoubleValidator("Correct the entry!"));

		preparedBy = new TextField("Prepared By");
		preparedBy.setRequiredIndicatorVisible(true);
		// preparedBy.addValidator(new DoubleValidator("Correct the entry!"));

		approvedBy = new TextField("Approver");
		approvedBy.setRequiredIndicatorVisible(true);

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_DANGER);
		submit.addClickListener((e) -> {
			String openingBalancee = openingBalance.getValue();
			String cashorchequeReceivedd = cashorchequeReceived.getValue();
			String firstReceiptNumberr = firstReceiptNumber.getValue();
			String lastReceiptNumberr = lastReceiptNumber.getValue();
			String cashBankedd = cashBanked.getValue();
			String preparedByy = preparedBy.getValue();
			String approvedByy = approvedBy.getValue();
			String date = new DateUtility().getDate();
			String schoolID = (String) getSession().getAttribute(UserSessionAttributes.SCHOOL_EMIS_NUMBER);

			if (!(openingBalancee.equals("") || cashorchequeReceivedd.equals("") || firstReceiptNumberr.equals("")
					|| lastReceiptNumberr.equals("") || cashBankedd.equals("") || preparedByy.equals("")
					|| approvedByy.equals(""))) {
				double openingBalanceee = Double.parseDouble(openingBalancee);
				double cashorchequeReceiveddd = Double.parseDouble(cashorchequeReceivedd);
				int firstReceiptNumberrr = Integer.parseInt(firstReceiptNumberr);
				int lastReceiptNumberrr = Integer.parseInt(lastReceiptNumberr);
				double cashBankeddd = Double.parseDouble(cashBankedd);

				InsertDataIntoCashReportTable cashReportTable = new InsertDataIntoCashReportTable(stm);
				cashReportTable.insertData(openingBalanceee, cashorchequeReceiveddd, firstReceiptNumberrr,
						lastReceiptNumberrr, cashBankeddd, preparedByy, approvedByy, date, schoolID);
				new Notification("Success", "End Of Day Cash Report Effected", Notification.TYPE_HUMANIZED_MESSAGE,
						true).show(Page.getCurrent());

			} else {
				new Notification("Error", "An empty field has been detected and the record cannot be submitted!",
						Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
			}
		});

		FormLayout formLayout = new FormLayout(openingBalance, cashorchequeReceived, firstReceiptNumber,
				lastReceiptNumber, cashBanked, preparedBy, approvedBy, submit);
		formLayout.setCaption("End Of Day Cash Report");
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

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
