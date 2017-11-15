/**
 *
 * @author Wellington
 */
package com.equation.equate.subjects.passed.record;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Wellington
 *
 */
public class RecordSubjectsPassed {

	public RecordSubjectsPassed() {
		// TODO Auto-generated constructor stub
	}

	public void recordPass(Statement stm, String student_name, String subject, String classname, String term,
			String year, String date) {
		String query = "INSERT INTO record_subjects_passed(student_name,subject,classname,term,year,date)VALUES('"
				+ student_name + "','" + subject + "','" + classname + "','" + term + "','" + year + "','" + date
				+ "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}

}
