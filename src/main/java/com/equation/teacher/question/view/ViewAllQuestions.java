package com.equation.teacher.question.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.equation.school.classes.collection.AllClasses;
import com.equation.school.subjects.AllSchoolSubjects;
import com.equation.utils.date.AllTerms;
import com.equation.utils.print.currentpage.PrintCurrentPage;
import com.equation.utils.schoolterms.AllYearTermsBin;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.data.Item;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial", "deprecation" })
public class ViewAllQuestions extends CustomComponent implements View {
	Statement stm, stmt;
	ResultSet rs, rs1;
	private ComboBox<String> subjects;
	private ComboBox<String> grades;
	private Button search;
	private Table grid;
	String schoolname;
	TabSheet tabs;
	ArrayList<String> questions = new ArrayList<>();
	ArrayList<String> answers = new ArrayList<>();
	ArrayList<String> d1 = new ArrayList<>();
	ArrayList<String> d2 = new ArrayList<>();
	ArrayList<String> d3 = new ArrayList<>();
	private String grade;
	private String subject;
	private String theterm;
	private String examaner;
	private String marks;
	private String qestionsToAttemp;
	private String period;
	private String papernumber;
	private TextArea[] instructions;
	private VerticalLayout instructionsLayout;
	private int instructionsvalue = 0;

	public ViewAllQuestions(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, TabSheet tabs,
			String schoolname) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.tabs = tabs;
		this.schoolname = schoolname;

		VerticalLayout layout = new VerticalLayout();
		instructionsLayout = new VerticalLayout();
		instructionsLayout.setSpacing(true);
		subjects = new ComboBox<>("Choose Subject");
		subjects.setRequiredIndicatorVisible(true);
		subjects.setEmptySelectionAllowed(false);
		subjects.setItems(new AllSchoolSubjects(rs, rs1, stm, stmt).allSchoolSubjects());

		grades = new ComboBox<>("Select Class");
		grades.setEmptySelectionAllowed(false);
		grades.setRequiredIndicatorVisible(true);
		grades.setItems(new AllClasses(stmt, rs1).classesCollection(schoolname));

		search = new Button("Search");
		FormLayout formLayout = new FormLayout(grades, subjects, search);
		formLayout.setCaption("<h1>Explore The Question Bank<h1/>");
		formLayout.setCaptionAsHtml(true);
		formLayout.setSpacing(true);

		HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
		horizontalLayout.setSpacing(true);
		horizontalLayout.addStyleName(ValoTheme.LAYOUT_CARD);
		search.addStyleName("student_report_search_button");
		search.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		search.setIcon(VaadinIcons.SEARCH);

