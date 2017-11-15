package com.equation.teacher.insertquestions;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoSubjectTable {
	Statement stm;

	public InsertDataIntoSubjectTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String subjectname, String question, String correctAnswer, String destractor1,
			String destractor2, String destractor3, String grade, String subject_id) {
		String query = "INSERT INTO questions_pool(question,correctAnswer,destractor1,destractor2,destractor3,grade,subject_id)VALUES('"
				+ question + "','" + correctAnswer + "','" + destractor1 + "','" + destractor2 + "','" + destractor3
				+ "','" + grade + "','" + subject_id + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}

}
