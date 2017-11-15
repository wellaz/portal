package com.equation.student.autosearch;

import com.vaadin.v7.ui.TextField;

@SuppressWarnings("deprecation")
public class ClearAndFocusInputField {
	public ClearAndFocusInputField() {
		super();
		return;
	}

	public static void clearAndFocusBarcodeField(TextField barcodeField) {
		barcodeField.clear();
		barcodeField.focus();
	}
}