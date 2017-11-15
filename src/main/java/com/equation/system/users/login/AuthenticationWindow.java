
package com.equation.system.users.login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.school.current.emis.collect.GetCurrentSchoolEMISNumber;
import com.equation.school.details.retrieve.RetrieveSchoolDetails;
import com.equation.school.district.RetrieveDistrictName;
import com.equation.user.session.attributes.UserSessionAttributes;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class AuthenticationWindow extends CustomComponent implements View {
	ResultSet rs, rs1;
	Statement stm, stmt, stmt1;
	String id;
	private TextField username;
	static final String address = "signup";

	public AuthenticationWindow(Statement stm, Statement stmt, Statement stmt1, ResultSet rs, ResultSet rs1) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.stmt1 = stmt1;
		username = new TextField("<h2>Username<h2/>");
		username.setCaptionAsHtml(true);
		username.addStyleName("username");
		username.setPlaceholder("Username");
		PasswordField password = new PasswordField("<h2>Password<h2/>");
		password.setCaptionAsHtml(true);
		password.addStyleName("password");
		password.setDescription("Password should be more than 7 characters");
		Button submit = new Button("Sign In");
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submit.setIcon(VaadinIcons.PLAY_CIRCLE);
		submit.addClickListener((e) -> {
			String usernametxt = username.getValue();
			String passwordtxt = password.getValue();
			String query = "SELECT identityID,userLevel,username,password FROM users WHERE username ='" + usernametxt
					+ "' AND password = '" + passwordtxt + "'";

			try {
				this.rs = this.stm.executeQuery(query);
				this.rs.last();
				int rows = this.rs.getRow();
				// System.out.println(rows);
				if (rows == 1) {
					id = this.rs.getString(1);
					String ulevel = this.rs.getString(2);
					// System.out.println(rows + " " + id + " " + ulevel);
					if (usernametxt.equals(id)) {
						UserSignUp myform = new UserSignUp(this.stm, this.stmt, this.stmt1, id, this.rs, this.rs1);
						getUI().getNavigator().addView(address, myform);
						getUI().getNavigator().navigateTo(address);
					} else {
						String emis = new GetCurrentSchoolEMISNumber(this.rs, this.stm).currentSchoolEMISNumber();
						RetrieveSchoolDetails retrieveSchoolDetails = new RetrieveSchoolDetails(this.rs, this.rs1,
								this.stm, this.stmt, emis);
						String schooldepartmentcode = retrieveSchoolDetails.getSchooldepartmentcode();
						String stationCode = retrieveSchoolDetails.getStationCode();
						String schoolname = retrieveSchoolDetails.getSchoolName();
						String nameOfDistrict = new RetrieveDistrictName(this.rs, this.rs1, this.stm, this.stmt)
								.getDistrictName(emis);

						UI.getCurrent().getSession().setAttribute(UserSessionAttributes.SCHOOL_EMIS_NUMBER, emis);
						UI.getCurrent().getSession().setAttribute(UserSessionAttributes.USER_ID, id);
						UI.getCurrent().getSession().setAttribute(UserSessionAttributes.USER_LEVEL, ulevel);

						UI.getCurrent().getSession().setAttribute(UserSessionAttributes.DEPT_CODE,
								schooldepartmentcode);

						UI.getCurrent().getSession().setAttribute(UserSessionAttributes.STATION_CODE, stationCode);

						new UserLevelSelector(this.stm, this.stmt, this.stmt1, this.rs, this.rs1, id, emis, ulevel,
								schoolname, nameOfDistrict).selectUser(ulevel);
					}

				} else {
					new Notification(
							"<h1 style='color:white;'>User account does not exist!!!<br/>Make user you have entered correct user details.<br/>Please make sure that you are part of this organisation!<h1/>",
							"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
				new Notification("<h1>Fatal error occured<br/>User details could not be verified<h1/>", "",
						Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			}
		});
		FormLayout logInForm = new FormLayout(username, password, submit);
		Panel panel = new Panel("<center style='text-decoration:underline;color:blue;'>Sign In<center/>");
		panel.setCaptionAsHtml(true);
		panel.setContent(logInForm);
		panel.setIcon(VaadinIcons.LOCK);
		panel.addStyleName("login");
		panel.setWidth("400px");
		// panel.setHeight("00px");

		HorizontalLayout layout = new HorizontalLayout(panel);
		layout.addStyleName("login_layout");
		VerticalLayout layout2 = new VerticalLayout(new SignInBanner(), layout);
		layout2.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
		layout2.setMargin(false);
		this.setCompositionRoot(layout2);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		username.focus();
	}
}