package com.equation.teacher.onleave.capture;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import com.equation.teacher.details.capture.TeachersECNumbers;
import com.equation.teacher.leave.onleave.leavetypes.TeacherLeaveTypes;
import com.equation.user.session.attributes.UserSessionAttributes;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class CaptureTeacherOnLeave extends CustomComponent implements View {
	DateField fromDate, toDate;
	ComboBox<String> ecNumber, leaveType, replacement;
	Statement stm;
	ResultSet rs;
	TabSheet tabs;

	@SuppressWarnings("deprecation")
	public CaptureTeacherOnLeave(Statement stm, ResultSet rs, TabSheet tabs) {
		this.stm = stm;
		this.rs = rs;
		this.tabs = tabs;

		ArrayList<String> ecnumbers = new TeachersECNumbers(rs, stm).ecNumbers();

		ecNumber = new ComboBox<>();
		ecNumber.setRequiredIndicatorVisible(true);
		ecNumber.setItems(ecnumbers);

		leaveType = new ComboBox<>();
		leaveType.setRequiredIndicatorVisible(true);
		leaveType.setItems(TeacherLeaveTypes.leaveTypes());

		replacement = new ComboBox<>();
		replacement.setItems(ecnumbers);

		fromDate = new DateField();
		fromDate.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		fromDate.setDateFormat("yyyy-MM-dd");
		fromDate.setRequiredIndicatorVisible(true);

		toDate = new DateField();
		toDate.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		toDate.setDateFormat("yyyy-MM-dd");
		toDate.setRequiredIndicatorVisible(true);

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.addStyleName("teachers_on_leave_submit_button");
		submit.addClickListener((e) -> {
			int schoolID = (int) getSession().getAttribute(UserSessionAttributes.SCHOOL_EMIS_NUMBER);
			String ecNumberr = (String) ecNumber.getValue();
			String leaveTypee = (String) leaveType.getValue();
			String fromDatee = String.format("%1$tY-%1$tm-%1$td", fromDate.getValue());
			String toDatee = String.format("%1$tY-%1$tm-%1$td", toDate.getValue());
			String replacementt = (String) replacement.getValue();

			if (ecNumberr.equals("") || leaveTypee.equals("") || replacementt.equals("")) {

				InsertDataIntoTeacherOnLeaveTable dataIntoTeacherOnLeaveTable = new InsertDataIntoTeacherOnLeaveTable(
						stm);
				dataIntoTeacherOnLeaveTable.insertData(schoolID, ecNumberr, leaveTypee, fromDatee, toDatee,
						replacementt);
				new Notification("Success", "The record was successfully saved", Notification.TYPE_HUMANIZED_MESSAGE,
						true).show(Page.getCurrent());
				ecNumber.clear();
				leaveType.clear();
				replacement.clear();
			} else {
				new Notification("Error", "All fields marked important should be checked",
						Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
			}
		});

		FormLayout formLayout = new FormLayout();
		formLayout.setSpacing(true);
		formLayout.setCaption("Submit Teacher on Leave Details");
		formLayout.addComponents(ecNumber, leaveType, replacement, fromDate, toDate, submit);

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
			if (tabs.getTab(x).getCaption().equals("Leave Details")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Leave Details", VaadinIcons.USER);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
