package com.equation.school.assets.details.capture;

import java.sql.Statement;

import com.equation.user.session.attributes.UserSessionAttributes;
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
@SuppressWarnings("serial")
public class CaptureSchoolAsset extends CustomComponent implements View {

	Statement stm;
	TextField quantity;
	ComboBox<String> type, status;
	TabSheet tabs;

	@SuppressWarnings("deprecation")
	public CaptureSchoolAsset(Statement stm, TabSheet tabs) {
		this.stm = stm;
		this.tabs = tabs;

		quantity = new TextField("Quantity");
		quantity.setRequiredIndicatorVisible(true);

		status = new ComboBox<>("Asset status");
		status.setRequiredIndicatorVisible(true);

		type = new ComboBox<>("Specify Asset Type");
		type.setRequiredIndicatorVisible(true);

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.addStyleName("asset_capture_submit_button");
		submit.addClickListener((e) -> {
			String schoolID = (String) getSession().getAttribute(UserSessionAttributes.SCHOOL_EMIS_NUMBER);
			String typee = (String) type.getValue();
			String quantityy = quantity.getValue();
			String statuss = (String) status.getValue();

			if (!(quantityy.equals("") || typee.equals("") || statuss.equals(""))) {
				InsertDataIntoAssestsTable assestsTable = new InsertDataIntoAssestsTable(stm);
				assestsTable.insertData(schoolID, typee, quantityy, statuss);

				new Notification("Success", "Asset Record Successfully saved", Notification.Type.HUMANIZED_MESSAGE,
						true).show(Page.getCurrent());

				quantity.clear();
				type.clear();
				status.clear();
			} else {
				new Notification("Error", "Blank fields cannot be submitted", Notification.TYPE_ERROR_MESSAGE, true)
						.show(Page.getCurrent());
			}
		});

		FormLayout formLayout = new FormLayout();
		formLayout.setSpacing(true);
		formLayout.setCaption("Enter New Asset Record");
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
			if (tabs.getTab(x).getCaption().equals("Other Assets")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Other Assets", VaadinIcons.BOOK);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
