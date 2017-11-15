package com.equation.administrator.users.create;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoUsersTable {
	Statement stm;

	public InsertDataIntoUsersTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(String identityID, String userLevel, String username, String password, String timeStamp) {
		String query = "INSERT INTO users(identityID,userLevel,username,password,timeStamp)VALUES('" + identityID
				+ "','" + userLevel + "','" + username + "','" + password + "','" + timeStamp + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}