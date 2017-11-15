package com.equation.school.details.retrieve;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

/**
 *
 * @author Wellington
 */
public class RetrieveSchoolDetails {

	ResultSet rs, rs1;
	Statement stm, stmt;
	String schoolName, schoolPostal, schoolPhone, schoolCell, schoolEmail, schoolID;
	private String website;
	private String physicalAddress;
	private String other;
	String schooldepartmentcode, stationCode;

	public RetrieveSchoolDetails(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, String schoolID) {
		this.rs = rs;
		this.stm = stm;
		this.stmt = stmt;
		this.rs1 = rs1;
		this.schoolID = schoolID;
		getData();
	}

	@SuppressWarnings("deprecation")
	public void getData() {
		String query = "SELECT schoolName,phone,cell,email,website,physicalAddress,postalAddress,other,deptCode,stnCode FROM schoolcontactdetails,schools WHERE schools.schoolID = '"
				+ schoolID + "'";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow();
			if (rows > 0) {
				String query1 = "SELECT schoolName,phone,cell,email,website,physicalAddress,postalAddress,other,deptCode,stnCode FROM schoolcontactdetails,schools WHERE schools.schoolID = '"
						+ schoolID + "'";
				rs = stm.executeQuery(query1);
				rs.next();
				schoolName = rs.getString(1);
				schoolPhone = rs.getString(2);
				schoolCell = rs.getString(3);
				schoolEmail = rs.getString(4);
				website = rs.getString(5);
				physicalAddress = rs.getString(6);
				schoolPostal = rs.getString(7);
				other = rs.getString(8);
				schooldepartmentcode = rs.getString(9);
				stationCode = rs.getString(10);

			} else {
				new Notification("Error", "School Details could not be found", Notification.TYPE_ERROR_MESSAGE, true)
						.show(Page.getCurrent());
			}

		} catch (SQLException ee) {
			ee.printStackTrace();

		}

	}

	public String getSchooldepartmentcode() {
		return schooldepartmentcode;
	}

	public String getStationCode() {
		return stationCode;
	}

	public String getPhysicalAddress() {
		return physicalAddress;
	}

	public String getWebsite() {
		return website;
	}

	public String getOther() {
		return other;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public String getSchoolPostal() {
		return schoolPostal;
	}

	public String getSchoolPhone() {
		return schoolPhone;
	}

	public String getSchoolCell() {
		return schoolCell;
	}

	public String getSchoolEmail() {
		return schoolEmail;
	}

}
