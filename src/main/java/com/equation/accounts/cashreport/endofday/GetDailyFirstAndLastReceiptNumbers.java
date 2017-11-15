package com.equation.accounts.cashreport.endofday;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.equation.utils.date.DateUtility;

/**
 *
 * @author Wellington
 */
public class GetDailyFirstAndLastReceiptNumbers {
	Statement stm;
	ResultSet rs;
	private String fisrReceiptNumber;
	private String lastReceiptNumber;

	public GetDailyFirstAndLastReceiptNumbers(Statement stm, ResultSet rs) {
		this.stm = stm;
		this.rs = rs;
		ArrayList<String> receipts = getReceipts();
		int size = receipts.size();
		if (size > 0) {
			fisrReceiptNumber = receipts.get(0);
			lastReceiptNumber = receipts.get(size - 1);
		} else {
			fisrReceiptNumber = "0";
			lastReceiptNumber = "0";
		}
	}

	private ArrayList<String> getReceipts() {
		ArrayList<String> receipts = new ArrayList<>();
		String today = new DateUtility().getDate();
		String query = "SELECT receiptNumber FROM feesreceived WHERE date = '" + today + "'";
		try {

			rs = stm.executeQuery(query);
			if (rs.next()) {
				do {
					receipts.add(rs.getString(1));
				} while (rs.next());

			} else {

			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return receipts;
	}

	public String getFisrReceiptNumber() {
		return fisrReceiptNumber;
	}

	public String getLastReceiptNumber() {
		return lastReceiptNumber;
	}

}
