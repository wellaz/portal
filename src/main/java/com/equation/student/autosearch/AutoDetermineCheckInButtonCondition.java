package com.equation.student.autosearch;

import java.sql.ResultSet;
import java.sql.Statement;

import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;

public class AutoDetermineCheckInButtonCondition {

	ResultSet rs, rs1;
	Statement stm, stmt;

	public AutoDetermineCheckInButtonCondition(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.stm = stm;
		this.rs = rs;
		this.stmt = stmt;
		this.rs1 = rs1;
	}

	public void checkit(String barcode_number, Button ckeckin, Button close, CheckBox mouseFreeMode,
			TextField barcodeField) {
		if (mouseFreeMode.getValue()) {
			ckeckin.setVisible(false);
			close.setVisible(false);

		} else {
			ckeckin.setVisible(true);
			close.setVisible(true);
		}
	}
}