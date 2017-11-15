package com.equation.checkin.teachers;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.student.autosearch.ClearAndFocusInputField;
import com.equation.utils.application.basepath.ApplicationBasePath;
import com.equation.utils.date.CalculateAge;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.v7.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */

@SuppressWarnings("deprecation")
public class TeacherGeneralDetails {
	ResultSet rs, rs1;
	Statement stm, stmt;
	String schoolID;
	VerticalLayout layout;
	TextField barcodeField;

	public TeacherGeneralDetails(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, String schoolID,
			VerticalLayout layout, TextField barcodeField) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.schoolID = schoolID;
		this.layout = layout;
		this.barcodeField = barcodeField;
	}

	public VerticalLayout displayTeacherDetails(String ecNumber) {
		VerticalLayout layout1 = new VerticalLayout();

		String selectQuery = "SELECT firstName FROM teachers,schools WHERE teachers.ecNumber = '" + ecNumber
				+ "' AND  schools.schoolID = '" + schoolID + "' OR schools.schoolName = '" + schoolID + "'";
		try {
			rs1 = stmt.executeQuery(selectQuery);
			rs1.last();
			int rows = rs1.getRow();
			if (rows > 0) {
				Table nametable = new Table("Personal Details");
				nametable.setSelectable(true);
				nametable.addContainerProperty("Property", String.class, null);
				nametable.addContainerProperty("Value", String.class, null);

				Table contactstable = new Table("Contact Details");
				contactstable.setSelectable(true);
				contactstable.addContainerProperty("Property", String.class, null);
				contactstable.addContainerProperty("Value", String.class, null);

				Table basix = new Table();
				basix.setSelectable(true);
				basix.addContainerProperty("Property", String.class, null);
				basix.addContainerProperty("Value", String.class, null);

				Table nextofkeen = new Table("Next Of Keen");
				nextofkeen.setSelectable(true);
				nextofkeen.addContainerProperty("Property", String.class, null);
				nextofkeen.addContainerProperty("Value", String.class, null);

				Table oleveltable = new Table("O'Level Passes");
				oleveltable.setSelectable(true);
				oleveltable.addContainerProperty("Subject", String.class, null);
				oleveltable.addContainerProperty("Symbol", String.class, null);
				oleveltable.addContainerProperty("School", String.class, null);
				oleveltable.addContainerProperty("Year", String.class, null);

				Table aleveltable = new Table("A'Level Passes");
				aleveltable.setSelectable(true);
				aleveltable.addContainerProperty("Subject", String.class, null);
				aleveltable.addContainerProperty("Symbol", String.class, null);
				aleveltable.addContainerProperty("School", String.class, null);
				aleveltable.addContainerProperty("Year", String.class, null);

				Table professiontable = new Table("Professional Qualification");
				professiontable.setSelectable(true);
				professiontable.addContainerProperty("Programme", String.class, null);
				professiontable.addContainerProperty("Specilization Area", String.class, null);
				professiontable.addContainerProperty("University Or College", String.class, null);
				professiontable.addContainerProperty("Proficiency", String.class, null);
				professiontable.addContainerProperty("Year", String.class, null);

				String query1 = "SELECT firstName,surname,gender,dateOfBirth,nationalID,nationality,dOJS,maritalStatus,mainSubjects,middleName,otherName,dateOfJoiningSchool FROM teachers,schools WHERE teachers.ecNumber = '"
						+ ecNumber + "' AND  schools.schoolID = '" + schoolID + "' OR schools.schoolName = '" + schoolID
						+ "'";
				rs = stm.executeQuery(query1);
				if (rs.next()) {
					String firstName = rs.getString(1);
					String surname = rs.getString(2);
					String gender = rs.getString(3);
					String dateOfBirth = rs.getString(4);
					String nationalID = rs.getString(5);
					String nationality = rs.getString(6);
					String dOJS = rs.getString(7);
					String maritalStatus = rs.getString(8);
					String mainSubjects = rs.getString(9);
					String middleName = rs.getString(10);
					String otherName = rs.getString(11);
					String dateOfJoiningSchool = rs.getString(12);

					int age = new CalculateAge().getAge(dateOfBirth);

					nametable.addItem(new Object[] { "EC Number", ecNumber }, 0);
					nametable.addItem(new Object[] { "First Name", firstName }, 1);
					nametable.addItem(new Object[] { "Middle Name", middleName }, 2);
					nametable.addItem(new Object[] { "Last name", surname }, 3);
					nametable.addItem(new Object[] { "Other Name", otherName }, 4);
					nametable.addItem(new Object[] { "Gender", gender }, 5);
					nametable.addItem(new Object[] { "Age", String.valueOf(age) }, 6);
					nametable.addItem(new Object[] { "National ID", nationalID }, 7);
					nametable.addItem(new Object[] { "Nationality", nationality }, 8);
					nametable.addItem(new Object[] { "Marial Tatus", maritalStatus }, 9);
					nametable.setPageLength(nametable.size());
					nametable.setColumnCollapsingAllowed(true);
					nametable.setFooterVisible(true);
					nametable.setSelectable(true);
					// nametable.setSizeFull();

					String subQuery1 = "SELECT gradeOrSubjects,period,cocuricular FROM teachers,teacherestablishment,schools WHERE teachers.ecNumber = '"
							+ ecNumber + "' AND schools.schoolID = '" + schoolID + "' OR schools.schoolName = '"
							+ schoolID + "'";
					String gradeOrSubjects = null, period = null, cocuricular = null;
					rs1 = stmt.executeQuery(subQuery1);
					if (rs1.next()) {
						gradeOrSubjects = rs1.getString(1);
						period = rs1.getString(2);
						cocuricular = rs1.getString(3);
					} else {
						gradeOrSubjects = "-";
						period = "-";
						cocuricular = "-";
					}

					basix.addItem(new Object[] { "Date Of Joining Service", dOJS }, 0);
					basix.addItem(new Object[] { "Main Subjects", mainSubjects }, 1);
					basix.addItem(new Object[] { "Date Of Joining School", dateOfJoiningSchool }, 2);
					basix.addItem(new Object[] { "Grade Or Subjects", gradeOrSubjects }, 3);
					basix.addItem(new Object[] { "Period", String.valueOf(period) }, 4);
					basix.addItem(new Object[] { "Co-curricular", cocuricular }, 5);
					basix.setPageLength(basix.size());

					String subQuery2 = "SELECT phone,cell,email,other,physicalAddress,postalAddress FROM teachers,teachercontactdetails,schools WHERE teachers.ecNumber = '"
							+ ecNumber + "' AND schools.schoolID = '" + schoolID + "' OR schools.schoolName = '"
							+ schoolID + "'";
					rs1 = stmt.executeQuery(subQuery2);
					String phone = null, cell = null, email = null, other = null, physicalAddress = null,
							postalAddress = null;
					if (rs1.next()) {
						phone = rs1.getString(1);
						cell = rs1.getString(2);
						email = rs1.getString(3);
						other = rs1.getString(4);
						physicalAddress = rs1.getString(5);
						postalAddress = rs1.getString(6);
					} else {
						phone = "-";
						cell = "-";
						email = "-";
						other = "-";
						physicalAddress = "-";
						postalAddress = "-";
					}
					contactstable.addItem(new Object[] { "Phone Number", String.valueOf(phone) }, 0);
					contactstable.addItem(new Object[] { "Mobile Number", String.valueOf(cell) }, 1);
					contactstable.addItem(new Object[] { "Email Address", email }, 2);
					contactstable.addItem(new Object[] { "Other Contacts", other }, 3);
					contactstable.addItem(new Object[] { "Physical Address", physicalAddress }, 4);
					contactstable.addItem(new Object[] { "Postal Address", postalAddress }, 5);
					contactstable.setPageLength(contactstable.size());

					String subQuery3 = "SELECT 	kFirstName,kSurname,kNationalID,kCell,kEmail,kAdress,kOther,kRelationship FROM teachers,teachernextofkeen,schools WHERE teachers.ecNumber = '"
							+ ecNumber + "' AND schools.schoolID = '" + schoolID + "' OR schools.schoolName = '"
							+ schoolID + "'";

					String kFirstName = null, kSurname = null, kNationalID = null, kCell = null, kEmail = null,
							kAdress = null, kOther = null, kRelationship = null;
					rs1 = stmt.executeQuery(subQuery3);
					if (rs1.next()) {
						kFirstName = rs1.getString(1);
						kSurname = rs1.getString(1);
						kNationalID = rs1.getString(1);
						kCell = rs1.getString(1);
						kEmail = rs1.getString(1);
						kAdress = rs1.getString(1);
						kOther = rs1.getString(1);
						kRelationship = rs1.getString(1);
					} else {
						kFirstName = "-";
						kSurname = "-";
						kNationalID = "-";
						kCell = "-";
						kEmail = "-";
						kAdress = "-";
						kOther = "-";
						kRelationship = "-";

					}

					nextofkeen.addItem(new Object[] { "First Name", kFirstName }, 0);
					nextofkeen.addItem(new Object[] { "Surname", kSurname }, 1);
					nextofkeen.addItem(new Object[] { "National ID", kNationalID }, 2);
					nextofkeen.addItem(new Object[] { "Mobile Number", String.valueOf(kCell) }, 3);
					nextofkeen.addItem(new Object[] { "Email Address", kEmail }, 4);
					nextofkeen.addItem(new Object[] { "Physical Address", kAdress }, 5);
					nextofkeen.addItem(new Object[] { "Other Details", kOther }, 6);
					nextofkeen.addItem(new Object[] { "Relationship", kRelationship }, 7);
					nextofkeen.setPageLength(nextofkeen.size());

					String subQuery4 = "SELECT subject,symbol,school,year FROM teachers,olevelteacheracademicqualifications,schools WHERE teachers.ecNumber = '"
							+ ecNumber + "' AND schools.schoolID = '" + schoolID + "' OR schools.schoolName = '"
							+ schoolID + "'";
					rs1 = stmt.executeQuery(subQuery4);
					int s = 0;
					while (rs1.next()) {
						String subject = rs1.getString(1);
						String symbol = rs1.getString(2);
						String school = rs1.getString(3);
						String year = rs1.getString(4);
						oleveltable.addItem(new Object[] { subject, symbol, school, String.valueOf(year) },
								new Integer(s));
						s++;
					}
					oleveltable.setPageLength(oleveltable.size());

					String subQuery5 = "SELECT subject,symbol,school,year FROM teachers,alevelteacheracademicqualifications,schools WHERE teachers.ecNumber = '"
							+ ecNumber + "' AND schools.schoolID = '" + schoolID + "' OR schools.schoolName = '"
							+ schoolID + "'";
					rs1 = stmt.executeQuery(subQuery5);
					int q = 0;
					while (rs1.next()) {
						String subject = rs1.getString(1);
						String symbol = rs1.getString(2);
						String school = rs1.getString(3);
						String year = rs1.getString(4);
						aleveltable.addItem(new Object[] { subject, symbol, school, String.valueOf(year) },
								new Integer(q));
						q++;
					}
					aleveltable.setPageLength(aleveltable.size());

					String subQuery6 = "SELECT programme,specialisation,universityorcollege,profficiency,year FROM teachers,teacherprofessionqualifications,schools WHERE teachers.ecNumber = '"
							+ ecNumber + "' AND schools.schoolID = '" + schoolID + "' OR schools.schoolName = '"
							+ schoolID + "'";
					rs1 = stmt.executeQuery(subQuery6);
					int p = 0;
					while (rs1.next()) {
						String programme = rs1.getString(1), specialisation = rs1.getString(2),
								universityorcollege = rs1.getString(3), profficiency = rs1.getString(4),
								year = rs1.getString(5);
						professiontable.addItem(new Object[] { programme, specialisation, universityorcollege,
								String.valueOf(profficiency), String.valueOf(year) }, new Integer(p));
						p++;
					}
					professiontable.setPageLength(professiontable.size());
				}
				Button close = new Button("Close");
				close.setIcon(VaadinIcons.CLOSE);
				close.addStyleName(ValoTheme.BUTTON_DANGER);
				close.addClickListener((e) -> {
					this.layout.removeAllComponents();
					clearAndFocusBarcodeField(barcodeField);
				});
				HorizontalLayout horizontalLayout = new HorizontalLayout(close);
				horizontalLayout.setSpacing(true);

				Image image = new Image(ecNumber, new FileResource(new File(
						ApplicationBasePath.basePath() + "/WEB-INF/images/" + ecNumber.toUpperCase() + ".png")));
				image.setWidth("200px");
				image.setHeight("200px");

				GridLayout gridLayout = new GridLayout(2, 1, image,
						nametable/*
									 * , contactstable, basix, nextofkeen,
									 * oleveltable, aleveltable, professiontable
									 */);
				gridLayout.setSpacing(true);

				layout1.addComponents(horizontalLayout, gridLayout);
				layout1.setSpacing(true);

			} else {
				new Notification("Error", "No Data", Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		// Panel panel = new Panel();
		// panel.setContent(layout1);
		// VerticalLayout layout2 = new VerticalLayout(panel);
		// layout2.setSpacing(true);
		// UI.getCurrent().addWindow(new Window("Test", layout1));

		return layout1;

	}

	private void clearAndFocusBarcodeField(TextField barcodeField) {
		ClearAndFocusInputField.clearAndFocusBarcodeField(barcodeField);
	}
}