package com.equation.accounts.payments.view;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.accounts.payments.students.fees.capture.CaptureStudentPaymentDetails;
import com.equation.student.payments.reports.IndividualStudentPaymentReport;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class FeesPaymentsView extends VerticalLayout {
	Statement stm, stmt;
	ResultSet rs, rs1;
	String schoolID, userID, userLevel;
	TabSheet tabs;
	public CaptureStudentPaymentDetails captureStudentPaymentDetails;

	public FeesPaymentsView(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String schoolID, TabSheet tabs,
			String userID, String userLevel) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.schoolID = schoolID;
		this.tabs = tabs;
		this.userID = userID;
		this.userLevel = userLevel;

		IndividualStudentPaymentReport individualStudentPaymentReport = new IndividualStudentPaymentReport(this.rs,
				this.rs1, this.stm, this.stmt, this.schoolID, this.tabs);

		Panel leftpanel = new Panel(individualStudentPaymentReport);
		leftpanel.setCaption("Search Student Payment Record");
		leftpanel.setIcon(VaadinIcons.SEARCH);

		captureStudentPaymentDetails = new CaptureStudentPaymentDetails(this.stm, this.rs, this.stmt, this.rs1,
				this.schoolID, this.userID);

		VerticalLayout verticalLayout = new VerticalLayout(captureStudentPaymentDetails);

		HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
		splitPanel.setFirstComponent(leftpanel);
		splitPanel.setSecondComponent(verticalLayout);
		splitPanel.setSplitPosition(20, Unit.PERCENTAGE);

		this.addComponent(splitPanel);
		this.setSpacing(true);

	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Payments")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Payments", VaadinIcons.BOOK);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}
}