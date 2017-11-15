package com.equation.teacher.multiplechoice.records;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertMultipleChoiceQuestion {
	Statement stm;

	public InsertMultipleChoiceQuestion(Statement stm) {
		this.stm = stm;
	}

	public void insertData(int test_id, String question, String correct_answer, String distractor1, String distractor2,
			String distractor3) {
		String query = "INSERT INTO inclasstestbank(test_id,question,correct_answer,distractor1,distractor2,distractor3)VALUES('"
				+ test_id + "','" + question + "','" + correct_answer + "','" + distractor1 + "','" + distractor2
				+ "','" + distractor3 + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}