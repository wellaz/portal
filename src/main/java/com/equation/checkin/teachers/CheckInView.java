package com.equation.checkin.teachers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.checkin.teacher.reports.TimeSheetSearchView;
import com.equation.main.banners.design.CheckinBanner;
import com.equation.student.autosearch.ClearAndFocusInputField;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.ui.TextField;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial", "deprecation" })
public class CheckInView extends CustomComponent implements View {
	ResultSet rs, rs1;
	Statement stm, stmt;
	TextField ecNumber, firstName, surname, gender, errand;
	PasswordField password;
	String ecNumberr;
	String schoolID, userID, userLevel, schoolName;
	String errandd;
	HorizontalLayout horizontalLayout = new HorizontalLayout();
	boolean automate = false;

	public String getErrandd() {
		return errandd;
	}

	public void setErrandd(String errandd) {
		this.errandd = errandd;
	}

	public String getEcNumberr() {
		return ecNumberr;
	}

	public void setEcNumberr(String ecNumberr) {
		this.ecNumberr = ecNumberr;
	}

	public CheckInView(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String schoolID, String userID,
			String userLevel, String schoolName) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.schoolName = schoolName;
		this.stmt = stmt;
		this.schoolID = schoolID;
		this.userID = userID;
		this.userLevel = userLevel;
		this.addStyleName("checkIn");
		this.setSizeFull();
		// this.setCompositionRoot(verticalLayout);

		VerticalLayout layout = new VerticalLayout(createTopBanner(), createBarcodeLayout());
		layout.setSpacing(true);
		this.setCompositionRoot(layout);

	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

	public HorizontalSplitPanel createBarcodeLayout() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setSizeFull();

		TextField autoField = new TextField("EC Number");
		autoField.setWidth("70%");
		autoField.setHeight("50%");
		autoField.addStyleName("barcode_field");
		autoField.focus();

		Button search = new Button("Search");
		search.setIcon(VaadinIcons.SEARCH);
		search.addStyleName(ValoTheme.BUTTON_PRIMARY);
		search.addStyleName("barcode_field_button");

		CheckBox mouseFreeMode = new CheckBox("Manual-Input", false);
		mouseFreeMode.setDescription("Enable keyboard input if the decoding device cannot function.");
		mouseFreeMode.setIcon(VaadinIcons.POINTER);
		mouseFreeMode.addStyleName("mouseFreeMode");

		mouseFreeMode.addValueChangeListener((e) -> {
			automate = (boolean) e.getValue();
		});

		autoField.addValueChangeListener((e) -> {
			search.focus();
			layout.removeAllComponents();
			String barcode = autoField.getValue();
			if (!barcode.equals("")) {
				if (!automate)
					startThread(barcode, autoField, mouseFreeMode, layout);
				else {

				}
			}
		});
		autoField.addFocusListener((e) -> {
			String barcode = autoField.getValue();
			if (!barcode.equals("")) {
				if (!automate)
					startThread(barcode, autoField, mouseFreeMode, layout);
				else {

				}
			}
		});

		autoField.addTextChangeListener((e) -> {
			if (!automate)
				search.focus();
			String barcode = autoField.getValue();
			if (!barcode.equals("")) {
				System.out.println("Text changed to " + barcode);
			}
		});

		search.addClickListener((e) -> {
			if (!automate)
				ClearAndFocusInputField.clearAndFocusBarcodeField(autoField);
			else {
				String barcode = autoField.getValue();
				if (!barcode.equals("")) {
					startThread(barcode, autoField, mouseFreeMode, layout);
				} else {
					autoField.focus();
				}
			}
		});
		search.addFocusListener((e) -> {
			if (!automate)
				ClearAndFocusInputField.clearAndFocusBarcodeField(autoField);
		});

		mouseFreeMode.addFocusListener((e) -> {
			ClearAndFocusInputField.clearAndFocusBarcodeField(autoField);
		});

