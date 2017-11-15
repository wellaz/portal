package com.equation.school.classes.capture;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.teacher.details.capture.TeachersECNumbers;
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
public class CaptureSchoolClasses extends CustomComponent implements View {

	TextField classname;
	ComboBox<String> ecNumber;
	ResultSet rs;
	Statement stm;
	TabSheet tabs;

	public CaptureSchoolClasses(ResultSet rs, Statement stm, TabSheet tabs) {
		this.rs = rs;
		this.stm = stm;
		this.tabs = tabs;

		classname = new TextField("Enter Class Name");
		classname.setRequiredIndicatorVisible(true);
		TextField room = new TextField("Room");
		room.setRequiredIndicatorVisible(true);

		ecNumber = new ComboBox<>("Select Class Teacher");
		ecNumber.setRequiredIndicatorVisible(true);
		ecNumber.setEmptySelectionAllowed(false);
		ecNumber.setItems(new TeachersECNumbers(rs, stm).teacherNames());

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.addStyleName("school_classes_submit_button");
		submit.addClickListener((e) -> {
			String classnamee = classname.getValue();
			String myroom = room.getValue();
			String schoolIDd = (String) getSession().getAttribute(UserSessionAttributes.SCHOOL_EMIS_NUMBER);
			String ecNumberr = (ecNumber.getValue() != null && !ecNumber.getValue().isEmpty())
					? new TeachersECNumbers(rs, stm).getECNumberForThis(ecNumber.getValue()) : "";

			if (!(classnamee.equals("") || ecNumberr.equals("") || myroom.equals(""))) {

				InsertDataIntoClassesTable dataIntoClassesTable = new InsertDataIntoClassesTable(stm);
				dataIntoClassesTable.insertData(classnamee, schoolIDd, ecNumberr, myroom);

				new Notification("Success", "Class Name Saved Succesfully!", Notification.Type.HUMANIZED_MESSAGE, true)
						.show(Page.getCurrent());
				classname.clear();
				ecNumber.clear();
				classname.focus();
			} else {
				new Notification("<h1>A blank field has been detected. <br/>The record cannot be submitted!<h1/>", "",
						Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
				classname.focus();
			}
		});

		FormLayout formLayout = new FormLayout();
		formLayout.setSpacing(true);
		formLayout.addComponents(classname, room, ecNumber, submit);

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

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Class Registration")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Class Registration", VaadinIcons.USER);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}
}
