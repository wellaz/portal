package com.equation.system.environment.determine;

import com.equation.user.session.attributes.UserSessionAttributes;
import com.vaadin.ui.UI;

/**
 *
 * @author Wellington
 */
public class SchoolEmisNumber {

	public SchoolEmisNumber() {
		// TODO Auto-generated constructor stub
	}

	public String getEmisThatExist() {
		return (String) UI.getCurrent().getSession().getAttribute(UserSessionAttributes.SCHOOL_EMIS_NUMBER);
	}
}