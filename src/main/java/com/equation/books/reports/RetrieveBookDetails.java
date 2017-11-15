package com.equation.books.reports;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.books.reports.search.SearchBookByBookName;
import com.equation.books.reports.search.SearchBookByISBN;
import com.equation.books.reports.search.SearchBookBySubjectName;
import com.equation.school.names.collection.SchoolNames;
import com.equation.system.environment.determine.SchoolEmisNumber;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
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
public class RetrieveBookDetails extends Panel {

	ResultSet rs, rs1;
	Statement stm, stmt;
	TabSheet tabs;

	ComboBox<String> searchCriteria;
	private ComboBox<String> schoolNames;

	public RetrieveBookDetails(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, TabSheet tabs) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.tabs = tabs;

		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		searchCriteria = new ComboBox<>("Select Search Criteria");
		searchCriteria.setItems("Book Title", "Subject Name", "Book ISBN");
		searchCriteria.setRequiredIndicatorVisible(true);
		searchCriteria.setEmptySelectionAllowed(false);

		schoolNames = new ComboBox<>("Select School");
		schoolNames.setEmptySelectionAllowed(false);
		schoolNames.setItems(new SchoolNames(stm, rs).schoolCollection());

		VerticalLayout horizontalLayout = new VerticalLayout();
		horizontalLayout.setSpacing(true);
		horizontalLayout.addComponent(searchCriteria);

		searchCriteria.addValueChangeListener((e) -> {
			String value = (String) e.getValue();
			String emis = new SchoolEmisNumber().getEmisThatExist();
			switch (value) {
			case "Book Title": {
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, schoolNames);
					Button search = new Button("Search");
					search.addStyleName(ValoTheme.BUTTON_DANGER);
					search.setIcon(VaadinIcons.SEARCH);
					TextField input = new TextField("Book Title");
					input.setRequiredIndicatorVisible(true);
					schoolNames.addValueChangeListener((eer) -> {
						String schoolname = (String) eer.getValue();
						horizontalLayout.removeAllComponents();
						horizontalLayout.addComponents(searchCriteria, schoolNames, input, search);
						search.addClickListener((eew) -> {
							String booktitle = input.getValue();
							Panel p = new SearchBookByBookName(rs, rs1, stm, stmt).createContentPanel(schoolname,
									booktitle);
							layout.removeAllComponents();
							layout.addComponents(horizontalLayout, p);
						});
					});

				} else {
					TextField input = new TextField("Book Title");
					input.setRequiredIndicatorVisible(true);
					Button search = new Button("Search");
					search.addStyleName(ValoTheme.BUTTON_DANGER);
					search.setIcon(VaadinIcons.SEARCH);
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, input, search);
					search.addClickListener((ee) -> {
						String booktitle = input.getValue();
						Panel p = new SearchBookByBookName(rs, rs1, stm, stmt).createContentPanel(emis, booktitle);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
				}
			}
				break;
			case "Subject Name": {
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, schoolNames);

					ComboBox<String> input = new ComboBox<>("Subject");
					input.setRequiredIndicatorVisible(true);
					schoolNames.addValueChangeListener((eer) -> {
						String schoolname = (String) eer.getValue();
						horizontalLayout.removeAllComponents();
						horizontalLayout.addComponents(searchCriteria, schoolNames, input);
						input.addValueChangeListener((ert) -> {
							String subject = (String) ert.getValue();
							Panel p = new SearchBookBySubjectName(rs, rs1, stm, stmt).createContentPanel(schoolname,
									subject);
							layout.removeAllComponents();
							layout.addComponents(horizontalLayout, p);
						});
					});
				} else {
					ComboBox<String> input = new ComboBox<>("Subject");

					input.setRequiredIndicatorVisible(true);
					input.addValueChangeListener((ev) -> {
						String subject = (String) ev.getValue();
						Panel p = new SearchBookBySubjectName(rs, rs1, stm, stmt).createContentPanel(emis, subject);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, input);
				}
			}
				break;
			case "Book ISBN": {
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, schoolNames);
					Button search = new Button("Search");
					search.addStyleName(ValoTheme.BUTTON_DANGER);
					search.setIcon(VaadinIcons.SEARCH);
					TextField input = new TextField("Book ISBN");
					input.setRequiredIndicatorVisible(true);
					schoolNames.addValueChangeListener((eer) -> {
						String schoolname = (String) eer.getValue();
						horizontalLayout.removeAllComponents();
						horizontalLayout.addComponents(searchCriteria, schoolNames, input, search);
						search.addClickListener((eew) -> {
							String iSBN = input.getValue();
							Panel p = new SearchBookByISBN(rs, rs1, stm, stmt).createContentPanel(schoolname, iSBN);
							layout.removeAllComponents();
							layout.addComponents(horizontalLayout, p);
						});
					});

				} else {
					TextField input = new TextField("Book Title");
					input.setRequiredIndicatorVisible(true);
					Button search = new Button("Search");
					search.addStyleName(ValoTheme.BUTTON_DANGER);
					search.setIcon(VaadinIcons.SEARCH);
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, input, search);
					search.addClickListener((ee) -> {
						String iSBN = input.getValue();
						Panel p = new SearchBookByISBN(rs, rs1, stm, stmt).createContentPanel(emis, iSBN);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
				}
			}
				break;
			default: {
				new Notification("Error", "Invalid Search crteria", Notification.Type.WARNING_MESSAGE, true)
						.show(Page.getCurrent());
			}
				break;
			}
		});
		layout.addComponents(horizontalLayout);
		this.setContent(layout);
		this.setCaption("Search Book");
		this.setIcon(VaadinIcons.SEARCH);
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
}