		search.addClickListener((e) -> {
			subject = (String) subjects.getValue();
			grade = (String) grades.getValue();
			// String tablename = (subject.replace(" ", "_")).toLowerCase();

			if (!(subjects.getValue().equals("") || grades.getValue().equals(""))) {
				String query = "SELECT question,correctAnswer,destractor1,destractor2,destractor3,grade FROM questions_pool,school_subjects,classes WHERE classes.classname='"
						+ grade + "' AND school_subjects.subject='" + subject + "'";

				try {
					this.rs1 = this.stmt.executeQuery(query);
					this.rs1.last();
					int rows = this.rs1.getRow();

					if (rows > 0) {
						// Panel panel = new Panel();
						// layout.removeAllComponents();
						String query1 = "SELECT question,correctAnswer,destractor1,destractor2,destractor3,grade FROM questions_pool,school_subjects,classes WHERE classes.classname='"
								+ grade + "' AND school_subjects.subject='" + subject + "' ORDER BY RAND()";

						grid = new Table();
						grid.addContainerProperty("Select", CheckBox.class, null);
						grid.addContainerProperty("Question", RichTextArea.class, null);
						grid.addContainerProperty("Corect Answer", String.class, null);
						grid.addContainerProperty("Distractor #1", String.class, null);
						grid.addContainerProperty("Distractor #2", String.class, null);
						grid.addContainerProperty("Distractor #3", String.class, null);
						grid.setMultiSelect(true);
						int i = 0;

						this.rs = this.stm.executeQuery(query1);
						CheckBox[] checkBox = new CheckBox[rows];
						RichTextArea area[] = new RichTextArea[rows];
						while (this.rs.next()) {
							String question = this.rs.getString(1);
							String correctAnswer = this.rs.getString(2);
							String distractor1 = this.rs.getString(3);
							String distractor2 = this.rs.getString(4);
							String distractor3 = this.rs.getString(5);

							area[i] = new RichTextArea("", question);
							area[i].setReadOnly(true);
							checkBox[i] = new CheckBox();
							checkBox[i].setValue(false);
							checkBox[i].addValueChangeListener(valueChange -> {
								// grid.select((Boolean)
								// checkBox[i].getValue());
							});

							grid.addItem(new Object[] { checkBox[i], area[i], correctAnswer, distractor1, distractor2,
									distractor3 }, new Integer(i));
							i++;
						}
						double size = grid.size();

						grid.setFooterVisible(true);
						grid.setColumnFooter("Question", "Total " + String.valueOf(size) + " quastions");
						grid.setSizeFull();
						grid.setPageLength((int) size);
						grid.addStyleName("questions_table");
						grid.setSelectable(true);
						grid.setColumnCollapsingAllowed(true);

						// panel.setContent(grid);
						// layout.addComponent(panel);
						createWindow(grid, size + " " + subject + " questions found", checkBox, area);
					} else {
						new Notification("Warning", "No Data Found Here", Notification.TYPE_WARNING_MESSAGE, true)
								.show(Page.getCurrent());
					}

				} catch (SQLException ee) {
					ee.printStackTrace();
				}

			} else {
				new Notification("Error", "Blank Fields in the search criteria", Notification.TYPE_WARNING_MESSAGE,
						true).show(Page.getCurrent());
			}

		});
		layout.addComponents(horizontalLayout);
		layout.setSpacing(true);
		layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
		this.setCompositionRoot(layout);
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

