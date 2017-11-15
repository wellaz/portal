package com.equation.checkin.ancillary;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoCheckin {
	Statement stm;

	public InsertDataIntoCheckin(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String ecNumber, String time_in, String time_out, String period, String date) {
		String query = "INSERT INTO ancillarycheckin(workerid,time_in,time_out,period,date)VALUES('" + ecNumber + "','"
				+ time_in + "','" + time_out + "','" + period + "','" + date + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}