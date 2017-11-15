package com.equation.accounts.search.console;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.Date;

import com.equation.accounts.collection.AllSchoolAccounts;
import com.equation.accounts.collection.MatchAccountWithItsTable;
import com.equation.accounts.search.reports.AccountReportPDF;
import com.equation.utils.date.DateUtility;
import com.equation.utils.print.currentpage.PrintCurrentPage;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
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
public class SearchAccountData extends CustomComponent implements View {

	String labels[] = { "Description", "", "Withdrawal", "Deposit", "Balance" };
	String footers[] = { "Ecs:Exit, ", "PgUp:Scroll Up, ", "PgDn:Scroll Down, ", "End:Last Record, ", "F1:More Help",
			"[ENTER]:Next Page" };
	String lbl = "*** Transaction History Enquiry ***";
	ComboBox<String> accountsCombo;
	DateField fromField, toField;
	Button search;
	ResultSet rs, rs1;
	Statement stm, stmt;
	TextArea display;
	TabSheet tabs;
	private Table grid;

	public SearchAccountData(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, TabSheet tabs) {
		this.stm = stm;
		this.rs = rs;
		this.stmt = stmt;
		this.rs1 = rs1;
		this.tabs = tabs;

		VerticalLayout horizontalSplitPanel = createvarticalLayout();
		horizontalSplitPanel.setSizeFull();

		// VerticalLayout verticalLayout1 = new VerticalLayout();

		// verticalLayout1.addComponent(verticalLayout);
		// verticalLayout1.setComponentAlignment(verticalLayout,
		// Alignment.MIDDLE_CENTER);

		this.setCompositionRoot(horizontalSplitPanel);
	}

	public VerticalLayout createvarticalLayout() {
		accountsCombo = new ComboBox<>("Select Account");
		accountsCombo.setRequiredIndicatorVisible(true);
		accountsCombo.setEmptySelectionAllowed(false);
		accountsCombo.setItems(AllSchoolAccounts.allAccountsArray());

		fromField = new DateField("Beginning from");
		fromField.setValue(new DateUtility().firstOfJanuary().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		fromField.setDateFormat("yyyy-MM-dd");
		fromField.setRequiredIndicatorVisible(true);

		toField = new DateField("Ending on");
		toField.setRequiredIndicatorVisible(true);
		toField.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		toField.setDateFormat("yyyy-MM-dd");

		display = new TextArea();
		display.addStyleName("accounts_search_editor");
		display.setWordWrap(true);
		display.setSizeFull();

		grid = new Table("Account History Enquiry From Inception To Date");
		grid.addContainerProperty("Description", String.class, null);
		grid.addContainerProperty("Withdral", String.class, null);
		grid.addContainerProperty("Deposit", String.class, null);
		grid.addContainerProperty("Balance", String.class, null);
		grid.setSizeFull();
		grid.setSelectable(true);

		Button searchbutton = new Button("Search");
		searchbutton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		searchbutton.setIcon(FontAwesome.SEARCH);
		searchbutton.addStyleName("account_search_button");

		Label mainlbl = new Label(lbl);
		HorizontalLayout searchLayout = new HorizontalLayout(mainlbl, accountsCombo, fromField, toField, searchbutton);
		searchLayout.setSpacing(true);

		searchbutton.addClickListener((e) -> {
			String accountname = (String) accountsCombo.getValue();
			if (!(accountname.equals(""))) {
				String date = String.format("%1$tY-%1$tm-%1$td", fromField.getValue());
				String date1 = String.format("%1$tY-%1$tm-%1$td", toField.getValue());

				String tablename = new MatchAccountWithItsTable().accountTableName(accountname);
				AccountSearchDaemon accountSearchDaemon = new AccountSearchDaemon(rs, rs1, stm, stmt);
				accountSearchDaemon.search(tablename, date, date1, grid);

				int size = labels.length;
				Label[] collumns = new Label[size];
				HorizontalLayout horizontalLayout = new HorizontalLayout();
				horizontalLayout.setSpacing(true);
				horizontalLayout.setWidth("100%");
				horizontalLayout.addStyleName("accounts_search_collumms_container");
				for (int i = 0; i < size; i++) {
					collumns[i] = new Label(labels[i]);
					collumns[i].addStyleName("accounts_search_collumms_labels");
					horizontalLayout.addComponent(collumns[i]);
				}

				UI.getCurrent().addWindow(createWindow(grid, horizontalLayout, accountname, date, date1));
			}
		});

		// area.setEnabled(false);

		VerticalLayout verticalLayout = new VerticalLayout(searchLayout);

		return verticalLayout;

	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Accounts Queries")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Accounts Queries", VaadinIcons.MONEY);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	public Window createWindow(Table grid, HorizontalLayout layout, String accountname, String from, String to) {
		Button download = new Button("Download");
		download.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		download.setIcon(VaadinIcons.DOWNLOAD);

		Button print = new Button("Print");
		print.addStyleName(ValoTheme.BUTTON_PRIMARY);
		print.setIcon(VaadinIcons.PRINT);

		Button close = new Button("Close");
		close.addStyleName(ValoTheme.BUTTON_DANGER);
		close.setIcon(VaadinIcons.CLOSE);

		HorizontalLayout horizontalLayout = new HorizontalLayout(download, print, close);
		horizontalLayout.setSpacing(true);

		Window window = new Window(accountname + " Account Extract From " + from + " to " + to);
		window.setIcon(VaadinIcons.MONEY);
		window.center();
		window.setSizeFull();
		window.setModal(false);
		close.addClickListener((e) -> {
			UI.getCurrent().removeWindow(window);
			window.close();
		});
		AccountReportPDF accountReportPDF = new AccountReportPDF();
		accountReportPDF.generatePDF(accountname, from, to, grid, download);
		print.addClickListener((e) -> {
			PrintCurrentPage.print();
		});
		VerticalLayout layout2 = new VerticalLayout(horizontalLayout, layout, grid);
		layout2.setSpacing(true);
		window.setContent(layout2);
		return window;
	}
}