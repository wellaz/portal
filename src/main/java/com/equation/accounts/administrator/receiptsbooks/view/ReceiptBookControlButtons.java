package com.equation.accounts.administrator.receiptsbooks.view;

import com.equation.utils.print.currentpage.PrintCurrentPage;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial", "deprecation" })
public class ReceiptBookControlButtons extends HorizontalLayout {
	Window window;
	Table table;

	public ReceiptBookControlButtons(Window window, Table table) {
		this.window = window;
		this.table = table;
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

		this.addComponents(download, print, close);

		close.addClickListener((e) -> {
			UI.getCurrent().removeWindow(this.window);
			this.window.close();
		});
		ReceiptBookReportPDF bookReportPDF = new ReceiptBookReportPDF();
		bookReportPDF.generatePDF(this.table, download);

		print.addClickListener((e) -> {
			PrintCurrentPage.print();
		});
	}
}