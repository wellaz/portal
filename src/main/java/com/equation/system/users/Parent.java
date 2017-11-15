package com.equation.system.users;

import java.sql.ResultSet;
import java.sql.Statement;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class Parent extends CustomComponent implements View {
	boolean canIhide = false;
	TabSheet tabs;
	Statement stm, stmt;
	ResultSet rs, rs1;

	public Parent(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;

		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSpacing(true);
		// HorizontalLayout banner = new
		// com.equation.banners.design.TopBanner();
		Button hide = new Button();
		hide.setIcon(VaadinIcons.ARROW_CIRCLE_UP_O);
		hide.setDescription("Hide Toolbar");
		hide.addStyleName(ValoTheme.BUTTON_QUIET);

		HorizontalLayout toolbar = new HorizontalLayout(createToolBar(), hide);
		toolbar.setSpacing(true);

		hide.addClickListener((e) -> {
			if (!canIhide) {
				toolbar.removeAllComponents();
				toolbar.addComponent(hide);
				hide.setIcon(VaadinIcons.ARROW_CIRCLE_DOWN_O);
				hide.setDescription("Show Toolbar");
				canIhide = true;
			} else {
				toolbar.addComponents(createToolBar(), hide);
				hide.setIcon(VaadinIcons.ARROW_CIRCLE_UP_O);
				hide.setDescription("Hide Toolbar");
				canIhide = false;
			}
		});
		toolbar.addStyleName(ValoTheme.LAYOUT_WELL);
		tabs = new TabSheet();
		tabs.addTab(createFirstPage(), "WELCOME", VaadinIcons.USER);
		tabs.setResponsive(true);
		tabs.addStyleName(ValoTheme.TABSHEET_ONLY_SELECTED_TAB_IS_CLOSABLE);
		VerticalLayout mainContent = new VerticalLayout(tabs);
		mainContent.addStyleName(ValoTheme.LAYOUT_CARD);
		verticalLayout.addComponent(createTopBanner());
		verticalLayout.addComponent(toolbar);
		verticalLayout.addComponent(mainContent);

		verticalLayout.setComponentAlignment(mainContent, Alignment.MIDDLE_CENTER);
		verticalLayout.setMargin(false);
		this.setCompositionRoot(verticalLayout);

	}

	public HorizontalLayout createTopBanner() {
		return new com.equation.main.banners.design.ParentTopBanner(rs, rs, stm, stm);
	}

	private TabSheet createToolBar() {
		TabSheet sheet = new TabSheet();
		return sheet;
	}

	private VerticalLayout createFirstPage() {
		VerticalLayout layout = new VerticalLayout();
		return layout;
	}

}
