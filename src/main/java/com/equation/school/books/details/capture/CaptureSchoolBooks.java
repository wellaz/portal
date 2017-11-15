package com.equation.school.books.details.capture;

import java.sql.Statement;

import com.equation.user.session.attributes.UserSessionAttributes;
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
@SuppressWarnings("serial")
public class CaptureSchoolBooks extends CustomComponent implements View {
	TextField title, iSBN, quantity;
	ComboBox<String> subject, status;
	Statement stm;
	TabSheet tabs;

	@SuppressWarnings("deprecation")
	public CaptureSchoolBooks(Statement stm, TabSheet tabs) {
		this.stm = stm;
		this.tabs = tabs;

		title = new TextField("Enter Book Title");
		title.setRequiredIndicatorVisible(true);

		iSBN = new TextField("Enter Book ISBN");
		iSBN.setRequiredIndicatorVisible(true);

		quantity = new TextField("Enter Quantity");
		quantity.setRequiredIndicatorVisible(true);
		// quantity.addValidator(new IntegerRangeValidator("Error", 1, 99999));

		subject = new ComboBox<>("Specify Subject");
		subject.setItems("Mathematics", "Shona", "English");
		subject.setRequiredIndicatorVisible(true);

		status = new ComboBox<>("Book Status");
		status.setItems("Good", "Bad");
		status.setRequiredIndicatorVisible(true);

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.addStyleName("submit_books_details_button");
		submit.addClickListener((e) -> {
			String schoolID = (String) getSession().getAttribute(UserSessionAttributes.SCHOOL_EMIS_NUMBER);
			String titlee = title.getValue();
			String subjectt = (String) subject.getValue();
			String iSBNn = iSBN.getValue();
			String statuss = (String) status.getValue();
			String quantityy = quantity.getValue();

			if (!(titlee.equals("") || subjectt.equals("") || iSBNn.equals("") || statuss.equals("")
					|| quantityy.equals(""))) {
				InsertDataIntoBooksTable booksTable = new InsertDataIntoBooksTable(stm);
				booksTable.insertData(schoolID, titlee, subjectt, iSBNn, statuss, quantityy);

				new Notification("Success", "The Record was successfully saved", Notification.Type.HUMANIZED_MESSAGE,
						true).show(Page.getCurrent());
				title.clear();
				iSBN.clear();
				quantity.clear();
			} else {
				new Notification("Error", "Check if all fields are filled!", Notification.TYPE_ERROR_MESSAGE, true)
						.show(Page.getCurrent());
			}
		});

		FormLayout formLayout = new FormLayout();
		formLayout.setSpacing(true);
		formLayout.setCaption("Enter Book Record");
		formLayout.addComponents(subject, title, iSBN, quantity, status, submit);

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
