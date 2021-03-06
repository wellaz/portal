package com.equation.equate.markschedule.overal.perclass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class GetStudentPaperTwoMark {
	Statement stm, stmt;
	ResultSet rs, rs1;

	public GetStudentPaperTwoMark(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;

	}

	public double getStudentMark(String subject, String student_name, String term, String year) {
		double mark = 0;
		String query = "SELECT adjusted_mark FROM paper2markschedule WHERE subject = '" + subject
				+ "' AND student_name = '" + student_name + "' AND term='" + term + "' AND year = '" + year + "' ";
		try {
			rs = stm.executeQuery(query);
			if (rs.next())
				mark = rs.getDouble(1);
			else
				mark = 0;

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		return mark;

	}

}
