package com.equation.school.subjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wellington
 */
public class AllSchoolSubjects {
	ResultSet rs, rs1;
	Statement stm, stmt;

	public AllSchoolSubjects(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
	}

	public ArrayList<String> allSchoolSubjects() {
		ArrayList<String> data = new ArrayList<>();
		String query = "SELECT subject FROM school_subjects";
		try {
			rs = stm.executeQuery(query);
			if (rs.next()) {
				do {
					data.add(rs.getString(1));
				} while (rs.next());
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return data;
	}

	public boolean isSubjectPosted(String subject) {
		List<String> fileTypes = allSchoolSubjects();
		return fileTypes.stream().anyMatch(t -> subject.equals(t));
	}

	/**
	 * 
	 * @param subject
	 *            The subject
	 * @return
	 */
	public String getSubjectIDFor(String subject) {
		String id = null;
		String query = "SELECT record_id FROM school_subjects WHERE subject = '" + subject + "'";
		try {
			rs = stm.executeQuery(query);
			rs.next();
			id = rs.getString(1);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return id;
	}

}
