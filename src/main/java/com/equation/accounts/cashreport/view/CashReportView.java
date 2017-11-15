package com.equation.accounts.cashreport.view;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.accounts.cashreport.endofday.GenerateEndOfDayReport;
import com.equation.accounts.cashreport.search.SearchCashReport;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class CashReportView extends VerticalLayout {

	Statement stm, stmt;
	ResultSet rs, rs1;
	String schoolID, userID;
	TabSheet tabs;

	public CashReportView(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String schoolID, TabSheet tabs,
			String userID) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.schoolID = schoolID;
		this.tabs = tabs;
		this.userID = userID;

		Panel leftpanel = new SearchCashReport(this.stm, this.stmt, this.rs, this.rs1, this.schoolID);

		Button endofDay = new Button("End Of Day");
		endofDay.addStyleName(ValoTheme.BUTTON_PRIMARY);
		endofDay.setIcon(VaadinIcons.SUN_O);
		endofDay.addClickListener((e) -> {
			UI.getCurrent().addWindow(
					new GenerateEndOfDayReport(this.rs, this.rs1, this.stm, this.stmt, this.schoolID, this.userID));
		});

		HorizontalLayout horizontalLayout = new HorizontalLayout(endofDay);
		horizontalLayout.setSizeFull();
		horizontalLayout.setSpacing(true);
		horizontalLayout.addStyleName("cash_report_buttons");
		VerticalLayout verticalLayout = new VerticalLayout(horizontalLayout);

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
			if (tabs.getTab(x).getCaption().equals("Cash Report")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Cash Report", VaadinIcons.BOOK);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}
}