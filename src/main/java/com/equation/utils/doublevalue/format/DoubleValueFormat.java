package com.equation.utils.doublevalue.format;

import java.text.DecimalFormat;

/**
 *
 * @author Wellington
 */
public class DoubleValueFormat {

	public DoubleValueFormat() {
		// TODO Auto-generated constructor stub
	}

	public double format(double num) {
		DecimalFormat df = new DecimalFormat("0.00");
		double n = Double.parseDouble(df.format(num));
		return n;
	}
}