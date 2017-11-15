/**
 *
 * @author Wellington
 */
package com.equation.equate.markschedule.passrate.pergrade;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Wellington
 *
 */
public class InsertGradePassrate {

	public void insertData(Statement stm, String class_name, String subject, int passrate, String term, String year,
			String date) {
		String query = "INSERT INTO grades_passrates(grade,subject,rate,term,year,date)VALUES('" + class_name + "','"
				+ subject + "','" + passrate + "','" + term + "','" + year + "','" + date + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}

	}

}
