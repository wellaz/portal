package com.equation.equate.capture;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.equate.utils.allclasses.AllClasses;
import com.equation.equate.utils.date.AllYearTermsBin;
import com.equation.equate.utils.date.AllYears;
import com.equation.equate.utils.date.DateUtility;
import com.equation.equate.utils.date.GetSchoolTerm;
import com.equation.equate.utils.file.receiver.MyReceiver;
import com.equation.equate.utils.subjects.AllSubjectPapers;
import com.equation.equate.utils.subjects.AllSubjects;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
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

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial", "deprecation" })
public class CaptureDetails extends CustomComponent {
	Statement stm;
	ResultSet rs;
	String term, subject, grade, paper;

	public CaptureDetails(Statement stm, ResultSet rs) {
		this.stm = stm;
		this.rs = rs;
		ComboBox<String> subjects = new ComboBox<>("<h1>Select Subject:<h1/>");
		subjects.setCaptionAsHtml(true);
		subjects.setEmptySelectionAllowed(false);
		subjects.setItems(AllSubjects.allSubjects());
		subjects.addValueChangeListener(e -> subject = (String) e.getValue());

		ComboBox<String> papers = new ComboBox<>("<h1>Select Paper:<h1/>");
		papers.setCaptionAsHtml(true);
		papers.setEmptySelectionAllowed(false);
		papers.setItems(AllSubjectPapers.allPapers());
		papers.addValueChangeListener(e -> paper = (String) e.getValue());

		ComboBox <String> terms = new ComboBox<>("<h1>Select Term:<h1/>");
		terms.setCaptionAsHtml(true);
		terms.setEmptySelectionAllowed(false);
		terms.setItems(AllYearTermsBin.allTermsArray());
		terms.setValue(new GetSchoolTerm().thisTerm());

		ComboBox<String> years = new ComboBox<>("<h1>Select Year:<h1/>");
		years.setCaptionAsHtml(true);
		years.setEmptySelectionAllowed(false);
		years.setItems(AllYears.makeYears());
		years.setValue(new DateUtility().getYear());

		ComboBox <String> classnames = new ComboBox<>("Class Name:");
		classnames.setEmptySelectionAllowed(false);
		classnames.setItems(new AllClasses(stm, rs).allClasses());
		classnames.addValueChangeListener(e -> grade = (String) e.getValue());

		TextField totalmarks = new TextField("Total Mark:");
		totalmarks.addStyleName("totalmarks");
		totalmarks.setRequiredIndicatorVisible(true);
		// totalmarks.addValidator(new IntegerValidator("Enter An Integer"));

		TextField percentages = new TextField("Percentage Contribution (%):");
		percentages.addStyleName("percentages");
		percentages.setRequiredIndicatorVisible(true);
	//	percentages.addValidator(new IntegerValidator("Enter An Integer"));

		Button next = new Button("Next >");
		next.addStyleName(ValoTheme.BUTTON_PRIMARY);

		next.addClickListener((e) -> {
			String totalmark = totalmarks.getValue();
			String percentage = percentages.getValue();
			String year = (String) years.getValue(), date = new DateUtility().getDate();
			term = (String) terms.getValue();
			if (!(subject.equals("") || paper.equals("") || grade.equals("") || totalmark.equals("")
					|| percentage.equals(""))) {

				Window window = new Window("Upload Raw Mark Schedule");
				window.center();
				window.setWidth("40%");
				window.setHeight("30%");
				window.setModal(true);

				Label label = new Label(
						"<h3><center>Upload the raw mark schedule file. The file should be of type CSV</center></h3>",
						ContentMode.HTML);

				MyReceiver receiver = new MyReceiver(window, grade, subject, paper, totalmark, percentage, stm, term,
						year, date);
				Upload upload = new Upload("Upload File Here", receiver);
				upload.setButtonCaption("Upload Now");
				upload.addSucceededListener(receiver);

				VerticalLayout layout = new VerticalLayout(label, upload);
				layout.setSpacing(true);

				window.setContent(layout);
				UI.getCurrent().addWindow(window);

			} else {
				new Notification("<h1>Blank fields have been detected<h1/>", "", Notification.TYPE_ERROR_MESSAGE, true)
						.show(Page.getCurrent());
			}

		});
		FormLayout formLayout = new FormLayout(terms, years, classnames, subjects, papers, totalmarks, percentages,
				next);
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
			if (tabs.getTab(x).getCaption().equals("Uploads")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Uploads", VaadinIcons.UPLOAD);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}
}