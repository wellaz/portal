package com.equation.shool.classes.payment.report;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.school.classes.capture.AllSchoolClasses;
import com.equation.util.school.fees.now.GetSchoolFeesForThisTerm;
import com.equation.util.years.ranges.PaymentHistoryYears;
import com.equation.utils.print.currentpage.PrintCurrentPage;
import com.equation.utils.schoolterms.AllYearTermsBin;
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
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
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
public class ClassPaymentReport extends CustomComponent implements View {

	ResultSet rs, rs1;
	Statement stm, stmt;
	String schoolID;
	private Table table;
	private Window window;
	TabSheet tabs;

	ComboBox<String> termCombo, yearCombo, classesCombo;
	private Button search;

	String[] header = { "Student ID", "Student name", "Amount", "Balance" };

	public ClassPaymentReport(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, String schoolID,
			TabSheet tabs) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.schoolID = schoolID;
		this.tabs = tabs;

		classesCombo = new ComboBox<>("Class Name");
		/*
		 * classesCombo.setRequired(true);
		 * classesCombo.setNullSelectionAllowed(false);
		 */
		classesCombo.setItems(new AllSchoolClasses(this.rs1, this.stmt, this.schoolID).allClasses());
		classesCombo.setScrollToSelectedItem(true);
		// classesCombo.addItems(new AllSchoolClasses(this.rs1, this.stmt,
		// this.schoolID).allClasses());

		termCombo = new ComboBox<>("Term");

		termCombo.setItems(AllYearTermsBin.allTermsArray());
		termCombo.setScrollToSelectedItem(true);

		yearCombo = new ComboBox<>("Year");

		yearCombo.setItems(PaymentHistoryYears.allYears());
		yearCombo.setScrollToSelectedItem(true);

		search = new Button("Search");
		search.setIcon(VaadinIcons.SEARCH);
		search.addStyleName(ValoTheme.BUTTON_FRIENDLY);

		window = new Window();
		setWindow(window);
		getWindow().center();
		getWindow().setModal(false);
		getWindow().setSizeFull();

		search.addClickListener((e) -> {

			String classname = (String) classesCombo.getValue();
			String year = (String) yearCombo.getValue();
			String term = (String) termCombo.getValue();

			if (!(classname.equals("") || year.equals("") || term.equals(""))) {
				VerticalLayout layout = new VerticalLayout();
				layout.setSpacing(true);

				if (!classname.equals("")) {
					double currentFees = new GetSchoolFeesForThisTerm(this.stmt, this.rs1).getSchoolFees(schoolID);
					getWindow().setCaption(classname + " " + term + " " + year + " Payment Report");
					try {
						String query = " SELECT feesreceived.studentID,sFirstName,sSurname,SUM(amount) FROM students,feesreceived,classes,schools WHERE classes.classname ='"
								+ classname + "' AND schools.schoolID = '" + schoolID
								+ "' GROUP BY feesreceived.studentID";
						this.rs1 = this.stmt.executeQuery(query);
						this.rs1.last();
						int numberOfRows = this.rs1.getRow(), i = 0;
						Object[][] data = new Object[numberOfRows][header.length];
						if (numberOfRows > 0) {
							table = new Table();
							for (String h : header) {
								table.addContainerProperty(h, String.class, null);
							}

							String query1 = " SELECT feesreceived.studentID,sFirstName,sSurname,SUM(amount) FROM students,feesreceived,classes,schools WHERE classes.classname ='"
									+ classname + "' AND schools.schoolID = '" + schoolID
									+ "' GROUP BY feesreceived.studentID";

							this.rs = this.stm.executeQuery(query1);
							double sum = 0, totatBalances = 0;

							while (this.rs.next()) {
								String studenID = this.rs.getString(1);
								String studentname = this.rs.getString(2) + " " + this.rs.getString(3);
								double amount = this.rs.getDouble(4);
								double balance = currentFees - amount;

								data[i][0] = studenID;
								data[i][1] = studentname;
								data[i][2] = amount;
								data[i][3] = balance;

								sum += amount;
								totatBalances += balance;
								i++;
							}
							double size1 = data.length;
							for (int y = 0; y < size1; y++) {
								table.addItem(data[i], new Integer(i));
							}

							table.setFooterVisible(true);
							table.setColumnFooter("Receipt Number",
									"Total " + String.valueOf(Math.floor(size1)) + " Records");
							table.setColumnFooter("Amount", "Sum $" + String.valueOf(sum));
							table.setColumnFooter("Balance", "Total Balance $" + String.valueOf(totatBalances));
							table.setSizeFull();
							table.setPageLength((int) size1);
							table.addStyleName("payments_table");
							table.setSelectable(true);
							table.setColumnCollapsingAllowed(true);

							Button close = new Button("Close");
							close.addStyleName(ValoTheme.BUTTON_DANGER);
							close.setIcon(VaadinIcons.CLOSE);
							close.addClickListener((ev) -> {
								UI.getCurrent().removeWindow(getWindow());
								getWindow().close();
							});

							Button print = new Button("Print");
							print.addStyleName(ValoTheme.BUTTON_PRIMARY);
							print.setIcon(VaadinIcons.PRINT);
							print.addClickListener((er) -> {
								PrintCurrentPage.print();
							});

							Button download = new Button("Download");
							download.addStyleName(ValoTheme.BUTTON_FRIENDLY);
							download.setIcon(VaadinIcons.DOWNLOAD);
							ClassPaymentReportPDF classPaymentReportPDF = new ClassPaymentReportPDF();
							classPaymentReportPDF.generatePDF(classname, term, year, table, download);

							HorizontalLayout horizontalLayout = new HorizontalLayout(download, print, print);
							horizontalLayout.setSpacing(true);
							horizontalLayout.setSizeFull();
							horizontalLayout.addStyleName("class_report_sub_window");

							layout.addComponents(horizontalLayout, table);
							getWindow().setContent(layout);
							UI.getCurrent().addWindow(getWindow());
						} else {
							new Notification("Warning",
									"No Records found for class " + classname + " for " + term + " " + year,
									Notification.TYPE_WARNING_MESSAGE, true).show(Page.getCurrent());
						}
					} catch (SQLException ee) {
						ee.printStackTrace();
					}
				} else {
					new Notification("Error", "Class " + classname + " not found", Notification.TYPE_ERROR_MESSAGE,
							true).show(Page.getCurrent());
				}
			} else {
				new Notification("Error", "Blank Fields cannot search a record", Notification.TYPE_ERROR_MESSAGE, true)
						.show(Page.getCurrent());
			}
		});

		FormLayout formLayout = new FormLayout(termCombo, yearCombo, classesCombo, search);
		formLayout.setCaption("<h1>Generate Class Payment Report<h1/>");
		formLayout.setCaptionAsHtml(true);
		formLayout.setSpacing(true);
		HorizontalLayout horizontalLayout2 = new HorizontalLayout(formLayout);
		horizontalLayout2.setSpacing(true);

		VerticalLayout layout = new VerticalLayout(horizontalLayout2);
		layout.setSpacing(true);
		layout.setComponentAlignment(horizontalLayout2, Alignment.MIDDLE_CENTER);

		this.setCompositionRoot(layout);
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Class Payments")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Class Payments", VaadinIcons.QUESTION_CIRCLE);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
