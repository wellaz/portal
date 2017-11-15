package com.equation.portal;

import java.io.File;

import javax.servlet.annotation.WebServlet;

import com.equation.database.connection.pool.MainDatabaseConnectionPool;
import com.equation.system.users.login.AuthenticationWindow;
import com.equation.utils.application.basepath.ApplicationBasePath;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of an HTML page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@SuppressWarnings("serial")
@Theme("mytheme")
public class MyUI extends UI {
	MainDatabaseConnectionPool connectionPool;
	public Navigator navigator;
	protected static final String SIGN_IN = "sign_in";

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		connectionPool = new MainDatabaseConnectionPool();
		connectionPool.connectionDb();
		navigator = new Navigator(this, this);
		AuthenticationWindow authenticationWindow = new AuthenticationWindow(connectionPool.stm, connectionPool.stmt,
				connectionPool.stmt1, connectionPool.rs, connectionPool.rs1);

		navigator.addView(SIGN_IN, authenticationWindow);

		navigator.navigateTo(SIGN_IN);
		Page.getCurrent().setTitle("Eqution");
		UI.getCurrent()
				.setIcon(new FileResource(new File(ApplicationBasePath.basePath() + "/WEB-INF/images/sigma_.png")));
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
