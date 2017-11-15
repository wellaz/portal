package com.equation.accounts.cashreport.endofday;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.accounts.cashreport.InsertDataIntoCashReportTable;
import com.equation.utils.date.DateUtility;
import com.equation.utils.doublevalue.format.DoubleValueFormat;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
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
@SuppressWarnings({ "serial", "deprecation" })
public class GenerateEndOfDayReport extends Window {

	ResultSet rs, rs1;
	Statement stm, stmt;
	private Table table;
	String schoolID, userID;
	private Button download;

	public GenerateEndOfDayReport(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, String schoolID,
			String userID) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.stm = stm;
		this.schoolID = schoolID;
		this.userID = userID;
		GetDailyFirstAndLastReceiptNumbers firstAndLastReceiptNumbers = new GetDailyFirstAndLastReceiptNumbers(
				this.stmt, this.rs1);
		DoubleValueFormat valueFormat = new DoubleValueFormat();

		double openingBalance = valueFormat
				.format(new OpeningBalance(this.rs, this.rs1, this.stm, this.stmt).getBalance());
		double cashReceived = valueFormat
				.format(new CashReceivedToday(this.rs, this.rs1, this.stm, this.stmt).getTodaysTotalDeposit());
		String firstReceipt = firstAndLastReceiptNumbers.getFisrReceiptNumber();
		String lastReceipt = firstAndLastReceiptNumbers.getLastReceiptNumber();
		double totalcashBanked = valueFormat
				.format(new CashBankedToday(this.rs, this.rs1, this.stm, this.stmt).getTodaysTotalCashBanked());

		double totalCashIn = valueFormat.format(openingBalance + cashReceived);
		double balanceBroughtFoward = valueFormat.format(totalCashIn - totalcashBanked);

		table = new Table();
		table.addContainerProperty("Description", String.class, null);
		table.addContainerProperty("Cash In", String.class, null);
		table.addContainerProperty("Cash Out", String.class, null);

		table.addItem(new Object[] { "", "$", "$" }, 0);
		table.addItem(new Object[] { "Opening Balance b/f", String.valueOf(openingBalance), "" }, 1);
		table.addItem(new Object[] { "Cash and Cheques Received", String.valueOf(cashReceived), "" }, 2);
		table.addItem(new Object[] { "Cash Receipt Nos", "", "" }, 3);
		table.addItem(new Object[] { "From         To", "", "" }, 4);
		table.addItem(new Object[] { String.valueOf(firstReceipt) + "         " + String.valueOf(lastReceipt), "", "" },
				5);
		table.addItem(new Object[] { "", "", "" }, 6);
		table.addItem(new Object[] { "Total Cash In", String.valueOf(totalCashIn), "" }, 7);
		table.addItem(new Object[] { "Cash Banked", "", String.valueOf(totalcashBanked) }, 8);
		table.addItem(new Object[] { "Closing Balance c/f", "", String.valueOf(balanceBroughtFoward) }, 9);

		table.setFooterVisible(true);
		table.setColumnFooter("Description", "Total ");
		table.setColumnFooter("Cash In", "Sum $" + String.valueOf(totalCashIn));
		table.setColumnFooter("Cash Out", "Sum $" + String.valueOf(balanceBroughtFoward));

		table.setSizeFull();
		table.setPageLength(table.size());
		table.setSelectable(true);
		table.setColumnCollapsingAllowed(true);
		table.setSortEnabled(false);
		table.setNullSelectionAllowed(false);

		Button cofirm = new Button("CONFIRM");
		cofirm.addStyleName(ValoTheme.BUTTON_DANGER);
		cofirm.setIcon(VaadinIcons.WARNING);

		download = new Button("Download");
		download.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		download.setIcon(VaadinIcons.DOWNLOAD);

		CashReportPDF cashReportPDF = new CashReportPDF();
		cashReportPDF.generatePDF(table, download);

		Button print = new Button("Print");
		print.addStyleName(ValoTheme.BUTTON_PRIMARY);
		print.setIcon(VaadinIcons.PRINT);

		Button close = new Button("Close");
		close.addStyleName(ValoTheme.BUTTON_DANGER);
		close.setIcon(VaadinIcons.CLOSE);

		HorizontalLayout horizontalLayout = new HorizontalLayout(cofirm, download, print, close);
		horizontalLayout.setSpacing(true);
		horizontalLayout.setSizeFull();
		horizontalLayout.addStyleName("end_of_day_horizontal_layout");

		close.addClickListener((e) -> {
			UI.getCurrent().removeWindow(this);
			this.close();
		});

		print.addClickListener((e) -> {
			JavaScript.getCurrent().execute("print();");
		});
		cofirm.addClickListener((e) -> {
			String today = new DateUtility().getDate();
			String query = "SELECT date FROM cash_report";
			try {
				this.rs = this.stm.executeQuery(query);
				if (this.rs.last()) {
					String date = this.rs.getString(1);
					if (!date.equals(today)) {
						postEndOfDayReport(openingBalance, cashReceived, firstReceipt, lastReceipt, totalcashBanked,
								today, cofirm);

					} else {
						new Notification("<h1>Today's End Of Day Cash Report has already been posted<h1/>", "",
								Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
					}
				} else {
					postEndOfDayReport(openingBalance, cashReceived, firstReceipt, lastReceipt, totalcashBanked, today,
							cofirm);
				}
			} catch (SQLException ee) {
				ee.printStackTrace();
			}
		});

		VerticalLayout layout = new VerticalLayout(horizontalLayout, table);
		layout.setSpacing(true);
		this.setContent(layout);
		this.setSizeFull();
		this.setModal(false);
		this.center();
		DateUtility dateUtility = new DateUtility();
		this.setCaption("Date " + dateUtility.getDate() + " Time " + dateUtility.getTime() + "End Of Day Cash Report");

	}

	public void postEndOfDayReport(double openingBalance, double cashReceived, String firstReceipt, String lastReceipt,
			double totalcashBanked, String today, AbstractComponent cofirm) {
		InsertDataIntoCashReportTable cashReportTable = new InsertDataIntoCashReportTable(stm);
		cashReportTable.insertData(openingBalance, cashReceived, Integer.parseInt(firstReceipt),
				Integer.parseInt(lastReceipt), totalcashBanked, userID, "pending approval", today, schoolID);
		cofirm.setEnabled(false);
		CashReportPDF cashReportPDF = new CashReportPDF();
		cashReportPDF.generatePDF(table, download);
		new Notification("Success", "End Of Day Cash Report was successfully processed",
				Notification.Type.HUMANIZED_MESSAGE, true).show(Page.getCurrent());

	}
}