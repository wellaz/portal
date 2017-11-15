package com.equation.equate.generate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import com.equation.equate.utils.allclasses.AllClasses;
import com.equation.equate.utils.date.AllYearTermsBin;
import com.equation.equate.utils.date.AllYears;
import com.equation.equate.utils.date.DateUtility;
import com.equation.equate.utils.date.GetSchoolTerm;
import com.equation.equate.utils.subjects.AllSubjects;
import com.equation.equate.utils.tablename.Tablename;
import com.vaadin.icons.VaadinIcons;
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
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class GenerateFinalMarkSchedule extends CustomComponent {

	ResultSet rs, rs1;
	Statement stm, stmt, stmt1;

	public GenerateFinalMarkSchedule(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, Statement stmt1) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.stmt1 = stmt1;
		ComboBox<String> subjects = new ComboBox<>("<h1>Select Subject:<h1/>");
		subjects.setCaptionAsHtml(true);
		subjects.addStyleName("subjects");
		subjects.setEmptySelectionAllowed(false);
		subjects.setItems(AllSubjects.allSubjects());

		ComboBox<String> terms = new ComboBox<>("<h1>Select Term:<h1/>");
		terms.setCaptionAsHtml(true);
		terms.addStyleName("subjects");
		terms.setEmptySelectionAllowed(false);
		terms.setItems(AllYearTermsBin.allTermsArray());
		terms.setValue(new GetSchoolTerm().thisTerm());

		ComboBox<String> years = new ComboBox<>("<h1>Select Year:<h1/>");
		years.setCaptionAsHtml(true);
		years.setEmptySelectionAllowed(false);
		years.setItems(AllYears.makeYears());
		years.setValue(new DateUtility().getYear());

		ComboBox<String> papers = new ComboBox<>("<h1>Select Paper:<h1/>");
		papers.setCaptionAsHtml(true);
		papers.addStyleName("papers");
		papers.setEmptySelectionAllowed(false);
		papers.setItems(Arrays.asList("1", "2", "All"));

		ComboBox<String> classnames = new ComboBox<>("<h1>Select Class Name:<h1/>");
		classnames.setCaptionAsHtml(true);
		classnames.addStyleName("classnames");
		classnames.setEmptySelectionAllowed(false);
		classnames.setItems(new AllClasses(stm, rs).allClasses());

		Button view = new Button("View mark Schedule");
		view.addStyleName(ValoTheme.BUTTON_PRIMARY);
		view.setIcon(VaadinIcons.DESKTOP);

		view.addClickListener((e) -> {
			PaperMarkSchedule markSchedule = new PaperMarkSchedule(this.rs, this.rs1, this.stm, this.stmt);
			String subject = (String) subjects.getValue().toString();
			String paper = (String) papers.getValue().toString();
			String class_name = (String) classnames.getValue().toString();
			String term = (String) terms.getValue();
			String year = (String) years.getValue();
			if (!(subject.equals("") || paper.equals("") || class_name.equals("") || term.equals(""))) {

				try {

					if (paper.equals("1")) {
						String query = "SELECT class_name,subject FROM " + Tablename.PAPER_ONE + " WHERE class_name = '"
								+ class_name + "' AND subject = '" + subject + "' AND term = '" + term
								+ "' AND year = '" + year + "'";
						this.rs1 = this.stmt.executeQuery(query);
						if (this.rs1.next()) {
							UI.getCurrent().addWindow(
									markSchedule.prepareMarkSchedule(class_name, subject, paper, Tablename.PAPER_ONE));
						} else {
							String thepaper = (paper.equals("All")) ? " for all papers" : " paper " + paper;
							new Notification(
									"<h1>There was no Raw Mark Schedule uploaded for " + class_name + " " + subject
											+ thepaper + "!<br>Click this button after filling the above form.<h1/>",
									"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
						}
					} else if (paper.equals("2")) {
						String query = "SELECT class_name,subject FROM " + Tablename.PAPER_TWO + " WHERE class_name = '"
								+ class_name + "' AND subject = '" + subject + "' AND term = '" + term
								+ "' AND year = '" + year + "'";
						this.rs1 = this.stmt.executeQuery(query);
						if (this.rs1.next()) {
							UI.getCurrent().addWindow(
									markSchedule.prepareMarkSchedule(class_name, subject, paper, Tablename.PAPER_TWO));
						} else {
							String thepaper = (paper.equals("All")) ? " for all papers" : " paper " + paper;
							new Notification(
									"<h1>There was no Raw Mark Schedule uploaded for " + class_name + " " + subject
											+ thepaper + "!<br>Click this button after filling the above form.<h1/>",
									"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
						}
					} else {
						String query = "SELECT class_name,subject FROM " + Tablename.PAPER_TWO + " WHERE class_name = '"
								+ class_name + "' AND subject = '" + subject + "' AND term = '" + term
								+ "' AND year = '" + year + "'";
						this.rs1 = this.stmt.executeQuery(query);
						if (this.rs1.next()) {
							UI.getCurrent().addWindow(markSchedule.prepareAllPapersMarkSchedule(class_name, subject));
						} else {
							String thepaper = (paper.equals("All")) ? " for all papers" : " paper " + paper;
							new Notification(
									"<h1>There was no Raw Mark Schedule uploaded for " + class_name + " " + subject
											+ thepaper + "!<br>Click this button after filling the above form.<h1/>",
									"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
						}
					}

				} catch (SQLException ee) {
					ee.printStackTrace();
				}

			} else {
				new Notification(
						"<h1>A blank field has been detected!<br>Click this button after filling the above form.<h1/>",
						"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			}

		});

		FormLayout formLayout = new FormLayout(classnames, terms, years, subjects, papers, view);
		formLayout.setSpacing(true);

		HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
		horizontalLayout.setSpacing(true);

		VerticalLayout layout = new VerticalLayout(horizontalLayout);
		layout.setSpacing(true);
		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
		this.setCompositionRoot(layout);

	}

	public void insertTab(TabSheet tabs) {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Downloads")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Downloads", VaadinIcons.DOWNLOAD);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

}
