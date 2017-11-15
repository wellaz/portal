package com.equation.accounts.sundryaccount.receipt;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

import com.equation.utils.date.DateUtility;

/**
 *
 * @author Wellington
 */
public class PrintSundryReceipt implements Printable {
	int receiptnumber;
	double amountTendered;

	String schoolName, schoolPostal, schoolPhone, schoolCell, schoolEmail;
	String tellerName, term, receivedFrom, description;

	public PrintSundryReceipt(int receiptnumber, double amountTendered, String schoolName, String tellerName,
			String schoolPostal, String schoolPhone, String schoolCell, String schoolEmail, String term,
			String receivedFrom, String description) {
		this.receiptnumber = receiptnumber;
		this.amountTendered = amountTendered;
		this.schoolName = schoolName;
		this.tellerName = tellerName;
		this.schoolPostal = schoolPostal;
		this.schoolPhone = schoolPhone;
		this.schoolCell = schoolCell;
		this.schoolEmail = schoolEmail;
		this.term = term;
		this.receivedFrom = receivedFrom;
		this.description = description;

	}

	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss a";

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		int result = NO_SUCH_PAGE;
		if (pageIndex == 0) {
			Graphics2D g2d = (Graphics2D) graphics;

			g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
			Font font = new Font("Monospaced", Font.BOLD, 7);
			g2d.setFont(font);

			try {
				/*
				 * Draw Image* assume that printing reciept has logo on top that
				 * logo image is in .gif format .png also support image
				 * resolution is width 100px and height 50px image located in
				 * root--->image folder
				 */
				int x = 0; // print start at 0 on x axies
				int y = 10; // print start at 10 on y axies
				int imagewidth = 100;
				int imageheight = 30;
				BufferedImage read = ImageIO.read(this.getClass().getResource("images/systemlogo.png"));
				// draw image
				g2d.drawImage(read, x, y, imagewidth, imageheight, null);
				g2d.drawLine(0, y + 60, 180, y + 60); // draw line
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				/* Draw Header */
				int y = 80;
				g2d.drawString("ministry of primary and secondary education".toUpperCase(), 0, y);
				g2d.drawString(schoolName, 10, y + 10);
				g2d.drawString(schoolPostal, 30, y + 20);
				g2d.drawString("Phone: " + schoolPhone, 30, y + 30);
				g2d.drawString("Cell: " + schoolCell, 30, y + 40);
				g2d.drawString("Email: " + schoolEmail, 30, y + 50);
				g2d.drawLine(0, y + 60, 180, y + 60);

				g2d.drawString("Date: " + now(), 5, y + 70); // print date
				g2d.drawString("Teller :" + tellerName, 5, y + 80);
				g2d.drawLine(0, y + 90, 180, y + 90);
				g2d.drawString("Receipt Number " + receiptnumber, 30, y + 100);
				g2d.drawString("Received From: " + receivedFrom, 30, y + 110);
				g2d.drawString(description, 0, y + 120);

				// shift a line by adding 10 to y value
				g2d.drawLine(0, y + 130, 180, y + 130);

				g2d.drawString("TOTAL PAID $" + amountTendered, 10, y + 140);
				g2d.drawString("For :" + term, 10, y + 150);

				/* Draw Colums */
				g2d.drawLine(0, y + 160, 180, y + 160);

				/* Footer */
				font = new Font("Arial", Font.ITALIC + Font.PLAIN, 5); // changed
																		// font
				// size
				g2d.setFont(font);
				g2d.drawString("\u00A9 " + new DateUtility().getYear() + "\u2192  Equation \u2122", 30, y + 170);
				// end of the reciept
			} catch (Exception r) {
				r.printStackTrace();
			}
			result = PAGE_EXISTS;
		}
		return result;
	}

	public static String now() {
		// get current date and time as a String output
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
}