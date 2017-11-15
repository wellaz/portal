package com.equation.accounts.payments.students.fees.capture;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */
public class FeesStructureBreakdown {
	Statement stm, stmt;
	ResultSet rs, rs1;
	String schoolID;
	int year;

	double admin;
	double tution;
	double bspz;
	double building;
	double generalPurpose;
	double sports;
	double levi;
	double other;
	double total;

	public FeesStructureBreakdown(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String schoolID,
			int year) {
		this.rs = rs;
		this.stm = stm;
		this.stmt = stmt;
		this.rs1 = rs1;

		this.year = year;
		this.schoolID = schoolID;
		breakdownStructure();
	}

	public void breakdownStructure() {
		String query = "SELECT admin,tution,bspz,building,generalPurpose,sports,levi,other,total FROM feestructure,schools WHERE schools.schoolID = '"
				+ schoolID + "' AND year = '" + year + "'";
		try {
			rs = stm.executeQuery(query);
			if (rs.next()) {
				admin = rs.getDouble(1);
				tution = rs.getDouble(2);
				bspz = rs.getDouble(3);
				building = rs.getDouble(4);
				generalPurpose = rs.getDouble(5);
				sports = rs.getDouble(6);
				levi = rs.getDouble(7);
				other = rs.getDouble(8);
				total = rs.getDouble(9);
			} else {
				admin = 0;
				tution = 0;
				bspz = 0;
				building = 0;
				generalPurpose = 0;
				sports = 0;
				levi = 0;
				other = 0;
				total = 0;
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}

	}

	public double getAdmin() {
		return admin;
	}

	public double getTution() {
		return tution;
	}

	public double getBspz() {
		return bspz;
	}

	public double getBuilding() {
		return building;
	}

	public double getGeneralPurpose() {
		return generalPurpose;
	}

	public double getSports() {
		return sports;
	}

	public double getLevi() {
		return levi;
	}

	public double getOther() {
		return other;
	}

	public double getTotal() {
		return total;
	}

}