	public Window createWindow(Table grid, String title, CheckBox[] checkBox, RichTextArea[] area) {
		Window answerWindow = new Window(subject.toUpperCase());
		Button close = new Button("Close");
		close.addStyleName(ValoTheme.BUTTON_DANGER);
		close.setIcon(VaadinIcons.CLOSE);
		Button extract = new Button("EXTRACT");
		extract.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		extract.setIcon(VaadinIcons.PRINT);
		Button delete = new Button("Delete");
		delete.setIcon(VaadinIcons.COPY_O);
		delete.addStyleName(ValoTheme.BUTTON_DANGER);

		HorizontalLayout horizontalLayout = new HorizontalLayout(extract, delete, close);
		horizontalLayout.setSizeFull();
		horizontalLayout.setSpacing(true);
		VerticalLayout subContent = new VerticalLayout(horizontalLayout, grid);
		subContent.setMargin(true);
		subContent.setSpacing(true);
		answerWindow.setContent(subContent);
		answerWindow.center();
		answerWindow.setModal(false);
		answerWindow.setSizeFull();
		// answerWindow.setHeight("600px");
		// answerWindow.setWidth("800px");
		UI.getCurrent().addWindow((answerWindow));

		close.addClickListener((ev) -> {
			UI.getCurrent().removeWindow(answerWindow);
			answerWindow.close();

		});
		extract.addClickListener((e) -> {
			int size = grid.size();
			Table toPring = new Table();
			toPring.addContainerProperty("Question", RichTextArea.class, null);
			toPring.addContainerProperty("Corect Answer", String.class, null);
			toPring.addContainerProperty("Distractor #1", String.class, null);
			toPring.addContainerProperty("Distractor #2", String.class, null);
			toPring.addContainerProperty("Distractor #3", String.class, null);
			for (int i = 0; i < size; i++) {
				if (checkBox[i].getValue()) {
					Item question = grid.getItem(i);
					Item correctAnswer = grid.getItem(i);
					Item distractor1 = grid.getItem(i);
					Item distractor2 = grid.getItem(i);
					Item distrator3 = grid.getItem(i);
					area[i].setValue(((RichTextArea) question.getItemProperty("Question").getValue()).getValue());

					String q = (String) area[i].getValue();
					String a = (String) correctAnswer.getItemProperty("Corect Answer").getValue();
					String d1 = (String) distractor1.getItemProperty("Distractor #1").getValue();
					String d2 = (String) distractor2.getItemProperty("Distractor #2").getValue();
					String d3 = (String) distrator3.getItemProperty("Distractor #3").getValue();

					questions.add(q);
					answers.add(a);
					this.d1.add(d1);
					this.d2.add(d2);
					this.d3.add(d3);

					RichTextArea chosenQuestions = new RichTextArea();
					chosenQuestions.setValue(q);
					chosenQuestions.setReadOnly(true);

					toPring.addItem(new Object[] { chosenQuestions, a, d1, d2, d3 }, new Integer(i));
				}
			}
			double size1 = toPring.size();

			toPring.setFooterVisible(true);
			toPring.setColumnFooter("Question", "Total " + String.valueOf(size1) + " quastions");
			toPring.setSizeFull();
			toPring.setPageLength((int) size1);
			toPring.addStyleName("questions_table");
			toPring.setSelectable(true);
			toPring.setColumnCollapsingAllowed(true);

			Button setQuestionPaper = new Button("Set Question Paper");
			setQuestionPaper.addStyleName(ValoTheme.BUTTON_FRIENDLY);
			setQuestionPaper.setIcon(VaadinIcons.PRINT);

			Button printButton = new Button("Print");
			printButton.setIcon(VaadinIcons.PRINT);
			printButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
			printButton.addClickListener((er) -> {
				PrintCurrentPage.print();
			});
			Button closeWinndow = new Button("Close");
			closeWinndow.addStyleName(ValoTheme.BUTTON_DANGER);
			closeWinndow.setIcon(VaadinIcons.CLOSE);

			HorizontalLayout horizontalLayoutT = new HorizontalLayout(setQuestionPaper, printButton, closeWinndow);
			horizontalLayoutT.setSpacing(true);
			VerticalLayout layout = new VerticalLayout(horizontalLayoutT, toPring);
			layout.setSpacing(true);

			Window window = new Window(size1 + ((size1 > 1) ? " Questions" : " Question") + "  ready for printing!",
					layout);
			closeWinndow.addClickListener((eer) -> {
				UI.getCurrent().removeWindow(window);
				window.close();
			});
			window.setSizeFull();
			window.setModal(false);
			window.center();
			UI.getCurrent().addWindow(window);
			Table table = new Table();
			table.addContainerProperty("Description", String.class, null);
			table.addContainerProperty("Value", String.class, null);

			Button download = new Button("Download");
			download.setIcon(VaadinIcons.DOWNLOAD);
			download.addStyleName(ValoTheme.BUTTON_FRIENDLY);
			setQuestionPaper.addClickListener((eer) -> {

				Window settings = new Window(
						size1 + ((size1 > 1) ? " Questions" : " Question") + " Selected For " + subject);
				settings.setSizeFull();
				Button closeButton = new Button("Close");
				closeButton.addStyleName(ValoTheme.BUTTON_DANGER);
				closeButton.setIcon(VaadinIcons.CLOSE);
				closeButton.addClickListener((ee) -> {
					UI.getCurrent().removeWindow(settings);
					settings.close();
				});

				Button continueButton = new Button("Continue");
				continueButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
				continueButton.setIcon(VaadinIcons.PLAY);

				HorizontalLayout horizontal = new HorizontalLayout(continueButton, closeButton);
				ComboBox<String> terms = new ComboBox<>("Select Term");
				terms.setItems(AllYearTermsBin.allTermsArray());
				terms.setEmptySelectionAllowed(false);
				terms.setRequiredIndicatorVisible(true);
				terms.setValue(new AllTerms().thisTerm());

				TextField numberOfMarks = new TextField("Number Of Marks");
				numberOfMarks.setRequiredIndicatorVisible(true);

				TextField timePeriod = new TextField("Time Period");
				timePeriod.setRequiredIndicatorVisible(true);

				TextField nameOfExamaner = new TextField("Examiner");
				nameOfExamaner.setRequiredIndicatorVisible(true);

				TextField numberOfQestionsToAttemp = new TextField("Number Of Questions To Attempt");
				numberOfQestionsToAttemp.setRequiredIndicatorVisible(true);

				TextField paperNumber = new TextField(subject.toUpperCase() + " Paper:");
				paperNumber.setRequiredIndicatorVisible(true);

				ComboBox<String> onumberOfInstructions = new ComboBox<>("How Many Instructions Do You Want To Write?");
				onumberOfInstructions.setItems("1", "2", "3", "4", "5", "6", "7", "8");
				onumberOfInstructions.setEmptySelectionAllowed(false);
				onumberOfInstructions.addValueChangeListener((er) -> {
					instructionsLayout.removeAllComponents();
					instructionsvalue = Integer.parseInt((String) er.getValue());
					instructions = new TextArea[instructionsvalue];
					for (int i = 0; i < instructionsvalue; i++) {
						instructions[i] = new TextArea();
						Label in = new Label("Instruction " + (i + 1));
						HorizontalLayout horizontalLayout2 = new HorizontalLayout(in, instructions[i]);
						horizontalLayout2.setSpacing(true);
						instructionsLayout.addComponent(horizontalLayout2);
					}
				});

				Window downnloadWindow = new Window("Download");
				downnloadWindow.setModal(true);
				continueButton.addClickListener((eee) -> {
					theterm = (String) terms.getValue().toString();
					examaner = nameOfExamaner.getValue();
					marks = numberOfMarks.getValue();
					qestionsToAttemp = numberOfQestionsToAttemp.getValue();
					papernumber = paperNumber.getValue();
					period = timePeriod.getValue();
					if (!(theterm.equals("") || examaner.equals("") || marks.equals("") || qestionsToAttemp.equals("")
							|| papernumber.equals("") || period.equals(""))) {
						if (instructionsvalue > 0) {

							table.addItem(new Object[] { "Class Name", grade }, 0);
							table.addItem(new Object[] { "Term", theterm }, 1);
							table.addItem(new Object[] { "Subject", subject }, 2);
							table.addItem(new Object[] { "Paper", papernumber }, 3);
							table.addItem(new Object[] { "Total Number Of marks", marks }, 4);
							table.addItem(new Object[] { "Number Of Questions To Answer", qestionsToAttemp }, 5);
							table.addItem(new Object[] { "Examiner", examaner }, 6);
							table.addItem(new Object[] { "TIME", period }, 7);
							table.setPageLength(table.size());
							table.setSizeFull();
							NewQuestionPaperPDF newQuestionPaperPDF = new NewQuestionPaperPDF();
							newQuestionPaperPDF.generatePDF(grade, theterm, download, questions, answers, d1, d2, d3,
									schoolname, table, instructionsvalue, instructions);

							Button closethis = new Button("Close");
							closethis.setIcon(VaadinIcons.CLOSE);
							closethis.addStyleName(ValoTheme.BUTTON_DANGER);

							UI.getCurrent().removeWindow(settings);
							settings.close();

							downnloadWindow.center();
							downnloadWindow.setWidth("40%");
							downnloadWindow.setHeight("70%");
							HorizontalLayout horizontalLayoutw = new HorizontalLayout(download, closethis);
							horizontalLayoutw.setSpacing(true);
							closethis.addClickListener((eeee) -> {
								UI.getCurrent().removeWindow(downnloadWindow);
								downnloadWindow.close();
							});

							VerticalLayout verticalLayout = new VerticalLayout(table, horizontalLayoutw);
							verticalLayout.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
							verticalLayout.setComponentAlignment(horizontalLayoutw, Alignment.BOTTOM_CENTER);
							verticalLayout.setSpacing(true);
							downnloadWindow.setContent(verticalLayout);

							UI.getCurrent().addWindow(downnloadWindow);
						} else {
							new Notification("Error", "Give At Least One Instruction", Notification.TYPE_ERROR_MESSAGE,
									true).show(Page.getCurrent());
						}
					} else {
						new Notification("Error", "Complete this form", Notification.TYPE_ERROR_MESSAGE, true)
								.show(Page.getCurrent());
					}

				});

				FormLayout formLayout = new FormLayout(terms, nameOfExamaner, numberOfMarks, numberOfQestionsToAttemp,
						paperNumber, timePeriod);
				formLayout.setSpacing(true);

				VerticalLayout verticalLayout4 = new VerticalLayout(onumberOfInstructions, instructionsLayout);
				verticalLayout4.setSpacing(true);

				HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
				splitPanel.setFirstComponent(formLayout);
				splitPanel.setSecondComponent(verticalLayout4);

				horizontal.setSpacing(true);
				Panel panel = new Panel("General Instructions");
				panel.setIcon(VaadinIcons.SUN_O);
				panel.setContent(splitPanel);

				VerticalLayout layout1 = new VerticalLayout(panel, horizontal);

				layout1.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
				layout1.setComponentAlignment(horizontal, Alignment.BOTTOM_CENTER);
				layout1.setSpacing(true);
				settings.setContent(layout1);
				settings.center();

				UI.getCurrent().addWindow(settings);
			});

		});
		delete.addClickListener((e) -> {
			int size = grid.size();
			Table tabletodelete = new Table();
			tabletodelete.addContainerProperty("Question", String.class, null);
			tabletodelete.addContainerProperty("Corect Answer", String.class, null);
			tabletodelete.addContainerProperty("Distractor #1", String.class, null);
			tabletodelete.addContainerProperty("Distractor #2", String.class, null);
			tabletodelete.addContainerProperty("Distractor #3", String.class, null);
			for (int i = 0; i < size; i++) {
				if (checkBox[i].getValue()) {
					Item question = grid.getItem(i);
					Item correctAnswer = grid.getItem(i);
					Item distractor1 = grid.getItem(i);
					Item distractor2 = grid.getItem(i);
					Item distrator3 = grid.getItem(i);

					area[i].setValue(((RichTextArea) question.getItemProperty("Question").getValue()).getValue());

					String q = area[i].getValue();
					String a = (String) correctAnswer.getItemProperty("Corect Answer").getValue();
					String d1 = (String) distractor1.getItemProperty("Distractor #1").getValue();
					String d2 = (String) distractor2.getItemProperty("Distractor #2").getValue();
					String d3 = (String) distrator3.getItemProperty("Distractor #3").getValue();

					tabletodelete.addItem(new Object[] { q, a, d1, d2, d3 }, new Integer(i));

					String deleteQuery = "DELETE FROM  questions_pool WHERE question  ='" + q
							+ "' AND correctAnswer = '" + a + "' AND 	destractor1 = '" + d1 + "' AND 	destractor2 = '"
							+ d2 + "' AND 	destractor3 = '" + d3 + "'";
					try {
						stm.execute(deleteQuery);
						grid.removeItem(question.getItemProperty("Question"));
						grid.removeItem(correctAnswer.getItemProperty("Corect Answer"));
						grid.removeItem(distractor1.getItemProperty("Distractor #1"));
						grid.removeItem(distractor2.getItemProperty("Distractor #2"));
						grid.removeItem(distrator3.getItemProperty("Distractor #3"));
					} catch (SQLException ee) {
						ee.printStackTrace(System.err);
					}
				}
			}
			double size1 = tabletodelete.size();

			tabletodelete.setFooterVisible(true);
			tabletodelete.setColumnFooter("Question", "Total " + String.valueOf(size1) + " quastions");
			tabletodelete.setSizeFull();
			tabletodelete.setPageLength((int) size1);
			tabletodelete.addStyleName("questions_table");
			tabletodelete.setSelectable(true);
			tabletodelete.setColumnCollapsingAllowed(true);

			Button ok = new Button("OK");
			ok.addStyleName(ValoTheme.BUTTON_DANGER);
			VerticalLayout layout = new VerticalLayout(ok, tabletodelete);

			Window window = new Window(size1 + ((size1 > 1) ? " Questions" : " Question") + " wiped out!", layout);
			window.setSizeFull();
			window.setModal(false);
			window.center();
			UI.getCurrent().addWindow(window);
			ok.addClickListener((ee) -> {
				UI.getCurrent().removeWindow(window);
				window.close();
			});

			grid.refreshRowCache();
		});
		return answerWindow;
	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Question Bank")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Question Bank", VaadinIcons.QUESTION_CIRCLE);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}
}