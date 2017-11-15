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
public class TeachersOnDutyAndOnSite {
	ResultSet rs, rs1;
	Statement stm, stmt;
	String schoolID;

	public TeachersOnDutyAndOnSite(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, String schoolID) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.schoolID = schoolID;
	}

	public Table onDutyAndOnSiteTable() {
		String status = TeacherStatus.ACTIVE;
		Table table = new Table("TEACHERS ON DUTY EXCLUDING THOSE ON LEAVE");

		String query = "SELECT firstName,surname,gender,teachers.ecNumber,dateOfBirth,dateOfJoiningSchool,dOJS,programme,specialisation,delegation,classname,COUNT(studentID)  FROM teachers,teacherprofessionqualifications,classes,students,schools WHERE teachers.status ='"
				+ status + "' AND students.classID = classes.classname AND schools.schoolID = '" + schoolID
				+ "' GROUP BY classes.classID,firstName,surname,gender,teachers.ecNumber,programme,specialisation,delegation";

		table.addContainerProperty("NUMBER", Integer.class, null);
		table.addContainerProperty("NAME OF TEACHER", String.class, null);
		table.addContainerProperty("EC NUMBER", String.class, null);
		table.addContainerProperty("GENDER", String.class, null);
		table.addContainerProperty("D.O.B", String.class, null);
		table.addContainerProperty("D.O.A", String.class, null);
		table.addContainerProperty("D.O.J.S", String.class, null);
		table.addContainerProperty("QUALIFICATIONS", String.class, null);
		table.addContainerProperty("STATUS", String.class, null);
		table.addContainerProperty("CLASS / GRADE", String.class, null);
		table.addContainerProperty("ENROLL", Integer.class, null);
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow(), i = 0;
			if (rows > 0) {

				String query1 = "SELECT firstName,surname,gender,teachers.ecNumber,dateOfBirth,dateOfJoiningSchool,dOJS,programme,specialisation,delegation,classname,COUNT(studentID)  FROM teachers,teacherprofessionqualifications,classes,students,schools WHERE teachers.status ='"
						+ status + "' AND students.classID = classes.classname AND schools.schoolID = '" + schoolID
						+ "' GROUP BY classes.classID,firstName,surname,gender,teachers.ecNumber,programme,specialisation,delegation";

				rs = stm.executeQuery(query1);
				while (rs.next()) {
					String name = rs.getString(1) + " " + rs.getString(2);
					String gender = rs.getString(3);
					String ec = rs.getString(4);
					String dateOfBirth = rs.getString(5);
					String dateOfJoiningSchool = rs.getString(6);
					String dOJS = rs.getString(7);
					String programme = rs.getString(8);
					String specialisation = rs.getString(9);
					String delegation = rs.getString(10);
					String classname = rs.getString(11);
					int enroll = rs.getInt(12);

					table.addItem(
							new Object[] { (i + 1), name, gender, ec, dateOfBirth, dateOfJoiningSchool, dOJS,
									(programme + " " + specialisation), delegation, classname, enroll },
							new Integer(i));
					i++;

				}

			} else {
				table.addItem(new Object[] { 1, "", "", "", "", "", "", "", "", "", "" }, 0);
				table.addItem(new Object[] { 2, "", "", "", "", "", "", "", "", "", "" }, 1);
				table.addItem(new Object[] { 3, "", "", "", "", "", "", "", "", "", "" }, 2);
				table.addItem(new Object[] { 4, "", "", "", "", "", "", "", "", "", "" }, 3);
			}
			table.setFooterVisible(true);
			table.setColumnCollapsingAllowed(true);
			int size = table.size();
			String narration = (size > 1) ? " Records" : " Record";
			table.setPageLength(size);
			table.setSelectable(true);
			table.setColumnFooter("NUMBER", String.valueOf(size) + narration);
			table.setSizeFull();

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		return table;
	}
}