/**
 *
 * @author Wellington
 */
package com.equation.equate.capture;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.equate.utils.allclasses.AllClasses;
import com.equation.equate.utils.date.AllYearTermsBin;
import com.equation.equate.utils.date.AllYears;
import com.equation.equate.utils.date.DateUtility;
import com.equation.equate.utils.date.GetSchoolTerm;
import com.equation.equate.utils.file.receiver.MyBatchFileReceiver;
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
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Wellington
 *
 */
@SuppressWarnings("serial")
public class CaptureBatchDetails extends CustomComponent {
	Statement stm, stmt;
	ResultSet rs, rs1;
	String myclass;

	@SuppressWarnings("deprecation")
	public CaptureBatchDetails(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.stm = stm;
		this.rs = rs;
		this.stmt = stmt;
		this.rs1 = rs1;

		ComboBox<String> classnames = new ComboBox<>("<h1>Class Name:<h1/>");
		classnames.setEmptySelectionAllowed(false);
		classnames.setCaptionAsHtml(true);
		classnames.setItems(new AllClasses(stm, rs).allClasses());
		classnames.addValueChangeListener((e) -> {
			myclass = (String) e.getValue();
		});

		ComboBox<String> terms = new ComboBox<>("<h1>Term:<h1/>");
		terms.setCaptionAsHtml(true);
		terms.setEmptySelectionAllowed(false);
		terms.setItems(AllYearTermsBin.allTermsArray());
		terms.setValue(new GetSchoolTerm().thisTerm());

		ComboBox<String> years = new ComboBox<>("<h1>Year:<h1/>");
		years.setCaptionAsHtml(true);
		years.setEmptySelectionAllowed(false);
		years.setItems(AllYears.makeYears());
		years.setValue(new DateUtility().getYear());

		Button next = new Button("Next >");
		next.addStyleName(ValoTheme.BUTTON_PRIMARY);

		next.addClickListener((e) -> {
			String grade = myclass;
			String date = new DateUtility().getDate();
			String year = (String) years.getValue();

			String term = (String) terms.getValue();
			// System.out.println(grade+" term "+term);

			if (!(grade.equals("") || term.equals(""))) {
				String query = "SELECT class_name FROM paper1markschedule WHERE class_name = '" + grade
						+ "' AND term = '" + term + "' AND year = '" + year + "'";
				try {
					this.rs = this.stm.executeQuery(query);
					this.rs.last();
					int rows = this.rs.getRow();

					if (rows > 0) {

						Window window = new Window();
						window.setModal(true);
						window.center();
						window.setWidth("100%");
						window.setHeight("50%");

						Button ok = new Button("Continue");
						ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
						ok.setIcon(VaadinIcons.THUMBS_UP_O);
						ok.addClickListener((ee) -> {
							UI.getCurrent().removeWindow(window);
							window.close();
							uploadWindow(grade, term, year, date);
						});

						Button cancel = new Button("Cancel");
						cancel.addStyleName(ValoTheme.BUTTON_DANGER);
						cancel.setIcon(VaadinIcons.CLOSE);
						cancel.addClickListener((ee) -> {
							UI.getCurrent().removeWindow(window);
							window.close();
						});

						Label lbl = new Label(
								"<h1 style='color:red;'><center>" + grade.toUpperCase()
										+ " raw mark schedule has already been posted. <br/>Confirm that you are posting ommitted exceptions that were not successfully posted!</center><h1/>",
								ContentMode.HTML);
						HorizontalLayout horizontalLayout = new HorizontalLayout(ok, cancel);
						horizontalLayout.setSpacing(true);
						VerticalLayout layout = new VerticalLayout(lbl, horizontalLayout);
						layout.setSpacing(true);
						layout.setComponentAlignment(lbl, Alignment.MIDDLE_CENTER);
						layout.setComponentAlignment(horizontalLayout, Alignment.BOTTOM_CENTER);
						window.setContent(layout);
						UI.getCurrent().addWindow(window);

					} else {
						String search = "SELECT * FROM subject_marks WHERE term ='" + term + "' AND year = '" + year
								+ "' AND class_name = '" + grade + "'";
						this.rs1 = this.stmt.executeQuery(search);
						this.rs1.last();
						int r = this.rs1.getRow();
						System.out.println(r);
						if (r > 0) {
							uploadWindow(grade, term, year, date);
						} else {
							new Notification(
									"<h1>Overal Marks For " + grade.toUpperCase()
											+ " subjects are not set!<br/>Go to Settings, click Mark Allocation, so that you can set overal marks for all subjects and papers.<h1/>",
									"", Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
						}
					}

				} catch (SQLException ee) {
					ee.printStackTrace();
				}

			} else {
				new Notification("<h1>Blank fields have been detected!<h1/>", "", Notification.TYPE_ERROR_MESSAGE, true)
						.show(Page.getCurrent());
			}

		});
		FormLayout formLayout = new FormLayout(classnames, terms, years, next);
		formLayout.setSpacing(true);

		HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
		horizontalLayout.setSpacing(true);

		VerticalLayout layout = new VerticalLayout(horizontalLayout);
		layout.setSpacing(true);
		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);

		this.setCompositionRoot(layout);

	}

	public void uploadWindow(String grade, String term, String year, String date) {

		Window window = new Window("<h1 style='color:blue;'>Upload " + grade.toUpperCase() + " Raw Mark Schedule<h1/>");
		window.setCaptionAsHtml(true);
		window.center();
		window.setWidth("100%");
		window.setHeight("50%");
		window.setModal(true);

		Button cancel = new Button("Cancel");
		cancel.addStyleName(ValoTheme.BUTTON_DANGER);
		cancel.setIcon(VaadinIcons.CLOSE);

		Label label = new Label(
				"<h2 style='color:blue'><center>Upload the raw mark schedule file. <br/>The file should be of type CSV</center></h2>",
				ContentMode.HTML);

		MyBatchFileReceiver receiver = new MyBatchFileReceiver(window, grade, stm, stmt, rs, rs1, term, year, date);

		Upload upload = new Upload("Upload Batch File Here", receiver);
		upload.setButtonCaption("Upload Now");
		upload.addSucceededListener(receiver);

		cancel.addClickListener((e) -> {
			UI.getCurrent().removeWindow(window);
			window.close();
		});

		HorizontalLayout horizontalLayout = new HorizontalLayout(upload);
		horizontalLayout.setSpacing(true);

		VerticalLayout layout = new VerticalLayout(label, horizontalLayout);
		layout.setSpacing(true);
		layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
		layout.setComponentAlignment(horizontalLayout, Alignment.BOTTOM_CENTER);
		// layout.setComponentAlignment(cancel, Alignment.BOTTOM_RIGHT);

		window.setContent(layout);
		UI.getCurrent().addWindow(window);
	}

	public void insertTab(TabSheet tabs) {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Batch Uploads")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Batch Uploads", VaadinIcons.UPLOAD);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}
}