		Button checkinCorrection = new Button("Correct User CheckIn");
		checkinCorrection.addStyleName(ValoTheme.BUTTON_DANGER);
		checkinCorrection.setIcon(VaadinIcons.BRIEFCASE);
		checkinCorrection.addClickListener((e) -> {
			Window window = new Window();
			window.setModal(true);
			window.setWidth("40%");
			window.setHeight("30%");
			window.center();
			window.setCaption("Correct User CheckIn And CheckOut");

			TextField ecnumber = new TextField("Enter EC Number");
			ecnumber.setDescription("Enter EC Number");
			ecnumber.focus();
			Button submit = new Button("Search");
			submit.setDescription("Search the record");
			submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
			submit.addClickListener((ee) -> {
				String value = ecnumber.getValue();
				System.out.println("Entered " + value);
				if (!value.equals("")) {
					String validate = "SELECT firstName FROM teachers WHERE ecNumber = '" + value.toUpperCase() + "'";
					try {
						rs = stm.executeQuery(validate);
						if (rs.next()) {
							System.out.println("Name is " + rs.getString(1));
							UI.getCurrent().removeWindow(window);
							window.close();
							new AdminCorrection(stm, stmt, rs, rs1).correct(value.toUpperCase(), window, ecnumber);
						} else {
							new Notification("<h3>Error<h3/>", "EC Number " + value + " does not exist!!!",
									Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
							ecnumber.selectAll();
						}
					} catch (SQLException eee) {
						eee.printStackTrace();
					}
				} else {
					ecnumber.focus();
				}
			});

			ecnumber.addValueChangeListener((ee) -> {
				String value = ecnumber.getValue();
				if (!value.equals("")) {
					String validate = "SELECT firstName FROM teachers WHERE ecNumber = '" + value.toUpperCase() + "'";
					try {
						rs = stm.executeQuery(validate);
						if (rs.next()) {
							System.out.println("Name is " + rs.getString(1));
							UI.getCurrent().removeWindow(window);
							window.close();
							new AdminCorrection(stm, stmt, rs, rs1).correct(value.toUpperCase(), window, ecnumber);
						} else {
							new Notification("<h3>Error<h3/>", "EC Number " + value + " does not exist!!!",
									Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
							ecnumber.selectAll();
						}
					} catch (SQLException eee) {
						eee.printStackTrace();
					}
				} else {
					ecnumber.focus();
				}
			});

			FormLayout formLayout = new FormLayout(ecnumber, submit);
			formLayout.setSpacing(true);
			HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
			horizontalLayout.setSpacing(true);
			VerticalLayout layout1 = new VerticalLayout(horizontalLayout);
			layout1.setSpacing(true);
			layout1.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
			window.setContent(layout1);
			UI.getCurrent().addWindow(window);
		});

		Button reports = new Button("Time Sheets");
		reports.setIcon(VaadinIcons.ARCHIVE);
		reports.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		reports.addClickListener((e) -> {
			TimeSheetSearchView searchView = new TimeSheetSearchView(stm, stmt, rs, rs1, schoolName);

			HorizontalLayout horizontalLayout = new HorizontalLayout(searchView);
			horizontalLayout.setSpacing(true);
			VerticalLayout vlayout = new VerticalLayout(horizontalLayout);
			vlayout.setSpacing(true);
			vlayout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);

			Window window = new Window();
			window.setModal(true);
			window.setWidth("60%");
			window.setHeight("60%");
			window.center();
			window.setCaption("Generate Time Sheets");

			window.setContent(vlayout);
			UI.getCurrent().addWindow(window);
		});

		VerticalLayout leftLayout = new VerticalLayout(autoField, search, mouseFreeMode);
		leftLayout.setSpacing(true);

		HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();

		splitPanel.setFirstComponent(leftLayout);

		splitPanel.setSplitPosition(25, Unit.PERCENTAGE);
		splitPanel.setSecondComponent(layout);
		return splitPanel;
	}

	public void startThread(String barcode, TextField barcodeField, CheckBox mouseFreeMode, VerticalLayout layout) {
		CheckIfECNumberExist exist = new CheckIfECNumberExist(stm, stmt, rs, rs1, schoolID, userID, userLevel, layout);
		exist.searchBarcodeAction(barcode, barcodeField, mouseFreeMode);
	}

	public HorizontalLayout createTopBanner() {
		return new CheckinBanner();
	}
}