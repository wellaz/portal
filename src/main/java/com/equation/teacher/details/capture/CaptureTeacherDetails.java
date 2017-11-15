package com.equation.teacher.details.capture;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.Date;

import com.equation.administrator.users.create.InsertDataIntoUsersTable;
import com.equation.administrator.users.create.SystemUserLevels;
import com.equation.school.secondary.subjects.bin.AllSecondarySubjectsBin;
import com.equation.teacher.alevelqualifications.InsertDataIntoTeacherAlevelQualifications;
import com.equation.teacher.categories.TeacherCategory;
import com.equation.teacher.delegation.TeacherDelegation;
import com.equation.teacher.nextofkeen.InsertDataIntoTeacherNextOfKeen;
import com.equation.teacher.olevelqualifications.InsertDataIntoTeacherOlevelQualifications;
import com.equation.teacher.professionalqualifications.InsertDataIntoTeacherProfessionalQualification;
import com.equation.teacher.status.TeacherStatus;
import com.equation.utils.colleges.AllColleges;
import com.equation.utils.countries.AllCountries;
import com.equation.utils.date.DateUtility;
import com.equation.utils.numberofsubjects.NumberOfSubjects;
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
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class CaptureTeacherDetails extends CustomComponent implements View {
	Statement stm;
	ResultSet rs;
	TextField kFirstName, kSurname, kNationalID, kCell, kEmail, kAdress, kOther, kRelationship;
	TextField phone, cell, email, contactother, physicalAddress, postalAddress;
	TextField firstName, middleName, surname, otherName, ecNumber, nationalID;
	ComboBox<String> gender, maritalStatus, mainSubjects, nationality;
	DateField dOJS, dateOfJoiningSchool, dateOfBirth;

	ComboBox<String>[] olevelSubjectsCombo, olevelSymbolsCombo, alevelSubjectsCombo, alevelSymbolsCombo;

	ComboBox<String> numberofolevelCombo, numberofalevelCombo;

	public static final String NAME = "teacher-contact-details";

	TabSheet tabs;
	private Button submit;
	private ComboBox<String> numberofprofessionsCombo;
	private TextField[] programme;
	private ComboBox<String>[] specialisation;
	private ComboBox<String>[] universityorcollege;
	private TextField[] proficiency;
	private DateField[] yearobtained;
	private TextField alevelschool;
	private DateField alevelyear;
	private TextField olevelschool;
	private DateField olevelyear;
	private GridLayout olevelgridLayout;
	private GridLayout alevelgridLayout;
	private GridLayout profgridLayout;
	private ComboBox<String> teacherCategories;
	private ComboBox<String> teacherDelegation;

	@SuppressWarnings("deprecation")
	public CaptureTeacherDetails(Statement stm, ResultSet rs, TabSheet tabs) {
		this.rs = rs;
		this.stm = stm;
		this.tabs = tabs;
		Panel panel = new Panel();
		panel.addStyleName("teacher_panel");

		firstName = new TextField("First Name");
		// firstName.addValidator(new NullValidator("First Name Must be given",
		// false));
		firstName.setRequiredIndicatorVisible(true);

		middleName = new TextField("Middle Name");
		middleName.setDescription("Ignore the Input area if inapplicable");

		otherName = new TextField("Other Name(s)");
		otherName.setDescription("Ignore the Input area if inapplicable");

		dateOfJoiningSchool = new DateField("date Of Joining School");
		dateOfJoiningSchool.setRequiredIndicatorVisible(true);
		dateOfJoiningSchool.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		dateOfJoiningSchool.setDateFormat("yyyy-MM-dd");

		surname = new TextField("Surname");
		surname.setRequiredIndicatorVisible(true);
		// surname.addValidator(new NullValidator("Last Name Must be given",
		// false));
		ecNumber = new TextField("EC Number");
		ecNumber.setRequiredIndicatorVisible(true);
		// ecNumber.addValidator(new NullValidator("Teacher's EC Number Must be
		// given", false));
		nationalID = new TextField("National Identity Number");
		nationalID.setRequiredIndicatorVisible(true);
		// nationalID.addValidator(new NullValidator("National ID Must be
		// given", false));

		mainSubjects = new ComboBox<>("Main Subjects");
		mainSubjects.setItems(AllSecondarySubjectsBin.allSubjectsArray());
		mainSubjects.setRequiredIndicatorVisible(true);
		// mainSubjects.addValidator(new NullValidator("Must be given", false));

		gender = new ComboBox<>("Gender");
		gender.setItems("Male", "Female");
		gender.setRequiredIndicatorVisible(true);
		nationality = new ComboBox<>("Nationality");
		nationality.setRequiredIndicatorVisible(true);
		nationality.setItems(AllCountries.allCountriesCollection());

		maritalStatus = new ComboBox<>("Marital Status");
		maritalStatus.setItems("Single", "Married", "Divorced", "Widowed");
		maritalStatus.setRequiredIndicatorVisible(true);
		dateOfBirth = new DateField("Date Of Birth");
		dateOfBirth.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		dateOfBirth.setDateFormat("yyyy-MM-dd");
		dateOfBirth.setRequiredIndicatorVisible(true);

		dOJS = new DateField("Date Of Joining Service");
		dOJS.setRequiredIndicatorVisible(true);
		dOJS.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		dOJS.setDateFormat("yyyy-MM-dd");

		teacherCategories = new ComboBox<>("Teacher Category");
		teacherCategories.setRequiredIndicatorVisible(true);
		teacherCategories.setEmptySelectionAllowed(false);
		teacherCategories.setItems(TeacherCategory.allTeacherCategories());

		teacherDelegation = new ComboBox<>("Position");
		teacherDelegation.setEmptySelectionAllowed(false);
		teacherDelegation.setRequiredIndicatorVisible(true);
		teacherDelegation.setItems(TeacherDelegation.allTeacherDeleation());

		submit = new Button("Submit");

		FormLayout formLayout3 = createContactsLayout();
		VerticalLayout professionalQualificationsLayout = createProfessionLayout();
		FormLayout formLayout4 = createNextOfKeenLayout();
		VerticalLayout olevelVerticalLayout = teacherOlevelPasses();
		VerticalLayout alevelVerticalLayout = teacherAlevelPasses();

		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.addStyleName("teacher_details_submit_button");
		submit.setWidth("30%");
		submit.addClickListener((e) -> {
			String firstname = firstName.getValue();
			String surnamee = surname.getValue();
			String genderr = gender.getValue();
			// String dateOfBirthh =
			// (DateFormat.getDateInstance(DateFormat.SHORT)).format(dateOfBirth.getValue());
			String dateOfBirthh = String.format("%1$tY-%1$tm-%1$td", dateOfBirth.getValue());
			String ecNumberr = ecNumber.getValue();
			String nationalIDd = nationalID.getValue();
			String nationalityy = nationality.getValue();
			// String dOJSs =
			// (DateFormat.getDateInstance(DateFormat.SHORT)).format(dOJS.getValue());
			String dOJSs = String.format("%1$tY-%1$tm-%1$td", dOJS.getValue());
			String maritalStatuss = maritalStatus.getValue();
			String mainSubjectss = mainSubjects.getValue();

			String phonee = phone.getValue();
			String celll = cell.getValue();
			String emaill = email.getValue();
			String contactotherr = contactother.getValue();
			String physicalAddresss = physicalAddress.getValue();
			String postalAddresss = postalAddress.getValue();
			String middleNamee = middleName.getValue();
			String otherNamee = otherName.getValue();
			String dateOfJoiningSchooll = String.format("%1$tY-%1$tm-%1$td", dateOfJoiningSchool.getValue());

			String olevelnumber = numberofolevelCombo.getValue();

			String alevelnumber = numberofalevelCombo.getValue();

			String profnumber = numberofprofessionsCombo.getValue();

			String kFirstNamee = kFirstName.getValue();
			String kSurnamee = kSurname.getValue();
			String kNationalIDd = kNationalID.getValue();
			String kCelll = kCell.getValue();
			String kEmaill = kEmail.getValue();
			String kAdresss = kAdress.getValue();
			String kOtherr = kOther.getValue();
			String kRelationshipp = kRelationship.getValue();

			String category = (String) teacherCategories.getValue().toString();
			String delegation = (String) teacherDelegation.getValue().toString();

			InsertDataIntoTeachersTable dataIntoTeachersTable = new InsertDataIntoTeachersTable(stm);
			if (!(firstname.equals("") || surnamee.equals("") || genderr.equals("") || dateOfBirthh.equals("")
					|| ecNumberr.equals("") || nationalIDd.equals("") || nationalityy.equals("") || dOJSs.equals("")
					|| maritalStatuss.equals("") || mainSubjectss.equals("") || celll.equals("")
					|| physicalAddresss.equals("") || postalAddresss.equals("") || profnumber.equals("")
					|| alevelnumber.equals("") || olevelnumber.equals("") || category.equals("")
					|| delegation.equals(""))) {

				// if (!olevelnumber.equals("")) {
				int number1 = Integer.parseInt(olevelnumber);
				String school1 = olevelschool.getValue();
				String olevelyearr = String.format("%1$tY-%1$tm-%1$td", olevelyear.getValue());
				InsertDataIntoTeacherOlevelQualifications dataIntoTeacherOlevelQualifications = new InsertDataIntoTeacherOlevelQualifications(
						stm);
				int x = 0;
				for (int i = 0; i < number1; i++) {
					String subject = (String) olevelSubjectsCombo[i].getValue();
					String symbol = (String) olevelSymbolsCombo[i].getValue();
					if (!(symbol.equals("") || subject.equals(""))) {
						x += i;
					}
				}
				if (x == (number1 - 1)) {
					for (int a = 0; a < number1; a++) {
						String subject = (String) olevelSubjectsCombo[a].getValue();
						String symbol = (String) olevelSymbolsCombo[a].getValue();
						dataIntoTeacherOlevelQualifications.insertData(ecNumberr, subject, symbol, school1,
								olevelyearr);
					}
				}
				// }

				// if (!alevelnumber.equals("")) {
				int number2 = Integer.parseInt(alevelnumber);
				String school2 = alevelschool.getValue();
				String year2 = String.format("%1$tY-%1$tm-%1$td", alevelyear.getValue());
				InsertDataIntoTeacherAlevelQualifications alevelQualifications = new InsertDataIntoTeacherAlevelQualifications(
						stm);
				for (int i = 0; i < number2; i++) {
					String subject = (String) alevelSubjectsCombo[i].getValue();
					String symbol = (String) alevelSymbolsCombo[i].getValue();
					alevelQualifications.insertData(ecNumberr, subject, symbol, school2, year2);
				}
				// }
				// if (!profnumber.equals("")) {
				int number3 = Integer.parseInt(profnumber);
				InsertDataIntoTeacherProfessionalQualification dataIntoTeacherProfessionalQualification = new InsertDataIntoTeacherProfessionalQualification(
						stm);
				for (int i = 0; i < number3; i++) {
					String programmee = programme[i].getValue();
					String specialisationn = (String) specialisation[i].getValue();
					String universityorcollegee = (String) universityorcollege[i].getValue();
					String profficiencyy = proficiency[i].getValue();
					String year3 = String.format("%1$tY-%1$tm-%1$td", yearobtained[i].getValue());
					dataIntoTeacherProfessionalQualification.insertData(ecNumberr, programmee, specialisationn,
							universityorcollegee, profficiencyy, year3);
				}
				// }

				TeachersECNumbers teachersECNumbers = new TeachersECNumbers(rs, stm);
				if (!teachersECNumbers.isFoundECNumber(ecNumberr)) {
					String status = TeacherStatus.ACTIVE;
					dataIntoTeachersTable.insertData(firstname, surnamee, genderr, dateOfBirthh,
							ecNumberr.toUpperCase(), nationalIDd, nationalityy, dOJSs, maritalStatuss, mainSubjectss,
							middleNamee, otherNamee, dateOfJoiningSchooll, category, delegation, status);
					String name = firstname + " " + surnamee;

					InsertDataIntoUsersTable dataIntoUsersTable = new InsertDataIntoUsersTable(stm);
					dataIntoUsersTable.insertData(ecNumberr.toUpperCase(), SystemUserLevels.getTeacher(),
							ecNumberr.toUpperCase(), ecNumberr.toUpperCase(), new DateUtility().timeStamp());

					InsertDataIntoTeacherContactDetails dataIntoTeacherContactDetails = new InsertDataIntoTeacherContactDetails(
							stm);

					dataIntoTeacherContactDetails.insertData(ecNumberr, phonee, celll, emaill, contactotherr,
							physicalAddresss, postalAddresss);

					new InsertDataIntoTeacherNextOfKeen(stm).insertData(ecNumberr, kFirstNamee, kSurnamee, kNationalIDd,
							kCelll, kEmaill, kAdresss, kOtherr, kRelationshipp);

					new Notification("Success",
							"Record Successfully saved.<br/>Please, proceed to enter contact details for "
									+ name.toUpperCase(),
							Notification.TYPE_HUMANIZED_MESSAGE, true).show(Page.getCurrent());

					firstName.clear();
					surname.clear();
					ecNumber.clear();
					nationalID.clear();
					phone.clear();
					cell.clear();
					email.clear();
					contactother.clear();
					physicalAddress.clear();
					postalAddress.clear();
					mainSubjects.clear();
					gender.clear();
					nationality.clear();
					maritalStatus.clear();

					ecNumber.clear();
					kFirstName.clear();
					kSurname.clear();
					kNationalID.clear();
					kCell.clear();
					kEmail.clear();
					kAdress.clear();
					kOther.clear();
					kRelationship.clear();
					teacherDelegation.clear();
					teacherCategories.clear();

					for (int i = 0; i < number3; i++) {
						programme[i].clear();
						specialisation[i].clear();
						universityorcollege[i].clear();
						proficiency[i].clear();
						yearobtained[i].clear();

					}
					/*
					 * private TextField[] programme; private TextField[]
					 * specialisation; private ComboBox[] universityorcollege;
					 * private TextField[] proficiency; private DateField[]
					 * yearobtained;
					 */

					for (int i = 0; i < number2; i++) {

					}
					// private TextField alevelschool;
					// private DateField alevelyear;

					// private TextField olevelschool;
					// private DateField olevelyear;*/

				} else {
					new Notification("Warning",
							"EC Number " + ecNumberr
									+ " already exist in the system.<br/>You may need to check the EC Number number that you have entered!",
							Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
					ecNumber.focus();
				}

			} else {
				// JavaScript.getCurrent().execute("alert('Error')");
				new Notification("Warning",
						"A Blank Field has been detected.<br/>Please, make sure that you have entered all the required details!",
						Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
			}
		});

		FormLayout formLayout1 = new FormLayout(firstName, middleName, surname, otherName, gender, maritalStatus,
				dateOfBirth, teacherCategories, teacherDelegation);
		formLayout1.setSpacing(true);
		formLayout1.setCaption(" Teachers Personal Details");
		formLayout1.setIcon(VaadinIcons.USERS);
		// formLayout1.addStyleName("formlayout1");

		FormLayout formLayout2 = new FormLayout(nationalID, nationality, ecNumber, dateOfJoiningSchool, mainSubjects,
				dOJS);
		formLayout2.setCaption("Qualifications");
		formLayout2.setSpacing(true);
		formLayout2.setIcon(VaadinIcons.BRIEFCASE);
		// formLayout2.addStyleName("formlayout2");

		TabSheet sheet = new TabSheet();
		Tab tab1 = sheet.addTab(formLayout1, "Personal Details", VaadinIcons.USER);
		tab1.setStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
		Tab tab2 = sheet.addTab(formLayout2, "Qualifications", VaadinIcons.USER);
		tab2.setStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
		Tab tab3 = sheet.addTab(formLayout3, "Contact Details", VaadinIcons.USER);
		tab3.setStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
		Tab tab4 = sheet.addTab(formLayout4, "Next Of Keen", VaadinIcons.USER);
		tab4.setStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
		Tab tab5 = sheet.addTab(olevelVerticalLayout, "O Level Details", VaadinIcons.USER);
		tab5.setStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
		Tab tab6 = sheet.addTab(alevelVerticalLayout, "A Level Details", VaadinIcons.USER);
		tab6.setStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
		Tab tab7 = sheet.addTab(professionalQualificationsLayout, "Professional Qualifications", VaadinIcons.USER);
		tab7.setStyleName(ValoTheme.TABSHEET_CENTERED_TABS);

		panel.addStyleName("panell");
		HorizontalLayout horizontalLayout1 = new HorizontalLayout(sheet);
		horizontalLayout1.setSpacing(true);
		panel.setContent(horizontalLayout1);
		VerticalLayout verticalLayout = new VerticalLayout(panel);

		verticalLayout.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

		this.setCompositionRoot(verticalLayout);
	}

	public FormLayout createContactsLayout() {

		phone = new TextField("Direct Phone Number");
		cell = new TextField("Mobile Number");
		email = new TextField("Email Address");
		contactother = new TextField("Other");
		physicalAddress = new TextField("Physical Address");
		postalAddress = new TextField("Postal Address");
		postalAddress.addFocusListener((e) -> {
			StringBuilder script = new StringBuilder();
			script.append("window.scrollBy(0, 1);").append("scrollDelay = setTimeout(pageScroll, 10);");

			JavaScript.getCurrent().execute(script.toString());

		});

		FormLayout formLayout = new FormLayout(phone, cell, email, contactother, physicalAddress, postalAddress);
		formLayout.setSpacing(true);
		formLayout.setCaption("Contact Details");
		formLayout.setIcon(VaadinIcons.PHONE);
		return formLayout;
	}

	public FormLayout createNextOfKeenLayout() {
		kFirstName = new TextField("First Name");
		kFirstName.setRequiredIndicatorVisible(true);

		kSurname = new TextField("Surname");
		kSurname.setRequiredIndicatorVisible(true);

		kNationalID = new TextField("National ID");
		kNationalID.setRequiredIndicatorVisible(true);

		kCell = new TextField("Cell Number");
		kCell.setRequiredIndicatorVisible(true);

		kEmail = new TextField("Email Address");
		kAdress = new TextField("Physical Address");
		kOther = new TextField("Other Contact Details");

		kRelationship = new TextField("Relationship");
		kRelationship.setRequiredIndicatorVisible(true);

		FormLayout formLayout = new FormLayout(kFirstName, kSurname, kNationalID, kCell, kEmail, kAdress, kOther,
				kRelationship);
		formLayout.setSpacing(true);
		formLayout.setCaption("Next Of Keen Details");
		formLayout.setIcon(VaadinIcons.FACEBOOK);
		return formLayout;
	}

	@SuppressWarnings({ "unchecked" })
	public VerticalLayout teacherOlevelPasses() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setCaption("O'Level Subjects");
		// layout.addStyleName("teacherolevelpasses");
		layout.setIcon(VaadinIcons.BOOK);

		numberofolevelCombo = new ComboBox<>("Number of O'Levels");
		numberofolevelCombo.setEmptySelectionAllowed(false);
		numberofolevelCombo.setItems(NumberOfSubjects.numbersArray());
		HorizontalLayout horizontalLayout = new HorizontalLayout(numberofolevelCombo);
		horizontalLayout.setSpacing(true);

		olevelgridLayout = new GridLayout();
		olevelgridLayout.setSpacing(true);
		olevelgridLayout.setColumns(3);

		numberofolevelCombo.addValueChangeListener((e) -> {
			String numberr = (String) e.getValue();
			if (!numberr.equals("")) {
				olevelgridLayout.removeAllComponents();
				int number = Integer.parseInt(numberr);
				if (number > 0) {
					olevelSubjectsCombo = new ComboBox[number];
					olevelSymbolsCombo = new ComboBox[number];
					Label[] alevelsubjectslbl = new Label[number];
					olevelgridLayout.setRows(number + 1);
					for (int i = 0; i < number; i++) {
						olevelSubjectsCombo[i] = new ComboBox<>("Select Subject");
						olevelSubjectsCombo[i].setRequiredIndicatorVisible(true);
						olevelSubjectsCombo[i].setItems(AllSecondarySubjectsBin.allSubjectsArray());
						olevelSubjectsCombo[i].setValue("None");
						olevelSubjectsCombo[i].setEmptySelectionAllowed(false);
						olevelSymbolsCombo[i] = new ComboBox<>("Select Symbol");
						olevelSymbolsCombo[i].setRequiredIndicatorVisible(true);
						olevelSymbolsCombo[i].setItems("A", "B", "C", "D", "E", "X");
						olevelSymbolsCombo[i].setValue("X");
						olevelSymbolsCombo[i].setEmptySelectionAllowed(false);
						alevelsubjectslbl[i] = new Label("" + (i + 1));
						olevelgridLayout.addComponent(alevelsubjectslbl[i], 0, i);
						olevelgridLayout.addComponent(olevelSubjectsCombo[i], 1, i);
						olevelgridLayout.addComponent(olevelSymbolsCombo[i], 2, i);
					}
					olevelschool = new TextField("Name of School");
					olevelschool.setRequiredIndicatorVisible(true);
					olevelgridLayout.addComponent(olevelschool, 1, (number));

					olevelyear = new DateField("Year Accomplished");
					olevelyear.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
					olevelyear.setDateFormat("yyyy");
					olevelyear.setRequiredIndicatorVisible(true);
					olevelgridLayout.addComponent(olevelyear, 2, (number));
				}
			}
		});

		layout.addComponents(horizontalLayout, olevelgridLayout);

		return layout;
	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Teachers Registration")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Teachers Registration", VaadinIcons.USER);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

	@SuppressWarnings({ "unchecked" })
	public VerticalLayout teacherAlevelPasses() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setCaption("A'Level Subjects");
		layout.setIcon(VaadinIcons.FOLDER);
		layout.addStyleName("musd");

		numberofalevelCombo = new ComboBox<>("Number of A'Levels");
		numberofalevelCombo.setEmptySelectionAllowed(false);
		// numberofalevelCombo.addStyleName(style);
		numberofalevelCombo.setItems(NumberOfSubjects.numbersArray());
		HorizontalLayout horizontalLayout = new HorizontalLayout(numberofalevelCombo);
		horizontalLayout.setSpacing(true);

		alevelgridLayout = new GridLayout();
		alevelgridLayout.setSpacing(true);
		alevelgridLayout.setColumns(3);

		numberofalevelCombo.addValueChangeListener((e) -> {
			String numberr = (String) e.getValue();
			if (!numberr.equals("")) {

				alevelgridLayout.removeAllComponents();
				int number = Integer.parseInt(numberr);
				if (number > 0) {
					alevelSubjectsCombo = new ComboBox[number];
					alevelSymbolsCombo = new ComboBox[number];

					Label[] alevelsubjectslbl = new Label[number];
					alevelgridLayout.setRows(number + 1);
					for (int i = 0; i < number; i++) {
						alevelSubjectsCombo[i] = new ComboBox<>("Select Subject");
						alevelSubjectsCombo[i].setRequiredIndicatorVisible(true);
						alevelSubjectsCombo[i].setItems(AllSecondarySubjectsBin.allSubjectsArray());
						alevelSubjectsCombo[i].setValue("None");
						alevelSubjectsCombo[i].setEmptySelectionAllowed(false);
						alevelSymbolsCombo[i] = new ComboBox<>("Select Symbol");
						alevelSymbolsCombo[i].setRequiredIndicatorVisible(true);
						alevelSymbolsCombo[i].setItems("A", "B", "C", "D", "E", "X");
						alevelSymbolsCombo[i].setValue("X");
						alevelSymbolsCombo[i].setEmptySelectionAllowed(false);
						alevelsubjectslbl[i] = new Label("" + (i + 1));
						alevelgridLayout.addComponent(alevelsubjectslbl[i], 0, i);
						alevelgridLayout.addComponent(alevelSubjectsCombo[i], 1, i);
						alevelgridLayout.addComponent(alevelSymbolsCombo[i], 2, i);
					}
					alevelschool = new TextField("Name of School");
					alevelschool.setRequiredIndicatorVisible(true);
					alevelgridLayout.addComponent(alevelschool, 1, (number));

					alevelyear = new DateField("Year Accomplished");
					alevelyear.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
					alevelyear.setDateFormat("yyyy");
					alevelyear.setRequiredIndicatorVisible(true);
					alevelgridLayout.addComponent(alevelyear, 2, (number));
				}

			}
		});

		layout.addComponents(horizontalLayout, alevelgridLayout);

		return layout;
	}

	@SuppressWarnings("unchecked")
	public VerticalLayout createProfessionLayout() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setCaption("Professional Qualifications");
		layout.setIcon(VaadinIcons.BRIEFCASE);

		numberofprofessionsCombo = new ComboBox<>("Qualifications");
		numberofprofessionsCombo.setEmptySelectionAllowed(false);
		numberofprofessionsCombo.setItems(NumberOfSubjects.numbersArray());

		HorizontalLayout horizontalLayout = new HorizontalLayout(numberofprofessionsCombo);
		horizontalLayout.setSpacing(true);

		profgridLayout = new GridLayout();
		profgridLayout.setSpacing(true);
		profgridLayout.setColumns(6);
		numberofprofessionsCombo.addValueChangeListener((e) -> {
			String numberr = (String) e.getValue();
			if (!numberr.equals("")) {
				profgridLayout.removeAllComponents();
				int number = Integer.parseInt(numberr);
				programme = new TextField[number];
				specialisation = new ComboBox[number];
				universityorcollege = new ComboBox[number];
				proficiency = new TextField[number];
				yearobtained = new DateField[number];
				Label[] alevelsubjectslbl = new Label[number];
				profgridLayout.setRows(number + 1);
				for (int i = 0; i < number; i++) {
					programme[i] = new TextField("Programme");
					programme[i].setRequiredIndicatorVisible(true);
					programme[i].setValue("None");

					specialisation[i] = new ComboBox<>("Specialisation");
					specialisation[i].setRequiredIndicatorVisible(true);
					specialisation[i].setItems("None", "Infants", "Primary", "Secondary", "Tertiary", "Degreed");
					specialisation[i].setValue("None");

					universityorcollege[i] = new ComboBox<>("University or College");
					universityorcollege[i].setRequiredIndicatorVisible(true);
					universityorcollege[i].setItems(AllColleges.colleges());
					universityorcollege[i].setValue("None");

					proficiency[i] = new TextField("Proficiency");
					proficiency[i].setRequiredIndicatorVisible(true);
					proficiency[i].setValue("None");

					yearobtained[i] = new DateField("Year Obtained");
					yearobtained[i].setRequiredIndicatorVisible(true);
					yearobtained[i].setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
					yearobtained[i].setDateFormat("yyyy");

					alevelsubjectslbl[i] = new Label("" + (i + 1));

					profgridLayout.addComponent(alevelsubjectslbl[i], 0, i);
					profgridLayout.addComponent(programme[i], 1, i);
					profgridLayout.addComponent(specialisation[i], 2, i);
					profgridLayout.addComponent(universityorcollege[i], 3, i);
					profgridLayout.addComponent(proficiency[i], 4, i);
					profgridLayout.addComponent(yearobtained[i], 5, i);

				}

				profgridLayout.addComponent(submit, 1, number);
				JavaScript.getCurrent().execute("window.scroll()");

			}
		});

		layout.addComponents(horizontalLayout, profgridLayout);

		return layout;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		firstName.focus();
	}
}