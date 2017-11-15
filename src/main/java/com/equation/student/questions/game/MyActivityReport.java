package com.equation.student.questions.game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.v7.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial", "deprecation" })
public class MyActivityReport extends Window {
	Statement stm, stmt;
	ResultSet rs, rs1;
	String studentname;

	public MyActivityReport(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String studentname) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.studentname = studentname;

		Button proceed = new Button("Close");
		proceed.addStyleName(ValoTheme.BUTTON_DANGER);

		this.center();
		this.setModal(false);
		this.setSizeFull();

		proceed.addClickListener((ev) -> {
			UI.getCurrent().removeWindow(this);
			this.close();

		});
		String name = studentname;

		VerticalLayout layout1 = new VerticalLayout();
		layout1.setSpacing(true);
		layout1.addComponent(proceed);

		if (!name.equals("")) {
			this.setCaption(name.toUpperCase() + " Performance Report");

			try {
				String q = "SELECT subject,grade,COUNT(subject),AVG(percentage) FROM studentactivities WHERE firstName = '"
						+ name + "' GROUP BY subject,grade";
				this.rs = this.stm.executeQuery(q);
				this.rs.last();
				int numRows = this.rs.getRow(), i = 0;
				if (numRows > 0) {
					Table[] table = new Table[numRows];

					String q1 = "SELECT subject,grade,COUNT(subject),AVG(percentage) FROM studentactivities WHERE firstName = '"
							+ name + "' GROUP BY subject,grade";
					this.rs1 = this.stmt.executeQuery(q1);
					while (this.rs1.next()) {
						String subjectt = this.rs1.getString(1);
						int gradee = this.rs1.getInt(2);
						int rows = this.rs1.getInt(3);
						double average = this.rs1.getDouble(4);

						table[i] = new Table(subjectt);

						table[i].addContainerProperty("Subject", String.class, null);
						table[i].addContainerProperty("Number Of Sessions", Integer.class, null);
						table[i].addContainerProperty("Class / Grade", Integer.class, null);
						table[i].addContainerProperty("Average Pass Rate", Double.class, null);

						table[i].addItem(new Object[] { subjectt, rows, gradee, Math.floor(average) }, new Integer(i));

						String va = (rows > 1) ? rows + " Records" : rows + " Record";
						table[i].setFooterVisible(true);
						table[i].setColumnFooter("Subject", va + ". Pass Rate");
						table[i].setColumnFooter("Average Pass Rate", String.valueOf(Math.ceil(average)) + "%");
						table[i].setWidth("80%");
						table[i].setPageLength((int) table[i].size());
						table[i].addStyleName("students_performance_table");
						table[i].setSelectable(true);
						table[i].setColumnCollapsingAllowed(true);

						layout1.addComponent(table[i]);
						i++;

					}
					this.setContent(layout1);
					// UI.getCurrent().addWindow((answerWindow));
				} else {
					new Notification("Warning", "No Performance Record Found For " + name.toUpperCase(),
							Notification.TYPE_WARNING_MESSAGE, true).show(Page.getCurrent());
				}
			} catch (SQLException ee) {
				ee.printStackTrace();
			}
		} else {
			new Notification("Error", "Blank Fields cannot search any student record", Notification.TYPE_ERROR_MESSAGE,
					true).show(Page.getCurrent());
		}
	}
}