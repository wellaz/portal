package com.equation.school.statistics.monthly.document;

import java.awt.Font;
import java.io.FileOutputStream;

import com.equation.util.pdf.PDFHeaderFooter;
import com.equation.utils.application.basepath.ApplicationBasePath;
import com.equation.utils.downloadable.filename.Filename;
import com.equation.utils.pdf.NewPage;
import com.equation.utils.time.period.FileDownloadFacilitator;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
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
public class MonthlyReturnPDF {

	public MonthlyReturnPDF() {
		super();
		return;
	}

	public void generatePDF(String schoolname, String thisMonth, String thisYear, String date, String departmentCode,
			String stationCode, String nameOfDistrict, String registrationNumber, Table table1, Table table2,
			Table table3, Table table4, Table table5, Table table6, Table table7, Table table8, Button download) {
		try {
			String filename = Filename.setFileName() + "monthly_return_paper.pdf";
			Document document = new Document(PageSize.A4.rotate(), 0, 0, 0, 0);

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

			document.add(new Paragraph(
					"PRIMARY SCHOOLS MONTHLY STAFFING AND ENROLMENT RETURN � MASHONALAND WEST PROVINCE".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.BOLD, BaseColor.BLACK)));

			document.add(new Paragraph(
					"Month........".toUpperCase() + " " + thisMonth + "\t\t Date".toUpperCase() + "......" + date,
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.PLAIN, BaseColor.BLACK)));

