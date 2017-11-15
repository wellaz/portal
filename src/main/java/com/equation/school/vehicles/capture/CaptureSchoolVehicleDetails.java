package com.equation.school.vehicles.capture;

import java.sql.Statement;

import com.equation.user.session.attributes.UserSessionAttributes;
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
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class CaptureSchoolVehicleDetails extends CustomComponent implements View {
	TextField regNumber, type, model, status;
	Statement stm;
	TabSheet tabs;

	@SuppressWarnings("deprecation")
	public CaptureSchoolVehicleDetails(Statement stm, TabSheet tabs) {
		this.stm = stm;
		this.tabs = tabs;

		type = new TextField("Vehicle Type");

		model = new TextField("Vehicle Model");

		status = new TextField("Current Status");

		regNumber = new TextField("Vehicle Registration Number");

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);

		FormLayout formLayout = new FormLayout();
		formLayout.setSpacing(true);
		formLayout.addComponents(regNumber, type, model, status, submit);

		VerticalLayout verticalLayout = new VerticalLayout();

		HorizontalLayout gridLayout = new HorizontalLayout();
		gridLayout.setSpacing(true);
		gridLayout.addComponent(formLayout);

		verticalLayout.addComponent(gridLayout);
		verticalLayout.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);
		submit.addClickListener((e) -> {
			int schoolID = (int) getSession().getAttribute(UserSessionAttributes.SCHOOL_EMIS_NUMBER);
			String typee = type.getValue();
			String modell = model.getValue();
			String regNumberr = regNumber.getValue();
			String statuss = status.getValue();

			InsertDataIntoSchoolVehicles dataIntoSchoolVehicles = new InsertDataIntoSchoolVehicles(stm);
			dataIntoSchoolVehicles.insertData(schoolID, typee, modell, regNumberr, statuss);
			regNumber.clear();
			type.clear();
			model.clear();
			status.clear();
			new Notification("Success", "Vehicle record Succesfully saved", Notification.TYPE_HUMANIZED_MESSAGE, true)
					.show(Page.getCurrent());
		});

		this.setCompositionRoot(verticalLayout);
	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Add Vehicle")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Add Vehicle", VaadinIcons.CAR);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
