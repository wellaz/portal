package com.equation.accounts.administrator.receiptsbooks;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

/**
 *
 * @author Wellington
 */
public class ValidateReceiptBook {
	Statement stm;
	ResultSet rs;
	String schoolID;
	ArrayList<String> booknumbers = new ArrayList<>();
	ArrayList<String> firstreceiptnumbers = new ArrayList<>();
	ArrayList<String> lastreceiptnumbers = new ArrayList<>();
	ArrayList<String> statuses = new ArrayList<>();

	public ValidateReceiptBook(Statement stm, ResultSet rs, String schoolID) {
		this.rs = rs;
		this.stm = stm;
		this.schoolID = schoolID;
	}

	public void populateReceiptBookDetailsCollection() {
		String selectQuery = "SELECT bookNumber,firstReceiptNumber,finalReceptNumber,status FROM receiptbooks,schools WHERE schools.schoolID = '"
				+ schoolID + "'";
		try {
			rs = stm.executeQuery(selectQuery);
			if (rs.next()) {
				do {
					String bookNumber = rs.getString(1);
					String firstReceipt = rs.getString(2);
					String lastReceipt = rs.getString(3);
					String status = rs.getString(4);
					booknumbers.add(bookNumber);
					firstreceiptnumbers.add(firstReceipt);
					lastreceiptnumbers.add(lastReceipt);
					statuses.add(status);
				} while (rs.next());
			} else {
				booknumbers.add("0");
				firstreceiptnumbers.add("0");
				lastreceiptnumbers.add("0");
				statuses.add("Closed");
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}

	private boolean isOpenStatusFound(String status) {
		List<String> numberslist = null;
		int arrsize = statuses.size();
		String[] dat = new String[arrsize];
		for (int i = 0; i < arrsize; i++)
			dat[i] = statuses.get(i);
		numberslist = Arrays.asList(dat);
		return numberslist.stream().anyMatch(t -> status.equals(t));
	}

	private boolean isBookNumberValid(String booknumber) {
		List<String> numberslist = null;
		int arrsize = booknumbers.size();
		String[] dat = new String[arrsize];
		for (int i = 0; i < arrsize; i++)
			dat[i] = booknumbers.get(i);
		numberslist = Arrays.asList(dat);
		return numberslist.stream().anyMatch(t -> booknumber.equals(t));
	}

	private boolean isFirstReceiptNumberValid(String receiptnumber) {
		List<String> numberslist = null;
		int arrsize = firstreceiptnumbers.size();
		String[] dat = new String[arrsize];
		for (int i = 0; i < arrsize; i++)
			dat[i] = firstreceiptnumbers.get(i);
		numberslist = Arrays.asList(dat);
		return numberslist.stream().anyMatch(t -> receiptnumber.equals(t));
	}

	private boolean isLastReceiptNumberValid(String receiptnumber) {
		List<String> numberslist = null;
		int arrsize = lastreceiptnumbers.size();
		String[] dat = new String[arrsize];
		for (int i = 0; i < arrsize; i++)
			dat[i] = lastreceiptnumbers.get(i);
		numberslist = Arrays.asList(dat);
		return numberslist.stream().anyMatch(t -> receiptnumber.equals(t));
	}

	@SuppressWarnings("deprecation")
	public void validateRecord(String schoolID, String bookNumber, String firstReceiptNumber, String finalReceptNumber,
			String date, String time, String status, TextField bookNumberField, TextField firstReceiptNumberField,
			TextField finalReceptNumberField, String userID) {
		populateReceiptBookDetailsCollection();
		if (!isBookNumberValid(bookNumber)) {
			if (!isFirstReceiptNumberValid(firstReceiptNumber)) {
				if (!isLastReceiptNumberValid(finalReceptNumber)) {
					int first = Integer.parseInt(firstReceiptNumber);
					int last = Integer.parseInt(finalReceptNumber);
					if (last > first) {
						if (!isOpenStatusFound(status)) {
							InsertDataIntoReceiptBooksTable insertDataIntoReceiptBooksTable = new InsertDataIntoReceiptBooksTable(
									stm);
							insertDataIntoReceiptBooksTable.insertData(schoolID, bookNumber, firstReceiptNumber,
									finalReceptNumber, date, time, status, userID);
							new Notification("Success",
									"Receipt Book Number " + bookNumber + "<br/>Initial Receipt " + firstReceiptNumber
											+ "<br/>Last Receipt Number " + finalReceptNumber
											+ "<br/>The Book is valid and was posted!",
									Notification.TYPE_HUMANIZED_MESSAGE, true).show(Page.getCurrent());
						} else {
							new Notification("Error",
									"The last Receipt Book Number requires Administrator approval before a new receipt book is entered.<br/><br/>Consider approving the receipt book number!",
									Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
						}
					} else {
						firstReceiptNumberField.clear();
						finalReceptNumberField.clear();
						new Notification("Error",
								"You might need to correct the  Initial and Final Receipt Numbers.<br/><br/>There could be a typing error!",
								Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
					}
				} else {
					finalReceptNumberField.focus();
					new Notification("Error",
							"There seems to be a match of last receipt<br/>number values with a previous receipt book.<br/>Chech and correct the last receipt number value!",
							Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
				}
			} else {
				firstReceiptNumberField.focus();
				new Notification("Error",
						"There seems to a match of first receipt<br/>number values with a previous receipt book.<br/>Check and correct the first receipt number value!",
						Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
			}
		} else {
			bookNumberField.focus();
			new Notification("Error",
					"The entered Receipt Book Number already exists!<br/>Check and correct the Receipt Book Number!",
					Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
		}
	}
}