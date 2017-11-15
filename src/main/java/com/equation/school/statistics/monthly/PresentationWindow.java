package com.equation.school.statistics.monthly;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.school.statistics.monthly.document.MonthlyReturnPDF;
import com.equation.user.session.attributes.UserSessionAttributes;
import com.equation.utils.date.DateUtility;
import com.equation.utils.print.currentpage.PrintCurrentPage;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("deprecation")
public class PresentationWindow {
	ResultSet rs, rs1;
	Statement stm, stmt;
	String schoolID, schoolname, nameOfDistrict;

	public PresentationWindow(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, String schoolID,
			String schoolname, String nameOfDistrict) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.schoolID = schoolID;
		this.schoolname = schoolname;
		this.nameOfDistrict = nameOfDistrict;
	}

	public Window present() {
		Window window = new Window();
		Button close = new Button("Close");
		close.setIcon(VaadinIcons.CLOSE);
		close.addStyleName(ValoTheme.BUTTON_DANGER);
		close.addClickListener((e) -> {
			UI.getCurrent().removeWindow(window);
			window.close();
		});

		Button print = new Button("Print");
		print.addStyleName(ValoTheme.BUTTON_PRIMARY);
		print.setIcon(VaadinIcons.PRINT);
		print.addClickListener((e) -> {
			PrintCurrentPage.print();
		});
		Button download = new Button("Download");
		download.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		download.setIcon(VaadinIcons.DOWNLOAD);
		HorizontalLayout horizontalLayout = new HorizontalLayout(download, print, close);
		horizontalLayout.setSpacing(true);

		Table table1 = new TotalPrimaryStudentsPerClassTable(rs, rs1, stm, stmt, schoolID).getPrimaryStatisticsTable();

		Table table2 = new BasicTeacherStatisticsTable(rs, rs1, stm, stmt, schoolID).basicTeachersTable();

		Table table3 = new TeachersOnLeaveStatistics(rs, rs1, stm, stmt, schoolID).onLeaveTable();

		Table table4 = new TeachersOnDutyAndOnSite(rs, rs1, stm, stmt, schoolID).onDutyAndOnSiteTable();

		Table table5 = new TeacherOnLeaveAndOnSite(rs, rs1, stm, stmt, schoolID).onSiteAndOffSiteTable();

		Table table6 = new AbsentTeachersOnPaysheet(rs, rs1, stm, stmt, schoolID).absentAndOnPaysheet();

		Table table7 = new TeachersOnVacationOrMaternityLeave(rs, rs1, stm, stmt, schoolID)
				.onVacationOrMaternityLeaveTable();
		Table table8 = new GhostTeahchers(rs, rs1, stm, stmt, schoolID).ghostTeachersTable();

		DateUtility dateutility = new DateUtility();

		String thisMonth = dateutility.thisMonth(), thisYear = dateutility.getYear(), date = dateutility.getDate();
		String departmentCode = (String) UI.getCurrent().getSession().getAttribute(UserSessionAttributes.DEPT_CODE)
				.toString();
		String stationCode = (String) UI.getCurrent().getSession().getAttribute(UserSessionAttributes.DEPT_CODE)
				.toString();

		MonthlyReturnPDF monthlyReturnPDF = new MonthlyReturnPDF();
		monthlyReturnPDF.generatePDF(schoolname, thisMonth, thisYear, date, departmentCode, stationCode, nameOfDistrict,
				schoolID, table1, table2, table3, table4, table5, table6, table7, table8, download);

		VerticalLayout layout = new VerticalLayout(horizontalLayout, table1, table2, table3, table4, table5, table6,
				table7, table8);

		layout.setSpacing(true);
		window.setContent(layout);
		window.setSizeFull();
		window.center();

		return window;
	}
}