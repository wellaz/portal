package com.equation.utils.pdf;

import java.awt.Font;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("restriction")
public class ParagraphSpace {

	public static void addSpace(Document document) {
		try {
			document.add(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLACK)));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}