package com.equation.teacher.primary.marks.capture;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.school.classes.collection.AllClasses;
import com.equation.student.ids.collection.AllStudentIDsBin;
import com.equation.teacher.current.classname.TeacherCurrentClass;
import com.equation.teacher.primary.marks.tables.select.ReturnPrimaryMarksTableName;
import com.equation.util.primary.subjects.PrimarySubjects;
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
public class PrimarySchoolMarksEntryFrom extends CustomComponent implements View {
	ResultSet rs, rs1;
	Statement stm, stmt;
	ComboBox<String> term, studentID;
	TabSheet tabs;
	String schoolID, ecNumber;
	private ComboBox<String> years, terms, classname;
	private ComboBox<String> subjects;
	private Button go;

	public PrimarySchoolMarksEntryFrom(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, TabSheet tabs,
			String schoolID, String ecNumber) {
		this.ecNumber = ecNumber;
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.tabs = tabs;
		this.schoolID = schoolID;

		studentID = new ComboBox<>("Select Student ID");
		studentID.setRequiredIndicatorVisible(true);
		studentID.setItems(new AllStudentIDsBin(this.rs, this.stm, this.schoolID).allStudentIDs());

		term = new ComboBox<>("Select Term");
		term.setItems(AllYearTermsBin.allTermsArray());
		term.setValue(new AllTerms().thisTerm());
		term.setRequiredIndicatorVisible(true);
		term.setEmptySelectionAllowed(false);

		years = new ComboBox<>("Year");
		years.setItems(PaymentHistoryYears.allYears());
		years.setValue(new DateUtility().getYear());
		years.setRequiredIndicatorVisible(true);
		years.setValue(new DateUtility().getYear());
		years.setEmptySelectionAllowed(false);

		subjects = new ComboBox<>("Subject");
		subjects.setItems(PrimarySubjects.allPrimarySubjects());
		subjects.setRequiredIndicatorVisible(true);
		subjects.setEmptySelectionAllowed(false);

		terms = new ComboBox<>("Term");
		terms.setRequiredIndicatorVisible(true);
		terms.setItems(AllYearTermsBin.allTermsArray());
		terms.setValue(new AllTerms().thisTerm());
		terms.setEmptySelectionAllowed(false);

		classname = new ComboBox<>("Class Name");
		classname.setRequiredIndicatorVisible(true);
		classname.setItems(new AllClasses(this.stm, this.rs).classesCollection(this.schoolID));
		classname.setValue(new TeacherCurrentClass(rs, rs1, stm, stmt).className(ecNumber, schoolID));

		go = new Button("Go");
		go.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		go.setIcon(VaadinIcons.PLAY_CIRCLE);
		go.addStyleName("primarymarks_captureform_button1");
		go.addClickListener((e) -> {
			String classname = (String) this.classname.getValue();
			String subject = (String) subjects.getValue();
			String term = (String) terms.getValue();
			String year = (String) years.getValue();
			String tablename = new ReturnPrimaryMarksTableName().getTableName(term);

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

						grid.setCaption(subject.toUpperCase() + "  Marks Entry Form For " + classname);
						grid.addContainerProperty("Student ID", String.class, null);
						grid.addContainerProperty("Student Name", String.class, null);
						grid.addContainerProperty("Paper One", String.class, null);
						grid.addContainerProperty("Paper Two", String.class, null);
						while (this.rs.next()) {
							String studentId = this.rs.getString(1);
							String studentFullName = this.rs.getString(2) + " " + this.rs.getString(3);
							String p1 = null, p2 = null;

							String query2 = "SELECT " + subject + "," + subject + "2" + " FROM " + tablename
									+ " WHERE studentID = '" + studentId + "'";
							this.rs = this.stm.executeQuery(query2);
							if (this.rs.next()) {
								p1 = this.rs.getString(1);
								p2 = this.rs.getString(2);
							} else {
								p1 = "";
								p2 = "";
							}
							grid.addItem(new Object[] { studentId, studentFullName, p1, p2 }, new Integer(i));
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
						grid.setPageLength(grid.size());

						Window w = new Window("Marks Entry Form");
						HorizontalLayout horizontalLayout = new PrimaryMarksEntryFormControlButtons(w, grid, this.stm,
								this.stmt, this.rs, this.rs1, this.schoolID, tablename, term, Integer.parseInt(year),
								subject.toLowerCase());
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

		FormLayout formLayout = new FormLayout(subjects, classname, terms, years, go);
		formLayout.setCaption("<h1>Mark Schedule<h1/>");
		formLayout.setCaptionAsHtml(true);
		formLayout.setSpacing(true);

		HorizontalLayout horizontalLayout2 = new HorizontalLayout(formLayout);
		horizontalLayout2.setSpacing(true);

		VerticalLayout verticalLayout = new VerticalLayout(horizontalLayout2);
		verticalLayout.addComponent(horizontalLayout2);
		verticalLayout.setSpacing(true);
		verticalLayout.setComponentAlignment(horizontalLayout2, Alignment.TOP_CENTER);

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
	}
}