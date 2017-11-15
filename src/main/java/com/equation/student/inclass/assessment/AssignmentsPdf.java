package com.equation.student.inclass.assessment;

import java.awt.Font;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Date;

import com.equation.util.pdf.PDFHeaderFooter;
import com.equation.utils.application.basepath.ApplicationBasePath;
import com.equation.utils.date.DateUtility;
import com.equation.utils.downloadable.filename.Filename;
import com.equation.utils.time.period.FileDownloadFacilitator;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
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

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "deprecation", "restriction" })
public class AssignmentsPdf {

	public AssignmentsPdf() {
		super();
		return;
	}

	public void generatePDF(Table table, Table table2, Button download, String studentname, String studentID,
			String class_name) {
		try {
			String filename = Filename.setFileName() + studentID + "_assignments_performance" + ".pdf";
			Document document = new Document(PageSize.A4, 40, 40, 40, 40);

			String path = ApplicationBasePath.basePath() + "/WEB-INF/reports/" + filename;
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
			Rectangle rect = new Rectangle(30, 30, 550, 800);
			writer.setBoxSize("art", rect);
			PDFHeaderFooter hp = new PDFHeaderFooter();
			writer.setPageEvent(hp);
			document.open();

			String img1path = ApplicationBasePath.basePath() + "/WEB-INF/images/zim_logo.png";

			String img2path = ApplicationBasePath.basePath() + "/WEB-INF/images/systemlogo.png";

			Image img1 = Image.getInstance(img1path);
			img1.scaleToFit(100f, 100f);

			Image img2 = Image.getInstance(img2path);
			img2.scaleToFit(100f, 100f);
			img2.setAbsolutePosition(450f, 10f);

			document.add(img1);
			document.add(img2);

			document.add(new Paragraph(
					new DateUtility().timeStamp() + "Assignments Continuous Assessment Report".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.ITALIC, BaseColor.BLACK)));
			document.add(new Paragraph("--------------------------------------------------------------",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.ITALIC, BaseColor.BLACK)));
			document.add(new Paragraph("Name : " + studentname,
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.ITALIC, BaseColor.BLACK)));
			document.add(new Paragraph("Student ID : " + studentID,
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.ITALIC, BaseColor.BLACK)));
			document.add(new Paragraph("Current Class : " + class_name,
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.ITALIC, BaseColor.BLACK)));
			document.add(new Paragraph("________________________________________________________________________"));
			document.add(
					new Paragraph("Generated on " + (DateFormat.getDateInstance(DateFormat.LONG)).format(new Date()),
							FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.ITALIC, BaseColor.BLACK)));
			document.add(new Paragraph("This Document is only issued by authorised signatory",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, com.itextpdf.text.Font.ITALIC, BaseColor.RED)));
			document.add(new Paragraph("  "));
			document.add(new Paragraph("  "));
			String[] array = table.getColumnHeaders();
			int length = array.length;
			PdfPTable ptable = new PdfPTable(length);
			PdfPCell cell = new PdfPCell(new Paragraph("All Assignments Continuous Assessment".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
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
					String a = (String) question.getItemProperty(array[cols]).getValue();
					ptable.addCell(a);
				}
			}

			String[] array2 = table2.getColumnHeaders();
			int length2 = array2.length;
			PdfPTable ptable2 = new PdfPTable(length2);
			PdfPCell cell2 = new PdfPCell(new Paragraph("Per Subject Assignments Continuous Assessment".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
			cell2.setColspan(length2);
			cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable2.addCell(cell2);

			for (int i = 0; i < length2; i++) {

				ptable2.addCell(new Phrase(array2[i],
						FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, BaseColor.BLACK)));
			}

			for (int rows = 0; rows < table2.size(); rows++) {
				for (int cols = 0; cols < length2; cols++) {
					Item question = table.getItem(rows);
					String a = (String) question.getItemProperty(array2[cols]).getValue();
					ptable2.addCell(a);
				}
			}

			document.add(ptable);

			document.add(new Paragraph("Subject Performance For All Assignments",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLACK)));
			document.add(new Paragraph(""));
			document.add(ptable2);
			document.add(new Paragraph(""));
			document.add(new Paragraph("Dated :" + (DateFormat.getDateInstance(DateFormat.MEDIUM)).format(new Date())));
			document.add(new Paragraph(""));
			document.add(new Paragraph(""));
			document.add(new Paragraph("Sign (Receiver) : __________________________Date_____________________"));
			document.add(new Paragraph(""));
			document.add(new Paragraph(""));
			document.add(new Paragraph("Sign (issuer) :   __________________________Date_____________________"));
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
			document.add(new Paragraph("                WITH THANKS!        "));
			document.close();

			FileDownloadFacilitator.downloadFile(path, download);

		} catch (Exception ee) {
			ee.printStackTrace();
			new Notification("Error", "Interrupted download", Notification.TYPE_ERROR_MESSAGE, true)
					.show(Page.getCurrent());
		}
	}

}
