package com.equation.school.statistics.monthly;

import java.sql.ResultSet;
import java.sql.Statement;

import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("deprecation")
public class GhostTeahchers {
	ResultSet rs, rs1;
	Statement stm, stmt;
	String schoolID;

	public GhostTeahchers(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, String schoolID) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.schoolID = schoolID;
	}

	public Table ghostTeachersTable() {
		// String status = TeacherStatus.TRANSFERRED;
		Table table = new Table("TEACHERS APPEARING ON STATION FROM NOWHERE (GHOST TEACHERS)");

		table.addContainerProperty("NUMBER", Integer.class, null);
		table.addContainerProperty("NAMES OF TEACHERS", String.class, null);
		table.addContainerProperty("EC NUMBER", String.class, null);
		table.addContainerProperty("ACTION TAKEN BY HEAD", String.class, null);

		String name = "", ec = "", action = "";
		table.addItem(new Object[] { 1, name, ec, action }, 0);
		table.addItem(new Object[] { 2, name, ec, action }, 1);
		table.addItem(new Object[] { 3, name, ec, action }, 2);
		table.addItem(new Object[] { 4, name, ec, action }, 3);
		table.setFooterVisible(true);
		table.setColumnCollapsingAllowed(true);
		int size = table.size();
		table.setPageLength(size);
		table.setSelectable(true);
		table.setSizeFull();

		return table;
	}

}
