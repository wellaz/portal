package com.equation.student.activity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.school.secondary.subjects.bin.AllSecondarySubjectsBin;
import com.equation.user.session.attributes.UserSessionAttributes;
import com.equation.utils.primary.grades.AllPrimaryGrades;
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
public class ViewStudentAcitivity extends CustomComponent implements View {
	Statement stm, stmt;
	ResultSet rs, rs1;
	private ComboBox<String> subjects;
	private ComboBox<String> grades;
	private TextField studentNameField;
	private Button search;
	private Table grid;
	TabSheet tabs;

	public ViewStudentAcitivity(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, TabSheet tabs) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.tabs = tabs;

		VerticalLayout layout = new VerticalLayout();
		subjects = new ComboBox<>("Choose Subject");
		subjects.setRequiredIndicatorVisible(true);
		subjects.setEmptySelectionAllowed(false);

		grades = new ComboBox<>();
		grades.setEmptySelectionAllowed(false);
		grades.setRequiredIndicatorVisible(true);

		String departmentode = (String) UI.getCurrent().getSession().getAttribute(UserSessionAttributes.DEPT_CODE)
				.toString();
		switch (departmentode) {
		case "3720":
			subjects.setItems(AllSecondarySubjectsBin.allSubjectsArray());

			grades.setCaption("Select Class (Form)");
			grades.setItems(AllPrimaryGrades.allGrades().subList(0, AllPrimaryGrades.allGrades().size() - 1));

			break;
		case "3730":

			subjects.setItems(AllPrimarySubjects.alSubjects());

			grades.setCaption("Select Class (Grade)");
			grades.setItems(AllPrimaryGrades.allGrades());
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

		HorizontalLayout horizontalLayout = new HorizontalLayout(grades, subjects, studentNameField, search);
		horizontalLayout.setSpacing(true);
		horizontalLayout.addStyleName(ValoTheme.LAYOUT_CARD);
		search.addStyleName("student_report_search_button");

		search.addClickListener((e) -> {
			String subject = (String) subjects.getValue().toString();
			String name = studentNameField.getValue();
			String grade = (String) grades.getValue().toString();

			if (!(subject.equals("") || name.equals("") || grade.equals(""))) {

				String query = "SELECT * FROM studentactivities WHERE 	firstName = '" + name + "' AND	subject = '"
						+ subject + "' AND	grade ='" + grade + "'";
				try {
					this.rs1 = this.stmt.executeQuery(query);
					this.rs1.last();
					int rows = this.rs1.getRow();

					if (rows > 0) {
						// Panel panel = new Panel();
						// layout.removeAllComponents();
						String query1 = "SELECT * FROM studentactivities WHERE 	firstName = '" + name
								+ "' AND	subject = '" + subject + "' AND	grade ='" + grade + "'";

						grid = new Table();

						grid.addContainerProperty("Class", Integer.class, null);
						grid.addContainerProperty("Percentage", Double.class, null);
						int i = 0;
						double performance = 0;
						this.rs = this.stm.executeQuery(query1);
						while (this.rs.next()) {
							// String firstName = this.rs.getString(2);
							// String subjectt = this.rs.getString(3);
							int gradee = this.rs.getInt(4);
							double percentage = this.rs.getDouble(5);
							performance += percentage;

							grid.addItem(new Object[] { gradee, Math.floor(percentage) }, new Integer(i));
							i++;
						}
						double size = grid.size();
						double avegare = Math.floor(performance / size);

						grid.setFooterVisible(true);
						grid.setColumnFooter("Class", String.valueOf(size) + " Records. Pass Rate");
						grid.setColumnFooter("Percentage", String.valueOf(avegare) + "%");
						grid.setWidth("80%");
						grid.setPageLength((int) size);
						grid.addStyleName("students_gender_table");
						grid.setSelectable(true);
						grid.setColumnCollapsingAllowed(true);
						// panel.setContent(grid);
						// layout.addComponent(panel);
						createWindow(grid, name, subject);
					} else {
						new Notification("Warning", "No Data Found Here", Notification.TYPE_WARNING_MESSAGE, true)
								.show(Page.getCurrent());
					}

				} catch (SQLException ee) {
					ee.printStackTrace();
				}

			} else {
				new Notification("Error", "Blank Fields in the search criteria", Notification.TYPE_WARNING_MESSAGE,
						true).show(Page.getCurrent());
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

	public Window createWindow(Table grid, String name, String subject) {
		Window answerWindow = new Window("Student: " + name.toUpperCase() + " Subject: " + subject.toUpperCase());
		Button proceed = new Button("Close");
		proceed.addStyleName(ValoTheme.BUTTON_DANGER);
		VerticalLayout subContent = new VerticalLayout(grid, proceed);
		subContent.setMargin(true);
		subContent.setSpacing(true);
		answerWindow.setContent(subContent);
		answerWindow.center();
		answerWindow.setModal(false);
		answerWindow.setHeight("600px");
		answerWindow.setWidth("400px");
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
			if (tabs.getTab(x).getCaption().equals("Student Activity")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Student Activity", VaadinIcons.QUESTION);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}
}
