package com.equation.checkin.ancillary.reports;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import com.equation.teacher.details.capture.TeachersECNumbers;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class AncillaryTimeSheetSearchView extends CustomComponent implements View {
	Statement stm, stmt;
	ResultSet rs, rs1;
	String schoolName;

	public AncillaryTimeSheetSearchView(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String schoolName) {
		this.stm = stm;
		this.rs = rs;
		this.stmt = stmt;
		this.rs1 = rs1;
		this.schoolName = schoolName;

		VerticalLayout one = singeDate();
		VerticalLayout two = dateRange();
		VerticalLayout three = useECumber();

		TabSheet tabSheet = new TabSheet();
		tabSheet.addTab(one, "Single Date Time Sheet".toUpperCase(), VaadinIcons.CALENDAR);
		tabSheet.addTab(two, "Date Range Time Sheet".toUpperCase(), VaadinIcons.CALENDAR_O);
		tabSheet.addTab(three, "Personal Time Sheet".toUpperCase(), VaadinIcons.USERS);
		VerticalLayout verticalLayout = new VerticalLayout();

		HorizontalLayout gridLayout = new HorizontalLayout();
		gridLayout.setSpacing(true);
		gridLayout.addComponents(tabSheet);

		verticalLayout.addComponent(gridLayout);
		verticalLayout.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);
		this.setCompositionRoot(verticalLayout);
	}

	public VerticalLayout singeDate() {
		VerticalLayout layout = new VerticalLayout();
		DateField dateField = new DateField("Select Date:");
		dateField.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		dateField.setDateFormat("yyyy-MM-dd");
		dateField.setRequiredIndicatorVisible(true);

		Button search = new Button("Search");
		search.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		search.setIcon(VaadinIcons.SEARCH);
		search.addClickListener((e) -> {
			String value = String.format("%1$tY-%1$tm-%1$td", dateField.getValue());
			if (!value.equals("")) {
				new SingleDateTimeSheet(rs, rs1, stm, stmt).search(value, schoolName);
			} else {
				new Notification("Error", "Select Date", Notification.Type.WARNING_MESSAGE, true)
						.show(Page.getCurrent());
				dateField.focus();
			}
		});

		FormLayout formLayout = new FormLayout(dateField, search);
		formLayout.setSpacing(true);
		HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
		horizontalLayout.setSpacing(true);
		layout.addComponent(horizontalLayout);
		layout.setSpacing(true);
		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);

		return layout;
	}

	public VerticalLayout dateRange() {
		VerticalLayout layout = new VerticalLayout();
		DateField dateFrom = new DateField("Select Date (From):");
		dateFrom.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		dateFrom.setDateFormat("yyyy-MM-dd");
		dateFrom.setRequiredIndicatorVisible(true);

		DateField dateTo = new DateField("Select Date  (To):");
		dateTo.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		dateTo.setDateFormat("yyyy-MM-dd");
		dateTo.setRequiredIndicatorVisible(true);

		Button search = new Button("Search");
		search.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		search.setIcon(VaadinIcons.SEARCH);
		search.addClickListener((e) -> {
			String from = String.format("%1$tY-%1$tm-%1$td", dateFrom.getValue());
			String to = String.format("%1$tY-%1$tm-%1$td", dateTo.getValue());
			if (!(from.equals("") || to.equals(""))) {
				new DateRangeTimeSheet(rs, rs1, stm, stmt).search(from, to, schoolName);
			} else {
				new Notification("Error", "Select Date Range", Notification.Type.WARNING_MESSAGE, true)
						.show(Page.getCurrent());
				dateFrom.focus();
			}
		});

		FormLayout formLayout = new FormLayout(dateFrom, dateTo, search);
		formLayout.setSpacing(true);
		HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
		horizontalLayout.setSpacing(true);
		layout.addComponent(horizontalLayout);
		layout.setSpacing(true);
		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);

		return layout;
	}

	public VerticalLayout useECumber() {
		VerticalLayout layout = new VerticalLayout();
		ArrayList<String> ecnumbers = new TeachersECNumbers(rs, stm).ecNumbers();

		ComboBox<String> ecNumber = new ComboBox<>("Select EC Number");
		ecNumber.setRequiredIndicatorVisible(true);
		ecNumber.setItems(ecnumbers);
		ecNumber.setEmptySelectionAllowed(false);

		Button search = new Button("Search");
		search.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		search.setIcon(VaadinIcons.SEARCH);
		search.addClickListener((e) -> {
			String value = (String) ecNumber.getValue().toString();
			if (!ecNumber.getValue().toString().equals("")) {
				new WorkerIDTimeSheet(rs, rs1, stm, stmt).search(value, schoolName);
			} else {
				new Notification("Error", "Select EC Number", Notification.Type.WARNING_MESSAGE, true)
						.show(Page.getCurrent());
				ecNumber.focus();
			}
		});

		FormLayout formLayout = new FormLayout(ecNumber, search);
		formLayout.setSpacing(true);
		HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
		horizontalLayout.setSpacing(true);
		layout.addComponent(horizontalLayout);
		layout.setSpacing(true);
		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);

		return layout;

	}

	public void insertTab(TabSheet tabs) {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Ancillary Time Sheet")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Ancillary Time Sheet", VaadinIcons.ARCHIVE);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
