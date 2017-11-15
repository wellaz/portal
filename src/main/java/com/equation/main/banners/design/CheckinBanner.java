package com.equation.main.banners.design;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.shared.ui.ContentMode;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial" })
public class CheckinBanner extends HorizontalLayout {

	public CheckinBanner() {
		this.setStyleName("top_lay");

		Label l = new Label("  \u2211QUATION   -  Administrator's C I C O Module " + VaadinIcons.CLOUD.getHtml(),
				ContentMode.HTML);
		l.addStyleName("main_label");
		this.addComponent(l);

		Button logout = new Button("Sign Out");

		logout.addStyleName(ValoTheme.BUTTON_QUIET);
		logout.addStyleName("system_logout");
		logout.addClickListener((e) -> {

			getSession().close();

		});
		this.setComponentAlignment(l, Alignment.MIDDLE_LEFT);
		this.addComponent(logout);
		this.setComponentAlignment(logout, Alignment.MIDDLE_RIGHT);
	}

}
