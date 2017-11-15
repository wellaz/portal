package com.equation.system.users.teacher.getname;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class FetchStuffMemberName {
	ResultSet rs, rs1;
	Statement stm, stmt;

	public FetchStuffMemberName(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs1 = rs1;
		this.rs = rs;
		this.stm = stm;
		this.stmt = stmt;
	}

	public String getActualName(String userID) {
		String name = null;

		String query1 = "SELECT userLevel FROM users WHERE identityID = '" + userID + "'";
		try {
			rs1 = stmt.executeQuery(query1);
			if (rs1.next()) {
				String userLevel = rs1.getString(1);
				switch (userLevel) {
				case "Administrator":
				case "Teacher": {
					String query = "SELECT firstName,middleName,surname,gender FROM teachers WHERE ecNumber = '"
							+ userID + "' ";
					try {
						rs1 = stmt.executeQuery(query);
						if (rs1.next()) {
							String firstName = rs1.getString(1);
							String middleName = rs1.getString(2);
							String surname = rs1.getString(3);
							String gender = rs1.getString(4);

							String title = (gender.equals("Male") ? "Mr " : " Mrs/Miss");

							String md = (middleName.equals("") ? "" : middleName.substring(0, 1).toUpperCase() + ".");

							name = title + firstName.substring(0, 1).toUpperCase() + ". " + md + " " + surname;
						}
					} catch (SQLException ee) {
						ee.printStackTrace();
					}

				}
					break;
				case "Bursar": {
					String query = "SELECT firstName,surname FROM acillary WHERE workerID = '" + userID + "'";
					try {
						rs1 = stmt.executeQuery(query);
						rs1.next();
						name = rs1.getString(1) + " " + rs1.getString(2);
					} catch (SQLException ee) {
						ee.printStackTrace();
					}
				}
					break;
				default: {

				}
					break;
				}
			} else {
				name = "Not Found";
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return name;
	}
}