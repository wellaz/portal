package com.equation.checkin.ancillary;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.checkin.teachers.CheckinState;
import com.equation.checkin.teachers.NormalWorkingHours;
import com.equation.utils.application.basepath.ApplicationBasePath;
import com.equation.utils.date.DateUtility;
import com.equation.utils.date.TimeDifference;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
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
public class DaemonCode {
	Statement stm, stmt;
	ResultSet rs, rs1;
	DateUtility dateUtility;
	TimeDifference timeDifference;

	public DaemonCode(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		dateUtility = new DateUtility();
		timeDifference = new TimeDifference();
	}

	/**
	 * 
	 * @param ecNumber
	 *            The employee number
	 * 
	 */
	public void process(String ecNumber) {
		String today = dateUtility.getDate();
		String time_in = dateUtility.getTime();
		String query = "SELECT time_in,time_out FROM ancillarycheckin WHERE workerid = '" + ecNumber + "' AND date = '"
				+ today + "'";
		try {
			String findAnomaly1 = "SELECT time_in,date FROM ancillarycheckin WHERE workerid = '" + ecNumber
					+ "' AND period = '" + CheckinState.PENDING + "' AND date <> '" + today + "' ";
			rs = stm.executeQuery(findAnomaly1);
			if (!rs.next()) {
				// nornal state

				rs1 = stmt.executeQuery(query);
				if (rs1.next()) {
					// the person has already checked in
					String check_in = rs1.getString(1);
					String check_out = rs1.getString(2);
					if (check_out.equals(CheckinState.PENDING)) {
						String query1 = "SELECT trip,time_out,time_in FROM ancillaryerrand WHERE workerid = '"
								+ ecNumber + "' AND date = '" + today + "'";
						rs = stm.executeQuery(query1);
						if (rs.next()) {
							// the person had a trip
							String trip = rs.getString(1);
							String err_out = rs.getString(2);
							String err_in = rs.getString(3);
							if (err_in.equals(CheckinState.PENDING)) {
								// record found..had gone out..not yet
								// back..do
								// errand in now
								String errand_period = timeDifference.getPeriod(err_out, time_in);
								String errand_hrs = errand_period.split(",")[0];
								String errand_mins = errand_period.split(",")[1];
								String you_took = errand_hrs.equals("0") ? "You took only " + errand_mins + " minutes."
										: "You took " + errand_hrs + " hours and " + errand_mins + " minutes.";
								// update errand_in
								String errandUpdate = "UPDATE ancillaryerrand SET time_in = '" + time_in
										+ "',period = '" + errand_period + "' WHERE workerid = '" + ecNumber
										+ "' AND trip = '" + trip + "'";
								stm.executeUpdate(errandUpdate);
								// new Notification("<WELCOME BACK>",
								// "Welcome back from " + trip + "<br>" +
								// you_took +
								// "<br> We hope you're safe!",
								// Notification.TYPE_TRAY_NOTIFICATION,
								// true).show(Page.getCurrent());
								Notification notif = new Notification("<h1>WELCOME BACK<h1/>",
										"Welcome back from " + trip + "<br/>" + you_took + "<br> We hope you're safe!",
										Notification.Type.WARNING_MESSAGE, true);
								// Customize it
								notif.setDelayMsec(100);
								notif.setPosition(Position.MIDDLE_CENTER);
								notif.setStyleName("auto_checkin_note");
								notif.setIcon(VaadinIcons.THUMBS_UP_O);
								notif.setHtmlContentAllowed(true);
								// Show it in the page
								notif.show(Page.getCurrent());
							} else {
								// had gone out for a trip and came back
								// we need to update checkindetails
								checkOut(today, check_in, time_in, ecNumber);
							}

						} else {
							// Checked in, not checked out, but no trip
							// Two options....check out for today OR take a
							// trip
							// Or we can find working hours...if the day is
							// over..then check out...else prompt the user
							String findPeriod = timeDifference.getPeriod(check_in, time_in);
							int period = Integer.parseInt(findPeriod.split(",")[0]);
							if (period < NormalWorkingHours.getWorkingHours()) {
								Window window = new Window();
								window.setModal(true);
								window.setWidth("40%");
								window.setHeight("30%");
								window.center();
								window.setCaption("OPTIONS");
								window.setIcon(VaadinIcons.QUESTION_CIRCLE);
								Image image = new Image(ecNumber, new FileResource(new File(
										ApplicationBasePath.basePath() + "/WEB-INF/images/" + ecNumber + ".png")));
								image.setWidth("80px");
								image.setHeight("80px");

								Button submit = new Button("SUBMIT");
								submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
								submit.setDescription("Submit");
								submit.setVisible(false);

								Button err_out_btn = new Button("ERRAND OUT");

								TextField goingto = new TextField("Errand:");
								goingto.setVisible(false);

								Button check_out_btn = new Button("CHECKOUT");
								check_out_btn.addStyleName(ValoTheme.BUTTON_DANGER);
								check_out_btn.setDescription("Check out");
								check_out_btn.addClickListener((e) -> {
									UI.getCurrent().removeWindow(window);
									window.close();
									checkOut(today, check_in, time_in, ecNumber);
								});

								err_out_btn.addStyleName(ValoTheme.BUTTON_PRIMARY);
								err_out_btn.setDescription("Take a tour");
								err_out_btn.addClickListener((e) -> {
									submit.setVisible(true);
									goingto.setVisible(true);
									goingto.focus();
									err_out_btn.setVisible(false);
									check_out_btn.setVisible(false);
									submit.addClickListener((ee) -> {
										String value = goingto.getValue();
										if (!value.equals("")) {
											// take a trip
											InsertDataIntoErrand dataIntoErrand = new InsertDataIntoErrand(stm);
											dataIntoErrand.insertData(ecNumber, value, time_in, CheckinState.PENDING,
													CheckinState.PENDING, today);
											UI.getCurrent().removeWindow(window);
											window.close();
											Notification notif = new Notification("<h1>Thanks !!!<h1/>",
													"<br/>Current Time" + time_in
															+ "<br/> We will meet when you're back from "
															+ value.toUpperCase(),
													Notification.Type.WARNING_MESSAGE, true);
											// Customize it
											notif.setDelayMsec(100);
											notif.setPosition(Position.MIDDLE_CENTER);
											notif.setStyleName("auto_checkin_note");
											notif.setIcon(VaadinIcons.THUMBS_UP_O);
											notif.setHtmlContentAllowed(true);
											// Show it in the page
											notif.show(Page.getCurrent());
										} else {
											goingto.focus();
										}
									});
								});

								HorizontalLayout horizontalLayout1 = new HorizontalLayout(image, goingto);
								horizontalLayout1.setSpacing(true);

								HorizontalLayout horizontalLayout2 = new HorizontalLayout(err_out_btn, check_out_btn,
										submit);
								horizontalLayout2.setSpacing(true);

								VerticalLayout layout = new VerticalLayout(horizontalLayout1, horizontalLayout2);
								layout.setSpacing(true);
								layout.setComponentAlignment(horizontalLayout1, Alignment.TOP_CENTER);
								layout.setComponentAlignment(horizontalLayout2, Alignment.BOTTOM_CENTER);
								window.setContent(layout);
								UI.getCurrent().addWindow(window);
							} else {
								// the day is over....check out immediately
								checkOut(today, check_in, time_in, ecNumber);
							}
						}

					} else {
						// note
						Notification notif = new Notification("<h1>Check Out Notification<h1/>",
								"End Of Day Check Out Already Performed", Notification.Type.WARNING_MESSAGE, true);
						// Customize it
						notif.setDelayMsec(100);
						notif.setPosition(Position.MIDDLE_CENTER);
						notif.setStyleName("auto_checkin_note_err");
						notif.setIcon(VaadinIcons.THUMBS_UP_O);
						notif.setHtmlContentAllowed(true);
						// Show it in the page
						notif.show(Page.getCurrent());
						// new Notification("", "",
						// Notification.TYPE_ERROR_MESSAGE,
						// true).show(Page.getCurrent());
					}

				} else {
					// the person has not yet checked in for todat, we need
					// to
					// post
					// check in deta
					String time_out = CheckinState.PENDING, period = CheckinState.PENDING;
					InsertDataIntoCheckin checkin = new InsertDataIntoCheckin(stm);
					checkin.insertData(ecNumber, time_in, time_out, period, today);
					// notify
					Notification notif = new Notification("<h1>WELCOME !!!<h1/>",
							"<br/>Current Time" + time_in + "<br/> Enjoy the rest of the working day!!!",
							Notification.Type.WARNING_MESSAGE, true);
					// Customize it
					notif.setDelayMsec(100);
					notif.setPosition(Position.MIDDLE_CENTER);
					notif.setStyleName("auto_checkin_note");
					notif.setIcon(VaadinIcons.THUMBS_UP_O);
					notif.setHtmlContentAllowed(true);
					// Show it in the page
					notif.show(Page.getCurrent());
				}

			} else {
				// did not check out some other day....find another anomaly
				// ultimately we need to check out for that day
				String last_checkin = rs.getString(1);
				String last_date = rs.getString(2);
				String anomaly2 = "SELECT trip FROM ancillaryerrand WHERE workerid = '" + ecNumber + "' AND period = '"
						+ CheckinState.PENDING + "' AND date = '" + last_date + "'";
				rs1 = stmt.executeQuery(anomaly2);
				if (!rs1.next()) {
					// no problem with errand....only check out required for
					// that day
					new Notification(
							"<h1>End Of Day Check Out Not Found for day " + last_date
									+ "<br/>Check In was done on this day at " + last_checkin
									+ "<br/>Consult the Administrator for assistance",
							"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());

				} else {
					// bad....two steps required....errand in ..then check out
					String trip = rs1.getString(1);
					new Notification(
							"<h1>End Of Day Check Out Not Found for day " + last_date + "<br/>Worker ID " + ecNumber
									+ " is not yet back from " + trip
									+ "!<br/>Consult the Administrator for assistance",
							"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
				}
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

	}

	public void checkOut(String today, String check_in, String time_in, String ecNumber) {
		String todays_hours = timeDifference.getPeriod(check_in, time_in);
		String updateCheckIn = "UPDATE ancillarycheckin SET time_out = '" + time_in + "',period = '" + todays_hours
				+ "' WHERE workerid = '" + ecNumber + "' AND date = '" + today + "'";

		try {
			stm.executeUpdate(updateCheckIn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		@SuppressWarnings("deprecation")
		Notification notif = new Notification("Thanks !!!",
				"<br/>Current Time" + time_in + "<br/> We will meet tomorrow", Notification.TYPE_WARNING_MESSAGE, true);
		// Customize it
		notif.setDelayMsec(100);
		notif.setPosition(Position.MIDDLE_CENTER);
		notif.setStyleName("auto_checkin_note");
		notif.setIcon(VaadinIcons.THUMBS_UP_O);
		notif.setHtmlContentAllowed(true);
		// Show it in the page
		notif.show(Page.getCurrent());
	}
}