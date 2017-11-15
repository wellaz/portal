package com.equation.student.autosearch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.server.Page;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.v7.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("deprecation")
public class SearchIfBarcodeExist {
	ResultSet rs, rs1;
	Statement stm, stmt;
	VerticalLayout layout;

	String schoolID, userID, userLevel;
	TabSheet tabs;

	public SearchIfBarcodeExist(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String schoolID,
			TabSheet tabs, String userID, String userLevel, VerticalLayout layout) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.schoolID = schoolID;
		this.tabs = tabs;
		this.userID = userID;
		this.userLevel = userLevel;
		this.layout = layout;
	}

	public void searchBarcodeAction(String barcode, TextField barcodeField, CheckBox mouseFreeMode, String schoolID) {
		if (!barcode.equals("")) {
			String query = "SELECT 	studentID,sFirstName,sSurname,classname,sgender,pSurname FROM students,parents,classes,schools WHERE students.studentID = '"
					+ barcode + "' AND schools.schoolID = '" + schoolID + "' OR schools.schoolName = '" + schoolID
					+ "'";
			try {
				rs1 = stmt.executeQuery(query);
				rs1.last();
				int rows = rs1.getRow();
				if (rows > 0) {
					startThread(barcode, barcodeField, mouseFreeMode, schoolID);
					ClearAndFocusInputField.clearAndFocusBarcodeField(barcodeField);
				} else {
					new Notification("Error", "STUDENT NUMBER " + barcode + " DOES NOT EXIST!").show(Page.getCurrent());
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
		AutoDisplayStudentDetails x = new AutoDisplayStudentDetails(stm, stmt, rs, rs1, schoolID, tabs, userID,
				userLevel, layout);
		if (!barcode.equals("")) {
			VerticalLayout layout2 = x.displayTransactionDetails(barcode, barcodeField, mouseFreeMode, schoolID);
			UI.getCurrent().access(() -> {
				layout.addComponent(layout2);
			});
		}
	}
}