package com.equation.teacher.establishment;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoTeacherEstablishment {
	Statement stm;

	public InsertDataIntoTeacherEstablishment(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String gradeOrSubjects, String period, String cocuricular, String ecNumber,
			String schoolID) {
		String query = "INSERT INTO teacherestablishment(gradeOrSubjects,period,cocuricular,ecNumber,schoolID)VALUES('"
				+ gradeOrSubjects + "','" + period + "','" + cocuricular + "','" + ecNumber + "','" + schoolID + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}