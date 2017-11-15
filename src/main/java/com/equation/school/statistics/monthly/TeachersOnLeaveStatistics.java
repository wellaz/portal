package com.equation.school.statistics.monthly;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.system.users.teacher.getname.FetchStuffMemberName;
import com.equation.teacher.status.TeacherStatus;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("deprecation")
public class TeachersOnLeaveStatistics {
	ResultSet rs, rs1;
	Statement stm, stmt;
	String schoolID;

	public TeachersOnLeaveStatistics(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, String schoolID) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.schoolID = schoolID;
	}

	public Table onLeaveTable() {
		String status = TeacherStatus.ON_LEAVE;
		Table table = new Table("TEACHERS ON LEAVE");
		String query = "SELECT firstName,surname,gender,teachers.ecNumber, fromDate,toDate,replacement,leaveType FROM teachers,teachersonleave,schools WHERE status = '"
				+ status + "' AND schools.schoolID = '" + schoolID + "'";
		table.addContainerProperty("NUMBER", Integer.class, null);
		table.addContainerProperty("NAME OF TEACHER", String.class, null);
		table.addContainerProperty("EC NUMBER", String.class, null);
		table.addContainerProperty("GENDER", String.class, null);
		table.addContainerProperty("FROM (Date)", String.class, null);
		table.addContainerProperty("TO (Date)", String.class, null);
		table.addContainerProperty("TYPE OF LEAVE", String.class, null);
		table.addContainerProperty("NAME AND EC NUMBER OF REPLACEMENT", String.class, null);
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow(), i = 0;
			if (rows > 0) {
				String query1 = "SELECT firstName,surname,gender,teachers.ecNumber, fromDate,toDate,replacement,leaveType FROM teachers,teachersonleave,schools WHERE status = '"
						+ status + "' AND schools.schoolID = '" + schoolID + "'";
				rs = stm.executeQuery(query1);
				while (rs.next()) {
					String name = rs.getString(1) + " " + rs.getString(2);
					String gender = rs.getString(3);
					String ec = rs.getString(4);
					String from = rs.getString(5);
					String to = rs.getString(6);
					String replacement = rs.getString(7);
					String leaveType = rs.getString(8);

					String replacementname = new FetchStuffMemberName(rs, rs1, stm, stmt).getActualName(replacement);

					table.addItem(new Object[] { (i + 1), name, ec, gender, from, to, leaveType, replacementname },
							new Integer(i));
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