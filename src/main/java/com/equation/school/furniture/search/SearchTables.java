package com.equation.school.furniture.search;

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
public class SearchTables {

	ResultSet rs, rs1;
	Statement stm, stmt;
	private Table grid;

	public SearchTables(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public Panel createContentPanel(String schoolname, String type) {
		Panel panel = new Panel();

		String query = "SELECT schoolName,type,quantity,status FROM furniture,schools WHERE schools.schoolName = '"
				+ schoolname + "' OR schools.schoolID = '" + schoolname + "' AND furniture.type = '" + type + "'";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow(), i = 0;
			if (rows > 0) {

				String query1 = "SELECT schoolName,type,quantity,status FROM furniture,schools WHERE schools.schoolName = '"
						+ schoolname + "' OR schools.schoolID = '" + schoolname + "' AND furniture.type = '" + type
						+ "'";
				rs = stm.executeQuery(query1);
				grid = new Table();
				grid.addContainerProperty("School", String.class, null);
				grid.addContainerProperty("Furniture", String.class, null);
				grid.addContainerProperty("Quantity", Integer.class, null);
				grid.addContainerProperty("Status", String.class, null);

				while (rs.next()) {
					String schoolnamee = rs.getString(1);
					String typee = rs.getString(2);
					String quantity = rs.getString(3);
					String status = rs.getString(4);

					grid.addItem(new Object[] { schoolnamee, typee, quantity, status }, new Integer(i));
					i++;

				}
				grid.setFooterVisible(true);
				grid.setColumnFooter("School", "Total");
				grid.setColumnFooter("Furniture", String.valueOf(rows));
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
