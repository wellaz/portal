package com.equation.accounts.administrator.fees.structure.search;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.accounts.administrator.feesstruturepdf.CurrentFeesStructureTable;
import com.equation.accounts.administrator.feesstruturepdf.GenerateFeesStructureReport;
import com.equation.accounts.bankingregister.search.BankingRegisterSearchResultsControlButtons;
import com.equation.school.details.retrieve.RetrieveSchoolDetails;
import com.equation.util.years.ranges.PaymentHistoryYears;
import com.equation.utils.date.DateUtility;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "deprecation", "serial" })
public class SearchFeesStructure extends Panel {
	Statement stm, stmt;
	ResultSet rs, rs1;
	private ComboBox<String> searchCriteria;
	String schoolID;

	public SearchFeesStructure(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String schoolID) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.schoolID = schoolID;

		searchCriteria = new ComboBox<>("Select Year");
		searchCriteria.setRequiredIndicatorVisible(true);
		searchCriteria.setItems(PaymentHistoryYears.allYears());

		Button generate = new Button("Download Current Fees Structure");
		generate.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		generate.setIcon(VaadinIcons.DOWNLOAD);
		generate.addClickListener((e) -> {
			String thisYear = new DateUtility().getYear();

			Table table = new CurrentFeesStructureTable(this.rs, this.rs1, this.stm, this.stmt, this.schoolID)
					.createCurrentFeesStructureTable(thisYear);
			GenerateFeesStructureReport feesStructureReport = new GenerateFeesStructureReport();
			RetrieveSchoolDetails details = new RetrieveSchoolDetails(this.rs, this.rs1, this.stm, this.stmt,
					this.schoolID);
			String schoolName = details.getSchoolName();
			String schoolPostal = details.getSchoolPostal();
			String schoolPhone = details.getSchoolPhone();
			String schoolCell = details.getSchoolCell();
			String schoolEmail = details.getSchoolEmail();
			Button download = new Button("Download File");
			feesStructureReport.generatePDF(table, schoolName, schoolPostal, schoolPhone, schoolCell, schoolEmail,
					download);

			Window window = new Window("Download Fees Structure");

			Button close = new Button("Close");
			close.setIcon(VaadinIcons.CLOSE);
			close.addStyleName(ValoTheme.BUTTON_DANGER);
			close.addClickListener((ee) -> {
				UI.getCurrent().removeWindow(window);
				window.close();
			});

			download.addStyleName(ValoTheme.BUTTON_PRIMARY);
			download.setIcon(VaadinIcons.DOWNLOAD);
			HorizontalLayout horizontalLayout = new HorizontalLayout(download, close);
			horizontalLayout.setSpacing(true);

			VerticalLayout layout = new VerticalLayout(horizontalLayout);
			layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
			window.setContent(layout);
			window.setModal(true);
			window.setWidth("40%");
			window.setHeight("30%");
			UI.getCurrent().addWindow(window);

		});

		VerticalLayout layout = new VerticalLayout(generate, searchCriteria);
		layout.setSpacing(true);

		searchCriteria.addValueChangeListener((e) -> {
			String criteria = (String) e.getValue();
			String query = "SELECT admin,tution,bspz,building,generalPurpose,sports,levi,other,total,dateSet,year FROM feestructure,schools WHERE year = '"
					+ criteria + "'";
			try {
				this.rs1 = this.stmt.executeQuery(query);
				this.rs1.last();
				int rows = this.rs1.getRow(), i = 0;
				if (rows > 0) {

					Table grid = new Table(); 
					grid.addContainerProperty("Administration", Double.class, null);
					grid.addContainerProperty("Tuition", Double.class, null);
					grid.addContainerProperty("B S P Z", Double.class, null);
					grid.addContainerProperty("Building", Double.class, null);
					grid.addContainerProperty("General Purpose", Double.class, null);
					grid.addContainerProperty("Sports", Double.class, null);
					grid.addContainerProperty("Levy", Double.class, null);
					grid.addContainerProperty("Other", Double.class, null);
					grid.addContainerProperty("TOTAL", Double.class, null);
					grid.addContainerProperty("Date Set", String.class, null);
					grid.addContainerProperty("Year", String.class, null);
					grid.setSelectable(true);

					String query1 = "SELECT admin,tution,bspz,building,generalPurpose,sports,levi,other,total,dateSet,year FROM feestructure,schools WHERE year = '"
							+ criteria + "'";
					this.rs = this.stm.executeQuery(query1);
					while (this.rs.next()) {
						double admin = this.rs.getDouble(1);

						double tution = this.rs.getDouble(2);
						double bspz = this.rs.getDouble(3);
						double building = this.rs.getDouble(4);
						double generalPurpose = this.rs.getDouble(5);
						double sports = this.rs.getDouble(6);
						double levi = this.rs.getDouble(7);
						double other = this.rs.getDouble(8);
						double total = this.rs.getDouble(9);
						String dateSet = this.rs.getString(10);
						int year = this.rs.getInt(11);

						grid.addItem(new Object[] { admin, tution, bspz, building, generalPurpose, sports, levi, other,
								total, dateSet, String.valueOf(year) }, new Integer(i));
						i++;
					}
					int size = grid.size();
					String ret = (size > 1) ? " Records" : " Record";
					grid.setColumnCollapsingAllowed(true);
					grid.setFooterVisible(true);
					grid.setColumnFooter("Administration", String.valueOf(size) + ret);
					grid.setSizeFull();
					grid.setPageLength(grid.size());
					grid.addStyleName("students_gender_table");

					Window window = new Window("Search Results");
					window.center();
					window.setSizeFull();

					VerticalLayout layout1 = new VerticalLayout(
							new BankingRegisterSearchResultsControlButtons(window, grid), grid);
					layout1.setSpacing(true);
					window.setContent(layout1);

					UI.getCurrent().addWindow(window);

				} else {
					new Notification("<h1>No Fees Structure Set for year " + criteria + "<h1/>", "",
							Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
				}

			} catch (SQLException ee) {
				ee.printStackTrace();
			}

		});
		this.setContent(layout);
		this.setCaption("Search Sundry Report Record");
		this.setIcon(VaadinIcons.SEARCH);
		this.addStyleName(ValoTheme.PANEL_WELL);

	}

}
