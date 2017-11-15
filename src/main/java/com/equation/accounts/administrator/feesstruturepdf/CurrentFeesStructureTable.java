package com.equation.accounts.administrator.feesstruturepdf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("deprecation")
public class CurrentFeesStructureTable {

	ResultSet rs, rs1;
	Statement stm, stmt;
	String schoolID;

	public CurrentFeesStructureTable(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, String schoolID) {
		this.rs1 = rs1;
		this.rs = rs;
		this.stm = stm;
		this.stmt = stmt;
		this.schoolID = schoolID;
	}

	public Table createCurrentFeesStructureTable(String year) {
		Table grid = null;
		String query = "SELECT admin,tution,bspz,building,generalPurpose,sports,levi,other,total,dateSet,year FROM feestructure,schools WHERE year = '"
				+ year + "'";
		try {
			this.rs1 = this.stmt.executeQuery(query);
			this.rs1.last();
			int rows = this.rs1.getRow();
			if (rows > 0) {

				grid = new Table();
				grid.addContainerProperty("DESCRIPTION", String.class, null);
				grid.addContainerProperty("AMOUNT ($)", Double.class, null);

				grid.setSelectable(true);

				String query1 = "SELECT admin,tution,bspz,building,generalPurpose,sports,levi,other,total,dateSet,year FROM feestructure,schools WHERE year = '"
						+ year + "'";
				this.rs = this.stm.executeQuery(query1);
				if (this.rs.next()) {
					double admin = this.rs.getDouble(1);
					double tution = this.rs.getDouble(2);
					double bspz = this.rs.getDouble(3);
					double building = this.rs.getDouble(4);
					double generalPurpose = this.rs.getDouble(5);
					double sports = this.rs.getDouble(6);
					double levi = this.rs.getDouble(7);
					double other = this.rs.getDouble(8);
					double total = this.rs.getDouble(9);
					// String dateSet = this.rs.getString(10);
					// int yearr = this.rs.getInt(11);

					grid.addItem(new Object[] { "Administration", admin }, 0);
					grid.addItem(new Object[] { "Tuition", tution }, 1);
					grid.addItem(new Object[] { "B S P Z", bspz }, 2);
					grid.addItem(new Object[] { "Building", building }, 3);
					grid.addItem(new Object[] { "General Purpose", generalPurpose }, 4);
					grid.addItem(new Object[] { "Sports", sports }, 5);
					grid.addItem(new Object[] { "Levy", levi }, 6);
					grid.addItem(new Object[] { "Other", other }, 7);
					grid.addItem(new Object[] { "TOTAL", total }, 8);

				}
				int size = grid.size();
				String ret = (size > 1) ? " Records" : " Record";
				grid.setColumnCollapsingAllowed(true);
				grid.setFooterVisible(true);
				grid.setColumnFooter("DESCRIPTION", String.valueOf(size) + ret);
				grid.setSizeFull();
				grid.setPageLength(grid.size());
				grid.addStyleName("students_gender_table");

			} else {
				new Notification("Error", "No Fees Structure Set for year " + year, Notification.TYPE_ERROR_MESSAGE,
						true).show(Page.getCurrent());
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return grid;
	}
}