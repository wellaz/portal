package com.eqauation.assessment.teacher.createasssignment;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import com.eqauation.assessment.teacher.createasssignment.receiver.AssignmentReceiver;
import com.equation.school.classes.collection.AllClasses;
import com.equation.school.subjects.AllSchoolSubjects;
import com.equation.teacher.assinment.set.SetOnPageAssinment;
import com.equation.utils.date.AllTerms;
import com.equation.utils.date.AllYears;
import com.equation.utils.date.DateUtility;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class CreateAssignment extends CustomComponent {
	String schoolname;
	Statement stm, stmt;
	ResultSet rs, rs1;

	public CreateAssignment(String schoolname, Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		;
		this.schoolname = schoolname;
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.setCompositionRoot(createayout());
	}

	private VerticalLayout createayout() {
		VerticalLayout layout = new VerticalLayout();

		TextField topics = new TextField("<h2>Assignment Topic:<h2>");
		topics.setRequiredIndicatorVisible(true);
		topics.setCaptionAsHtml(true);
		topics.setPlaceholder("Topic here...");

		ComboBox<String> classes = new ComboBox<>("<h1>Select Class:<h2/>");
		classes.setRequiredIndicatorVisible(true);
		classes.setCaptionAsHtml(true);
		classes.setItems(new AllClasses(stm, rs).classesCollection(schoolname));

		TextField totalmark = new TextField("<h2>Total Mark:<h2/>");
		totalmark.setPlaceholder("Total Mark...");
		totalmark.setCaptionAsHtml(true);
		totalmark.setRequiredIndicatorVisible(true);

		ComboBox<String> subjects = new ComboBox<>("<h2>Select Subject:<h2/>");
		subjects.setRequiredIndicatorVisible(true);
		subjects.setCaptionAsHtml(true);
		subjects.setItems(new AllSchoolSubjects(rs, rs1, stm, stmt).allSchoolSubjects());

		ComboBox<String> terms = new ComboBox<>("<h2>Term<h2/>");
		terms.setRequiredIndicatorVisible(true);
		terms.setCaptionAsHtml(true);
		terms.setItems(new AllTerms().allTerms());
		terms.setValue(new AllTerms().thisTerm());

		ComboBox<String> years = new ComboBox<>("<h2>Year<h2/>");
		years.setRequiredIndicatorVisible(true);
		years.setCaptionAsHtml(true);
		years.setItems(AllYears.makeYears());
		years.setValue(new DateUtility().getYear());

		DateField dateField = new DateField("<h2>Due Date:<h2/>");
		dateField.setRequiredIndicatorVisible(true);
		dateField.setCaptionAsHtml(true);
		dateField.setDateFormat("yyyy-MM-dd");
		dateField.setValue(LocalDate.now());

		ComboBox<String> types = new ComboBox<>("<h2>Assignment Type:<h2/>");
		types.setEmptySelectionAllowed(false);
		types.setRequiredIndicatorVisible(true);
		types.setCaptionAsHtml(true);
		types.setItems(AssignmentTypes.types());

		Button next = new Button("Next");
		next.addStyleName(ValoTheme.BUTTON_PRIMARY);

		next.addClickListener((e) -> {
			String topic = topics.getValue();
			String classname = classes.getValue();
			String mark = totalmark.getValue();
			String subject = subjects.getValue();
			String term = terms.getValue();
			String year = years.getValue();
			String due = String.format("%1$tY-%1$tm-%1$td", dateField.getValue());
			String type = types.getValue();

			if (!(topic.equals("") || classname.equals("") || mark.equals("") || subject.equals("") || term.equals("")
					|| year.equals("") || due.equals("") || type.equals(""))) {

				String date_posted = new DateUtility().getDate();
				String class_id = new AllClasses(stm, rs).getClassIdFor(classname);
				String subject_id = new AllSchoolSubjects(rs, rs1, stm, stmt).getSubjectIDFor(subject);
				if (type.equals(AssignmentTypes.DOCUMENT))
					createUploadWindow(classname, subject, mark, due, topic, term, year, topics, totalmark, date_posted,
							class_id, subject_id);
				else
					UI.getCurrent().addWindow(new SetOnPageAssinment(rs, rs1, stm, stmt, subject, classname, topic,
							class_id, mark, subject_id, term, year, date_posted, due));

			} else {
				new Notification(
						"<h1 style='color:white;'>An empty field has been detected!<br/> Make sure that you capture all fields!<h1/>",
						"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			}

		});

		FormLayout formLayout = new FormLayout(topics, classes, subjects, totalmark, terms, years, dateField, types,
				next);
		formLayout.setCaption("<h2 style='color:red;text-decoration:underline;'>Set Out An Assigment<h2/>");

		HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
		layout.addComponent(horizontalLayout);
		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
		return layout;

	}

	public void insertTab(TabSheet tabs) {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Upload An Assignment")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Upload An Assignment", VaadinIcons.DOWNLOAD);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

	private Window createUploadWindow(String classname, String subject, String mark, String due, String topic,
			String term, String year, TextField topics, TextField totalmark, String date_posted, String class_id,
			String subject_id) {
		Window window = new Window("<h2>Upload " + classname.toUpperCase() + " Assignment <h2/>");
		window.center();
		window.setWidth("100%");
		window.setHeight("50%");

		Label label = new Label("<h2 style='color=blue;'><center>Class Name: " + classname + "<br/>Subject: " + subject
				+ "<br/>Topic: " + topic + "<br/>Total Mark: " + mark + "<br/>Due Date: " + due
				+ "<br/>You can upload the file.<center/><h2/>");
		label.setCaptionAsHtml(true);

		AssignmentReceiver assignmentReceiver = new AssignmentReceiver(window, stm, stmt, rs, rs1, topic, class_id,
				mark, subject_id, term, year, date_posted, due, classname, topics, totalmark);

		Upload upload = new Upload("Upload Here", assignmentReceiver);
		upload.setButtonCaption("Upload Now");
		upload.addSucceededListener(assignmentReceiver);

		VerticalLayout layout = new VerticalLayout(label, upload);
		layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
		layout.setComponentAlignment(upload, Alignment.BOTTOM_CENTER);
		window.setContent(layout);

		return window;
	}

}