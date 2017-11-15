package com.equation.school.subjects;

import java.sql.ResultSet;
import java.sql.Statement;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class CaptureSchoolSubjects extends CustomComponent {
	Statement stm, stmt;
	ResultSet rs, rs1;

	public CaptureSchoolSubjects(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.stm = stm;
		this.rs = rs;
		this.stmt = stmt;
		this.rs1 = rs1;

		this.setCompositionRoot(createlayout());
	}

	private VerticalLayout createlayout() {
		HorizontalSplitPanel splitPanel = new HorizontalSplitPanel(leftlayout(), rightlayout());
		splitPanel.setSplitPosition(40, Unit.PERCENTAGE);

		HorizontalLayout horizontalLayout = new HorizontalLayout(splitPanel);
		VerticalLayout layout = new VerticalLayout(horizontalLayout);
		layout.addStyleName(ValoTheme.LAYOUT_WELL);
		return layout;
	}

	private VerticalLayout leftlayout() {
		Label lbl = new Label("<h2 style = 'color:blue;'><center>Upload A CSV File <center/><h2/>", ContentMode.HTML);
		lbl.setCaptionAsHtml(true);
		UploadSubjectsFile uploadSubjectsFile = new UploadSubjectsFile(stm, stmt, rs, rs1);

		Upload upload = new Upload("Upload here", uploadSubjectsFile);
		upload.setButtonCaption("Choose File");
		upload.addSucceededListener(uploadSubjectsFile);

		VerticalLayout layout = new VerticalLayout(lbl, upload);
		// layout.setCaption("<h3
		// style='color:blue;text-decoration:underline;'>Make An Upload<h3/>");
		// layout.setCaptionAsHtml(true);
		layout.setComponentAlignment(lbl, Alignment.MIDDLE_CENTER);
		layout.setComponentAlignment(upload, Alignment.BOTTOM_CENTER);
		return layout;

	}

	private VerticalLayout rightlayout() {
		TextField subjects = new TextField("<h2>Subject Name:<h2/>");
		subjects.setCaptionAsHtml(true);
		subjects.setPlaceholder("Subject Name");
		subjects.setRequiredIndicatorVisible(true);

		TextField codes = new TextField("<h2>Subject Code:<h2/>");
		codes.setCaptionAsHtml(true);
		codes.setPlaceholder("Subject Code");
		codes.setRequiredIndicatorVisible(true);

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.addClickListener((e) -> {
			String subject = subjects.getValue();
			String code = codes.getValue();
			if (!subject.equals("")) {
				new InsertSubjects().insertData(stm, subject, code);
				subjects.clear();
				codes.clear();
			} else {
				subjects.focus();
			}
		});

		FormLayout formLayout = new FormLayout(subjects, codes, submit);
		formLayout.setCaption("<h2 style='color:blue;text-decoration:underline;'>Capture Subject<h2/>");
		formLayout.setCaptionAsHtml(true);
		HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);

		VerticalLayout layout = new VerticalLayout(horizontalLayout);
		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
		return layout;

	}

	public void insertTab(TabSheet tabs) {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Upload Subjects")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Upload Subjects", VaadinIcons.DOWNLOAD);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

}
