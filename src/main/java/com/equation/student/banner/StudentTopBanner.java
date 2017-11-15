package com.equation.student.banner;

import java.io.File;

import com.equation.utils.application.basepath.ApplicationBasePath;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial" })
public class StudentTopBanner extends HorizontalLayout {
	String studentname, studentID;

	public StudentTopBanner(String studentname, String studentID) {
		this.studentname = studentname;
		this.studentID = studentID;
		this.setStyleName("top_lay");
		Label l = new Label(
				"\u2211QUATION      " + VaadinIcons.CLOUD.getHtml() + "  Hi " + this.studentname.toUpperCase(),
				ContentMode.HTML);
		l.addStyleName("main_label");

		Button logout = new Button("Sign Out");

		logout.addStyleName(ValoTheme.BUTTON_QUIET);
		logout.addStyleName("system_logout");
		logout.addClickListener((e) -> {
			getSession().close();
		});
		FileResource resource = new FileResource(
				new File(ApplicationBasePath.basePath() + "/WEB-INF/images/" + studentID + ".png"));
		Image image = new Image();
		image.setSource(resource);
		image.setWidth("60%");
		image.setHeight("50%");
		image.addStyleName("student_welcome_image_bmp");

		this.addComponent(image);
		this.addComponent(l);
		this.setComponentAlignment(l, Alignment.MIDDLE_LEFT);
		this.addComponent(logout);

		this.setComponentAlignment(logout, Alignment.MIDDLE_RIGHT);
		this.setComponentAlignment(image, Alignment.TOP_RIGHT);
	}
}