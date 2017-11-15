package com.equation.teacher.reports.search;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.utils.date.CalculateAge;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("deprecation")
public class SearchTeacherBySurname {

	ResultSet rs, rs1;
	Statement stm, stmt;
	private Table grid;

	public SearchTeacherBySurname(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public Panel createContetPanel(String schoolID, String surname) {
		Panel panel = new Panel();

		String selectQuery = "SELECT firstName,surname,gender,dateOfBirth,teachers.ecNumber,nationalID,nationality,dOJS,maritalStatus,mainSubjects,middleName,otherName,dateOfJoiningSchool FROM teachers,teacherestablishment,schools WHERE  teachers.surname = '"
				+ surname + "' AND schools.schoolID = '" + schoolID + "' OR schools.schoolName = '" + schoolID + "'";
		try {
			rs1 = stmt.executeQuery(selectQuery);
			rs1.last();
			int rows = rs1.getRow(), i = 0;
			if (rows > 0) {
				grid = new Table();
				grid.addContainerProperty("EC Number", String.class, null);
				grid.addContainerProperty("First Name", String.class, null);
				grid.addContainerProperty("Middle Name", String.class, null);
				grid.addContainerProperty("Surname", String.class, null);
				grid.addContainerProperty("Other Name", String.class, null);
				grid.addContainerProperty("Gender", String.class, null);
				grid.addContainerProperty("Age", Integer.class, null);
				grid.addContainerProperty("National ID", String.class, null);
				grid.addContainerProperty("Nationality", String.class, null);
				grid.addContainerProperty("Date Of Joining Service", String.class, null);
				grid.addContainerProperty("Marital Status", String.class, null);
				grid.addContainerProperty("Main Subjects", String.class, null);
				grid.addContainerProperty("Date Of Joining School", String.class, null);
				grid.addContainerProperty("Period", Integer.class, null);
				// grid.setSizeFull();
				grid.setSelectable(true);
				String selectQuery1 = "SELECT firstName,surname,gender,dateOfBirth,teachers.ecNumber,nationalID,nationality,dOJS,maritalStatus,mainSubjects,middleName,otherName,dateOfJoiningSchool FROM teachers,teacherestablishment,schools WHERE teachers.surname = '"
						+ surname + "' AND schools.schoolID = '" + schoolID + "' OR schools.schoolName = '" + schoolID
						+ "'";
				rs = stm.executeQuery(selectQuery1);
				while (rs.next()) {
					String firstName = rs.getString(1);
					String surnamee = rs.getString(2);
					String genderr = rs.getString(3);
					String dateOfBirth = rs.getString(4);
					String ecNumber = rs.getString(5);
					String nationalID = rs.getString(6);
					String nationality = rs.getString(7);
					String dOJS = rs.getString(8);
					String maritalStatus = rs.getString(9);
					String mainSubjects = rs.getString(10);
					String middleName = rs.getString(11);
					String otherName = rs.getString(12);
					String dateOfJoiningSchool = rs.getString(13);

					int age = new CalculateAge().getAge(dateOfBirth);
					int period = new CalculateAge().getAge(dateOfJoiningSchool);
					grid.addItem(new Object[] { ecNumber, firstName, middleName, surnamee, otherName, genderr, age,
							nationalID, nationality, dOJS, maritalStatus, mainSubjects, dateOfJoiningSchool, period },
							new Integer(i));
					i++;
				}
				grid.setFooterVisible(true);
				grid.setColumnFooter("EC Number", "Total");
				grid.setColumnFooter("First Name", String.valueOf(rows));
				grid.setWidth("80%");
				grid.setPageLength(grid.size());
				grid.addStyleName("students_gender_table");
				panel.setContent(grid);

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
