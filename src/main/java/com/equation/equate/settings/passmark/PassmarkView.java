/**
 *
 * @author Wellington
 */
package com.equation.equate.settings.passmark;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.equate.utils.date.AllYearTermsBin;
import com.equation.equate.utils.date.AllYears;
import com.equation.equate.utils.date.DateUtility;
import com.equation.equate.utils.date.GetSchoolTerm;
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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Wellington
 *
 */
@SuppressWarnings({ "serial", "deprecation" })
public class PassmarkView extends CustomComponent {
	ResultSet rs, rs1;
	Statement stm, stmt;
	// String myterm;

	public PassmarkView(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;

		int myMark = new PassMark(this.rs1, this.rs1, this.stmt, this.stmt).getPassMark();

		TextField markField = new TextField("<h1>Pass Mark:<h1/>");
		markField.setCaptionAsHtml(true);
		markField.setRequiredIndicatorVisible(true);
		// markField.addValidator(new IntegerValidator("An Integer Is Required
		// Here!!!"));
		markField.setValue("" + myMark);

		ComboBox<String> terms = new ComboBox<>("<h1>Term:<h1/>");
		terms.setCaptionAsHtml(true);
		terms.setEmptySelectionAllowed(false);
		terms.setItems(AllYearTermsBin.allTermsArray());
		terms.setValue(new GetSchoolTerm().thisTerm());
		// terms.setValue(new GetSchoolTerm().thisTerm());
		// terms.addValueChangeListener((e) -> {
		// myterm = (String) e.getProperty().getValue();
		// });

		ComboBox<String> years = new ComboBox<>("<h1>Select Year:<h1/>");
		years.setCaptionAsHtml(true);
		years.setEmptySelectionAllowed(false);
		years.setItems(AllYears.makeYears());
		years.setValue(new DateUtility().getYear());

		Button update = new Button("UPDATE");
		update.addClickListener((ee) -> {
			String value = markField.getValue();
			DateUtility dateUtility = new DateUtility();
			String myterm = (String) terms.getValue();
			if (!(value.equals("") || myterm.equals(""))) {
				InsertPassMark insertPassMark = new InsertPassMark(this.rs, this.rs1, this.stm, this.stmt);
				insertPassMark.insertData(value, 1, myterm, dateUtility.getYear(), dateUtility.getDate());
				new Notification("Success", "Pass Mark has been updated!!!", Notification.TYPE_TRAY_NOTIFICATION, true)
						.show(Page.getCurrent());

			} else
				markField.focus();

		});
		FormLayout formLayout = new FormLayout(markField, terms, years, update);
		formLayout.setSpacing(true);
		HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
		VerticalLayout layout = new VerticalLayout(horizontalLayout);
		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
		this.setCompositionRoot(layout);
	}

	public void insertTab(TabSheet tabs) {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Pass Mark Settings")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "PassMark Settings", VaadinIcons.SAFE_LOCK);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}
}