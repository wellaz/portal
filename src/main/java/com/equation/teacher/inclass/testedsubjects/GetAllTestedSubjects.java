package com.equation.teacher.inclass.testedsubjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class GetAllTestedSubjects {
	Statement stm, stmt;
	ResultSet rs, rs1;
	String subject, classname;

	ArrayList<String> topics = new ArrayList<>();
	ArrayList<String> testids = new ArrayList<>();
	ArrayList<String> numberOfQuestions = new ArrayList<>();
	ArrayList<String> totalMarks = new ArrayList<>();
	ArrayList<String> periods = new ArrayList<>();
	ArrayList<String> teachers = new ArrayList<>();
	ArrayList<String> dates = new ArrayList<>();
	ArrayList<String> dues = new ArrayList<>();

	public GetAllTestedSubjects(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String subject,
			String classname) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.subject = subject;
		this.classname = classname;
		getData();
	}

	public void getData() {
		String query = "SELECT test_id,topic,number_of_questions,total_mark,test_period,prepared_by,date,time,due FROM inclasstestrecords WHERE subject = '"
				+ subject + "' AND class_name = '" + classname + "'";
		try {
			rs = stm.executeQuery(query);
			while (rs.next()) {
				String test_id = rs.getString(1);
				String topic = rs.getString(2);
				String number_of_questions = rs.getString(3);
				String total_mark = rs.getString(4);
				String test_period = rs.getString(5);
				String prepared_by = rs.getString(6);
				String dated = rs.getString(7) + " " + rs.getString(8);
				String due = rs.getString(9);
				testids.add(test_id);
				topics.add(topic);
				numberOfQuestions.add(number_of_questions);
				totalMarks.add(total_mark);
				periods.add(test_period);
				teachers.add(prepared_by);
				dates.add(dated);
				dues.add(due);

			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

	}

	public ArrayList<String> getTopics() {
		return topics;
	}

	public ArrayList<String> getTestids() {
		return testids;
	}

	public ArrayList<String> getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public ArrayList<String> getTotalMarks() {
		return totalMarks;
	}

	public ArrayList<String> getPeriods() {
		return periods;
	}

	public ArrayList<String> getTeachers() {
		return teachers;
	}

	public ArrayList<String> getDates() {
		return dates;
	}

	public ArrayList<String> getDues() {
		return dues;
	}
}