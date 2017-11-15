package com.equation.equate.views.main;

import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.equate.capture.CaptureBatchDetails;
import com.equation.equate.capture.CaptureDetails;
import com.equation.equate.generate.GenerateFinalMarkSchedule;
import com.equation.equate.markschedule.overal.perclass.OveralMarkSchedule;
import com.equation.equate.markschedule.overal.pergrade.OveralGradeMarkSchedule;
import com.equation.equate.results.analysis.perclass.ClassDataAnalysis;
import com.equation.equate.results.analysis.pergrade.GradeDataAnalysis;
import com.equation.equate.settings.marks.allocation.SubjectsMarkAllocation;
import com.equation.equate.settings.passmark.PassmarkView;
import com.equation.equate.utils.application.basepath.ApplicationBasePath;
import com.equation.equate.utils.banners.CreateTopBanner;
import com.equation.equate.utils.pagetitles.PageTitles;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class MyPrimaryEOTMainView extends CustomComponent implements View {

	ResultSet rs, rs1;
	Statement stm, stmt, stmt1;
	TabSheet tabs;
	private Button upload;

	private Button download;
	private Button downloadall;
	private Button grademarkschedule;
	private Button uploadBatch;
	private Button classanalyis;
	private Button gradeanalyis;
	private Button settings;
	private Button passmark;
	boolean canIhide = false;

	public MyPrimaryEOTMainView(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, Statement stmt1) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.stmt1 = stmt1;
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSpacing(true);
		// HorizontalLayout banner = new
		// com.equation.banners.design.TopBanner();

		Button hide = new Button();
		hide.setIcon(VaadinIcons.ARROW_CIRCLE_UP_O);
		hide.setDescription("Hide Toolbar");
		hide.addStyleName(ValoTheme.BUTTON_QUIET);

		HorizontalLayout toolbar = new HorizontalLayout(createToolBar(), hide);
		toolbar.setSpacing(true);

		hide.addClickListener((e) -> {
			if (!canIhide) {
				toolbar.removeAllComponents();
				toolbar.addComponent(hide);
				hide.setIcon(VaadinIcons.ARROW_CIRCLE_DOWN_O);
				hide.setDescription("Show Toolbar");
				canIhide = true;
			} else {
				toolbar.addComponents(createToolBar(), hide);
				hide.setIcon(VaadinIcons.ARROW_CIRCLE_UP_O);
				hide.setDescription("Hide Toolbar");
				canIhide = false;
			}
		});

		toolbar.addStyleName(ValoTheme.LAYOUT_WELL);
		toolbar.addStyleName("transaparent_layout");

		tabs = new TabSheet();
		tabs.addTab(createFirstPage(), "WELCOME", VaadinIcons.THUMBS_UP);
		tabs.setResponsive(true);
		tabs.addStyleName(ValoTheme.TABSHEET_ONLY_SELECTED_TAB_IS_CLOSABLE);
		VerticalLayout mainContent = new VerticalLayout(tabs);
		mainContent.addStyleName(ValoTheme.LAYOUT_CARD);

		verticalLayout.addComponent(createTopBanner());
		verticalLayout.addComponent(toolbar);
		verticalLayout.addComponent(mainContent);

		verticalLayout.setComponentAlignment(mainContent, Alignment.MIDDLE_CENTER);
		this.setCompositionRoot(verticalLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle(PageTitles.maintitle + "-Teacher's Module");
	}

	public HorizontalLayout createTopBanner() {
		return new CreateTopBanner();
	}

	public VerticalLayout createFirstPage() {
		VerticalLayout layout = new VerticalLayout();
		FileResource resource = new FileResource(
				new File(ApplicationBasePath.basePath() + "/WEB-INF/images/systemlogo.png"));
		// Show the image in the application
		FileResource resource2 = new FileResource(
				new File(ApplicationBasePath.basePath() + "/WEB-INF/images/sigma_burned.png"));
		// System.out.println(ApplicationBasePath.basePath());
		Image image = new Image("", resource);
		// image.setWidth("30%");
		// image.setHeight("30%");
		image.addStyleName("zim_logo");
		// layout.addComponent(image);

		Label eq4 = new Label("\u2211QUATION");
		eq4.addStyleName("eq4");

		Label descripton = new Label("Mark Schedule Processor! ! !");
		descripton.addStyleName("eq4_descripton");

		Image image2 = new Image("", resource2);
		image2.setWidth("250px");
		image2.setHeight("250px");
		image.setWidth("250px");
		image.setHeight("250px");

		// layout.addComponent(eq4);
		// layout.addComponent(descripton);

		HorizontalLayout horizontalLayout = new HorizontalLayout(image2, eq4, image);
		horizontalLayout.setSpacing(true);

		layout.addComponent(horizontalLayout);
		layout.addComponent(descripton);

		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);

		return layout;
	}

	public TabSheet createToolBar() {
		TabSheet pane = new TabSheet();

		upload = new Button("Subject Raw Mark Schedules");
		upload.setDescription("Upload Subject Raw Mark Schedules");
		upload.setIcon(VaadinIcons.UPLOAD);
		upload.addStyleName(ValoTheme.BUTTON_QUIET);
		upload.addClickListener((e) -> {
			CaptureDetails captureDetails = new CaptureDetails(stm, rs);
			captureDetails.insertTab(tabs);
		});

		uploadBatch = new Button("Batch Raw Mark Schedules");
		uploadBatch.setDescription("Upload Batch Raw Mark Schedules ");
		uploadBatch.setIcon(VaadinIcons.UPLOAD);
		uploadBatch.addStyleName(ValoTheme.BUTTON_QUIET);
		uploadBatch.addClickListener((e) -> {
			CaptureBatchDetails batchDetails = new CaptureBatchDetails(stm, stmt, rs, rs1);
			batchDetails.insertTab(tabs);
		});

		download = new Button("Subject Mark Schedule");
		download.addStyleName(ValoTheme.BUTTON_QUIET);
		download.setIcon(VaadinIcons.DOWNLOAD);
		download.addClickListener((ee) -> {
			GenerateFinalMarkSchedule finalMarkSchedule = new GenerateFinalMarkSchedule(rs, rs1, stm, stmt, stmt1);
			finalMarkSchedule.insertTab(tabs);
		});

		downloadall = new Button("Class Mark Schedule");
		downloadall.addStyleName(ValoTheme.BUTTON_QUIET);
		downloadall.setIcon(VaadinIcons.DOWNLOAD);
		downloadall.addClickListener((e) -> {
			OveralMarkSchedule markSchedule = new OveralMarkSchedule(stm, stmt, rs, rs1);
			markSchedule.insertTab(tabs);
		});

		grademarkschedule = new Button("Grade Mark Schedule");
		grademarkschedule.addStyleName(ValoTheme.BUTTON_QUIET);
		grademarkschedule.setIcon(VaadinIcons.DOWNLOAD);
		grademarkschedule.addClickListener((e) -> {
			OveralGradeMarkSchedule gradeMarkSchedule = new OveralGradeMarkSchedule(stm, stmt, rs, rs1);
			gradeMarkSchedule.insertTab(tabs);
		});

		classanalyis = new Button("Class Analysis");
		classanalyis.addStyleName(ValoTheme.BUTTON_QUIET);
		classanalyis.setIcon(VaadinIcons.PIE_CHART);
		classanalyis.addClickListener((e) -> {
			ClassDataAnalysis analysis = new ClassDataAnalysis(stm, stmt, rs, rs1);
			analysis.insertTab(tabs);
		});

		gradeanalyis = new Button("Grade Analysis");
		gradeanalyis.addStyleName(ValoTheme.BUTTON_QUIET);
		gradeanalyis.setIcon(VaadinIcons.PYRAMID_CHART);
		gradeanalyis.addClickListener((e) -> {
			GradeDataAnalysis analysis = new GradeDataAnalysis(stm, stmt, rs, rs1);
			analysis.insertTab(tabs);
		});

		settings = new Button("Mark Allocation");
		settings.addStyleName(ValoTheme.BUTTON_QUIET);
		settings.setIcon(VaadinIcons.BULLETS);
		settings.addClickListener((e) -> {
			SubjectsMarkAllocation allocation = new SubjectsMarkAllocation(rs, rs1, stm, stmt);
			allocation.insertTab(tabs);
		});
		passmark = new Button("Pass Mark");
		passmark.addStyleName(ValoTheme.BUTTON_QUIET);
		passmark.setIcon(VaadinIcons.BULLETS);
		passmark.addClickListener((e) -> {
			PassmarkView passmarkView = new PassmarkView(rs, rs1, stm, stmt);
			passmarkView.insertTab(tabs);
		});

		HorizontalLayout questionsbank = new HorizontalLayout(uploadBatch, upload);
		HorizontalLayout students = new HorizontalLayout(download, downloadall, grademarkschedule);
		students.setSpacing(true);
		HorizontalLayout dataan = new HorizontalLayout(classanalyis, gradeanalyis);
		dataan.setSpacing(true);

		HorizontalLayout stngs = new HorizontalLayout(settings, passmark);
		stngs.setSpacing(true);

		pane.addTab(stngs, "Settings", VaadinIcons.BULLETS);
		pane.addTab(questionsbank, "Make Uploads", VaadinIcons.UPLOAD);
		pane.addTab(students, "Make Downloads", VaadinIcons.DOWNLOAD);
		pane.addTab(dataan, "Data Analysis", VaadinIcons.DESKTOP);

		return pane;
	}
}