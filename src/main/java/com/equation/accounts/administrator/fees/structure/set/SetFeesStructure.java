package com.equation.accounts.administrator.fees.structure.set;

import java.io.File;
import java.sql.Statement;

import com.equation.utils.application.basepath.ApplicationBasePath;
import com.equation.utils.date.DateUtility;
import com.equation.utils.doublevalue.format.DoubleValueFormat;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial", "deprecation" })
public class SetFeesStructure extends CustomComponent implements View {
	Statement stm;
	TabSheet tabs;
	String schoolID;

	TextField tution, bspz, building, generalPurpose, sports, levi, other, total;

	public SetFeesStructure(Statement stm, TabSheet tabs, String schoolID) {
		this.stm = stm;
		this.tabs = tabs;
		this.schoolID = schoolID;

		tution = new TextField("Tuition");
		tution.setRequiredIndicatorVisible(true);
		// tution.addValidator(new DoubleValidator("Invalid Input"));

		bspz = new TextField("B S P Z");
		// bspz.addValidator(new DoubleValidator("Invalid Input"));
		bspz.setRequiredIndicatorVisible(true);

		building = new TextField("Building Levy");
		building.setRequiredIndicatorVisible(true);
		// building.addValidator(new DoubleValidator("Invalid Input"));

		generalPurpose = new TextField("General Purpose Levy");
		generalPurpose.setRequiredIndicatorVisible(true);
		// generalPurpose.addValidator(new DoubleValidator("Invalid Input"));

		sports = new TextField("N A P H / Sports");
		// sports.addValidator(new DoubleValidator("Invalid Input"));
		sports.setRequiredIndicatorVisible(true);

		levi = new TextField("Levy");
		levi.setRequiredIndicatorVisible(true);
		// levi.addValidator(new DoubleValidator("Invalid Input"));

		other = new TextField("Other");
		other.setRequiredIndicatorVisible(true);
		// other.addValidator(new DoubleValidator("Invalid Input"));

		total = new TextField("Total");
		total.setRequiredIndicatorVisible(true);
		// total.addValidator(new DoubleValidator("Invalid Input"));

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.addStyleName("admin_fees_structure_set");
		submit.addClickListener((e) -> {
			DoubleValueFormat doubleValueFormat = new DoubleValueFormat();

			if (!(tution.getValue().equals("") || bspz.getValue().equals("") || building.getValue().equals("")
					|| generalPurpose.getValue().equals("") || sports.getValue().equals("")
					|| levi.getValue().equals("") || other.getValue().equals("") || total.getValue().equals(""))) {

				double tutionn = Double.parseDouble(tution.getValue());
				double bspzz = Double.parseDouble(bspz.getValue());
				double buildingg = Double.parseDouble(building.getValue());
				double generalPurposee = Double.parseDouble(generalPurpose.getValue());
				double sportss = Double.parseDouble(sports.getValue());
				double levii = Double.parseDouble(levi.getValue());
				double otherr = Double.parseDouble(other.getValue());
				double totall = Double.parseDouble(total.getValue());

				double totalll = doubleValueFormat
						.format(tutionn + bspzz + buildingg + generalPurposee + sportss + levii + otherr);

				String dateSet = new DateUtility().getDate();
				if (doubleValueFormat.format(totalll) == doubleValueFormat.format(totall)) {
					UI.getCurrent().addWindow(createConfirmationindow(tutionn, bspzz, buildingg, generalPurposee,
							sportss, levii, otherr, totalll, dateSet, schoolID));

				} else {
					new Notification("Warning",
							"The fees breakdown is not equal to the Total entered.<br/><br/>Make sure the breakdown is equal to the total fees!",
							Notification.TYPE_WARNING_MESSAGE, true).show(Page.getCurrent());
					tution.clear();
					bspz.clear();
					building.clear();
					generalPurpose.clear();
					sports.clear();
					levi.clear();
					other.clear();
					total.clear();
				}

			} else {
				new Notification("Error", "A blank field has been detected. The record cannot be submitted!",
						Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
				tution.clear();
				bspz.clear();
				building.clear();
				generalPurpose.clear();
				sports.clear();
				levi.clear();
				other.clear();
				total.clear();
			}
		});

		FormLayout formLayout1 = new FormLayout(tution, bspz, building, generalPurpose);
		formLayout1.setSpacing(true);
		FormLayout formLayout2 = new FormLayout(sports, levi, other, total, submit);
		formLayout2.setSpacing(true);

		GridLayout formLayout = new GridLayout(2, 1, formLayout1, formLayout2);
		formLayout.setSpacing(true);

		VerticalLayout verticalLayout = new VerticalLayout();

		HorizontalLayout gridLayout = new HorizontalLayout();
		gridLayout.setSpacing(true);
		gridLayout.addComponents(formLayout);

		verticalLayout.addComponent(gridLayout);
		verticalLayout.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);

		Panel panel = new Panel(verticalLayout);
		panel.setIcon(FontAwesome.GEARS);
		panel.setWidth("50%");
		panel.setHeight("50%");
		panel.setCaption(
				"<h1>Set Fees Structure for ".toUpperCase() + new DateUtility().getYear().toUpperCase() + "<h1/>");
		panel.setCaptionAsHtml(true);
		panel.addStyleName("fees_settings");
		panel.addStyleName(ValoTheme.PANEL_WELL);

		HorizontalLayout horizontalLayout = new HorizontalLayout(panel);
		VerticalLayout layout = new VerticalLayout(horizontalLayout);
		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);

