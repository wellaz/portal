package com.equation.accounts.otheraccounts.capture;

import java.sql.Statement;

import com.equation.utils.date.DateUtility;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class OtherAccountCapture extends CustomComponent {
	TextField accountName, amountField;
	TextArea description;
	Statement stm;
	String schoolID;

	public OtherAccountCapture(Statement stm, String schoolD) {
		this.stm = stm;
		this.schoolID = schoolD;

		accountName = new TextField("Account Name");
		amountField = new TextField("Amount");

		description = new TextArea("Description");

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.setIcon(VaadinIcons.UPLOAD);

		submit.addClickListener((e) -> {
			String accountNametxt = accountName.getValue();
			String amountFieldtxt = amountField.getValue();
			String descriptiontxt = description.getValue();

			if (!(accountNametxt.equals("") || amountFieldtxt.equals("") || descriptiontxt.equals(""))) {
				DateUtility dateUtility = new DateUtility();
				String date = dateUtility.getDate();
				String time = dateUtility.getTime();
				double amount = Double.parseDouble(amountFieldtxt);
				new InsertDataIntoOtherAccountsTable(stm).insertData(accountNametxt, amount, descriptiontxt, date, time,
						schoolID);

				new Notification("Success", "The account " + accountNametxt + " was successfully created!",
						Notification.Type.HUMANIZED_MESSAGE, true).show(Page.getCurrent());
				accountName.clear();
				amountField.clear();
				description.clear();
				accountName.focus();

			}

		});

		FormLayout formLayout = new FormLayout(accountName, amountField, description, submit);
		formLayout.setSpacing(true);
		HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
		horizontalLayout.setSpacing(true);
		VerticalLayout layout = new VerticalLayout(horizontalLayout);
		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);

		this.setCompositionRoot(layout);
	}

}
