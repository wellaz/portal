package com.equation.school.statistics.monthly;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.teacher.status.TeacherStatus;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("deprecation")
public class TeacherOnLeaveAndOnSite {
	ResultSet rs, rs1;
	Statement stm, stmt;
	String schoolID;

	public TeacherOnLeaveAndOnSite(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, String schoolID) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.schoolID = schoolID;
	}

	public Table onSiteAndOffSiteTable() {
		String status = TeacherStatus.TRANSFERRED;
		Table table = new Table("BONAFIDE TEACHERS EMPLOYED  AND STATIONED AT THE SCHOOL");

		String query = "SELECT firstName,surname,teachers.ecNumber,programme,specialisation,classname,COUNT(studentID)  FROM teachers,teacherprofessionqualifications,classes,students,schools WHERE teachers.status <> '"
				+ status + "' AND students.classID = classes.classname AND schools.schoolID = '" + schoolID
				+ "' GROUP BY classes.classID,firstName,surname,teachers.ecNumber,programme,specialisation";

		table.addContainerProperty("NUMBER", Integer.class, null);
		table.addContainerProperty("NAMES OF TEACHERS", String.class, null);
		table.addContainerProperty("EC NUMBER", String.class, null);
		table.addContainerProperty("QUALIFICATIONS", String.class, null);
		table.addContainerProperty("CLASS / SUBJECTS TAUGHT", String.class, null);
		table.addContainerProperty("CLASS ENROLLMENT", Integer.class, null);
		table.addContainerProperty("TEACHERS' SIGNATURE", String.class, null);
		table.addContainerProperty("PAID / NOT PAID", String.class, null);
		int totalStudents = 0;
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow(), i = 0;
			if (rows > 0) {

				String query1 = "SELECT firstName,surname,teachers.ecNumber,programme,specialisation,classname,COUNT(studentID)  FROM teachers,teacherprofessionqualifications,classes,students,schools WHERE teachers.status <> '"
						+ status + "' AND students.classID = classes.classname AND schools.schoolID = '" + schoolID
						+ "' GROUP BY classes.classID,firstName,surname,teachers.ecNumber,programme,specialisation";

				rs = stm.executeQuery(query1);
				while (rs.next()) {
					String name = rs.getString(1) + " " + rs.getString(2);
					String ec = rs.getString(3);
					String programme = rs.getString(4);
					String specialisation = rs.getString(5);
					String classname = rs.getString(6);
					int enroll = rs.getInt(7);
					totalStudents += enroll;

					table.addItem(new Object[] { (i + 1), name, ec, (programme + " " + specialisation), classname,
							enroll, "", "" }, new Integer(i));
					i++;

				}
			} else {
				table.addItem(new Object[] { 1, "", "", "", "", "", "", "" }, 0);
				table.addItem(new Object[] { 2, "", "", "", "", "", "", "" }, 1);
				table.addItem(new Object[] { 3, "", "", "", "", "", "", "" }, 2);
				table.addItem(new Object[] { 4, "", "", "", "", "", "", "" }, 3);
			}
			table.setFooterVisible(true);
			table.setColumnCollapsingAllowed(true);
			int size = table.size();
			table.setPageLength(size);
			table.setSelectable(true);
			table.setColumnFooter("NUMBER", "TOTAL NUMBER OF TEACHERS: " + String.valueOf(size));
			table.setColumnFooter("CLASS ENROLLMENT", "TOTAL NUMBER OF STUDENTS: " + String.valueOf(totalStudents));
			table.setSizeFull();
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return table;
	}
}