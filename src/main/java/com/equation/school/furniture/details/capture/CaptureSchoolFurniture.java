package com.equation.school.furniture.details.capture;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.user.session.attributes.UserSessionAttributes;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
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
@SuppressWarnings({ "serial", "deprecation" })
public class CaptureSchoolFurniture extends CustomComponent implements View {
	ResultSet rs;
	Statement stm;
	ComboBox <String>type, status;
	TextField quantity;
	TabSheet tabs;

	public CaptureSchoolFurniture(ResultSet rs, Statement stm, TabSheet tabs) {
		this.rs = rs;
		this.stm = stm;
		this.tabs = tabs;

		type = new ComboBox<>("Furniture Type");
		type.setItems("Chairs", "Desks", "Tables", "Portable White Boards");
		type.setRequiredIndicatorVisible(true);

		status = new ComboBox<>("Status");
		status.setItems("Good", "Broken");

		quantity = new TextField("Quantity");
		quantity.setRequiredIndicatorVisible(true);
		//quantity.addValidator(new IntegerValidator("Correct this input!"));

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.addStyleName("furniture_details_submit_button");

		submit.addClickListener((e) -> {
			int schoolID = (int) getSession().getAttribute(UserSessionAttributes.SCHOOL_EMIS_NUMBER);
			String typee = (String) type.getValue();
			int quantityy = Integer.parseInt(quantity.getValue());
			String statuss = (String) status.getValue();

			if (!(typee.equals("") || statuss.equals(""))) {
				if (quantityy > 0) {
					InsertDataIntoSchoolFurnitureTable dataIntoSchoolFurnitureTable = new InsertDataIntoSchoolFurnitureTable(
							stm);
					dataIntoSchoolFurnitureTable.insertData(schoolID, typee, quantityy, statuss);

					quantity.clear();
					status.clear();
					type.clear();

					new Notification("Success", "The Record has been successfully saved!",
							Notification.TYPE_HUMANIZED_MESSAGE, true).show(Page.getCurrent());
				} else {
					new Notification("Error", "Quantity zero cannot be submitted", Notification.TYPE_ERROR_MESSAGE,
							true).show(Page.getCurrent());
				}

			} else {
				new Notification("Error", "A Blank field has been detected, and <br/>the record cannot be submitted!",
						Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
			}
		});
		FormLayout formLayout = new FormLayout();
		formLayout.setSpacing(true);
		formLayout.addComponents(type, quantity, status, submit);

		VerticalLayout verticalLayout = new VerticalLayout();

		HorizontalLayout gridLayout = new HorizontalLayout();
		gridLayout.setSpacing(true);
		gridLayout.addComponents(formLayout);

		verticalLayout.addComponent(gridLayout);
		verticalLayout.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);

		this.setCompositionRoot(verticalLayout);

	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Add Furniture")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Add Furniture", FontAwesome.BUS);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
