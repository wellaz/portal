package com.equation.accounts.bankingregister.search;

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
@SuppressWarnings({ "serial", "deprecation" })
public class SearchOneBankingRegisterEntry extends Panel {

	Statement stm, stmt;
	ResultSet rs, rs1;
	private ComboBox<String> searchCriteria;
	String schoolID;

	public SearchOneBankingRegisterEntry(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String schoolID) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.schoolID = schoolID;

		searchCriteria = new ComboBox<>("Search Criteria");
		searchCriteria.setRequiredIndicatorVisible(true);
		searchCriteria.setEmptySelectionAllowed(false);
		searchCriteria.setItems("Deposit Number", "Amount", "Messager ID", "Sender ID", "Date");
		VerticalLayout layout = new VerticalLayout(searchCriteria);
		layout.setSpacing(true);

		searchCriteria.addValueChangeListener((e) -> {
			String criteria = (String) e.getValue();
			switch (criteria) {
			case "Deposit Number": {
				layout.removeAllComponents();
				TextField depositNumbertxt = new TextField("Deposit Number");
				depositNumbertxt.setRequiredIndicatorVisible(true);

				Button search = new Button("Search");
				search.setIcon(VaadinIcons.SEARCH);
				search.addStyleName(ValoTheme.BUTTON_PRIMARY);
				search.addClickListener((ee) -> {
					String depositNumber = depositNumbertxt.getValue();
					if (!depositNumber.equals("")) {
						SearchBankingRegisterByDepositNumber bankingRegisterByDepositNumber = new SearchBankingRegisterByDepositNumber(
								rs, rs1, stm, stmt);
						UI.getCurrent()
								.addWindow(bankingRegisterByDepositNumber.createContentPanel(schoolID, depositNumber));
					} else {
						new Notification("<h1>Empty Deposit Number<h1/>", "", Notification.Type.ERROR_MESSAGE, true)
								.show(Page.getCurrent());
					}
				});
				layout.addComponents(searchCriteria, depositNumbertxt, search);
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
						SearchBankingRegisterByAmount bankingRegisterByAmount = new SearchBankingRegisterByAmount(rs,
								rs1, stm, stmt);

						UI.getCurrent().addWindow(bankingRegisterByAmount.createContentPanel(schoolID, amount));
					} else {
						new Notification("Error", "Empty amount", Notification.TYPE_ERROR_MESSAGE, true)
								.show(Page.getCurrent());
					}
				});
				layout.addComponents(searchCriteria, depositNumbertxt, search);
			}
				break;
			case "Messager ID": {
				layout.removeAllComponents();
				TextField depositNumbertxt = new TextField("Messanger ID");
				depositNumbertxt.setRequiredIndicatorVisible(true);

				Button search = new Button("Search");
				search.setIcon(VaadinIcons.SEARCH);
				search.addStyleName(ValoTheme.BUTTON_PRIMARY);
				search.addClickListener((ee) -> {
					String messangerid = depositNumbertxt.getValue();
					if (!messangerid.equals("")) {
						SearchBankingRegisterByMessanderID bankingRegisterByMessanderID = new SearchBankingRegisterByMessanderID(
								rs, rs1, stm, stmt);

						UI.getCurrent()
								.addWindow(bankingRegisterByMessanderID.createContentPanel(schoolID, messangerid));
					} else {
						new Notification("<h1>Empty Field Detected<h1/>", "", Notification.Type.ERROR_MESSAGE, true)
								.show(Page.getCurrent());
					}
				});
				layout.addComponents(searchCriteria, depositNumbertxt, search);

			}
				break;
			case "Sender ID": {
				layout.removeAllComponents();
				TextField depositNumbertxt = new TextField("Sender ID");
				depositNumbertxt.setRequiredIndicatorVisible(true);

				Button search = new Button("Search");
				search.setIcon(VaadinIcons.SEARCH);
				search.addStyleName(ValoTheme.BUTTON_PRIMARY);
				search.addClickListener((ee) -> {
					String senderID = depositNumbertxt.getValue();
					if (!senderID.equals("")) {
						SearchbankingRegisterBySenderID registerBySenderID = new SearchbankingRegisterBySenderID(rs,
								rs1, stm, stmt);

						UI.getCurrent().addWindow(registerBySenderID.createContentPanel(schoolID, senderID));
					} else {
						new Notification("Error", "Empty Field", Notification.TYPE_ERROR_MESSAGE, true)
								.show(Page.getCurrent());
					}
				});
				layout.addComponents(searchCriteria, depositNumbertxt, search);

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

					SearchbankingRegisterByDate byDate = new SearchbankingRegisterByDate(rs, rs1, stm, stmt);

					UI.getCurrent().addWindow(byDate.createContentPanel(schoolID, from, to));

				});
				layout.addComponents(searchCriteria, fromField, toField, search);

			}
				break;
			default: {
				new Notification("<h1>Invalid search criteria<h1/>", "", Notification.Type.ERROR_MESSAGE, true)
						.show(Page.getCurrent());
			}
				break;

			}

		});
		this.setContent(layout);
		this.setCaption("Search Banking Register");
		this.setIcon(VaadinIcons.SEARCH);

	}

}
