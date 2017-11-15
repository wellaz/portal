package com.equation.accounts.vouchers.search;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.accounts.vouchers.search.expenses.relevant.VoucherExpenditureList;
import com.equation.accounts.vouchers.search.pdf.VouchersListPDF;
import com.equation.utils.print.currentpage.PrintCurrentPage;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.data.Item;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial", "deprecation" })
public class VouchersSearchWindowControlButtons extends HorizontalLayout {
	Window window;
	Table table;
	Statement stm, stmt;
	ResultSet rs, rs1;
	String schoolID;

	public VouchersSearchWindowControlButtons(Window window, Table table, Statement stm, Statement stmt, ResultSet rs,
			ResultSet rs1, String schoolID) {
		this.window = window;
		this.table = table;
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.schoolID = schoolID;

		this.setSpacing(true);
		Button download = new Button("Download");
		download.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		download.setIcon(VaadinIcons.DOWNLOAD);

		Button print = new Button("Print");
		print.addStyleName(ValoTheme.BUTTON_PRIMARY);
		print.setIcon(VaadinIcons.PRINT);

		Button close = new Button("Close");
		close.addStyleName(ValoTheme.BUTTON_DANGER);
		close.setIcon(VaadinIcons.CLOSE);
		this.table.setSortEnabled(false);
		this.table.setNullSelectionAllowed(false);

		this.table.addContextClickListener((e) -> {
			this.removeAllComponents();
			int row = (int) table.getValue();
			Item item = table.getItem(row);
			String voucherNumber = (String) item.getItemProperty("Voucher Number").getValue().toString();
			Label current = new Label();
			current.setValue("Selected: " + voucherNumber);

			VerticalLayout popupContent = new VerticalLayout();
			Panel panel = new Panel();
			panel.setCaption("Voucher Number " + voucherNumber + " Expenditure Breakdown");
			panel.setIcon(VaadinIcons.SUN_DOWN);
			VoucherExpenditureList expenditureList = new VoucherExpenditureList(this.stm, this.stmt, this.rs, this.rs1,
					voucherNumber, this.schoolID);
			panel.setContent(expenditureList);
			popupContent.addComponent(panel);
			PopupView popup = new PopupView(null, popupContent);
			this.addComponents(download, print, close, popup);
			popup.setPopupVisible(true);

			popup.addPopupVisibilityListener(event -> {
				if (event.isPopupVisible()) {
					popupContent.removeAllComponents();
					popupContent.addComponent(panel);
				}
			});

		});

		this.addComponents(download, print, close);

		close.addClickListener((e) -> {
			UI.getCurrent().removeWindow(this.window);
			this.window.close();
		});
		VouchersListPDF listPDF = new VouchersListPDF();
		listPDF.generatePDF(this.table, download);

		print.addClickListener((e) -> {
			PrintCurrentPage.print();
		});
	}
}