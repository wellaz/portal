package com.equation.accounts.vouchers.capture;

import java.util.ArrayList;

/**
 *
 * @author Wellington
 */
public class PaymentMethods {
	static String[] paymentmethods = { "Cash", "Cheque", "Bank Transfer (R T G S)" };

	public PaymentMethods() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<String> paymentMethodsArray() {
		ArrayList<String> data = new ArrayList<>();
		for (String s : paymentmethods)
			data.add(s);
		return data;
	}
}