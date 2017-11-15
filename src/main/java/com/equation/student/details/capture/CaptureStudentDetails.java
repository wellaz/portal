package com.equation.student.details.capture;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import com.equation.administrator.users.create.InsertDataIntoUsersTable;
import com.equation.administrator.users.create.SystemUserLevels;
import com.equation.school.classes.capture.AllSchoolClasses;
import com.equation.school.cocurricular.subjects.AllCocurricularSubjects;
import com.equation.school.houses.AllSchoolHouses;
import com.equation.student.disabilities.Disabilities;
import com.equation.student.parent.details.capture.InsertDataIntoParentsTable;
import com.equation.student.registration.ids.generate.Alphabet;
import com.equation.student.registration.ids.generate.AutoIncrementStudentID;
import com.equation.student.registration.ids.generate.ConvertValueToThreeDigits;
import com.equation.student.registration.ids.generate.FirstFiveDigitsOfStudentID;
import com.equation.student.registration.ids.generate.GenerateSecondCheckLetter;
import com.equation.student.registration.ids.generate.GenerateTheFirstCheckLetter;
import com.equation.student.registration.ids.generate.LastTwoDigitsOfYear;
import com.equation.student.registration.ids.generate.RegistrationYearsList;
import com.equation.user.session.attributes.UserSessionAttributes;
import com.equation.utils.date.DateUtility;
import com.equation.utils.religions.AllReligions;
import com.equation.utils.student.status.StudentStatus;
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
public class CaptureStudentDetails extends CustomComponent implements View {

	Statement stm;
	ResultSet rs;

	private TextField sFirstName, sSurname, sBirthEntry;
	ComboBox<String> disability;
	private ComboBox<String> religion, house, sgender, boarder, studentClass, yearRegistered;
	private DateField sBirthDate;
	private TabSheet tabs;
	private TextField pFirstName, pSurname, pNationalID, pCell, pEmail, pAdress, pOther;
	private Button submit;
	private ComboBox<String> cocurrucularCombo;
	String emisNumber;

