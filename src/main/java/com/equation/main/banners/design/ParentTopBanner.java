package com.equation.main.banners.design;

import java.sql.ResultSet;
import java.sql.Statement;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class ParentTopBanner extends HorizontalLayout {
	ResultSet rs, rs1;
	Statement stm, stmt;

	public ParentTopBanner(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.setStyleName("top_lay");
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm; 
		this.stmt = stmt;

		Label l = new Label("\u2211QUATION      " + VaadinIcons.CLOUD.getHtml(), ContentMode.HTML);
		l.addStyleName("main_label");
		this.addComponent(l);

		Button logout = new Button("Sign Out");

		logout.addStyleName(ValoTheme.BUTTON_QUIET);
		logout.addStyleName("system_logout");
		logout.addClickListener((e) -> {

			getSession().close();

		});
		this.setComponentAlignment(l, Alignment.MIDDLE_LEFT);
		this.addComponent(logout);
		this.setComponentAlignment(logout, Alignment.MIDDLE_RIGHT);
	}

}
