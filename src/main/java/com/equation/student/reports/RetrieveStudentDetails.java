package com.equation.student.reports;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.school.classes.collection.AllClasses;
import com.equation.school.cocurricular.subjects.AllCocurricularSubjects;
import com.equation.school.houses.AllSchoolHouses;
import com.equation.school.names.collection.SchoolNames;
import com.equation.school.secondary.subjects.bin.AllSecondarySubjectsBin;
import com.equation.student.disabilities.Disabilities;
import com.equation.student.reports.search.SearchStudentByAge;
import com.equation.student.reports.search.SearchStudentByClassName;
import com.equation.student.reports.search.SearchStudentByCocurrucular;
import com.equation.student.reports.search.SearchStudentByDisability;
import com.equation.student.reports.search.SearchStudentByFirstName;
import com.equation.student.reports.search.SearchStudentByParentNationalID;
import com.equation.student.reports.search.SearchStudentByReligion;
import com.equation.student.reports.search.SearchStudentBySchoolName;
import com.equation.student.reports.search.SearchStudentBySportsHouse;
import com.equation.student.reports.search.SearchStudentByStudentID;
import com.equation.student.reports.search.SearchStudentBySubjects;
import com.equation.student.reports.search.SearchStudentBySurname;
import com.equation.student.reports.search.SearchStudentsByBoarding;
import com.equation.student.reports.search.SearchStudentsByGender;
import com.equation.system.environment.determine.SchoolEmisNumber;
import com.equation.utils.numberofsubjects.NumberOfSubjects;
import com.equation.utils.religions.AllReligions;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.data.Property.ValueChangeListener;
import com.vaadin.v7.ui.ComboBox;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial", "deprecation" })
public class RetrieveStudentDetails extends CustomComponent implements View {
	ComboBox searchCriteria;
	ComboBox itemsCombo;
	TextField inputField;
	ResultSet rs, rs1;
	Statement stm, stmt;
	private ComboBox classNames;
	private ComboBox schoolNames;
	TabSheet tabs;

