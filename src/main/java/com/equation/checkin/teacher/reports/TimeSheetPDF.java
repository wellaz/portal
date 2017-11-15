package com.equation.checkin.teacher.reports;

import java.awt.Font;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Date;

import com.equation.util.pdf.PDFHeaderFooter;
import com.equation.utils.application.basepath.ApplicationBasePath;
import com.equation.utils.time.period.FileDownloadFacilitator;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
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
public class TimeSheetPDF {

	public void generatePDF(String schoolName, String date, Table table, Button download) {
		try {
			String filename = date + "_checkin_register" + ".pdf";
			Document document = new Document(PageSize.A4.rotate(), 40, 40, 40, 40);

			String path = ApplicationBasePath.basePath() + "/WEB-INF/reports/" + filename;
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
			Rectangle rect = new Rectangle(30, 30, 550, 800);
			writer.setBoxSize("art", rect);
			PDFHeaderFooter hp = new PDFHeaderFooter();
			writer.setPageEvent(hp);
			document.open();

			String img2path = ApplicationBasePath.basePath() + "/WEB-INF/images/systemlogo.png";

			Image img2 = Image.getInstance(img2path);
			img2.scaleToFit(300f, 200f);

			document.add(img2);

			document.add(new Paragraph(schoolName.toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.ITALIC, BaseColor.BLACK)));

			document.add(new Paragraph("Teachers Check In And Check Out Register".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.ITALIC, BaseColor.BLACK)));

			document.add(new Paragraph("Date: " + date,
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.ITALIC, BaseColor.BLACK)));
			document.add(new Paragraph("-----------------------------",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.ITALIC, BaseColor.BLACK)));
			document.add(new Paragraph("Equation Report Generator Module ",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.ITALIC, BaseColor.BLACK)));
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

			ptable.setWidths(new int[] { 10, 10, 10, 10, 10, 10, 10, 10 });
			ptable.setWidthPercentage(100);

			PdfPCell cell = new PdfPCell(new Paragraph("Check In Check Out Register".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
			cell.setColspan(length);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable.addCell(cell);

			PdfPCell names = new PdfPCell(new Paragraph("name".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
			names.setColspan(1);
			names.setBackgroundColor(BaseColor.LIGHT_GRAY);
			names.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable.addCell(names);

			PdfPCell timein = new PdfPCell(new Paragraph("time in".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
			timein.setColspan(1);
			timein.setBackgroundColor(BaseColor.LIGHT_GRAY);
			timein.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable.addCell(timein);

			PdfPCell errand = new PdfPCell(new Paragraph("errand".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
			errand.setColspan(4);
			errand.setBackgroundColor(BaseColor.LIGHT_GRAY);
			errand.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable.addCell(errand);

			PdfPCell timeout = new PdfPCell(new Paragraph("time out".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
			timeout.setColspan(1);
			timeout.setBackgroundColor(BaseColor.LIGHT_GRAY);
			timeout.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable.addCell(timeout);

			PdfPCell signature = new PdfPCell(new Paragraph("signature".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
			signature.setColspan(1);
			signature.setBackgroundColor(BaseColor.LIGHT_GRAY);
			signature.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable.addCell(signature);

			PdfPCell one = new PdfPCell(new Paragraph("-",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
			one.setColspan(1);
			one.setBackgroundColor(BaseColor.LIGHT_GRAY);
			one.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable.addCell(one);

			PdfPCell two = new PdfPCell(new Paragraph("-",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
			two.setColspan(1);
			two.setBackgroundColor(BaseColor.LIGHT_GRAY);
			two.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable.addCell(two);

			PdfPCell errandout = new PdfPCell(new Paragraph("time out".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
			errandout.setColspan(1);
			errandout.setBackgroundColor(BaseColor.LIGHT_GRAY);
			errandout.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable.addCell(errandout);

			PdfPCell myerrand = new PdfPCell(new Paragraph("errand".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
			myerrand.setColspan(1);
			myerrand.setBackgroundColor(BaseColor.LIGHT_GRAY);
			myerrand.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable.addCell(myerrand);

			PdfPCell errandin = new PdfPCell(new Paragraph("time in".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
			errandin.setColspan(1);
			errandin.setBackgroundColor(BaseColor.LIGHT_GRAY);
			errandin.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable.addCell(errandin);

			PdfPCell approved = new PdfPCell(new Paragraph("approved by".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
			approved.setColspan(1);
			approved.setBackgroundColor(BaseColor.LIGHT_GRAY);
			approved.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable.addCell(approved);

			PdfPCell three = new PdfPCell(new Paragraph("-",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
			three.setColspan(1);
			three.setBackgroundColor(BaseColor.LIGHT_GRAY);
			three.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable.addCell(three);

			PdfPCell four = new PdfPCell(new Paragraph("-",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
			four.setColspan(1);
			four.setBackgroundColor(BaseColor.LIGHT_GRAY);
			four.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable.addCell(four);

			ptable.setHeaderRows(1);
			ptable.setHeaderRows(2);
			ptable.setHeaderRows(3);

			for (int rows = 0; rows < table.size(); rows++) {
				for (int cols = 0; cols < length; cols++) {
					Item question = table.getItem(rows);
					String a = (String) question.getItemProperty(array[cols]).getValue().toString();
					ptable.addCell(a);
				}
			}

			document.add(ptable);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph("Dated :" + (DateFormat.getDateInstance(DateFormat.MEDIUM)).format(new Date())));
			document.add(Chunk.NEWLINE);
			String grantString = ((ptable.size() - 3) > 0) ? "" + (ptable.size() - 3) : "None";
			document.add(new Paragraph("Number of records " + grantString));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph("Sign (Receiver) : __________________________Date_____________________"));
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph("Sign (issuer) :   __________________________Date_____________________"));
			document.add(new Paragraph("................................................"));
			document.add(new Paragraph(".                                              ."));
			document.add(new Paragraph("                  Stamp                 ",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.ITALIC, BaseColor.LIGHT_GRAY)));
			document.add(new Paragraph(".                                              ."));
			document.add(new Paragraph(".                                              ."));
			document.add(new Paragraph("................................................"));
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
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
