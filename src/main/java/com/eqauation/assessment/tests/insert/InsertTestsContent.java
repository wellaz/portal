package com.eqauation.assessment.tests.insert;

import java.sql.SQLException;
import java.sql.Statement;

public class InsertTestsContent {

	public InsertTestsContent() {
		// TODO Auto-generated constructor stub
	}

	public void insertData(Statement stm, String question, String correct_answer, String distractor1,
			String distractor2, String distractor3) {
		String query = "INSERT INTO tests_content(question,correct_answer,distractor1,distractor2,distractor3)VALUES(	'"
				+ question + "','" + correct_answer + "','" + distractor1 + "','" + distractor2 + "','" + distractor3
				+ "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}

	}

}
