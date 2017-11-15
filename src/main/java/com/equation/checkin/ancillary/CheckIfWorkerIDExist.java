package com.equation.checkin.ancillary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
public class CheckIfWorkerIDExist {
	ResultSet rs, rs1;
	Statement stm, stmt;
	VerticalLayout layout;

	String schoolID, userID, userLevel;

	public CheckIfWorkerIDExist(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String schoolID,
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
			String findBarcode = "SELECT userID FROM users_barcodes WHERE barcode = '" + barcode + "'";
			try {
				rs = stm.executeQuery(findBarcode);
				if (rs.next()) {
					String ecNumber = rs.getString(1);
					String query = "SELECT firstName FROM acillary,schools WHERE acillary.workerID = '" + ecNumber
							+ "' AND  schools.schoolID = '" + schoolID + "' OR schools.schoolName = '" + schoolID + "'";

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
						new Notification("Error", "Worker ID " + barcode + " DOES NOT EXIST!").show(Page.getCurrent());
						ClearAndFocusInputField.clearAndFocusBarcodeField(barcodeField);
						layout.removeAllComponents();
					}
				} else {
					System.out.println(barcode + " not found");
					new Notification("<h1>Error<h1/>", "Identity  ID" + barcode + " Cannot perform this operation!")
							.show(Page.getCurrent());
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

	private void startThread(String barcode, TextField barcodeField, CheckBox mouseFreeMode, String schoolID) {
		AncillaryGeneralDetails details = new AncillaryGeneralDetails(rs, rs1, stm, stmt, schoolID, layout,
				barcodeField);
		if (!barcode.equals("")) {
			VerticalLayout layout2 = details.displayTeacherDetails(barcode);
			UI.getCurrent().access(() -> {
				layout.addComponent(layout2);
			});
		}
	}
}