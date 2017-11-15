package com.equation.checkin.ancillary;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.student.autosearch.ClearAndFocusInputField;
import com.equation.utils.application.basepath.ApplicationBasePath;
import com.equation.utils.date.CalculateAge;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.v7.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */

@SuppressWarnings("deprecation")
public class AncillaryGeneralDetails {
	ResultSet rs, rs1;
	Statement stm, stmt;
	String schoolID;
	VerticalLayout layout;
	TextField barcodeField;

	public AncillaryGeneralDetails(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, String schoolID,
			VerticalLayout layout, TextField barcodeField) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.schoolID = schoolID;
		this.layout = layout;
		this.barcodeField = barcodeField;
	}

	public VerticalLayout displayTeacherDetails(String ecNumber) {
		VerticalLayout layout1 = new VerticalLayout();

		String selectQuery = "SELECT firstName FROM acillary,schools WHERE acillary.workerID = '" + ecNumber
				+ "' AND  schools.schoolID = '" + schoolID + "' OR schools.schoolName = '" + schoolID + "'";
		try {
			rs1 = stmt.executeQuery(selectQuery);
			rs1.last();
			int rows = rs1.getRow();
			if (rows > 0) {
				Table nametable = new Table("Personal Details");
				nametable.setSelectable(true);
				nametable.addContainerProperty("Property", String.class, null);
				nametable.addContainerProperty("Value", String.class, null);

				Table contactstable = new Table("Contact Details");
				contactstable.setSelectable(true);
				contactstable.addContainerProperty("Property", String.class, null);
				contactstable.addContainerProperty("Value", String.class, null);

				Table basix = new Table();
				basix.setSelectable(true);
				basix.addContainerProperty("Property", String.class, null);
				basix.addContainerProperty("Value", String.class, null);

				Table nextofkeen = new Table("Next Of Keen");
				nextofkeen.setSelectable(true);
				nextofkeen.addContainerProperty("Property", String.class, null);
				nextofkeen.addContainerProperty("Value", String.class, null);

				Table oleveltable = new Table("O'Level Passes");
				oleveltable.setSelectable(true);
				oleveltable.addContainerProperty("Subject", String.class, null);
				oleveltable.addContainerProperty("Symbol", String.class, null);
				oleveltable.addContainerProperty("School", String.class, null);
				oleveltable.addContainerProperty("Year", String.class, null);

				Table aleveltable = new Table("A'Level Passes");
				aleveltable.setSelectable(true);
				aleveltable.addContainerProperty("Subject", String.class, null);
				aleveltable.addContainerProperty("Symbol", String.class, null);
				aleveltable.addContainerProperty("School", String.class, null);
				aleveltable.addContainerProperty("Year", String.class, null);

				Table professiontable = new Table("Professional Qualification");
				professiontable.setSelectable(true);
				professiontable.addContainerProperty("Programme", String.class, null);
				professiontable.addContainerProperty("Specilization Area", String.class, null);
				professiontable.addContainerProperty("University Or College", String.class, null);
				professiontable.addContainerProperty("Proficiency", String.class, null);
				professiontable.addContainerProperty("Year", String.class, null);

				String query1 = "SELECT firstName,surname,gender,dob,nationalID,nationality,dojs FROM acillary,schools WHERE acillary.workerID = '"
						+ ecNumber + "' AND  schools.schoolID = '" + schoolID + "' OR schools.schoolName = '" + schoolID
						+ "'";
				rs = stm.executeQuery(query1);
				if (rs.next()) {
					String firstName = rs.getString(1);
					String surname = rs.getString(2);
					String gender = rs.getString(3);
					String dateOfBirth = rs.getString(4);
					String nationalID = rs.getString(5);
					String nationality = rs.getString(6);
					String dOJS = rs.getString(7);

					int age = new CalculateAge().getAge(dateOfBirth);

					nametable.addItem(new Object[] { "Worker ID", ecNumber }, 0);
					nametable.addItem(new Object[] { "First Name", firstName }, 1);
					nametable.addItem(new Object[] { "Last name", surname }, 2);
					nametable.addItem(new Object[] { "Gender", gender }, 3);
					nametable.addItem(new Object[] { "Age", String.valueOf(age) }, 4);
					nametable.addItem(new Object[] { "National ID", nationalID }, 5);
					nametable.addItem(new Object[] { "Nationality", nationality }, 6);
					nametable.addItem(new Object[] { "Date Of Joining School", dOJS }, 7);
					nametable.setPageLength(nametable.size());
					nametable.setColumnCollapsingAllowed(true);
					nametable.setFooterVisible(true);
					nametable.setSelectable(true);
					// nametable.setSizeFull();
					Button close = new Button("Close");
					close.setIcon(VaadinIcons.CLOSE);
					close.addStyleName(ValoTheme.BUTTON_DANGER);
					close.addClickListener((e) -> {
						this.layout.removeAllComponents();
						clearAndFocusBarcodeField(barcodeField);
					});
					HorizontalLayout horizontalLayout = new HorizontalLayout(close);
					horizontalLayout.setSpacing(true);

					Image image = new Image(ecNumber, new FileResource(new File(
							ApplicationBasePath.basePath() + "/WEB-INF/images/" + ecNumber.toUpperCase() + ".png")));
					image.setWidth("200px");
					image.setHeight("200px");

					GridLayout gridLayout = new GridLayout(2, 1, image,
							nametable/*
										 * , contactstable, basix, nextofkeen,
										 * oleveltable, aleveltable,
										 * professiontable
										 */);
					gridLayout.setSpacing(true);

					layout1.addComponents(horizontalLayout, gridLayout);
					layout1.setSpacing(true);

				} else {
					new Notification("Error", "No Data", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
				}
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		// Panel panel = new Panel();
		// panel.setContent(layout1);
		// VerticalLayout layout2 = new VerticalLayout(panel);
		// layout2.setSpacing(true);
		// UI.getCurrent().addWindow(new Window("Test", layout1));

		return layout1;

	}

	private void clearAndFocusBarcodeField(TextField barcodeField) {
		ClearAndFocusInputField.clearAndFocusBarcodeField(barcodeField);
	}
}