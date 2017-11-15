package com.equation.student.inclasstest.getquestions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class GetInClassTestQuestions {
	String testid;
	Statement stm;
	ResultSet rs;
	ArrayList<String> questions = new ArrayList<>();
	ArrayList<String> answers = new ArrayList<>();
	ArrayList<String> unsorted = new ArrayList<>();
	ArrayList<String> dummy1 = new ArrayList<>();
	ArrayList<String> dummy2 = new ArrayList<>();
	ArrayList<String> dummy3 = new ArrayList<>();

	public GetInClassTestQuestions(String testid, Statement stm, ResultSet rs) {
		this.rs = rs;
		this.stm = stm;
		this.testid = testid;
		getPaper();
	}

	public void getPaper() {
		String query = "SELECT question,correct_answer,distractor1,distractor2,distractor3 FROM inclasstestbank WHERE test_id = '"
				+ this.testid + "'  ORDER BY RAND()";
		try {
			this.rs = this.stm.executeQuery(query);
			while (this.rs.next()) {
				String question = rs.getString(1);
				String answer = rs.getString(2);
				String destractor1 = rs.getString(3);
				String destractor2 = rs.getString(4);
				String destractor3 = rs.getString(5);

				questions.add(question);

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

	public ArrayList<String> getQuestions() {
		return questions;
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