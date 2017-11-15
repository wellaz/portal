package com.equation.accounts.bankingregister;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.accounts.bankingregister.search.SearchOneBankingRegisterEntry;
import com.equation.utils.date.DateUtility;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class CaptureBankingRegisterDetails extends CustomComponent implements View {

	TextField depositNumber, amount, messangerIDSign, senderIDSign;
	ComboBox<String> messagerID, senderID;
	TextArea comments;
	Statement stm, stmt;
	ResultSet rs, rs1;
	TabSheet tabs;
	String schoolID;

	@SuppressWarnings("deprecation")
	public CaptureBankingRegisterDetails(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, TabSheet tabs,
			String schoolID) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.schoolID = schoolID;
		this.tabs = tabs;

		depositNumber = new TextField("Deposit Number");
		depositNumber.setRequiredIndicatorVisible(true);

		amount = new TextField("Amount");
		amount.setRequiredIndicatorVisible(true);

		messangerIDSign = new TextField("Messanger Signature");
		messangerIDSign.setRequiredIndicatorVisible(true);

		senderIDSign = new TextField("Sender Signature");
		senderIDSign.setRequiredIndicatorVisible(true);

		messagerID = new ComboBox<>("Messanger");
		messagerID.setRequiredIndicatorVisible(true);

		senderID = new ComboBox<>("Sender");
		senderID.setRequiredIndicatorVisible(true);

		comments = new TextArea("Comments");
		comments.setWordWrap(true);

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.addClickListener((e) -> {
			String depositNumberr = depositNumber.getValue();
			String amountt = amount.getValue();
			String messagerIDd = (String) messagerID.getValue();
			String messangerIDSignn = messangerIDSign.getValue();
			String senderIDd = (String) senderID.getValue();
			String senderIDSignn = senderIDSign.getValue();
			String commentss = comments.getValue();

			if (!(depositNumberr.equals("") || amountt.equals("") || messagerIDd.equals("")
					|| messangerIDSignn.equals("") || senderIDd.equals("") || senderIDSignn.equals("")
					|| commentss.equals(""))) {
				int depositNumberrr = Integer.parseInt(depositNumberr);
				double amounttt = Double.parseDouble(amountt);
				String now = new DateUtility().getDate();

				InsertDataIntoBankingRegister bankingRegister = new InsertDataIntoBankingRegister(stm);
				bankingRegister.insertData(depositNumberrr, amounttt, messagerIDd, messangerIDSignn, senderIDd,
						senderIDSignn, commentss, now, schoolID);
				Button download = new Button("Download File");
				download.addStyleName(ValoTheme.BUTTON_PRIMARY);
				download.setIcon(VaadinIcons.DOWNLOAD);

				BankingRegisterPDF bankingRegisterPDF = new BankingRegisterPDF();
				bankingRegisterPDF.generatePDF(now, depositNumberr, messagerIDd, messangerIDSignn, senderIDd,
						senderIDSignn, amounttt, commentss, download);
				Button close = new Button("Close");
				close.addStyleName(ValoTheme.BUTTON_DANGER);
				close.setIcon(VaadinIcons.CLOSE);
				Window window = new Window("Download File");
				close.addClickListener((ee) -> {
					UI.getCurrent().removeWindow(window);
					window.close();
				});
				HorizontalLayout horizontalLayout = new HorizontalLayout(download, close);
				horizontalLayout.setSpacing(true);

				VerticalLayout layout = new VerticalLayout(horizontalLayout);
				layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
				window.setContent(layout);
				window.setModal(true);
				window.setWidth("40%");
				window.setHeight("30%");
				UI.getCurrent().addWindow(window);

				depositNumber.clear();
				amount.clear();
				messangerIDSign.clear();
				senderIDSign.clear();
				messagerID.clear();
				senderID.clear();

				new Notification("Success", "Banking Register Record Submitted", Notification.TYPE_HUMANIZED_MESSAGE,
						true).show(Page.getCurrent());
			} else {
				new Notification("Error", "A Blank field has been detected", Notification.TYPE_ERROR_MESSAGE, true)
						.show(Page.getCurrent());
			}
		});
		FormLayout formLayout = new FormLayout(depositNumber, amount, messangerIDSign, senderIDSign, messagerID,
				senderID);
		formLayout.setCaption("Banking Register Entry Form");
		formLayout.setSpacing(true);
		formLayout.setIcon(VaadinIcons.BRIEFCASE);

		FormLayout formLayout2 = new FormLayout(comments, submit);
		formLayout2.setCaption("Write any comments");
		formLayout2.setIcon(VaadinIcons.FILE_TEXT);
		formLayout2.setSpacing(true);

		VerticalLayout verticalLayout = new VerticalLayout();

		HorizontalLayout gridLayout = new HorizontalLayout();
		gridLayout.setSpacing(true);
		gridLayout.addComponents(formLayout, formLayout2);

		verticalLayout.addComponents(gridLayout);
		verticalLayout.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);

		Panel verticalLayout1 = new SearchOneBankingRegisterEntry(stm, stmt, rs, rs1, schoolID);
		verticalLayout1.setCaption("Search Banking Register");

		HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
		splitPanel.setFirstComponent(verticalLayout1);
		splitPanel.setSecondComponent(verticalLayout);
		splitPanel.setSplitPosition(15, Unit.PERCENTAGE);

		this.setCompositionRoot(splitPanel);

	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Banking Register")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Banking Register", VaadinIcons.MONEY);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}