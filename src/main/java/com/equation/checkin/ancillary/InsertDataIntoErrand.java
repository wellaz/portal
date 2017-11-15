package com.equation.checkin.ancillary;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoErrand {
	Statement stm;

	public InsertDataIntoErrand(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String ecNumber, String trip, String time_out, String time_in, String period, String date) {
		String query = "INSERT INTO ancillaryerrand(workerid,trip,time_out,time_in,period,date)VALUES('" + ecNumber
				+ "','" + trip + "','" + time_out + "','" + time_in + "','" + period + "','" + date + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}