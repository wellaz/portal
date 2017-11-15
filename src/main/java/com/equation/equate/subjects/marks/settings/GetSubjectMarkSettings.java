/**
 *
 * @author Wellington
 */
package com.equation.equate.subjects.marks.settings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Wellington
 *
 */
public class GetSubjectMarkSettings {

	Statement stm;
	ResultSet rs;
	private String paperone_raw;
	private String paperone_cont;
	private String papertwo_raw;
	private String papertwo_cont;

	public GetSubjectMarkSettings(Statement stm, ResultSet rs) {
		this.stm = stm;
		this.rs = rs;
	}

	public ArrayList<String> getData(String subject, String class_name, String term, String year) {
		ArrayList<String> data = new ArrayList<>();

		String query = "SELECT p1raw,p1contribution,p2raw,p2contribution FROM subject_marks WHERE subject ='" + subject
				+ "' AND class_name='" + class_name + "' AND term='" + term + "' AND year = '" + year + "' ";
		try {
			rs = stm.executeQuery(query);
			while (rs.next()) {
				paperone_raw = rs.getString(1);
				paperone_cont = rs.getString(2);
				papertwo_raw = rs.getString(3);
				papertwo_cont = rs.getString(4);
				data.addAll(Arrays.asList(paperone_raw, paperone_cont, papertwo_raw, papertwo_cont));

			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return data;
	}

	/**
	 * @return the paperone_raw
	 */
	public String getPaperone_raw() {
		return paperone_raw;
	}

	/**
	 * @return the paperone_cont
	 */
	public String getPaperone_cont() {
		return paperone_cont;
	}

	/**
	 * @return the papertwo_raw
	 */
	public String getPapertwo_raw() {
		return papertwo_raw;
	}

	/**
	 * @return the papertwo_cont
	 */
	public String getPapertwo_cont() {
		return papertwo_cont;
	}

}
