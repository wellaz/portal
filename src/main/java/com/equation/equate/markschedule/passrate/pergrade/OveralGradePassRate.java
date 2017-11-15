/**
 *
 * @author Wellington
 */
package com.equation.equate.markschedule.passrate.pergrade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.equate.absent.GetAbsentStudentsForGrade;
import com.equation.equate.settings.passmark.PassMark;
import com.equation.equate.utils.roundoff.RoundOff;
import com.equation.equate.utils.subjects.AllSubjects;
import com.vaadin.v7.ui.Table;

/**
 * @author Wellington
 *
 */
@SuppressWarnings("deprecation")
public class OveralGradePassRate {

	ResultSet rs, rs1;
	Statement stm, stmt;

	public OveralGradePassRate(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;

	}

	public Table createPassrateTable(String class_name, String term, String year) {
		Table table = new Table("Overall Pass rate");
		table.addContainerProperty("OVERALL PASS RATE", String.class, null);
		table.addContainerProperty("PERCENTAGE %", String.class, null);
		table.setSizeFull();

		int passMark = new PassMark(rs, rs1, stm, stmt).getPassMark();
		int absent1 = new GetAbsentStudentsForGrade(rs, stm).getAbsentNumber(class_name, AllSubjects.ENGLISH);
		int absent2 = new GetAbsentStudentsForGrade(rs, stm).getAbsentNumber(class_name, AllSubjects.SHONA);
		int absent3 = new GetAbsentStudentsForGrade(rs, stm).getAbsentNumber(class_name, AllSubjects.MATHEMATICS);
		int absent4 = new GetAbsentStudentsForGrade(rs, stm).getAbsentNumber(class_name, AllSubjects.GENERAL_PAPER);
		int absent5 = new GetAbsentStudentsForGrade(rs, stm).getAbsentNumber(class_name, AllSubjects.AGRICULTURE);

		String query = "SELECT matht,engt,shot,gent,agt FROM thistermmarks WHERE class_name LIKE '" + class_name + "%"
				+ "' AND matht >= '" + passMark + "' AND engt >= '" + passMark + "' AND shot >= '" + passMark
				+ "' AND gent >= '" + passMark + "' AND agt >= '" + passMark + "'  AND term = '" + term
				+ "' AND year = '" + year + "'";

		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow();
			if (rows > 0) {
				String query1 = "SELECT matht,engt,shot,gent,agt FROM thistermmarks WHERE class_name LIKE '"
						+ class_name + "%" + "'  AND term = '" + term + "' AND year = '" + year + "'";
				rs = stm.executeQuery(query1);
				rs.last();

				int rows1 = rs.getRow() - (absent1 + absent2 + absent3 + absent4 + absent5);

				int passrate = RoundOff.numbber(((double) rows / rows1) * 100);

				table.addItem(new Object[] { "-", String.valueOf(passrate) }, 0);

			} else {
				table.addItem(new Object[] { "-", "0" }, 0);
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		table.setPageLength(table.size());
		table.setColumnCollapsingAllowed(true);
		table.setSelectable(true);
		table.setFooterVisible(true);

		return table;

	}
}