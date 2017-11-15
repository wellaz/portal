package com.equation.student.parent.details.capture;

import java.sql.Statement;

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
public class CaptureStudentParentDetails extends CustomComponent implements View {

	TextField pFirstName, pSurname, pNationalID, pCell, pEmail, pAdress, pOther;
	Statement stm;
	TabSheet tabs;

	@SuppressWarnings("deprecation")
	public CaptureStudentParentDetails(Statement stm, TabSheet tabs) {
		this.stm = stm;
		this.tabs = tabs;
		pFirstName = new TextField();
		pFirstName.setRequiredIndicatorVisible(true);
		pSurname = new TextField();
		pSurname.setRequiredIndicatorVisible(true);
		pNationalID = new TextField();
		pNationalID.setRequiredIndicatorVisible(true);
		pCell = new TextField();
		pCell.setRequiredIndicatorVisible(true);
		pEmail = new TextField();
		//pEmail.addValidator(new EmailValidator("Correct the emain address"));
		pAdress = new TextField();
		pAdress.setRequiredIndicatorVisible(true);
		pOther = new TextField();

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.addClickListener((e) -> {
			String pFirstNamee = pFirstName.getValue();
			String pSurnamee = pSurname.getValue();
			String pNationalIDd = pNationalID.getValue();
			String pCelll = pCell.getValue();
			String pEmaill = pEmail.getValue();
			String pAdresss = pAdress.getValue();
			String pOtherr = pOther.getValue();

			if (pFirstNamee.equals("") || pSurnamee.equals("") || pNationalIDd.equals("") || pCelll.equals("")
					|| pEmaill.equals("") || pAdresss.equals("") || pOtherr.equals("")) {

				// InsertDataIntoParentsTable dataIntoParentsTable = new
				// InsertDataIntoParentsTable(stm);
				// dataIntoParentsTable.insertData(pFirstNamee, pSurnamee,
				// pNationalIDd, pCelll, pEmaill, pAdresss,
				// pOtherr);

				new Notification("Success", "The Parents Record Successfully saved",
						Notification.TYPE_HUMANIZED_MESSAGE, true).show(Page.getCurrent());

				pFirstName.clear();
				pSurname.clear();
				pNationalID.clear();
				pCell.clear();
				pEmail.clear();
				pAdress.clear();
				pOther.clear();
				pFirstName.focus();
			} else {
				new Notification("Error", "Ensure that you have filled all the fields before submission",
						Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
			}
		});

		FormLayout formLayout = new FormLayout();
		formLayout.setSpacing(true);
		formLayout.addComponents(pFirstName, pSurname, pNationalID, pCell, pEmail, pAdress, pOther, submit);

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
			if (tabs.getTab(x).equals("Students Registration")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Students Registration", VaadinIcons.USERS);
			tabs.setSelectedTab(count);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
