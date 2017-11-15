package com.equation.teacher.secondary.marks.capture;

import java.sql.ResultSet;
import java.sql.Statement;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.data.Container;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial", "deprecation" })
public class SecondaryMarksEntryFormControlButtons extends HorizontalLayout {
	Window window;
	Table table;
	Statement stm, stmt;
	ResultSet rs, rs1;
	String schoolID;
	String tablename, term, year;

	public SecondaryMarksEntryFormControlButtons(Window window, Table table, Statement stm, Statement stmt,
			ResultSet rs, ResultSet rs1, String schoolID, String tablename, String term, String year) {
		this.window = window;
		this.table = table;
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.schoolID = schoolID;
		this.tablename = tablename;
		this.term = term;
		this.year = year;

		this.setSpacing(true);
		Button download = new Button("Download");
		download.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		download.setIcon(VaadinIcons.DOWNLOAD);

		Button print = new Button("Print");
		print.addStyleName(ValoTheme.BUTTON_PRIMARY);
		print.setIcon(VaadinIcons.PRINT);

		Button close = new Button("Close");
		close.addStyleName(ValoTheme.BUTTON_DANGER);
		close.setIcon(VaadinIcons.CLOSE);

		Button save = new Button("Save");
		save.addStyleName(ValoTheme.BUTTON_DANGER);
		save.setIcon(VaadinIcons.SAFE);

		close.addClickListener((e) -> {
			UI.getCurrent().removeWindow(this.window);
			this.window.close();
		});
		download.addClickListener((e) -> {

		});
		print.addClickListener((e) -> {
			JavaScript.getCurrent().execute("print();");
		});

		save.addClickListener((e) -> {
			Container container = (Container) table.getContainerDataSource();
			int size1 = container.size();
			for (int i = 0; i < size1; i++) {

				String studentID = container.getContainerProperty(i, "Student ID").getValue().toString();
				String paper1 = container.getContainerProperty(i, "Paper One").getValue().toString();
				String paper2 = container.getContainerProperty(i, "Paper Two").getValue().toString();

				new InsertMarksIntoSecondarySubject(stm).insertData(this.tablename, studentID, paper1, paper2,
						this.term, this.year);

			}

		});
		this.addComponents(download, save, print, close);
	}

}
