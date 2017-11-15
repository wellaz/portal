package com.equation.equate.generate;

import java.awt.Font;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Date;

import com.equation.equate.utils.application.basepath.ApplicationBasePath;
import com.equation.equate.utils.file.downloader.FileDownloadFacilitator;
import com.equation.equate.utils.pdf.headerfooter.PDFHeaderFooter;
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
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.v7.data.Item;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class PaperMarkSchedulePDF {

	public PaperMarkSchedulePDF() {
		super();
		return;
	}

	@SuppressWarnings("deprecation")
	public void generatePDF(String class_name, String subject, String paper, String out_of, String percentage,
			Table table, Button download) {
		try {
			String filename = class_name + subject + paper + "_marks" + ".pdf";
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

			document.add(img2);

			document.add(new Paragraph(class_name + " Mark Schedule",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.ITALIC, BaseColor.BLACK)));
			document.add(new Paragraph("Subject: " + subject,
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.ITALIC, BaseColor.BLACK)));
			document.add(new Paragraph("Paper " + paper,
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.ITALIC, BaseColor.BLACK)));
			document.add(new Paragraph("Marked Out Of " + out_of,
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.ITALIC, BaseColor.BLACK)));
			document.add(new Paragraph("Percentage Contribution " + percentage + "%",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.ITALIC, BaseColor.BLACK)));
			document.add(new Paragraph("-----------------------------",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.ITALIC, BaseColor.BLACK)));
			document.add(new Paragraph(" Report Generator Module ",
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
			PdfPCell cell = new PdfPCell(new Paragraph(subject.toUpperCase() + " Paper " + paper,
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
			

			document.add(ptable);
			document.add(new Paragraph(""));
			document.add(new Paragraph(""));
			document.add(new Paragraph(""));
			document.add(new Paragraph(""));
			document.add(new Paragraph("Dated :" + (DateFormat.getDateInstance(DateFormat.MEDIUM)).format(new Date())));
			document.add(new Paragraph(""));
			String grantString = ((length - 1) > 1) ? "" + (length - 1) : "None";
			document.add(new Paragraph("Number of records " + grantString));
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
