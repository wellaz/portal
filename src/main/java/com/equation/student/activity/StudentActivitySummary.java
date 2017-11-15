package com.equation.student.activity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.school.secondary.subjects.bin.AllSecondarySubjectsBin;
import com.equation.user.session.attributes.UserSessionAttributes;
import com.equation.utils.primary.subjects.AllPrimarySubjects;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial", "deprecation" })
public class StudentActivitySummary extends CustomComponent implements View {
	Statement stm, stmt;
	ResultSet rs, rs1;
	private ComboBox<String> subjects;
	private TextField studentNameField;
	private Button search;
	TabSheet tabs;

	public StudentActivitySummary(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, TabSheet tabs) {
		this.rs = rs;  
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.tabs = tabs;

		VerticalLayout layout = new VerticalLayout();
		subjects = new ComboBox<>("Choose Student Subject");
		subjects.setRequiredIndicatorVisible(true);
		subjects.setEmptySelectionAllowed(false);

		String departmentode = (String) UI.getCurrent().getSession().getAttribute(UserSessionAttributes.DEPT_CODE)
				.toString();
		switch (departmentode) {
		case "3720":
			subjects.setItems(AllSecondarySubjectsBin.allSubjectsArray());
			break;
		case "3730":
			subjects.setItems(AllPrimarySubjects.alSubjects());
			break;
		default:
			new Notification(
					"<h1>An Error occured whie trying to retrieve a marks entry form.<br/>Please contact the administrator to verify school details!<h1/>",
					"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
		}

		studentNameField = new TextField("Student Full Name");
		studentNameField.setRequiredIndicatorVisible(true);

		search = new Button("Search");
		search.addStyleName("continuous_search_button");
		search.addStyleName(ValoTheme.BUTTON_PRIMARY);
		search.setIcon(VaadinIcons.SEARCH_PLUS);

		HorizontalLayout horizontalLayout = new HorizontalLayout(studentNameField, search);
		horizontalLayout.setSpacing(true);
		horizontalLayout.addStyleName(ValoTheme.LAYOUT_CARD);
		search.addStyleName("student_report_search_button");

		search.addClickListener((e) -> {
			// String subject = (String) subjects.getValue();
			String name = studentNameField.getValue();

			VerticalLayout layout1 = new VerticalLayout();
			layout1.setSpacing(true);

			if (!name.equals("")) {

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

							table[i].addItem(new Object[] { subjectt, rows, gradee, Math.floor(average) },
									new Integer(i));

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
						createWindow(layout1, name);
					} else {
						new Notification("Warning", "No Performance Record Found For " + name.toUpperCase(),
								Notification.TYPE_WARNING_MESSAGE, true).show(Page.getCurrent());
					}
				} catch (SQLException ee) {

				}
			} else {
				new Notification("Error", "Blank Fields cannot search any student record",
						Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());

			}

		});
		layout.addComponents(horizontalLayout);
		layout.setSpacing(true);
		this.setCompositionRoot(layout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	public Window createWindow(VerticalLayout layout, String name) {
		Window answerWindow = new Window(name.toUpperCase() + " Performance Report");
		Button proceed = new Button("Close");
		proceed.addStyleName(ValoTheme.BUTTON_DANGER);
		HorizontalLayout horizontalLayout = new HorizontalLayout(proceed);
		VerticalLayout subContent = new VerticalLayout(horizontalLayout, layout);
		subContent.setMargin(true);
		subContent.setSpacing(true);
		answerWindow.setContent(subContent);
		answerWindow.center();
		answerWindow.setModal(false);
		answerWindow.setSizeFull();
		UI.getCurrent().addWindow((answerWindow));

		proceed.addClickListener((ev) -> {
			UI.getCurrent().removeWindow(answerWindow);
			answerWindow.close();

		});
		return answerWindow;
	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Student Record")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Student Record", VaadinIcons.QUESTION);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
			studentNameField.focus();
		}
	}
}