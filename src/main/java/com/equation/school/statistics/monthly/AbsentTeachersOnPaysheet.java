package com.equation.school.statistics.monthly;

import java.sql.ResultSet;
import java.sql.Statement;

import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("deprecation")
public class AbsentTeachersOnPaysheet {
	ResultSet rs, rs1;
	Statement stm, stmt;
	String schoolID;

	public AbsentTeachersOnPaysheet(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, String schoolID) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.schoolID = schoolID;
	}

	public Table absentAndOnPaysheet() {
		// String status = TeacherStatus.TRANSFERRED;
		Table table = new Table("TEACHERS WHO LEFT THE SCHOOL BUT STILL APPEARING ON PAYSHEET");

		table.addContainerProperty("NUMBER", Integer.class, null);
		table.addContainerProperty("NAMES OF TEACHERS", String.class, null);
		table.addContainerProperty("EC NUMBER", String.class, null);
		table.addContainerProperty("DATE LEFT", String.class, null);
		table.addContainerProperty("FROM", String.class, null);
		table.addContainerProperty("TO", String.class, null);
		table.addContainerProperty("TYPE", String.class, null);
		table.addContainerProperty("REPLACEMENT NAME", String.class, null);
		table.addContainerProperty("REPLACEMENT EC NUMBER", String.class, null);

		String name = "", ec = "", dateLeft = "", from = "", to = "", type = "", replacement = "", ecNumber = "";
		table.addItem(new Object[] { 1, name, ec, dateLeft, from, to, type, replacement, ecNumber }, 0);
		table.addItem(new Object[] { 2, name, ec, dateLeft, from, to, type, replacement, ecNumber }, 1);
		table.addItem(new Object[] { 3, name, ec, dateLeft, from, to, type, replacement, ecNumber }, 2);
		table.addItem(new Object[] { 4, name, ec, dateLeft, from, to, type, replacement, ecNumber }, 3);
		table.setFooterVisible(true);
		table.setColumnCollapsingAllowed(true);
		int size = table.size();
		table.setPageLength(size);
		table.setSelectable(true);
		table.setSizeFull();
		return table;
	}
}