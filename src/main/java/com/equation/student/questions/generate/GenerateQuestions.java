package com.equation.student.questions.generate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class GenerateQuestions {
	ResultSet rs;
	Statement stm;
	ArrayList<String> queestions = new ArrayList<>();
	ArrayList<String> answers = new ArrayList<>();
	ArrayList<String> unsorted = new ArrayList<>();
	ArrayList<String> dummy1 = new ArrayList<>();
	ArrayList<String> dummy2 = new ArrayList<>();
	ArrayList<String> dummy3 = new ArrayList<>();

	public GenerateQuestions(ResultSet rs, Statement stm) {
		this.rs = rs;
		this.stm = stm;
	}

	public void questions(String tablename, int grade, int limit) {

		String query = "SELECT question,correctAnswer,destractor1,destractor2,destractor3 FROM " + tablename
				+ " WHERE grade = '" + grade + "' ORDER BY RAND() LIMIT " + limit + "";
		try {
			rs = stm.executeQuery(query);
			while (rs.next()) {
				String question = rs.getString(1);
				String answer = rs.getString(2);
				String destractor1 = rs.getString(3);
				String destractor2 = rs.getString(4);
				String destractor3 = rs.getString(5);

				queestions.add(question);

				answers.add(answer);

				unsorted.add(answer);
				unsorted.add(destractor1);
				unsorted.add(destractor2);
				unsorted.add(destractor3);

				dummy1.add(destractor1);
				dummy2.add(destractor2);
				dummy3.add(destractor3);
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}

	public ArrayList<String> getQueestions() {
		return queestions;
	}

	public ArrayList<String> getAnswers() {
		return answers;
	}

	public ArrayList<String> getUnsorted() {
		return unsorted;
	}

	public ArrayList<String> getDummy1() {
		return dummy1;
	}

	public ArrayList<String> getDummy2() {
		return dummy2;
	}

	public ArrayList<String> getDummy3() {
		return dummy3;
	}

}
