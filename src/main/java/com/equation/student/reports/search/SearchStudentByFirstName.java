package com.equation.student.reports.search;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.ui.Panel;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("deprecation")
public class SearchStudentByFirstName {

	ResultSet rs, rs1;
	Statement stm, stmt;
	private Table grid;

	public SearchStudentByFirstName(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public Panel createContentPanel(String schoolID, String first_name) {
		Panel panel = new Panel();
		String query = "SELECT 	studentID,sSurname,classname,sgender,pSurname FROM students,parents,classes,schools WHERE students.sFirstName = '"
				+ first_name + "' AND schools.schoolID = '" + schoolID + "' OR schools.schoolName = '" + schoolID + "'";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow(), i = 0;
			if (rows > 0) {
				String query1 = "SELECT studentID,sSurname,classname,sgender,pSurname,pCell,pEmail,pAdress FROM students,parents,classes,schools WHERE students.sFirstName = '"
						+ first_name + "' AND schools.schoolID = '" + schoolID + "' OR schools.schoolName = '"
						+ schoolID + "' ";
				rs = stm.executeQuery(query1);

				grid = new Table("Students details By Gender");
				grid.addContainerProperty("Student ID", String.class, null);
				grid.addContainerProperty("Student Name", String.class, null);
				grid.addContainerProperty("Class Name", String.class, null);
				grid.addContainerProperty("Gender", String.class, null);
				grid.addContainerProperty("Parent Name", String.class, null);
				grid.addContainerProperty("Parent Contacts", String.class, null);
				grid.addContainerProperty("Home Address", String.class, null);
				// grid.setSizeFull();
				grid.setSelectable(true);
				while (rs.next()) {
					String studentID = rs.getString(1);
					String studentName = first_name + " " + rs.getString(2);
					String classname = rs.getString(3);
					String sgender = rs.getString(4);
					String parentname = rs.getString(5);
					String parentcontacts = "Cell:" + rs.getString(6) + " Email:" + rs.getString(7);
					String parentAddress = rs.getString(8);

					grid.addItem(new Object[] { studentID, studentName, classname, sgender, parentname, parentcontacts,
							parentAddress }, new Integer(i));
					i++;
				}
				grid.setFooterVisible(true);
				grid.setColumnFooter("Student ID", "Total");
				grid.setColumnFooter("Student Name", String.valueOf(rows));
				grid.setWidth("80%");
				grid.setPageLength(grid.size());
				grid.addStyleName("students_studentID_table");
				panel.setContent(grid);
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		return panel;
	}

}
