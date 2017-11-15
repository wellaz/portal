package com.equation.accounts.vouchers.capture;

import java.sql.Statement;

import com.equation.user.session.attributes.UserSessionAttributes;
import com.equation.utils.date.DateUtility;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
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
@SuppressWarnings("serial")
public class CaptureNewVoucherRange extends CustomComponent implements View {
	TextField bookNumber, firstVoucherNumber, finalVoucherNumber;
	Statement stm;

	public CaptureNewVoucherRange(Statement stm) {
		this.stm = stm;

		bookNumber = new TextField();
		bookNumber.setRequiredIndicatorVisible(true);

		firstVoucherNumber = new TextField();
		firstVoucherNumber.setRequiredIndicatorVisible(true);

		finalVoucherNumber = new TextField();
		finalVoucherNumber.setRequiredIndicatorVisible(true);

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.addClickListener((e) -> {
			String schoolIDd = (String) getSession().getAttribute(UserSessionAttributes.SCHOOL_EMIS_NUMBER);
			String bookNumberr = bookNumber.getValue();
			String firstVoucherNumberr = firstVoucherNumber.getValue();
			String finalVoucherNumberr = finalVoucherNumber.getValue();
			String datePosted = new DateUtility().getDate();

			if (!(bookNumberr.equals("") || firstVoucherNumberr.equals("") || finalVoucherNumberr.equals(""))) {
				int firstVoucherNumberrr = Integer.parseInt(firstVoucherNumberr);
				int finalVoucherNumberrr = Integer.parseInt(finalVoucherNumberr);
				InsertDataIntoVoucherNumbersTable dataIntoVoucherNumbersTable = new InsertDataIntoVoucherNumbersTable(
						stm);
				dataIntoVoucherNumbersTable.insertData(schoolIDd, bookNumberr, firstVoucherNumberrr,
						finalVoucherNumberrr, datePosted);
				bookNumber.clear();
				firstVoucherNumber.clear();
				finalVoucherNumber.clear();
				new Notification("Success", "The new Voucher Numbers Range is successfully submitted!",
						Notification.Type.HUMANIZED_MESSAGE, true).show(Page.getCurrent());
			} else {
				new Notification("<h1>A blank field has been detected and the record cannot be submitted<h1/>", "",
						Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			}
		});

		FormLayout formLayout = new FormLayout(bookNumber, firstVoucherNumber, finalVoucherNumber, submit);
		formLayout.setCaption("Voucher Numbers Entry Form");
		formLayout.setSpacing(true);
		formLayout.setIcon(VaadinIcons.BRIEFCASE);

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