package com.equation.ancillary.details.capture;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class NextAutoIncrementValue {
	Statement stm;
	ResultSet rs;

	public NextAutoIncrementValue(Statement stm, ResultSet rs) {
		this.rs = rs;
		this.stm = stm;
	}

	public String nextValue(String emis) {
		int value = 0;
		String idnumber = null;
		String query = "SELECT workerID FROM acillary";
		try {
			rs = stm.executeQuery(query);
			rs.last();
			value = rs.getRow();
			if (value == 0) {
				emis = new StringBuilder(emis).reverse().toString();
				int myID = Integer.parseInt(emis);
				int p = (myID - 80) + 3;
				int index = p - (((int) Math.floor((p) / 23)) * 23);
				String c = AncillaryAlphabet.alphabetArray().get(index);
				idnumber = p + c;
			} else {
				String id = rs.getString(1);
				int p = Integer.parseInt(id.substring(0, id.length() - 1)) + 3;
				int index = p - (((int) Math.floor((p) / 23)) * 23);
				String c = AncillaryAlphabet.alphabetArray().get(index);
				idnumber = p + c;
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return idnumber;
	}
}