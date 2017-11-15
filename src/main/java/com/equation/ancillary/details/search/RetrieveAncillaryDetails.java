package com.equation.ancillary.details.search;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.school.names.collection.SchoolNames;
import com.equation.system.environment.determine.SchoolEmisNumber;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class RetrieveAncillaryDetails extends CustomComponent implements View {

	ResultSet rs, rs1;
	Statement stm, stmt;
	TabSheet tabs;

	ComboBox<String> searchCriteria;
	private ComboBox<String> schoolNames;

	public RetrieveAncillaryDetails(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, TabSheet tabs) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.tabs = tabs;

		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		searchCriteria = new ComboBox<>("Select Search Criteria");
		searchCriteria.setItems("Worker ID", "Firstname", "Surname", "Gender", "National ID", "Nationality",
				"Marital Status");
		searchCriteria.setRequiredIndicatorVisible(true);
		searchCriteria.setEmptySelectionAllowed(false);

		schoolNames = new ComboBox<>("Select School");
		schoolNames.setEmptySelectionAllowed(false);
		schoolNames.setItems(new SchoolNames(stm, rs).schoolCollection());

		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSpacing(true);
		horizontalLayout.addComponent(searchCriteria);

		Panel panel = new Panel();
		panel.setSizeFull();
		searchCriteria.addValueChangeListener((e) -> {
			String value = (String) e.getValue();
			String emis = new SchoolEmisNumber().getEmisThatExist();
			switch (value) {
			case "Worker ID": {
				TextField input = new TextField("Worker ID");
				input.setRequiredIndicatorVisible(true);
				Button submit = new Button("Search");
				submit.setIcon(VaadinIcons.SEARCH);
				submit.addStyleName(ValoTheme.BUTTON_DANGER);
				horizontalLayout.addComponents(input, submit);
				submit.addClickListener((eee) -> {
					String workerID = input.getValue();
					if (!workerID.equals("")) {

					} else {

					}
				});
			}
				break;
			case "Firstname": {
				if (emis.startsWith("d")) {

				} else {

				}

			}
				break;
			case "Surname": {
				if (emis.startsWith("d")) {

				} else {

				}

			}
				break;
			case "Gender": {
				if (emis.startsWith("d")) {

				} else {

				}

			}
				break;
			case "National ID": {
				if (emis.startsWith("d")) {

				} else {

				}

			}
				break;
			case "Nationality": {
				if (emis.startsWith("")) {

				} else {

				}

			}
				break;
			default: {
				new Notification("Error", "Unknown Search criteria", Notification.Type.ERROR_MESSAGE, true)
						.show(Page.getCurrent());
			}
				break;
			}

		});

		layout.addComponents(horizontalLayout, panel);
		this.setCompositionRoot(layout);
	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Search Books")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Search Books", VaadinIcons.SEARCH);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
