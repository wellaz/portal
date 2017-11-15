package com.equation.books.reports;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.school.books.details.capture.CaptureSchoolBooks;
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
public class BooksView extends VerticalLayout {

	Statement stm, stmt;
	ResultSet rs, rs1;
	String schoolID, userID;
	TabSheet tabs;

	public BooksView(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String schoolID, TabSheet tabs,
			String userID) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.schoolID = schoolID;
		this.tabs = tabs;
		this.userID = userID;

		Panel leftpanel = new RetrieveBookDetails(this.rs, this.rs1, this.stm, this.stmt, this.tabs);
		CaptureSchoolBooks captureSundryEntries = new CaptureSchoolBooks(this.stm, this.tabs);

		VerticalLayout verticalLayout = new VerticalLayout(captureSundryEntries);

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
			if (tabs.getTab(x).getCaption().equals("Books")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Books", VaadinIcons.BOOK);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

}