	@SuppressWarnings("deprecation")
	public CaptureStudentDetails(Statement stm, ResultSet rs, TabSheet tabs, String emisNumber) {
		this.rs = rs;
		this.stm = stm;
		this.tabs = tabs;
		this.emisNumber = emisNumber;
		AllSchoolClasses allSchoolClasses = new AllSchoolClasses(rs, stm, emisNumber);

		sFirstName = new TextField("First Name");
		sFirstName.setRequiredIndicatorVisible(true);

		yearRegistered = new ComboBox<>("Year Registered");
		yearRegistered.setItems(RegistrationYearsList.yearsList());
		yearRegistered.setRequiredIndicatorVisible(true);
		yearRegistered.setValue(RegistrationYearsList.thisYear());

		sSurname = new TextField("Surname");
		sSurname.setRequiredIndicatorVisible(true);

		sBirthEntry = new TextField("Birth Entry Number");
		sBirthEntry.setRequiredIndicatorVisible(true);

		disability = new ComboBox<>("Any Impairment");
		disability.setRequiredIndicatorVisible(true);
		disability.setItems(Disabilities.allDisabilities());
		disability.setEmptySelectionAllowed(false);

		religion = new ComboBox<>("Religion");
		religion.setItems(AllReligions.allReligionsCollecion());

		house = new ComboBox<>("Sporting House");
		house.setRequiredIndicatorVisible(true);
		house.setItems(new AllSchoolHouses(stm, rs).allSchoolHousesByEmis(emisNumber));
		house.setEmptySelectionAllowed(false);

		sgender = new ComboBox<>("Gender");
		sgender.setItems("Male", "Female");
		sgender.setRequiredIndicatorVisible(true);
		sgender.setEmptySelectionAllowed(false);

		cocurrucularCombo = new ComboBox<>("Co-cocurrucular Activity");
		cocurrucularCombo.setRequiredIndicatorVisible(true);
		cocurrucularCombo.setItems(AllCocurricularSubjects.allHobbies());

		boarder = new ComboBox<>("Boarding Student");
		boarder.setItems("Yes", "No");
		boarder.setRequiredIndicatorVisible(true);
		boarder.setEmptySelectionAllowed(false);

		sBirthDate = new DateField("Date Of Birth");
		sBirthDate.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		sBirthDate.setDateFormat("yyyy-MM-dd");
		sBirthDate.setRequiredIndicatorVisible(true);

		studentClass = new ComboBox<>("Select Student Class");
		studentClass.setEmptySelectionAllowed(false);
		studentClass.setRequiredIndicatorVisible(true);
		studentClass.setItems(allSchoolClasses.allClasses());

		submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.addClickListener((e) -> {

			String year = (String) yearRegistered.getValue();

			String lastTwoDigits = new LastTwoDigitsOfYear().getLastTwoDigitsOfYear(year);

			int nextAutoIncrementValue = new AutoIncrementStudentID(rs, stm).generateNextValue(lastTwoDigits);

			String emis = (String) getSession().getAttribute(UserSessionAttributes.SCHOOL_EMIS_NUMBER);

			GenerateTheFirstCheckLetter generateTheFirstCheckLetter = new GenerateTheFirstCheckLetter();
			FirstFiveDigitsOfStudentID firstFiveDigitsOfStudentID = new FirstFiveDigitsOfStudentID();
			LastTwoDigitsOfYear lastTwoDigitsOfYear = new LastTwoDigitsOfYear();
			GenerateSecondCheckLetter generateSecondCheckLetter = new GenerateSecondCheckLetter();
			ConvertValueToThreeDigits convertValueToThreeDigits = new ConvertValueToThreeDigits();

			ArrayList<String> alphabetArray = Alphabet.alphabetArray();
			int arraySize = alphabetArray.size();

			// hokoyo

			String fiveDigitValue = firstFiveDigitsOfStudentID.generateFirstFiveDigits(nextAutoIncrementValue, year);

			int quotient = generateTheFirstCheckLetter.returnTheQuotient(fiveDigitValue, arraySize);

			int product = generateTheFirstCheckLetter.returnTheProduct(quotient, arraySize);

			int index = generateTheFirstCheckLetter.returnTheDifference(fiveDigitValue, product);

			String firstCheckLetter = generateTheFirstCheckLetter.getFirstCheckLetter(fiveDigitValue, quotient,
					product);

			int firstIndex = index;

			String studentIDd = firstCheckLetter + lastTwoDigitsOfYear.getLastTwoDigitsOfYear(year)
					+ convertValueToThreeDigits.generateThreeDigitValue(nextAutoIncrementValue)
					+ generateSecondCheckLetter.getSecondCheckLetter(emis, firstIndex);

			String sFirstNamee = sFirstName.getValue();
			String sSurnamee = sSurname.getValue();
			String sBirthDatee = String.format("%1$tY-%1$tm-%1$td", sBirthDate.getValue());
			String sBirthEntryy = sBirthEntry.getValue();
			int classIDd = allSchoolClasses.getClassID((String) studentClass.getValue());
			String disabilityy = (String) disability.getValue();
			String sgenderr = (String) sgender.getValue();
			String cocurrucular = (String) cocurrucularCombo.getValue();
			String boarderr = (String) boarder.getValue();
			String housee = (String) house.getValue();
			String religionn = (String) religion.getValue();

			String pFirstNamee = pFirstName.getValue();
			String pSurnamee = pSurname.getValue();
			String pNationalIDd = pNationalID.getValue();
			String pCelll = pCell.getValue();
			String pEmaill = pEmail.getValue();
			String pAdresss = pAdress.getValue();
			String pOtherr = pOther.getValue();

			if (!(studentIDd.equals("") || sFirstNamee.equals("") || sSurnamee.equals("") || sBirthDatee.equals("")
					|| sBirthEntryy.equals("") || disabilityy.equals("") || sgenderr.equals("") || boarderr.equals("")
					|| housee.equals("") || religionn.equals("") || pFirstNamee.equals("") || pSurnamee.equals("")
					|| pNationalIDd.equals("") || pCelll.equals("") || pEmaill.equals("") || pAdresss.equals("")
					|| pOtherr.equals(""))) {

				InsertDataIntoParentsTable dataIntoParentsTable = new InsertDataIntoParentsTable(stm);
				dataIntoParentsTable.insertData(pFirstNamee, pSurnamee, pNationalIDd, pCelll, pEmaill, pAdresss,
						pOtherr);

				InsertDataIntoStudentsTable dataIntoStudentsTable = new InsertDataIntoStudentsTable(stm);
				String status = StudentStatus.ACTIVE;
				dataIntoStudentsTable.insertData(studentIDd, sFirstNamee, sSurnamee, sBirthDatee, sBirthEntryy,
						classIDd, disabilityy, sgenderr, boarderr, housee, religionn, pNationalIDd, cocurrucular,
						status);

				InsertDataIntoUsersTable dataIntoUsersTable = new InsertDataIntoUsersTable(stm);
				dataIntoUsersTable.insertData(studentIDd, SystemUserLevels.getStudent(), sFirstNamee,
						studentIDd.toLowerCase(), new DateUtility().timeStamp());

				new Notification("Success",
						"Name :<br/>" + (sFirstNamee + " " + sSurnamee).toUpperCase() + "<br/>Student ID: " + studentIDd
								+ "<br/>Registration: Success",
						Notification.TYPE_HUMANIZED_MESSAGE, true).show(Page.getCurrent());

				sFirstName.clear();
				sSurname.clear();
				sBirthEntry.clear();
				disability.clear();
				religion.clear();
				house.clear();
				sgender.clear();
				boarder.clear();
				studentClass.clear();
				cocurrucularCombo.clear();

				pFirstName.clear();
				pSurname.clear();
				pNationalID.clear();
				pCell.clear();
				pEmail.clear();
				pAdress.clear();
				pOther.clear();
			} else {
				new Notification("Warning", "A blank field has been detected!", Notification.TYPE_ERROR_MESSAGE, true)
						.show(Page.getCurrent());
			}

		});

		FormLayout formLayout = parentsDetailsForm();
		formLayout.setCaption("Parents Details");

		FormLayout formLayout1 = new FormLayout(yearRegistered, studentClass, sFirstName, sSurname, sgender,
				cocurrucularCombo);
		formLayout1.setCaption("Student Registration Form".toUpperCase());
		formLayout1.setSpacing(true);

		VerticalLayout verticalLayout = new VerticalLayout();

		FormLayout formLayout2 = new FormLayout(sBirthDate, sBirthEntry, disability, boarder, house, religion);
		formLayout2.setSpacing(true);
		formLayout2.setCaption("");

		HorizontalLayout gridLayout = new HorizontalLayout();
		gridLayout.setSpacing(true);
		gridLayout.addComponents(formLayout1, formLayout2, formLayout);

		verticalLayout.addComponent(gridLayout);
		verticalLayout.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);
		this.setCompositionRoot(verticalLayout);
	}

	public FormLayout parentsDetailsForm() {

		pFirstName = new TextField("Parent First Name");
		pFirstName.setRequiredIndicatorVisible(true);
		pSurname = new TextField("Parent Surname");
		pSurname.setRequiredIndicatorVisible(true);
		pNationalID = new TextField("Parent National ID");
		pNationalID.setRequiredIndicatorVisible(true);
		pCell = new TextField("Parent Cell Number");
		pCell.setRequiredIndicatorVisible(true);
		pEmail = new TextField("Parent Email");
		// pEmail.addValidator(new EmailValidator("Correct the emain address"));
		pAdress = new TextField("Parent Physica Address");
		pAdress.setRequiredIndicatorVisible(true);
		pOther = new TextField("Other Details");

		FormLayout formLayout = new FormLayout(pFirstName, pSurname, pNationalID, pCell, pEmail, pAdress, pOther,
				submit);
		formLayout.setSpacing(true);

		return formLayout;
	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Student Registration")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Student Registration", VaadinIcons.USER);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		sFirstName.focus();
	}
}