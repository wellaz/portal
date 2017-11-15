package com.equation.teacher.assinment.set;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertOnPageAssignment {
	Statement stm;

	public InsertOnPageAssignment(Statement stm) {
		this.stm = stm;
	}

	public void insertData(int assinment_id, String question, String correct_answer, String distractor1,
			String distractor2, String distractor3) {
		String query = "INSERT INTO assignmentsbank(assinment_id,question,correct_answer,distractor1,distractor2,distractor3)VALUES('"
				+ assinment_id + "','" + question + "','" + correct_answer + "','" + distractor1 + "','" + distractor2
				+ "','" + distractor3 + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}