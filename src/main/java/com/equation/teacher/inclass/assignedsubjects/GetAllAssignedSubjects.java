package com.equation.teacher.inclass.assignedsubjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class GetAllAssignedSubjects {
	Statement stm, stmt;
	ResultSet rs, rs1;
	String subject, classname;

	ArrayList<String> topics = new ArrayList<>();
	ArrayList<String> testids = new ArrayList<>();
	// ArrayList<String> numberOfQuestions = new ArrayList<>();
	ArrayList<String> totalMarks = new ArrayList<>();
	// ArrayList<String> teachers = new ArrayList<>();
	ArrayList<String> dates = new ArrayList<>();
	ArrayList<String> dues = new ArrayList<>();
	ArrayList<String> types = new ArrayList<>();

	public GetAllAssignedSubjects(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String subject,
			String classname) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.subject = subject;
		this.classname = classname;
		getData();
	}

	public ArrayList<String> getTypes() {
		return types;
	}

	public void getData() {
		String query = "SELECT assinment_id,topic,total_mark,date_posted,due,type FROM assignment_details,school_subjects,classes WHERE school_subjects.subject = '"
				+ subject + "' AND classes.classname = '" + classname + "'";
		try {
			rs = stm.executeQuery(query);
			while (rs.next()) {
				String test_id = rs.getString(1);
				String topic = rs.getString(2);
				// String number_of_questions = rs.getString(3);
				String total_mark = rs.getString(3);
				// String prepared_by = rs.getString(5);
				String dated = rs.getString(4);
				String due = rs.getString(5);
				String type = rs.getString(6);
				testids.add(test_id);
				topics.add(topic);
				// numberOfQuestions.add(number_of_questions);
				totalMarks.add(total_mark);
				// teachers.add(prepared_by);
				dates.add(dated);
				dues.add(due);
				types.add(type);

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

	/*
	 * public ArrayList<String> getNumberOfQuestions() { return
	 * numberOfQuestions; }
	 */

	public ArrayList<String> getTotalMarks() {
		return totalMarks;
	}

	/*
	 * public ArrayList<String> getTeachers() { return teachers; }
	 */

	public ArrayList<String> getDates() {
		return dates;
	}

	public ArrayList<String> getDues() {
		return dues;
	}

}
