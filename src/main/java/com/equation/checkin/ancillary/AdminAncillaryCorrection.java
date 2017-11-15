package com.equation.checkin.ancillary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.checkin.teachers.CheckinState;
import com.equation.utils.date.DateUtility;
import com.equation.utils.date.TimeDifference;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
public class AdminAncillaryCorrection {
	Statement stm, stmt;
	ResultSet rs, rs1;
	DateUtility dateUtility;
	TimeDifference timeDifference;

	public AdminAncillaryCorrection(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		dateUtility = new DateUtility();
		timeDifference = new TimeDifference();
	}

	@SuppressWarnings("deprecation")
	public void correct(String ecNumber, Window myWindow, TextField myTextArea) {
		String today = dateUtility.getDate();
		// String time_in = dateUtility.getTime();

		try {
			// ecNumber is found
			String findAnomaly1 = "SELECT time_in,date FROM ancillarycheckin WHERE workerid = '" + ecNumber
					+ "' AND period = '" + CheckinState.PENDING + "' AND date <> '" + today + "' ";
			rs = stm.executeQuery(findAnomaly1);
			if (!rs.next()) {
				// everything is correct
				new Notification("<h2>Correct Record<h2/>", "All check in records are correct!!",
						Notification.TYPE_HUMANIZED_MESSAGE, true).show(Page.getCurrent());
				UI.getCurrent().removeWindow(myWindow);
				myWindow.close();

			} else {
				// did not check out some other day....find another anomaly
				// ultimately we need to check out for that day
				String last_checkin = rs.getString(1);
				String last_date = rs.getString(2);
				String anomaly2 = "SELECT trip,time_out FROM ancillaryerrand WHERE workerid = '" + ecNumber
						+ "' AND period = '" + CheckinState.PENDING + "' AND date = '" + last_date + "'";
				rs1 = stmt.executeQuery(anomaly2);
				if (!rs1.next()) {
					// no problem with errand....only check out required for
					// that day
					Window window = new Window();
					window.setModal(true);
					window.setWidth("40%");
					window.setHeight("30%");
					window.center();
					window.setCaption("Check Out");

					TextField out = new TextField("Check Out Time");
					Button submit = new Button("Submit");
					submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
					submit.addClickListener((e) -> {
						String value = out.getValue();
						if (!value.equals("")) {
							String period = timeDifference.getPeriod(last_checkin, value);
							String updateCheckIn = "UPDATE ancillarycheckin SET time_out = '" + value + "', period = '"
									+ period + "' WHERE workerid = '" + ecNumber + "' AND date = '" + last_date + "'";

							try {
								stm.executeUpdate(updateCheckIn);
								UI.getCurrent().removeWindow(window);
								window.close();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							out.focus();
						}

					});
					FormLayout formLayout = new FormLayout(out, submit);
					formLayout.setSpacing(true);
					HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
					horizontalLayout.setSpacing(true);
					VerticalLayout layout = new VerticalLayout(horizontalLayout);
					layout.setSpacing(true);
					layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
					window.setContent(layout);
					UI.getCurrent().addWindow(window);
					UI.getCurrent().removeWindow(myWindow);
					myWindow.close();

				} else {
					// bad....two steps required....errand in ..then check
					// out
					String trip = rs1.getString(1);
					String out_at = rs1.getString(2);

					Window window = new Window();
					window.setModal(true);
					window.setWidth("30%");
					window.setHeight("50%");
					window.center();
					window.setCaption("Check Out");

					TextField out = new TextField("Check Out Time");
					TextField err_back = new TextField("Back From Errand Time");
					Button submit = new Button("Submit");
					submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
					submit.addClickListener((e) -> {
						String value = out.getValue();
						String value1 = err_back.getValue();
						if (!(value.equals("") || value1.equals(""))) {
							String period = timeDifference.getPeriod(last_checkin, value);
							String errperiod = timeDifference.getPeriod(out_at, value1);
							String updateCheckIn = "UPDATE ancillarycheckin SET time_out = '" + value + "', period = '"
									+ period + "' WHERE workerid = '" + ecNumber + "' AND date = '" + last_date + "'";
							String updateErand = "UPDATE ancillaryerrand SET time_in = '" + value1 + "', period = '"
									+ errperiod + "' WHERE workerid = '" + ecNumber + "' AND period = '"
									+ CheckinState.PENDING + "' AND trip = '" + trip + "'";
							try {
								stm.executeUpdate(updateCheckIn);
								stm.executeUpdate(updateErand);
								UI.getCurrent().removeWindow(window);
								window.close();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							out.focus();
						}
					});
					UI.getCurrent().removeWindow(myWindow);
					myWindow.close();

					FormLayout formLayout = new FormLayout(out, err_back, submit);
					formLayout.setSpacing(true);
					HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
					horizontalLayout.setSpacing(true);
					VerticalLayout layout = new VerticalLayout(horizontalLayout);
					layout.setSpacing(true);
					layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
					window.setContent(layout);
					UI.getCurrent().addWindow(window);
				}
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}