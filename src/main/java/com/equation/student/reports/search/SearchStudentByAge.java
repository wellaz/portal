package com.equation.student.reports.search;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.utils.date.CalculateAge;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("deprecation")
public class SearchStudentByAge {
	ResultSet rs, rs1;
	Statement stm, stmt;
	private Table grid;
	CalculateAge calculateAge;

	public SearchStudentByAge(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		calculateAge = new CalculateAge();
	}

	public Panel createContentPanel(String schoolID, int minage, int maxage) {
		Panel panel = new Panel();
		String query = "SELECT studentID,sFirstName,classname,sBirthDate,sgender,pSurname,pCell,pEmail,pAdress FROM students,parents,classes,schoolhouses,schools WHERE schools.schoolID = '"
				+ schoolID + "' OR schools.schoolName = '" + schoolID + "'";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow(), i = 0;
			if (rows > 0) {
				String query1 = "SELECT studentID,sFirstName,sSurname,classname,sBirthDate,sgender,pSurname,pCell,pEmail,pAdress FROM students,parents,classes,schoolhouses,schools WHERE schools.schoolID = '"
						+ schoolID + "' OR schools.schoolName = '" + schoolID + "'";
				rs = stm.executeQuery(query1);

				grid = new Table();
				grid.addContainerProperty("Student ID", String.class, null);
				grid.addContainerProperty("Age", Integer.class, null);
				grid.addContainerProperty("Student Name", String.class, null);
				grid.addContainerProperty("Class Name", String.class, null);
				grid.addContainerProperty("Gender", String.class, null);
				grid.addContainerProperty("Parent Name", String.class, null);
				grid.addContainerProperty("Parent Contacts", String.class, null);
				grid.addContainerProperty("Home Address", String.class, null);
				grid.setSelectable(true);
				while (rs.next()) {
					String dob = rs.getString(5);
					int calculatedage = calculateAge.getAge(dob);
					if (calculatedage >= minage) {
						if (calculatedage <= maxage) {
							String studentID = rs.getString(1);
							String studentName = rs.getString(2) + " " + rs.getString(3);
							String classname = rs.getString(4);
							String sgender = rs.getString(6);
							String parentname = rs.getString(7);
							String parentcontacts = "Cell:" + rs.getString(8) + " Email:" + rs.getString(9);
							String parentAddress = rs.getString(10);

							grid.addItem(new Object[] { studentID, calculatedage, studentName, classname, sgender,
									parentname, parentcontacts, parentAddress }, new Integer(i));
						}
					}
					i++;
				}
				grid.setCaption("Ages Between " + minage + " and " + maxage);
				grid.setFooterVisible(true);
				grid.setColumnFooter("Student ID", "Total");
				grid.setColumnFooter("Student Name", String.valueOf(rows));
				grid.setWidth("80%");
				grid.setPageLength(grid.size());
				grid.addStyleName("students_gender_table");
				panel.setContent(grid);
			} else {
				panel.setContent(new Label("No Data"));
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		return panel;
	}

}
