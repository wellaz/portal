package com.equation.student.payments.myreports;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.student.name.retrieve.RetrieveStudentName;
import com.equation.system.users.teacher.getname.FetchStuffMemberName;
import com.equation.utils.print.currentpage.PrintCurrentPage;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
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
public class MyPaymentsReport {
	ResultSet rs, rs1;
	Statement stm, stmt;
	String schoolID, studentID;
	private Table table;
	private Window window;

	public MyPaymentsReport(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, String schoolID,
			String studentID) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.schoolID = schoolID;
		this.studentID = studentID;

		Button closeButton = new Button("Close");
		closeButton.addStyleName(ValoTheme.BUTTON_DANGER);
		closeButton.setIcon(VaadinIcons.CLOSE);

		Button print = new Button("Print");
		print.addStyleName(ValoTheme.BUTTON_PRIMARY);
		print.setIcon(VaadinIcons.PRINT);
		print.addClickListener((e) -> {
			PrintCurrentPage.print();
		});

		Button download = new Button("Download");
		download.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		download.setIcon(VaadinIcons.DOWNLOAD);

		window = new Window();
		setWindow(window);
		getWindow().center();
		getWindow().setModal(false);
		getWindow().setSizeFull();

		closeButton.addClickListener((ev) -> {
			UI.getCurrent().removeWindow(getWindow());
			getWindow().close();
		});

		HorizontalLayout horizontalLayout = new HorizontalLayout(download, print, closeButton);
		horizontalLayout.setSpacing(true);
		horizontalLayout.setSizeFull();
		horizontalLayout.addStyleName("student_report_sub_window");

		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);

		getWindow().setCaption(
				new RetrieveStudentName(rs, stm).fetchStudentName(studentID).toUpperCase() + " Payment Report");
		try {
			String query = "SELECT receiptNumber,userID,amount,date,time,paidBy FROM feesreceived,schools WHERE studentID = '"
					+ studentID + "' AND schools.schoolID = '" + schoolID + "'";
			this.rs1 = this.stmt.executeQuery(query);
			this.rs1.last();
			int numberOfRows = this.rs1.getRow(), i = 0;
			if (numberOfRows > 0) {
				table = new Table();
				table.addContainerProperty("Receipt Number", Integer.class, null);
				table.addContainerProperty("Amount", Double.class, null);
				table.addContainerProperty("Term", String.class, null);
				table.addContainerProperty("Year", String.class, null);
				table.addContainerProperty("Balance", Double.class, null);
				table.addContainerProperty("Dated", String.class, null);
				table.addContainerProperty("Payee", String.class, null);

				table.addContainerProperty("Cashier", String.class, null);
				FetchStuffMemberName fetchUserFirstNane = new FetchStuffMemberName(this.rs, this.rs1, this.stm,
						this.stmt);

				String query1 = "SELECT receiptNumber,userID,amount,date,time,paidBy,balance,term,year FROM feesreceived,schools WHERE studentID = '"
						+ studentID + "' AND schools.schoolID = '" + schoolID + "'";
				this.rs = this.stm.executeQuery(query1);
				double sum = 0;
				while (this.rs.next()) {
					int receitNumber = this.rs.getInt(1);
					String userID = this.rs.getString(2);
					double amount = this.rs.getDouble(3);
					String timestamp = this.rs.getString(4) + " " + this.rs.getString(5);
					String paidby = this.rs.getString(6);
					double balance = this.rs.getDouble(7);
					String termm = this.rs.getString(8);
					int yearr = this.rs.getInt(9);

					String cashiername = fetchUserFirstNane.getActualName(userID);

					table.addItem(new Object[] { receitNumber, amount, termm, String.valueOf(yearr), balance, timestamp,
							paidby, cashiername }, new Integer(i));
					sum += amount;
					i++;
				}
				double size1 = table.size();

				table.setFooterVisible(true);
				table.setColumnFooter("Receipt Number", "Total " + String.valueOf(Math.floor(size1)) + " Records");
				table.setColumnFooter("Amount", "Sum $" + String.valueOf(sum));

				table.setSizeFull();
				table.setPageLength((int) size1);
				table.addStyleName("payments_table");
				table.setSelectable(true);
				table.setColumnCollapsingAllowed(true);
				layout.addComponents(horizontalLayout, table);
				getWindow().setContent(layout);
				UI.getCurrent().addWindow(getWindow());
				String studentname = new RetrieveStudentName(this.rs1, this.stmt).fetchStudentName(studentID);
				new MyPaymentreportPDF().generatePDF(studentID, studentname, table, download);

			} else {
				new Notification(
						"<h1>No Payment Record was found for you!<br/>You may need to see the school Bursar or the Headmaster if you think have any queries!<br/>THANK YOU!!!<h1/>",
						"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}

	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

}