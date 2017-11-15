package com.equation.school.furniture.search;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.school.names.collection.SchoolNames;
import com.equation.system.environment.determine.SchoolEmisNumber;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class RetrieveFurnutureDetails extends CustomComponent implements View {

	ResultSet rs, rs1;
	Statement stm, stmt;
	TabSheet tabs;

	ComboBox <String>searchCriteria;
	private ComboBox<String> schoolNames;

	public RetrieveFurnutureDetails(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, TabSheet tabs) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.tabs = tabs;

		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		searchCriteria = new ComboBox<>("Select Search Criteria");
		searchCriteria.setItems("Chairs", "Desks", "Tables", "Portable White Boards");
		searchCriteria.setRequiredIndicatorVisible(true);
		searchCriteria.setEmptySelectionAllowed(false);

		schoolNames = new ComboBox<>("Select School");
		schoolNames.setEmptySelectionAllowed(false);
		schoolNames.setItems(new SchoolNames(stm, rs).schoolCollection());

		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSpacing(true);
		horizontalLayout.addComponent(searchCriteria);
		searchCriteria.addValueChangeListener((e) -> {
			String value = (String) e.getValue();
			String emis = new SchoolEmisNumber().getEmisThatExist();
			layCompomonents(emis, horizontalLayout, layout, value);
			/*
			 * switch (value) { case "Chairs": { layCompomonents(emis,
			 * horizontalLayout, layout, value); } break; case "Desks": {
			 * layCompomonents(emis, horizontalLayout, layout, value);
			 * 
			 * } break; case "Tables": {
			 * 
			 * } break; case "Portable White Boards": {
			 * 
			 * } break; default: { new Notification("Error",
			 * "Invalid Search criteria", Notification.TYPE_WARNING_MESSAGE,
			 * true) .show(Page.getCurrent()); } break; }
			 */

		});

		Panel panel = new Panel();
		panel.setSizeFull();

		layout.addComponents(horizontalLayout, panel);
		this.setCompositionRoot(layout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	public void layCompomonents(String emis, HorizontalLayout horizontalLayout, VerticalLayout layout, String value) {
		if (emis.startsWith("d")) {
			horizontalLayout.removeAllComponents();
			horizontalLayout.addComponents(searchCriteria, schoolNames);
			schoolNames.addValueChangeListener((e) -> {
				String schoolname = (String) e.getValue();
				layout.removeAllComponents();
				Panel p = new SearchTables(rs, rs1, stm, stmt).createContentPanel(schoolname, value);
				layout.addComponents(horizontalLayout, p);
			});

		} else {
			layout.removeAllComponents();
			Panel p = new SearchTables(rs, rs1, stm, stmt).createContentPanel(emis, value);
			layout.addComponents(horizontalLayout, p);
		}
	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Search Furniture")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Search Furniture", VaadinIcons.SEARCH);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}
}