package com.equation.equate.adjustedmark;

/**
 *
 * @author Wellington
 */
public class GetAdjustedMark {

	public GetAdjustedMark() {
	}

	public static int getAdjustedMark(int studentmark, int totalmark, int percentage) {
		double value = ((double) studentmark / totalmark) * (double) percentage;
		return (int) Math.round(value);
	}
}