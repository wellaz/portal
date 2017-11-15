package com.equation.student.autosearch;

import java.sql.ResultSet;
import java.sql.Statement;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TabSheet;
import com.vaadin.v7.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings({ "serial", "deprecation" })
public class StudentAutoSearchView extends CustomComponent {

	ResultSet rs, rs1;
	Statement stm, stmt;
	private TextField autoField;
	private VerticalLayout layout;
	private Button search;
	private CheckBox mouseFreeMode;

	String schoolID, userID, userLevel;
	TabSheet tabs;

	public StudentAutoSearchView(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String schoolID,
			TabSheet tabs, String userID, String userLevel) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.schoolID = schoolID;
		this.tabs = tabs;
		this.userID = userID;
		this.userLevel = userLevel;

		layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setSizeFull();

		autoField = new TextField("Student Number");
		autoField.setWidth("70%");
		autoField.setHeight("50%");
		autoField.addStyleName("barcode_field");
		autoField.focus();

		search = new Button("Search");
		search.setIcon(VaadinIcons.SEARCH);
		search.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		search.addStyleName("barcode_field_button");

		mouseFreeMode = new CheckBox("Mouse-Free Mode", false);
		mouseFreeMode.setIcon(VaadinIcons.POINTER);
		mouseFreeMode.addStyleName("mouseFreeMode");

		autoField.addValueChangeListener((e) -> {
			search.focus();
			layout.removeAllComponents();

			String barcode = (String) e.getProperty().getValue();
			if (!barcode.equals("")) {
				startThread(barcode.toUpperCase(), autoField, mouseFreeMode, this.schoolID);
				System.out.println(barcode);
			}
		});
		autoField.addFocusListener((e) -> {
			String barcode = autoField.getValue();
			if (!barcode.equals("")) {
				startThread(barcode.toUpperCase(), autoField, mouseFreeMode, this.schoolID);
				System.out.println(barcode);
			}
		});

		autoField.addTextChangeListener((e) -> {
			search.focus();
			String barcode = autoField.getValue();
			if (!barcode.equals("")) {
				System.out.println("Text changed to " + barcode);
			}
		});

		search.addClickListener((e) -> {
			ClearAndFocusInputField.clearAndFocusBarcodeField(autoField);
		});
		search.addFocusListener((e) -> {
			ClearAndFocusInputField.clearAndFocusBarcodeField(autoField);
		});
		mouseFreeMode.addFocusListener((e) -> {
			ClearAndFocusInputField.clearAndFocusBarcodeField(autoField);
		});

		VerticalLayout leftLayout = new VerticalLayout(autoField, search, mouseFreeMode);
		leftLayout.setSpacing(true);

		HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();

		splitPanel.setFirstComponent(leftLayout);

		splitPanel.setSplitPosition(25, Unit.PERCENTAGE);
		splitPanel.setSecondComponent(layout);
		this.setCompositionRoot(splitPanel);
	}

	public void insertTab(TabSheet tabs) {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Auto Search")) {
				exist = true;
				tabs.setSelectedTab(x);
				// startThread();
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Auto Search", VaadinIcons.BARCODE);
			tabs.setSelectedTab(count);
			// startThread();
			tabs.getTab(this).setClosable(true);
		}
	}

	public void startThread(String barcode, TextField barcodeField, CheckBox mouseFreeMode, String schoolID) {
		SearchIfBarcodeExist exist = new SearchIfBarcodeExist(stm, stmt, rs, rs1, schoolID, tabs, userID, userLevel,
				layout);
		exist.searchBarcodeAction(barcode, barcodeField, mouseFreeMode, schoolID);
	}
}