package com.equation.teacher.reports;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.school.classes.collection.AllClasses;
import com.equation.school.names.collection.SchoolNames;
import com.equation.school.secondary.subjects.bin.AllSecondarySubjectsBin;
import com.equation.school.teachers.ecnumbers.ECNumbersCollection;
import com.equation.student.reports.search.SearchStudentBySchoolName;
import com.equation.system.environment.determine.SchoolEmisNumber;
import com.equation.teacher.reports.search.SearchTeacherByAgeRange;
import com.equation.teacher.reports.search.SearchTeacherByClassname;
import com.equation.teacher.reports.search.SearchTeacherByECNumber;
import com.equation.teacher.reports.search.SearchTeacherByGender;
import com.equation.teacher.reports.search.SearchTeacherByNationality;
import com.equation.teacher.reports.search.SearchTeacherBySurname;
import com.equation.teacher.reports.search.SearchTeacherByYearOfJoiningSchool;
import com.equation.teacher.reports.search.SearchTeacherByYearOfJoiningService;
import com.equation.teacher.reports.search.SearchteacherByNationalID;
import com.equation.teacher.reports.search.SeatchTeacherByMaritalStatus;
import com.equation.teacher.reports.search.SelectTeacherByMainSubject;
import com.equation.util.years.ranges.AllYearsOfJoining;
import com.equation.util.years.ranges.TeachersAgeGroups;
import com.equation.utils.countries.AllCountries;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class RetrieveTeacherDetails extends CustomComponent implements View {

	ComboBox<String> searchCriteria;
	ComboBox<String> itemsCombo;
	TextField inputField;
	ResultSet rs, rs1;
	Statement stm, stmt;
	ComboBox<String> classNames;
	ComboBox<String> schoolNames;
	TabSheet tabs;
	private ComboBox<String> teachersECCombo;
	private ComboBox<String> genderCombo;
	private ComboBox<String> yearOfJoiningSchool;
	private ComboBox<String> minAgeCombo;
	private ComboBox<String> maxAgeCombo;

	@SuppressWarnings("deprecation")
	public RetrieveTeacherDetails(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, TabSheet tabs) {

		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.tabs = tabs;
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		searchCriteria = new ComboBox<>("Select Search Criteria");
		searchCriteria.setItems("EC Number", "Surname", "Gender", "National ID", "Nationality", "School Name",
				"Class Name", "Year of joining service", "Main Subject", "Age Group", "Marital Status",
				"Year of Joining School");
		searchCriteria.setRequiredIndicatorVisible(true);
		searchCriteria.setEmptySelectionAllowed(false);

		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSpacing(true);
		horizontalLayout.addComponent(searchCriteria);

		schoolNames = new ComboBox<>("Select School");
		schoolNames.setEmptySelectionAllowed(false);
		schoolNames.setItems(new SchoolNames(stm, rs).schoolCollection());

		teachersECCombo = new ComboBox<>("Select EC Numbers");
		teachersECCombo.setRequiredIndicatorVisible(true);
		teachersECCombo.setEmptySelectionAllowed(false);

		genderCombo = new ComboBox<>("Gender");
		genderCombo.setEmptySelectionAllowed(false);
		genderCombo.setRequiredIndicatorVisible(true);
		genderCombo.setItems("Male", "Female");

		yearOfJoiningSchool = new ComboBox<>("Select Year Of Joining School");
		yearOfJoiningSchool.setEmptySelectionAllowed(false);
		yearOfJoiningSchool.setRequiredIndicatorVisible(true);
		yearOfJoiningSchool.setItems(AllYearsOfJoining.allYears());

		minAgeCombo = new ComboBox<>("Minimum Age");
		minAgeCombo.setRequiredIndicatorVisible(true);
		minAgeCombo.setEmptySelectionAllowed(false);
		minAgeCombo.setItems(TeachersAgeGroups.allAgeGroups());

		maxAgeCombo = new ComboBox<>("Minimum Age");
		maxAgeCombo.setRequiredIndicatorVisible(true);
		maxAgeCombo.setEmptySelectionAllowed(false);
		maxAgeCombo.setItems(TeachersAgeGroups.allAgeGroups());

		Panel panel = new Panel();
		panel.setSizeFull();

		searchCriteria.addValueChangeListener((e) -> {
			String value = (String) e.getValue();
			String emis = new SchoolEmisNumber().getEmisThatExist();

			switch (value) {
			case "EC Number": {
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, schoolNames);
					schoolNames.addValueChangeListener((ee) -> {
						String schoolname = (String) ee.getValue();
						teachersECCombo.setItems(new ECNumbersCollection(stmt, rs1).ecNumbers(schoolname));
						horizontalLayout.removeAllComponents();
						horizontalLayout.addComponents(searchCriteria, schoolNames, teachersECCombo);
						teachersECCombo.addValueChangeListener((ed) -> {
							String ecNumber = (String) ed.getValue();
							layout.removeAllComponents();
							Panel p = new SearchTeacherByECNumber(rs, rs1, stm, stmt).createContentPanel(schoolname,
									ecNumber);
							layout.addComponents(horizontalLayout, p);
						});
					});

				} else {
					horizontalLayout.removeAllComponents();
					teachersECCombo.setItems(new ECNumbersCollection(stmt, rs1).ecNumbers(emis));
					horizontalLayout.addComponents(searchCriteria, teachersECCombo);
					teachersECCombo.addValueChangeListener((eeer) -> {
						String ecNumber = (String) eeer.getValue();
						layout.removeAllComponents();
						Panel p = new SearchTeacherByECNumber(rs, rs1, stm, stmt).createContentPanel(emis, ecNumber);
						layout.addComponents(horizontalLayout, p);
					});
				}
			}
				break;
			case "Surname": {
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					TextField input = new TextField("Surname");
					input.setRequiredIndicatorVisible(true);
					Button search = new Button("Search");
					search.addStyleName(ValoTheme.BUTTON_DANGER);
					search.setIcon(VaadinIcons.SEARCH);
					horizontalLayout.addComponents(searchCriteria, schoolNames);
					schoolNames.addValueChangeListener((eert) -> {
						String schoolname = (String) eert.getValue();
						horizontalLayout.removeAllComponents();
						horizontalLayout.addComponents(searchCriteria, schoolNames, input, search);
						search.addClickListener((ee) -> {
							String surname = input.getValue();
							Panel p = new SearchTeacherBySurname(rs, rs1, stm, stmt).createContetPanel(schoolname,
									surname);
							layout.removeAllComponents();
							layout.addComponents(horizontalLayout, p);
						});
					});
				} else {
					horizontalLayout.removeAllComponents();
					TextField input = new TextField("Surname");
					input.setRequiredIndicatorVisible(true);
					Button search = new Button("Search");
					search.setIcon(VaadinIcons.SEARCH);
					search.addStyleName(ValoTheme.BUTTON_DANGER);
					horizontalLayout.addComponents(searchCriteria, input, search);
					search.addClickListener((eee) -> {
						String surname = input.getValue();
						layout.removeAllComponents();
						Panel p = new SearchTeacherBySurname(rs, rs1, stm, stmt).createContetPanel(emis, surname);
						layout.addComponents(horizontalLayout, p);
					});
				}
			}
				break;
			case "Gender": {
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, schoolNames);
					schoolNames.addValueChangeListener((ee) -> {
						String schoolname = (String) ee.getValue();
						teachersECCombo.setItems(new ECNumbersCollection(stmt, rs1).ecNumbers(schoolname));
						horizontalLayout.removeAllComponents();
						horizontalLayout.addComponents(searchCriteria, schoolNames, genderCombo);
						genderCombo.addValueChangeListener((ed) -> {
							String gender = (String) ed.getValue();
							layout.removeAllComponents();
							Panel p = new SearchTeacherByGender(rs, rs1, stm, stmt).createContetPanel(schoolname,
									gender);
							layout.addComponents(horizontalLayout, p);
						});
					});

				} else {
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, genderCombo);
					genderCombo.addValueChangeListener((eeer) -> {
						String gender = (String) eeer.getValue();
						Panel p = new SearchTeacherByGender(rs, rs1, stm, stmt).createContetPanel(emis, gender);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
				}
			}
				break;
			case "National ID": {
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, schoolNames);
					schoolNames.addValueChangeListener((er) -> {
						String schoolname = (String) er.getValue();
						TextField nationalIDtxt = new TextField("National ID");
						nationalIDtxt.setRequiredIndicatorVisible(true);
						Button search = new Button("Search");
						search.addStyleName(ValoTheme.BUTTON_DANGER);
						search.setIcon(VaadinIcons.SEARCH);
						horizontalLayout.removeAllComponents();
						horizontalLayout.addComponents(searchCriteria, schoolNames, nationalIDtxt, search);
						nationalIDtxt.focus();
						search.addClickListener((erf) -> {
							String nationalID = nationalIDtxt.getValue();
							layout.removeAllComponents();
							if (!nationalID.equals("")) {
								Panel p = new SearchteacherByNationalID(rs, rs1, stm, stmt)
										.createContentPanel(schoolname, nationalID);
								layout.addComponents(horizontalLayout, p);
							} else {
								new Notification("Error", "A blank national ID cannot be submitted!",
										Notification.TYPE_WARNING_MESSAGE, true).show(Page.getCurrent());
							}
						});
					});
				} else {
					horizontalLayout.removeAllComponents();
					TextField nationalIDtxt = new TextField("National ID");
					nationalIDtxt.setRequiredIndicatorVisible(true);
					Button search = new Button("Search");
					search.addStyleName(ValoTheme.BUTTON_DANGER);
					search.setIcon(VaadinIcons.SEARCH);
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, nationalIDtxt, search);
					nationalIDtxt.focus();
					search.addClickListener((erf) -> {
						String nationalID = nationalIDtxt.getValue();
						layout.removeAllComponents();
						Panel p = new SearchteacherByNationalID(rs, rs1, stm, stmt).createContentPanel(emis,
								nationalID);
						layout.addComponents(horizontalLayout, p);

					});
				}
			}
				break;
			case "Nationality": {
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, schoolNames);
					schoolNames.addValueChangeListener((er) -> {
						String schoolname = (String) er.getValue();
						layout.removeAllComponents();
						ComboBox<String> nationalityBox = new ComboBox<>("Nationality");
						nationalityBox.setItems(AllCountries.allCountriesCollection());
						nationalityBox.setEmptySelectionAllowed(false);
						nationalityBox.setRequiredIndicatorVisible(true);

						horizontalLayout.removeAllComponents();
						horizontalLayout.addComponents(searchCriteria, schoolNames, nationalityBox);
						nationalityBox.addValueChangeListener((erw) -> {
							String nationality = (String) erw.getValue();
							Panel p = new SearchTeacherByNationality(rs, rs1, stm, stmt).createContetPanel(schoolname,
									nationality);
							layout.removeAllComponents();
							layout.addComponents(horizontalLayout, p);
						});
					});
				} else {
					horizontalLayout.removeAllComponents();
					ComboBox<String> nationalityCombo = new ComboBox<>("Nationality");
					nationalityCombo.setItems(AllCountries.allCountriesCollection());
					nationalityCombo.setRequiredIndicatorVisible(true);
					nationalityCombo.setEmptySelectionAllowed(false);
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, nationalityCombo);
					nationalityCombo.addValueChangeListener((ert) -> {
						String nationality = (String) ert.getValue();
						Panel p = new SearchTeacherByNationality(rs, rs1, stm, stmt).createContetPanel(emis,
								nationality);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
				}
			}
				break;
			case "School Name": {
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, schoolNames);
					schoolNames.addValueChangeListener((er) -> {
						String schoolname = (String) er.getValue();
						layout.removeAllComponents();
						Panel p = new SearchStudentBySchoolName(rs, rs1, stm, stmt).createContentPanel(schoolname);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
				} else {
					horizontalLayout.removeAllComponents();
					Button search = new Button("Search");
					search.addStyleName(ValoTheme.BUTTON_DANGER);
					search.setIcon(VaadinIcons.SEARCH);
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, search);
					search.addClickListener((ert) -> {
						Panel p = new SearchStudentBySchoolName(rs, rs1, stm, stmt).createContentPanel(emis);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
				}
			}
				break;
			case "Class Name": {
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, schoolNames);
					schoolNames.addValueChangeListener((er) -> {
						String schoolname = (String) er.getValue();
						horizontalLayout.removeAllComponents();
						ComboBox<String> cassesCombo = new ComboBox<>("Select Year");
						cassesCombo.setEmptySelectionAllowed(false);
						cassesCombo.setRequiredIndicatorVisible(true);
						cassesCombo.setItems(new AllClasses(stmt, rs1).classesCollection(schoolname));
						horizontalLayout.addComponents(searchCriteria, schoolNames, cassesCombo);
						cassesCombo.addValueChangeListener((eer) -> {
							String classname = (String) eer.getValue();
							layout.removeAllComponents();
							Panel p = new SearchTeacherByClassname(rs, rs1, stm, stmt).createContetPanel(schoolname,
									classname);
							layout.addComponents(horizontalLayout, p);
						});

					});
				} else {
					horizontalLayout.removeAllComponents();

					ComboBox<String> cassesCombo = new ComboBox<>("Select Year");
					cassesCombo.setEmptySelectionAllowed(false);
					cassesCombo.setRequiredIndicatorVisible(true);
					cassesCombo.setItems(new AllClasses(stmt, rs1).classesCollection(emis));
					horizontalLayout.addComponents(searchCriteria, cassesCombo);
					cassesCombo.addValueChangeListener((er) -> {
						String classname = (String) er.getValue();
						Panel p = new SearchTeacherByClassname(rs, rs1, stm, stmt).createContetPanel(emis, classname);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
				}
			}
				break;
			case "Year of joining service": {
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, schoolNames);
					schoolNames.addValueChangeListener((er) -> {
						String schoolname = (String) er.getValue();
						horizontalLayout.removeAllComponents();
						ComboBox<String> yearOfJoiningService = new ComboBox<>("Select Year");
						yearOfJoiningService.setEmptySelectionAllowed(false);
						yearOfJoiningService.setRequiredIndicatorVisible(true);
						yearOfJoiningService.setItems(AllYearsOfJoining.allYears());
						horizontalLayout.addComponents(searchCriteria, schoolNames, yearOfJoiningService);
						yearOfJoiningService.addValueChangeListener((eer) -> {
							String year = (String) eer.getValue();
							layout.removeAllComponents();
							Panel p = new SearchTeacherByYearOfJoiningService(rs, rs1, stm, stmt)
									.createContetPanel(schoolname, year);
							layout.addComponents(horizontalLayout, p);
						});

					});
				} else {
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, yearOfJoiningSchool);
					ComboBox<String> yearOfJoiningService = new ComboBox<>("Select Year");
					yearOfJoiningService.setEmptySelectionAllowed(false);
					yearOfJoiningService.setRequiredIndicatorVisible(true);
					yearOfJoiningService.setItems(AllYearsOfJoining.allYears());
					yearOfJoiningService.addValueChangeListener((er) -> {
						String year = (String) er.getValue();
						Panel p = new SearchTeacherByYearOfJoiningService(rs, rs1, stm, stmt).createContetPanel(emis,
								year);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});

				}
			}
				break;
			case "Main Subject": {
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, schoolNames);
					ComboBox<String> subjectsCombo = new ComboBox<>("Select Subject");
					subjectsCombo.setRequiredIndicatorVisible(true);
					subjectsCombo.setItems(AllSecondarySubjectsBin.allSubjectsArray());
					schoolNames.addValueChangeListener((er) -> {
						String schoolname = (String) er.getValue();
						horizontalLayout.removeAllComponents();
						horizontalLayout.addComponents(searchCriteria, schoolNames, subjectsCombo);
						subjectsCombo.addValueChangeListener((ee) -> {
							String subject = (String) ee.getValue();
							Panel p = new SelectTeacherByMainSubject(rs, rs1, stm, stmt).createContetPanel(schoolname,
									subject);
							layout.removeAllComponents();
							layout.addComponents(horizontalLayout, p);
						});
					});
				} else {
					horizontalLayout.removeAllComponents();
					ComboBox<String> subjectsCombo = new ComboBox<>("Select Subject");
					subjectsCombo.setEmptySelectionAllowed(false);
					subjectsCombo.setRequiredIndicatorVisible(true);
					subjectsCombo.setItems(AllSecondarySubjectsBin.allSubjectsArray());
					horizontalLayout.addComponents(searchCriteria, subjectsCombo);
					subjectsCombo.addValueChangeListener((ee) -> {
						String subject = (String) ee.getValue();
						Panel p = new SelectTeacherByMainSubject(rs, rs1, stm, stmt).createContetPanel(emis, subject);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});
				}
			}
				break;
			case "Age Group": {
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, schoolNames);
					schoolNames.addValueChangeListener((er) -> {
						String schoolname = (String) er.getValue();
						horizontalLayout.removeAllComponents();
						Button search = new Button("Search");
						search.setIcon(VaadinIcons.SEARCH);
						search.addStyleName(ValoTheme.BUTTON_DANGER);
						horizontalLayout.addComponents(searchCriteria, schoolNames, minAgeCombo, maxAgeCombo, search);
						search.addClickListener((in) -> {
							String min = (String) minAgeCombo.getValue();
							String max = (String) maxAgeCombo.getValue();
							if (!(min.equals("") || max.equals(""))) {
								int minAge = Integer.parseInt(min);
								int maxAge = Integer.parseInt(max);
								if (minAge < maxAge) {
									layout.removeAllComponents();
									Panel p = new SearchTeacherByAgeRange(rs, rs1, stm, stmt)
											.createContetPanel(schoolname, minAge, maxAge);
									layout.addComponents(horizontalLayout, p);
								} else {
									new Notification("Warning",
											"Correct the minimum age, so that the range makes sense",
											Notification.Type.WARNING_MESSAGE, true).show(Page.getCurrent());
								}

							} else {
								new Notification("Warning", "A blank field detected in the search criteria",
										Notification.Type.WARNING_MESSAGE, true).show(Page.getCurrent());
							}
						});
					});
				} else {
					horizontalLayout.removeAllComponents();
					Button search = new Button("Search");
					search.setIcon(VaadinIcons.SEARCH);
					search.addStyleName(ValoTheme.BUTTON_DANGER);
					horizontalLayout.addComponents(searchCriteria, minAgeCombo, maxAgeCombo, search);
					search.addClickListener((cl) -> {
						String min = (String) minAgeCombo.getValue().toString();
						String max = (String) maxAgeCombo.getValue().toString();
						if (!(min.equals("") || max.equals(""))) {
							int minAge = Integer.parseInt(min);
							int maxAge = Integer.parseInt(max);
							if (minAge < maxAge) {
								layout.removeAllComponents();
								Panel p = new SearchTeacherByAgeRange(rs, rs1, stm, stmt).createContetPanel(emis,
										minAge, maxAge);
								layout.addComponents(horizontalLayout, p);
							} else {
								new Notification("Warning", "Correct the minimum age, so that the range makes sense",
										Notification.Type.WARNING_MESSAGE, true).show(Page.getCurrent());
							}
						} else {
							new Notification("Warning", "A blank field detected in the search criteria",
									Notification.Type.WARNING_MESSAGE, true).show(Page.getCurrent());
						}
					});
				}
			}
				break;
			case "Marital Status": {
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, schoolNames);
					schoolNames.addValueChangeListener((er) -> {
						String schoolname = (String) er.getValue();
						horizontalLayout.removeAllComponents();
						ComboBox<String> maritalStatus = new ComboBox<>("Marital Status");
						maritalStatus.setEmptySelectionAllowed(false);
						maritalStatus.setRequiredIndicatorVisible(true);
						maritalStatus.setItems("Single", "Married", "Divorced", "Widowed");
						horizontalLayout.addComponents(searchCriteria, schoolNames, maritalStatus);
						maritalStatus.addValueChangeListener((eer) -> {
							String status = (String) eer.getValue();
							layout.removeAllComponents();
							Panel p = new SeatchTeacherByMaritalStatus(rs, rs1, stm, stmt).createContetPanel(schoolname,
									status);
							layout.addComponents(horizontalLayout, p);
						});

					});
				} else {
					horizontalLayout.removeAllComponents();
					ComboBox<String> maritalStatus = new ComboBox<>("Marital Status");
					maritalStatus.setEmptySelectionAllowed(false);
					maritalStatus.setRequiredIndicatorVisible(true);
					maritalStatus.setItems("Single", "Married", "Divorced", "Widowed");
					horizontalLayout.addComponents(searchCriteria, maritalStatus);
					maritalStatus.addValueChangeListener((er) -> {
						String status = (String) er.getValue();
						Panel p = new SeatchTeacherByMaritalStatus(rs, rs1, stm, stmt).createContetPanel(emis, status);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});

				}

			}
				break;
			case "Year of Joining School": {
				if (emis.startsWith("d")) {
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, schoolNames);
					schoolNames.addValueChangeListener((er) -> {
						String schoolname = (String) er.getValue();
						horizontalLayout.removeAllComponents();
						horizontalLayout.addComponents(searchCriteria, schoolNames, yearOfJoiningSchool);
						yearOfJoiningSchool.addValueChangeListener((eer) -> {
							String year = (String) eer.getValue();
							layout.removeAllComponents();
							Panel p = new SearchTeacherByYearOfJoiningSchool(rs, rs1, stm, stmt)
									.createContetPanel(schoolname, year);
							layout.addComponents(horizontalLayout, p);
						});

					});
				} else {
					horizontalLayout.removeAllComponents();
					horizontalLayout.addComponents(searchCriteria, yearOfJoiningSchool);
					yearOfJoiningSchool.addValueChangeListener((er) -> {
						String year = (String) er.getValue();
						Panel p = new SearchTeacherByYearOfJoiningSchool(rs, rs1, stm, stmt).createContetPanel(emis,
								year);
						layout.removeAllComponents();
						layout.addComponents(horizontalLayout, p);
					});

				}
			}
				break;
			default: {
				new Notification("Information", "Invalid Search Criteria", Notification.Type.WARNING_MESSAGE, true)
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
			if (tabs.getTab(x).getCaption().equals("Search Teacher")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Search Teacher", VaadinIcons.SEARCH);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
