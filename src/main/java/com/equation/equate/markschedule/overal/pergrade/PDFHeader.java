/**
 *
 * @author Wellington
 */
package com.equation.equate.markschedule.overal.pergrade;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * @author Wellington
 *
 */
@SuppressWarnings("restriction")
public class PDFHeader {

	public void addHeader(PdfPTable ptable, int length, String grade) {
		PdfPCell cell = new PdfPCell(new Paragraph(grade.toUpperCase() + " Mark Schedule",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		cell.setColspan(length);
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(cell);

		PdfPCell no = new PdfPCell(new Paragraph("No",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		no.setColspan(1);
		no.setBackgroundColor(BaseColor.LIGHT_GRAY);
		no.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(no);

		PdfPCell cn = new PdfPCell(new Paragraph("Class",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		cn.setColspan(1);
		cn.setBackgroundColor(BaseColor.LIGHT_GRAY);
		cn.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(cn);

		PdfPCell names = new PdfPCell(new Paragraph("Students",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		names.setColspan(1);
		names.setBackgroundColor(BaseColor.LIGHT_GRAY);
		names.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(names);

		PdfPCell maths = new PdfPCell(new Paragraph(" English",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		maths.setColspan(4);
		maths.setBackgroundColor(BaseColor.LIGHT_GRAY);
		maths.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(maths);

		PdfPCell english = new PdfPCell(new Paragraph("Shona",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		english.setColspan(4);
		english.setBackgroundColor(BaseColor.LIGHT_GRAY);
		english.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(english);

		PdfPCell shona = new PdfPCell(new Paragraph("Mathematics",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		shona.setColspan(4);
		shona.setBackgroundColor(BaseColor.LIGHT_GRAY);
		shona.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(shona);

		PdfPCell gp = new PdfPCell(new Paragraph("General Paper",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		gp.setColspan(4);
		gp.setBackgroundColor(BaseColor.LIGHT_GRAY);
		gp.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(gp);

		PdfPCell agric = new PdfPCell(new Paragraph("Agriculture",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		agric.setColspan(4);
		agric.setBackgroundColor(BaseColor.LIGHT_GRAY);
		agric.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(agric);

		PdfPCell T = new PdfPCell(new Paragraph("Overall",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		T.setColspan(1);
		T.setBackgroundColor(BaseColor.LIGHT_GRAY);
		T.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(T);

		PdfPCell ut = new PdfPCell(new Paragraph("Ut",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		ut.setColspan(1);
		ut.setBackgroundColor(BaseColor.LIGHT_GRAY);
		ut.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(ut);

		PdfPCell cp = new PdfPCell(new Paragraph("Pos.",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		cp.setColspan(1);
		cp.setBackgroundColor(BaseColor.LIGHT_GRAY);
		cp.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(cp);

		// end

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

		PdfPCell five = new PdfPCell(new Paragraph("-",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		five.setColspan(1);
		five.setBackgroundColor(BaseColor.LIGHT_GRAY);
		five.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(five);

		// from here

		PdfPCell maths1 = new PdfPCell(new Paragraph("P1",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		maths1.setColspan(1);
		maths1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		maths1.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(maths1);

		PdfPCell maths2 = new PdfPCell(new Paragraph("P2",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		maths2.setColspan(1);
		maths2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		maths2.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(maths2);

		PdfPCell mathst = new PdfPCell(new Paragraph("T",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		mathst.setColspan(1);
		mathst.setBackgroundColor(BaseColor.LIGHT_GRAY);
		mathst.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(mathst);

		PdfPCell u1 = new PdfPCell(new Paragraph("U",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		u1.setColspan(1);
		u1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		u1.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(u1);

		PdfPCell eng1 = new PdfPCell(new Paragraph("P1",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		eng1.setColspan(1);
		eng1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		eng1.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(eng1);

		PdfPCell eng2 = new PdfPCell(new Paragraph("P2",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		eng2.setColspan(1);
		eng2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		eng2.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(eng2);

		PdfPCell engt = new PdfPCell(new Paragraph("T",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		engt.setColspan(1);
		engt.setBackgroundColor(BaseColor.LIGHT_GRAY);
		engt.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(engt);

		PdfPCell u2 = new PdfPCell(new Paragraph("U",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		u2.setColspan(1);
		u2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		u2.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(u2);

		PdfPCell shona1 = new PdfPCell(new Paragraph("P1",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		shona1.setColspan(1);
		shona1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		shona1.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(shona1);

		PdfPCell shona2 = new PdfPCell(new Paragraph("P2",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		shona2.setColspan(1);
		shona2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		shona2.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(shona2);

		PdfPCell shonat = new PdfPCell(new Paragraph("T",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		shonat.setColspan(1);
		shonat.setBackgroundColor(BaseColor.LIGHT_GRAY);
		shonat.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(shonat);

		PdfPCell u3 = new PdfPCell(new Paragraph("U",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		u3.setColspan(1);
		u3.setBackgroundColor(BaseColor.LIGHT_GRAY);
		u3.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(u3);

		PdfPCell gen1 = new PdfPCell(new Paragraph("P1",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		gen1.setColspan(1);
		gen1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		gen1.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(gen1);

		PdfPCell gen2 = new PdfPCell(new Paragraph("P2",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		gen2.setColspan(1);
		gen2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		gen2.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(gen2);

		PdfPCell gent = new PdfPCell(new Paragraph("T",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		gent.setColspan(1);
		gent.setBackgroundColor(BaseColor.LIGHT_GRAY);
		gent.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(gent);

		PdfPCell u4 = new PdfPCell(new Paragraph("U",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		u4.setColspan(1);
		u4.setBackgroundColor(BaseColor.LIGHT_GRAY);
		u4.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(u4);

		PdfPCell ag1 = new PdfPCell(new Paragraph("P1",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		ag1.setColspan(1);
		ag1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		ag1.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(ag1);

		PdfPCell ag2 = new PdfPCell(new Paragraph("P2",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		ag2.setColspan(1);
		ag2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		ag2.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(ag2);

		PdfPCell agt = new PdfPCell(new Paragraph("T",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		agt.setColspan(1);
		agt.setBackgroundColor(BaseColor.LIGHT_GRAY);
		agt.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(agt);

		PdfPCell u5 = new PdfPCell(new Paragraph("U",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		u5.setColspan(1);
		u5.setBackgroundColor(BaseColor.LIGHT_GRAY);
		u5.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(u5);

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

		PdfPCell six = new PdfPCell(new Paragraph("-",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, com.itextpdf.text.Font.ITALIC, BaseColor.BLUE)));
		six.setColspan(1);
		six.setBackgroundColor(BaseColor.LIGHT_GRAY);
		six.setHorizontalAlignment(Element.ALIGN_CENTER);
		ptable.addCell(six);
	}

}
