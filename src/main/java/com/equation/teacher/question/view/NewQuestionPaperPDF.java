package com.equation.teacher.question.view;

import java.awt.Font;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.equation.util.pdf.PDFHeaderFooter;
import com.equation.utils.application.basepath.ApplicationBasePath;
import com.equation.utils.downloadable.filename.Filename;
import com.equation.utils.time.period.FileDownloadFacilitator;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.v7.data.Item;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.v7.ui.Table;
import com.vaadin.ui.TextArea;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "deprecation", "restriction" })
public class NewQuestionPaperPDF {

	public NewQuestionPaperPDF() {
		super();
		return;
	}

	public void generatePDF(String grade, String theterm, Button download, ArrayList<String> questions,
			ArrayList<String> answers, ArrayList<String> d1, ArrayList<String> d2, ArrayList<String> d3,
			String schoolname, Table table, int instructionsvalue, TextArea[] instructions) {
		try {
			String filename = Filename.setFileName() + grade + theterm + "question_paper.pdf";
			Document document = new Document(PageSize.A4, 40, 40, 40, 40);

			String path = ApplicationBasePath.basePath() + "/WEB-INF/reports/" + filename;
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
			Rectangle rect = new Rectangle(30, 30, 550, 800);
			writer.setBoxSize("art", rect);
			PDFHeaderFooter hp = new PDFHeaderFooter();
			writer.setPageEvent(hp);
			document.open();

			String img2path = ApplicationBasePath.basePath() + "/WEB-INF/images/systemlogo.png";

			Image img2 = Image.getInstance(img2path);
			img2.scaleToFit(100f, 100f);
			// img2.setAbsolutePosition(450f, 10f);

			document.add(img2);

			document.add(new Paragraph(schoolname.toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.ITALIC, BaseColor.BLACK)));

			document.add(new Paragraph("INSTRUCTIONS:",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.ITALIC, BaseColor.RED)));

			for (int i = 0; i < instructionsvalue; i++) {
				document.add(new Paragraph((i + 1) + ". " + instructions[i].getValue(),
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.ITALIC, BaseColor.RED)));
			}

			document.add(
					new Paragraph("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.ITALIC, BaseColor.RED)));

			document.add(
					new Paragraph("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.ITALIC, BaseColor.RED)));

			document.add(
					new Paragraph("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.ITALIC, BaseColor.RED)));

			String[] array = table.getColumnHeaders();
			int length = array.length;
			PdfPTable ptable = new PdfPTable(length);
			PdfPCell cell = new PdfPCell(new Paragraph("Examination Specifications".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.RED)));
			cell.setColspan(length);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable.addCell(cell);

			for (int i = 0; i < length; i++) {
				ptable.addCell(new Phrase(array[i],
						FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, BaseColor.BLACK)));
			}

			for (int rows = 0; rows < table.size(); rows++) {
				for (int cols = 0; cols < length; cols++) {
					Item question = table.getItem(rows);
					String a = (String) question.getItemProperty(array[cols]).getValue().toString();
					ptable.addCell(a);
				}
			}
			document.add(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLACK)));
			document.add(ptable);

			document.add(new Paragraph("________________________________________________________________________"));
			document.add(
					new Paragraph("Generated on " + (DateFormat.getDateInstance(DateFormat.LONG)).format(new Date()),
							FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.ITALIC, BaseColor.BLACK)));
			document.add(new Paragraph("This Document is only issued by authorised signatory",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, com.itextpdf.text.Font.ITALIC, BaseColor.RED)));

			document.add(new Paragraph(""));
			document.add(new Paragraph("Dated :" + (DateFormat.getDateInstance(DateFormat.MEDIUM)).format(new Date())));
			document.add(new Paragraph(""));
			document.add(new Paragraph("................................................"));
			document.add(new Paragraph(".                                              ."));
			document.add(new Paragraph("                  Stamp                 ",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.ITALIC, BaseColor.LIGHT_GRAY)));
			document.add(new Paragraph(".                                              ."));
			document.add(new Paragraph(".                                              ."));
			document.add(new Paragraph("................................................"));
			document.add(new Paragraph(""));

			document.add(new Paragraph(""));
			document.add(new Paragraph(""));
			document.add(new Paragraph(""));

			document.newPage();

			ArrayList<String> dummies = new ArrayList<>();
			int arraysize = questions.size();
			for (int i = 0; i < arraysize; i++) {

				dummies.add(d1.get(i));
				dummies.add(d2.get(i));
				dummies.add(d3.get(i));
				dummies.add(answers.get(i));

				Collections.shuffle(dummies);

				document.add(new Paragraph((i + 1) + ").    " + questions.get(i),
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.ROMAN_BASELINE, BaseColor.BLACK)));

				document.add(new Paragraph("A. " + dummies.get(0),
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.ROMAN_BASELINE, BaseColor.BLACK)));

				document.add(new Paragraph("B. " + dummies.get(1),
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.ROMAN_BASELINE, BaseColor.BLACK)));

				document.add(new Paragraph("C. " + dummies.get(2),
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.ROMAN_BASELINE, BaseColor.BLACK)));

				document.add(new Paragraph("D. " + dummies.get(3),
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.ROMAN_BASELINE, BaseColor.BLACK)));
				addSpace(document);
				addSpace(document);
				dummies.clear();
			}

			document.add(new Paragraph(""));
			document.add(new Paragraph(""));
			document.add(new Paragraph(""));

			document.add(new Paragraph("============END OF QUESTION PAPER===========",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.ITALIC, BaseColor.RED)));
			document.close();

			FileDownloadFacilitator.downloadFile(path, download);

		} catch (Exception ee) {
			ee.printStackTrace();
			new Notification("Error", "Interrupted download", Notification.TYPE_ERROR_MESSAGE, true)
					.show(Page.getCurrent());
		}
	}

	public void addSpace(Document document) {
		try {
			document.add(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLACK)));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}