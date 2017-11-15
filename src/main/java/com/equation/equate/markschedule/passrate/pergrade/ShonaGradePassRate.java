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

/**
 * @author Wellington
 *
 */
public class ShonaGradePassRate {
	ResultSet rs, rs1;
	Statement stm, stmt;

	public ShonaGradePassRate(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
	}

	public int getShonapassRate(String class_name, String term, String year) {
		int passMark = new PassMark(rs, rs1, stm, stmt).getPassMark();
		int absent = new GetAbsentStudentsForGrade(rs, stm).getAbsentNumber(class_name, AllSubjects.SHONA);
		String query = "SELECT COUNT(shot) FROM thistermmarks WHERE  shot >= '" + passMark + "' AND class_name LIKE '"
				+ class_name + "%" + "'  AND term = '" + term + "' AND year = '" + year + "'";
		int passrate = 0;
		try {
			rs1 = stmt.executeQuery(query);
			if (rs1.next()) {
				int rows = rs1.getInt(1);
				String query1 = "SELECT shot FROM thistermmarks WHERE class_name LIKE '" + class_name + "%"
						+ "' AND term = '" + term + "' AND year = '" + year + "'";
				rs = stm.executeQuery(query1);
				rs.last();

				int rows1 = rs.getRow() - absent;

				passrate = RoundOff.numbber(((double) rows / rows1) * 100);

			} else {
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		return passrate;

	}

}