	public RetrieveStudentDetails(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, TabSheet tabs) {

		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.tabs = tabs;
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		searchCriteria = new ComboBox("Select Search Criteria");
		searchCriteria.addItems("Student ID", "First Name", "Surname", "Gender", "Parent National ID", "School Name",
				"Class", "Border Students", "Subject", "Age Group", "Cocurrucular", "Disability", "Religion", "House");
		searchCriteria.setRequired(true);
		searchCriteria.setNullSelectionAllowed(false);

		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSpacing(true);
		horizontalLayout.addComponent(searchCriteria);

		Panel panel = new Panel();
		panel.setSizeFull();

		itemsCombo = new ComboBox();
		itemsCombo.setRequired(true);
		itemsCombo.setNullSelectionAllowed(false);

		classNames = new ComboBox("Select Class");
		classNames.setNullSelectionAllowed(false);
		classNames.setRequired(true);
		classNames.addItems();

		schoolNames = new ComboBox("Select School");
		schoolNames.setNullSelectionAllowed(false);
		schoolNames.addItems(new SchoolNames(stm, rs).schoolCollection());

		schoolNames.addValueChangeListener((e) -> {
			String schoolname = (String) e.getProperty().getValue();
			horizontalLayout.removeAllComponents();
			horizontalLayout.addComponents(searchCriteria, schoolNames, classNames);
			classNames.addItems(new AllClasses(stm, rs).classesCollection(schoolname));
		});

		inputField = new TextField();
		inputField.setRequiredIndicatorVisible(true);

		searchCriteria.addValueChangeListener((e) -> {
			String value = (String) e.getProperty().getValue();
			String emis = new SchoolEmisNumber().getEmisThatExist();
			switch (value) {
			case "Student ID": {
				horizontalLayout.removeAllComponents();
				inputField.setCaption("Student ID");
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					schoolNames.addValueChangeListener((ew) -> {
						String schoolname = (String) e.getProperty().getValue();
						horizontalLayout.removeAllComponents();
						Button search = new Button("Search");
						search.setIcon(FontAwesome.SEARCH);
						search.addStyleName(ValoTheme.BUTTON_DANGER);
						horizontalLayout.addComponents(searchCriteria, schoolNames, inputField, search);
						search.addClickListener((evv) -> {
							String studentID = inputField.getValue();
							Panel p = new SearchStudentByStudentID(rs, rs1, stm, stmt).createContentPanel(schoolname,
									studentID);
							layout.removeAllComponents();
							layout.addComponents(horizontalLayout, p);
						});
					});
					horizontalLayout.addComponents(searchCriteria, schoolNames);
				} else {
					Button button1 = new Button("Search");
					button1.setIcon(FontAwesome.SEARCH);
					button1.addStyleName(ValoTheme.BUTTON_DANGER);
					horizontalLayout.addComponents(searchCriteria, inputField, button1);
					button1.addClickListener((evc) -> {
						String studentID = inputField.getValue();
						Panel p = new SearchStudentByStudentID(rs, rs1, stm, stmt).createContentPanel(emis, studentID);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
				}

				break;
			}

			case "First Name": {
				horizontalLayout.removeAllComponents();
				inputField.setCaption("Student First Name");
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					schoolNames.addValueChangeListener((val) -> {
						String schoolname = (String) val.getProperty().getValue();
						horizontalLayout.removeAllComponents();
						Button button = new Button("Search");
						button.setIcon(FontAwesome.SEARCH);
						button.addStyleName(ValoTheme.BUTTON_DANGER);
						horizontalLayout.addComponents(searchCriteria, schoolNames, inputField, button);
						button.addClickListener((bl) -> {
							String first_name = inputField.getValue();
							Panel p = new SearchStudentByFirstName(rs, rs1, stm, stmt).createContentPanel(schoolname,
									first_name);
							layout.removeAllComponents();
							layout.addComponents(horizontalLayout, p);
						});
					});
					horizontalLayout.addComponents(searchCriteria, schoolNames);
				} else {
					Button button = new Button("Search");
					button.setIcon(FontAwesome.SEARCH);
					button.addStyleName(ValoTheme.BUTTON_DANGER);
					horizontalLayout.addComponents(searchCriteria, inputField, button);
					button.addClickListener((b1) -> {
						String first_name = inputField.getValue();
						Panel p = new SearchStudentByFirstName(rs, rs1, stm, stmt).createContentPanel(emis, first_name);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
				}

				break;
			}

			case "Surname": {
				horizontalLayout.removeAllComponents();
				inputField.setCaption("Student Surname");
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					schoolNames.addValueChangeListener((ed) -> {
						horizontalLayout.removeAllComponents();
						String schoolname = (String) ed.getProperty().getValue();
						Button search = new Button("Search");
						search.setIcon(FontAwesome.SEARCH);
						search.addStyleName(ValoTheme.BUTTON_DANGER);
						horizontalLayout.addComponents(searchCriteria, schoolNames, inputField, search);
						search.addClickListener((ew) -> {
							String surname = inputField.getValue();
							Panel p = new SearchStudentBySurname(rs, rs1, stm, stmt).createContentPanel(schoolname,
									surname);
							layout.removeAllComponents();
							layout.addComponents(horizontalLayout, p);
						});
					});
					horizontalLayout.addComponents(searchCriteria, schoolNames);
				} else {
					Button search = new Button("Search");
					search.setIcon(FontAwesome.SEARCH);
					search.addStyleName(ValoTheme.BUTTON_DANGER);
					horizontalLayout.addComponents(searchCriteria, inputField, search);
					search.addClickListener((eew) -> {
						String surname = inputField.getValue();
						Panel p = new SearchStudentBySurname(rs, rs1, stm, stmt).createContentPanel(emis, surname);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
				}

				break;
			}

			case "Gender": {
				horizontalLayout.removeAllComponents();
				itemsCombo.removeAllItems();
				itemsCombo.addItems("Male", "Female");
				itemsCombo.setCaption("Gender");
				if (emis.startsWith("d")) {
					schoolNames.addValueChangeListener((ev) -> {
						String schoolName = (String) ev.getProperty().getValue();
						horizontalLayout.removeAllComponents();
						horizontalLayout.addComponents(searchCriteria, schoolNames, itemsCombo);
						itemsCombo.addValueChangeListener((evc) -> {
							String sgender = (String) evc.getProperty().getValue();
							Panel p = new SearchStudentsByGender(rs, rs1, stm, stmt).createConentPanel(schoolName,
									sgender);
							layout.removeAllComponents();
							layout.addComponents(horizontalLayout, p);
						});
					});

					horizontalLayout.addComponents(searchCriteria, schoolNames);
				} else {
					horizontalLayout.addComponents(searchCriteria, itemsCombo);
					itemsCombo.addValueChangeListener((ee) -> {
						String sgender = (String) ee.getProperty().getValue();
						Panel p = new SearchStudentsByGender(rs, rs1, stm, stmt).createConentPanel(emis, sgender);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});

				}

				break;
			}

			case "Parent National ID": {
				horizontalLayout.removeAllComponents();
				inputField.setCaption("Parent National ID");
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					schoolNames.addValueChangeListener((eeet) -> {
						String schoolname = (String) eeet.getProperty().getValue();
						horizontalLayout.removeAllComponents();
						Button search = new Button("Search");
						search.setIcon(FontAwesome.SEARCH);
						search.addStyleName(ValoTheme.BUTTON_DANGER);
						horizontalLayout.addComponents(searchCriteria, schoolNames, inputField, search);
						search.addClickListener((ewq) -> {
							String national_id = inputField.getValue();
							Panel p = new SearchStudentByParentNationalID(rs, rs1, stm, stmt)
									.createContentPanel(schoolname, national_id);
							layout.removeAllComponents();
							layout.addComponents(horizontalLayout, p);
						});
					});

					horizontalLayout.addComponents(searchCriteria, schoolNames);
				} else {
					Button search = new Button("Search");
					search.setIcon(FontAwesome.SEARCH);
					search.addStyleName(ValoTheme.BUTTON_DANGER);
					horizontalLayout.addComponents(searchCriteria, inputField, search);
					search.addClickListener((eer) -> {
						String nationa_id = inputField.getValue();
						Panel p = new SearchStudentByParentNationalID(rs, rs1, stm, stmt).createContentPanel(emis,
								nationa_id);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
				}
				break;
			}

			case "School Name": {
				horizontalLayout.removeAllComponents();
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					schoolNames.addValueChangeListener((er) -> {
						String schoolname = (String) er.getProperty().getValue();
						Panel p = new SearchStudentBySchoolName(rs, rs1, stm, stmt).createContentPanel(schoolname);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
					horizontalLayout.addComponents(searchCriteria, schoolNames);
				} else {
					searchCriteria.removeValueChangeListener((ValueChangeListener) e);
					searchCriteria.addValueChangeListener((ewq) -> {
						Panel p = new SearchStudentBySchoolName(rs, rs1, stm, stmt).createContentPanel(emis);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
					horizontalLayout.addComponents(searchCriteria);
				}
				break;
			}
			case "Class": {
				horizontalLayout.removeAllComponents();
				if (emis.startsWith("d")) {
					schoolNames.addValueChangeListener((eve) -> {
						String schoolname = (String) e.getProperty().getValue();
						horizontalLayout.removeAllComponents();
						horizontalLayout.addComponents(searchCriteria, schoolNames, classNames);
						classNames.addItem(new AllClasses(stm, rs).classesCollection(schoolname));
						classNames.addValueChangeListener((cle) -> {
							String class_name = (String) cle.getProperty().getValue();
							Panel p = new SearchStudentByClassName(rs, rs1, stm, stmt).createContentPanel(schoolname,
									class_name);
							layout.removeAllComponents();
							layout.addComponents(horizontalLayout, p);
						});
					});
					horizontalLayout.addComponents(searchCriteria, schoolNames);
				} else {
					classNames.addItems(new AllClasses(stm, rs).classesCollection(emis));
					horizontalLayout.addComponents(searchCriteria, classNames);
					classNames.addValueChangeListener((itv) -> {
						String class_name = (String) itv.getProperty().getValue();
						Panel p = new SearchStudentByClassName(rs, rs1, stm, stmt).createContentPanel(emis, class_name);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
				}
				break;
			}
			case "Border Students": {
				horizontalLayout.removeAllComponents();
				itemsCombo.removeAllItems();
				itemsCombo.setCaption("Border Students");
				itemsCombo.addItems("Yes", "No");
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					schoolNames.addValueChangeListener((vc) -> {
						String schoolname = (String) vc.getProperty().getValue();
						horizontalLayout.removeAllComponents();
						horizontalLayout.addComponents(searchCriteria, schoolNames, itemsCombo);
						itemsCombo.addValueChangeListener((vcq) -> {
							String border = (String) vcq.getProperty().getValue();
							Panel p = new SearchStudentsByBoarding(rs, rs1, stm, stmt).createContentPanel(schoolname,
									border);
							layout.removeAllComponents();
							layout.addComponents(horizontalLayout, p);
						});
					});
					horizontalLayout.addComponents(searchCriteria, schoolNames);

				} else {
					horizontalLayout.addComponents(searchCriteria, itemsCombo);
					itemsCombo.addValueChangeListener((vvc) -> {
						String border = (String) vvc.getProperty().getValue();
						Panel p = new SearchStudentsByBoarding(rs, rs1, stm, stmt).createContentPanel(emis, border);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
				}

				break;
			}
			case "Subject": {
				horizontalLayout.removeAllComponents();
				itemsCombo.removeAllItems();
				itemsCombo.setCaption("Select Subject");
				if (emis.startsWith("d")) {
					schoolNames.addValueChangeListener((ev) -> {
						String schoolName = (String) ev.getProperty().getValue();
						horizontalLayout.removeAllComponents();
						itemsCombo.addItems(AllSecondarySubjectsBin.allSubjectsArray());
						horizontalLayout.addComponents(searchCriteria, schoolNames, itemsCombo);
						itemsCombo.addValueChangeListener((valc) -> {
							String subject_name = (String) valc.getProperty().getValue();
							Panel p = new SearchStudentBySubjects(rs, rs1, stm, stmt).createContentPanel(schoolName,
									subject_name);
							layout.removeAllComponents();
							layout.addComponents(horizontalLayout, p);
						});
					});
					horizontalLayout.addComponents(searchCriteria, schoolNames);

				} else {
					itemsCombo.removeAllItems();
					itemsCombo.addItems(AllSecondarySubjectsBin.allSubjectsArray());
					horizontalLayout.addComponents(searchCriteria, itemsCombo);
					itemsCombo.addValueChangeListener((suv) -> {
						String subject_name = (String) suv.getProperty().getValue();
						Panel p = new SearchStudentBySubjects(rs, rs1, stm, stmt).createContentPanel(emis,
								subject_name);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
				}
				break;
			}
			case "Age Group": {
				horizontalLayout.removeAllComponents();
				ComboBox minCombo = new ComboBox("Minimun age");
				minCombo.addItems(NumberOfSubjects.numbersArray());
				minCombo.setNullSelectionAllowed(false);
				minCombo.setRequired(true);
				ComboBox maxCombo = new ComboBox("Maximum Age");
				maxCombo.addItems(NumberOfSubjects.numbersArray());
				maxCombo.setNullSelectionAllowed(false);
				maxCombo.setRequired(true);
				if (emis.startsWith("d")) {
					schoolNames.addValueChangeListener((ee) -> {
						String schoolname = (String) ee.getProperty().getValue();
						horizontalLayout.removeAllComponents();
						Button search = new Button("Search");
						search.addStyleName(ValoTheme.BUTTON_DANGER);
						search.setIcon(FontAwesome.SEARCH);
						horizontalLayout.addComponents(searchCriteria, schoolNames, minCombo, maxCombo, search);
						search.addClickListener((sc) -> {
							String mi = (String) minCombo.getValue();
							String ma = (String) maxCombo.getValue();
							if (!(mi.equals("") || ma.equals(""))) {
								int minage = Integer.parseInt(mi);
								int maxage = Integer.parseInt(ma);
								if (minage <= maxage) {
									Panel p = new SearchStudentByAge(rs, rs1, stm, stmt).createContentPanel(schoolname,
											minage, maxage);
									layout.removeAllComponents();
									layout.addComponents(horizontalLayout, p);
								} else {
									new Notification("Warning", "Kindly correct age range",
											Notification.TYPE_WARNING_MESSAGE, true).show(Page.getCurrent());
								}
							}
						});
					});
					horizontalLayout.addComponents(searchCriteria, schoolNames);
				} else {
					horizontalLayout.removeAllComponents();
					Button search = new Button("Search");
					search.addStyleName(ValoTheme.BUTTON_DANGER);
					search.setIcon(FontAwesome.SEARCH);
					horizontalLayout.addComponents(searchCriteria, minCombo, maxCombo, search);
					search.addClickListener((ecl) -> {
						String mi = (String) minCombo.getValue();
						String ma = (String) maxCombo.getValue();
						if (!(mi.equals("") || ma.equals(""))) {
							int minage = Integer.parseInt(mi);
							int maxage = Integer.parseInt(ma);
							if (minage <= maxage) {
								Panel p = new SearchStudentByAge(rs, rs1, stm, stmt).createContentPanel(emis, minage,
										maxage);
								layout.removeAllComponents();
								layout.addComponents(horizontalLayout, p);
							} else {
								new Notification("Warning", "Kindly correct age range",
										Notification.TYPE_WARNING_MESSAGE, true).show(Page.getCurrent());
							}
						}
					});
				}
				break;
			}
			case "Cocurrucular": {
				horizontalLayout.removeAllComponents();
				itemsCombo.removeAllItems();
				itemsCombo.setCaption("Select Subject");
				if (emis.startsWith("d")) {
					schoolNames.addValueChangeListener((ee) -> {
						String schoolname = (String) ee.getProperty().getValue();
						horizontalLayout.removeAllComponents();
						itemsCombo.addItems(AllCocurricularSubjects.allHobbies());
						horizontalLayout.addComponents(searchCriteria, schoolNames, itemsCombo);
						itemsCombo.addValueChangeListener((ers) -> {
							String cocurrucular = (String) ers.getProperty().getValue();
							Panel p = new SearchStudentByCocurrucular(rs, rs1, stm, stmt).createContentPanel(schoolname,
									cocurrucular);
							layout.removeAllComponents();
							layout.addComponents(horizontalLayout, p);
						});
					});
					horizontalLayout.addComponents(searchCriteria, schoolNames);

				} else {
					itemsCombo.removeAllItems();
					itemsCombo.addItems(AllCocurricularSubjects.allHobbies());
					horizontalLayout.addComponents(searchCriteria, itemsCombo);
					itemsCombo.addValueChangeListener((erq) -> {
						String cocurrucular = (String) erq.getProperty().getValue();
						Panel p = new SearchStudentByCocurrucular(rs, rs1, stm, stmt).createContentPanel(emis,
								cocurrucular);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
				}
				break;
			}
			case "Disability": {
				horizontalLayout.removeAllComponents();
				itemsCombo.removeAllItems();
				itemsCombo.setCaption("Select Disability");
				if (emis.startsWith("d")) {
					schoolNames.addValueChangeListener((ee) -> {
						String schoolname = (String) ee.getProperty().getValue();
						horizontalLayout.removeAllComponents();
						itemsCombo.addItems(Disabilities.allDisabilities());
						horizontalLayout.addComponents(searchCriteria, schoolNames, itemsCombo);
						itemsCombo.addValueChangeListener((dv) -> {
							String disability = (String) dv.getProperty().getValue();
							Panel p = new SearchStudentByDisability(rs, rs1, stm, stmt).createContentPanel(schoolname,
									disability);
							layout.removeAllComponents();
							layout.addComponents(horizontalLayout, p);
						});
					});
					horizontalLayout.addComponents(searchCriteria, schoolNames);

				} else {
					itemsCombo.removeAllItems();
					itemsCombo.addItems(Disabilities.allDisabilities());
					horizontalLayout.addComponents(searchCriteria, itemsCombo);
					itemsCombo.addValueChangeListener((ery) -> {
						String disability = (String) ery.getProperty().getValue();
						Panel p = new SearchStudentByDisability(rs, rs1, stm, stmt).createContentPanel(emis,
								disability);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
				}
				break;
			}
			case "Religion": {
				horizontalLayout.removeAllComponents();
				itemsCombo.removeAllItems();
				itemsCombo.setCaption("Select Religion");
				if (emis.startsWith("d")) {
					schoolNames.addValueChangeListener((ee) -> {
						String schoolname = (String) ee.getProperty().getValue();
						horizontalLayout.removeAllComponents();
						itemsCombo.addItems(AllReligions.allReligionsCollecion());
						horizontalLayout.addComponents(searchCriteria, schoolNames, itemsCombo);
						itemsCombo.addValueChangeListener((edc) -> {
							String religion = (String) edc.getProperty().getValue();
							Panel p = new SearchStudentByReligion(rs, rs1, stm, stmt).createContentPanel(schoolname,
									religion);
							layout.removeAllComponents();
							layout.addComponents(horizontalLayout, p);
						});
					});
					horizontalLayout.addComponents(searchCriteria, schoolNames);
				} else {
					itemsCombo.removeAllItems();
					itemsCombo.addItems(AllReligions.allReligionsCollecion());
					horizontalLayout.addComponents(searchCriteria, itemsCombo);
					itemsCombo.addValueChangeListener((edcv) -> {
						String religion = (String) edcv.getProperty().getValue();
						Panel p = new SearchStudentByReligion(rs, rs1, stm, stmt).createContentPanel(emis, religion);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
				}
				break;
			}
			case "House": {
				horizontalLayout.removeAllComponents();
				itemsCombo.removeAllItems();
				itemsCombo.setCaption("Select House");
				if (emis.startsWith("d")) {
					schoolNames.addValueChangeListener((ee) -> {
						String schoolEmis = (String) ee.getProperty().getValue();
						horizontalLayout.removeAllComponents();
						itemsCombo.addItems(new AllSchoolHouses(stm, rs).allSchoolHouses(schoolEmis));
						horizontalLayout.addComponents(searchCriteria, schoolNames, itemsCombo);
						itemsCombo.addValueChangeListener((eert) -> {
							String housename = (String) eert.getProperty().getValue();
							Panel p = new SearchStudentBySportsHouse(rs, rs1, stm, stmt).createContentPanel(schoolEmis,
									housename);
							layout.removeAllComponents();
							layout.addComponents(horizontalLayout, p);
						});
					});
					horizontalLayout.addComponents(searchCriteria, schoolNames);
				} else {
					layout.removeAllComponents();
					itemsCombo.addItems(new AllSchoolHouses(stm, rs).allSchoolHousesByEmis(emis));
					horizontalLayout.addComponents(searchCriteria, itemsCombo);
					itemsCombo.addValueChangeListener((erw) -> {
						String housename = (String) erw.getProperty().getValue();
						Panel p = new SearchStudentBySportsHouse(rs, rs1, stm, stmt).createContentPanel(emis,
								housename);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
				}
				break;
			}

			default: {
				new Notification("Information", "Invalid Search Criteria", Notification.TYPE_WARNING_MESSAGE, true)
						.show(Page.getCurrent());
			}
				break;
			}
		});

		layout.addComponents(horizontalLayout, panel);

		this.setCompositionRoot(layout);

	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Search Student")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Search Student", FontAwesome.SEARCH);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}