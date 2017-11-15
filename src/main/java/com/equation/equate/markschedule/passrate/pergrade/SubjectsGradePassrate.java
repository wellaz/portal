/**
 *
 * @author Wellington
 */
package com.equation.equate.markschedule.passrate.pergrade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.v7.ui.Table;

/**
 * @author Wellington
 *
 */
@SuppressWarnings("deprecation")
public class SubjectsGradePassrate {

	ResultSet rs, rs1;
	Statement stm, stmt;

	public SubjectsGradePassrate(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
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

		int maths = new MathsGradePassrate(rs, rs1, stm, stmt).getMathspassRate(class_name, term, year);
		int english = new EnglishGradePassrate(rs, rs1, stm, stmt).getEnglishpassRate(class_name, term, year);
		int shona = new ShonaGradePassRate(rs, rs1, stm, stmt).getShonapassRate(class_name, term, year);
		int gp = new GPGradePassrate(rs, rs1, stm, stmt).getGPpassRate(class_name, term, year);
		int agric = new AgricGradePassrate(rs, rs1, stm, stmt).getAgricpassRate(class_name, term, year);
		System.out.println(agric);

		table.addItem(new Object[] { "ENGLISH", String.valueOf(english) }, 0);
		table.addItem(new Object[] { "SHONA", String.valueOf(shona) }, 1);
		table.addItem(new Object[] { "MATHEMATICS", String.valueOf(maths) }, 2);
		table.addItem(new Object[] { "GENERAL PAPER", String.valueOf(gp) }, 3);
		table.addItem(new Object[] { "AGRICULTURE", String.valueOf(agric) }, 4);

		InsertGradePassrate gradePassrate = new InsertGradePassrate();

		String query = "SELECT grade FROM grades_passrates WHERE grade = '" + class_name + "'";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow();
			if (rows > 0) {

			} else {
				gradePassrate.insertData(stm, class_name, "ENGLISH", english, term, year, date);
				gradePassrate.insertData(stm, class_name, "SHONA", shona, term, year, date);
				gradePassrate.insertData(stm, class_name, "MATHEMATICS", maths, term, year, date);
				gradePassrate.insertData(stm, class_name, "GENERAL PAPER", gp, term, year, date);
				gradePassrate.insertData(stm, class_name, "AGRICULTURE", agric, term, year, date);
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
