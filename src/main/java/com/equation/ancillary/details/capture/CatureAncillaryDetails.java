package com.equation.ancillary.details.capture;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.Date;

import com.equation.administrator.users.create.InsertDataIntoUsersTable;
import com.equation.user.session.attributes.UserSessionAttributes;
import com.equation.utils.countries.AllCountries;
import com.equation.utils.date.DateUtility;
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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class CatureAncillaryDetails extends CustomComponent implements View {
	TextField firstName, surname, nationalID, qualification, salary;
	ComboBox<String> jobDescription;
	ComboBox<String> maritalStatus, gender, nationality, employmentType;
	DateField dob, dojs;
	Statement stm;
	ResultSet rs;
	TextField phone, cell, email, physicalAddress, postalAddress, other;
	private Button submit;
	TabSheet tabs;

	@SuppressWarnings("deprecation")
	public CatureAncillaryDetails(Statement stm, TabSheet tabs, ResultSet rs) {
		this.stm = stm;
		this.rs = rs;
		this.tabs = tabs;

		firstName = new TextField("First Name");
		surname = new TextField("Surname");
		nationalID = new TextField("National ID");
		qualification = new TextField("Qualification(s)");

		gender = new ComboBox<>("Gender");
		gender.setItems("Male", "Female");

		maritalStatus = new ComboBox<>("Marital Status");
		maritalStatus.setItems("Single", "Married");

		nationality = new ComboBox<>("Nationality");
		nationality.setItems(AllCountries.allCountriesCollection());

		dojs = new DateField("Date of Joining Service");
		dojs.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		dojs.setDateFormat("yyyy-MM-dd");

		dob = new DateField("Date of Birth");
		dob.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		dob.setDateFormat("yyyy-MM-dd");

		submit = new Button("Submit");

		FormLayout formLayout3 = createContactsForm();
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);

		FormLayout formLayout = new FormLayout();
		formLayout.setSpacing(true);
		formLayout.setCaption("Personal Details");
		formLayout.addComponents(firstName, surname, dob, gender, maritalStatus, nationalID, nationality,
				qualification);

		VerticalLayout verticalLayout = new VerticalLayout();

		HorizontalLayout gridLayout = new HorizontalLayout();
		gridLayout.setSpacing(true);
		gridLayout.addComponents(formLayout, createJobDescriptionForm(), formLayout3);

		verticalLayout.addComponent(gridLayout);
		verticalLayout.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);

		submit.addClickListener((e) -> {
			String firstNamee = firstName.getValue();
			String surnamee = surname.getValue();
			String genderr = (String) gender.getValue();
			String dobb = String.format("%1$tY-%1$tm-%1$td", dob.getValue());
			String nationalIDd = nationalID.getValue();
			String nationalityy = (String) nationality.getValue();
			String dojss = String.format("%1$tY-%1$tm-%1$td", dojs.getValue());
			String qualificationn = qualification.getValue();
			String maritalStatuss = (String) maritalStatus.getValue();

			String emis = (String) getSession().getAttribute(UserSessionAttributes.SCHOOL_EMIS_NUMBER);
			String workerID = new NextAutoIncrementValue(stm, rs).nextValue(emis);

			String jobDescriptionj = (String) jobDescription.getValue();
			String salaryy = salary.getValue();
			String employmentTypee = (String) employmentType.getValue();

			String phonee = phone.getValue();
			String celll = cell.getValue();
			String emaill = email.getValue();
			String physicalAddresss = physicalAddress.getValue();
			String postalAddresss = postalAddress.getValue();
			String otherr = other.getValue();

			if (!(firstNamee.equals("") || surnamee.equals("") || genderr.equals("") || dobb.equals("")
					|| nationalIDd.equals("") || nationalityy.equals("") || dojss.equals("")
					|| qualificationn.equals("") || maritalStatuss.equals("") || celll.equals("")
					|| physicalAddresss.equals("") || postalAddresss.equals("") || salaryy.equals(""))) {
				InsertDataIntoAncillaryTable ancillaryTable = new InsertDataIntoAncillaryTable(stm);
				ancillaryTable.insertData(firstNamee, surnamee, genderr, dobb, nationalIDd, nationalityy, dojss,
						qualificationn, maritalStatuss);
				double salarrry = Double.parseDouble(salaryy);
				InsertDataIntoAnscillaryEstablishment anscillaryEstablishment = new InsertDataIntoAnscillaryEstablishment(
						stm);
				anscillaryEstablishment.insertData(workerID, jobDescriptionj, salarrry, employmentTypee);

				InsertDataIntoUsersTable dataIntoUsersTable = new InsertDataIntoUsersTable(stm);
				dataIntoUsersTable.insertData(workerID, jobDescriptionj, firstNamee.toLowerCase(),
						workerID.toLowerCase(), new DateUtility().timeStamp());

				InsertDataIntoAnscillaryContactDetails anscillaryContactDetails = new InsertDataIntoAnscillaryContactDetails(
						stm);
				anscillaryContactDetails.insertData(workerID, phonee, celll, emaill, physicalAddresss, postalAddresss,
						otherr);

				new Notification("Success", "The record as successfully saved!", Notification.TYPE_HUMANIZED_MESSAGE,
						true).show(Page.getCurrent());

				firstName.clear();
				surname.clear();
				nationalID.clear();
				qualification.clear();

				phone.clear();
				cell.clear();
				email.clear();
				physicalAddress.clear();
				postalAddress.clear();
				other.clear();

			} else {
				new Notification("Error", "Ensure that at all fields are filled before submission!",
						Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
			}
		});

		this.setCompositionRoot(verticalLayout);

	}

	private FormLayout createJobDescriptionForm() {
		FormLayout formLayout = new FormLayout();
		formLayout.setCaption("Anscillary Job Description");
		formLayout.setSpacing(true);

		jobDescription = new ComboBox<>("Job Description");
		jobDescription.setRequiredIndicatorVisible(true);
		jobDescription.setItems("Bursar", "Caretaker", "Accountant", "Secretary", "Driver", "Security", "WorkShop",
				"kitchen", "Boarding Master / Mistress", "Health Nurse", "Clerk");
		salary = new TextField("Monthly Salary");
		salary.setRequiredIndicatorVisible(true);

		employmentType = new ComboBox<>("Employment Type");
		employmentType.setItems("Permanant", "Contract");
		formLayout.addComponents(jobDescription, salary, employmentType, dojs);

		return formLayout;
	}

	public FormLayout createContactsForm() {
		phone = new TextField("Direct Number");
		cell = new TextField("Mobile Number");
		cell.setRequiredIndicatorVisible(true);
		email = new TextField("Email Address");
		physicalAddress = new TextField("Physical Address");
		postalAddress = new TextField("Postal Address");
		other = new TextField("Other Contact Details");

		FormLayout formLayout = new FormLayout(phone, cell, email, physicalAddress, postalAddress, other, submit);
		formLayout.setSpacing(true);
		formLayout.setCaption("Contact Details");
		return formLayout;
	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Ansillary")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Ancillary", VaadinIcons.USER);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}