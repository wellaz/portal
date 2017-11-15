package com.equation.accounts.vouchers.search;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.Date;

import com.equation.utils.date.DateUtility;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial" })
public class SearchVouchers extends Panel {
	Statement stm, stmt;
	ResultSet rs, rs1;
	private ComboBox<String> searchCriteria;
	String schoolID;

	public SearchVouchers(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String schoolID) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.schoolID = schoolID;

		searchCriteria = new ComboBox<>("Search Criteria");
		searchCriteria.setRequiredIndicatorVisible(true);
		searchCriteria.setEmptySelectionAllowed(false);
		searchCriteria.setItems("Amount", "Voucher Number", "Date");
		VerticalLayout layout = new VerticalLayout(searchCriteria);
		layout.setSpacing(true);

		searchCriteria.addValueChangeListener((e) -> {
			String criteria = (String) e.getValue();
			switch (criteria) {
			case "Voucher Number": {
				layout.removeAllComponents();
				TextField depositNumbertxt = new TextField("Receint / Invoice Number");
				depositNumbertxt.setRequiredIndicatorVisible(true);

				Button search = new Button("Search");
				search.setIcon(VaadinIcons.SEARCH);
				search.addStyleName(ValoTheme.BUTTON_PRIMARY);
				search.addClickListener((ee) -> {
					String receiptNumber = depositNumbertxt.getValue();
					if (!receiptNumber.equals("")) {
						SearchVoucherByVoucherNumber byReceiptNumber = new SearchVoucherByVoucherNumber(this.rs,
								this.rs1, this.stm, this.stmt);
						UI.getCurrent().addWindow(byReceiptNumber.createContentPanel(schoolID, receiptNumber));
					} else {
						new Notification("<h1>Empty receipt Number<h1/>", "", Notification.Type.ERROR_MESSAGE, true)
								.show(Page.getCurrent());
					}
				});
				layout.addComponents(searchCriteria, depositNumbertxt, search);
				depositNumbertxt.focus();
			}
				break;
			case "Amount": {
				layout.removeAllComponents();
				TextField depositNumbertxt = new TextField("Amount");
				depositNumbertxt.setRequiredIndicatorVisible(true);
				// depositNumbertxt.addValidator(new DoubleValidator("Enter
				// amount here "));

				Button search = new Button("Search");
				search.setIcon(VaadinIcons.SEARCH);
				search.addStyleName(ValoTheme.BUTTON_PRIMARY);
				search.addClickListener((ee) -> {
					String amoutt = depositNumbertxt.getValue();
					if (!amoutt.equals("")) {
						double amount = Double.parseDouble(amoutt);
						SearchVoucherByAmount byAmount = new SearchVoucherByAmount(this.rs, this.rs1, this.stm,
								this.stmt);

						UI.getCurrent().addWindow(byAmount.createContentPanel(schoolID, amount));
					} else {
						new Notification("Error", "Empty amount", Notification.Type.ERROR_MESSAGE, true)
								.show(Page.getCurrent());
					}
				});
				layout.addComponents(searchCriteria, depositNumbertxt, search);
				depositNumbertxt.focus();
			}
				break;
			case "Date": {
				layout.removeAllComponents();
				DateField fromField = new DateField("Beginning from");
				fromField.setValue(
						new DateUtility().firstOfJanuary().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				fromField.setDateFormat("yyyy-MM-dd");
				fromField.setRequiredIndicatorVisible(true);

				DateField toField = new DateField("Ending on");
				toField.setRequiredIndicatorVisible(true);
				toField.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				toField.setDateFormat("yyyy-MM-dd");

				Button search = new Button("Search");
				search.setIcon(VaadinIcons.SEARCH);
				search.addStyleName(ValoTheme.BUTTON_PRIMARY);
				search.addClickListener((ee) -> {
					String from = String.format("%1$tY-%1$tm-%1$td", fromField.getValue());
					String to = String.format("%1$tY-%1$tm-%1$td", toField.getValue());

					SearchVouchersByDate byDate = new SearchVouchersByDate(this.rs, this.rs1, this.stm, this.stmt);

					UI.getCurrent().addWindow(byDate.createContentPanel(schoolID, from, to));

				});
				layout.addComponents(searchCriteria, fromField, toField, search);

			}
				break;
			default: {
				new Notification("Error", "Invalid search criteria", Notification.Type.ERROR_MESSAGE, true)
						.show(Page.getCurrent());
			}
				break;
			}
		});
		this.setContent(layout);
		this.setCaption("Search Vouchers Record");
		this.setIcon(VaadinIcons.SEARCH);
	}
}