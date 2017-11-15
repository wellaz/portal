package com.equation.accounts.vouchers.view;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.accounts.vouchers.capture.CaptureNewVoucherDetails;
import com.equation.accounts.vouchers.search.SearchVouchers;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class VouchersMainPanel extends VerticalLayout {

	Statement stm, stmt;
	ResultSet rs, rs1;
	String schoolID, userID;
	TabSheet tabs;

	@SuppressWarnings("deprecation")
	public VouchersMainPanel(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String schoolID, TabSheet tabs,
			String userID) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.schoolID = schoolID;
		this.tabs = tabs;
		this.userID = userID;

		Panel leftpanel = new SearchVouchers(this.stm, this.stmt, this.rs, this.rs1, this.schoolID);
		CaptureNewVoucherDetails captureNewVoucherDetails = new CaptureNewVoucherDetails(this.stm, this.rs, this.tabs,
				this.schoolID);

		VerticalLayout verticalLayout = new VerticalLayout(captureNewVoucherDetails);

		HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
		splitPanel.setFirstComponent(leftpanel);
		splitPanel.setSecondComponent(verticalLayout);
		splitPanel.setSplitPosition(20, Sizeable.UNITS_PERCENTAGE);

		this.addComponent(splitPanel);
		this.setSpacing(true);

	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Vouchers")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Vouchers", VaadinIcons.BOOK);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}
}