			document.add(new Paragraph(
					"Name of school:  ".toUpperCase() + schoolname.toUpperCase() + "\t\t DEPT/STN: " + departmentCode
							+ "/" + stationCode,
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.BOLD, BaseColor.BLACK)));

			document.add(new Paragraph(
					"name of district:  ".toUpperCase() + nameOfDistrict + "\t\t REGISTRATION NUMBER: "
							+ registrationNumber,
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.BOLD, BaseColor.BLACK)));

			document.add(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLACK)));

			String[] array1 = table1.getColumnHeaders();
			int length1 = array1.length;
			PdfPTable ptable1 = new PdfPTable(length1);
			ptable1.setWidthPercentage(99.9f);
			ptable1.setWidths(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 });
			ptable1.setWidthPercentage(100);

			PdfPCell cell1 = new PdfPCell(new Paragraph("ENROLMENT BY Gender AND GRADE".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			cell1.setColspan(length1);
			cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(cell1);

			PdfPCell gender = new PdfPCell(new Paragraph("Gender".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			gender.setColspan(1);
			gender.setBackgroundColor(BaseColor.LIGHT_GRAY);
			gender.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(gender);

			PdfPCell grades = new PdfPCell(new Paragraph("Grades".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			grades.setColspan(7);
			grades.setBackgroundColor(BaseColor.LIGHT_GRAY);
			grades.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(grades);

			PdfPCell total1 = new PdfPCell(new Paragraph("Total 1".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			total1.setColspan(1);
			total1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			total1.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(total1);

			PdfPCell others = new PdfPCell(new Paragraph("Other Classes".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			others.setColspan(2);
			others.setBackgroundColor(BaseColor.LIGHT_GRAY);
			others.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(others);

			PdfPCell ecd = new PdfPCell(new Paragraph("e c d".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			ecd.setColspan(2);
			ecd.setBackgroundColor(BaseColor.LIGHT_GRAY);
			ecd.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(ecd);

			PdfPCell total2 = new PdfPCell(new Paragraph("total 2".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			total2.setColspan(1);
			total2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			total2.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(total2);

			PdfPCell one = new PdfPCell(new Paragraph("-".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			one.setColspan(1);
			one.setBackgroundColor(BaseColor.LIGHT_GRAY);
			one.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(one);

			PdfPCell two = new PdfPCell(new Paragraph("1".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			two.setColspan(1);
			two.setBackgroundColor(BaseColor.LIGHT_GRAY);
			two.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(two);

			PdfPCell three = new PdfPCell(new Paragraph("2".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			three.setColspan(1);
			three.setBackgroundColor(BaseColor.LIGHT_GRAY);
			three.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(three);

			PdfPCell four = new PdfPCell(new Paragraph("3".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			four.setColspan(1);
			four.setBackgroundColor(BaseColor.LIGHT_GRAY);
			four.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(four);

			PdfPCell five = new PdfPCell(new Paragraph("4".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			five.setColspan(1);
			five.setBackgroundColor(BaseColor.LIGHT_GRAY);
			five.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(five);

			PdfPCell six = new PdfPCell(new Paragraph("5".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			six.setColspan(1);
			six.setBackgroundColor(BaseColor.LIGHT_GRAY);
			six.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(six);

			PdfPCell seven = new PdfPCell(new Paragraph("7".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			seven.setColspan(1);
			seven.setBackgroundColor(BaseColor.LIGHT_GRAY);
			seven.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(seven);

			PdfPCell eight = new PdfPCell(new Paragraph("7".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			eight.setColspan(1);
			eight.setBackgroundColor(BaseColor.LIGHT_GRAY);
			eight.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(eight);

			PdfPCell nine = new PdfPCell(new Paragraph("-".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			nine.setColspan(1);
			nine.setBackgroundColor(BaseColor.LIGHT_GRAY);
			nine.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(nine);

			PdfPCell ru = new PdfPCell(new Paragraph("r u".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			ru.setColspan(1);
			ru.setBackgroundColor(BaseColor.LIGHT_GRAY);
			ru.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(ru);

			PdfPCell sp = new PdfPCell(new Paragraph("special".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			sp.setColspan(1);
			sp.setBackgroundColor(BaseColor.LIGHT_GRAY);
			sp.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(sp);

			PdfPCell ecda = new PdfPCell(new Paragraph("ecd a".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			ecda.setColspan(1);
			ecda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			ecda.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(ecda);

			PdfPCell ecdb = new PdfPCell(new Paragraph("ecd b".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			ecdb.setColspan(1);
			ecdb.setBackgroundColor(BaseColor.LIGHT_GRAY);
			ecdb.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(ecdb);

			PdfPCell ten = new PdfPCell(new Paragraph("-".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			ten.setColspan(1);
			ten.setBackgroundColor(BaseColor.LIGHT_GRAY);
			ten.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable1.addCell(ten);

			ptable1.setHeaderRows(1);
			ptable1.setHeaderRows(2);
			ptable1.setHeaderRows(3);

			/*
			 * for (int i = 0; i < length1; i++) { ptable1.addCell(new
			 * Phrase(array1[i], FontFactory.getFont(FontFactory.TIMES_BOLD, 12,
			 * Font.BOLD, BaseColor.BLACK))); }
			 */

			for (int rows = 0; rows < table1.size(); rows++) {
				for (int cols = 0; cols < length1; cols++) {
					Item question = table1.getItem(rows);
					String a = (String) question.getItemProperty(array1[cols]).getValue().toString();
					ptable1.addCell(a);
				}
			}

			String[] array2 = table2.getColumnHeaders();
			int length2 = array2.length;
			PdfPTable ptable2 = new PdfPTable(length2);

			ptable2.setWidthPercentage(100);

			PdfPCell cell2 = new PdfPCell(new Paragraph(
					"NUMBER OF ALL TEACHERS AT THE SCHOOL INCLUDING THOSE ON LEAVE".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
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
					Item question = table2.getItem(rows);
					String a = (String) question.getItemProperty(array2[cols]).getValue().toString();
					ptable2.addCell(a);
				}
			}

			ptable2.setHeaderRows(1);
			ptable2.setHeaderRows(2);

			String[] array3 = table3.getColumnHeaders();
			int length3 = array3.length;
			PdfPTable ptable3 = new PdfPTable(length3);

			ptable3.setWidthPercentage(100);

			PdfPCell cell3 = new PdfPCell(new Paragraph("TEACHERS ON LEAVE".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			cell3.setColspan(length3);
			cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable3.addCell(cell3);

			for (int i = 0; i < length3; i++) {
				ptable3.addCell(new Phrase(array3[i],
						FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, BaseColor.BLACK)));
			}

			for (int rows = 0; rows < table3.size(); rows++) {
				for (int cols = 0; cols < length3; cols++) {
					Item question = table3.getItem(rows);
					String a = (String) question.getItemProperty(array3[cols]).getValue().toString();
					ptable3.addCell(a);
				}
			}

			ptable3.setHeaderRows(1);
			ptable3.setHeaderRows(2);

			String[] array4 = table4.getColumnHeaders();
			int length4 = array4.length;
			PdfPTable ptable4 = new PdfPTable(length4);

			ptable4.setWidthPercentage(100);

			PdfPCell cell4 = new PdfPCell(new Paragraph("TEACHERS ON DUTY EXCLUDING THOSE ON LEAVE".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			cell4.setColspan(length4);
			cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable4.addCell(cell4);

			for (int i = 0; i < length4; i++) {
				ptable4.addCell(new Phrase(array4[i],
						FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, BaseColor.BLACK)));
			}

			for (int rows = 0; rows < table4.size(); rows++) {
				for (int cols = 0; cols < length4; cols++) {
					Item question = table4.getItem(rows);
					String a = (String) question.getItemProperty(array4[cols]).getValue().toString();
					ptable4.addCell(a);
				}
			}

			ptable4.setHeaderRows(1);
			ptable4.setHeaderRows(2);

			String[] array5 = table5.getColumnHeaders();
			int length5 = array5.length;
			PdfPTable ptable5 = new PdfPTable(length5);

			ptable5.setWidthPercentage(100);

			PdfPCell cell5 = new PdfPCell(new Paragraph(
					"BONAFIDE TEACHERS EMPLOYED  AND STATIONED AT THE SCHOOL".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			cell5.setColspan(length5);
			cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable5.addCell(cell5);

			for (int i = 0; i < length5; i++) {
				ptable5.addCell(new Phrase(array5[i],
						FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, BaseColor.BLACK)));
			}

			for (int rows = 0; rows < table5.size(); rows++) {
				for (int cols = 0; cols < length5; cols++) {
					Item question = table5.getItem(rows);
					String a = (String) question.getItemProperty(array5[cols]).getValue().toString();
					ptable5.addCell(a);
				}
			}

			ptable5.setHeaderRows(1);
			ptable5.setHeaderRows(2);

			String[] array6 = table6.getColumnHeaders();
			int length6 = array6.length;
			PdfPTable ptable6 = new PdfPTable(length6);

			ptable6.setWidthPercentage(100);

			PdfPCell cell6 = new PdfPCell(new Paragraph(
					"TEACHERS WHO LEFT THE SCHOOL BUT STILL APPEARING ON PAYSHEET".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			cell6.setColspan(length6);
			cell6.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable6.addCell(cell6);

			for (int i = 0; i < length6; i++) {
				ptable6.addCell(new Phrase(array6[i],
						FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, BaseColor.BLACK)));
			}

			for (int rows = 0; rows < table6.size(); rows++) {
				for (int cols = 0; cols < length6; cols++) {
					Item question = table6.getItem(rows);
					String a = (String) question.getItemProperty(array6[cols]).getValue().toString();
					ptable6.addCell(a);
				}
			}

			ptable6.setHeaderRows(1);
			ptable6.setHeaderRows(2);

			String[] array7 = table7.getColumnHeaders();
			int length7 = array7.length;
			PdfPTable ptable7 = new PdfPTable(length7);

			ptable7.setWidthPercentage(100);

			PdfPCell cell7 = new PdfPCell(new Paragraph("TEACHERS ON VACATION/MATERNITY/SICK/MDL LEAVE".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			cell7.setColspan(length7);
			cell7.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable7.addCell(cell7);

			for (int i = 0; i < length7; i++) {
				ptable7.addCell(new Phrase(array7[i],
						FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, BaseColor.BLACK)));
			}

			for (int rows = 0; rows < table7.size(); rows++) {
				for (int cols = 0; cols < length7; cols++) {
					Item question = table7.getItem(rows);
					String a = (String) question.getItemProperty(array7[cols]).getValue().toString();
					ptable7.addCell(a);
				}
			}

			ptable7.setHeaderRows(1);
			ptable7.setHeaderRows(2);

			String[] array8 = table8.getColumnHeaders();
			int length8 = array8.length;
			PdfPTable ptable8 = new PdfPTable(length8);
			PdfPCell cell8 = new PdfPCell(new Paragraph(
					"TEACHERS APPEARING ON STATION FROM NOWHERE (GHOST TEACHERS)".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK)));
			cell8.setColspan(length8);
			cell8.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
			ptable8.addCell(cell8);

			for (int i = 0; i < length8; i++) {
				ptable8.addCell(new Phrase(array8[i],
						FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, BaseColor.BLACK)));
			}

			for (int rows = 0; rows < table8.size(); rows++) {
				for (int cols = 0; cols < length8; cols++) {
					Item question = table8.getItem(rows);
					String a = (String) question.getItemProperty(array8[cols]).getValue().toString();
					ptable8.addCell(a);
				}
			}

			ptable8.setHeaderRows(1);
			ptable8.setHeaderRows(2);

			document.add(Chunk.NEWLINE);
			document.add(new Paragraph("ENROLMENT BY GENDER AND GRADE",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.ITALIC, BaseColor.RED)));
			document.add(Chunk.NEWLINE);
			document.add(ptable1);

			NewPage.newPage(document);
			document.add(new Paragraph("NUMBER OF ALL TEACHERS AT THE SCHOOL INCLUDING THOSE ON LEAVE",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.ITALIC, BaseColor.RED)));
			document.add(Chunk.NEWLINE);
			document.add(ptable2);
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph("AUTHORISED ESTABLISHMENT.................... TEACHERS",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.ITALIC, BaseColor.RED)));
			document.add(new Paragraph("(FOR DEO ONLY)",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.ITALIC, BaseColor.RED)));

			document.add(Chunk.NEWLINE);
			NewPage.newPage(document);
			document.add(new Paragraph("TEACHERS ON LEAVE",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.ITALIC, BaseColor.RED)));
			document.add(Chunk.NEWLINE);
			document.add(ptable3);

			document.add(Chunk.NEWLINE);
			document.add(new Paragraph("TEACHERS ON DUTY EXCLUDING THOSE ON LEAVE",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.ITALIC, BaseColor.RED)));
			document.add(Chunk.NEWLINE);
			document.add(ptable4);
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("certification".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, Font.BOLD + Font.ITALIC, BaseColor.BLACK)));
			document.add(new Paragraph("The above information is certified true and correct",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.ITALIC, BaseColor.RED)));
			document.add(new Paragraph(
					"Name of School Head..............................................................  Signature...................  Date................",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.ITALIC, BaseColor.RED)));
			document.add(new Paragraph(
					"Name of School Deputy Head......................................................  Signature...................  Date.................",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.ITALIC, BaseColor.RED)));
			document.add(new Paragraph("................................................"));
			document.add(new Paragraph(".                                              ."));
			document.add(new Paragraph("               School Stamp                 ",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.ITALIC, BaseColor.LIGHT_GRAY)));
			document.add(new Paragraph(".                                              ."));
			document.add(new Paragraph(".                                              ."));
			document.add(new Paragraph("................................................"));

			document.newPage();

			document.add(new Paragraph("BONA FIDE MONTHLY RETURNS",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLACK)));

			document.add(new Paragraph(
					"NAME OF SCHOOL:  " + schoolname + "\t\t DEPT/STN: " + departmentCode + "/" + stationCode
							+ "\t\t  district".toUpperCase() + ": " + nameOfDistrict,
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLACK)));
			document.add(new Paragraph(
					"Month".toUpperCase() + "..." + thisMonth + "\t\t  Year".toUpperCase() + ": " + thisYear
							+ "\t\t  REG Number ".toUpperCase() + ":" + registrationNumber,
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.PLAIN, BaseColor.BLACK)));

			document.add(Chunk.NEWLINE);
			document.add(new Paragraph("BONAFIDE TEACHERS EMPLOYED  AND STATIONED AT THE SCHOOL",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.ITALIC, BaseColor.RED)));
			document.add(Chunk.NEWLINE);
			document.add(ptable5);

			document.add(Chunk.NEWLINE);
			document.add(new Paragraph("TEACHERS WHO LEFT THE SCHOOL BUT STILL APPEARING ON PAYSHEET",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.ITALIC, BaseColor.RED)));
			document.add(Chunk.NEWLINE);
			document.add(ptable6);

			NewPage.newPage(document);
			document.add(new Paragraph("TEACHERS ON VACATION/MATERNITY/SICK/MDL LEAVE",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.ITALIC, BaseColor.RED)));
			document.add(Chunk.NEWLINE);
			document.add(ptable7);

			document.add(Chunk.NEWLINE);
			document.add(new Paragraph("TEACHERS APPEARING ON STATION FROM NOWHERE (GHOST TEACHERS)",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.ITALIC, BaseColor.RED)));
			document.add(Chunk.NEWLINE);
			document.add(ptable8);
			document.add(Chunk.NEWLINE);

			document.add(new Phrase("certification".toUpperCase(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.BOLD + Font.ITALIC, BaseColor.BLACK)));
			document.add(new Paragraph("The above information is certified true and correct",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.ITALIC, BaseColor.RED)));
			document.add(new Paragraph(
					"Name of School Head....................................................................................................  Signature...................  Date................",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.ITALIC, BaseColor.RED)));
			document.add(new Paragraph(
					"Name of School Deputy Head...........................  Signature...................  Date.................",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.ITALIC, BaseColor.RED)));
			document.add(new Paragraph("................................................"));
			document.add(new Paragraph(".                                              ."));
			document.add(new Paragraph("               School Stamp                 ",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.ITALIC, BaseColor.LIGHT_GRAY)));
			document.add(new Paragraph(".                                              ."));
			document.add(new Paragraph(".                                              ."));
			document.add(new Paragraph("................................................"));

			document.add(new Paragraph("=================================END OF REPORT==========================",
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.ITALIC, BaseColor.RED)));
			document.close();

			FileDownloadFacilitator.downloadFile(path, download);

		} catch (Exception ee) {
			ee.printStackTrace();
			new Notification("Error", "Interrupted download", Notification.TYPE_ERROR_MESSAGE, true)
					.show(Page.getCurrent());
		}
	}
}