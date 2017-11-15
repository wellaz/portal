package com.equation.teacher.secondary.marks.capture;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.school.classes.collection.AllClasses;
import com.equation.school.secondary.subjects.bin.AllSecondarySubjectsBin;
import com.equation.teacher.secondary.subjects.bin.ReturnTableName;
import com.equation.util.years.ranges.PaymentHistoryYears;
import com.equation.utils.date.DateUtility;
import com.equation.utils.date.AllTerms;
import com.equation.utils.schoolterms.AllYearTermsBin;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
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
public class SecondarySchoolMarksEntryForm extends CustomComponent implements View {

	ComboBox<String> subjects, myClass;

	ResultSet rs, rs1;
	Statement stm, stmt;
	String tablename = null;
	TabSheet tabs;
	String schoolID;

	private ComboBox<String> terms;

	private ComboBox<String> years;

	public SecondarySchoolMarksEntryForm(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, TabSheet tabs,
			String schoolID) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.tabs = tabs;
		this.schoolID = schoolID;

		// RetrieveStudentName studentName = new RetrieveStudentName(this.rs,
		// this.stm);
		ReturnTableName returnTableName = new ReturnTableName();

		subjects = new ComboBox<>("Select Subject");
		subjects.setRequiredIndicatorVisible(true);
		subjects.setItems(AllSecondarySubjectsBin.allSubjectsArray());
		subjects.setEmptySelectionAllowed(false);
		subjects.addValueChangeListener((event) -> {
			String subject = (String) event.getValue();
			tablename = returnTableName.getTableName(subject);

		});

		myClass = new ComboBox<>("Class Name");
		myClass.setRequiredIndicatorVisible(true);
		myClass.setEmptySelectionAllowed(false);
		myClass.setItems(new AllClasses(this.stm, this.rs).classesCollection(this.schoolID));

		terms = new ComboBox<>("Term");
		terms.setRequiredIndicatorVisible(true);
		terms.setEmptySelectionAllowed(false);
		terms.setItems(AllYearTermsBin.allTermsArray());
		terms.setValue(new AllTerms().thisTerm());

		years = new ComboBox<>("Year");
		years.setRequiredIndicatorVisible(true);
		years.setEmptySelectionAllowed(false);
		years.setItems(PaymentHistoryYears.allYears());
		years.setValue(new DateUtility().getYear());

		HorizontalLayout horizontalLayout1 = new HorizontalLayout(terms, years);
		horizontalLayout1.setSpacing(true);

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.addClickListener((e) -> {
			String classname = (String) myClass.getValue();
			String subject = (String) subjects.getValue();
			String term = (String) terms.getValue();
			String year = (String) years.getValue();

			if (!(classname.equals("") || subject.equals("") || term.equals("") || year.equals(""))) {

				String query = "SELECT studentID,sFirstName,sSurname FROM students,classes,schools WHERE classes.classname = '"
						+ classname + "' AND schools.schoolID = '" + this.schoolID + "'";
				try {
					this.rs1 = this.stmt.executeQuery(query);
					this.rs1.last();
					int rows = this.rs1.getRow(), i = 0;
					// Object[][] data = new Object[rows][4];
					if (rows > 0) {

						String query1 = "SELECT studentID,sFirstName,sSurname FROM students,classes,schools WHERE classes.classname = '"
								+ classname + "' AND schools.schoolID = '" + this.schoolID + "'";
						this.rs = this.stm.executeQuery(query1);
						Table grid = new Table();

						grid.setCaption(subject.toUpperCase() + " " + classname + " Marks Entry Form");
						grid.addContainerProperty("Student ID", String.class, null);
						grid.addContainerProperty("Student Name", String.class, null);
						grid.addContainerProperty("Paper One", String.class, null);
						grid.addContainerProperty("Paper Two", String.class, null);
						while (this.rs.next()) {
							String studentId = this.rs.getString(1);
							String studentFullName = this.rs.getString(2) + " " + this.rs.getString(3);

							// data[i][0] = (studentId);
							// data[i][1] = (studentFullName);
							// data[i][2] = ("");
							// data[i][3] = ("");
							grid.addItem(new Object[] { studentId, studentFullName, "", "" }, new Integer(i));
							i++;

						}

						// for (Object[] student : data)
						// grid.addItem(student);

						/*
						 * HeaderRow filterRow = grid.appendHeaderRow(); for
						 * (Object pid :
						 * grid.getContainerDataSource().getContainerPropertyIds
						 * ()) { HeaderCell cell = filterRow.getCell(pid);
						 * 
						 * // Have an input field to use for filter TextField
						 * TextField filterField = new TextField();
						 * filterField.setColumns(8); // Update filter When the
						 * filter input is changed
						 * filterField.addTextChangeListener(change -> { //
						 * Can't modify filters so need to replace
						 * 
						 * ((IndexedContainer)
						 * grid.getContainerDataSource()).removeContainerFilters
						 * (pid );
						 * 
						 * // (Re)create the filter if necessary if
						 * (!change.getText().isEmpty()) ((IndexedContainer)
						 * grid.getContainerDataSource())
						 * .addContainerFilter(new SimpleStringFilter(pid,
						 * change.getText(), true, false)); });
						 * cell.setComponent(filterField); }
						 */

						// grid.setEditorEnabled(true);
						grid.setSizeFull();
						grid.setEditable(true);

						Window w = new Window("Marks Entry Form");
						HorizontalLayout horizontalLayout = new SecondaryMarksEntryFormControlButtons(w, grid, this.stm,
								this.stmt, this.rs, this.rs1, this.schoolID, tablename, term, year);
						VerticalLayout layout = new VerticalLayout(horizontalLayout, grid);
						layout.setSpacing(true);
						w.center();
						w.setModal(true);
						w.setContent(layout);
						w.setSizeFull();
						UI.getCurrent().addWindow(w);

					} else {
						new Notification("Information", "No students Found for class " + classname,
								Notification.Type.HUMANIZED_MESSAGE, true).show(Page.getCurrent());
					}

				} catch (SQLException ee) {
					ee.printStackTrace();
				}
			} else {

				new Notification("Error", "Submit All fields " + classname, Notification.Type.HUMANIZED_MESSAGE, true)
						.show(Page.getCurrent());
			}
		});

		FormLayout formLayout = new FormLayout(myClass, subjects, horizontalLayout1, submit);
		formLayout.setCaption("<h1>Mark Schedule<h1/>");
		formLayout.setCaptionAsHtml(true);
		formLayout.setSpacing(true);

		HorizontalLayout gridLayout = new HorizontalLayout(formLayout);
		gridLayout.setSpacing(true);

		VerticalLayout verticalLayout = new VerticalLayout(gridLayout);
		verticalLayout.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);
		this.setCompositionRoot(verticalLayout);

	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Student Marks Entry")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Student Marks Entry", VaadinIcons.USER);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
