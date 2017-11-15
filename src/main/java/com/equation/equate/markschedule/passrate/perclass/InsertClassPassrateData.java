/**
 *
 * @author Wellington
 */
package com.equation.equate.markschedule.passrate.perclass;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Wellington
 *
 */
public class InsertClassPassrateData {

	public void insertData(Statement stm, String class_name, String subject, int passrate, String term, String year,
			String date) {
		String query = "INSERT INTO classes_passrates(class_name,subject,passrate,term,year,date)VALUES('" + class_name
				+ "','" + subject + "','" + passrate + "','" + term + "','" + year + "','" + date + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}

	}

}
