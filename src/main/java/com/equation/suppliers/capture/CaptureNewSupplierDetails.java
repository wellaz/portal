package com.equation.suppliers.capture;

import java.sql.ResultSet;
import java.sql.Statement;

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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class CaptureNewSupplierDetails extends CustomComponent implements View {

	TextField postalAddress, physicalAddress, directLine, cell, email, companyName;
	ComboBox <String>category;

	Statement stm;
	ResultSet rs;

	@SuppressWarnings("deprecation")
	public CaptureNewSupplierDetails(Statement stm, ResultSet rs) {
		this.rs = rs;
		this.stm = stm;

		postalAddress = new TextField("Postal Address");
		postalAddress.setRequiredIndicatorVisible(true);

		physicalAddress = new TextField("Physical Address");
		physicalAddress.setRequiredIndicatorVisible(true);

		directLine = new TextField("Direct Line");
		directLine.setRequiredIndicatorVisible(true);

		cell = new TextField("Cell Number");
		cell.setRequiredIndicatorVisible(true);

		email = new TextField("Email Address");
		email.setRequiredIndicatorVisible(true);
		//email.addValidator(new EmailValidator("Correct the email address!"));

		companyName = new TextField("Company Name");
		companyName.setRequiredIndicatorVisible(true);

		category = new ComboBox<>("Supplier Category");
		category.setRequiredIndicatorVisible(true);
		category.setItems("Textile", "Carpentry");

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.addStyleName("suplier_submit_button");
		submit.addClickListener((e) -> {
			String companyNamee = companyName.getValue();
			String emaill = email.getValue();
			String celll = cell.getValue();
			String directLinee = directLine.getValue();
			String physicalAddresss = physicalAddress.getValue();
			String postalAddresss = postalAddress.getValue();
			String categoryy = (String) category.getValue();

			if (!(companyNamee.equals("") || emaill.equals("") || celll.equals("") || directLinee.equals("")
					|| physicalAddresss.equals("") || postalAddresss.equals("") || categoryy.equals(""))) {
				InsertDataIntoSuppliersTable dataIntoSuppliersTable = new InsertDataIntoSuppliersTable(stm);
				dataIntoSuppliersTable.insertData(companyNamee, emaill, celll, directLinee, physicalAddresss,
						postalAddresss, categoryy);
				postalAddress.clear();
				physicalAddress.clear();
				directLine.clear();
				cell.clear();
				email.clear();
				companyName.clear();
				category.clear();

				new Notification("Success", "Company record successfylly saved!", Notification.TYPE_HUMANIZED_MESSAGE,
						true).show(Page.getCurrent());
			} else {
				new Notification("Error", "A blank field has been detected and the<br/>record cannot be submitted!",
						Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
			}
		});

		FormLayout formLayout = new FormLayout(companyName, category, postalAddress, physicalAddress, directLine, cell,
				email, submit);
		formLayout.setCaption("Enter New Supplier Details");
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
