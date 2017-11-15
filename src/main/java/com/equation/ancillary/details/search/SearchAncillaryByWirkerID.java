package com.equation.ancillary.details.search;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.utils.date.CalculateAge;
import com.vaadin.ui.Panel;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("deprecation")
public class SearchAncillaryByWirkerID {

	ResultSet rs, rs1;
	Statement stm, stmt;
	private Table grid;
	private Table grid1;
	private Table grid2;

	public SearchAncillaryByWirkerID(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public Panel createContentPanel(String schoolID, String workerID) {
		Panel panel = new Panel();

		String query = "SELECT acillary.workerID,firstName,surname,gender,dob,nationalID,nationality,dojs,qualification,maritalStatus,phone,cell,email,physicalAddress,postalAddress,other,jobDescription,employmentType FROM schools,ancillaryestablishment,ancillarycontactdetails WHERE schools.schoolID = '"
				+ schoolID + "' AND acillary.workerID='" + workerID + "'";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow(), i = 0;
			if (rows > 0) {
				grid = new Table();
				grid.addContainerProperty("Worker ID", String.class, null);
				grid.addContainerProperty("First Name", String.class, null);
				grid.addContainerProperty("Surname", String.class, null);
				grid.addContainerProperty("Gender", String.class, null);
				grid.addContainerProperty("Age", Integer.class, null);
				grid.addContainerProperty("National ID", String.class, null);
				grid.addContainerProperty("Nationalality", String.class, null);
				grid.addContainerProperty("Date of Joinong Service", String.class, null);
				grid.addContainerProperty("Qaualification", String.class, null);
				grid.addContainerProperty("Marital Status", String.class, null);

				grid1 = new Table();
				grid1.addContainerProperty("Phone", String.class, null);
				grid1.addContainerProperty("Mobile Number", String.class, null);
				grid1.addContainerProperty("Email Address", String.class, null);
				grid1.addContainerProperty("Physical Address", String.class, null);
				grid1.addContainerProperty("Postal Address", String.class, null);
				grid1.addContainerProperty("Other", String.class, null);

				grid2 = new Table();
				grid2.addContainerProperty("Job Description", String.class, null);
				grid2.addContainerProperty("Employment Type", String.class, null);

				String query1 = "SELECT acillary.workerID,firstName,surname,gender,dob,nationalID,nationality,dojs,qualification,maritalStatus,phone,cell,email,physicalAddress,postalAddress,other,jobDescription,salary,employmentType FROM schools,ancillaryestablishment,ancillarycontactdetails WHERE schools.schoolID = '"
						+ schoolID + "' AND acillary.workerID='" + workerID + "'";
				rs = stm.executeQuery(query1);
				while (rs.next()) {

					String workerIDd = rs.getString(1);
					String firstName = rs.getString(2);
					String surname = rs.getString(3);
					String gender = rs.getString(4);
					int age = new CalculateAge().getAge(rs.getString(5));
					String nationalID = rs.getString(6);
					String nationality = rs.getString(7);
					String dojs = rs.getString(8);
					String qualification = rs.getString(9);
					String maritalStatus = rs.getString(10);
					String phone = rs.getString(11);
					String cell = rs.getString(12);
					String email = rs.getString(13);
					String physicalAddress = rs.getString(14);
					String postalAddress = rs.getString(15);
					String other = rs.getString(16);
					String jobDescription = rs.getString(17);
					String employmentType = rs.getString(18);

					grid.addItem(new Object[] { workerIDd, firstName, surname, gender, age, nationalID, nationality,
							dojs, qualification, maritalStatus }, new Integer(i));

					grid1.addItem(new Object[] { phone, cell, email, physicalAddress, postalAddress, other },
							new Integer(i));
					grid2.addItem(new Object[] { jobDescription, employmentType });

					i++;

				}
				grid.setFooterVisible(true);
				grid1.setFooterVisible(true);
				grid2.setFooterVisible(true);

				grid.setWidth("80%");
				grid1.setWidth("80%");
				grid2.setWidth("80%");

				grid.setPageLength(grid.size());
				grid1.setPageLength(grid.size());
				grid2.setPageLength(grid.size());

				grid.addStyleName("students_gender_table");
				grid1.addStyleName("students_gender_table");
				grid2.addStyleName("students_gender_table");

				panel.setContent(grid);

			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		return panel;
	}
}
