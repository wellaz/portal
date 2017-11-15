package com.equation.student.banner;

import java.io.File;

import com.equation.utils.application.basepath.ApplicationBasePath;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial" })
public class StudentInClassWritingLogo extends HorizontalLayout {
	String studentname, subject, totalQuestions, totalMark, peroid, teacher, topic, classname, due;

	public StudentInClassWritingLogo(String studentname, String subject, String totalQuestions, String totalMark,
			String peroid, String teacher, String topic, String classname, String due) {
		this.studentname = studentname;
		this.subject = subject;
		this.totalQuestions = totalQuestions;
		this.totalMark = totalMark;
		this.peroid = peroid;
		this.teacher = teacher;
		this.topic = topic;
		this.classname = classname;
		this.due = due;

		this.setStyleName("top_lay");
		Label l = new Label(
				this.studentname + ", attending " + subject + " In Class Test on " + topic + ". \u2211QUATION",
				ContentMode.HTML);
		l.addStyleName("main_label");
		Button ins = new Button();
		ins.setIcon(VaadinIcons.QUESTION_CIRCLE);
		ins.addStyleName("inclass_instructions_button");
		ins.addClickListener((e) -> {
			UI.getCurrent().addWindow(createInstructionsWindow());
		});

		HorizontalLayout horizontalLayout = new HorizontalLayout(l, ins);
		horizontalLayout.setSpacing(true);

		VerticalLayout layout = new VerticalLayout(horizontalLayout);
		layout.setSpacing(true);

		this.addComponent(layout);
		this.setSizeFull();
		this.setSpacing(true);
	}

	Window createInstructionsWindow() {
		Window window = new Window();
		window.center();
		window.setModal(false);
		window.setWidth("50%");
		window.setHeight("90%");
		Image schoolLogo = new Image("",
				new FileResource(new File(ApplicationBasePath.basePath() + "/WEB-INF/images/systemlogo.png")));
		schoolLogo.setWidth("30%");
		schoolLogo.setHeight("20%");
		Label teacherlbl = new Label("Teacher : ");
		Label teacherlbl1 = new Label(teacher);
		Label subjectlbl = new Label("SUBJECT :");
		Label subjectlbl1 = new Label(subject);
		Label classnamelbl = new Label("Class :");
		Label classnamelbl1 = new Label(classname);
		Label topiclbl = new Label("Topic :");
		Label topiclbl1 = new Label(topic);
		Label totalQuestionslbl = new Label("Number of Questions :");
		Label totalQuestionslbl1 = new Label(totalQuestions);
		Label totalMarklbl = new Label("Total Mark :");
		Label totalMarklbl1 = new Label(totalMark);
		Label peroidlbl = new Label("Period :");
		Label peroidlbl1 = new Label(peroid);
		Label duelbl = new Label("Due Date :");
		Label duelbl1 = new Label(due);

		GridLayout gridLayout = new GridLayout(2, 8, teacherlbl, teacherlbl1, subjectlbl, subjectlbl1, classnamelbl,
				classnamelbl1, topiclbl, topiclbl1, totalQuestionslbl, totalQuestionslbl1, totalMarklbl, totalMarklbl1,
				peroidlbl, peroidlbl1, duelbl, duelbl1);
		gridLayout.setSpacing(true);
		gridLayout.addStyleName(ValoTheme.LAYOUT_WELL);

		Button close = new Button("Close");
		close.setIcon(VaadinIcons.CLOSE);
		close.addStyleName(ValoTheme.BUTTON_DANGER);
		close.addClickListener((e) -> {
			UI.getCurrent().removeWindow(window);
			window.close();
		});
		HorizontalLayout horizontalLayout = new HorizontalLayout(close);
		horizontalLayout.setSpacing(true);

		VerticalLayout layout = new VerticalLayout(horizontalLayout, schoolLogo, gridLayout);
		layout.setSpacing(true);
		layout.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);
		Panel panel = new Panel("Examination Instructions");
		panel.setIcon(VaadinIcons.QUESTION_CIRCLE);
		panel.setContent(layout);
		window.setContent(panel);

		return window;
	}
}