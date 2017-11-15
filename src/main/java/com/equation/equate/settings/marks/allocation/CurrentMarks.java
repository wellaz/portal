/**
 *
 * @author Wellington
 */
package com.equation.equate.settings.marks.allocation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Wellington
 *
 */
public class CurrentMarks {
	ResultSet rs, rs1;
	Statement stm, stmt;

	public CurrentMarks(ResultSet rs, Statement stm, ResultSet rs1, Statement stmt) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
	}

	public ArrayList<Integer> forSubject(String subject, String term, String year, String class_name) {
		ArrayList<Integer> data = new ArrayList<>();
		String query = "SELECT p1raw,p1contribution,p2raw,p2contribution FROM subject_marks WHERE subject = '" + subject
				+ "' AND term ='" + term + "' AND year = '" + year + "' AND class_name='" + class_name + "'";
		try {
			rs1 = stmt.executeQuery(query);
			if (rs1.next()) {
				String query1 = "SELECT p1raw,p1contribution,p2raw,p2contribution FROM subject_marks WHERE subject = '"
						+ subject + "' AND term ='" + term + "' AND year = '" + year + "' AND class_name='" + class_name
						+ "'";
				rs = stm.executeQuery(query1);
				while (rs.next()) {
					data.add(rs.getInt(1));
					data.add(rs.getInt(2));
					data.add(rs.getInt(3));
					data.add(rs.getInt(4));
				}
			} else {
				// System.out.println(term+" not found");
				data.add(0);
				data.add(0);
				data.add(0);
				data.add(0);
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		return data;
	}

}
