/**
 *
 * @author Wellington
 */
package com.equation.equate.markschedule.passrate.perclass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.v7.ui.Table;

/**
 * @author Wellington
 *
 */
@SuppressWarnings("deprecation")
public class SubjectsPassrate {

	ResultSet rs, rs1;
	Statement stm, stmt;

	public SubjectsPassrate(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;

	}

	public Table createSubjectsPassrate(String class_name, String term, String year, String date) {
		Table table = new Table("Subjects Pass Rate");
		table.addContainerProperty("SUBJECT", String.class, null);
		table.addContainerProperty("PASS RATE", String.class, null);
		table.setSizeFull();

		int maths = new MathsPassrate(rs, rs1, stm, stmt).getMathspassRate(class_name, term, year);
		int english = new EnglishPassrate(rs, rs1, stm, stmt).getEnglishpassRate(class_name, term, year);
		int shona = new ShonaPassRate(rs, rs1, stm, stmt).getShonapassRate(class_name, term, year);
		int gp = new GPPassrate(rs, rs1, stm, stmt).getGPpassRate(class_name, term, year);
		int agric = new AgricPassrate(rs, rs1, stm, stmt).getAgricpassRate(class_name, term, year);
		System.out.println(agric);

		table.addItem(new Object[] { "ENGLISH", String.valueOf(english) }, 0);
		table.addItem(new Object[] { "SHONA", String.valueOf(shona) }, 1);
		table.addItem(new Object[] { "MATHEMATICS", String.valueOf(maths) }, 2);
		table.addItem(new Object[] { "GENERAL PAPER", String.valueOf(gp) }, 3);
		table.addItem(new Object[] { "AGRICULTURE", String.valueOf(agric) }, 4);

		InsertClassPassrateData Passrate = new InsertClassPassrateData();
		String query = "SELECT class_name FROM classes_passrates WHERE class_name = '" + class_name + "' AND term = '"
				+ term + " AND year = '" + year + "''";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow();
			if (rows > 0) {

			} else {
				Passrate.insertData(stm, class_name, "ENGLISH", english, term, year, date);
				Passrate.insertData(stm, class_name, "SHONA", shona, term, year, date);
				Passrate.insertData(stm, class_name, "MATHEMATICS", maths, term, year, date);
				Passrate.insertData(stm, class_name, "GENERAL PAPER", gp, term, year, date);
				Passrate.insertData(stm, class_name, "AGRICULTURE", agric, term, year, date);
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		table.setPageLength(table.size());
		table.setColumnCollapsingAllowed(true);
		table.setFooterVisible(true);

		return table;

	}

}
