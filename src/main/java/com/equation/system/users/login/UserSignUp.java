package com.equation.system.users.login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class UserSignUp extends CustomComponent implements View {
	Statement stm, stmt, stmt1;
	String id;
	ResultSet rs, rs1;
	static final String address = "Sign_In";

	public UserSignUp(Statement stm, Statement stmt, Statement stmt1, String id, ResultSet rs, ResultSet rs1) {

		this.stm = stm;
		this.id = id;
		this.rs = rs;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.stmt1 = stmt1;
		TextField username = new TextField("Type your Username");
		username.setPlaceholder("New Username");
		PasswordField password = new PasswordField("New Password");
		password.setDescription("Type a password of your choice \nPassword should be strong");
		PasswordField confirmpword = new PasswordField("Confirm New Password");
		confirmpword.setDescription("Confirm your new password again");
		Button submit = new Button("Create Account");
		submit.addClickListener((e) -> {

			String newUsername = username.getValue();
			String newPassword = password.getValue();
			String confirmPassword = confirmpword.getValue();
			if (!(newUsername.equals(""))) {
				if (!(newPassword.equals(""))) {
					if (!(confirmPassword.equals(""))) {
						if ((newPassword.equals(confirmPassword))) {
							if (!(newUsername.equals(id))) {
								int len = newPassword.length();
								if (!(len < 8)) {
									String name = "name";

									try {

										String query = "UPDATE users SET username='" + newUsername + "',password ='"
												+ newPassword + "' WHERE identityID ='" + this.id + "'";
										this.stm.executeUpdate(query);
										System.out.println(newPassword.toString());
										new Notification(
												"<h1>CONGRATULATIONS!!! <br/>" + name.toUpperCase()
														+ ", your account has been succesfully created<br/>Now yu can Sign In using your new account<h1/>",
												"", Notification.Type.HUMANIZED_MESSAGE, true).show(Page.getCurrent());
										password.clear();
										confirmpword.clear();
										username.clear();
										AuthenticationWindow myform = new AuthenticationWindow(this.stm, this.stmt,
												this.stmt1, this.rs, this.rs1);
										getUI().getNavigator().addView(address, myform);
										getUI().getNavigator().navigateTo(address);

									} catch (SQLException ek) {
										ek.printStackTrace();
										new Notification(
												"<h1 style = 'color:white;'>Fatal error occured<br/>Account could not be created<h1/>",
												"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
										password.clear();
										confirmpword.clear();
										username.clear();
										username.focus();

									}
								} else {
									new Notification(
											"<h1 style='color:white;'>Passwords should be not less than 8 characters<h1/>",
											"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
									password.clear();
									confirmpword.clear();
									password.focus();
								}

							} else {
								new Notification("<h1 style='color:white;'>Please enter a new user name<h1/>", "",
										Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
								password.clear();
								confirmpword.clear();
								username.clear();
								username.focus();
							}

						} else {
							new Notification(
									"<h1 style='color:white;'>The passwords did not match!<br/>Please enter matching passwords<h1/>",
									"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
							password.clear();
							confirmpword.clear();
							password.focus();
						}

					} else {
						new Notification(
								"<h1 style='color:white;'>You did not confirm your password!<br/>Confirm your new password to continue<h1/>",
								"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
						password.clear();
						confirmpword.clear();
						password.focus();
					}
				} else {
					new Notification(
							"<h1 style = 'color:white;'>The new password field is empty<br/>Enter your new password to continue<h1/>",
							"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
					password.clear();
					confirmpword.clear();
					password.focus();
				}
			} else {
				new Notification(
						"<h1 style='color:white;'>Username TextBox is Empty<br/>Enter a new Username to continue<h1/>",
						"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
				password.clear();
				confirmpword.clear();
				password.focus();
			}
		});
		FormLayout signUpForm = new FormLayout(username, password, confirmpword, submit);
		Panel panel = new Panel("Sign In");
		panel.setContent(signUpForm);
		panel.setIcon(VaadinIcons.USERS);
		panel.addStyleName("login");
		panel.setWidth("400px");
		// panel.setHeight("00px");

		HorizontalLayout layout = new HorizontalLayout(panel);
		layout.addStyleName("login_layout");
		VerticalLayout layout2 = new VerticalLayout(new SignInBanner(), layout);
		layout2.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
		this.setCompositionRoot(layout2);
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}
}
