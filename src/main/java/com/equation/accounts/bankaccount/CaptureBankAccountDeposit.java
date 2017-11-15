package com.equation.accounts.bankaccount;

import java.sql.ResultSet;
import java.sql.Statement;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class CaptureBankAccountDeposit extends CustomComponent implements View {

	Statement stm;
	ResultSet rs;

	ComboBox<String> sourceAccounts;

	public CaptureBankAccountDeposit(Statement stm, ResultSet rs) {
		this.stm = stm;
		this.rs = rs;

		sourceAccounts = new ComboBox<>();
		sourceAccounts.setRequiredIndicatorVisible(true);
		sourceAccounts.setItems("Donation", "");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}
