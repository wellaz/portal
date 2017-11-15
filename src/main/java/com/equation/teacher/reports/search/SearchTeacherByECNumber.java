package com.equation.teacher.reports.search;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.utils.date.CalculateAge;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.v7.ui.Table;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("deprecation")
public class SearchTeacherByECNumber {

	ResultSet rs, rs1;
	Statement stm, stmt;
	private Table nametable, contactstable, basix, nextofkeen, oleveltable, aleveltable, professiontable;

	public SearchTeacherByECNumber(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public Panel createContentPanel(String schoolID, String ecNumber) {
		Panel panel = new Panel();

		String selectQuery = "SELECT firstName,surname,gender,dateOfBirth,nationalID,nationality,dOJS,maritalStatus,mainSubjects,middleName,otherName,dateOfJoiningSchool FROM teachers,teacherestablishment,schools WHERE teachers.ecNumber = '"
				+ ecNumber + "' AND  schools.schoolID = '" + schoolID + "' OR schools.schoolName = '" + schoolID + "'";

		try {
			rs1 = stmt.executeQuery(selectQuery);
			rs1.last();
			int rows = rs1.getRow();
			if (rows > 0) {
				nametable = new Table("Personal Details");
				nametable.setSelectable(true);
				nametable.addContainerProperty("EC Number", String.class, null);
				nametable.addContainerProperty("First Name", String.class, null);
				nametable.addContainerProperty("Middle Name", String.class, null);
				nametable.addContainerProperty("Surname", String.class, null);
				nametable.addContainerProperty("Other Name", String.class, null);
				nametable.addContainerProperty("Gender", String.class, null);
				nametable.addContainerProperty("Age", Integer.class, null);
				nametable.addContainerProperty("National ID", String.class, null);
				nametable.addContainerProperty("Nationality", String.class, null);
				nametable.addContainerProperty("Marital Status", String.class, null);

				contactstable = new Table("Contact Details");
				contactstable.setSelectable(true);
				contactstable.addContainerProperty("Phone Number", String.class, null);
				contactstable.addContainerProperty("Mobile Number", String.class, null);
				contactstable.addContainerProperty("Email Address", String.class, null);
				contactstable.addContainerProperty("Other Contacts", String.class, null);
				contactstable.addContainerProperty("Physical Address", String.class, null);
				contactstable.addContainerProperty("Postal Address", String.class, null);

				basix = new Table();
				basix.setSelectable(true);
				basix.addContainerProperty("Date Of Joining Service", String.class, null);
				basix.addContainerProperty("Main Subjects", String.class, null);
				basix.addContainerProperty("Date Of Joining School", String.class, null);
				basix.addContainerProperty("Grade Or Subjects", String.class, null);
				basix.addContainerProperty("Period", String.class, null);
				basix.addContainerProperty("Co-curricular", String.class, null);

				nextofkeen = new Table("Next Of Keen");
				nextofkeen.setSelectable(true);
				nextofkeen.addContainerProperty("First Name", String.class, null);
				nextofkeen.addContainerProperty("Surname", String.class, null);
				nextofkeen.addContainerProperty("national ID", String.class, null);
				nextofkeen.addContainerProperty("Mobile Number", String.class, null);
				nextofkeen.addContainerProperty("Email Address", String.class, null);
				nextofkeen.addContainerProperty("Physical Address", String.class, null);
				nextofkeen.addContainerProperty("Other Details", String.class, null);
				nextofkeen.addContainerProperty("Relationship", String.class, null);

				oleveltable = new Table("O'Level Passes");
				oleveltable.setSelectable(true);
				oleveltable.addContainerProperty("Subject", String.class, null);
				oleveltable.addContainerProperty("Symbol", String.class, null);
				oleveltable.addContainerProperty("School", String.class, null);
				oleveltable.addContainerProperty("Year", String.class, null);

				aleveltable = new Table("A'Level Passes");
				aleveltable.setSelectable(true);
				aleveltable.addContainerProperty("Subject", String.class, null);
				aleveltable.addContainerProperty("Symbol", String.class, null);
				aleveltable.addContainerProperty("School", String.class, null);
				aleveltable.addContainerProperty("Year", String.class, null);

				professiontable = new Table("Professional Qualification");
				professiontable.setSelectable(true);
				professiontable.addContainerProperty("Programme", String.class, null);
				professiontable.addContainerProperty("Specilization Area", String.class, null);
				professiontable.addContainerProperty("University Or College", String.class, null);
				professiontable.addContainerProperty("Proficiency", String.class, null);
				professiontable.addContainerProperty("Year", String.class, null);

				String query1 = "SELECT firstName,surname,gender,dateOfBirth,nationalID,nationality,dOJS,maritalStatus,mainSubjects,middleName,otherName,dateOfJoiningSchool FROM teachers,teacherestablishment,schools WHERE teachers.ecNumber = '"
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

					nametable.addItem(new Object[] { ecNumber, firstName, middleName, surname, otherName, gender, age,
							nationalID, nationality, maritalStatus }, 0);
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
					basix.addItem(new Object[] { dOJS, mainSubjects, dateOfJoiningSchool, gradeOrSubjects, period,
							cocuricular }, 0);

					String subQuery2 = "SELECT phone,cell,email,other,physicalAddress,postalAddress FROM teachers,teachercontactdetails,teacherestablishment,schools WHERE teachers.ecNumber = '"
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
					contactstable.addItem(new Object[] { phone, cell, email, other, physicalAddress, postalAddress },
							0);

					String subQuery3 = "SELECT 	kFirstName,kSurname,kNationalID,kCell,kEmail,kAdress,kOther,kRelationship FROM teachers,teacherestablishment,teachernextofkeen,schools WHERE teachers.ecNumber = '"
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
					nextofkeen.addItem(new Object[] { kFirstName, kSurname, kNationalID, kCell, kEmail, kAdress, kOther,
							kRelationship }, 0);

					String subQuery4 = "SELECT subject,symbol,school,year FROM teachers,teacherestablishment,olevelteacheracademicqualifications,schools WHERE teachers.ecNumber = '"
							+ ecNumber + "' AND schools.schoolID = '" + schoolID + "' OR schools.schoolName = '"
							+ schoolID + "'";
					rs1 = stmt.executeQuery(subQuery4);
					int s = 0;
					while (rs1.next()) {
						String subject = rs1.getString(1);
						String symbol = rs1.getString(2);
						String school = rs1.getString(3);
						String year = rs1.getString(4);
						oleveltable.addItem(new Object[] { subject, symbol, school, year }, new Integer(s));
						s++;
					}

					String subQuery5 = "SELECT subject,symbol,school,year FROM teachers,teacherestablishment,alevelteacheracademicqualifications,schools WHERE teachers.ecNumber = '"
							+ ecNumber + "' AND schools.schoolID = '" + schoolID + "' OR schools.schoolName = '"
							+ schoolID + "'";
					rs1 = stmt.executeQuery(subQuery5);
					int q = 0;
					while (rs1.next()) {
						String subject = rs1.getString(1);
						String symbol = rs1.getString(2);
						String school = rs1.getString(3);
						String year = rs1.getString(4);
						aleveltable.addItem(new Object[] { subject, symbol, school, year }, new Integer(q));
						q++;
					}

					String subQuery6 = "SELECT programme,specialisation,universityorcollege,profficiency,year FROM teachers,teacherestablishment,teacherprofessionqualifications,schools WHERE teachers.ecNumber = '"
							+ ecNumber + "' AND schools.schoolID = '" + schoolID + "' OR schools.schoolName = '"
							+ schoolID + "'";
					rs1 = stmt.executeQuery(subQuery6);
					int p = 0;
					while (rs1.next()) {
						String programme = rs1.getString(1), specialisation = rs1.getString(2),
								universityorcollege = rs1.getString(3), profficiency = rs1.getString(4),
								year = rs1.getString(5);
						professiontable.addItem(
								new Object[] { programme, specialisation, universityorcollege, profficiency, year },
								new Integer(p));
						p++;
					}
				}
				nametable.setPageLength(nametable.size());
				contactstable.setPageLength(contactstable.size());
				basix.setPageLength(basix.size());
				nextofkeen.setPageLength(nextofkeen.size());
				oleveltable.setPageLength(oleveltable.size());
				aleveltable.setPageLength(aleveltable.size());
				professiontable.setPageLength(professiontable.size());
				VerticalLayout layout = new VerticalLayout(nametable, contactstable, basix, nextofkeen, oleveltable,
						aleveltable, professiontable);
				layout.setSpacing(true);
				panel.setContent(layout);
			} else {
				new Notification("Warning", "No record found!", Notification.TYPE_WARNING_MESSAGE, true)
						.show(Page.getCurrent());
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return panel;
	}
}