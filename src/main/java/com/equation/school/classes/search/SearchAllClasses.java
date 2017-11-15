package com.equation.school.classes.search;

import java.sql.ResultSet;
import java.sql.Statement;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class SearchAllClasses extends Panel {
	Statement stm, stmt;
	ResultSet rs, rs1;
	String schoolID;

	public SearchAllClasses(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String schoolID) {
		this.stm = stm;
		this.stmt = stmt;
		this.rs = rs;
		this.rs1 = rs;
		this.schoolID = schoolID;

		Button search = new Button("List All School Classes");
		search.addStyleName(ValoTheme.BUTTON_PRIMARY);
		search.setIcon(VaadinIcons.SEARCH);

		search.addClickListener((e) -> {

		});

		this.setCaption("List A  Classes");
		this.setIcon(VaadinIcons.SEARCH);
		this.setContent(search);

	}

}
