package com.equation.accounts.administrator.fees.structure.set;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class InsertDataIntoFeesStructureTable {
	Statement stm;

	public InsertDataIntoFeesStructureTable(Statement stm) {
		this.stm = stm;
	}

	public void insertData(double tution, double bspz, double building, double generalPurpose, double sports,
			double levi, double other, double total, String dateSet, String schoolID) {
		String query = "INSERT INTO feestructure(tution,bspz,building,generalPurpose,sports,levi,other,total,dateSet,schoolID)VALUES('"
				+ tution + "','" + bspz + "','" + building + "','" + generalPurpose + "','" + sports + "','" + levi
				+ "','" + other + "','" + total + "','" + dateSet + "','" + schoolID + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}