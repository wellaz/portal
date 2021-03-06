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
public class SearchStudentsByGender {

	ResultSet rs, rs1;
	Statement stm, stmt;
	private Table grid;

	public SearchStudentsByGender(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public Panel createConentPanel(String schoolID, String gender) {
		Panel panel = new Panel();

		String query = "SELECT 	studentID,sFirstName,sSurname,classname,sgender,pSurname FROM students,parents,classes,schools WHERE students.sgender = '"
				+ gender + "' AND schools.schoolID = '" + schoolID + "' OR schools.schoolName = '" + schoolID + "'";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow(), i = 0;
			if (rows > 0) {
				String query1 = "SELECT 	studentID,sFirstName,sSurname,classname,sgender,pSurname FROM students,parents,classes,schools WHERE students.sgender = '"
						+ gender + "' AND schools.schoolID = '" + schoolID + "' OR schools.schoolName = '" + schoolID
						+ "'";
				rs = stm.executeQuery(query1);

				grid = new Table("Students details By Gender");
				grid.addContainerProperty("Student ID", String.class, null);
				grid.addContainerProperty("Student Name", String.class, null);
				grid.addContainerProperty("Class Name", String.class, null);
				grid.addContainerProperty("Gender", String.class, null);
				grid.addContainerProperty("Parent Name", String.class, null);
				// grid.setSizeFull();
				grid.setSelectable(true);
				while (rs.next()) {
					String studentID = rs.getString(1);
					String studentName = rs.getString(2) + " " + rs.getString(3);
					String classname = rs.getString(4);
					String sgender = rs.getString(5);
					String parent = rs.getString(6);

					grid.addItem(new Object[] { studentID, studentName, classname, sgender, parent }, new Integer(i));
					i++;
				}
				grid.setFooterVisible(true);
				grid.setColumnFooter("Student ID", "Total");
				grid.setColumnFooter("Student Name", String.valueOf(rows));
				grid.setWidth("80%");
				grid.setPageLength(grid.size());
				grid.addStyleName("students_gender_table");
				panel.setContent(grid);
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		return panel;
	}

}
