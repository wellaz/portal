package com.equation.checkin.teachers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.checkin.ancillary.CheckIfWorkerIDExist;
import com.equation.student.autosearch.ClearAndFocusInputField;
import com.vaadin.server.Page;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Notification;
import com.vaadin.v7.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("deprecation")
public class CheckIfECNumberExist {
	ResultSet rs, rs1;
	Statement stm, stmt;
	VerticalLayout layout;

	String schoolID, userID, userLevel;

	public CheckIfECNumberExist(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String schoolID,
			String userID, String userLevel, VerticalLayout layout) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.schoolID = schoolID;
		this.userID = userID;
		this.userLevel = userLevel;
		this.layout = layout;
	}

	public void searchBarcodeAction(String barcode, TextField barcodeField, CheckBox mouseFreeMode) {
		if (!barcode.equals("")) {
			String findBarcode = "SELECT userID,userlevel FROM users_barcodes WHERE barcode = '" + barcode + "'";
			try {
				rs = stm.executeQuery(findBarcode);
				if (rs.next()) {
					String ecNumber = rs.getString(1);
					String userlevel = rs.getString(2);
					if (userlevel.equals(UserLevels.TEACHER)) {
						String query = "SELECT firstName FROM teachers,schools WHERE teachers.ecNumber = '" + ecNumber
								+ "' AND  schools.schoolID = '" + schoolID + "' OR schools.schoolName = '" + schoolID
								+ "'";

						rs1 = stmt.executeQuery(query);
						rs1.last();
						int rows = rs1.getRow();
						if (rows > 0) {
							System.out.println("Found");
							startThread(ecNumber, barcodeField, mouseFreeMode, schoolID);
							// check in action
							new DaemonCode(stm, stmt, rs, rs1).process(ecNumber.toUpperCase());

							// clear and focus input box
							ClearAndFocusInputField.clearAndFocusBarcodeField(barcodeField);
						} else {
							System.out.println(barcode + " not found");
							new Notification("Error", "EC NUMBER " + barcode + " DOES NOT EXIST!")
									.show(Page.getCurrent());
							ClearAndFocusInputField.clearAndFocusBarcodeField(barcodeField);
							layout.removeAllComponents();
						}
					} else {
						CheckIfWorkerIDExist checkIfWorkerIDExist = new CheckIfWorkerIDExist(stm, stmt, rs, rs1,
								schoolID, userID, userLevel, layout);
						checkIfWorkerIDExist.searchBarcodeAction(barcode, barcodeField, mouseFreeMode);
					}
				} else {
					System.out.println(barcode + " not found");
					new Notification("Error", "Identity  ID" + barcode + " DOES NOT EXIST!").show(Page.getCurrent());
					ClearAndFocusInputField.clearAndFocusBarcodeField(barcodeField);
					layout.removeAllComponents();
				}
			} catch (SQLException ee) {
				ee.printStackTrace();
			}

		} else {
			barcodeField.focus();
		}
	}

	public void startThread(String barcode, TextField barcodeField, CheckBox mouseFreeMode, String schoolID) {
		TeacherGeneralDetails details = new TeacherGeneralDetails(rs, rs1, stm, stmt, schoolID, layout, barcodeField);
		if (!barcode.equals("")) {
			VerticalLayout layout2 = details.displayTeacherDetails(barcode);
			UI.getCurrent().access(() -> {
				layout.addComponent(layout2);
			});
		}
	}
}