package com.equation.teacher.primary.marks.capture;

import java.sql.ResultSet;
import java.sql.SQLException;
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
public class PrimaryMarksEntryFormControlButtons extends HorizontalLayout {
	Window window;
	Table table;
	Statement stm, stmt;
	ResultSet rs, rs1;
	String schoolID;
	String tablename, term;
	int year;

	public PrimaryMarksEntryFormControlButtons(Window window, Table table, Statement stm, Statement stmt, ResultSet rs,
			ResultSet rs1, String schoolID, String tablename, String term, int year, String subjectname) {
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
		save.setIcon(VaadinIcons.SELECT);

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

				String query = "SELECT total FROM " + tablename + " WHERE studentID='" + studentID + "'";
				try {
					this.rs1 = this.stmt.executeQuery(query);
					this.rs1.last();
					int ros = this.rs1.getRow();
					if (ros > 0) {
						String query01 = "SELECT total FROM " + tablename + " WHERE studentID='" + studentID + "'";
						this.rs = this.stm.executeQuery(query01);
						this.rs.next();
						int total = this.rs.getInt(1);
						int newTotal = total + Integer.parseInt(paper1) + Integer.parseInt(paper2);
						// update
						String query2 = "UPDATE " + tablename + " SET " + subjectname + " = '" + paper1 + "', "
								+ subjectname + "2" + " ='" + paper2 + "',total = '" + newTotal
								+ "' WHERE studentID = '" + studentID + "'";
						this.stm.executeUpdate(query2);

					} else {
						// insert
						int tottal = Integer.parseInt(paper1) + Integer.parseInt(paper2);
						new InsertDataIntoPrimaryMarksTable(this.stm).insertData(tablename, 0, 0, 0, 0, 0, 0, 0, 0, 0,
								0, 0, studentID, term, schoolID, year);
						String query2 = "UPDATE " + tablename + " SET " + subjectname + " = '" + paper1 + "', "
								+ subjectname + "2" + " ='" + paper2 + "',total = '" + tottal + "' WHERE studentID = '"
								+ studentID + "'";
						this.stm.executeUpdate(query2);
					}
				} catch (SQLException ee) {
					ee.printStackTrace();
				}
			}
		});
		this.addComponents(download, save, print, close);
	}
}