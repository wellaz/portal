package com.equation.school.subjects;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */

public class InsertSubjects {

	public void insertData(Statement stm, String subject, String code) {
		String query = "INSERT INTO school_subjects(subject,code)VALUES('" + subject + "','" + code + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}

	}

}
