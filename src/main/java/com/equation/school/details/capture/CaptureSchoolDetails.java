package com.equation.school.details.capture;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.equation.school.electricity.details.capture.InsertDataIntoSchoolElectrificationTable;
import com.equation.school.houses.InsertDataIntoSchoolHousesTable;
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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class CaptureSchoolDetails extends CustomComponent implements View {

	TextField schoolname, emisnumber, departmentcode, stationcode, responsibleauthority, centernumber;
	TextField phonefield;
	TextField cellfield;
	TextField emailfield;
	TextField websitefield;
	TextField physicalAddressfield;
	TextField postalAddressfield;
	TextField otherfield;
	ComboBox<String> schooltype, boardingorday, electrified;
	DateField yearestablished;
	private Button submit;
	Statement stm;
	ResultSet rs;
	TabSheet tabs;
	private ComboBox<String> numberofHouses;
	private TextField[] houses;
	public static final String NAME = "school-contact-details";

	@SuppressWarnings("deprecation")
	public CaptureSchoolDetails(Statement stm, ResultSet rs, TabSheet tabs) {
		this.stm = stm;
		this.rs = rs;
		this.tabs = tabs;
		schoolname = new TextField("School Name");
		emisnumber = new TextField("EMIS Number");
		departmentcode = new TextField("Department Code");
		stationcode = new TextField("Station Code");
		responsibleauthority = new TextField("Responsible Authority");
		centernumber = new TextField("Center Number");

		schooltype = new ComboBox<>("Type of School");
		schooltype.setItems("Government", "Private", "Council", "Mission");

		boardingorday = new ComboBox<>("Boarding School");
		boardingorday.setItems("Yes", "No");

		yearestablished = new DateField("Year Established");
		yearestablished.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		yearestablished.setDateFormat("yyyy");

		electrified = new ComboBox<>("Electrified");
		electrified.setItems("Yes", "No");

		submit = new Button("Submit", VaadinIcons.ARCHIVE);
		FormLayout contactslayout = createContactDetailsForm();
		FormLayout housesLayout = createHousesForm();
		submit.addClickListener((e) -> {
			InsertDataIntoSchoolsTable dataIntoSchoolsTable = new InsertDataIntoSchoolsTable(stm);

			String schoolName = schoolname.getValue();
			String type = (String) schooltype.getValue();
			String emisNumber = emisnumber.getValue();
			String deptCode = departmentcode.getValue();
			String stnCode = stationcode.getValue();
			String responsibleAuthority = responsibleauthority.getValue();
			String boarding = (String) boardingorday.getValue();
			LocalDate yearEstablish = yearestablished.getValue();
			Date yearEstablished = Date.from(yearEstablish.atStartOfDay(ZoneId.systemDefault()).toInstant());
			String centreNumber = centernumber.getValue();
			String electifiedd = (String) electrified.getValue();

			String phone = phonefield.getValue();
			String cell = cellfield.getValue();
			String email = emailfield.getValue();
			String website = websitefield.getValue();
			String physicalAddress = physicalAddressfield.getValue();
			String postalAddress = postalAddressfield.getValue();
			String other = otherfield.getValue();
			String numberofhuses = (String) numberofHouses.getValue();

			if (!(schoolName.equals("") || type.equals("") || emisNumber.equals("") || deptCode.equals("")
					|| stnCode.equals("") || responsibleAuthority.equals("") || boarding.equals("")
					|| yearEstablished.equals("") || centreNumber.equals("") || cell.equals("")
					|| physicalAddress.equals("") || postalAddress.equals("") || numberofhuses.equals(""))) {

				SchoolsEMISNumbersCollection collection = new SchoolsEMISNumbersCollection(rs, stm);
				InsertDataIntoSchoolContactDetails contactDetails = new InsertDataIntoSchoolContactDetails(stm);
				InsertDataIntoSchoolElectrificationTable dataIntoSchoolElectrificationTable = new InsertDataIntoSchoolElectrificationTable(
						stm);
				InsertDataIntoSchoolHousesTable dataIntoSchoolHousesTable = new InsertDataIntoSchoolHousesTable(stm);
				if (!collection.isFoundEmis(emisNumber)) {
					int value = Integer.parseInt(numberofhuses);
					for (int i = 0; i < value; i++) {
						String houseName = houses[i].getValue();
						dataIntoSchoolHousesTable.insertData(emisNumber, houseName);
					}

					String date = (DateFormat.getDateInstance(DateFormat.MEDIUM)).format(yearEstablished);

					dataIntoSchoolsTable.insertData(schoolName, type, emisNumber, deptCode, stnCode,
							responsibleAuthority, boarding, (date.split(","))[1].trim(), centreNumber);
					contactDetails.insertData(phone, cell, email, website, physicalAddress, postalAddress, other,
							centreNumber);
					dataIntoSchoolElectrificationTable.insertData(emisNumber, electifiedd);
					new Notification("Success".toUpperCase(), "The record has been successfully saved.",
							Notification.TYPE_HUMANIZED_MESSAGE).show(Page.getCurrent());

					phonefield.clear();
					cellfield.clear();
					emailfield.clear();
					websitefield.clear();
					physicalAddressfield.clear();
					postalAddressfield.clear();
					otherfield.clear();
					schoolname.clear();
					emisnumber.clear();
					departmentcode.clear();
					stationcode.clear();
					responsibleauthority.clear();
					centernumber.clear();
					schooltype.clear();
					boardingorday.clear();
					electrified.clear();

					for (int i = 0; i < value; i++) {
						houses[i].clear();
					}

				} else {
					new Notification("Warning",
							"EMIS Number " + emisNumber
									+ " already exists in the system.\nPlease enter correct EMIS number!",
							Notification.TYPE_ERROR_MESSAGE).show(Page.getCurrent());
					emisnumber.focus();
				}

			} else {
				new Notification("Warning",
						"A Blank Field has been detected.\nPlease, make sure that you have entered all the required details!",
						Notification.TYPE_ERROR_MESSAGE).show(Page.getCurrent());
			}

		});
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.setId("school_registration_submit");

		FormLayout formLayout = new FormLayout(schoolname, emisnumber, departmentcode, stationcode, centernumber);
		formLayout.setSpacing(true);
		FormLayout formLayout2 = new FormLayout(responsibleauthority, schooltype, boardingorday, electrified,
				yearestablished);
		formLayout2.setSpacing(true);

		VerticalLayout verticalLayout = new VerticalLayout();
		HorizontalLayout banner = new HorizontalLayout(formLayout, formLayout2, contactslayout, housesLayout);
		banner.setSpacing(true);
		Panel panel = new Panel(banner);
		verticalLayout.addComponent(panel);

		verticalLayout.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

		this.setCompositionRoot(verticalLayout);

	}

	/**
	 * 
	 * @return The school Name
	 */
	public String getSchoolName() {
		return schoolname.getValue();
	}

	/**
	 * 
	 * @return The school EMIS Number
	 */
	public String getEMISNumber() {
		return emisnumber.getValue();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	public FormLayout createContactDetailsForm() {

		phonefield = new TextField("Phone Number");
		cellfield = new TextField("Cell Number");
		emailfield = new TextField("Email Address");
		emailfield.setPlaceholder("schoolname@email.com");
		// emailfield.addValidator(new EmailValidator("Type in the correct email
		// address"));
		websitefield = new TextField("Website");
		physicalAddressfield = new TextField("Physical Address");
		postalAddressfield = new TextField("Postal Address");
		otherfield = new TextField("Other(s)");
		FormLayout formLayout = new FormLayout(phonefield, cellfield, emailfield, websitefield, physicalAddressfield,
				postalAddressfield, otherfield);
		return formLayout;
	}

	public FormLayout createHousesForm() {
		numberofHouses = new ComboBox<>("Number of Sporting Houses");
		numberofHouses.setRequiredIndicatorVisible(true);
		numberofHouses.setEmptySelectionAllowed(false);
		numberofHouses.setItems(NumberOfSubjects.numbersArray());
		FormLayout formLayout = new FormLayout();
		numberofHouses.addValueChangeListener((e) -> {
			String st = (String) e.getValue();
			formLayout.removeAllComponents();
			formLayout.addComponent(numberofHouses);
			if (!st.equals("")) {
				int value = Integer.parseInt(st);
				houses = new TextField[value];
				for (int i = 0; i < value; i++) {
					houses[i] = new TextField("House #" + (i + 1));
					houses[i].setRequiredIndicatorVisible(true);
					formLayout.addComponent(houses[i]);
				}
			}
			formLayout.addComponent(submit);

		});
		formLayout.addComponent(numberofHouses);
		formLayout.setSpacing(true);
		return formLayout;
	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("School Registration")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "School Registration", VaadinIcons.BUILDING_O);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}
}