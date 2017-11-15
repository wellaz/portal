package com.equation.school.statistics.students;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.school.classes.capture.AllSchoolClasses;
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
public class StudentStatisticsByClass extends CustomComponent implements View {

	ResultSet rs, rs1;
	Statement stm, stmt;
	String schoolID;
	private Table table;
	private Window window;
	TabSheet tabs;

	ComboBox<String> classesCombo;
	private Button search;

	public StudentStatisticsByClass(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, String schoolID,
			TabSheet tabs) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.schoolID = schoolID;
		this.tabs = tabs;

		classesCombo = new ComboBox<>("Class Name");
		classesCombo.setRequiredIndicatorVisible(true);
		classesCombo.setEmptySelectionAllowed(false);
		classesCombo.setItems(new AllSchoolClasses(this.rs1, this.stmt, this.schoolID).allClasses());

		search = new Button("Search");
		search.setIcon(VaadinIcons.SEARCH);
		search.addStyleName(ValoTheme.BUTTON_FRIENDLY);

		window = new Window();
		setWindow(window);
		getWindow().center();
		getWindow().setModal(false);
		getWindow().setSizeFull();

		search.addClickListener((e) -> {

			String classname = (String) classesCombo.getValue();

			if (!(classname.equals(""))) {
				VerticalLayout layout = new VerticalLayout();
				layout.setSpacing(true);

				if (!classname.equals("")) {
					getWindow().setCaption(classname + " Statistics Report");
					try {

						String query = " SELECT students.studentID,sFirstName,sSurname FROM students,classes,schools WHERE classes.classname ='"
								+ classname + "' AND schools.schoolID = '" + schoolID + "'";
						this.rs1 = this.stmt.executeQuery(query);
						this.rs1.last();
						int numberOfRows = this.rs1.getRow(), i = 0;
						if (numberOfRows > 0) {
							table = new Table();
							table.addContainerProperty("Student ID", String.class, null);
							table.addContainerProperty("Student name", String.class, null);

							String query1 = " SELECT students.studentID,sFirstName,sSurname FROM students,classes,schools WHERE classes.classname ='"
									+ classname + "' AND schools.schoolID = '" + schoolID + "'";

							this.rs = this.stm.executeQuery(query1);
							while (this.rs.next()) {
								String studenID = this.rs.getString(1);
								String studentname = this.rs.getString(2) + " " + this.rs.getString(3);

								table.addItem(new Object[] { studenID, studentname }, new Integer(i));
								i++;
							}
							double size1 = table.size();

							table.setFooterVisible(true);
							table.setColumnFooter("Student name",
									"Total " + String.valueOf(Math.floor(size1)) + " Records");
							table.setSizeFull();
							table.setPageLength((int) size1);
							// table.addStyleName("class_statistics_table");
							table.setSelectable(true);
							table.setColumnCollapsingAllowed(true);

							Button close = new Button("Close");
							close.addStyleName(ValoTheme.BUTTON_DANGER);
							close.setIcon(VaadinIcons.CLOSE);
							close.addClickListener((ev) -> {
								UI.getCurrent().removeWindow(getWindow());
								getWindow().close();
							});

							Button print = new Button("Print");
							print.addStyleName(ValoTheme.BUTTON_PRIMARY);
							print.setIcon(VaadinIcons.PRINT);

							Button download = new Button("Download");
							download.addStyleName(ValoTheme.BUTTON_FRIENDLY);
							download.setIcon(VaadinIcons.DOWNLOAD);

							HorizontalLayout horizontalLayout = new HorizontalLayout(download, print, close);
							horizontalLayout.setSpacing(true);
							horizontalLayout.setSizeFull();
							horizontalLayout.addStyleName("class_report_sub_window");

							layout.addComponents(horizontalLayout, table);
							getWindow().setContent(layout);

							StudentStatisticsByClassPDF statisticsByClassPDF = new StudentStatisticsByClassPDF();
							statisticsByClassPDF.generatePDF(classname, table, download);

							UI.getCurrent().addWindow(getWindow());

						} else {
							new Notification("Warning", "No Records found for class " + classname,
									Notification.TYPE_WARNING_MESSAGE, true).show(Page.getCurrent());
						}
					} catch (SQLException ee) {
						ee.printStackTrace();
					}
				} else {
					new Notification("Error", "Class " + classname + " not found", Notification.TYPE_ERROR_MESSAGE,
							true).show(Page.getCurrent());
				}
			} else {
				new Notification("Error", "Blank Fields cannot search a record", Notification.TYPE_ERROR_MESSAGE, true)
						.show(Page.getCurrent());
			}
		});
		HorizontalLayout horizontalLayout2 = new HorizontalLayout(classesCombo, search);
		horizontalLayout2.setSpacing(true);

		this.setCompositionRoot(horizontalLayout2);
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Class Statistics")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Class Statistics", VaadinIcons.QUESTION_CIRCLE);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
