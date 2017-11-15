/**
 *
 * @author Wellington
 */
package com.equation.equate.settings.marks.allocation;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.equation.equate.utils.allclasses.AllClasses;
import com.equation.equate.utils.date.AllYearTermsBin;
import com.equation.equate.utils.date.AllYears;
import com.equation.equate.utils.date.DateUtility;
import com.equation.equate.utils.date.GetSchoolTerm;
import com.equation.equate.utils.subjects.AllSubjects;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Wellington
 *
 */
@SuppressWarnings({ "serial" })
public class SubjectsMarkAllocation extends CustomComponent {
	ResultSet rs, rs1;
	Statement stm, stmt;
	String myclass;

	public SubjectsMarkAllocation(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;

		ComboBox<String> terms = new ComboBox<>("<h1>Select Term</h1>");
		terms.setCaptionAsHtml(true);
		terms.setEmptySelectionAllowed(false);
		terms.setItems(AllYearTermsBin.allTermsArray());
		terms.setValue(new GetSchoolTerm().thisTerm());
		// terms.addValueChangeListener(e -> myterm = (String)
		// e.getValue());
		// terms.setValue(new GetSchoolTerm().thisTerm());

		ComboBox<String> years = new ComboBox<>("<h1>Select Year:<h1/>");
		years.setCaptionAsHtml(true);
		years.setEmptySelectionAllowed(false);
		years.setItems(AllYears.makeYears());
		years.setValue(new DateUtility().getYear());

		ComboBox<String> classes = new ComboBox<>("<h1>Select Class</h1>");
		classes.setCaptionAsHtml(true);
		classes.setEmptySelectionAllowed(false);
		classes.setItems(new AllClasses(stm, rs).allClasses());
		classes.addValueChangeListener(e -> myclass = (String) e.getValue());

		Button submit = new Button("Search");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.setIcon(VaadinIcons.SEARCH);

		FormLayout formLayout = new FormLayout(classes, terms, years, submit);
		formLayout.setSpacing(true);

		HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
		splitPanel.setFirstComponent(formLayout);

		submit.addClickListener((e) -> {
			String year = (String) years.getValue();
			String date = new DateUtility().getDate();
			String myterm = (String) terms.getValue();

			if (!(myterm.equals("") || myclass.equals(""))) {
				CurrentMarks currentMarks = new CurrentMarks(this.rs, this.stm, this.rs1, this.stmt);

				ArrayList<Integer> data1 = currentMarks.forSubject(AllSubjects.ENGLISH, myterm, year, myclass);
				ArrayList<Integer> data2 = currentMarks.forSubject(AllSubjects.SHONA, myterm, year, myclass);
				ArrayList<Integer> data3 = currentMarks.forSubject(AllSubjects.MATHEMATICS, myterm, year, myclass);
				ArrayList<Integer> data4 = currentMarks.forSubject(AllSubjects.GENERAL_PAPER, myterm, year, myclass);
				ArrayList<Integer> data5 = currentMarks.forSubject(AllSubjects.AGRICULTURE, myterm, year, myclass);

				TextField english1 = new TextField("English Paper One Overal Mark:");
				english1.setRequiredIndicatorVisible(true);
				// english1.addValidator(new IntegerValidator("The Value Is Not
				// An Integer"));
				english1.setValue("" + data1.get(0));
				TextField englishperc1 = new TextField("Paper One Percentage Contribution:");
				englishperc1.setRequiredIndicatorVisible(true);
				// englishperc1.addValidator(new IntegerValidator("The Value Is
				// Not An Integer"));
				englishperc1.setValue("" + data1.get(1));
				TextField english2 = new TextField("English Paper Two Overal Mark:");
				english2.setRequiredIndicatorVisible(true);
				// english2.addValidator(new IntegerValidator("The Value Is Not
				// An Integer"));
				english2.setValue("" + data1.get(2));
				TextField englishperc2 = new TextField("Paper Two Percentage Contribution:");
				englishperc2.setRequiredIndicatorVisible(true);
				// englishperc2.addValidator(new IntegerValidator("The Value Is
				// Not An Integer"));
				englishperc2.setValue("" + data1.get(3));

				TextField shona1 = new TextField("Shona Paper One Overal Mark:");
				shona1.setRequiredIndicatorVisible(true);
				// shona1.addValidator(new IntegerValidator("The Value Is Not An
				// Integer"));
				shona1.setValue("" + data2.get(0));
				TextField shonaperc1 = new TextField("Paper One Percentage Contribution:");
				shonaperc1.setRequiredIndicatorVisible(true);
				// shonaperc1.addValidator(new IntegerValidator("The Value Is
				// Not An Integer"));
				shonaperc1.setValue("" + data2.get(1));
				TextField shona2 = new TextField("Shona Paper Two Overal Mark:");
				shona2.setRequiredIndicatorVisible(true);
				// shona2.addValidator(new IntegerValidator("The Value Is Not An
				// Integer"));
				shona2.setValue("" + data2.get(2));
				TextField shonaperc2 = new TextField("Paper Two Percentage Contribution:");
				shonaperc2.setRequiredIndicatorVisible(true);
				// shonaperc2.addValidator(new IntegerValidator("The Value Is
				// Not An Integer"));
				shonaperc2.setValue("" + data2.get(3));

				TextField maths1 = new TextField("Mathematics Paper One Overal Mark:");
				maths1.setRequiredIndicatorVisible(true);
				// maths1.addValidator(new IntegerValidator("The Value Is Not An
				// Integer"));
				maths1.setValue("" + data3.get(0));
				TextField mathsperc1 = new TextField("Paper One Percentage Contribution:");
				mathsperc1.setRequiredIndicatorVisible(true);
				// mathsperc1.addValidator(new IntegerValidator("The Value Is
				// Not An Integer"));
				mathsperc1.setValue("" + data3.get(1));
				TextField maths2 = new TextField("Mathematics Paper Two Overal Mark:");
				maths2.setRequiredIndicatorVisible(true);
				// maths2.addValidator(new IntegerValidator("The Value Is Not An
				// Integer"));
				maths2.setValue("" + data3.get(2));
				TextField mathsperc2 = new TextField("Paper Two Percentage Contribution:");
				mathsperc2.setRequiredIndicatorVisible(true);
				// mathsperc2.addValidator(new IntegerValidator("The Value Is
				// Not An Integer"));
				mathsperc2.setValue("" + data3.get(3));

				TextField gp1 = new TextField("General Paper One Overal Mark:");
				gp1.setRequiredIndicatorVisible(true);
				// gp1.addValidator(new IntegerValidator("The Value Is Not An
				// Integer"));
				gp1.setValue("" + data4.get(0));
				TextField gpperc1 = new TextField("Paper One Percentage Contribution:");
				gpperc1.setRequiredIndicatorVisible(true);
				// gpperc1.addValidator(new IntegerValidator("The Value Is Not
				// An Integer"));
				gpperc1.setValue("" + data4.get(1));
				TextField gp2 = new TextField("General Paper Two Overal Mark:");
				gp2.setRequiredIndicatorVisible(true);
				// gp2.addValidator(new IntegerValidator("The Value Is Not An
				// Integer"));
				gp2.setValue("" + data4.get(2));
				TextField gpperc2 = new TextField("Paper Two Percentage Contribution:");
				gpperc2.setRequiredIndicatorVisible(true);
				// gpperc2.addValidator(new IntegerValidator("The Value Is Not
				// An Integer"));
				gpperc2.setValue("" + data4.get(3));

				TextField agric1 = new TextField("Agriculture Paper One Overal Mark:");
				agric1.setRequiredIndicatorVisible(true);
				// agric1.addValidator(new IntegerValidator("The Value Is Not An
				// Integer"));
				agric1.setValue("" + data5.get(0));
				TextField agricperc1 = new TextField("Paper One Percentage Contribution:");
				agricperc1.setRequiredIndicatorVisible(true);
				// agricperc1.addValidator(new IntegerValidator("The Value Is
				// Not An Integer"));
				agricperc1.setValue("" + data5.get(1));
				TextField agric2 = new TextField("Agriculture Paper Two Overal Mark:");
				agric2.setRequiredIndicatorVisible(true);
				// agric2.addValidator(new IntegerValidator("The Value Is Not An
				// Integer"));
				agric2.setValue("" + data5.get(2));
				TextField agricperc2 = new TextField("Paper Two Percentage Contribution:");
				agricperc2.setRequiredIndicatorVisible(true);
				// agricperc2.addValidator(new IntegerValidator("The Value Is
				// Not An Integer"));
				agricperc2.setValue("" + data5.get(3));

				FormLayout englishform = new FormLayout(english1, englishperc1, english2, englishperc2);
				englishform.setCaption("english".toUpperCase());
				englishform.setSpacing(true);

				FormLayout shonaform = new FormLayout(shona1, shonaperc1, shona2, shonaperc2);
				shonaform.setCaption("shona".toUpperCase());
				shonaform.setSpacing(true);

				FormLayout mathsform = new FormLayout(maths1, mathsperc1, maths2, mathsperc2);
				mathsform.setCaption("maths".toUpperCase());
				mathsform.setSpacing(true);

				FormLayout gpform = new FormLayout(gp1, gpperc1, gp2, gpperc2);
				gpform.setCaption("general paper".toUpperCase());
				gpform.setSpacing(true);

				FormLayout agricform = new FormLayout(agric1, agricperc1, agric2, agricperc2);
				agricform.setCaption("agriculture".toUpperCase());
				agricform.setSpacing(true);

				Button update = new Button("UPDATE");
				update.setIcon(VaadinIcons.UPLOAD);
				update.addStyleName(ValoTheme.BUTTON_PRIMARY);
				update.addClickListener((ee) -> {
					UpdateMarkAllocation updateAllocation = new UpdateMarkAllocation(this.stm, this.stmt, this.rs,
							this.rs1);
					if (!(english1.getValue().equals("") || englishperc1.getValue().equals("")
							|| english2.getValue().equals("") || englishperc2.getValue().equals("")
							|| shona1.getValue().equals("") || shonaperc1.getValue().equals("")
							|| shona2.getValue().equals("") || shonaperc2.getValue().equals("")
							|| maths1.getValue().equals("") || mathsperc1.getValue().equals("")
							|| maths2.getValue().equals("") || mathsperc2.getValue().equals("")
							|| gp1.getValue().equals("") || gpperc1.getValue().equals("") || gp2.getValue().equals("")
							|| gpperc2.getValue().equals("") || agric1.getValue().equals("")
							|| agricperc1.getValue().equals("") || agric2.getValue().equals("")
							|| agricperc2.getValue().equals(""))) {
						updateAllocation.updateMarks(english1.getValue(), englishperc1.getValue(), english2.getValue(),
								englishperc2.getValue(), AllSubjects.ENGLISH, myterm, year, date, myclass);
						updateAllocation.updateMarks(shona1.getValue(), shonaperc1.getValue(), shona2.getValue(),
								shonaperc2.getValue(), AllSubjects.SHONA, myterm, year, date, myclass);
						updateAllocation.updateMarks(maths1.getValue(), mathsperc1.getValue(), maths2.getValue(),
								mathsperc2.getValue(), AllSubjects.MATHEMATICS, myterm, year, date, myclass);
						updateAllocation.updateMarks(gp1.getValue(), gpperc1.getValue(), gp2.getValue(),
								gpperc2.getValue(), AllSubjects.GENERAL_PAPER, myterm, year, date, myclass);
						updateAllocation.updateMarks(agric1.getValue(), agricperc1.getValue(), agric2.getValue(),
								agricperc2.getValue(), AllSubjects.AGRICULTURE, myterm, year, date, myclass);

						new Notification("<h1>All Records have been updated!<h1/>", "",
								Notification.Type.TRAY_NOTIFICATION, true).show(Page.getCurrent());

					} else {
						// empty field
						new Notification(
								"<h1>An Empty Field Has Been Detected!!!<br>Make Sure That All Fields Are Filled!<h1/>",
								"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
					}

				});

				GridLayout gridLayout = new GridLayout(6, 1, englishform, shonaform, mathsform, gpform, agricform,
						update);
				gridLayout.setSpacing(true);

				TabSheet sheet = new TabSheet();
				sheet.addTab(englishform, "english".toUpperCase(), VaadinIcons.BOOK);
				sheet.addTab(shonaform, "shona".toUpperCase(), VaadinIcons.ARCHIVE);
				sheet.addTab(mathsform, "mathematics".toUpperCase(), VaadinIcons.CHART_GRID);
				sheet.addTab(gpform, "general paper".toUpperCase(), VaadinIcons.SUN_RISE);
				sheet.addTab(agricform, "agriculture".toUpperCase(), VaadinIcons.CLOUD);

				// HorizontalLayout horizontalLayout = new
				// HorizontalLayout(gridLayout);
				HorizontalLayout horizontalLayout = new HorizontalLayout(sheet);
				horizontalLayout.setSpacing(true);

				VerticalLayout verticalLayout = new VerticalLayout(horizontalLayout, update);
				verticalLayout.setSpacing(true);

				splitPanel.setSecondComponent(verticalLayout);

			} else {
				new Notification("<h1>A Blank selection cannot be submitted<h1/>", "", Notification.Type.ERROR_MESSAGE,
						true).show(Page.getCurrent());
			}
		});
		splitPanel.setSplitPosition(30, Unit.PERCENTAGE);

		VerticalLayout layout = new VerticalLayout(splitPanel);
		layout.setComponentAlignment(splitPanel, Alignment.MIDDLE_CENTER);

		this.setCompositionRoot(layout);
	}

	public void insertTab(TabSheet tabs) {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Settings")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Settings", VaadinIcons.LOCK);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

}
