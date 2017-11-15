package com.equation.system.errors;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

/**
 *
 * @author Wellington
 */
public class DatabaseConnectionErrorHandler {
	@SuppressWarnings("deprecation")
	public static void dbseError() {
		new Notification("Error",
				"An error occured while trying to connect to the server.<br/><br/>Please contact the Admin for assistance",
				Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());

	}

}