		this.setCompositionRoot(layout);
	}

	public Window createConfirmationindow(double tutionn, double bspzz, double buildingg, double generalPurposee,
			double sportss, double levii, double otherr, double totall, String dateSet, String schoolID) {

		FileResource resource = new FileResource(
				new File(ApplicationBasePath.basePath() + "/WEB-INF/images/systemlogo.png"));
		Image image = new Image();
		image.setSource(resource);
		image.setWidth("250px");
		image.setHeight("150px");

		Label tutionnlbl = new Label("Tuition");
		Label tutionnlbl2 = new Label("" + tutionn);

		Label bspzzlbl = new Label("BSPZ");
		Label bspzzlbl2 = new Label("" + bspzz);

		Label buildingglbl = new Label("Building");
		Label buildingglbl2 = new Label("" + buildingg);

		Label generalPurposeelbl = new Label("General Purpose");
		Label generalPurposeelbl2 = new Label("" + generalPurposee);

		Label sportsslbl = new Label("Sports");
		Label sportsslbl2 = new Label("" + sportss);

		Label leviilbl = new Label("Levy");
		Label leviilbl2 = new Label("" + levii);

		Label otherrlbl = new Label("Other");
		Label otherrlbl2 = new Label("" + otherr);

		Label totalllbl = new Label("TOTAL");
		Label totalllbl2 = new Label("" + totall);

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.setIcon(FontAwesome.UPLOAD);
		submit.addClickListener((e) -> {
			InsertDataIntoFeesStructureTable dataIntoFeesStructureTable = new InsertDataIntoFeesStructureTable(stm);
			dataIntoFeesStructureTable.insertData(tutionn, bspzz, buildingg, generalPurposee, sportss, levii, otherr,
					totall, dateSet, schoolID);

			new Notification("<h1>Fees Structure successfully saved<h1/>", "", Notification.Type.HUMANIZED_MESSAGE,
					true).show(Page.getCurrent());

			tution.clear();
			bspz.clear();
			building.clear();
			generalPurpose.clear();
			sports.clear();
			levi.clear();
			other.clear();
			total.clear();
		});

		Button cancel = new Button("Cancel");
		cancel.addStyleName(ValoTheme.BUTTON_DANGER);
		cancel.setIcon(FontAwesome.CLOSE);

		GridLayout gridLayout = new GridLayout(2, 9, tutionnlbl, tutionnlbl2, bspzzlbl, bspzzlbl2, buildingglbl,
				buildingglbl2, generalPurposeelbl, generalPurposeelbl2, sportsslbl, sportsslbl2, leviilbl, leviilbl2,
				otherrlbl, otherrlbl2, totalllbl, totalllbl2, submit, cancel);
		gridLayout.setSpacing(true);

		VerticalLayout layout = new VerticalLayout(image, gridLayout);
		layout.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);

		Window w = new Window("CONFIRM", layout);
		w.center();
		w.setWidth("40%");
		w.setHeight("80%");
		w.addStyleName("confirmation_window");
		cancel.addClickListener((e) -> {
			UI.getCurrent().removeWindow(w);
			w.close();
		});
		return w;
	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Set Fees Structure")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Set Fees Structure", FontAwesome.USER_SECRET);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
